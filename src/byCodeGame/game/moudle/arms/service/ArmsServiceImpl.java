package byCodeGame.game.moudle.arms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import byCodeGame.game.cache.file.ArmsConfigCache;
import byCodeGame.game.cache.file.ArmsResearchConfigCache;
import byCodeGame.game.cache.file.ArmyLvConfigCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.ItemCache;
import byCodeGame.game.cache.file.RoleArmyConfigCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.RoleArmy;
import byCodeGame.game.entity.file.ArmsConfig;
import byCodeGame.game.entity.file.ArmsResearchConfig;
import byCodeGame.game.entity.file.ArmyLvConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.HeroLvConfig;
import byCodeGame.game.entity.file.Item;
import byCodeGame.game.entity.file.RoleArmyConfig;
import byCodeGame.game.entity.po.ArmyData;
import byCodeGame.game.entity.po.HeroSoldier;
import byCodeGame.game.moudle.arms.ArmsConstant;
import byCodeGame.game.moudle.hero.HeroConstant;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.remote.Message;

public class ArmsServiceImpl implements ArmsService {

	private static final Logger armsLog = LoggerFactory.getLogger("arms");
	/** 经验分配表HashMap<兵符id,HashMap<兵品阶,经验值>> **/
	private static HashMap<Integer, HashMap<Integer, Integer>> expMap = new HashMap<Integer, HashMap<Integer, Integer>>();
	/** HashMap<印绶品阶号,道具id> **/
	private static HashMap<Integer, Integer> qualityPropMap = new HashMap<Integer, Integer>();

	private RoleService roleService;
	private HeroService heroService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}

	public ArmsServiceImpl() {
		HashMap<Integer, Integer> one = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> two = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> three = new HashMap<Integer, Integer>();

		Item expProp1 = ItemCache.getItemById(ArmsConstant.HERO_SOLDIER_EXP1_PROP_ID);
		Item expProp2 = ItemCache.getItemById(ArmsConstant.HERO_SOLDIER_EXP2_PROP_ID);
		Item expProp3 = ItemCache.getItemById(ArmsConstant.HERO_SOLDIER_EXP3_PROP_ID);

		int expProp1Value = expProp1 != null ? expProp1.getValue() : 0;
		int expProp2Value = expProp2 != null ? expProp2.getValue() : 0;
		int expProp3Value = expProp3 != null ? expProp3.getValue() : 0;

		one.put(ArmsConstant.QUALITY_1, (int) (expProp1Value * ArmsConstant.percent100));
		one.put(ArmsConstant.QUALITY_2, (int) (expProp1Value * ArmsConstant.percent50));
		one.put(ArmsConstant.QUALITY_3, (int) (expProp1Value * ArmsConstant.percent20));
		two.put(ArmsConstant.QUALITY_2, (int) (expProp2Value * ArmsConstant.percent100));
		two.put(ArmsConstant.QUALITY_3, (int) (expProp2Value * ArmsConstant.percent50));
		three.put(ArmsConstant.QUALITY_3, (int) (expProp3Value * ArmsConstant.percent100));
		expMap.put(ArmsConstant.HERO_SOLDIER_EXP1_PROP_ID, one);
		expMap.put(ArmsConstant.HERO_SOLDIER_EXP2_PROP_ID, two);
		expMap.put(ArmsConstant.HERO_SOLDIER_EXP3_PROP_ID, three);

		qualityPropMap.put(ArmsConstant.QUALITY_1, ArmsConstant.SMALL_HERO_SOLDIER_QUALITY_PROP_ID);
		qualityPropMap.put(ArmsConstant.QUALITY_2, ArmsConstant.BIG_HERO_SOLDIER_QUALITY_PROP_ID);
	}

	@Override
	public Message getArmsByType(Role role, int type) {
		Message message = new Message();
		message.setType(ArmsConstant.GET_ARMS_RESEARCH);
		Map<Integer, ArmsResearchConfig> armsResearchConfig = ArmsResearchConfigCache.getArmsResearchConfigMap();
		message.putInt(8);// 当前版本固定8个
		byte three = 1;
		for (Map.Entry<Integer, ArmsResearchConfig> map : armsResearchConfig.entrySet()) {
			three = 1;
			ArmsResearchConfig armsResearch = map.getValue();
			if (armsResearch.getFunctionType() == type) {
				if (armsResearch.getArmyTypeLv() == 2) {
					if (role.getArmsResearchData().get(armsResearch.getId()) != armsResearch.getNeedNum()) {
						three = 0;
					}
				}
				message.putInt(armsResearch.getId());
				message.putInt(armsResearch.getArmyTypeLv());
				message.putInt(role.getArmsResearchData().get(armsResearch.getId()));
				message.putInt(armsResearch.getNeedNum());
			}
		}
		/** 三阶兵是否点亮 */
		message.put(three);
		message.putInt(role.getArmsResearchNum());
		return message;
	}

	@Override
	public Message addArmsResearchLv(Role role, int armsId) {
		Message message = new Message();
		message.setType(ArmsConstant.ADD_ARMS_RESEARCH_LV);
		int armsIdLv = 0;
		if (null == role.getArmsResearchData()) {
			return null;
		}
		armsIdLv = role.getArmsResearchData().get(armsId);
		int needLv = ArmsResearchConfigCache.getArmsResearchConfigById(armsId).getNeedNum();
		int armsTypeLv = ArmsResearchConfigCache.getArmsResearchConfigById(armsId).getArmyTypeLv();
		int functionType = ArmsResearchConfigCache.getArmsResearchConfigById(armsId).getFunctionType();
		if (armsTypeLv == 3) {// 当升级为3阶兵种时
			for (Map.Entry<Integer, ArmsResearchConfig> map : ArmsResearchConfigCache.getArmsResearchConfigMap()
					.entrySet()) {
				if (map.getValue().getArmyTypeLv() == 2 && functionType == map.getValue().getFunctionType()) {// 该类型的2阶兵种必须全部研发完毕
					if (map.getValue().getNeedNum() > role.getArmsResearchData().get(map.getKey())) {
						message.putShort(ErrorCode.NOT_ARMSRESEARCH_MAX);
						return message;
					}
				}
			}
		}

		if (armsIdLv < needLv) {// 当前兵种科技等级未满
			if (role.getArmsResearchNum() > 0) {// 玩家科技点数>0
				role.setArmsResearchNum(role.getArmsResearchNum() - 1);
				role.getArmsResearchData().put(armsId, armsIdLv + 1);
				message.putShort(ErrorCode.SUCCESS);
			} else {
				message.putShort(ErrorCode.NO_ARMSRESEARCH_NUM);
			}
		} else {
			message.putShort(ErrorCode.MAX_ARMS);
		}
		return message;
	}

	/**
	 * 升级天赋
	 * 
	 * @author xjd
	 */
	public Message upRoleArmy(Role role, int id) {
		Message message = new Message();
		message.setType(ArmsConstant.UP_ROLE_ARMY);
		// 判断天赋是否合法
		RoleArmyConfig config = RoleArmyConfigCache.getRoleArmyConfigById(id);
		if (config == null)
			return null;
		// 判断是否达到前置要求
		if (role.getRoleArmy().getOneGiftPoint(config.getType()) < config.getNeedPoint()) {
			message.putShort(ErrorCode.ERR_UP_ROLE_ARMY);
			return message;
		}
		// 判断天赋点是否足够
		int temp = role.getChapter().getStarMap().size() * ArmsConstant.NUM_CHAPTER;
		// 检查是否已经升级过天赋
		ArmyData armyData = role.getRoleArmy().getArmyData(config.getType(), id);
		int need = config.getPoint();
		if (armyData != null) {
			if (armyData.getLv() >= config.getMaxLv()) {
				message.putShort(ErrorCode.MAX_LV_ROLE_ARMY);
				return message;
			}
		}
		if (temp - role.getRoleArmy().getUsedPoint() < need) {
			message.putShort(ErrorCode.NO_ROLE_POINT);
			return message;
		}
		// 判断同等级天赋是否已经存在
		boolean flag = this.checkRoleArmy(role, config);
		if (!flag) {
			message.putShort(ErrorCode.ALREADY_HAS_ROLE_ARMY);
			return message;
		}
		// 升级成功
		role.getRoleArmy().setUsedPoint(role.getRoleArmy().getUsedPoint() + need);

		if (armyData == null) {
			armyData = new ArmyData();
			armyData.setId(config.getId());
			armyData.setLv(ArmsConstant.FIRST_UP);
			armyData.setPoint(config.getPoint());
			armyData.setRank(config.getRank());
			armyData.setType(config.getType());
			role.getRoleArmy().putArmyData(armyData);
		} else {
			armyData.setLv(armyData.getLv() + ArmsConstant.FIRST_UP);
			armyData.setPoint(armyData.getPoint() + config.getPoint());
		}
		role.getRoleArmy().setChange(true);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 重置天赋
	 * 
	 * @author xjd
	 */
	public Message reSetRoleArmy(Role role) {
		Message message = new Message();
		message.setType(ArmsConstant.RE_SET_ROLE_ARMY);
		// 判断玩家元宝是否足够
		if (role.getGold() < ArmsConstant.RE_SET_NEED) {
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		// 重置成功
		roleService.addRoleGold(role, -ArmsConstant.RE_SET_NEED);
		role.getRoleArmy().clearRoleArmy();
		role.getRoleArmy().setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 获取天赋信息
	 * 
	 * @author xjd
	 */
	public Message getRoleArmyInfo(Role role, int type) {
		Message message = new Message();
		message.setType(ArmsConstant.GET_ROLE_ARMY_INFO);
		RoleArmy roleArmy = role.getRoleArmy();
		Map<Integer, RoleArmyConfig> map = RoleArmyConfigCache.getMapInfo(type);
		if (map == null)
			return null;
		message.putInt(map.size());
		for (RoleArmyConfig x : map.values()) {
			message.putInt(x.getId());
			message.putInt(x.getRank());
			ArmyData tempData = roleArmy.getArmyData(type, x.getId());
			if (tempData == null) {
				message.putInt(ArmsConstant.NUMBER_0);
			} else {
				message.putInt(tempData.getLv());
			}
			if (roleArmy.getUsedPoint() < x.getNeedPoint()) {
				message.put(ArmsConstant.CAN_NOT_UP);
			} else {
				message.put(ArmsConstant.CAN_UP);
			}
		}
		int temp = role.getChapter().getStarMap().size() * ArmsConstant.NUM_CHAPTER;
		message.putInt(temp - roleArmy.getUsedPoint());
		return message;
	}

	@Override
	public Message useSoldierTally(Role role, int heroId, int heroSoldierId, int propId) {
		Message message = new Message(ArmsConstant.UPDATE_HERO_SOLDIER_EXP);
		Hero hero = role.getHeroMap().get(heroId);
		HeroSoldier soldier = hero.getArmyInfoMap().get(heroSoldierId);
		// 如果兵种不可用
		if (soldier.getStatus() == ArmsConstant.CAN_NOT_USE) {
			message.putShort(ErrorCode.ARMS_SOLDIER_CAN_NOT_USE);
			return message;
		}
		// 如果兵种没有解锁
		if (soldier.getStatus() == ArmsConstant.NOT_UNLOCK) {
			message.putShort(ErrorCode.ARMS_NOT_UNLOCK);
			return message;
		}

		// 检查兵种等级是否超过武将等级
		if (soldier.getLv() > hero.getLv()
				|| soldier.getExp() >= ArmyLvConfigCache.getArmyLvConfig(hero.getLv()).getNextExp() - 1) {
			message.putShort(ErrorCode.ARMS_SOLDIERLV_BIGGER_THAN_HEROLV);
			return message;
		}

		// 检查是否有该品阶的兵符
		Prop prop = role.getPropMap().get(propId);
		if (prop == null || prop.getNum() == 0) {
			message.putShort(ErrorCode.ARMS_PROP_NOT_EXIST);
			return message;
		}

		// 检查兵符与兵种的品阶是否匹配
		int heroSoldierQuality = getHeroSoldierQualityById(heroSoldierId);
		int propItemId = prop.getItemId();
		HashMap<Integer, Integer> mapping = expMap.get(propItemId);
		if (mapping == null) {
			message.putShort(ErrorCode.ARMS_PROP_CAN_NOT_USE);
			return message;
		}

		/**
		 * 兵符不可使用
		 */
		// 没有可匹配项
		Integer addExpValue = mapping.get(heroSoldierQuality);
		if (addExpValue == null) {
			message.putShort(ErrorCode.ARMS_PROP_CAN_NOT_USE);
			return message;
		}

		// 符合条件，准许升级
		int heroLv = hero.getLv();
		addHeroSoldierExp(role, soldier, addExpValue, heroLv);
		prop.setNum((short) (prop.getNum() - 1));
		prop.setChange(true);
		hero.setChange(true);

		ArmyLvConfig armyLvCofig = ArmyLvConfigCache.getArmyLvConfig(soldier.getLv());
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(soldier.getLv());
		message.putInt(soldier.getExp() - armyLvCofig.getBaseExp());
		message.putInt(armyLvCofig.getNextExp() - armyLvCofig.getBaseExp());

		return message;
	}

	@Override
	public Message updateHeroSoldierSkill(Role role, int heroId, int heroSoldierId) {
		Message message = new Message(ArmsConstant.UPDATE_HERO_SOLDIER_SKILL);
		Hero hero = role.getHeroMap().get(heroId);
		HashMap<Integer, HeroSoldier> heroSoldierMap = hero.getArmyInfoMap();
		HeroSoldier soldier = heroSoldierMap.get(heroSoldierId);
		// int quality = getHeroSoldierQualityById(heroSoldierId);

		// // 一阶兵种没有技能
		// if (quality == ArmsConstant.QUALITY_ONE) {
		// message.putShort(ErrorCode.ARMS_QUALITY_ONE_HAS_NOT_SKILL);
		// return message;
		// }

		// 兵种技能等级不可超过兵种等级
		if (soldier.getSkillLv() >= soldier.getLv()) {
			message.putShort(ErrorCode.ARMS_SKILLLV_BIGGER_THAN_SOLDIERLV);
			return message;
		}

		// 如果银两不够则返回失败
		int money = role.getMoney();
		int needMoney = ArmyLvConfigCache.getArmyLvConfig(soldier.getLv()).getSkillCost();
		if (money - needMoney < 0) {
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}

		// 技能升级
		message.putShort(ErrorCode.SUCCESS);
		soldier.setSkillLv(soldier.getSkillLv() + 1);
		role.setMoney(money - needMoney);
		role.setChange(true);
		message.putInt(needMoney);
		message.putInt(soldier.getSkillLv());

		return message;
	}

	@Override
	public Message updateHeroSoldierQuality(Role role, int heroId, int propId, int requestQuality) {
		Message message = new Message(ArmsConstant.UPDATE_HERO_SOLDIER_TYPE);
		Hero hero = role.getHeroMap().get(heroId);
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(heroId);
		int currentMaxQuality = hero.getMaxArmyQuality();

		// 检查是否已经升顶
		if (checkHeroSoldierMaxQuality(currentMaxQuality)) {
			message.putShort(ErrorCode.ARMS_QULALITY_MAX);
			return message;
		}

		// 检查前一阶兵种是否解锁
		if (!isHeroSoldierReachSoldierQuality(currentMaxQuality, requestQuality)) {
			message.putShort(ErrorCode.ARMS_SOLDIER_QUALITY_NOT_UNLOCK);
			return message;
		}

		// 检查武将等级达到
		if (!isHeroSoldierReachHeroQuality(hero.getRebirthLv(), currentMaxQuality)) {
			message.putShort(ErrorCode.ARMS_HERO_QUALITY_NOT_REACH);
			return message;
		}

		// 检查是否有对应印绶
		Integer propItemId = qualityPropMap.get(currentMaxQuality);
		Prop prop = role.getPropMap().get(propId);
		if (prop == null || propItemId != prop.getItemId() || prop.getNum() == 0) {
			message.putShort(ErrorCode.ARMS_PROP_NOT_EXIST);
			return message;
		}

		// 都符合条件，准许进阶
		prop.setNum((short) (prop.getNum() - 1));
		hero.setMaxArmyQuality(currentMaxQuality + 1);

		HashMap<Integer, HashMap<Integer, ArrayList<ArmsConfig>>> mapping = ArmsConfigCache.getArmsTypeQualityMapping();
//		HashMap<Integer, ArrayList<ArmsConfig>> allSoldier = mapping.get(heroConfig.getType());
//		ArrayList<ArmsConfig> array = allSoldier.get(hero.getMaxArmyQuality());
//		for (ArmsConfig armsConfig : array) {
//			heroService.createHeroSoldier(role, hero, armsConfig.getId());
//		}
		hero.setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(prop.getItemId());
		message.putInt(1);

		return message;
	}

	@Override
	public Message changeHeroSoldier(Role role, int heroId, int heroSoldierId) {
		Message message = new Message(ArmsConstant.CHANGE_HERO_SOLDIER);
		// 检查该兵种是否解锁
		Hero hero = role.getHeroMap().get(heroId);
//		HeroSoldier soldier = hero.getArmyInfoMap().get(heroSoldierId);
//		if (soldier.getStatus() == ArmsConstant.NOT_UNLOCK) {
//			message.putShort(ErrorCode.ARMS_NOT_UNLOCK);
//			return message;
//		}
		
		//检查兵种是否在正在使用范围
		HashSet<Integer> usingArmySet = hero.getUsingArmySet();
		if(!usingArmySet.contains(heroSoldierId)){
			message.putShort(ErrorCode.ARMS_NOT_UNLOCK);
			return message;
		}
		
		// 准许更换
		hero.setUseArmyId(heroSoldierId);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(heroSoldierId);

		return message;
	}

	/**
	 * 当前最大解锁兵种是否符合
	 * 
	 * @param currentMaxQuality
	 * @param requestQuality
	 * @return
	 * @author wcy
	 */
	private boolean isHeroSoldierReachSoldierQuality(int currentMaxQuality, int requestQuality) {
		return requestQuality == (currentMaxQuality + 1);
	}

	private void addHeroSoldierExp(Role role, HeroSoldier soldier, Integer addExp, int heroLv) {
		int currentExp = soldier.getExp();
		int forSoldierLv = getHeroSoldierLvByExp(soldier, currentExp + addExp);
		if (forSoldierLv <= heroLv) {
			soldier.setExp(currentExp + addExp);
			soldier.setLv((short) (forSoldierLv));
		} else {
			ArmyLvConfig config = ArmyLvConfigCache.getArmyLvConfig(heroLv);
			addExp = config.getNextExp() - 1;
			soldier.setLv(heroLv);
			soldier.setExp(addExp);
		}
	}

	/**
	 * 根据exp获得等级
	 * 
	 * @param soldier
	 * @param exp
	 * @return
	 * @author wcy
	 */
	private int getHeroSoldierLvByExp(HeroSoldier soldier, int exp) {
		int maxLv = 0;
		for (ArmyLvConfig armyLvConfig : ArmyLvConfigCache.getMaps().values()) {
			int lv = armyLvConfig.getLv();
			if (lv > maxLv) {
				maxLv = lv;
			}
			if (exp >= armyLvConfig.getBaseExp() && exp < armyLvConfig.getNextExp()) {
				return lv;
			}
		}
		return maxLv;
	}

	/**
	 * 兵种进阶是否达到的武将转生等级
	 * 
	 * @param herolv
	 * @param currentMaxQuality
	 * @return
	 * @author wcy
	 */
	private boolean isHeroSoldierReachHeroQuality(int rebirthLv, int currentMaxQuality) {
		if (currentMaxQuality == ArmsConstant.QUALITY_1) {
			if (rebirthLv >= 2) {
				return true;
			}
		} else if (currentMaxQuality == ArmsConstant.QUALITY_2) {
			if (rebirthLv >= 4) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得兵种的品阶
	 * 
	 * @param heroSoldierId
	 * @return
	 * @author wcy
	 */
	private int getHeroSoldierQualityById(int heroSoldierId) {
		int quality = ArmsConfigCache.getArmsConfigById(heroSoldierId).getRank();
		return quality;
	}

	/**
	 * 检查是否英雄兵种的品阶升顶
	 * 
	 * @param currentMaxSoldierQuality
	 * @return
	 * @author wcy
	 */
	private boolean checkHeroSoldierMaxQuality(int currentMaxSoldierQuality) {
		return currentMaxSoldierQuality == ArmsConstant.MAX_HERO_SOLDIER_QUALITY;
	}

	/***
	 * 判断同等级天赋是否已经存在
	 * 
	 * @return
	 */
	private boolean checkRoleArmy(Role role, RoleArmyConfig config) {
		boolean flage = true;
		Map<Integer, ArmyData> map = role.getRoleArmy().getMapById(config.getType());
		for (ArmyData x : map.values()) {
			if (x.getRank() == config.getRank() && x.getId() != config.getId()) {
				flage = false;
				break;
			}
		}
		return flage;
	}
}
