package byCodeGame.game.moudle.hero.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import byCodeGame.game.cache.file.ArmsConfigCache;
import byCodeGame.game.cache.file.ArmsResearchConfigCache;
import byCodeGame.game.cache.file.ArmyLvConfigCache;
import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.EquiptPrefixCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.HeroFavourConfigCache;
import byCodeGame.game.cache.file.HeroLvConfigCache;
import byCodeGame.game.cache.file.ItemCache;
import byCodeGame.game.cache.file.NotableCache;
import byCodeGame.game.cache.file.ProfessionCache;
import byCodeGame.game.cache.file.RoleScienceConfigCache;
import byCodeGame.game.cache.file.ZhuanshengConfigCache;
import byCodeGame.game.cache.file.ZhuanzhiConfigCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.ArmsConfig;
import byCodeGame.game.entity.file.ArmsResearchConfig;
import byCodeGame.game.entity.file.ArmyLvConfig;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.EquiptPrefix;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.HeroFavourConfig;
import byCodeGame.game.entity.file.HeroLvConfig;
import byCodeGame.game.entity.file.Item;
import byCodeGame.game.entity.file.Profession;
import byCodeGame.game.entity.file.RoleScienceConfig;
import byCodeGame.game.entity.file.ZhuanshengConfig;
import byCodeGame.game.entity.file.ZhuanzhiConfig;
import byCodeGame.game.entity.po.FormationData;
import byCodeGame.game.entity.po.HeroSoldier;
import byCodeGame.game.entity.po.Rank;
import byCodeGame.game.moudle.arms.ArmsConstant;
import byCodeGame.game.moudle.chat.ChatConstant;
import byCodeGame.game.moudle.chat.service.ChatService;
import byCodeGame.game.moudle.hero.HeroConstant;
import byCodeGame.game.moudle.inCome.InComeConstant;
import byCodeGame.game.moudle.inCome.InComeConstant.HeroAttrType;
import byCodeGame.game.moudle.prop.PropConstant;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.moudle.pub.PubConstant;
import byCodeGame.game.moudle.rank.RankConstant;
import byCodeGame.game.moudle.rank.service.RankService;
import byCodeGame.game.moudle.role.RoleConstant;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.HeroUtils;
import byCodeGame.game.util.Utils;

public class HeroServiceImpl implements HeroService {

	private static final Logger heroLog = LoggerFactory.getLogger("hero");

	private HeroDao heroDao;

	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}

	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private PropService propService;

	public void setPropService(PropService propService) {
		this.propService = propService;
	}


	private Map<Integer, Short> heroUpDutyCampLvMap = new HashMap<>();


	private ChatService chatService;
	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}
	
	private RankService rankService;
	public void setRankService(RankService rankService){
		this.rankService = rankService;
	}

	@Override
	public void heroDataLoginHandle(Role role) {
		List<Hero> heroList = heroDao.getAllheroByRoleId(role.getId());
		for (Hero hero : heroList) {
			role.getHeroMap().put(hero.getHeroId(), hero);
			// if (hero.getStatus() == HeroConstant.HERO_STATUS_DUILIE) {
			// role.getRecruitHeroMap().put(hero.getHeroId(), hero);
			// }
			this.getManualOff(hero);
		}
	}

	public Message getHeroList(Role role) {
		Message message = new Message();
		message.setType(HeroConstant.GET_HERO_LIST);
		message.putInt(role.getHeroMap().size());

		for (Map.Entry<Integer, Hero> entry : role.getHeroMap().entrySet()) {
			Hero tempHero = entry.getValue();
			int heroId = tempHero.getHeroId();
			int currentStarNum = tempHero.getRank();
			int currentRebirthLv = tempHero.getRebirthLv();
			Prop heroStarProp = role.getConsumePropMap().get(HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX + heroId);
			int totalHeroStarPropNum = heroStarProp != null ? heroStarProp.getNum() : 0;
			HeroLvConfig lvConfig = HeroLvConfigCache.getHeroLvConfig((int) tempHero.getLv());
			HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(tempHero.getHeroId());
			HeroFavourConfig heroFavourConfig = HeroFavourConfigCache.getHeroFavourConfig(currentStarNum + 1);
			ZhuanshengConfig zhuanshengConfig = ZhuanshengConfigCache.getZhuanshengConfig(currentRebirthLv + 1);
			int fullStar = heroConfig.getMaxR();
			int needHeroStarPropNum = currentStarNum >= fullStar ? 0 : heroFavourConfig.getCost();
			int upRebirthNeedLv = zhuanshengConfig != null ? zhuanshengConfig.getNeedLv() : 0;
			byte isUpDuty = checkUpDuty(role, tempHero);
			byte isUpRebirth = checkUpRebirth(role, tempHero);
			byte isUpRank = checkUpRank(role, tempHero);
			byte isUpLv = checkUpLv(role, tempHero);
			int dutyLv = tempHero.getDutyLv();

			message.putInt(heroId);
			message.putShort(tempHero.getLv());
			message.putInt(tempHero.getExp() - lvConfig.getBaseExp());
			message.putInt(lvConfig.getNextExp() - lvConfig.getBaseExp());
			message.putInt(tempHero.getFightValue());
			message.put((byte) tempHero.getRank());
			message.put((byte) heroConfig.getMaxR());
			message.putInt(tempHero.getArmsNum());
			// TODO
			message.putInt(tempHero.getEmotion());
			message.put((byte) tempHero.getEmotionLv());
			// TODO
			message.putInt(0);
			StringBuilder equipSb = new StringBuilder();
			for (Map.Entry<Byte, Integer> equipEntry : tempHero.getEquipMap().entrySet()) {
				int propId = equipEntry.getValue();
				Prop prop = role.getPropMap().get(propId);
				equipSb.append(propId).append(",").append(prop.getLv()).append(",").append(prop.getPrefixId())
						.append(";");
			}
//			System.err.println(equipSb.toString());
			message.putString(equipSb.toString());
			HeroConfig use = HeroUtils.getCaptain(tempHero);
			message.putInt(use.getCaptain());
			message.putInt(use.getPower());
			message.putInt(use.getIntel());
			message.putInt(tempHero.getBingji());// 兵击
			message.putInt(tempHero.getUseArmyId());
			message.putInt(tempHero.getArmyInfoMap().get(tempHero.getUseArmyId()).getLv());
			message.putInt(tempHero.getArmyInfoMap().get(tempHero.getUseArmyId()).getSkillLv());
			message.putInt(tempHero.getSkillId());
			message.putInt(tempHero.getSkillLv());
			message.putInt(tempHero.getSkillBD());
			message.putInt(tempHero.getSkillBDLv());
			message.putInt(0);// 叫阵
			message.putInt(0);// 叫阵LV
			message.put((byte) tempHero.getMaxArmyQuality());
			message.put(tempHero.getBulidId());
			if (role.getBuild().getLevyMap().containsKey(tempHero.getHeroId())) {
				message.put(role.getBuild().getLevyMap().get(tempHero.getHeroId()).getType());
			} else {
				message.put(HeroConstant.BYTE_0);
			}

			message.putInt(totalHeroStarPropNum);
			message.putInt(needHeroStarPropNum);
			message.putInt(tempHero.getRebirthLv());// 当前转生数
			message.putInt(upRebirthNeedLv);// 转生需要的等级
			message.put(isUpLv);
			message.put(isUpRank);
			message.put(isUpDuty);
			message.put(isUpRebirth);
			message.putInt(tempHero.getManual());
			message.putInt(dutyLv);
			message.putInt(tempHero.getLocation());
		}
		return message;
	}

	public Message getHeroArmyInfo(Hero hero) {
		Message message = new Message();
		message.setType(HeroConstant.GET_HERO_ARMY_INFO);
		message.put((byte) hero.getArmyInfoMap().size());
		for (HeroSoldier soldier : hero.getArmyInfoMap().values()) {
			int armsConfigId = soldier.getSoldierId();

			hero.setChange(true);
			ArmsConfig armsConfig = ArmsConfigCache.getArmsConfigById(armsConfigId);
			ArmyLvConfig armyLvConfig = ArmyLvConfigCache.getArmyLvConfig(soldier.getLv());
			message.putInt(soldier.getSoldierId());
			message.putInt(soldier.getLv());
			message.putInt(armsConfig.getArmSkillID());
			message.putInt(soldier.getSkillLv());
			message.putInt(soldier.getExp() - armyLvConfig.getBaseExp());
			message.putInt(armyLvConfig.getSkillCost());
			message.put(hero.getUseArmyId() == soldier.getSoldierId() ? (byte) 1 : (byte) 0);
			message.putInt(armyLvConfig.getNextExp() - armyLvConfig.getBaseExp());
		}
		return message;
	}

	@Override
	public Message getAllHeroArmsInfo(Role role, Hero hero) {
		Message message = new Message();
		message.setType(HeroConstant.GET_ALL_HERO_ARMY_INFO);
		if (hero == null) {
			message.putShort(ErrorCode.NO_HERO);
			return message;
		}
		int heroId = hero.getHeroId();
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(heroId);
		int soldierType = ArmsConfigCache.getArmsConfigById(hero.getUseArmyId()).getFunctionType();
		HashMap<Integer, HeroSoldier> heroContainsSoldierMapping = hero.getArmyInfoMap();
		HashMap<Integer, ArrayList<ArmsConfig>> mapping = ArmsConfigCache.getArmsTypeQualityMapping().get(soldierType);

		Map<Integer, Prop> consumePropMap = role.getConsumePropMap();
		Prop prop1 = consumePropMap.get(HeroConstant.SMALL_SOLDIER_EXP_PROP_ID);
		Prop prop2 = consumePropMap.get(HeroConstant.MEDIUM_SOLDIER_EXP_PROP_ID);
		Prop prop3 = consumePropMap.get(HeroConstant.BIG_SOLDIER_EXP_PROP_ID);
		short prop1Num = prop1 != null ? prop1.getNum() : (short) 0;
		short prop2Num = prop2 != null ? prop2.getNum() : (short) 0;
		short prop3Num = prop3 != null ? prop3.getNum() : (short) 0;
		int size = 0;
		for (ArrayList<ArmsConfig> list : mapping.values()) {
			size += list.size();
		}
		message.putInt(10);
		message.putInt(20);
		double percent = 0.2;
		message.putDouble(percent);
		message.put((byte) hero.getMaxArmyQuality());
		message.putShort(prop1Num);
		message.putShort(prop2Num);
		message.putShort(prop3Num);
		message.putInt(size);
		for (ArrayList<ArmsConfig> list : mapping.values()) {
			for (ArmsConfig armsConfig : list) {
				message.putInt(armsConfig.getId());
				message.putInt(armsConfig.getRank());

				int soldierId = armsConfig.getId();
				if (heroContainsSoldierMapping.containsKey(soldierId)) {
					if (hero.getUseArmyId() == soldierId) {
						message.put(HeroConstant.SOLDIER_USING);
					} else {
						message.put(HeroConstant.SOLDIER_CAN_USE);
					}
					message.putInt(heroContainsSoldierMapping.get(soldierId).getLv());
				} else {
					message.put(HeroConstant.SOLDIER_CAN_NOT_USE);
					message.putInt(0);
				}
			}
		}
		return message;
	}

	public Message getRebLvInfo(Role role, Hero hero) {
		Message message = new Message();
		message.setType(HeroConstant.GET_HERO_RE_LV);
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(hero.getHeroId());
		HeroConfig config = HeroUtils.getCaptain(hero);
		int nextRlv = hero.getRebirthLv() + HeroConstant.BYTE_1;
		byte flag = HeroConstant.BYTE_0;
		HeroConfig config2 = HeroUtils.getCaptain(hero.getHeroId(), nextRlv);
		if (hero.getRebirthLv() + HeroConstant.BYTE_1 > heroConfig.getMaxR()) {
			nextRlv = hero.getRebirthLv();
			flag = HeroConstant.BYTE_2;
		}
		// 判断是否可以转生
		ZhuanshengConfig x = ZhuanshengConfigCache.getZhuanshengConfig(nextRlv);
		// 战功数值
		// if (role.getExploit() > x.getCost() && hero.getEmotion() >
		// x.getNeedE() && hero.getLv() > (short) x.getNeedLv()) {
		if (hero.getEmotionLv() >= x.getNeedE()) {
			flag = HeroConstant.BYTE_1;
		}
		message.put(flag);
		message.putInt(config.getCaptain());
		message.putInt(config.getPower());
		message.putInt(config.getIntel());
		message.putInt(config2.getCaptain());
		message.putInt(config2.getPower());
		message.putInt(config2.getIntel());
		return message;
	}

	public Message quickTrainHero(Role role, int heroId, int propId) {
		Message message = new Message();
		message.setType(HeroConstant.QUICK_TRAIN_HERO);
		Map<Integer, Hero> heroMap = role.getHeroMap();
		Hero hero = heroMap.get(heroId);
		if (hero == null) { // 玩家没有该武将
			message.putShort(ErrorCode.NO_HERO);
			return message;
		}
		// 判断等级限制
		Map<Integer, HeroLvConfig> heroLvConfigMap = HeroLvConfigCache.getHeroLvConfigMap();
		HeroLvConfig tempHeroLvConfig = heroLvConfigMap.get((int) role.getLv());
		if (hero.getExp() >= tempHeroLvConfig.getNextExp() - HeroConstant.TYPE_1) {
			message.putShort(ErrorCode.MAX_EXP_HERO);
			return message;
		}
		// 选择道具
		Prop prop = role.getPropMap().get(propId);
		Item item = ItemCache.getItemById(prop.getItemId());
		if (prop == null || item.getType() != HeroConstant.TYPE_HERO_EXP || prop.getNum() <= 0) {
			message.putShort(ErrorCode.NO_PROP);
			return message;
		}

		// 获得的经验值,武将等级无法超过人物等级
		int getExp = item.getValue();
		int formerFightValue = hero.getFightValue();

		// 允许加经验
		heroAddExp(hero, role, getExp);
		// prop.setNum((short) (prop.getNum() - 1));
		propService.addProp(role, prop.getItemId(), PropConstant.FUNCTION_TYPE_2, -1,
				SessionCache.getSessionById(role.getId()));
		hero.setChange(true);
		role.setChange(true);
		this.taskService.checkTrainTask(role, heroId, (byte) item.getQuality());

		message.putShort(ErrorCode.SUCCESS);
		HeroLvConfig currentHeroLvConfig = heroLvConfigMap.get((int) hero.getLv());
		message.putInt(hero.getExp() - currentHeroLvConfig.getBaseExp());
		message.putShort(hero.getLv());
		message.putInt(currentHeroLvConfig.getNextExp() - currentHeroLvConfig.getBaseExp());
		message.putInt(hero.getFightValue() - formerFightValue);

		return message;
	}

	/**
	 * 根据exp获得英雄等级
	 * 
	 * @param hero
	 * @param exp
	 * @return
	 * @author wcy
	 */
	private int getHeroLvByExp(Hero hero, int exp) {
		Map<Integer, HeroLvConfig> map = HeroLvConfigCache.getHeroLvConfigMap();
		int maxLv = 0;
		for (HeroLvConfig heroLvConfig : map.values()) {
			int baseExp = heroLvConfig.getBaseExp();
			int nextExp = heroLvConfig.getNextExp();
			int lv = heroLvConfig.getLv();
			if (lv > maxLv) {
				maxLv = lv;
			}
			if (exp >= baseExp && exp < nextExp) {
				return lv;
			}
		}
		return maxLv;
	}

	/**
	 * 武将增加经验
	 * 
	 * @param message
	 * 
	 * @param hero
	 * @param exp
	 */
	private StringBuilder heroAddExp(Hero hero, Role role, int exp) {
		// Map<Integer, HeroLvConfig> heroLvConfigMap =
		// HeroLvConfigCache.getHeroLvConfigMap();
		// HeroLvConfig tempHeroLvConfig = heroLvConfigMap.get(role.getLv());
		// if ((hero.getExp() + exp) >= tempHeroLvConfig.getNextExp() -
		// HeroConstant.TYPE_1) {
		// exp = tempHeroLvConfig.getNextExp() - HeroConstant.TYPE_1 -
		// hero.getExp();
		// }
		//
		// hero.setExp(hero.getExp() + exp);
		// this.countHeroLv(hero, role);
		// hero.setChange(true);
		StringBuilder sb = new StringBuilder();
		HeroLvConfig x = HeroLvConfigCache.getHeroLvConfig(hero.getLv());
		sb.append(hero.getHeroId()).append(",");
		sb.append(hero.getLv()).append(",");
		sb.append(hero.getExp() - x.getBaseExp()).append(",");
		sb.append(x.getNextExp() - x.getBaseExp()).append(",");
		Map<Integer, HeroLvConfig> heroLvConfigMap = HeroLvConfigCache.getHeroLvConfigMap();

		int heroLv = hero.getLv();
		int forHeroLv = getHeroLvByExp(hero, hero.getExp() + exp);
		if (forHeroLv <= role.getLv()) {
			hero.setExp(hero.getExp() + exp);
			hero.setLv((short) (forHeroLv));
		} else {
			HeroLvConfig config = heroLvConfigMap.get((int) role.getLv());
			// exp = config.getNextExp() - HeroConstant.TYPE_1;
			exp = (config.getNextExp() - HeroConstant.TYPE_1) - hero.getExp();
			if (exp <= HeroConstant.INT_0) {
				exp = HeroConstant.INT_0;
			}
			hero.setLv(role.getLv());
			hero.setExp(hero.getExp() + exp);
		}
		sb.append(exp).append(";");
		if (heroLv < hero.getLv()) {
			this.initHeroFightValue(role, hero);
		}
		hero.setChange(true);
		taskService.checkHeroLv(role);
		return sb;
	}

	public void countHeroLv(Hero hero, Role role) {
		// Map<Integer, HeroLvConfig> heroLvConfigMap =
		// HeroLvConfigCache.getHeroLvConfigMap();
		// for (int i = (int) hero.getLv(); i <= heroLvConfigMap.size(); i++) {
		// HeroLvConfig tempHeroLvConfig = heroLvConfigMap.get(i);
		// if (hero.getExp() >= tempHeroLvConfig.getBaseExp() && hero.getExp() <
		// tempHeroLvConfig.getNextExp()) {
		// // 角色升级
		// if (hero.getLv() < tempHeroLvConfig.getLv()) {
		// hero.setLv((short) tempHeroLvConfig.getLv());
		// this.taskService.checkHeroLv(role);
		// break;
		// }
		// }
		// }

		taskService.checkHeroLv(role);

	}

	// /**
	// * 英雄等级升级(服务器主动推送）
	// *
	// * @param role
	// * @param hero
	// * @author wcy
	// */
	// private void addHeroLv(Role role, Hero hero) {
	// IoSession is = SessionCache.getSessionById(role.getId());
	// int baseExp =
	// HeroLvConfigCache.getHeroLvConfig(hero.getLv()).getBaseExp();
	// Message message = new Message();
	// message.setType(HeroConstant.HERO_LV_UP);
	// message.putInt(hero.getHeroId());
	// message.putInt(hero.getLv());
	// message.putInt(hero.getExp() - baseExp);
	// is.write(message);
	// }

	public void addHero(IoSession is, Hero hero) {
		if (hero == null)
			return;
		StringBuilder sb = new StringBuilder();

		sb.append(hero.getHeroId()).append(",").append(hero.getLv()).append(",");

		// 检查是否有和招募相关的任务
		Role role = RoleCache.getRoleBySession(is);
		this.taskService.checkRecruitTask(role, (byte) 2, role.getHeroMap().size());

		Message message = new Message();
		message.setType(HeroConstant.ADD_HERO);
		message.putString(sb.toString());
		is.write(message);
		//TODO 修改为正确的武将品阶
		if(HeroConfigCache.getHeroConfigById(hero.getHeroId()).getQuality()
				>= 0)
		{
			chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_HERO,""+hero.getHeroId());
		}
		
	}

	/**
	 * 保存阵型
	 */
	public Message saveFormation(Role role, int useFormationID, byte key, int heroId) {
		Message message = new Message();
		message.setType(HeroConstant.SAVE_FORMATION);
		if (!getLocation(role, useFormationID, key, heroId)) {
			message.putShort(ErrorCode.ERR_LOC_NUM);
			return message;
		}
		// heroId!=2表示阵上武将下阵
		// role.getRecruitHeroMap().containsKey(heroId)判断武将是否存在
		if (!role.getHeroMap().containsKey(heroId) && heroId != HeroConstant.RM_HERO) {
			message.putShort(ErrorCode.NO_HERO);
			return message;
		}
		// int
		// scienceLv=role.getRoleScienceMap().get(Integer.valueOf(useFormationID));
		// if (Integer.valueOf(role.getLv()) <
		// RoleScienceConfigCache.getRoleScienceConfig(useFormationID).getOpenLv())
		// {
		// message.putShort(ErrorCode.NO_LV);
		// return message;
		// }
		Map<Byte, Integer> map = role.getFormationData().get(useFormationID).getData();
		if (heroId == HeroConstant.RM_HERO && role.getUseFormationID() == useFormationID) {// 默认阵型最后一名武将不能下阵
			int index = 0;
			for (Map.Entry<Byte, Integer> map2 : map.entrySet()) {
				if (map2.getValue() > HeroConstant.RM_HERO) {
					++index;
				}
			}
			if (index <= 1) {//
				message.putShort(ErrorCode.FORMATION_NO_HERO);
				return message;
			}
		}
		byte key2 = 0;// 保存替换武将原来所在的位置
		int heroId2;// 保存该位置原来的武将id
		heroId2 = role.getFormationData().get(useFormationID).getData().get(key);
		for (Map.Entry<Byte, Integer> map2 : map.entrySet()) {
			if (map2.getValue() == heroId) {
				key2 = map2.getKey();
			}
		}
		role.getFormationData().get(useFormationID).getData().put(key, heroId);
		if (key2 != (byte) 0 && heroId != HeroConstant.RM_HERO) {
			role.getFormationData().get(useFormationID).getData().put(key2, heroId2);
		}
		role.setChange(true);
		this.taskService.checkFormationTask(role, (byte) 1);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 判断该位置是否可用
	 * 
	 * @param role
	 * @param useFormationID
	 * @param key
	 * @return
	 */
	public boolean getLocation(Role role, int useFormationID, byte key, int heroId) {
		boolean flag = false;
		int cont = 0;
		// 位置不合法
		if (key > 9 || key < 1) {
			return false;
		}
		// int
		// scienceLv=role.getRoleScienceMap().get(Integer.valueOf(useFormationID));
		if(role == null){
//			System.out.println("role is Null");
		}
		for (Entry<Byte, Integer> x : role.getFormationData().get(useFormationID).getData().entrySet()) {
			if (x.getValue() > 2 && x.getKey() != key) {
				cont++;
			}
			if (x.getValue() == heroId) {
				cont--;
			}
		}
		RoleScienceConfig x = RoleScienceConfigCache.getRoleScienceConfig(1001, role.getRoleScienceMap().get(1001));
		if (cont < x.getValue()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 保存阵型
	 * 
	 * public Message saveFormation(Role role,byte fId,Map<Byte, Integer>
	 * dataMap){ Message message = new Message();
	 * message.setType(HeroConstant.SAVE_FORMATION); if(fId >
	 * FormationConfigCache.getMaps().size()){ return null; } if(dataMap.size()
	 * <= 0){ return null; } role.getFormationData().getData().clear();
	 * for(Map.Entry<Byte, Integer> entry : dataMap.entrySet()){ byte seatId =
	 * entry.getKey(); int tempHeroId = entry.getValue(); if(seatId <= 5 ||
	 * role.getHeroMap().containsKey(tempHeroId)){ Hero tempHero =
	 * role.getHeroMap().get(tempHeroId); if(tempHero == null) { continue; }
	 * if(tempHero.getStatus() == HeroConstant.HERO_STATUS_DUILIE){
	 * role.getFormationData().getData().put(seatId, tempHeroId); } } }
	 * 
	 * this.taskService.checkFormationTask(role, fId);
	 * message.putShort(ErrorCode.SUCCESS); return message; }
	 */

	/**
	 * 获取阵型数据
	 */
	public Message getFormationData(Role role, int id) {
		Message message = new Message();
		message.setType(HeroConstant.GET_FORMATION_DATA);
		// message.put(role.getUseFormationID());
		Map<Byte, Integer> temp = role.getFormationData().get(id).getData();
		message.put((byte) temp.size());
		for (Map.Entry<Byte, Integer> entry : role.getFormationData().get(id).getData().entrySet()) {
			message.put(entry.getKey());
			message.putInt(entry.getValue());
		}
		return message;
	}

	public Message getFormationDataAll(Role role) {
		Message message = new Message();
		message.setType(HeroConstant.GET_FORMATION_DATAALL);
		// message.put(role.getUseFormationID());
		RoleScienceConfig x = RoleScienceConfigCache.getRoleScienceConfig(1001, role.getRoleScienceMap().get(1001));
		message.putInt(x.getValue());
		message.put(HeroConstant.NUM_FORMATION);
		// 获取1~8所有阵型的数据 1~8为阵型类型
		for (int id = 1001; id <= 1003; id++) {
			Map<Integer, FormationData> formationDataMap = role.getFormationData();
			FormationData formationData = formationDataMap.get(id);
			Map<Byte, Integer> temp = formationData.getData();
			StringBuffer sb = new StringBuffer();
			sb.append(id).append(":");
			for (Map.Entry<Byte, Integer> entry : temp.entrySet()) {
				sb.append(entry.getKey()).append(",");
				sb.append(entry.getValue()).append(";");
			}
			message.putString(sb.toString());
		}
		message.putInt(role.getUseFormationID());
		return message;
	}

	/**
	 * 武将转生
	 * 
	 * @author xjd
	 */
	public Message upHeroRebirth(Role role, int heroId) {
		Message message = new Message();
		message.setType(HeroConstant.UP_REBIRTH_LV);
		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null) {
			return null;
		}
		// 当前进阶等级
		short nowRebirthLv = hero.getRebirthLv();

		// 检查最大转生
		if (nowRebirthLv >= HeroConstant.MAX_REBIRTH_LV) {
			message.putShort(ErrorCode.MAX_R_LV);
			return message;
		}

		// 检查转生所需等级
		ZhuanshengConfig zhuanshengConfig = ZhuanshengConfigCache.getZhuanshengConfig(nowRebirthLv + 1);
		int heroLv = hero.getLv();
		int needLv = zhuanshengConfig.getNeedLv();
		if (heroLv < needLv) {
			message.putShort(ErrorCode.NO_LV_HERO);
			return message;
		}

		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(heroId);
		hero.setRebirthLv((short) (nowRebirthLv + HeroConstant.RE_LV));
		hero.setLv(HeroConstant.RE_LV);
		hero.setExp(HeroConstant.RE_LV);

		// 处理转生收益
		this.updataHeroR(hero, heroConfig);
		// 武将转生重置面板属性
		this.initHeroFightValue(role, hero);
		this.taskService.checkRlv(role, heroId);
		int captain = HeroUtils.getCaptainValue(hero);
		int power = HeroUtils.getPowerValue(hero);
		int intel = HeroUtils.getIntelValue(hero);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(heroId);
		message.putShort(hero.getRebirthLv());
		message.putInt(captain);
		message.putInt(power);
		message.putInt(intel);
		
		if(chatService.checkSystemChat(ChatConstant.MESSAGE_TYPE_REBIRTH,hero.getRebirthLv())){			
			StringBuilder sb = getHeroRebirthStringBuilder(role, hero, heroConfig);
			chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_REBIRTH, sb.toString());
		}
		this.recordRankAndSendNotable(role, Utils.getNowTime());

		return message;
	}


	private StringBuilder getHeroRebirthStringBuilder(Role role, Hero hero, HeroConfig heroConfig) {
		String heroName = heroConfig.getName();
		StringBuilder sb = new StringBuilder();
		sb.append(role.getId()).append(",");
		sb.append(role.getName()).append(",");
		sb.append(hero.getRebirthLv()).append(",");
		sb.append(heroName).append(",");
		sb.append(Utils.getNationName(role.getNation())).append(",");
		sb.append(heroConfig.getQuality()).append(",");
		sb.append(hero.getHeroId()).append(",");
		return sb;
	}

	public Message updateSkill(Role role, int heroId, int skillType) {
		Message message = new Message();
		message.setType(HeroConstant.UP_SKILL);
		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null)
			return null;
		int nowLv = 0;

		if (skillType == HeroConstant.TYPE_1) {
			nowLv = hero.getSkillLv();
		} else if (skillType == (int) HeroConstant.R_TYPE_2) {
			nowLv = hero.getSkillBDLv();
		}
		// 等级判断
		if (nowLv + HeroConstant.R_TYPE_1 > (int) hero.getLv()) {
			message.putShort(ErrorCode.NO_LV_HERO);
			return message;
		}
		HeroLvConfig config = HeroLvConfigCache.getHeroLvConfig(nowLv);
		int cost = 0;
		if (skillType == 1) {
			cost = config.getInitiativeSkillCost();
		} else if (skillType == 2) {
			cost = config.getPassiveSkillCost();
		}

		if (role.getMoney() < cost) {
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}
		// 升级成功
		roleService.addRoleMoney(role, -cost);
		if (skillType == 1) {
			hero.setSkillLv(hero.getSkillLv() + 1);
		} else if (skillType == 2) {
			cost = config.getPassiveSkillCost();
			hero.setSkillBDLv(hero.getSkillBDLv() + 1);
		}
		hero.setChange(true);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	public Message deployArmsForHero(Role role, int heroId, int armsId) {
		Message message = new Message();
		message.setType(HeroConstant.DEPLOY_ARMS);
		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null)
			return null;

		Map<Integer, ArmsResearchConfig> armsResearchConfig = ArmsResearchConfigCache.getArmsResearchConfigMap();
		if (armsResearchConfig.get(armsId).getNeedNum() != role.getArmsResearchData().get(armsId)) {
			return null;
		}
		hero.setArmsId(armsId);
		this.taskService.checkDeployArmsTask(role, armsId);
		hero.setChange(true);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	@Override
	public Message addArmsNum(Role role, int armsNum) {
		Message message = new Message();
		message.setType(HeroConstant.ADD_ARMSNUM);
		int maxPopulation = role.getPopulation(role) + armsNum;
		if (maxPopulation > role.getPopulationLimit(role))
			return null;
		if (armsNum < 0)
			return null;
		int addFood = -armsNum * 10;
		if (role.getFood() < addFood) {
			message.putShort(ErrorCode.NO_GOLD);
		}
		roleService.addRolePopulation(role, (int) armsNum);
		roleService.addRoleFood(role, addFood);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	@Override
	public Message updateUseFormationID(Role role, int formationId) {
		Message message = new Message();
		message.setType(HeroConstant.UPDATE_USEFORMATIONID);
		if (RoleScienceConfigCache.getRoleScienceConfig(1001, role.getRoleScienceMap().get(1001)) == null
				|| formationId > HeroConstant.MAX_FORMATIONID) {
			message.putShort(ErrorCode.NO_FORMATIONID);
			return message;
		}
		// if (Integer.valueOf(role.getLv()) <
		// RoleScienceConfigCache.getRoleScienceConfig(formationId).getOpenLv())
		// {
		// message.putShort(ErrorCode.NO_LV);
		// return message;
		// }
		FormationData formationData = role.getFormationData().get(formationId);

		Map<Byte, Integer> data = formationData.getData();
		for (Map.Entry<Byte, Integer> entry : data.entrySet()) {
			if (entry.getValue() > HeroConstant.RM_HERO) {
				role.setUseFormationID(formationId);
				message.putShort(ErrorCode.SUCCESS);
				return message;
			}
		}
		message.putShort(ErrorCode.FORMATION_NO_HERO);
		return message;
	}

	/**
	 * 处理武将转生收益
	 * 
	 * @author xjd
	 */
	public void updataHeroR(Hero hero, HeroConfig config) {
		switch (hero.getRebirthLv()) {
		case HeroConstant.R_TYPE_0:
			//
			hero.setSkillId(config.getSkillId());
			hero.setSkillLv((hero.getSkillLv() > HeroConstant.INT_0) ? hero.getSkillLv() : HeroConstant.TYPE_1);
			break;
		case HeroConstant.R_TYPE_1:
			//
			hero.setSkillId(config.getSkillId());
			hero.setSkillLv((hero.getSkillLv() > HeroConstant.INT_0) ? hero.getSkillLv() : HeroConstant.TYPE_1);
			break;
		case HeroConstant.R_TYPE_2:
			hero.setSkillId(config.getSkillId());
			hero.setSkillBD(config.getSkillBD());
			hero.setSkillLv((hero.getSkillLv() > HeroConstant.INT_0) ? hero.getSkillLv() : HeroConstant.TYPE_1);
			hero.setSkillBDLv((hero.getSkillLv() > HeroConstant.INT_0) ? hero.getSkillBDLv() : HeroConstant.TYPE_1);
			break;
		case HeroConstant.R_TYPE_3:
			hero.setSkillId(config.getSkillId());
			hero.setSkillBD(config.getSkillBD());
			hero.setSkillLv((hero.getSkillLv() > HeroConstant.INT_0) ? hero.getSkillLv() : HeroConstant.TYPE_1);
			hero.setSkillBDLv((hero.getSkillLv() > HeroConstant.INT_0) ? hero.getSkillBDLv() : HeroConstant.TYPE_1);
			break;
		case HeroConstant.R_TYPE_4:
			hero.setSkillId(config.getSkillId());
			hero.setSkillBD(config.getSkillBD());
			hero.setSkillLv((hero.getSkillLv() > HeroConstant.INT_0) ? hero.getSkillLv() : HeroConstant.TYPE_1);
			hero.setSkillBDLv((hero.getSkillLv() > HeroConstant.INT_0) ? hero.getSkillBDLv() : HeroConstant.TYPE_1);
			break;
		case HeroConstant.R_TYPE_5:
			hero.setSkillBD(config.getSkillBD());
			hero.setSkillId(config.getSkillAY());
			hero.setSkillLv((hero.getSkillLv() > HeroConstant.INT_0) ? hero.getSkillLv() : HeroConstant.TYPE_1);
			hero.setSkillBDLv((hero.getSkillLv() > HeroConstant.INT_0) ? hero.getSkillBDLv() : HeroConstant.TYPE_1);
			break;
		default:
			break;
		}
		hero.setChange(true);
	}

	@Override
	public void addHeroEmotion(Role role, int heroId, int emotionValue) {
		// Hero hero = role.getHeroMap().get(heroId);
		// int currentEmotion = hero.getEmotion();
		// hero.setEmotion(currentEmotion + emotionValue);
		// checkHeroEmotionLvUpdate(role, hero, emotionValue);
		IoSession is = SessionCache.getSessionById(role.getId());
		propService.addProp(role, HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX + heroId, PropConstant.FUNCTION_TYPE_2,
				emotionValue, is);

	}

	/**
	 * 英雄获得经验
	 * 
	 * @author xjd
	 */
	public String addHeroExp(Role role, int exp) {
		StringBuilder sb = new StringBuilder();
		for (int x : role.getFormationData().get(role.getUseFormationID()).getData().values()) {
			if (x > 2) {
				Hero hero = role.getHeroMap().get(x);
				sb.append(this.heroAddExp(hero, role, exp));

			}
		}
		rankService.refreshHeroFightValue(role, Utils.getNowTime(), RankConstant.HERO_FIGHT_VALUE_NUM);
		return sb.toString();
	}

	/**
	 * 检查英雄升级,若升级则升级
	 * 
	 * @param hero
	 * @author wcy
	 */
	private void checkHeroEmotionLvUpdate(Role role, Hero hero, int emotionDelta) {
		int emotion = hero.getEmotion();
		int emotionLv = hero.getEmotionLv();
		boolean isEmotionLvUp = false;
		for (HeroFavourConfig config : HeroFavourConfigCache.getHeroFavourConfigMaps().values()) {
			// TODO
			int favourMin = 0;
			int favourMax = 0;
			if (emotion >= favourMin && emotion < favourMax) {
				int lv = config.getLv();
				if (lv > emotionLv) {
					isEmotionLvUp = true;
					hero.setEmotionLv(lv);
				}
				break;
			}
		}
		sendMessageHeroEmotionAdd(role, hero, isEmotionLvUp, emotionDelta);
	}

	/**
	 * 返回英雄好感度提示
	 * 
	 * @param role
	 * @param hero
	 * @param isEmotionLvUp
	 * @param emotionDelta
	 * @author wcy
	 */
	private void sendMessageHeroEmotionAdd(Role role, Hero hero, boolean isEmotionLvUp, int emotionDelta) {
		HeroFavourConfig heroFavourConfig = HeroFavourConfigCache.getHeroFavourConfig(hero.getEmotionLv());
		IoSession is = SessionCache.getSessionById(role.getId());
		Message message = new Message();
		message.setType(HeroConstant.ADD_HERO_EMOTION);
		message.putInt(hero.getHeroId());
		message.putInt(hero.getEmotionLv());
		// TODO
		int emotion = 0;
		message.putInt(emotion);
		message.putInt(0);
		message.put(isEmotionLvUp ? (byte) 1 : (byte) 0);
		message.putInt(emotionDelta);
		is.write(message);
	}

	@Override
	public Hero createHero(Role role, int heroId) {
		//已有武将转将魂
		if(convertHeroToPropIfExist(role, heroId)){
			Hero hero = role.getHeroMap().get(heroId);
			return hero;
		}
		
		Hero hero = createHeroNotInsertDao(role, heroId);
		if (hero == null) {
//			System.err.print("heroId :" + heroId + " 小鲜肉，你是个大傻逼，没事别哔哔");
			return null;
		}
		// 插入数据库
		heroDao.insertHero(hero);
		// 缓存增加
		role.getHeroMap().put(heroId, hero);
		// 放入英雄可用列表
		// role.getRecruitHeroMap().put(heroId, hero);
		hero.setChange(true);
		
		this.recordRankAndSendNotable(role, Utils.getNowTime());
		return hero;
	}
	
	private void recordRankAndSendNotable(Role role,int nowTime){
		List<Rank> originList = rankService.sortHeroFightValueRank();
		List<Rank> originTopList = rankService.getTopCountList(originList, 1);
		
		rankService.refreshHeroFightValue(role, nowTime, RankConstant.HERO_FIGHT_VALUE_NUM);
		List<Rank> targetList = rankService.sortHeroFightValueRank();
		rankService.ifTopRankChangeThenNotable(RankConstant.RANK_TYPE_FIGHT_VALUE, originTopList, targetList);
	}
	
	/**
	 * 已有武将转将魂
	 * @param role
	 * @param heroId
	 * @return
	 * @author wcy 2016年2月2日
	 */
	private boolean convertHeroToPropIfExist(Role role,int heroId){
		Hero hero = role.getHeroMap().get(heroId);
		if(hero == null){
			return false;
		}
		
		IoSession is = SessionCache.getSessionById(role.getId());
		
		int heroConvertHeroSoul = GeneralNumConstantCache.getValue("HERO_CONVERT_HEROSOUL");			
		int propId = heroId + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX;
		propService.addProp(role, propId, PropConstant.FUNCTION_TYPE_2, heroConvertHeroSoul, is);
		return true;
	}

	public Hero createHeroNotInsertDao(Role role, int heroId) {
		HeroConfig config = HeroConfigCache.getHeroConfigById(heroId);
		if (config == null)
			return null;

		Hero hero = new Hero();
		hero.setRoleId(role.getId());
		hero.setHeroId(heroId);
		hero.setLv(PubConstant.LV_HERO);
		hero.setRebirthLv((short) 0);
		hero.setArmsNum(ProfessionCache.getProfessionById(config.getProfession()).getHp());
		hero.setMaxArmyQuality(1);
		hero.setArmsId(HeroUtils.getArmyIdByHero(config));
		hero.setDutyLv(1);
		hero.setRank(config.getMinR());// MinR现在改为初始的星数
		updataHeroR(hero, config);

		// 加入兵种
		int[] usingArmy = this.getRetrainArmyId(hero);
		hero.getUsingArmySet().clear();
		for (int i = 0; i < usingArmy.length; i++) {
			hero.getUsingArmySet().add(usingArmy[i]);
			// 加入兵种信息
			createHeroSoldier(role, hero, usingArmy[i]);
		}
		hero.getInsteadArmySet().clear();

		// 设置正在使用的兵种
		hero.setUseArmyId(usingArmy[0]);

		// TODO
		hero.setEmotion(0);
		hero.setManual(config.getMaxMa());
		hero.setLastGetMa((int)(System.currentTimeMillis()/1000));
		return hero;
	}

	@Override
	public HeroSoldier createHeroSoldier(Role role, Hero hero, int soldierId) {
		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
		HeroSoldier soldier = new HeroSoldier();
		soldier.setSoldierId(soldierId);
		soldier.setExp(0);
		soldier.setSkillLv(1);
		soldier.setLv(1);
		soldier.setStatus(ArmsConstant.UNLOCK);
		hero.addArmyInfo(soldier);
		this.updataHeroR(hero, config);
		this.initHeroFightValue(role, hero);
		return soldier;
	}

	@Override
	public void initHeroFightValue(Role role, Hero hero) {
		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
		hero.setArmsNum(HeroUtils.getMaxHp(role, hero));		
		hero.setGeneralAttrck(config.getAttack());
		hero.setGeneralDefence(config.getDefence());
		hero.setPowerAttack(config.getZatk());
		hero.setPowerDefence(config.getZdef());
		hero.setMagicalAttack(config.getCatk());
		hero.setMagicalDefence(config.getCdef());
		int atk = HeroConstant.INT_0, def = HeroConstant.INT_0, zatk = HeroConstant.INT_0, zdef = HeroConstant.INT_0, catk = HeroConstant.INT_0, cdef = HeroConstant.INT_0, addHurt = HeroConstant.INT_0, rmHurt = HeroConstant.INT_0, bingji = HeroConstant.INT_0, hp = HeroConstant.INT_0;
		for (int id : hero.getEquipMap().values()) {
			Prop prop = role.getPropMap().get(id);
			if (prop == null)
				continue;
			EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());
			if (equipConfig == null)
				continue;
			EquiptPrefix prefix = EquiptPrefixCache.getEquipConfigById(prop.getPrefixId());
			atk += equipConfig.getAtk() + equipConfig.getAtkStrongValue() * (prop.getLv()-1);
			def += equipConfig.getDef() + equipConfig.getDefStrongValue() * (prop.getLv()-1);
			zatk += equipConfig.getZatk() + equipConfig.getZatkStrongValue() *(prop.getLv()-1);
			zdef += equipConfig.getZdef() + equipConfig.getZdefStrongValue() * (prop.getLv()-1);
			catk += equipConfig.getCatk() + equipConfig.getCatkStrongValue() * (prop.getLv()-1);
			cdef += equipConfig.getCdef() + equipConfig.getCdefStrongValue() * (prop.getLv()-1);
			hp += equipConfig.getHp() + equipConfig.getHpStrongValue() * (prop.getLv()-1);
			if (prefix == null)
				continue;
			addHurt += prefix.getAddHurt();
			rmHurt += prefix.getRmHurt();
			bingji += prefix.getBj();
			hp += prefix.getHp();
		}
		// 最终数值
		hero.setArmsNum(hero.getArmsNum() + hp);
		hero.setGeneralAttrck(hero.getGeneralAttrck() + atk);
		hero.setGeneralDefence(hero.getGeneralDefence() + def);
		hero.setPowerAttack(hero.getPowerAttack() + zatk);
		hero.setPowerDefence(hero.getPowerDefence() + zdef);
		hero.setMagicalAttack(hero.getMagicalAttack() + catk);
		hero.setMagicalDefence(hero.getMagicalDefence() + cdef);
		hero.setAddHurt(addHurt);
		hero.setRmHurt(rmHurt);
		hero.setBingji(bingji);
		

		
//		StringBuilder sb = new StringBuilder();
//		sb.append("hp:");
//		sb.append(hero.getArmsNum());
//		sb.append("普攻");
//		sb.append(hero.getGeneralAttrck());
//		sb.append("普防");
//		sb.append(hero.getGeneralDefence());
//		sb.append("战攻");
//		sb.append(hero.getPowerAttack());
//		sb.append("战防");
//		sb.append(hero.getPowerDefence());
//		sb.append("策攻");
//		sb.append(hero.getMagicalAttack());
//		sb.append("策防");
//		sb.append(hero.getMagicalDefence());
//		System.out.println("========================="+sb.toString()+"==========================");
	}

	/**
	 * 更新战力 等级 提升 计算等级差1时的提升值
	 * 
	 * @author xjd
	 */
	private void changeHeroFightValue(Hero hero, byte type) {
		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
		Profession x = ProfessionCache.getProfessionById(config.getProfession());
		// int hpOld = x.getHp() + hero.getLv() * x.getHpGrow()
		// , hpNew = HeroConstant.INT_0;
		//
		// int atkOld = HeroConstant.INT_0, defOld = HeroConstant.INT_0, zatkOld
		// = HeroConstant.INT_0, zdefOld = HeroConstant.INT_0,
		// catkOld = HeroConstant.INT_0, cdefOld = HeroConstant.INT_0;
		// short oldLv = (short)(hero.getLv()-HeroConstant.BYTE_1);
		// atkOld = x.getAttack() + HeroUtils.getGrowValue(x.getAttackGrow(),
		// oldLv);
		// defOld = x.getDefence() + HeroUtils.getGrowValue(x.getDefenceGrow(),
		// oldLv);
		// zatkOld = x.getKongfuAtk() +
		// HeroUtils.getGrowValue(x.getKongfuAtkGrow(), oldLv);
		// zdefOld = x.getKongfuDef() +
		// HeroUtils.getGrowValue(x.getKongfuDefGrow(),oldLv);
		// catkOld = x.getMagicAtk() +
		// HeroUtils.getGrowValue(x.getMagicAtkGrow(), oldLv);
		// cdefOld = x.getMagicDef() +
		// HeroUtils.getGrowValue(x.getMagicDefGrow(), oldLv);
		//
		// int atkNew = HeroConstant.INT_0, defNew = HeroConstant.INT_0, zatkNew
		// = HeroConstant.INT_0, zdefNew = HeroConstant.INT_0,
		// catkNew = HeroConstant.INT_0, cdefNew = HeroConstant.INT_0;
		// hpNew = x.getHp() + hero.getLv() * x.getHpGrow();
		// atkNew = x.getAttack() + HeroUtils.getGrowValue(x.getAttackGrow(),
		// hero.getLv());
		// defNew = x.getDefence() + HeroUtils.getGrowValue(x.getDefenceGrow(),
		// hero.getLv());
		// zatkNew = xNew.getPowerAttack() + xxNew.getZatk();
		// zdefNew = xNew.getPowerDefence() + xxNew.getZdef();
		// catkNew = xNew.getMagicalAttack() + xxNew.getCatk();
		// cdefNew = xNew.getMagicalDefence() + xxNew.getCdef();
		// 最终数值
		hero.setArmsNum(hero.getArmsNum() + x.getHpGrow());
		hero.setGeneralAttrck(hero.getGeneralAttrck() + x.getAttackGrow());
		hero.setGeneralDefence(hero.getGeneralDefence() + x.getDefenceGrow());
		hero.setPowerAttack(hero.getPowerAttack() + x.getKongfuAtkGrow());
		hero.setPowerDefence(hero.getPowerDefence() + x.getKongfuDefGrow());
		hero.setMagicalAttack(hero.getMagicalAttack() + x.getMagicAtkGrow());
		hero.setMagicalDefence(hero.getMagicalDefence() + x.getMagicDefGrow());
	}

	@Override
	public Message retrainArmy(Role role, int heroId) {
		Message message = new Message();
		message.setType(HeroConstant.RETREAIN_ARMY);

		Hero hero = role.getHeroMap().get(heroId);

		// 检查一阶不可重修
		if (hero.getDutyLv() <= HeroConstant.NO_RETRAIN_LV) {
			message.putShort(ErrorCode.NO_RETRAIN_LV);
			return message;
		}

		int useGold = this.getRetrainNeedGold(role);

		// 检查金币是否足够
		if (role.getGold() < useGold) {
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}

		if (useGold != 0) {
			roleService.addRoleGold(role, -useGold);
		}

		role.setReTrainTimes(role.getReTrainTimes() + 1);

		int[] soldierIdArray = getRetrainArmyId(hero);

		HashSet<Integer> insteadArmySet = hero.getInsteadArmySet();
		insteadArmySet.clear();
		for (int i = 0; i < soldierIdArray.length; i++) {
			int soldierId = soldierIdArray[i];
			// 加入到替换列表
			insteadArmySet.add(soldierId);
		}
		hero.setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		for (Integer soldierId : insteadArmySet) {
			message.putInt(soldierId);
		}
		message.putInt(useGold);

		return message;
	}

	/**
	 * 重修得出的替换士兵
	 * 
	 * @param hero
	 * @return
	 * @author wcy
	 */
	private int[] getRetrainArmyId(Hero hero) {
		int dutyLv = hero.getDutyLv();
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(hero.getHeroId());

		int profession = heroConfig.getProfession();
		Profession professionConfig = ProfessionCache.getProfessionById(profession);
		int armsType1 = professionConfig.getArmy1();
		int armsType2 = professionConfig.getArmy2();

		ArrayList<ArmsConfig> array1 = ArmsConfigCache.getArmsTypeQualityMapping().get(armsType1).get(dutyLv);
		ArrayList<ArmsConfig> array2 = ArmsConfigCache.getArmsTypeQualityMapping().get(armsType2).get(dutyLv);

		Random rand = new Random();
		int[] soldierIdArray = { 0, 0 };// 只是为了返回消息用

		int size = array1.size();
		int value = rand.nextInt(size);
		int soldierId = array1.get(value).getId();
		soldierIdArray[0] = soldierId;

		size = array2.size();
		value = rand.nextInt(size);
		soldierId = array2.get(value).getId();
		soldierIdArray[1] = soldierId;

		hero.setChange(true);

		return soldierIdArray;
	}

	@Override
	public Message upDutyLv(Role role, int heroId) {
		Message message = new Message();
		message.setType(HeroConstant.UP_DUTY_LV);

		Hero hero = role.getHeroMap().get(heroId);
		// 检查是否最大转职数
		int currentDutyLv = hero.getDutyLv();
		if (currentDutyLv >= HeroConstant.MAX_DUTY_LV) {
			message.putShort(ErrorCode.MAX_DUTY_LV);
			return message;
		}

//		 //武将转职有兵营等级限制
//		Short campLv = role.getBuild().getBuildLvMap().get(InComeConstant.TYPE_CAMP);
//		
//		if(campLv == null){
//			message.putShort(ErrorCode.BUILD_NO_LOCK);
//			return message;
//		}
//		Short needCampLv = this.heroUpDutyCampLvMap.get(hero.getRank() + 1);
//		if (needCampLv != null) {
//			if (campLv < needCampLv) {
//				message.putShort(ErrorCode.NO_CAMP_LV);
//				return message;
//			}
//		}

		// 检查等级
		int currentLv = hero.getLv();
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(hero.getHeroId());
		int professionId = heroConfig.getProfession();

		ZhuanzhiConfig zhuanzhiConfig = ZhuanzhiConfigCache.getZhuanzhiConfig(professionId, currentDutyLv + 1);
		int needLv = zhuanzhiConfig.getNeedLv();
		if (currentLv < needLv) {
			message.putShort(ErrorCode.NO_LV_HERO);
			return message;
		}

		// 检查印绶数量
		Prop dutyProp = role.getConsumePropMap().get(HeroConstant.DUTY_PROP);
		int needDutyPropNum = zhuanzhiConfig.getCost();
		if (dutyProp == null || (int) dutyProp.getNum() < needDutyPropNum) {
			message.putShort(ErrorCode.NO_PROP);
			return message;
		}

		// 可以转职
		hero.setDutyLv(currentDutyLv + 1);
		dutyProp.setNum((short) (dutyProp.getNum() - needDutyPropNum));
		hero.setMaxArmyQuality(hero.getMaxArmyQuality() + 1);

		int[] armsId = getRetrainArmyId(hero);
		hero.getInsteadArmySet().clear();
		hero.getUsingArmySet().clear();
		for (int i = 0; i < armsId.length; i++) {
			int soldierId = armsId[i];
			hero.getUsingArmySet().add(soldierId);
			if (hero.getArmyInfoMap().get(soldierId) == null) {
				createHeroSoldier(role, hero, soldierId);
			}
		}

		message.putShort(ErrorCode.SUCCESS);
		for (Integer soldierId : hero.getUsingArmySet()) {
			message.putInt(soldierId);
		}

		hero.setUseArmyId(armsId[0]);
		hero.setChange(true);

		message.putInt(needDutyPropNum);
		message.putInt(hero.getDutyLv());
		message.putInt(hero.getUseArmyId());

		if(chatService.checkSystemChat(ChatConstant.MESSAGE_TYPE_DUTY, hero.getDutyLv())){
			StringBuilder sb = this.getHeroDutyStringBuilder(role, hero, heroConfig);
			chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_DUTY, sb.toString());
		}
		this.recordRankAndSendNotable(role, Utils.getNowTime());
		return message;
	}


	private StringBuilder getHeroDutyStringBuilder(Role role, Hero hero, HeroConfig heroConfig) {
		String heroName = heroConfig.getName();
		StringBuilder sb = new StringBuilder();
		sb.append(role.getId()).append(",");
		sb.append(role.getName()).append(",");
		sb.append(hero.getDutyLv()).append(",");
		sb.append(heroName).append(",");
		sb.append(Utils.getNationName(role.getNation())).append(",");
		sb.append(heroConfig.getQuality()).append(",");
		sb.append(hero.getHeroId()).append(",");
		return sb;
	}

	@Override
	public Message upRankLv(Role role, int heroId) {
		Message message = new Message();
		message.setType(HeroConstant.UP_STAR);

		Hero hero = role.getHeroMap().get(heroId);
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(hero.getHeroId());
		int currentStar = hero.getRank();
		int totalStar = heroConfig.getMaxR();// 满星数

		int formerCaptain = HeroUtils.getCaptainValue(hero);
		int formerPower = HeroUtils.getPowerValue(hero);
		int formerIntel = HeroUtils.getIntelValue(hero);

		// 检查满星
		if (currentStar >= totalStar) {
			message.putShort(ErrorCode.MAX_STAR_LV);
			return message;
		}

		// 获得升级需要的将魂
		HeroFavourConfig heroFavourConfig = HeroFavourConfigCache.getHeroFavourConfig(currentStar + 1);
		int needHeroPropNum = heroFavourConfig.getCost();

		Prop heroProp = role.getConsumePropMap().get(heroId + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX);
		if (heroProp == null || heroProp.getNum() == 0) {
			message.putShort(ErrorCode.NO_PROP);
			return message;
		}

		// 将魂不足
		if (heroProp.getNum() < needHeroPropNum) {
			message.putShort(ErrorCode.ERR_NUMBER_SELL);
			return message;
		}

		// 可以升星
		heroProp.setNum((short) (heroProp.getNum() - needHeroPropNum));
		hero.setRank(hero.getRank() + 1);

		hero.setChange(true);
		int nextStar = hero.getRank() + 1;
		heroFavourConfig = HeroFavourConfigCache.getHeroFavourConfig(nextStar);
		int nextStarNeedHeroStarPropNum = nextStar >= totalStar ? 0 : heroFavourConfig.getCost();

		int afterCaptain = HeroUtils.getCaptainValue(hero);
		int afterPower = HeroUtils.getPowerValue(hero);
		int afterIntel = HeroUtils.getIntelValue(hero);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(needHeroPropNum);
		message.putInt(hero.getRank());
		message.putInt(formerCaptain);
		message.putInt(formerPower);
		message.putInt(formerIntel);
		message.putInt(afterCaptain);
		message.putInt(afterPower);
		message.putInt(afterIntel);
		message.putInt(nextStarNeedHeroStarPropNum);
		
		
		if (chatService.checkSystemChat(ChatConstant.MESSAGE_TYPE_RANK, hero.getRank())) {
			StringBuilder sb = this.getHeroRankStringBuilder(role, hero, heroConfig);
			chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_RANK, sb.toString());
		}
		
		this.recordRankAndSendNotable(role, Utils.getNowTime());
		
		return message;
	}


	private StringBuilder getHeroRankStringBuilder(Role role, Hero hero, HeroConfig heroConfig) {
		String heroName = heroConfig.getName();
		StringBuilder sb = new StringBuilder();
		sb.append(role.getId()).append(",");
		sb.append(role.getName()).append(",");
		sb.append(hero.getRank()).append(",");
		sb.append(heroName).append(",");
		sb.append(Utils.getNationName(role.getNation())).append(",");
		sb.append(heroConfig.getQuality()).append(",");
		sb.append(hero.getHeroId()).append(",");
		return sb;
	}

	@Override
	public Message insteadArmy(Role role, int heroId) {
		Message message = new Message();
		message.setType(HeroConstant.INSTEAD_ARMY);

		Hero hero = role.getHeroMap().get(heroId);
		HashSet<Integer> usingSet = hero.getUsingArmySet();
		HashSet<Integer> insteadSet = hero.getInsteadArmySet();

		boolean canInstead = false;
		for (Integer soldier : insteadSet) {
			if (soldier != 0) {
				canInstead = true;
			}
		}

		if (!canInstead) {
			message.putShort(ErrorCode.NO_INSTEAD_ARMY);
			return message;
		}

		message.putShort(ErrorCode.SUCCESS);
		usingSet.clear();
		usingSet.addAll(insteadSet);		
		insteadSet.clear();
		
		//检查替换兵种是否没有创建，没有创建则创建
		for(Integer soldierId:usingSet){
			if(!hero.getArmyInfoMap().containsKey(soldierId)){
				this.createHeroSoldier(role, hero, soldierId);
			}
		}

		int useArmyId = 0;
		for (Integer soldierId : usingSet) {
			if (useArmyId == 0 && soldierId != 0)
				useArmyId = soldierId;
			message.putInt(soldierId);
		}

		// 设置现在正在使用的兵种
		hero.setUseArmyId(useArmyId);

		hero.setChange(true);
		message.putInt(useArmyId);

		return message;
	}

	@Override
	public Message showDuty(Role role, int heroId) {
		Message message = new Message();
		message.setType(HeroConstant.SHOW_DUTY);

		Hero hero = role.getHeroMap().get(heroId);
		if(hero == null)
		{
			HeroConfig config = HeroConfigCache.getHeroConfigById(heroId);
			if(config == null) return null;
			Profession profession = ProfessionCache.getProfessionById(config.getProfession());
			message.putInt(HeroConstant.ARMY_ID[profession.getArmy1()]);
			message.putInt(HeroConstant.ARMY_ID[profession.getArmy2()]);
			message.putInt(HeroConstant.NO_ARMY_ID);
		}else {
			HashSet<Integer> usingArmySet = hero.getUsingArmySet();
			for (Integer usingArmy : usingArmySet) {
				message.putInt(usingArmy);
			}
			message.putInt(hero.getUseArmyId());
		}
		return message;
	}

	@Override
	public Message showRank(Role role, int heroId) {
		Message message = new Message(HeroConstant.SHOW_RANK);
		Hero hero = role.getHeroMap().get(heroId);

		int fullRank = HeroConfigCache.getHeroConfigById(heroId).getMaxR();

		byte flag = HeroConstant.BYTE_1;
		int currentRank = hero.getRank();
		int newCaptain = 0;
		int newIntel = 0;
		int newPower = 0;

		// 检查星满
		if (currentRank >= fullRank) {
			flag = HeroConstant.BYTE_2;
		} else {
			// 检查道具数量
			HeroFavourConfig heroFavourConfig = HeroFavourConfigCache.getHeroFavourConfig(currentRank + 1);
			int needRankProp = heroFavourConfig.getCost();
			Prop rankProp = role.getConsumePropMap().get(HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX + heroId);
			flag = rankProp != null && rankProp.getNum() >= needRankProp ? HeroConstant.BYTE_1 : HeroConstant.BYTE_0;
			
			short rebirthLv = hero.getRebirthLv();
			short heroLv = hero.getLv();
			
			newCaptain = HeroUtils.getSanWei(HeroAttrType.captain, currentRank + 1, rebirthLv, heroLv, heroId);
			newIntel = HeroUtils.getSanWei(HeroAttrType.intel, currentRank + 1, rebirthLv, heroLv, heroId);
			newPower = HeroUtils.getSanWei(HeroAttrType.power, currentRank + 1, rebirthLv, heroLv, heroId);
		}

		message.put(flag);
		message.putInt(newCaptain);
		message.putInt(newIntel);
		message.putInt(newPower);

		return message;
	}

	@Override
	public Message showHeroDetail(Role role, int heroId) {
		Message message = new Message(HeroConstant.SHOW_HERO_DETAIL);

		Hero hero = role.getHeroMap().get(heroId);
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(heroId);
		int profession = heroConfig.getProfession();
		Profession professionConfig = ProfessionCache.getProfessionById(profession);
		int captain = HeroUtils.getCaptainValue(hero);
		int intel = HeroUtils.getIntelValue(hero);
		int power = HeroUtils.getPowerValue(hero);
		double captainGrow = heroConfig.getCaptainGrowValue();
		double intelGrow = heroConfig.getIntelGrowValue();
		double powerGrow = heroConfig.getPowerGrowValue();
		int lv = hero.getLv();
		int nowRebirthLv = hero.getRebirthLv();
		int armsNum = hero.getArmsNum();
		int fightValue = hero.getFightValue();

		ZhuanshengConfig zhuanshengConfig = ZhuanshengConfigCache.getZhuanshengConfig(nowRebirthLv + 1);
		int needLvToRebirth = zhuanshengConfig.getNeedLv();
		int addArmsNum = professionConfig.getHpGrow();
		String name = professionConfig.getName();
		int armsType1 = professionConfig.getArmy1();
		int armsType2 = professionConfig.getArmy2();
		int heroSkillId = heroConfig.getSkillId();
		int dutyLv = hero.getDutyLv();

		message.putInt(armsNum);
		message.putInt(fightValue);
		message.putInt(captain);
		message.putInt(intel);
		message.putInt(power);
		message.putDouble(captainGrow);
		message.putDouble(intelGrow);
		message.putDouble(powerGrow);
		message.putInt(lv);
		message.putInt(nowRebirthLv);
		message.putInt(needLvToRebirth);
		message.putInt(addArmsNum);
		message.putString(name);
		message.putInt(heroSkillId);
		message.putInt(armsType1);
		message.putInt(armsType2);
		message.putInt(dutyLv);

		return message;
	}

	@Override
	public Message showRetrain(Role role) {
		Message message = new Message(HeroConstant.SHOW_RETRAIN);
		int useGold = this.getRetrainNeedGold(role);
		message.putInt(useGold);
		return message;
	}

	/**
	 * 获得重修所需金币
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	private int getRetrainNeedGold(Role role) {
		int retrainTimes = role.getReTrainTimes();
		int useGold = 0;
		if (retrainTimes >= RoleConstant.FREE_RETRAIN) {
			// 不是免费
			int len = RoleConstant.RETRAIN_USE_GOLD_ARRAY.length;
			int index = retrainTimes >= len ? len - 1 : retrainTimes;
			useGold = RoleConstant.RETRAIN_USE_GOLD_ARRAY[index];
		}
		return useGold;
	}

	/**
	 * 检查进阶
	 * 
	 * @param hero
	 * @return
	 * @author wcy
	 */
	private byte checkUpRank(Role role, Hero hero) {
		int heroId = hero.getHeroId();
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(hero.getHeroId());
		int currentStar = hero.getRank();
		int totalStar = heroConfig.getMaxR();// 满星数

		// 检查满星
		if (currentStar >= totalStar) {
			return 0;
		}

		// 获得升级需要的将魂
		HeroFavourConfig heroFavourConfig = HeroFavourConfigCache.getHeroFavourConfig(currentStar + 1);
		int needHeroPropNum = heroFavourConfig.getCost();

		Prop heroProp = role.getConsumePropMap().get(heroId + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX);
		if (heroProp == null || heroProp.getNum() == 0) {
			return 0;
		}

		// 将魂不足
		if (heroProp.getNum() < needHeroPropNum) {
			return 0;
		}
		return 1;
	}

	/**
	 * 检查升级
	 * 
	 * @param hero
	 * @return
	 * @author wcy
	 * @param role
	 */
	private byte checkUpLv(Role role, Hero hero) {
		int roleLv = role.getLv();
		int heroLv = hero.getLv();
		if (heroLv >= roleLv) {
			return 0;
		}
		return 1;
	}

	/**
	 * 检查转职
	 * 
	 * @param hero
	 * @return
	 * @author wcy
	 */
	private byte checkUpDuty(Role role, Hero hero) {
		Message message = new Message();
		message.setType(HeroConstant.UP_DUTY_LV);

		// 检查是否最大转职数
		int currentDutyLv = hero.getDutyLv();
		if (currentDutyLv >= HeroConstant.MAX_DUTY_LV) {
			return 0;
		}

		// 检查等级
		int currentLv = hero.getLv();
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(hero.getHeroId());
		int professionId = heroConfig.getProfession();

		ZhuanzhiConfig zhuanzhiConfig = ZhuanzhiConfigCache.getZhuanzhiConfig(professionId, currentDutyLv + 1);
		int needLv = zhuanzhiConfig.getNeedLv();
		if (currentLv < needLv) {
			return 0;
		}

		// 检查印绶数量
		Prop dutyProp = role.getConsumePropMap().get(HeroConstant.DUTY_PROP);
		int needDutyPropNum = zhuanzhiConfig.getCost();
		if (dutyProp == null || (int) dutyProp.getNum() < needDutyPropNum) {
			return 0;
		}
		return 1;
	}

	/**
	 * 检查转生
	 * 
	 * @param hero
	 * @return
	 * @author wcy
	 * @param role
	 */
	private byte checkUpRebirth(Role role, Hero hero) {
		// 当前进阶等级
		short nowRebirthLv = hero.getRebirthLv();

		// 检查最大转生
		if (nowRebirthLv >= HeroConstant.MAX_REBIRTH_LV) {
			return 0;
		}

		// 检查转生所需等级
		ZhuanshengConfig zhuanshengConfig = ZhuanshengConfigCache.getZhuanshengConfig(nowRebirthLv + 1);
		int heroLv = hero.getLv();
		int needLv = zhuanshengConfig.getNeedLv();
		if (heroLv < needLv) {
			return 0;
		}
		return 1;
	}

	@Override
	public Message compoundHero(Role role, int heroId, IoSession is) {
		Message message = new Message(HeroConstant.COMPOUND_HERO);
		Hero hero = role.getHeroMap().get(heroId);
		if (hero != null) {
			message.putShort(ErrorCode.HERO_AREADY_HAS);
			return message;
		}
		int heroPropId = HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX + heroId;
		Prop heroProp = role.getConsumePropMap().get(heroPropId);
		short heroPropNum = heroProp.getNum();
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(heroId);
		short needPropNum = (short) HeroFavourConfigCache.getHeroFavourConfig(heroConfig.getMinR()).getCost();

		if (heroPropNum < needPropNum) {
			message.putShort(ErrorCode.ERR_NUMBER_SELL);
			return message;
		}

		message.putShort(ErrorCode.SUCCESS);
		hero = createHero(role, heroId);

		propService.addProp(role, heroProp.getItemId(), PropConstant.FUNCTION_TYPE_2, -needPropNum, is);

		role.getPub().getHeroGoldSet().remove(heroId);
		role.getPub().getHeroMoneySet().remove(heroId);
		message.putShort(heroProp.getNum());
		
		this.addHero(is, hero);

		return message;
	}

	@Override
	public void initHeroService() {
		heroUpDutyCampLvMap.put(2, (short) GeneralNumConstantCache.getValue("HERO_UP_DUTY_1_NEED_CAMP_LV"));
		heroUpDutyCampLvMap.put(3, (short) GeneralNumConstantCache.getValue("HERO_UP_DUTY_2_NEED_CAMP_LV"));
	}

	
	private void getManualOff(Hero hero)
	{
		int nowTime = Utils.getNowTime();
		int num = (nowTime - hero.getLastGetMa()) / 1800;
		if(num > 0)
		{
			hero.addManual(num);
			hero.setLastGetMa(nowTime);
		}
	}


	@Override
	public Message showBuyManual(Role role, int heroId) {
		Message message = new Message();
		message.setType(HeroConstant.SHOW_GOLD_TO_MANUAL);
		
		Hero hero = role.getHeroMap().get(heroId);
		if(hero == null)
			return null;
		
		int currentManual = hero.getManual();
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(heroId);
		int maxManual = heroConfig.getMaxMa();
		int deltaManual = maxManual - currentManual;
		byte isFull = 0;
		if(deltaManual<=0){
			isFull = 1;
		}
		
		int needGold = manualNeedGold(deltaManual);
		message.put(isFull);
		message.putInt(needGold);
			
		return message;
	}


	@Override
	public Message buyManual(Role role, int heroId) {
		Message message = new Message();
		message.setType(HeroConstant.GOLD_TO_MANUAL);
		Hero hero = role.getHeroMap().get(heroId);
		if(hero == null)
			return null;
		
		int currentManual = hero.getManual();
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(heroId);
		int maxManual = heroConfig.getMaxMa();
		int deltaManual = maxManual - currentManual;
		if (deltaManual <= 0) {
			message.putShort(ErrorCode.HERO_FULL_MANUAL);
			return message;
		}

		int needGold = manualNeedGold(deltaManual);
		
		if(role.getGold()<needGold){
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		message.putShort(ErrorCode.SUCCESS);
		roleService.addRoleGold(role, -needGold);
		hero.setManual(maxManual);
		
		role.setChange(true);
		hero.setChange(true);
		
		return message;
	}
	
	/**
	 * 体力需要的金币
	 * @param deltaManual
	 * @return
	 * @author wcy
	 */
	private int manualNeedGold(int deltaManual){
		int value = GeneralNumConstantCache.getValue("ONE_MANUAL_EQUALS_GOLD");
		int useGold = deltaManual*value;
		return useGold;
	}
	
}
