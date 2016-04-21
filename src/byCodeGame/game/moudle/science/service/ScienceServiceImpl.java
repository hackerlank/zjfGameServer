package byCodeGame.game.moudle.science.service;

import java.util.Map;

import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.MainBuildingConfigCache;
import byCodeGame.game.cache.file.RoleScienceConfigCache;
import byCodeGame.game.cache.file.VipConfigCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Build;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.MainBuildingConfig;
import byCodeGame.game.entity.file.RoleScienceConfig;
import byCodeGame.game.entity.file.VipConfig;
import byCodeGame.game.entity.po.LevyInfo;
import byCodeGame.game.entity.po.RoleScienceQueue;
import byCodeGame.game.entity.po.VisitScienceData;
import byCodeGame.game.moudle.chapter.ChapterConstant;
import byCodeGame.game.moudle.inCome.InComeConstant;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.science.ScienceConstant;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.InComeUtils;
import byCodeGame.game.util.Utils;

public class ScienceServiceImpl implements ScienceService {
	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private InComeService inComeService;
	public void setInComeService(InComeService inComeService) {
		this.inComeService = inComeService;
	}
	/**
	 * 获取玩家科技信息
	 * 
	 * @author xjd
	 */
	public Message getRoleScienceInfo(Role role) {
		Message message = new Message();
		message.setType(ScienceConstant.GET_INFO_ROLE_SCIENCE);
		// 取出玩家科技信息
		Map<Integer, Integer> map = role.getRoleScienceMap();

		message.put((byte) map.size());
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			message.putInt(entry.getKey());
			message.putInt(entry.getValue());
			RoleScienceConfig x = RoleScienceConfigCache.getRoleScienceConfig(entry.getKey(), entry.getValue() + 1);
			if (x == null) {
				message.putInt(ScienceConstant.NOT_OPEN);
				message.putInt(ScienceConstant.NOT_OPEN);
				x = RoleScienceConfigCache.getRoleScienceConfig(entry.getKey(), entry.getValue());
			} else {
				message.putInt(x.getCost());
				message.putInt(x.getNeedLv());
			}
			message.put((byte) x.getType());
		}
		return message;
	}

	/**
	 * 升级科技
	 * 
	 * @author xjd
	 */
	public Message upgradeRoleScience(Role role, int type) {
		Message message = new Message();
		message.setType(ScienceConstant.UPGRADE_ROLE_SCIENCE);
		// 判断类型是否合法
		if (type < ScienceConstant.MIN_ROLE_SCIENCE || type > ScienceConstant.MAX_ROLE_SCIENCE) {
			message.putShort(ErrorCode.ERR_TYPE_SCIENCE);
			return message;
		}
		if (!role.getRoleScienceMap().containsKey(type)
				|| role.getRoleScienceMap().get(type) == ScienceConstant.NOT_OPEN) {
			message.putShort(ErrorCode.SCIENCE_BUILD_LV_NO_REACH);
			return message;
		}
		int scienceLv = role.getRoleScienceMap().get(type);
		// 取出对应升级的科技配置表信息
		int newLv = scienceLv + ScienceConstant.GET_LV;
		RoleScienceConfig roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(type, newLv);
		if (roleScienceConfig == null) {
			message.putShort(ErrorCode.ERR_MAX_SCIENCE);
			return message;
		}
		// 判断玩家战功是否足够
		if (role.getExploit() < roleScienceConfig.getCost()) {
			message.putShort(ErrorCode.NO_EXPLOIT);
			return message;
		}
		Short buildLv = role.getBuild().getBuildLvMap().get(InComeConstant.TYPE_TECH);
		if(buildLv == null){
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		// 建筑等级是否达到
		if (buildLv < roleScienceConfig.getNeedLv()) {
			message.putShort(ErrorCode.BUILD_LV_NO_REACH);
			return message;
		}
		// 扣除玩家战功，科技等级+1
		roleService.addRoleExploit(role, -roleScienceConfig.getCost());
		role.getRoleScienceMap().put(type, newLv);
		role.setChange(true);
		this.taskService.checkScienceTask(role, type);
		// 获取解锁的位置
		// if(type<=ScienceConstant.MAX_FORMATION__SCIENCE&&type>ScienceConstant.MIN_ROLE_SCIENCE){
		// int [] arr=roleScienceConfig.getFormationPosition(role);
		// for (int i = 0; i < arr.length; i++) {
		// if(arr[i]>0){
		// if(role.getFormationData().get(type).getData().get((byte)arr[i])==1){//将解锁位置为未解锁的位置
		// role.getFormationData().get(type).getData().put((byte)arr[i],2);//设置为已解锁
		// }
		// }
		// }
		// }

		// if(role.getScienceQueue().getTime() >=
		// ScienceConstant.MAX_QUEUE_TIME){
		// //关闭建筑队列
		// role.getScienceQueue().setOpen(ScienceConstant.CLOSE_QUEUE);
		// }
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	public void refreshRoleScienceTime(Role role) {

		long nowTime = Utils.getNowTime();
		RoleScienceQueue roleScienceQueue = role.getScienceQueue();

		if (roleScienceQueue == null)
			return;
		// 当前日期 - 上一次更新时间
		int cdTime = (int) ((nowTime - roleScienceQueue.getLastUpTime()));
		if (cdTime <= 0)
			cdTime = 0;
		int nowBuildTime = roleScienceQueue.getTime() - cdTime;
		if (nowBuildTime <= 0) {
			nowBuildTime = 0;
			roleScienceQueue.setOpen(ScienceConstant.OPEN_QUEUE);
		}
		roleScienceQueue.setTime(nowBuildTime);
		roleScienceQueue.setLastUpTime(nowTime);
		role.getBuild().setChange(true);
	}

	/**
	 * 获取玩家科技等级
	 * 
	 * @author xjd
	 */
	public int getRoleScienceById(Role role, int scienceId) {
		Map<Integer, Integer> roleScience = role.getRoleScienceMap();
		return roleScience.get(scienceId);
	}

	@Override
	public Message getRoleScienceQueue(Role role) {
		Message message = new Message();
		message.setType(ScienceConstant.GET_ROLE_SCIENCE_QUEUE);
		this.refreshRoleScienceTime(role);
		message.putInt(role.getScienceQueue().getTime());
		message.put(role.getScienceQueue().getOpen());
		return message;
	}

	@Override
	public Message clearRoleScienceQueueTime(Role role) {
		Message message = new Message();
		message.setType(ScienceConstant.CLEAR_ROLE_SCIENCE_QUEUE_TIME);
		// 获取建筑队列cd时间
		int time = role.getScienceQueue().getTime();
		if (time > 0) {
			// 消耗的金币
			int consumeGold = ((time + ScienceConstant.TIME_UNIT - 1) / ScienceConstant.TIME_UNIT)
					* ScienceConstant.GOLDBYTIME_UNIT;
			if (role.getGold() >= consumeGold) {
				roleService.addRoleGold(role, -consumeGold);
				role.getScienceQueue().setTime(0);
				message.putShort(ErrorCode.SUCCESS);
			} else {
				message.putShort(ErrorCode.NO_GOLD);
			}
		} else {
			return null;
		}
		return message;
	}

	@Override
	public Message offerScience(Role role, int scienceId, int heroId) {
		Message message = new Message();
		message.setType(ScienceConstant.OFFER_SCIENCE);
		this.refreshRoleScienceTime(role);
		long nowTime = Utils.getNowTime();
		inComeService.checkHeroManual(role,(int) nowTime);
		Hero hero = role.getHeroMap().get(heroId);
		int needManual = GeneralNumConstantCache.getValue("USE_MANUAL_1");
		//检查是否有体力派遣
		if (!inComeService.checkCanSendHero(hero, needManual)){
			message.putShort(ErrorCode.NO_MANUAL);
			return message;
		}
		//检查次数
		VipConfig config = VipConfigCache.getVipConfigByVipLv(role.getVipLv());
		if(role.getScienceTime() >= config.getScienceEruptTimes())
		{
			message.putShort(ErrorCode.MAX_TIME_SCIENCE);
			return message;
		}
		Build build = role.getBuild();
		Map<Byte, Short> buildLvMap = build.getBuildLvMap();
		Integer scienceLv = role.getRoleScienceMap().get(scienceId);
		Short techLv = buildLvMap.get(InComeConstant.TYPE_TECH);
		if(techLv == null){
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(InComeConstant.TYPE_TECH,
				techLv);

		Map<Integer, LevyInfo> levyInfoMap = build.getLevyMap();
		
		if(!InComeUtils.checkLevy(role, InComeConstant.TYPE_TECH, heroId)){
			message.putShort(ErrorCode.HERO_IN_LEVY);
			return message;
		}

		int valueOther = 0;
		int type = 0;
		
		hero.addManual(-needManual);

		RoleScienceConfig roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(scienceId, scienceLv);
		if (scienceId == ScienceConstant.MONEY_SCIENCE_ID) {
			type = InComeConstant.TYPE_HOUSE;
			valueOther = roleScienceConfig.getValue();
		} else if (scienceId == ScienceConstant.FOOD_SCIENCE_ID) {
			type = InComeConstant.TYPE_FARM;
			valueOther = roleScienceConfig.getValue();
		}
		
		//刷新所有库存
		inComeService.refreshAllCache(role, (int) nowTime);

		LevyInfo levyInfo = new LevyInfo();
		levyInfo.setCd(mainBuildingConfig.getCd());
		levyInfo.setHeroId(heroId);
		levyInfo.setStartTime(nowTime);
		levyInfo.setType(InComeConstant.TYPE_TECH);
		levyInfo.setValue(type);
		levyInfo.setValueOther(valueOther);
		levyInfo.setLastRefreshTime((int) nowTime);

		levyInfoMap.put(heroId, levyInfo);
		role.setScienceTime(role.getScienceTime() + ScienceConstant.CAN_OPEN);
		
		build.setChange(true);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(levyInfo.getCd());

		return message;
	}

	public Message getOfferScienceAward(Role role, int startTimeId) {
		Message message = new Message();
		message.setType(ScienceConstant.GET_VISIT_SCIENCE_AWARD);

		Build build = role.getBuild();

		VisitScienceData visitScienceData = build.getVisitScienceDataMap().get(startTimeId);
		if (visitScienceData == null) {
			return null;
		}

		int num = visitScienceData.getNum();// 战功值
		int heroId = visitScienceData.getHeroId();

		// 处理奖励
		roleService.addRoleExploit(role, num);

		// 清除数据
		build.getVisitScienceDataMap().remove(startTimeId);
		build.setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		message.put(ChapterConstant.Award.EXPLOIT.getVal());
		message.putInt(num);
		message.putInt(heroId);

		return message;
	}
}
