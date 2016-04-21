package byCodeGame.game.moudle.activity.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import byCodeGame.game.cache.file.ActivityConfigCache;
import byCodeGame.game.cache.local.ActivityCache;
import byCodeGame.game.cache.local.LegionCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.db.dao.ActivityDao;
import byCodeGame.game.entity.bo.Activity;
import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.entity.bo.Mail;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.ActivityConfig;
import byCodeGame.game.entity.po.ActivityMessage;
import byCodeGame.game.entity.po.ActivityData;
import byCodeGame.game.entity.po.LegionConquerCityActivityData;
import byCodeGame.game.moudle.activity.ActivityConstant;
import byCodeGame.game.moudle.chat.ChatConstant;
import byCodeGame.game.moudle.chat.service.ChatService;
import byCodeGame.game.moudle.legion.LegionConstant;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.Utils;

public class ActivityServiceImpl implements ActivityService {

	private ActivityDao activityDao;

	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private ChatService chatService;

	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}

	private MailService mailService;

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	@Override
	public void initActivity() {
		int nowTime = Utils.getNowTime();
		Map<Integer, ActivityConfig> map = ActivityConfigCache.getAllActivityMap();
		try (Connection conn = dataSource.getConnection()) {
			for (ActivityConfig config : map.values()) {
				int startTime = config.getStartTime();
				int endTime = config.getEndTime();
				int activityId = config.getActivityId();

				if (nowTime >= startTime) {
					if (endTime == -1 || nowTime < endTime) {
						Activity activity = activityDao.getActivityByActivityId(activityId);
						if (activity != null) {
							this.deserializeActivityData(activity, config);
						}
						if (activity == null) {
							activity = this.createActivity(activityId);
							int id = activityDao.insertActivity(activity, conn);
							activity.setId(id);
							activity.setStatus(ActivityConstant.STATUS_START);
						}

						ActivityCache.addActivity(activity);
					}
				} else {
					Activity activity = activityDao.getActivityByActivityId(activityId);
					if (activity == null) {
						activity = this.createActivity(activityId);
						int id = activityDao.insertActivity(activity, conn);
						activity.setId(id);
						activity.setStatus(ActivityConstant.STATUS_WAIT);
					}
					ActivityCache.addActivity(activity);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Activity createActivity(int activityId) {
		Activity activity = new Activity();
		activity.setActivityId(activityId);
		activity.setStatus(ActivityConstant.STATUS_WAIT);
		return activity;
	}

	@Override
	public void noticeActivity(ActivityMessage am) {
		for (Activity activity : ActivityCache.getAllActivityMap().values()) {
			this.checkActivityStart(am.getNowTime(), activity);
			byte status = activity.getStatus();
			if (status == ActivityConstant.STATUS_START) {
				int activityId = activity.getActivityId();
				ActivityConfig config = ActivityConfigCache.getAllActivityMap().get(activityId);
				int activityType = config.getActivityType();

				if (activityType == ActivityConstant.ACTIVITY_WORLD_ATTACK_CITY) {// 公会攻城活动
					this.activityWorldAttackCity(activity, config, am);
				}
			}
		}
	}

	/**
	 * 
	 * @param activityId
	 * @return
	 * @author wcy 2016年4月20日
	 */
	private void checkActivityStart(int nowTime, Activity activity) {
		byte status = activity.getStatus();
		int activityId = activity.getActivityId();

		if (status == ActivityConstant.STATUS_WAIT) {
			ActivityConfig config = ActivityConfigCache.getAllActivityMap().get(activityId);
			if (config == null) {
				activity.setStatus(ActivityConstant.STATUS_END);
				return;
			}
			int startTime = config.getStartTime();
			if (nowTime >= startTime) {
				activity.setStatus(ActivityConstant.STATUS_START);
			}
		}
	}

	/**
	 * 反序列化数据
	 * @param activity
	 * @param config
	 * @author wcy 2016年4月20日
	 */
	private void deserializeActivityData(Activity activity, ActivityConfig config) {
		int type = config.getActivityType();
		Map<Integer, ActivityData> map = activity.getDataMap();
		if (type == ActivityConstant.ACTIVITY_WORLD_ATTACK_CITY) {
			Map<Integer, LegionConquerCityActivityData> newMap = new HashMap<>();
			for (Entry<Integer, ActivityData> entry : map.entrySet()) {
				int id = entry.getKey();
				ActivityData p = entry.getValue();

				LegionConquerCityActivityData np = new LegionConquerCityActivityData(p.getProgressStr());
				newMap.put(id, np);
			}
			
			map.clear();
			
			for(Entry<Integer,LegionConquerCityActivityData> entry:newMap.entrySet()){
				map.put(entry.getKey(), entry.getValue());
			}			
		}

	}
	
	/**
	 * 公会攻城活动
	 * @param activity
	 * @param config
	 * @param am
	 * @author wcy 2016年4月20日
	 */
	private void activityWorldAttackCity(Activity activity, ActivityConfig config, ActivityMessage am) {
		int action = am.getAction();
		if (action == ActivityMessage.CHANGE_CITY) {
			Role role = am.getRole();
			int cityId = am.getValue();
			int legionId = role.getLegionId();
			if (legionId != 0) {
				Map<Integer, ActivityData> map = activity.getDataMap();
				ActivityData p = map.get(legionId);
				if (p == null) {
					p = new LegionConquerCityActivityData();
					map.put(legionId, p);
				}
				// 移除活动中所有公会已经占有该城市的数据
				for (ActivityData a : map.values()) {
					LegionConquerCityActivityData lccapa = (LegionConquerCityActivityData) a;
					lccapa.getCityIdSet().remove(cityId);
				}
				// 加入城市
				LegionConquerCityActivityData lccap = (LegionConquerCityActivityData) p;
				lccap.getCityIdSet().add(cityId);

				// 检查是否到达了指定城的数量
				String content = config.getActivityContent();
				int CITY_TARGET_NUM = Integer.parseInt(content);
				Legion legion = LegionCache.getLegionById(legionId);
				if (lccap.getCityIdSet().size() >= CITY_TARGET_NUM) {
					activity.setStatus(ActivityConstant.STATUS_END);

					// 发放奖励
					String award = config.getAward();
					Set<Integer> roleIdSet = legion.getMemberSet();

					// 新建邮件对象,邮件内容还没有写
					Mail mail = new Mail();
					mail.setTitle(config.getMailTitle());
					mail.setContext(config.getMailContext());
					mail.setAttached(award);

					for (Integer roleId : roleIdSet) {
						Role targetRole = RoleCache.getRoleById(roleId);
						mailService.sendSYSMail(targetRole, mail);
					}
					StringBuilder sb = new StringBuilder();
					sb.append(config.getActivityType()).append(",");
					sb.append(legion.getName());
					chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_ACTIVITY_END, sb.toString());
				}
			}
		}
		if (action == ActivityMessage.DISMISS_LEGION) {
			int legionId = am.getValue();
			Map<Integer, ActivityData> map = activity.getDataMap();
			map.remove(legionId);
		}

	}

	@Override
	public Message showActivity(Role role, int activityId) {
		Message message = new Message();
		message.setType(ActivityConstant.SHOW_ACTIVITY);
		Map<Integer, Activity> allActivityMap = ActivityCache.getAllActivityMap();
		Activity activity = allActivityMap.get(activityId);
		if (activity == null) {
			return null;
		}

		ActivityConfig config = ActivityConfigCache.getActivityConfigByActivityId(activityId);
		if (config == null) {
			return null;
		}

		int type = config.getActivityType();
		byte status = activity.getStatus();

		StringBuilder sb = new StringBuilder();
		if (type == ActivityConstant.ACTIVITY_WORLD_ATTACK_CITY) {
			if (status == ActivityConstant.STATUS_START) {
				this.getActivityWorldAttackCityData(role, activity, sb);
			}
		}
		message.put(status);
		message.putString(sb.toString());

		return message;
	}

	/**
	 * 攻城活动
	 * 
	 * @param role
	 * @param activity
	 * @param sb
	 * @author wcy 2016年4月20日
	 */
	private void getActivityWorldAttackCityData(Role role, Activity activity, StringBuilder sb) {
		Map<Integer, ActivityData> map = activity.getDataMap();
		List<ActivityData> list = new ArrayList<>(map.values());

		Collections.sort(list, new Comparator<ActivityData>() {

			@Override
			public int compare(ActivityData o1, ActivityData o2) {
				LegionConquerCityActivityData p1 = (LegionConquerCityActivityData) o1;
				LegionConquerCityActivityData p2 = (LegionConquerCityActivityData) o2;

				int size1 = p1.getCityIdSet().size();
				int size2 = p2.getCityIdSet().size();
				if (size1 == size2) {
					int nowTime1 = p1.getNowTime();
					int nowTime2 = p2.getNowTime();
					return nowTime1 - nowTime2;
				}
				return size2 - size1;
			}
		});

		byte hasFirstLegion = 0;
		String firstLegionName = "";
		int firstLegionNumber = 0;
		byte hasCurrentLegion = 0;
		String currentLegionName = "";
		int currentLegionNumber = 0;
		if (list.size() != 0) {
			hasFirstLegion = 1;
			LegionConquerCityActivityData p = (LegionConquerCityActivityData) list.get(0);
			firstLegionNumber = p.getCityIdSet().size();
			int legionId = p.getLegionId();
			Legion legion = LegionCache.getLegionById(legionId);
			firstLegionName = legion.getName();
		}

		int currentLegionId = role.getLegionId();
		if (currentLegionId != LegionConstant.NOT_IN_LEGION) {
			hasCurrentLegion = 1;
			ActivityData activityProgress = map.get(currentLegionId);
			if (activityProgress != null) {
				LegionConquerCityActivityData p = (LegionConquerCityActivityData) activityProgress;
				currentLegionNumber = p.getCityIdSet().size();
				Legion legion = LegionCache.getLegionById(currentLegionId);
				currentLegionName = legion.getName();
			}
		}

		sb.append(hasFirstLegion).append(",");
		sb.append(firstLegionName).append(",");
		sb.append(firstLegionNumber).append(",");
		sb.append(hasCurrentLegion).append(",");
		sb.append(currentLegionName).append(",");
		sb.append(currentLegionNumber).append(",");
	}

}
