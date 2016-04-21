package byCodeGame.game.moudle.barrack.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.ArmsConfigCache;
import byCodeGame.game.cache.file.ArmyScienceCache;
import byCodeGame.game.cache.file.BarrackUnLockCache;
import byCodeGame.game.cache.file.BuildConfigCache;
import byCodeGame.game.cache.file.BuildingUnlockCache;
import byCodeGame.game.cache.file.ChapterArmsConfigCache;
import byCodeGame.game.cache.file.ChapterConfigCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.MainBuildingConfigCache;
import byCodeGame.game.cache.file.VipConfigCache;
import byCodeGame.game.cache.local.RankCache;
import byCodeGame.game.cache.local.RankCache.RankType;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Barrack;
import byCodeGame.game.entity.bo.Build;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.ArmsConfig;
import byCodeGame.game.entity.file.ArmyScience;
import byCodeGame.game.entity.file.BarrackUnLock;
import byCodeGame.game.entity.file.BuildConfig;
import byCodeGame.game.entity.file.BuildingUnlock;
import byCodeGame.game.entity.file.ChapterArmsConfig;
import byCodeGame.game.entity.file.ChapterConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.MainBuildingConfig;
import byCodeGame.game.entity.file.VipConfig;
import byCodeGame.game.entity.po.BarrackData;
import byCodeGame.game.entity.po.BuildInfo;
import byCodeGame.game.entity.po.BuildQueue;
import byCodeGame.game.entity.po.OffensiveData;
import byCodeGame.game.entity.po.Rank;
import byCodeGame.game.moudle.barrack.BarrackConstant;
import byCodeGame.game.moudle.inCome.InComeConstant;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.moudle.prop.PropConstant;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.moudle.rank.service.RankService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.PVPUitls;
import byCodeGame.game.util.Utils;
import cn.bycode.game.battle.core.Battle;
import cn.bycode.game.battle.data.FormationData;
import cn.bycode.game.battle.data.ResultData;
import cn.bycode.game.battle.data.TroopData;

public class BarrackServiceImpl implements BarrackService {
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

	private PropService propService;

	public void setPropService(PropService propService) {
		this.propService = propService;
	}

	private RankService rankService;

	public void setRankService(RankService rankService) {
		this.rankService = rankService;
	}

	public Message buildBarrack(Role role, Byte buildId, byte type) {
		Message message = new Message();
		message.setType(BarrackConstant.BUILD_BARRACK);

		Map<Byte, BarrackData> buildLvMap = role.getBarrack().getBuildLvMap();
		BarrackData barrackData = buildLvMap.get(buildId);
		if (barrackData.getType() > 0) { // 该训练位已有建筑
			return null;
		}

		if (type <= (byte) 0 || type > (byte) 4) {
			return null;
		}

		BuildConfig buildConfig = BuildConfigCache.getBuildConfig(0, (byte) 3);
		if (role.getMoney() < buildConfig.getUpMoney()) { // 银币币不够
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}
		if (role.getFood() < buildConfig.getUpFood()) { // 粮食不够
			message.putShort(ErrorCode.NO_FOOD);
			return message;
		}

		this.refreshQueue(role);
		// 可用的建筑队列ID
		byte queueId = this.getOpenQueue(role);
		if (queueId <= 0) { // 没有空闲的建筑队列
			message.putShort(ErrorCode.NO_BUILD_QUEUE);
			return message;
		}

		// 升级成功
		barrackData.setLv(BarrackConstant.SHORT_1);
		barrackData.setType(type);
		role.getBarrack().setChange(true);
		this.addQueueTime(role, queueId, buildConfig.getUpTime());
		roleService.addRoleMoney(role, -buildConfig.getUpMoney());
		roleService.addRoleFood(role, -buildConfig.getUpFood());
		role.setChange(true);

		this.taskService.checkBuildUpTask(role, (byte) 3, (byte) 3, (byte) barrackData.getLv());

		message.putShort(ErrorCode.SUCCESS);
		message.put(buildId);
		message.put(type);
		message.putInt(buildConfig.getUpMoney());
		message.putInt(buildConfig.getUpFood());
		return message;
	}

	/**
	 * 获取空闲的建筑队列 如果为0代表当前没有建筑队列可用
	 * 
	 * @param role
	 * @return
	 */
	private byte getOpenQueue(Role role) {
		byte id = 0;
		Map<Byte, BuildQueue> queueMap = role.getBarrack().getQueueMap();
		for (Map.Entry<Byte, BuildQueue> entry : queueMap.entrySet()) {
			BuildQueue tempBuildQueue = entry.getValue();
			if (tempBuildQueue.getOpen() <= 0) {
				id = (byte) tempBuildQueue.getId();
				break;
			}
		}
		return id;
	}

	/**
	 * 刷新兵营建筑队列
	 * 
	 * @param role
	 */
	private void refreshQueue(Role role) {
		long nowTime = System.currentTimeMillis() / 1000;
		Map<Byte, BuildQueue> queueMap = role.getBarrack().getQueueMap();

		for (Map.Entry<Byte, BuildQueue> entry : queueMap.entrySet()) {
			BuildQueue tempBuildQueue = entry.getValue();
			// 过了多少时间
			long overTime = nowTime - tempBuildQueue.getLastUpTime();
			long nowCdTime = (long) tempBuildQueue.getTime() - overTime;
			if (nowCdTime < 0) {
				nowCdTime = 0;
				tempBuildQueue.setOpen(BarrackConstant.OPEN_QUEUE);
			}
			tempBuildQueue.setTime((int) nowCdTime);
			tempBuildQueue.setLastUpTime(nowTime);
		}
		role.getBarrack().setChange(true);
	}

	/**
	 * 队列增加CD时间 同时判定该队列是否关闭
	 * 
	 * @param role
	 * @param queueId
	 * @param time
	 */
	private void addQueueTime(Role role, byte queueId, int time) {
		long nowTime = System.currentTimeMillis() / 1000;
		Map<Byte, BuildQueue> queueMap = role.getBarrack().getQueueMap();
		BuildQueue buildQueue = queueMap.get(queueId);
		buildQueue.setTime(buildQueue.getTime() + time);
		if (buildQueue.getTime() >= BarrackConstant.MAX_QUEUE_TIME) {
			buildQueue.setOpen(BarrackConstant.CLOSE_QUEUE);
		}
		buildQueue.setLastUpTime(nowTime);

		role.getBarrack().setChange(true);
	}

	public Message upBarrack(Role role, byte id) {
		Message message = new Message();
		message.setType(BarrackConstant.UP_BARRACK);
		Map<Byte, BarrackData> buildMap = role.getBarrack().getBuildLvMap();
		BarrackData barrackData = buildMap.get(id);
		if (barrackData == null)
			return null;

		BuildConfig buildConfig = BuildConfigCache.getBuildConfig((int) barrackData.getLv(), (byte) 3);
		if (buildConfig == null)
			return null;

		int needMoney = buildConfig.getUpMoney();
		if (role.getMoney() < needMoney) { // 金币不够
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}

		byte queueId = this.getOpenQueue(role);
		this.refreshQueue(role);
		if (queueId <= (byte) 0) { // 没有空闲的建筑队列
			message.putShort(ErrorCode.NO_BUILD_QUEUE);
			return message;
		}

		// 升级成功
		roleService.addRoleMoney(role, -needMoney);
		role.setChange(true);

		this.addQueueTime(role, queueId, buildConfig.getUpTime());

		barrackData.setLv((short) (barrackData.getLv() + 1));
		role.getBarrack().setChange(true);
		this.taskService.checkBuildUpTask(role, (byte) 3, (byte) barrackData.getLv());

		message.putShort(ErrorCode.SUCCESS);
		message.put(id);
		message.putInt(needMoney);
		message.put(queueId);
		message.putInt(buildConfig.getUpTime());
		return message;
	}

	public Message sellBarrack(Role role, byte id) {
		Message message = new Message();
		message.setType(BarrackConstant.SELL_BARRACK);
		BarrackData barrackData = role.getBarrack().getBuildLvMap().get(id);
		if (barrackData == null)
			return null;
		BuildConfig buildConfig = BuildConfigCache.getBuildConfig((int) barrackData.getLv(),
				BarrackConstant.BARRACK_TYPE);
		if (buildConfig == null)
			return null;

		// 出售成功
		roleService.addRoleMoney(role, buildConfig.getSellMoney());
		role.setChange(true);
		barrackData.setLv(BarrackConstant.LV_SELL);
		barrackData.setType(BarrackConstant.TYPE_SELL);
		role.getBarrack().setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		message.put(id);
		message.putInt(buildConfig.getSellMoney());
		return message;
	}

	public Message changeBarrack(Role role, byte id, byte type) {
		Message message = new Message();
		message.setType(BarrackConstant.CHANGE_BARRACK);

		BarrackData barrackData = role.getBarrack().getBuildLvMap().get(id);
		if (barrackData == null)
			return null;
		BuildConfig buildConfig = BuildConfigCache.getBuildConfig((int) barrackData.getLv(),
				BarrackConstant.BARRACK_TYPE);
		if (buildConfig == null)
			return null;

		int needMoney = buildConfig.getChangeMoney();
		if (role.getGold() < needMoney) { // 没有足够金币
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}

		// 更改成功
		roleService.addRoleMoney(role, -needMoney);
		role.setChange(true);

		barrackData.setType(type);
		role.getBarrack().setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		message.put(id);
		message.put(type);
		message.putInt(needMoney);
		return message;
	}

	public int getMaxCapacity(Role role, byte type) {
		int num = 0;
		Map<Byte, BarrackData> buildMap = role.getBarrack().getBuildLvMap();
		for (Map.Entry<Byte, BarrackData> entry : buildMap.entrySet()) {
			BarrackData tempBarrackData = entry.getValue();
			BuildConfig tempBuildConfig = BuildConfigCache.getBuildConfig(tempBarrackData.getLv(), (byte) 3);
			if (tempBarrackData.getType() == type) {
				// num += tempBuildConfig.getCapacity();
			}
		}
		return num;
	}

	public int getNowArmsNum(Role role, byte type) {
		int num = 0;
		Map<Integer, Integer> troopMap = role.getBarrack().getTroopMap();
		for (Map.Entry<Integer, Integer> entry : troopMap.entrySet()) {
			int tempNum = entry.getValue();
			int tempId = entry.getKey();
			ArmsConfig tempArmsConfig = ArmsConfigCache.getArmsConfigById(tempId);
			if ((byte) tempArmsConfig.getFunctionType() == type) {
				num += tempNum;
			}
		}

		return num;
	}

	public Message recruitArms(Role role, int armsId, int num) {
		Message message = new Message();
		message.setType(BarrackConstant.RECRUIT_ARMS);
		ArmsConfig armsConfig = ArmsConfigCache.getArmsConfigById(armsId);
		if (armsConfig == null)
			return null;
		// 最大可存储兵力
		int maxCapacity = this.getMaxCapacity(role, (byte) armsConfig.getFunctionType());
		// 当前此兵种类型兵力
		int nowArmsNum = this.getNowArmsNum(role, (byte) armsConfig.getFunctionType());

		// 判定当前数量和招募后数量是否大于最大值
		if (nowArmsNum >= maxCapacity || nowArmsNum + num > maxCapacity) {
			message.putShort(ErrorCode.MAX_ARMS);
			return message;
		}

		int needFood = 0;
		int needMoney = 0;

		if (role.getFood() < needFood) { // 粮食不足
			message.putShort(ErrorCode.NO_FOOD);
			return message;
		}
		if (role.getMoney() < needMoney) { // 银币不足
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}

		short maxBuildLv = this.getMaxLvBarrack(role, (byte) armsConfig.getFunctionType());
		BarrackUnLock barrackUnLock = BarrackUnLockCache.getBarrackUnLock((byte) armsConfig.getFunctionType(),
				maxBuildLv);
		if (!barrackUnLock.getOpenArmsIdSet().contains(armsId)) { // 判定当前建筑等级是否可以购买该兵种
			return null;
		}

		// 招募成功
		role.getBarrack().addArmsNumById(armsId, num);
		role.getBarrack().setChange(true);
		roleService.addRoleMoney(role, -needMoney);
		roleService.addRoleFood(role, -needMoney);
		role.setChange(true);

		this.taskService.checkRecruitTask(role, (byte) 1, num);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(armsId);
		message.put((byte) armsConfig.getFunctionType());
		message.putInt(num);
		message.putInt(needMoney);

		return message;
	}

	/**
	 * 获取指定类型最大的建筑等级
	 * 
	 * @return
	 */
	private short getMaxLvBarrack(Role role, byte type) {
		short lv = 0;
		Map<Byte, BarrackData> buildLvMap = role.getBarrack().getBuildLvMap();
		for (Map.Entry<Byte, BarrackData> entry : buildLvMap.entrySet()) {
			BarrackData tempBarrackData = entry.getValue();
			if (tempBarrackData.getType() == type && tempBarrackData.getLv() > lv) {
				lv = tempBarrackData.getLv();
			}
		}
		return lv;
	}

	public Message getBarrackData(Role role) {
		Message message = new Message();
		message.setType(BarrackConstant.GET_BARRACK_DATA);
		Barrack barrack = role.getBarrack();
		message.put((byte) barrack.getBuildLvMap().size());
		for (Map.Entry<Byte, BarrackData> entry : barrack.getBuildLvMap().entrySet()) {
			BarrackData tempBarrackData = entry.getValue();
			message.put(tempBarrackData.getId());
			message.put(tempBarrackData.getType());
			message.putShort(tempBarrackData.getLv());
		}
		// 队列
		this.refreshQueue(role);
		message.put((byte) barrack.getQueueMap().size());
		for (Map.Entry<Byte, BuildQueue> entry1 : barrack.getQueueMap().entrySet()) {
			BuildQueue tempBuildQueue = entry1.getValue();
			message.put((byte) tempBuildQueue.getId());
			message.putInt(tempBuildQueue.getTime());
			message.put(tempBuildQueue.getOpen());
		}

		return message;
	}

	public Message getTroopsData(Role role) {
		Message message = new Message();
		message.setType(BarrackConstant.GET_TROOPS_DATA);
		Map<Integer, Integer> troopsMap = role.getBarrack().getTroopMap();
		message.put((byte) troopsMap.size());
		for (Map.Entry<Integer, Integer> entry : troopsMap.entrySet()) {
			int id = entry.getKey();
			int num = entry.getValue();
			message.putInt(id);
			message.putInt(num);
		}
		return message;
	}

	@Override
	public Message showMakeExploit(Role role) {
		Message message = new Message();
		message.setType(BarrackConstant.SHOW_MAKE_EXPLOIT);
		Build build = role.getBuild();
		Short campLv = build.getBuildLvMap().get(InComeConstant.TYPE_CAMP);
		if(campLv == null){
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		int heroId = build.getAttachHeroMap().get(InComeConstant.TYPE_CAMP).getHeroId();
		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(InComeConstant.TYPE_CAMP,
				campLv);
		int cdTime = mainBuildingConfig.getCd();

		long nowTime = System.currentTimeMillis() / 1000;
		this.refreshGetExploit(role, nowTime);

		int inCome = 0;
		int remainTime = 0;
		if (heroId != 0) {// 配属武将不能为0
			// 剩余时间
			Map<Byte, Long> lastIncomeTimeMap = build.getBuildLastIncomeTimeMap();
			long startTime = lastIncomeTimeMap.get(InComeConstant.TYPE_CAMP);
			remainTime = (int) (cdTime - (nowTime - startTime) % cdTime);

			// 每小时产量
			inCome = inComeService.getHourIncomeValue(role, InComeConstant.TYPE_CAMP);
		}

		// 当前战功数量
		int exploit = role.getExploit();

		message.putInt(remainTime);
		message.putInt(inCome);
		message.putInt(exploit);

		return message;
	}

	@Override
	public void refreshGetExploit(Role role, long nowTime) {
//		byte type = InComeConstant.TYPE_CAMP;
//		Build build = role.getBuild();
//		Short barrackLv = build.getBuildLvMap().get(type);
//		if(barrackLv == null) return;
//		
////		BuildInfo buildInfo = build.getAttachHeroMap().get(type);
////		int attachHeroId = 0;
////		try{
////			attachHeroId = buildInfo.getHeroId();
////		}catch(NullPointerException e){
////			attachHeroId = 0;
////		}		
//		Map<Byte, Long> lastIncomeTimeMap = build.getBuildLastIncomeTimeMap();		
//		
////		// 没有武将
////		if (attachHeroId == InComeConstant.NO_HERO) {
////			lastIncomeTimeMap.put(type, nowTime);
////			return;
////		}
//		
//		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(type,
//				barrackLv);
//
//		int cdTime = mainBuildingConfig.getCd();
//
//		int inCome = inComeService.getHourIncomeValue(role, type);
//		long startTime = lastIncomeTimeMap.get(type);
//
//		// 可领取次数		
//		int getExploitTimes = cdTime != 0
//			? (int) (nowTime - startTime) / cdTime
//			: 0;
//		
//		// 可领取数量
//		int getExploitNum = getExploitTimes * inCome;
//		Map<Byte, Integer> incomeCacheMap = build.getInComeCacheMap();
//		Integer cache = incomeCacheMap.get(type);
//		getExploitNum+=cache;
//
//		int maxCapacity = mainBuildingConfig.getCapacity();
//		if (getExploitNum > maxCapacity) {
//			getExploitNum = maxCapacity;
//		}
//
//		incomeCacheMap.put(type, getExploitNum);
//		lastIncomeTimeMap.put(type, startTime + getExploitTimes * cdTime);
//
//		build.setChange(true);
		inComeService.refreshGetResources(role, nowTime, InComeConstant.TYPE_CAMP);
	}

	@Override
	public Message getExploit(Role role, IoSession is) {
		Message message = new Message();
		message.setType(BarrackConstant.GET_EXPLOIT);

		Build build = role.getBuild();
		Short campLv = build.getBuildLvMap().get(InComeConstant.TYPE_CAMP);
		if (campLv == null) {
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(InComeConstant.TYPE_CAMP,
				campLv);
		int cdTime = mainBuildingConfig.getCd();

		// 获取配属英雄缩减后的cd时间
		int nowTime = Utils.getNowTime();
		this.refreshGetExploit(role, nowTime);

		// 剩余时间
		Map<Byte, Long> lastIncomeTimeMap = build.getBuildLastIncomeTimeMap();
		long startTime = lastIncomeTimeMap.get(InComeConstant.TYPE_CAMP);
		int remainTime = (int) ((nowTime - startTime) % cdTime);

		Map<Byte, Integer> incomeCacheMap = build.getInComeCacheMap();
		int getExploitNum = incomeCacheMap.get(InComeConstant.TYPE_CAMP);
		if (getExploitNum == 0) {// 检查领取是否是空
			message.putShort(ErrorCode.NO_EXPLOIT);
			return message;
		}
		roleService.addRoleExploit(role, getExploitNum);
		// 兵营缓存清空
		incomeCacheMap.put(InComeConstant.TYPE_CAMP, 0);
		
		inComeService.recordIncomeRank(role, InComeConstant.TYPE_CAMP, getExploitNum, nowTime);
		build.setChange(true);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(getExploitNum);
		message.putInt(remainTime);

		return message;
	}

	@Override
	public void initBarrack(Role role) {
		long nowTime = System.currentTimeMillis() / 1000;
		Build build = role.getBuild();
		build.getBuildLastIncomeTimeMap().put(InComeConstant.TYPE_CAMP, nowTime);
//		build.getInComeCacheMap().put(InComeConstant.TYPE_CAMP,
//				GeneralNumConstantCache.getValue("INIT_BARRACK_EXPLOIT_VALUE"));
		build.addInComeCacheMap(InComeConstant.TYPE_CAMP,
				GeneralNumConstantCache.getValue("INIT_BARRACK_EXPLOIT_VALUE"));
		build.setChange(true);
	}

	@Override
	public Message upArmySkill(Role role, int armySkillId) {
		Message message = new Message();
		message.setType(BarrackConstant.UP_ARMY_SKILL);

		Barrack barrack = role.getBarrack();
		Build build = role.getBuild();
		int exploit = role.getExploit();
		Short buildLv = build.getBuildLvMap().get(InComeConstant.TYPE_CAMP);
		if(buildLv == null){
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		
		int barrackLv = (int)buildLv;
		Integer armySkillLvId = barrack.getArmySkillLvMap().get(armySkillId);

		int needBuildLv = 0;
		int needExploit = 0;
		int targetArmySkillLvId = 0;

		if (armySkillLvId == null) {
			ArmyScience minArmyScience = ArmyScienceCache.getMinArmyScience(armySkillId);
			ArmyScience secondArmyScience = ArmyScienceCache.getArmyScienceById(minArmyScience.getNextId());
			needBuildLv = secondArmyScience.getNeedLv();
			needExploit = secondArmyScience.getCost();
			targetArmySkillLvId = secondArmyScience.getId();
		} else {
			ArmyScience armyScience = ArmyScienceCache.getArmyScienceById(armySkillLvId);
			targetArmySkillLvId= armyScience.getNextId();
			
			if(targetArmySkillLvId == 0){
				message.putShort(ErrorCode.MAX_ARMY_SKILL);
				return message;
			}
			ArmyScience nextArmyScience = ArmyScienceCache.getArmyScienceById(targetArmySkillLvId);
			
			needBuildLv = nextArmyScience.getNeedLv();
			needExploit = nextArmyScience.getCost();
		}

		if (barrackLv < needBuildLv) {
			message.putShort(ErrorCode.BARRACK_BUILD_LV_NO_REACH);
			return message;
		}

		if (exploit < needExploit) {
			message.putShort(ErrorCode.NO_EXPLOIT);
			return message;
		}

		// 可以升级
		roleService.addRoleExploit(role, -needExploit);
		barrack.getArmySkillLvMap().put(armySkillId, targetArmySkillLvId);
		barrack.setChange(true);

		ArmyScience currentArmyScience = ArmyScienceCache.getArmyScienceById(targetArmySkillLvId);
		int lv = currentArmyScience.getLv();
		int needLv = 0;
		int needCost = 0;
		int nextArmySkillLvId = currentArmyScience.getNextId();
		if (nextArmySkillLvId != 0) {
			ArmyScience nextArmyScience = ArmyScienceCache.getArmyScienceById(nextArmySkillLvId);
			needLv = nextArmyScience.getNeedLv();
			needCost = nextArmyScience.getCost();
		}

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(lv);
		message.putInt(needLv);
		message.putInt(needCost);

		return message;
	}

	/**
	 * 
	 * @param role
	 * @param heroId
	 * @return
	 * @author wcy
	 */
	@Override
	public Message visitOffensive(Role role, IoSession is) {
		Message message = new Message();
		message.setType(BarrackConstant.VISIT_OFFENSIVE);
		int nowTime = (int) (System.currentTimeMillis() / 1000);
		int propNum = GeneralNumConstantCache.getValue("OFFENSIVE_AWARD_PROP_NUM");
		int round = GeneralNumConstantCache.getValue("OFFENSIVE_ROUND");
		VipConfig config = VipConfigCache.getVipConfigByVipLv(role.getVipLv());
//		int maxOffensiveTimes = GeneralNumConstantCache.getValue("OFFENSIVE_TIMES");
		int chapterId = GeneralNumConstantCache.getValue("OFFENSIVE_CHAPTER_ID");

		Barrack barrack = role.getBarrack();
		// 征讨上限
		if (barrack.getOffensiveTimes() >= config.getBarracksAtkTimes()) {
			message.putShort(ErrorCode.MAX_OFFENSIVE);
			return message;
		}

		TroopData attackPlayer = PVPUitls.getTroopDataByRole(role);
		TroopData defensePlayer = PVPUitls.getTroopDataByChapterId(chapterId);

		ResultData resultData = new Battle(round).fightPVP(attackPlayer, defensePlayer);
		int hurtValue = resultData.defLost;
		String report = resultData.report;

		/**
		 * 添加至排名表
		 */
		int totalHurtValue = rankService.addOffensive(role, nowTime, hurtValue);

		// 增加当日征讨次数
		barrack.setOffensiveTimes(barrack.getOffensiveTimes() + 1);

		// 返回印绶
		propService.addProp(role, BarrackConstant.OFFENSIVE_AWARD_PROP, PropConstant.FUNCTION_TYPE_2, propNum, is);

		this.taskService.checkVisit(role, InComeConstant.TYPE_CAMP);

		barrack.setChange(true);
		

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(totalHurtValue);
		message.putInt(hurtValue);
		message.putString(report);
		message.putString(BarrackConstant.OFFENSIVE_AWARD_PROP + "," + propNum+";");

		return message;
	}

	@Override
	public Message showVisitOffensive(Role role) {
		// TODO Auto-generated method stub
		Message message = new Message();
		message.setType(BarrackConstant.SHOW_VISIT_OFFENSIVE);
		
		Barrack barrack = role.getBarrack();
		int maxOffensiveTimes = GeneralNumConstantCache.getValue("OFFENSIVE_TIMES");
		int chapterId = GeneralNumConstantCache.getValue("OFFENSIVE_CHAPTER_ID");
		
		int remainOffensive = maxOffensiveTimes - barrack.getOffensiveTimes();
		int propId = BarrackConstant.OFFENSIVE_AWARD_PROP;
		int propNum = GeneralNumConstantCache.getValue("OFFENSIVE_AWARD_PROP_NUM");	
		
		ChapterConfig info = ChapterConfigCache.getChapterConfigById(chapterId);
		if (info == null)
			return null;
		String[] strs = info.getTroopStr().split(",");
		StringBuilder sb = new StringBuilder();
		for (String temp : strs) {
			ChapterArmsConfig cac = ChapterArmsConfigCache.getChapterArmsConfig(temp);
			if (cac == null) {
				sb.append(temp);
			} else {
				HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(cac.getHeroId());

				sb.append(heroConfig.getImgId());
			}
			sb.append(",");
		}
		
		message.putString(sb.toString());
		message.putInt(remainOffensive);
		message.putInt(propId);
		message.putInt(propNum);
		
		return message;
	}
	
	@Override
	public Message showArmySkill(Role role) {
		Message message = new Message();
		message.setType(BarrackConstant.SHOW_ARMY_SKILL);
		HashMap<Integer, Integer> map = role.getBarrack().getArmySkillLvMap();

		message.putInt(map.size());
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			int skillId = entry.getKey();
			int skillLvId = entry.getValue();
			message.putInt(skillId);
			message.putInt(skillLvId);
		}
		return message;
	}

	@Override
	public Message getRank() {
		Message message = new Message();
		message.setType(BarrackConstant.GET_OFFENSIVE_RANK);
		List<Rank> offensiveRankRecord = RankCache.getRankRecord(RankType.OFFENSIVE);
		int size = offensiveRankRecord.size();
		message.putInt(size);
		for(Rank rank :offensiveRankRecord ){
			OffensiveData data = (OffensiveData)rank;
			
			Role role = roleService.getRoleById(data.getRoleId());
			int hurtValue = data.getHurtValue();
			int offensiveTime = data.getOffensiveTime();
			int rankNum = data.getRankNum();
			String account = role.getAccount();
			
			message.putInt(hurtValue);
			message.putInt(offensiveTime);
			message.putInt(rankNum);
			message.putString(account);
		}
		
		return message;
	}
}
