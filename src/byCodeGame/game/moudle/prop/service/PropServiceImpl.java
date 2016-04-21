package byCodeGame.game.moudle.prop.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.ChapterConfigCache;
import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.EquiptPrefixCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.ItemCache;
import byCodeGame.game.cache.file.MainBuildingConfigCache;
import byCodeGame.game.cache.file.StrengthSuccessConfigCache;
import byCodeGame.game.cache.file.StrengthenConfigCache;
import byCodeGame.game.cache.file.TreasureConfigCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.PropDao;
import byCodeGame.game.entity.bo.Build;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.ChapterConfig;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.EquiptPrefix;
import byCodeGame.game.entity.file.Item;
import byCodeGame.game.entity.file.MainBuildingConfig;
import byCodeGame.game.entity.file.StrengthSuccessConfig;
import byCodeGame.game.entity.file.StrengthenConfig;
import byCodeGame.game.entity.file.TreasureConfig;
import byCodeGame.game.entity.po.BuildInfo;
import byCodeGame.game.entity.po.LevyInfo;
import byCodeGame.game.entity.po.Package;
import byCodeGame.game.entity.po.VisitTreasureData;
import byCodeGame.game.moudle.chapter.ChapterConstant;
import byCodeGame.game.moudle.hero.HeroConstant;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.inCome.InComeConstant;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.moudle.prop.PropConstant;
import byCodeGame.game.moudle.pub.PubConstant;
import byCodeGame.game.moudle.rank.RankConstant;
import byCodeGame.game.moudle.rank.service.RankService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.HeroUtils;
import byCodeGame.game.util.InComeUtils;
import byCodeGame.game.util.Utils;

public class PropServiceImpl implements PropService {
	// private static final Logger propLog = LoggerFactory.getLogger("prop");

	private PropDao propDao;

	public void setPropDao(PropDao propDao) {
		this.propDao = propDao;
	}

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private InComeService inComeService;

	public void setInComeService(InComeService inComeService) {
		this.inComeService = inComeService;
	}

	private HeroService heroService;

	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}

	private RankService rankService;

	public void setRankService(RankService rankService) {
		this.rankService = rankService;
	}

	@Override
	public void propDataLoginHandle(Role role) {
		List<Prop> propList = propDao.getAllPropByRoleId(role.getId());
		for (Prop prop : propList) {
			role.getPropMap().put(prop.getId(), prop);

			Item item = ItemCache.getItemById(prop.getItemId());
			if (item != null && !(item.getCanUse() == PropConstant.CAN_USE)) {
				role.getPassivePropMap().put(prop.getItemId(), prop);
			}

			if (prop.getFunctionType() == PropConstant.FUNCTION_TYPE_2) {
				role.getConsumePropMap().put(prop.getItemId(), prop);
			}

			// 加装备放入武将MAP
			if (prop.getFunctionType() == PropConstant.FUNCTION_TYPE_1 && prop.getUseID() > 0) {
				Hero useHero = role.getHeroMap().get(prop.getUseID());
				useHero.getEquipMap().put(prop.getSlotId(), prop.getId());
			}
		}
	}

	@Override
	public void propDataOffLineHandle(Role role) {
		Map<Integer, Prop> propMap = role.getPropMap();
		for (Map.Entry<Integer, Prop> entry : propMap.entrySet()) {
			Prop tempProp = entry.getValue();
			if (tempProp.isChange())
				propDao.updateProp(tempProp);
		}
	}

	public Message getPropList(Role role) {
		Message message = new Message();
		message.setType(PropConstant.GET_PROP_LIST);
		message.putShort(ErrorCode.SUCCESS);

		Map<Integer, Prop> propMap = role.getPropMap();

		HashMap<Integer, Prop> gridPropsMap = new HashMap<Integer, Prop>();
		for (Map.Entry<Integer, Prop> entry : propMap.entrySet()) {
			Integer propId = entry.getKey();
			Prop prop = entry.getValue();
			if (prop.getFunctionType() == PropConstant.FUNCTION_TYPE_2) {
				this.initPropFightValue(prop);
			}
			short propNum = entry.getValue().getNum();
			// ArrayList<Short> propCountList = new ArrayList<Short>();
			// Short max = PropConstant.MAX_HEAP_VALUE;
			// while (propNum != 0) {
			// gridNum++;
			// if (propNum >= max) {
			// propCountList.add(max);
			// propNum -= max;
			// } else {
			// propCountList.add(propNum);
			// break;
			// }
			// }
			if (propNum >= PropConstant.NUM_1) {
				gridPropsMap.put(propId, entry.getValue());
			}

		}

		// 加入消息
		message.putInt(gridPropsMap.size());
		for (Map.Entry<Integer, Prop> entry : gridPropsMap.entrySet()) {
			int id = entry.getValue().getId();
			Prop prop = entry.getValue();
			message.putInt(id);
			message.putInt(prop.getItemId());
			message.putShort(prop.getNum());
			message.putShort(prop.getLv());
			message.putInt(prop.getUseID());
			message.putInt(prop.getPrefixId());
		}
		return message;
	}

	public Message useProp(Role role, int propId, short num, IoSession is) {
		Message message = new Message();
		message.setType(PropConstant.USE_PROP);
		Map<Integer, Prop> propMap = role.getPropMap();

		if (num <= 0) { // 数量过滤
			return null;
		}
		if (!propMap.containsKey(propId)) { // 角色没有该道具
			message.putShort(ErrorCode.NO_PROP);
			return message;
		}
		Prop prop = role.getPropMap().get(propId);
		if (prop.getFunctionType() != PropConstant.FUNCTION_TYPE_2) { // 该道具不是消耗品
			message.putShort(ErrorCode.PROP_CANT_USE);
			return message;
		}
		if (prop.getNum() <= PropConstant.USE_0) {
			message.putShort(ErrorCode.NO_PROP);
			return message;
		}
		// 道具使用成功
		Item item = ItemCache.getItemById(prop.getItemId());
		// 判断是否可用
		if (item.getCanUse() != PropConstant.ITEM_TYPE_1) {
			message.putShort(ErrorCode.PROP_CANT_USE);
			return message;
		}
		// 判断是否可以批量使用
		if (num > PropConstant.NUM_1 && item.getType() == PropConstant.ITEM_TYPE_1) {
			message.putShort(ErrorCode.ERR_UES_NUM);
			return message;
		}
		if (item.getNeedLv() > role.getLv()) { // 没有到达道具使用等级
			message.putShort(ErrorCode.LV_NOT_ENOUGH);
			return message;
		}

		// 是否使用经验道具
		if (item.getType() == PropConstant.ITEM_TYPE_5) {
			int nowChapterId = role.getChapter().getNowChapterId();
			ChapterConfig chapterConfig = ChapterConfigCache.getChapterConfigById(nowChapterId);
			int maxLv = 1;
			if (chapterConfig == null) {
				maxLv = 5;
			} else {
				ChapterConfig chapterConfig2 = ChapterConfigCache
						.getChapterConfigById(chapterConfig.getNextChapterId());
				if (chapterConfig2 != null && chapterConfig2.getBattleId() != chapterConfig.getBattleId()) {
					maxLv = (chapterConfig2.getBattleId()) * 5;
				} else {
					maxLv = (chapterConfig.getBattleId()) * 5;
				}

			}
			if (role.getLv() >= (short) maxLv) {
				message.putShort(ErrorCode.ERR_LV_USE);
				return message;
			}
		}

		if (item.getType() == PropConstant.ITEM_TYPE_1) { // 礼包类
			this.usePackageProp(role, prop, item, num, is);
		} else if (item.getType() == PropConstant.ITEM_TYPE_2) { // 银币类
			this.useMoneyProp(role, item, num);
		} else if (item.getType() == PropConstant.ITEM_TYPE_3) { // 军令类
			roleService.getArmyToken(role, item.getValue() * num);
		} else if (item.getType() == PropConstant.ITEM_TYPE_4) { // 元宝类
			roleService.addRoleGold(role, item.getValue() * num);
		} else if (item.getType() == PropConstant.ITEM_TYPE_5) { // 经验类
			this.useExpProp(role, item, num);
		} else if (item.getType() == PropConstant.ITEM_TYPE_6) { // 战功类
			this.useExploitProp(role, item, num);
		} else if (item.getType() == PropConstant.ITEM_TYPE_7) { // 粮食类
			this.useFoodProp(role, item, num);
		} else if (item.getType() == PropConstant.ITEM_TYPE_8) { // 里程
			role.getPub().setTalkMile(role.getPub().getTalkMile() + item.getValue());
			role.getPub().setChang(true);
		} else if (item.getType() == PropConstant.ITEM_TYPE_9) { // 扫荡券

		} else if (item.getType() == PropConstant.ITEM_TYPE_10) { // 精炼石

		} else if (item.getType() == PropConstant.ITEM_TYPE_11) { // 兵符

		} else if (item.getType() == PropConstant.ITEM_TYPE_12) { // 印绶

		} else if (item.getType() == PropConstant.ITEM_TYPE_13) {// 突飞令

		} else {
			return null;
		}

		// 减去数量
		prop.setNum((short) (prop.getNum() - num));
		prop.setChange(true);
		// 如果使用后数量为0 从背包中减去
		// if (prop.getNum() <= 0) {
		// role.getBackPack().remove(prop.getId());
		// }
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(propId);
		message.putShort(num);
		return message;
	}

	/**
	 * 使用银币类道具
	 */
	private void useMoneyProp(Role role, Item item, short num) {
		int value = item.getValue() * num;
		roleService.addRoleMoney(role, value);
	}

	/**
	 * 使用经验类道具
	 * 
	 * @param role
	 * @param prop
	 * @param item
	 * @param num
	 */
	private void useExpProp(Role role, Item item, short num) {
		int value = item.getValue() * num;
		roleService.addRoleExp(role, value);
	}

	/**
	 * 使用战功类道具
	 * 
	 * @param role
	 * @param prop
	 * @param item
	 * @param num
	 */
	private void useExploitProp(Role role, Item item, short num) {
		int value = item.getValue() * num;
		roleService.addRoleExploit(role, value);
	}

	private void useFoodProp(Role role, Item item, short num) {
		int value = item.getValue() * num;
		roleService.addRoleFood(role, value);
	}

	/**
	 * 使用礼包类道具
	 * 
	 * @param role
	 * @param prop
	 * @param item
	 * @param num
	 */
	private void usePackageProp(Role role, Prop prop, Item item, short num, IoSession is) {
		// 礼包奖励内容map
		Map<Integer, Package> rewardMap = item.getRewardMap();

		for (Map.Entry<Integer, Package> entry : rewardMap.entrySet()) {
			Package tempPackage = entry.getValue();
			if (tempPackage.getFunctionType() == PropConstant.FUNCTION_TYPE_1) { // 装备
				// 过滤装备和配置表匹配
				if (EquipConfigCache.getEquipConfigById(tempPackage.getId()) == null)
					continue;
				// 增加装备并向数据库插入
				for (int i = 1; i <= tempPackage.getValue(); i++) {
					Prop newEquip = new Prop();
					newEquip.setRoleId(role.getId());
					newEquip.setFunctionType(tempPackage.getFunctionType());
					newEquip.setItemId(tempPackage.getId());
					newEquip.setNum(PropConstant.NUM_1);
					newEquip.setLv(PropConstant.LV_1);
					newEquip.setSlotId(PropConstant.SLOT_0);
					int newEquipId = propDao.insertProp(newEquip);
					newEquip.setId(newEquipId);
					// 加入缓存
					role.getPropMap().put(newEquipId, newEquip);
					// 加入背包
					// role.getBackPack().put(newEquipId, newEquip);

					this.addProp(newEquip, is);
				}
			} else if (tempPackage.getFunctionType() == PropConstant.FUNCTION_TYPE_2) { // 消耗品
				// 匹配配置表
				if (ItemCache.getItemById(tempPackage.getId()) == null)
					continue;
				// 是否有相同itemId的道具
				Prop sameProp = null;
				for (Map.Entry<Integer, Prop> propEntry : role.getPropMap().entrySet()) {
					Prop userProp = propEntry.getValue();
					if (userProp.getItemId() == tempPackage.getId()) {
						sameProp = userProp;
						break;
					}
				}
				if (sameProp == null) { // 缓存中没有相同itemId的消耗品
					Prop newProp = new Prop();
					newProp.setRoleId(role.getId());
					newProp.setFunctionType(tempPackage.getFunctionType());
					newProp.setItemId(tempPackage.getId());
					newProp.setNum((short) tempPackage.getValue());
					int newPropId = propDao.insertProp(newProp);
					newProp.setId(newPropId);
					// 加入缓存
					role.getPropMap().put(newPropId, newProp);
					role.getConsumePropMap().put(newProp.getItemId(), newProp);
					// 加入背包
					// role.getBackPack().put(newPropId, newProp);
					this.addProp(newProp, is);
				} else {
					// 以下两行只为解决推送数量问题
					sameProp.setNum((short) tempPackage.getValue());
					this.addProp(sameProp, is);

					sameProp.setNum((short) (sameProp.getNum() + tempPackage.getValue()));
					sameProp.setChange(true);

					// 加入背包
					role.getPropMap().put(sameProp.getId(), sameProp);
				}
			}
		}
	}

	/**
	 * 如果使用的道具为礼包类型，判定剩余的背包格子数量
	 * 
	 * @param role
	 * @param itemId
	 * @return
	 */

	// private boolean checkBackPack(Role role, Item item) {
	// boolean b = false;
	// // 剩余的背包格子数
	// int remainNum = (int) role.getMaxBagNum() - role.getPropMap().size();
	// // 打开礼包所需要的格子数
	// int needNum = 0;
	// // 礼包奖励的道具MAP
	// Map<Integer, Package> rewardMap = item.getRewardMap();
	// for (Map.Entry<Integer, Package> entry : rewardMap.entrySet()) {
	// Package tempPackage = entry.getValue();
	// if (tempPackage.getFunctionType() == PropConstant.FUNCTION_TYPE_1) {
	// needNum += tempPackage.getValue();
	// } else if (tempPackage.getFunctionType() == PropConstant.FUNCTION_TYPE_2)
	// {
	// int num1 = 0;
	// for (Map.Entry<Integer, Prop> backPackEentry :
	// role.getBackPack().entrySet()) {
	// Prop tempProp = backPackEentry.getValue();
	// if (tempProp.getItemId() != tempPackage.getId()) {
	// num1++;
	// }
	// }
	// if (role.getBackPack().size() == num1) {
	// needNum++;
	// }
	// }
	// }
	// if (remainNum >= needNum)
	// b = true;
	// return b;
	// }

	public void addProp(Prop prop, IoSession is) {
		Message message = new Message();
		message.setType(PropConstant.ADD_PROP);
		// 道具ID,物品ID,道具数量,道具等级,使用者ID
		StringBuilder sb = new StringBuilder();
		sb.append(prop.getId()).append(",").append(prop.getItemId()).append(",").append(prop.getNum()).append(",")
				.append(prop.getLv()).append(",").append(prop.getUseID());

		message.putString(sb.toString());
		is.write(message);
	}

	public void addProp(Role role, int itemId, byte type, int num, IoSession is) {
		Message message = new Message();
		message.setType(PropConstant.ADD_PROP);
		num = (short) num > PropConstant.MAX_HEAP_VALUE ? PropConstant.MAX_HEAP_VALUE : num;
		// 道具ID,物品ID,道具数量,道具等级,使用者ID
		StringBuilder sb = new StringBuilder();
		EquipConfig equipConfig = null;
		Item item = ItemCache.getItemById(itemId);
		if (type == PropConstant.FUNCTION_TYPE_1) { // 装备
			equipConfig = EquipConfigCache.getEquipConfigById(itemId);
			if (equipConfig == null) {
				return;
			}
			Prop prop = new Prop();
			prop.setRoleId(role.getId());
			prop.setFunctionType(PropConstant.FUNCTION_TYPE_1);
			prop.setItemId(itemId);
			prop.setNum(PropConstant.NUM_1);
			prop.setLv(PropConstant.LV_1);
			int id = propDao.insertProp(prop);
			prop.setId(id);
			this.initPropFightValue(prop);
			role.getPropMap().put(prop.getId(), prop);
			// role.getBackPack().put(prop.getId(), prop);
			sb.append(prop.getId()).append(",").append(prop.getItemId()).append(",").append(prop.getNum()).append(",")
					.append(prop.getLv()).append(",").append(prop.getUseID());
		} else if (type == PropConstant.FUNCTION_TYPE_2) { // 消耗品
			item = ItemCache.getItemById(itemId);
			if (item == null) {
				return;
			}
			if (checkItemInBackPack(role, itemId)) { // 背包内有相同
				int id = sameItemAddNum(role, itemId, num);
				sb.append(id).append(",").append(itemId).append(",").append(num).append(",").append(0).append(",")
						.append(0);
			} else { // 背包内没有相同
				Prop prop = new Prop();
				prop.setRoleId(role.getId());
				prop.setFunctionType(PropConstant.FUNCTION_TYPE_2);
				prop.setItemId(itemId);
				prop.setNum((short) num);
				int id = propDao.insertProp(prop);
				prop.setId(id);
				role.getPropMap().put(prop.getId(), prop);
				role.getConsumePropMap().put(prop.getItemId(), prop);
				if (!(ItemCache.getItemById(prop.getItemId()).getCanUse() == PropConstant.CAN_USE)) {
					role.getPassivePropMap().put(prop.getItemId(), prop);
				}
				// role.getBackPack().put(prop.getId(), prop);
				sb.append(prop.getId()).append(",").append(itemId).append(",").append(num).append(",").append(0)
						.append(",").append(0);
			}
		}

		role.setChange(true);
		message.putString(sb.toString());
		is.write(message);
	}

	public Message equipProp(Role role, int heroId, byte slotId, int propId) {
		Message message = new Message();
		message.setType(PropConstant.EQUIP_PROP);
		Prop prop = role.getPropMap().get(propId);
		if (prop == null) { // 背包没有该道具
			message.putShort(ErrorCode.NO_PROP);
			return message;
		}
		if (prop.getSlotId() > PropConstant.SLOT_0) { // 该装备正在装备中
			message.putShort(ErrorCode.PROP_IS_EQUIP);
			return message;
		}
		if (prop.getFunctionType() != PropConstant.FUNCTION_TYPE_1) { // 该物品非装备类
			message.putShort(ErrorCode.PROP_NOT_EQUIP);
			return message;
		}

		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null) {
			return null;
		}

		EquipConfig config = EquipConfigCache.getEquipConfigById(prop.getItemId());
		if (config == null) {
			return null;
		}
		if (hero.getLv() < config.getNeedLv()) {
			message.putShort(ErrorCode.ERR_HERO_LV);
			return message;
		}

		// 装备成功
		Prop oldProp = null;
		if (hero.getEquipMap().containsKey(slotId)) {
			int oldPropId = hero.getEquipMap().get(slotId);
			if (oldPropId > 0) { // 原装备位置有装备 卸载装备
				oldProp = role.getPropMap().get(oldPropId);
				oldProp.setUseID(PropConstant.USE_0);
				oldProp.setSlotId(PropConstant.SLOT_0);
				oldProp.setChange(true);
				// // 加入背包
				// role.getBackPack().put(oldProp.getId(), oldProp);
			}
		}

		prop.setUseID(heroId);
		prop.setSlotId(slotId);
		prop.setChange(true);
		// 检查是否有装备的任务
		this.taskService.checkEquipTask(role, (byte) 1, prop.getSlotId());
		// 从背包内移除
		// role.getBackPack().remove(prop.getId());
		hero.getEquipMap().put(slotId, prop.getId());
		// 面板属性
		this.changeFightValue(prop, hero, oldProp);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(heroId);
		message.put(slotId);
		message.putInt(propId);
		return message;
	}

	public Message uninstallEquip(Role role, int heroId, byte slotId, int propId) {
		Message message = new Message();
		message.setType(PropConstant.UNINSTALL_EQUIP);
		Hero hero = role.getHeroMap().get(heroId);

		StringBuffer sb = new StringBuffer();
		if (slotId == -1) {
			// 一键卸载
			Map<Byte, Integer> heroEquipMap = hero.getEquipMap();
			if (heroEquipMap.size() == 0) {
				message.putShort(ErrorCode.NO_EQUIP_PROP);
				return message;
			}

			for (Integer v : heroEquipMap.values()) {
				Prop p = role.getPropMap().get(v);
				if (p == null)
					continue;
				p.setSlotId(PropConstant.SLOT_0);
				p.setUseID(PropConstant.USE_0);
				p.setChange(true);
				this.changeFightValue(null, hero, p);

				sb.append(v).append(",").append(p.getLv()).append(",").append(p.getPrefixId()).append(";");

				hero.setChange(true);
			}
			hero.getEquipMap().clear();
		} else {
			Prop prop = role.getPropMap().get(propId);
			if (prop == null) { // 没有改道具
				message.putShort(ErrorCode.NO_PROP);
				return message;
			}

			if (prop.getSlotId() == PropConstant.SLOT_0) { // 该道具未装备
				prop.setUseID(PropConstant.USE_0);
				return null;
			}

			if (hero == null) { // 没有该英雄
				return null;
			}// 单件卸载成功
			hero.getEquipMap().remove(slotId);
			prop.setSlotId(PropConstant.SLOT_0);
			prop.setUseID(PropConstant.USE_0);
			prop.setChange(true);
			this.changeFightValue(null, hero, prop);
			sb.append(propId).append(",").append(prop.getLv()).append(",").append(prop.getPrefixId()).append(";");
			hero.setChange(true);
		}

		message.putShort(ErrorCode.SUCCESS);
		message.putString(sb.toString());

		return message;
	}

	public boolean uninstallEquipAuto(Role role, int heroId, Collection<Integer> set) {
		boolean flag = false;
		Set<Integer> use = new HashSet<Integer>();
		for (Integer x : set) {
			use.add(x);
		}
		for (Integer x : use) {
			Prop prop = role.getPropMap().get(x);
			if (prop == null) { // 没有改道具
				return flag;
			}
			if (prop.getSlotId() == PropConstant.SLOT_0) { // 该道具未装备
				return flag;
			}
			Hero hero = role.getHeroMap().get(heroId);
			if (hero == null) { // 没有该英雄
				return flag;
			}
			// 卸载成功
			hero.getEquipMap().remove(prop.getSlotId());
			prop.setSlotId(PropConstant.SLOT_0);
			prop.setUseID(PropConstant.USE_0);
			prop.setChange(true);
			// role.getBackPack().put(prop.getId(), prop);
			this.changeFightValue(null, hero, prop);
		}
		use = null;
		flag = true;
		return flag;
	}

	public void initialProp(Role role, Connection conn) {

		// Prop prop3 = new Prop();
		// prop3.setRoleId(role.getId());
		// prop3.setItemId(20001);
		// prop3.setNum((short) 2);
		// prop3.setFunctionType(PropConstant.FUNCTION_TYPE_2);
		// int id3 = propDao.insertProp(prop3, conn);
		// prop3.setId(id3);
		//
		// Prop prop4 = new Prop();
		// prop4.setRoleId(role.getId());
		// prop4.setItemId(20002);
		// prop4.setNum((short) 1);
		// prop4.setFunctionType(PropConstant.FUNCTION_TYPE_2);
		// int id4 = propDao.insertProp(prop4, conn);
		// prop4.setId(id4);
		//
		// Prop prop5 = new Prop();
		// prop5.setRoleId(role.getId());
		// prop5.setItemId(20003);
		// prop5.setNum((short) 1);
		// prop5.setFunctionType(PropConstant.FUNCTION_TYPE_2);
		// int id5 = propDao.insertProp(prop5, conn);
		// prop5.setId(id5);
		//
		// // 加入背包
		// role.getPropMap().put(prop3.getId(), prop3);
		// // role.getBackPack().put(prop3.getId(), prop3);
		// role.getPropMap().put(prop4.getId(), prop4);
		// // role.getBackPack().put(prop4.getId(), prop4);
		// role.getPropMap().put(prop5.getId(), prop5);
		// // role.getBackPack().put(prop5.getId(), prop5);
		// // role.getBackPack().put(prop7.getId(), prop7);
		//
		// Map<Integer, Prop> consumePropMap = role.getConsumePropMap();
		// consumePropMap.put(prop3.getItemId(), prop3);
		// consumePropMap.put(prop4.getItemId(), prop4);
		// consumePropMap.put(prop5.getItemId(), prop5);

	}

	// public Message quickDress(Role role, int heroId1, int heroId2) {
	// Message message = new Message();
	// message.setType(PropConstant.QUICE_DRESS);
	// for (byte i = 1; i <= PropConstant.SLOT_6; i++) {
	// boolean b = this.checkSingleDress(role, heroId1, heroId2, i);
	// if (b == false) {
	// message.putShort(ErrorCode.CAN_NOT_QUICE_DRESS);
	// return message;
	// }
	// }
	// for (byte j = 1; j <= PropConstant.SLOT_6; j++) {
	// this.singleDressPri(role, heroId1, heroId2, j);
	// }
	//
	// message.putShort(ErrorCode.SUCCESS);
	// message.putInt(heroId1);
	// message.putInt(heroId2);
	// return message;
	// }

	@Override
	public Message quickDress(Role role, int heroId) {
		Message message = new Message(PropConstant.QUICE_DRESS);

		Hero hero = role.getHeroMap().get(heroId);

		Map<Byte, Integer> equipMap = hero.getEquipMap();
		Map<Integer, Prop> propMap = role.getPropMap();

		for (Entry<Integer, Prop> entry : propMap.entrySet()) {
			Integer serverId = entry.getKey();
			Prop prop = entry.getValue();
			int itemId = prop.getItemId();
			EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(itemId);

			if (prop.getFunctionType() != PropConstant.FUNCTION_TYPE_1 || prop.getUseID() != 0
					|| hero.getLv() < equipConfig.getNeedLv()) {
				continue;
			}

			byte slot = (byte) equipConfig.getSlot();

			Integer currentEquipId = equipMap.get(slot);
			if (currentEquipId == null) {
				equipMap.put(slot, serverId);
				prop.setUseID(heroId);
				prop.setSlotId(slot);
			} else {
				boolean replaceEquip = false;
				Prop currentEquip = role.getPropMap().get(currentEquipId);
				if (currentEquip.getFightValue() < prop.getFightValue()) {// 比战力值
					replaceEquip = true;
				} else if (currentEquip.getFightValue() == prop.getFightValue()) {
					EquipConfig currentEquipConfig = EquipConfigCache.getEquipConfigById(currentEquip.getItemId());
					if (currentEquipConfig.getQuality() < equipConfig.getQuality()) {// 比品质
						replaceEquip = true;
					} else if (currentEquipConfig.getQuality() == equipConfig.getQuality()) {
						if (currentEquip.getLv() < prop.getLv()) {// 比等级
							replaceEquip = true;
						} else if (currentEquip.getLv() == prop.getLv()) {
							if (currentEquip.getItemId() > prop.getItemId()) {// 比id号
								replaceEquip = true;
							}
						}
					}
				}

				if (replaceEquip) {
					currentEquip.setSlotId(PropConstant.SLOT_0);
					currentEquip.setUseID(PropConstant.USE_0);

					equipMap.put(slot, serverId);
					prop.setSlotId(slot);
					prop.setUseID(heroId);

					prop.setChange(true);
					currentEquip.setChange(true);
				}

			}

		}

		heroService.initHeroFightValue(role, hero);

		hero.setChange(true);

		StringBuilder equipSb = new StringBuilder();
		for (Entry<Byte, Integer> entry : equipMap.entrySet()) {
			int propId = entry.getValue();
			Prop prop = propMap.get(propId);
			equipSb.append(propId).append(",").append(prop.getLv()).append(",").append(prop.getPrefixId()).append(";");
		}
		message.putString(equipSb.toString());
		return message;
	}

	/**
	 * 熔炼装备
	 * 
	 * @author xjd
	 */
	@Override
	public Message recoveryEquip(Role role, String id, IoSession is) {
		Message message = new Message();
		message.setType(PropConstant.RETURN_EQUIP);
		Short buildLv = role.getBuild().getBuildLvMap().get(InComeConstant.TYPE_FORGE);
		if (buildLv == null) {
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}

		if (buildLv < PropConstant.UNLOCK_RECOVERY_LV) {
			message.putShort(ErrorCode.NO_LV);
			return message;
		}
		// 开始炼化过程
		int num = PropConstant.USE_0;
		int money = PropConstant.USE_0;
		String[] i = id.split(",");
		for (String x : i) {
			Prop prop = role.getPropMap().get(Integer.parseInt(x));
			if (prop == null) {
				return null;
			}
			EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());
			if (equipConfig == null) {
				return null;
			}
			if (prop.getPrefixId() > PropConstant.USE_0) {
				EquiptPrefix equiptPrefix = EquiptPrefixCache.getEquipConfigById(prop.getPrefixId());
				num += equiptPrefix.getReturnStone();
			}
			StrengthenConfig strengthenConfig = StrengthenConfigCache.getStrengthenConfigByLv(prop.getLv());
			money = equipConfig.getSellPrice() + strengthenConfig.getCost(equipConfig.getQuality());
			// 从背包和数据库中删除该数量的道具
			propDao.removeProp(prop.getId());
			// role.getBackPack().remove(prop.getId());
			role.getPropMap().remove(prop.getId());
		}

		this.addProp(role, PropConstant.STONE_ID, PropConstant.FUNCTION_TYPE_2, num, is);
		roleService.addRoleMoney(role, money);

		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 附魔装备
	 * 
	 * @author xjd
	 */
	public Message refineEquip(Role role, int id) {
		Message message = new Message();
		message.setType(PropConstant.REFINE_EQUIP);

		Short buildLv = role.getBuild().getBuildLvMap().get(InComeConstant.TYPE_FORGE);
		if (buildLv == null) {
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		if (buildLv < PropConstant.UNLOCK_REFINE_LV) {
			message.putShort(ErrorCode.NO_LV);
			return message;
		}

		Prop prop = role.getPropMap().get(id);
		if (prop == null) {
			return null;
		}
		EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());
		if (equipConfig == null) {
			return null;
		}
		// 开始精炼流程
		int need = chooesNumStone(equipConfig.getQuality());
		if (need == PropConstant.USE_0) {
			message.putShort(ErrorCode.ERR_RE);
			return message;
		}
		// 判断精炼石数量
		Prop prop2 = role.getPropMap().get(this.getSid(role));
		if (prop2 == null || prop2.getNum() < need) {
			message.putShort(ErrorCode.ERR_NUM_STONE);
			return message;
		}
		// // 是否达到当前品质上限
		EquiptPrefix equiptPrefix = EquiptPrefixCache.getEquipConfigById(prop.getPrefixId());
		// int max = this.checkMax(equipConfig.getQuality());
		// if (equiptPrefix.getLv() > max || max == PropConstant.USE_0) {
		// message.putShort(ErrorCode.ERR_MAX_STONE);
		// return message;
		// }
		prop2.setNum((short) (prop2.getNum() - need));
		prop2.setChange(true);
		int oldId = prop.getPrefixId();
		EquiptPrefix x = this.chooesPrefix(equiptPrefix.getType());
		prop.setPrefixId(x.getId());
		prop.setChange(true);
		Hero hero = role.getHeroMap().get(prop.getUseID());
		if (hero != null)
			this.changeFightValue2(prop.getPrefixId(), hero, oldId);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(x.getId());
		return message;

	}

	/**
	 * 更换词缀|初次集火词缀 刷新强化队列
	 * 
	 * @author xjd
	 */
	public Message changePrefix(Role role, int id) {
		Message message = new Message();
		message.setType(PropConstant.CHANGE_PREFIX);
		Prop prop = role.getPropMap().get(id);
		if (prop == null) {
			return null;
		}
		// 取出装备配置表
		EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());
		if (equipConfig == null) {
			return null;
		}
		int need = chooesNumStone(equipConfig.getQuality());
		if (need == PropConstant.USE_0) {
			message.putShort(ErrorCode.ERR_RE);
			return message;
		}
		// 判断精炼石数量
		Prop prop2 = role.getPropMap().get(this.getSid(role));
		if (prop2 == null || prop2.getNum() < need) {
			message.putShort(ErrorCode.ERR_NUM_STONE);
			return message;
		}
		// 开始更换词缀
		prop2.setNum((short) (prop2.getNum() - need));
		prop2.setChange(true);
		int oldId = prop.getPrefixId();
		EquiptPrefix x = this.chooesPrefix(PropConstant.USE_0);
		prop.setPrefixId(x.getId());
		this.initPropFightValue(prop2);
		prop.setChange(true);

		Hero hero = role.getHeroMap().get(prop.getUseID());
		if (hero != null)
			this.changeFightValue2(prop.getPrefixId(), hero, oldId);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(x.getId());
		return message;
	}

	/**
	 * 强化装备
	 * 
	 * @author xjd
	 */
	public Message strengthenEquip(IoSession is, Role role, int id, int num) {
		Message message = new Message();
		message.setType(PropConstant.STRENGTHEN_EQUIP);
		// 取出装备
		Prop prop = role.getPropMap().get(id);
		if (prop == null) {
			return null;
		}
		short propLv = prop.getLv();
		Build build = role.getBuild();
		Short forgeLv = build.getBuildLvMap().get(InComeConstant.TYPE_FORGE);
		if(forgeLv == null){
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		// 装备等级无法超过锻造坊等级
		if (propLv >= forgeLv) {
			message.putShort(ErrorCode.PROP_BUILD_LV_NO_REACH);
			return message;
		}
		// 取出装备配置表
		EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());
		// 本次强化花费
		StrengthenConfig strengthenConfig = StrengthenConfigCache.getStrengthenConfigByLv(prop.getLv());
		int cost = strengthenConfig.getCost(equipConfig.getQuality());

		Integer attachHeroId = build.getAttachHeroMap().containsKey(InComeConstant.TYPE_FORGE)?
				build.getAttachHeroMap().get(InComeConstant.TYPE_FORGE).getHeroId() : null;
		Hero attachHero = null;

		if (attachHeroId != null)
			attachHero = role.getHeroMap().get(attachHeroId);

		// 获得该装备强化成功率
//		StrengthSuccessConfig strengthSuccessConfig = StrengthSuccessConfigCache
//				.getStrengthSuccessConfigByLv((int) propLv);
//		int rate = strengthSuccessConfig.getRate();
//		int ironCount = strengthSuccessConfig.getIron();
//		int addRate = strengthSuccessConfig.getAddRate();
//		int consumeIron = num / ironCount;
//		rate = rate + consumeIron * addRate + inComeService.getPropStrengthAddDelta(attachHero);
//		rate = rate > PropConstant.MAX_STRENGTHEN ? PropConstant.MAX_STRENGTHEN : rate;
//		rate = rate < PropConstant.MIN_STRENGTHEN ? PropConstant.MIN_STRENGTHEN : rate;

		double rate = this.getStrengthSuccessRate(role, prop, num, attachHero);
		
		// 判断玩家银币是否足够
		if (role.getMoney() < cost) {
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}

		int currentIronNum = 0;
		Prop iron = role.getConsumePropMap().get(PropConstant.PROP_IRON);
		if (iron != null) {
			currentIronNum = iron.getNum();
		}
		if (currentIronNum < num) {
			message.putShort(ErrorCode.NO_PROP);
			return message;
		}
		// 扣钱
		roleService.addRoleMoney(role, -cost);

		// 随机数以判定是否成功
		int randNum = Utils.getRandomNum(PropConstant.MAX_STRENGTHEN + PropConstant.MIN_STRENGTHEN);
		boolean success = randNum <= rate ? true : false;

		// 铁矿减去
		if (iron != null && num > 0) {
			iron.setNum((short) (iron.getNum() - num));
			iron.setChange(true);
		}

		int propNum = 0;
		message.putShort(ErrorCode.SUCCESS);
		if (success) {
			if (prop.getUseID() > PropConstant.CAN_USE) {
				this.changeFightValue3(prop, role.getHeroMap().get(prop.getUseID()));
			}
			prop.setLv((short) (prop.getLv() + PropConstant.LV_UP_PROP));
			this.initPropFightValue(prop);
			prop.setChange(true);
			this.taskService.checkStrengthenTask(role, equipConfig.getSlot(), prop.getLv());
		} else {
//			propNum = 1;
//			addProp(role, PropConstant.PROP_IRON, PropConstant.FUNCTION_TYPE_2, propNum, is);
		}
		//TODO
		int nowTime = Utils.getNowTime();
		rankService.refreshHeroFightValue(role, nowTime,  RankConstant.HERO_FIGHT_VALUE_NUM);
		
		message.put(success?PropConstant.SUCCESS_STRENGTHEN:PropConstant.FAIL_STRENGTHEN);
		message.putInt(cost);
		message.putInt(propNum);
		message.putInt(num);
		

		return message;
	}

	@Override
	public Message makeIronIngot(Role role, int oreNum, IoSession is) {
		Message message = new Message();
		message.setType(PropConstant.MAKE_IRON);

		if (oreNum <= 0) {
			return null;
		}

		Prop prop = role.getConsumePropMap().get(PropConstant.PROP_ORE);
		if (prop == null) {
			message.putShort(ErrorCode.ERR_NUMBER_SELL);
			return message;
		}

		int canGetIronNum = this.convertOreNumToIronNum(oreNum);
		int useOreNum = canGetIronNum * GeneralNumConstantCache.getValue("IRON_NEED_ORE_NUM");
		if (useOreNum == 0) {
			message.putShort(ErrorCode.FAIL_MAKE_IRON);
			return message;
		}
		int currentNum = prop.getNum();
		if (currentNum < useOreNum) {
			// 没有那么多矿石
			message.putShort(ErrorCode.ERR_NUMBER_SELL);
			return message;
		}

		prop.setNum((short) (prop.getNum() - useOreNum));
		addProp(role, PropConstant.PROP_IRON, PropConstant.FUNCTION_TYPE_2, canGetIronNum, is);
		int remainMaxMakeIronNum = this.convertOreNumToIronNum((int) prop.getNum());

		prop.setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(useOreNum);// 消耗的铁矿数量
		message.putInt(remainMaxMakeIronNum);// 剩余可以锻造的最大数量

		return message;
	}

	@Override
	public Message showMakeIron(Role role) {
		Message message = new Message();
		message.setType(PropConstant.SHOW_MAKE_IRON);
		int nowTime = Utils.getNowTime();
		this.inComeService.checkHeroManual(role, nowTime);
		Build build = role.getBuild();
		Short forgeLv = build.getBuildLvMap().get(InComeConstant.TYPE_FORGE);
		if (forgeLv == null) {
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(
				InComeConstant.TYPE_FORGE, forgeLv);
		int cdTime = mainBuildingConfig.getCd();

		// 最大炼铁数
		Map<Integer, Prop> consumePropMap = role.getConsumePropMap();
		Prop ore = consumePropMap.get(PropConstant.PROP_ORE);

		int oreNum = ore != null ? ore.getNum() : 0;
		int maxIronNum = this.convertOreNumToIronNum(oreNum);

		// 刷新领取数量
		refreshGetIron(role, nowTime);
		int getIronNum = build.getInComeCacheMap().get(InComeConstant.TYPE_FORGE);

		// 剩余时间
		Map<Byte, Long> lastIncomeTimeMap = build.getBuildLastIncomeTimeMap();
		long startTime = lastIncomeTimeMap.get(InComeConstant.TYPE_FORGE);
		int remainTime = (int) (cdTime - (nowTime - startTime) % cdTime);

		// 每小时产量
		int inCome = inComeService.getHourIncomeValue(role, InComeConstant.TYPE_FORGE);

		// 当前铁和铁矿的数量
		Prop irons = role.getConsumePropMap().get(PropConstant.PROP_IRON);

		int ironsNum = 0;
		if (irons != null) {
			ironsNum = irons.getNum();
		}

		message.putInt(maxIronNum);
		message.putInt(remainTime);
		message.putInt(getIronNum);
		message.putInt(inCome);
		message.putInt(ironsNum);
		message.putInt(oreNum);
		message.putInt(GeneralNumConstantCache.getValue("IRON_NEED_ORE_NUM"));

		return message;
	}

	@Override
	public void refreshGetIron(Role role, long nowTime) {
		// Build build = role.getBuild();
		// Short forgeLv = build.getBuildLvMap().get(InComeConstant.TYPE_FORGE);
		// if(forgeLv == null) return;
		// MainBuildingConfig mainBuildingConfig =
		// MainBuildingConfigCache.getMainBuildingConfig(
		// InComeConstant.TYPE_FORGE, forgeLv);
		//
		// int cdTime = mainBuildingConfig.getCd();
		//
		// int inCome = inComeService.getHourIncomeValue(role,
		// InComeConstant.TYPE_FORGE);
		// Map<Byte, Long> lastIncomeTimeMap =
		// build.getBuildLastIncomeTimeMap();
		// long startTime = lastIncomeTimeMap.get(InComeConstant.TYPE_FORGE);
		//
		// // 可领取次数
		// int getIronTimes = (int) (nowTime - startTime) / cdTime;
		// // 可领取数量
		// int getIronNum = getIronTimes * inCome;
		// Map<Byte, Integer> incomeCacheMap = build.getInComeCacheMap();
		// Integer cache = incomeCacheMap.get(InComeConstant.TYPE_FORGE);
		// getIronNum += cache;
		//
		// int maxCapacity = mainBuildingConfig.getCapacity();
		// if (getIronNum > maxCapacity) {
		// getIronNum = maxCapacity;
		// }
		//
		// incomeCacheMap.put(InComeConstant.TYPE_FORGE, getIronNum);
		// lastIncomeTimeMap.put(InComeConstant.TYPE_FORGE, startTime +
		// getIronTimes * cdTime);

		inComeService.refreshGetResources(role, nowTime, InComeConstant.TYPE_FORGE);
	}

	@Override
	public void initForge(Role role) {
		long nowTime = System.currentTimeMillis() / 1000;
		Build build = role.getBuild();
		build.getBuildLastIncomeTimeMap().put(InComeConstant.TYPE_FORGE, nowTime);
//		build.getInComeCacheMap().put(InComeConstant.TYPE_FORGE, 500);
		build.addInComeCacheMap(InComeConstant.TYPE_FORGE, 500);
		build.setChange(true);
	}

	/**
	 * 根据铁矿数量获得可锻造的铁锭的数量
	 * 
	 * @param oreNum
	 * @return
	 * @author wcy
	 */
	private int convertOreNumToIronNum(int oreNum) {
		int ironNum = oreNum / GeneralNumConstantCache.getValue("IRON_NEED_ORE_NUM");
		return ironNum;
	}

	@Override
	public Message getIron(Role role, IoSession is) {
		Message message = new Message();
		message.setType(PropConstant.GET_IRONS);
		int nowTime = Utils.getNowTime();
		inComeService.checkHeroManual(role, nowTime);
		Build build = role.getBuild();
		Short forgeLv = build.getBuildLvMap().get(InComeConstant.TYPE_FORGE);
		if (forgeLv == null) {
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(
				InComeConstant.TYPE_FORGE, forgeLv);
		int cdTime = mainBuildingConfig.getCd();

		// 获取配属英雄缩减后的cd时间
		refreshGetIron(role, nowTime);

		// 剩余时间
		Map<Byte, Long> lastIncomeTimeMap = build.getBuildLastIncomeTimeMap();
		long startTime = lastIncomeTimeMap.get(InComeConstant.TYPE_FORGE);
		int remainTime = (int) ((nowTime - startTime) % cdTime);

		Map<Byte, Integer> incomeCacheMap = build.getInComeCacheMap();
		int getIronNum = incomeCacheMap.get(InComeConstant.TYPE_FORGE);
		if (getIronNum == 0) {// 检查领取是否是空
			message.putShort(ErrorCode.NO_PROP);
			return message;
		}
		this.addProp(role, PropConstant.PROP_IRON, PropConstant.FUNCTION_TYPE_2, getIronNum, is);
		// 锻造房缓存清空
		incomeCacheMap.put(InComeConstant.TYPE_FORGE, 0);

		inComeService.recordIncomeRank(role, InComeConstant.TYPE_FORGE, getIronNum, nowTime);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(getIronNum);
		message.putInt(remainTime);

		return message;
	}

	/**
	 * 
	 * 获取寻宝奖励
	 * 
	 * @author xjd
	 */
	@Override
	public Message getVisitTreasureAward(Role role, int id, IoSession is) {
		Message message = new Message();
		message.setType(PropConstant.GET_VISIT_TREASURE_AWARD);

		Build build = role.getBuild();

		// 根据规则随机奖励
		VisitTreasureData visitTreasureData = build.getVisitTreasureDataMap().get(id);
		if (visitTreasureData == null) {
			return null;
		}
		// 根据城市ID和声望值选择Po
		int itemId = visitTreasureData.getItemId();
		byte functionType = 0;
		byte itemType = 0;
		int num = visitTreasureData.getNum();
		int heroId = visitTreasureData.getHeroId();

		// 处理奖励
		if (itemId >= PubConstant.EQUIP_FLAG && itemId < PubConstant.ITEM_FLAG) {
			// 装备
			itemType = ChapterConstant.Award.EQUIP.getVal();
			functionType = PropConstant.FUNCTION_TYPE_1;
		} else if (itemId >= PubConstant.ITEM_FLAG) {
			// 道具
			itemType = ChapterConstant.Award.ITEM.getVal();
			functionType = PropConstant.FUNCTION_TYPE_2;
		}
		this.addProp(role, itemId, functionType, num, is);

		// 清除数据
		build.getVisitTreasureDataMap().remove(id);
		build.setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		message.put(itemType);
		message.putInt(itemId);
		message.putInt(num);
		message.putInt(heroId);

		return message;
	}

	/**
	 * 寻宝
	 * 
	 * @author xjd
	 */
	@Override
	public Message visitTreasure(Role role, int heroId) {
		Message message = new Message();
		message.setType(PropConstant.VISIT_TREASURE);
		long nowTime = System.currentTimeMillis() / 1000;
		inComeService.refreshLevyData(role, nowTime);

		// 上次寻访未结束
		if (!InComeUtils.checkLevy(role, InComeConstant.TYPE_FORGE, heroId)) {
			message.putShort(ErrorCode.VISIT_NOT);
			return message;
		}

		// 检测体力
		if (!inComeService.checkCanSendHero(role.getHeroMap().get(heroId),
				GeneralNumConstantCache.getValue("USE_MANUAL_1"))) {
			message.putShort(ErrorCode.NO_MANUAL);
			return message;
		}

		// 根据规则随机奖励
		Random rand = new Random();
		int totalWeight = TreasureConfigCache.getTreasureWeight();
		int randNum = rand.nextInt(totalWeight);
		int min = 0;
		int max = 0;
		int itemId = 0;
		int num = 0;
		for (Entry<Integer, TreasureConfig> entry : TreasureConfigCache.getMaps().entrySet()) {
			int itemid = entry.getKey();
			TreasureConfig config = entry.getValue();
			int weight = config.getWeight();
			min = max;
			max += weight;
			if (randNum >= min && randNum < max) {
				itemId = itemid;
				num = config.getNum();
				break;
			}

		}

		// 创建 派遣事件
		Build build = role.getBuild();
		Hero levyHero = role.getHeroMap().get(heroId);

		// 获得cd时间
		int cd = 7200 - (HeroUtils.getCaptainValue(levyHero) * 30);
		if (cd < 0)
			cd = 3;

		LevyInfo levyInfo = new LevyInfo();
		levyInfo.setType(InComeConstant.TYPE_FORGE);
		levyInfo.setStartTime(nowTime);
		levyInfo.setHeroId(heroId);
		levyInfo.setCd(cd);
		levyInfo.setValue(itemId);// 道具id
		levyInfo.setValueOther(num);// 数量

		build.getLevyMap().put(heroId, levyInfo);
		build.setChange(true);
		this.taskService.checkVisit(role, InComeConstant.TYPE_FORGE);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(levyInfo.getCd());
		return message;
	}

	/**
	 * 扩充玩家背包
	 * 
	 * @author xjd
	 */
	public Message addMaxBagNum(Role role, int number) {

		Message message = new Message();
		// message.setType(PropConstant.ADD_MAX_BACK_NUM);
		// // 检查格子数量是否合法
		// if (number <= PropConstant.INIT_NUM_BACK || number <
		// role.getMaxBagNum()) {
		// message.putShort(ErrorCode.ERR_NUM_BACK);
		// return message;
		// }
		// // 开启背包格子的等级过滤
		// LevelLimit limit = LevelLimitCache.getLevelLimitByLv(role.getLv());
		// if (number > limit.getBackNum()) {
		// message.putShort(ErrorCode.NO_LV);
		// return message;
		// }
		// // 计算花费
		// int finalCost = this.countCostAddBack(role.getMaxBagNum(), number);
		// // 检查玩家金币是否足够
		// if (role.getGold() < finalCost) {
		// message.putShort(ErrorCode.NO_GOLD);
		// return message;
		// }
		// // 扣除玩家金币
		// roleService.addRoleGold(role, -finalCost);
		// // 扩充玩家背包
		// role.setMaxBagNum((short) number);
		// role.setChange(true);
		//
		// message.putShort(ErrorCode.SUCCESS);
		// message.putInt(finalCost);
		return message;
	}

	/**
	 * 查询单个装备是否可互换
	 * 
	 * @return
	 */
	private boolean checkSingleDress(Role role, int heroId1, int heroId2, byte slot) {
		boolean b = true;
		Hero hero1 = role.getHeroMap().get(heroId1);
		Hero hero2 = role.getHeroMap().get(heroId2);
		// 指定装备位置的道具ID
		int propId1 = 0;
		int propId2 = 0;
		if (hero1.getEquipMap().containsKey(slot))
			propId1 = hero1.getEquipMap().get(slot);
		if (hero2.getEquipMap().containsKey(slot))
			propId2 = hero2.getEquipMap().get(slot);

		if (propId1 > 0 && propId2 > 0) { // 都装有道具
			Prop prop1 = role.getPropMap().get(propId1);
			Prop prop2 = role.getPropMap().get(propId2);
			EquipConfig equipConfig1 = EquipConfigCache.getEquipConfigById(prop1.getItemId());
			EquipConfig equipConfig2 = EquipConfigCache.getEquipConfigById(prop2.getItemId());
			if (hero1.getLv() < equipConfig2.getNeedLv() || hero2.getLv() < equipConfig1.getNeedLv()) {
				b = false;
			}
			return b;
		}
		if (propId1 > 0 && propId2 <= 0) { // 武将1装有道具而武将2没有
			Prop prop1 = role.getPropMap().get(propId1);
			EquipConfig equipConfig1 = EquipConfigCache.getEquipConfigById(prop1.getItemId());
			if (hero2.getLv() < equipConfig1.getNeedLv()) {
				b = false;
			}
			return b;
		}
		if (propId2 > 0 && propId1 <= 0) { // 武将2装有道具而武将1没有
			Prop prop2 = role.getPropMap().get(propId2);
			EquipConfig equipConfig2 = EquipConfigCache.getEquipConfigById(prop2.getItemId());
			if (hero1.getLv() < equipConfig2.getNeedLv()) {
				b = false;
			}
			return b;
		}
		return b;
	}

	/**
	 * 单个换装
	 * 
	 * @param role
	 * @param heroId1
	 * @param heroId2
	 * @param slot
	 */
	private void singleDressPri(Role role, int heroId1, int heroId2, byte slot) {
		Hero hero1 = role.getHeroMap().get(heroId1);
		Hero hero2 = role.getHeroMap().get(heroId2);

		// 指定装备位置的道具ID
		int propId1 = 0;
		int propId2 = 0;
		if (hero1.getEquipMap().containsKey(slot))
			propId1 = hero1.getEquipMap().get(slot);
		if (hero2.getEquipMap().containsKey(slot))
			propId2 = hero2.getEquipMap().get(slot);

		if (propId1 > 0 && propId2 > 0) {
			Prop prop1 = role.getPropMap().get(propId1);
			Prop prop2 = role.getPropMap().get(propId2);

			hero1.getEquipMap().remove(slot);
			hero2.getEquipMap().remove(slot);
			hero1.getEquipMap().put(slot, prop2.getId());
			hero2.getEquipMap().put(slot, prop1.getId());

			prop1.setUseID(hero2.getHeroId());
			prop2.setUseID(hero1.getHeroId());
			prop1.setChange(true);
			prop2.setChange(true);
		}
		if (propId1 > 0 && propId2 <= 0) {
			Prop prop1 = role.getPropMap().get(propId1);

			hero1.getEquipMap().remove(slot);
			hero2.getEquipMap().put(slot, prop1.getId());
			prop1.setUseID(hero2.getHeroId());
			prop1.setChange(true);
		}
		if (propId2 > 0 && propId1 <= 0) {
			Prop prop2 = role.getPropMap().get(propId2);
			hero2.getEquipMap().remove(slot);
			hero1.getEquipMap().put(slot, prop2.getId());
			prop2.setUseID(hero1.getHeroId());
			prop2.setChange(true);
		}
	}

	public Message singleDress(Role role, int heroId1, int heroId2, byte slot) {
		Message message = new Message();
		message.setType(PropConstant.SINGLE_DRESS);
		boolean b = this.checkSingleDress(role, heroId1, heroId2, slot);
		if (b == false) {
			message.putShort(ErrorCode.CAN_NOT_SINGLE_DRESS);
			return message;
		}

		// 兑换装备
		this.singleDressPri(role, heroId1, heroId2, slot);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(heroId1);
		message.putInt(heroId2);
		message.put(slot);
		return message;
	}

	// public int getEmptyBackPack(Role role) {
	// int num = 0;
	// int backPackNum = role.getBackPack().size();
	// int maxNum = role.getMaxBagNum();
	// num = maxNum - backPackNum;
	// return num;
	// }

	public boolean checkItemInBackPack(Role role, int itemId) {
		// boolean b = false;
		// Map<Integer, Prop> backPack = role.getBackPack();
		// for (Map.Entry<Integer, Prop> entry : backPack.entrySet()) {
		// Prop tempProp = entry.getValue();
		// if (tempProp.getItemId() == itemId && tempProp.getFunctionType() ==
		// PropConstant.FUNCTION_TYPE_2) {
		// b = true;
		// break;
		// }
		// }
		// return b;
		for (Prop prop : role.getPropMap().values()) {
			if (prop.getItemId() == itemId) {
				return true;
			}
		}
		return false;

	}

	public int sameItemAddNum(Role role, int itemId, int num) {
		// int id = 0;
		// Map<Integer, Prop> backPack = role.getBackPack();
		// for (Map.Entry<Integer, Prop> entry : backPack.entrySet()) {
		// Prop tempProp = entry.getValue();
		// if (tempProp.getItemId() == itemId && tempProp.getFunctionType() ==
		// PropConstant.FUNCTION_TYPE_2) {
		// tempProp.setNum((short) (tempProp.getNum() + num));
		// id = tempProp.getId();
		// tempProp.setChange(true);
		// break;
		// }
		// }
		// return id;

		for (Prop prop : role.getPropMap().values()) {
			if (prop.getItemId() == itemId) {
				short val = (short) (prop.getNum() + num) > PropConstant.MAX_HEAP_VALUE ? PropConstant.MAX_HEAP_VALUE : (short) (prop
						.getNum() + num);
				val = val < 0 ? 0 : val;
				prop.setNum(val);
				prop.setChange(true);
				return prop.getId();
			}
		}
		return 0;
	}

	/**
	 * 根据类型选择不同的随机规则
	 * 
	 * @param type
	 * @return
	 */
	private EquiptPrefix chooesPrefix(int type) {
		int limit = 0;
		EquiptPrefix eq = null;
		Map<Integer, EquiptPrefix> choose = null;
		switch (type) {
		case PropConstant.ITEM_TYPE_1:
			choose = EquiptPrefixCache.getJz_1_use();
			break;
		case PropConstant.ITEM_TYPE_2:
			choose = EquiptPrefixCache.getFn_2_use();
			break;
		case PropConstant.ITEM_TYPE_3:
			choose = EquiptPrefixCache.getYy_3_use();
			break;
		case PropConstant.ITEM_TYPE_4:
			choose = EquiptPrefixCache.getSs_4_use();
			break;
		default:
			choose = EquiptPrefixCache.getAll();
			break;
		}
		if (choose == null) {
			return null;
		}
		for (EquiptPrefix x : choose.values()) {
			limit += x.getWeight();
		}
		Random rr = new Random();
		int r = rr.nextInt(limit);
		int min = 0;
		for (EquiptPrefix x : choose.values()) {
			if (min <= r && r < min + x.getWeight()) {
				eq = x;
				break;
			}
			min += x.getWeight();
		}
		return eq;
	}

	/**
	 * 查询装备品质需求的精炼石数量
	 * 
	 * @param quality
	 * @return
	 */
	private int chooesNumStone(int quality) {
		int need = 0;
		switch (quality) {
		case PropConstant.QUALITY_TYPE_1:
			return need;
		case PropConstant.QUALITY_TYPE_2:
			need = 1;
			return need;
		case PropConstant.QUALITY_TYPE_3:
			need = 1;
			return need;
		case PropConstant.QUALITY_TYPE_4:
			need = 1;
			return need;
		default:
			return need;
		}
	}

	/**
	 * 查询装备品质的最高词缀等级
	 * 
	 * @param quality
	 * @return 每日调度 开始天数每天递减
	 * 
	 * @author xjd
	 */
	private int checkMax(int quality) {
		switch (quality) {
		case PropConstant.QUALITY_TYPE_2:
			return PropConstant.USE_0;
		case PropConstant.QUALITY_TYPE_3:
			return PropConstant.ITEM_TYPE_3;
		case PropConstant.QUALITY_TYPE_4:
			return PropConstant.ITEM_TYPE_5;
		case PropConstant.QUALITY_TYPE_5:
			return PropConstant.ITEM_TYPE_10;
		default:
			return PropConstant.USE_0;
		}
	}

	private int getSid(Role role) {
		for (Prop x : role.getPropMap().values()) {
			if (x.getItemId() == PropConstant.STONE_ID)
				return x.getId();
		}
		return PropConstant.SLOT_0;
	}

	/*----------------------------------------------------------------------------------
	 * 			此处逻辑可以优化														   -
	 *----------------------------------------------------------------------------------
	 */
	/**
	 * 更换装备改变面板属性
	 * 
	 * @param prop new
	 * @param hero
	 * @param prop2 old
	 */
	private void changeFightValue(Prop prop, Hero hero, Prop prop2) {
		// 计算新装备的属性
		int atkNew = HeroConstant.INT_0, defNew = HeroConstant.INT_0, zatkNew = HeroConstant.INT_0, zdefNew = HeroConstant.INT_0, catkNew = HeroConstant.INT_0, cdefNew = HeroConstant.INT_0, addHurtNew = HeroConstant.INT_0, rmHurtNew = HeroConstant.INT_0, bingjiNew = HeroConstant.INT_0, hpNew = HeroConstant.INT_0;
		if (prop != null) {
			EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());
			if (equipConfig == null)
				return;
			EquiptPrefix prefixNew = EquiptPrefixCache.getEquipConfigById(prop.getPrefixId());
			atkNew += equipConfig.getAtk() + equipConfig.getAtkStrongValue() *(prop.getLv() - 1);
			defNew += equipConfig.getDef() + equipConfig.getDefStrongValue() * (prop.getLv() - 1);
			zatkNew += equipConfig.getZatk() + equipConfig.getZatkStrongValue() * (prop.getLv() - 1);
			zdefNew += equipConfig.getZdef() + equipConfig.getZdefStrongValue() * (prop.getLv() - 1);
			catkNew += equipConfig.getCatk() + equipConfig.getCatkStrongValue() * (prop.getLv() - 1);
			cdefNew += equipConfig.getCdef() + equipConfig.getCdefStrongValue() * (prop.getLv() - 1);
			hpNew += equipConfig.getHp() + equipConfig.getHpStrongValue() * (prop.getLv() - 1);
			if (prefixNew != null) {
				addHurtNew += prefixNew.getAddHurt();
				rmHurtNew += prefixNew.getRmHurt();
				bingjiNew += prefixNew.getBj();
				hpNew += prefixNew.getHp();
			}
		}
		// 计算旧装备的属性
		int atkOld = HeroConstant.INT_0, defOld = HeroConstant.INT_0, zatkOld = HeroConstant.INT_0, zdefOld = HeroConstant.INT_0, catkOld = HeroConstant.INT_0, cdefOld = HeroConstant.INT_0, addHurtOld = HeroConstant.INT_0, rmHurtOld = HeroConstant.INT_0, bingjiOld = HeroConstant.INT_0, hpOld = HeroConstant.INT_0;
		// 如果存在旧装备计算属性并扣除
		if (prop2 != null) {
			EquipConfig equipConfigOld = EquipConfigCache.getEquipConfigById(prop2.getItemId());
			if (equipConfigOld == null)
				return;
			EquiptPrefix prefixOld = EquiptPrefixCache.getEquipConfigById(prop2.getPrefixId());
			atkOld += equipConfigOld.getAtk() + equipConfigOld.getAtkStrongValue() * (prop2.getLv() - 1);
			defOld += equipConfigOld.getDef() + equipConfigOld.getDefStrongValue() * (prop2.getLv() - 1);
			zatkOld += equipConfigOld.getZatk() + equipConfigOld.getZatkStrongValue() * (prop2.getLv() - 1);
			zdefOld += equipConfigOld.getZdef() + equipConfigOld.getZdefStrongValue() * (prop2.getLv() - 1);
			catkOld += equipConfigOld.getCatk() + equipConfigOld.getCatkStrongValue() * (prop2.getLv() - 1);
			cdefOld += equipConfigOld.getCdef() + equipConfigOld.getCdefStrongValue() * (prop2.getLv() - 1);
			hpOld += equipConfigOld.getHp() + equipConfigOld.getHpStrongValue() * (prop2.getLv() - 1);
			if (prefixOld != null) {
				addHurtOld += prefixOld.getAddHurt();
				rmHurtOld += prefixOld.getRmHurt();
				bingjiOld += prefixOld.getBj();
				hpOld += prefixOld.getHp();
			}
		}

		// 最终数值
		hero.setArmsNum(hero.getArmsNum() + hpNew - hpOld);
		hero.setGeneralAttrck(hero.getGeneralAttrck() + atkNew - atkOld);
		hero.setGeneralDefence(hero.getGeneralDefence() + defNew - defOld);
		hero.setPowerAttack(hero.getPowerAttack() + zatkNew - zatkOld);
		hero.setPowerDefence(hero.getPowerDefence() + zdefNew - zdefOld);
		hero.setMagicalAttack(hero.getMagicalAttack() + catkNew - catkOld);
		hero.setMagicalDefence(hero.getMagicalDefence() + cdefNew - cdefOld);
		hero.setAddHurt(hero.getAddHurt() + addHurtNew - addHurtOld);
		hero.setRmHurt(hero.getRmHurt() + rmHurtNew - rmHurtOld);
		hero.setBingji(hero.getBingji() + bingjiNew - bingjiOld);

		
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
	 * 词缀更改面板属性
	 * 
	 * @param newId
	 * @param hero
	 * @param oldId
	 */
	private void changeFightValue2(int newId, Hero hero, int oldId) {
		int addHurtNew = HeroConstant.INT_0, rmHurtNew = HeroConstant.INT_0, bingjiNew = HeroConstant.INT_0, hpNew = HeroConstant.INT_0;
		EquiptPrefix prefixNew = EquiptPrefixCache.getEquipConfigById(newId);
		if (prefixNew != null) {
			addHurtNew += prefixNew.getAddHurt();
			rmHurtNew += prefixNew.getRmHurt();
			bingjiNew += prefixNew.getBj();
			hpNew += prefixNew.getHp();
		}

		int addHurtOld = HeroConstant.INT_0, rmHurtOld = HeroConstant.INT_0, bingjiOld = HeroConstant.INT_0, hpOld = HeroConstant.INT_0;
		EquiptPrefix prefixOld = EquiptPrefixCache.getEquipConfigById(oldId);
		if (prefixOld != null) {
			addHurtOld += prefixOld.getAddHurt();
			rmHurtOld += prefixOld.getRmHurt();
			bingjiOld += prefixOld.getBj();
			hpOld += prefixOld.getHp();
		}
		hero.setArmsNum(hero.getArmsNum() + hpNew - hpOld);
		hero.setAddHurt(hero.getAddHurt() + addHurtNew - addHurtOld);
		hero.setRmHurt(hero.getRmHurt() + rmHurtNew - rmHurtOld);
		hero.setBingji(hero.getBingji() + bingjiNew - bingjiOld);

		
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
	 * 强化装备更改战力
	 * 
	 * @param prop
	 * @param hero
	 * @author xjd
	 */
	private void changeFightValue3(Prop prop, Hero hero) {
		// 计算新装备的属性
		int atkNew = HeroConstant.INT_0, defNew = HeroConstant.INT_0, zatkNew = HeroConstant.INT_0, zdefNew = HeroConstant.INT_0, catkNew = HeroConstant.INT_0, cdefNew = HeroConstant.INT_0, hpNew = HeroConstant.INT_0;
		if (prop != null) {
			EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());
			if (equipConfig == null)
				return;
			atkNew += equipConfig.getAtk() + equipConfig.getAtkStrongValue() * prop.getLv();
			defNew += equipConfig.getDef() + equipConfig.getDefStrongValue() * prop.getLv();
			zatkNew += equipConfig.getZatk() + equipConfig.getZatkStrongValue() * prop.getLv();
			zdefNew += equipConfig.getZdef() + equipConfig.getZdefStrongValue() * prop.getLv();
			catkNew += equipConfig.getCatk() + equipConfig.getCatkStrongValue() * prop.getLv();
			cdefNew += equipConfig.getCdef() + equipConfig.getCdefStrongValue() * prop.getLv();
			hpNew += equipConfig.getHp() + equipConfig.getHpStrongValue() * prop.getLv();
		}
		// 计算旧装备的属性
		int atkOld = HeroConstant.INT_0, defOld = HeroConstant.INT_0, zatkOld = HeroConstant.INT_0, zdefOld = HeroConstant.INT_0, catkOld = HeroConstant.INT_0, cdefOld = HeroConstant.INT_0, hpOld = HeroConstant.INT_0;
		// 如果存在旧装备计算属性并扣除
		if (prop != null) {
			EquipConfig equipConfigOld = EquipConfigCache.getEquipConfigById(prop.getItemId());
			if (equipConfigOld == null)
				return;
			atkOld += equipConfigOld.getAtk() + equipConfigOld.getAtkStrongValue() * (prop.getLv() - 1);
			defOld += equipConfigOld.getDef() + equipConfigOld.getDefStrongValue() * (prop.getLv() - 1);
			zatkOld += equipConfigOld.getZatk() + equipConfigOld.getZatkStrongValue() * (prop.getLv() - 1);
			zdefOld += equipConfigOld.getZdef() + equipConfigOld.getZdefStrongValue() * (prop.getLv() - 1);
			catkOld += equipConfigOld.getCatk() + equipConfigOld.getCatkStrongValue() * (prop.getLv() - 1);
			cdefOld += equipConfigOld.getCdef() + equipConfigOld.getCdefStrongValue() * (prop.getLv() - 1);
			hpOld += equipConfigOld.getHp() + equipConfigOld.getHpStrongValue() * (prop.getLv() - 1);
		}

		// 最终数值
		hero.setArmsNum(hero.getArmsNum() + hpNew - hpOld);
		hero.setGeneralAttrck(hero.getGeneralAttrck() + atkNew - atkOld);
		hero.setGeneralDefence(hero.getGeneralDefence() + defNew - defOld);
		hero.setPowerAttack(hero.getPowerAttack() + zatkNew - zatkOld);
		hero.setPowerDefence(hero.getPowerDefence() + zdefNew - zdefOld);
		hero.setMagicalAttack(hero.getMagicalAttack() + catkNew - catkOld);
		hero.setMagicalDefence(hero.getMagicalDefence() + cdefNew - cdefOld);
		
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

	@Override
	public Message showStrengthen(Role role, int equipId) {
		Message message = new Message();
		message.setType(PropConstant.SHOW_STRENGTHEN);
		Prop prop = role.getPropMap().get(equipId);
		byte type = prop.getFunctionType();
		if (type == PropConstant.FUNCTION_TYPE_2) {
			return null;
		}

		int ironMaxNum = 0;

		// 获得铁的总数量
		Prop iron = role.getConsumePropMap().get(PropConstant.PROP_IRON);
		if (iron != null) {
			ironMaxNum = iron.getNum();
		}

		// 获得花费
		// 取出装备配置表
		EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());
		// 本次强化花费
		StrengthenConfig strengthenConfig = StrengthenConfigCache.getStrengthenConfigByLv((int)prop.getLv());
		int cost = strengthenConfig.getCost(equipConfig.getQuality());

		// 获得成功概率
		BuildInfo buildInfo = role.getBuild().getAttachHeroMap().get(InComeConstant.TYPE_FORGE);
		Integer attachHeroId = buildInfo != null ? buildInfo.getHeroId() : null;
		Hero attachHero = null;
		if (attachHeroId != null) {
			attachHero = role.getHeroMap().get(attachHeroId);
		}

		double rate = this.getStrengthSuccessRate(role, prop, 0, attachHero);
		int showRate = this.getShowStrengthSuccessRate(rate);
		// 成功率100%所需要的铁块数量
		int needIronTo100PercentNum = this.getNeedIronTo100PercentNum(role, prop, attachHero);

		int maxUseIronNum = ironMaxNum < needIronTo100PercentNum ? ironMaxNum : needIronTo100PercentNum;
		message.putInt(cost);
		message.putInt(showRate);
		message.putInt(maxUseIronNum);

		return message;
	}

	@Override
	public Message showAddIronStrengthen(Role role, int equipId, int ironNum) {
		Message message = new Message();
		message.setType(PropConstant.SHOW_ADD_IRON_STRENGTHEN);
		Prop prop = role.getPropMap().get(equipId);
		byte type = prop.getFunctionType();
		if (type == PropConstant.FUNCTION_TYPE_2) {
			return null;
		}

		// 获得成功概率
		BuildInfo buildInfo = role.getBuild().getAttachHeroMap().get(InComeConstant.TYPE_FORGE);
		Integer attachHeroId = buildInfo != null ? buildInfo.getHeroId() : null;
		Hero hero = null;
		if (attachHeroId != null) {
			hero = role.getHeroMap().get(attachHeroId);
		}
		double rate = this.getStrengthSuccessRate(role, prop, ironNum, hero);
		int showRate = this.getShowStrengthSuccessRate(rate);

		message.putInt(showRate);

		return message;
	}

	/**
	 * 成功率100%所需要的铁块数量
	 * 
	 * @param prop
	 * @param attachHero
	 * @return
	 * @author wcy 2016年1月13日
	 */
	private int getNeedIronTo100PercentNum(Role role, Prop prop, Hero attachHero) {
		short lv = prop.getLv();
		StrengthSuccessConfig strengthSuccessConfig = StrengthSuccessConfigCache.getStrengthSuccessConfigByLv(lv);
		// 获得成功概率
		double addRate = strengthSuccessConfig.getAddRate()/1000.0;
		int ironCount = strengthSuccessConfig.getIron();
		// 成功率100%所需要的铁块数量
		int needIronTo100PercentNum = (int) (Math.ceil((PropConstant.MAX_STRENGTHEN - this.getStrengthSuccessRate(role,
				prop, 0, attachHero)) / (addRate * ironCount)));
		return needIronTo100PercentNum;
	}

	/**
	 * 获得强化成功率
	 * 
	 * @param role
	 * @param prop
	 * @param ironNum
	 * @return
	 * @author wcy 2016年1月13日
	 */
	private double getStrengthSuccessRate(Role role, Prop prop, int ironNum, Hero attachHero) {
		short lv = prop.getLv();
		StrengthSuccessConfig strengthSuccessConfig = StrengthSuccessConfigCache.getStrengthSuccessConfigByLv(lv);
		// 获得成功概率
		double rate = strengthSuccessConfig.getRate();

		int heroAddRate = inComeService.getPropStrengthAddDelta(attachHero);
//		int addRate = strengthSuccessConfig.getAddRate();
		double addRate = strengthSuccessConfig.getAddRate()/1000.0;
		int ironCount = strengthSuccessConfig.getIron();
		int rateUnitNum = ironNum / ironCount;

		rate = rate / 100.0 + rateUnitNum * addRate + heroAddRate;
		// rate = rate > PropConstant.MAX_STRENGTHEN ?
		// PropConstant.MAX_STRENGTHEN : rate;
		// rate = rate < PropConstant.MIN_STRENGTHEN ?
		// PropConstant.MIN_STRENGTHEN : rate;

		return rate;
	}

	@Override
	public Message showEquip(Role role) {
		Message message = new Message(PropConstant.SHOW_EQUIP);
		Map<Integer, Prop> propMap = role.getPropMap();
		ArrayList<Prop> array = new ArrayList<>();
		for (Prop prop : propMap.values()) {
			if (prop.getUseID() == 0 && prop.getFunctionType() == PropConstant.FUNCTION_TYPE_1) {
				array.add(prop);
			}
		}
		int size = array.size();
		message.putInt(size);
		for (Prop equip : array) {
			message.putInt(equip.getId());
			message.putInt(equip.getItemId());
			message.putInt(equip.getLv());
			message.putInt(equip.getPrefixId());
			message.putInt(equip.getSlotId());
		}
		return message;
	}

	@Override
	public void initPropFightValue(Prop prop) {
		if (prop.getFunctionType() == PropConstant.FUNCTION_TYPE_2) {
			return;
		}

		EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());

		double hp = (equipConfig.getHp() + prop.getLv() * equipConfig.getHpStrongValue()) * .004;// 兵力

		double atk = equipConfig.getAtk() + prop.getLv() * equipConfig.getAtkStrongValue();// 普攻
		double catk = equipConfig.getCatk() + prop.getLv() * equipConfig.getCatkStrongValue();// 计策攻击
		double zatk = equipConfig.getZatk() + prop.getLv() * equipConfig.getZatkStrongValue();// 战力攻击
		double atkValue = (atk + catk + zatk) * .05;

		double def = equipConfig.getDef() + prop.getLv() * equipConfig.getDefStrongValue();
		double zdef = equipConfig.getZdef() + prop.getLv() * equipConfig.getZdefStrongValue();
		double cdef = equipConfig.getCdef() + prop.getLv() * equipConfig.getCdefStrongValue();
		double defValue = (def + zdef + cdef) * .1;

		EquiptPrefix equipPrefix = EquiptPrefixCache.getEquipConfigById(prop.getPrefixId());
		double prefixFightValue = 0.0;
		if (equipPrefix != null)
			prefixFightValue = (equipPrefix.getHp() + equipPrefix.getRmHurt() + equipPrefix.getAddHurt() + equipPrefix
					.getBj()) * 2.5f;

		int fightValue = (int) (atkValue + defValue + prefixFightValue);

		prop.setFightValue(fightValue);
	}

	/**
	 * 获得显示的成功率
	 * 
	 * @param rate
	 * @return
	 * @author wcy 2016年2月1日
	 */
	private int getShowStrengthSuccessRate(double rate) {
		int showRate = (int) rate;

		if (rate > PropConstant.MAX_STRENGTHEN) {
			showRate = PropConstant.MAX_STRENGTHEN;
		} else if (rate < PropConstant.MIN_STRENGTHEN) {
			showRate = PropConstant.MIN_STRENGTHEN;
		}
		return showRate;
	}

}
