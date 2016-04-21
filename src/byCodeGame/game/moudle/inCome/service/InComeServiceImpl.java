package byCodeGame.game.moudle.inCome.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.BuildConfigCache;
import byCodeGame.game.cache.file.BuildQueueConfigCache;
import byCodeGame.game.cache.file.BuildingUnlockCache;
import byCodeGame.game.cache.file.ChapterArmsConfigCache;
import byCodeGame.game.cache.file.ChapterConfigCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.IconUnlockConfigCache;
import byCodeGame.game.cache.file.LevelLimitCache;
import byCodeGame.game.cache.file.LevyConfigCache;
import byCodeGame.game.cache.file.MainBuildingConfigCache;
import byCodeGame.game.cache.file.MainCityConfigCache;
import byCodeGame.game.cache.file.MarketConfigCache;
import byCodeGame.game.cache.file.RoleScienceConfigCache;
import byCodeGame.game.cache.file.VipConfigCache;
import byCodeGame.game.cache.local.RankCache.RankType;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.ServerCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Build;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Market;
import byCodeGame.game.entity.bo.Pub;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.BuildConfig;
import byCodeGame.game.entity.file.BuildingUnlock;
import byCodeGame.game.entity.file.ChapterArmsConfig;
import byCodeGame.game.entity.file.ChapterConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.IconUnlockConfig;
import byCodeGame.game.entity.file.LevelLimit;
import byCodeGame.game.entity.file.LevyConfig;
import byCodeGame.game.entity.file.MainBuildingConfig;
import byCodeGame.game.entity.file.MainCityConfig;
import byCodeGame.game.entity.file.MarketConfig;
import byCodeGame.game.entity.file.RoleScienceConfig;
import byCodeGame.game.entity.file.VipConfig;
import byCodeGame.game.entity.po.BuildInfo;
import byCodeGame.game.entity.po.BuildQueue;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.entity.po.FormationData;
import byCodeGame.game.entity.po.HeroFightValueRank;
import byCodeGame.game.entity.po.History;
import byCodeGame.game.entity.po.IncomeLoopData;
import byCodeGame.game.entity.po.IncomeRank;
import byCodeGame.game.entity.po.KillRank;
import byCodeGame.game.entity.po.LevyInfo;
import byCodeGame.game.entity.po.MarketItems;
import byCodeGame.game.entity.po.OwnCityRank;
import byCodeGame.game.entity.po.Rank;
import byCodeGame.game.entity.po.RoleLvRank;
import byCodeGame.game.entity.po.VisitData;
import byCodeGame.game.entity.po.VisitScienceData;
import byCodeGame.game.entity.po.VisitTreasureData;
import byCodeGame.game.moudle.barrack.service.BarrackService;
import byCodeGame.game.moudle.chapter.ChapterConstant;
import byCodeGame.game.moudle.chapter.ChapterConstant.Award;
import byCodeGame.game.moudle.chapter.service.ChapterService;
import byCodeGame.game.moudle.fight.FightConstant;
import byCodeGame.game.moudle.fight.service.FightService;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.inCome.InComeConstant;
import byCodeGame.game.moudle.inCome.InComeConstant.HeroAttrType;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.moudle.market.MarketConstant;
import byCodeGame.game.moudle.prop.PropConstant;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.moudle.pub.PubConstant;
import byCodeGame.game.moudle.pub.service.PubService;
import byCodeGame.game.moudle.rank.RankConstant;
import byCodeGame.game.moudle.rank.service.RankService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.science.ScienceConstant;
import byCodeGame.game.moudle.task.TaskConstant;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.HeroUtils;
import byCodeGame.game.util.InComeUtils;
import byCodeGame.game.util.PVPUitls;
import byCodeGame.game.util.Utils;
import cn.bycode.game.battle.core.Battle;
import cn.bycode.game.battle.data.ResultData;
import cn.bycode.game.battle.data.TroopData;

public class InComeServiceImpl implements InComeService {

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private HeroService heroService;

	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}

	private FightService fightService;

	public void setFightService(FightService fightService) {
		this.fightService = fightService;
	}

	private PubService pubService;

	public void setPubService(PubService pubService) {
		this.pubService = pubService;
	}

	private PropService propService;

	public void setPropService(PropService propService) {
		this.propService = propService;
	}

	private ChapterService chapterService;

	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}

	private BarrackService barrackService;

	public void setBarrackService(BarrackService barrackService) {
		this.barrackService = barrackService;
	}

	private MailService mailService;

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
	private RankService rankService;
	public void setRankService(RankService rankService){
		this.rankService = rankService;
	}

	@Override
	public Message showLevyInfo(Role role,byte type,int heroId) {
		Message message = new Message();
		message.setType(InComeConstant.SHOW_LEVY_INFO);
		int income = 0;
		int freeRemainLevyTimes = 0;
		int goldRemainLevyTimes = 0;
		int goldCost = 0;
		
		Integer goldLevyTimes = 0;
		Integer freeLevyTimes = 0;
		Build build = role.getBuild();
		byte vipLv = role.getVipLv();
		VipConfig vipConfig = VipConfigCache.getVipConfigByVipLv((int)vipLv);
		int totalLevyTimes = 0;
		int totalFreeLevyTimes = 0;
		if(type == InComeConstant.TYPE_FARM){
			totalLevyTimes = vipConfig.getLevyFarmTimes();
			totalFreeLevyTimes = vipConfig.getFreeLevyFarmTimes();
		}else if(type == InComeConstant.TYPE_HOUSE){
			totalLevyTimes = vipConfig.getLevyCoinTimes();
			totalFreeLevyTimes = vipConfig.getFreeLevyCoinTimes();
		}
		
		//金币征收次数
		goldLevyTimes = build.getLevyTimeMap().get(type);
		if(goldLevyTimes == null){
			goldLevyTimes = 0;
		}
		goldRemainLevyTimes = totalLevyTimes - goldLevyTimes;		
		if(goldRemainLevyTimes != 0){
//			goldCost = LevyConfigCache.getLevyConfigByTimes(goldLevyTimes + 1).getGoldCost();
			int levyValue = this.getLevyIncomeValue(role, type, heroId);
			goldCost = this.getNextLevyNeedGold(role, type, levyValue);			
		}

		//免费征收次数
		freeLevyTimes = build.getFreeLevyTimeMap().get(type);
		if(freeLevyTimes == null){
			freeLevyTimes = 0;
		}
		freeRemainLevyTimes = totalFreeLevyTimes - freeLevyTimes;	
		
		//征收量
		income = this.getLevyIncomeValue(role, type, heroId);
		
		message.putInt(income);
		message.put((byte) freeRemainLevyTimes);
		message.put((byte) goldRemainLevyTimes);
		message.putInt(goldCost);
		return message;
	}

	public Message levy(byte type, Role role, int heroId,byte levyType) {
		Message message = new Message();
		message.setType(InComeConstant.LEVY);

		levy(message, role, type, heroId, levyType);
		this.taskService.checkInComeTask(role, type, TaskConstant.TYPE_BYTE_2);
		return message;
	}
	

	public Message upBuild(Role role, byte type, byte buildId) {
		Message message = new Message();
		message.setType(InComeConstant.UP_BUILD);
		Build build = role.getBuild();
		Short buildTypeLv = build.getBuildLvMap().get(type);
		if (buildTypeLv == null) {
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		int buildLv = 0;
		if (type == InComeConstant.TYPE_HOUSE) { // 民居
			// 建筑等级
			buildLv = role.getBuild().getHouseMap().get((int) buildId);
		} else if (type == InComeConstant.TYPE_FARM) { // 农场
			// 建筑等级
			buildLv = role.getBuild().getFarmLvMap().get((int) buildId);
		}

		// 可以解锁，但是还没有解锁
		if (buildLv == InComeConstant.NO_LV) {
			message.putShort(ErrorCode.NO_LV);
			return message;
		}

		BuildConfig buildConfig = BuildConfigCache.getBuildConfig(buildLv, type);
		if (role.getMoney() < buildConfig.getUpMoney()) { // 升级所需金钱不足
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}

		// 子资源建筑不能大于资源建筑等级

		if (buildTypeLv <= buildConfig.getLv()) {// 等级不够
			message.putShort(ErrorCode.BUILD_LV_NO_REACH);
			return message;
		}

		//
		// 升级成功 更改建筑等级 扣除主角金钱 更新建筑队列
		//
		// 刷新建筑队列时间
		this.refreshBuildTime(role);
		// 获取空闲的ID
		byte queueId = this.getIdleBuildQueue(role);
		if (queueId <= 0) { // 没有空闲的建筑队列
			message.putShort(ErrorCode.NO_BUILD_QUEUE);
			return message;
		}
		BuildQueue buildQueue = new BuildQueue();
		buildLv = buildLv + 1;
		if (type == InComeConstant.TYPE_HOUSE) {
			Map<Integer, Integer> houseMap = role.getBuild().getHouseMap();
			houseMap.put((int) buildId, buildLv);
			buildQueue = role.getBuildQueueMap().get(queueId);
		} else if (type == InComeConstant.TYPE_FARM) {
			Map<Integer, Integer> farmMap = role.getBuild().getFarmLvMap();
			farmMap.put((int) buildId, buildLv);
			buildQueue = role.getBuildQueueMap().get(queueId);
		}

		// 建筑队列添加时间
		buildQueue.setTime(buildQueue.getTime() + buildConfig.getUpTime());
		if (buildQueue.getTime() >= InComeConstant.MAX_QUEUE_TIME) {
			// 关闭建筑队列
			buildQueue.setOpen(InComeConstant.CLOSE_QUEUE);
		}

		role.getBuild().setChange(true);
		roleService.addRoleMoney(role, -buildConfig.getUpMoney());
		role.setChange(true);

		// 查询任务
		taskService.checkBuildUpTask(role, type, (byte) buildLv);
		taskService.checkInComeTask(role, type, TaskConstant.TYPE_BYTE_1);

		message.putShort(ErrorCode.SUCCESS);
		message.put(type);
		message.put(buildId);
		message.putInt(buildConfig.getUpMoney());
		message.put(queueId);
		message.putInt(buildConfig.getUpTime());
		BuildConfig buildConfig1 = BuildConfigCache.getBuildConfig(buildLv, type);
		message.putInt(buildConfig1.getInCome());
		BuildConfig buildConfig2 = BuildConfigCache.getBuildConfig((buildLv + 1), type);
		if (buildConfig2 != null) {
			message.putInt(buildConfig2.getInCome());
			message.putInt(buildConfig1.getUpMoney());
			message.putInt(buildConfig1.getUpTime());
		} else {
			message.putInt(0);
			message.putInt(0);
			message.putInt(0);
		}
		int hourValue = this.getHourIncomeValue(role,type);
		int baseValue = this.getBaseHourIncomeValue(role, type);
		
		message.putInt(hourValue);
		message.putInt(baseValue);
		message.putInt(hourValue - baseValue);
		
		this.buildInfoChange(role, type, InComeConstant.CHANGE_TYPE_INCOME, Utils.getNowTime());
		
		return message;
	}

	/**
	 * 获得无增益的每小时收益值
	 * 
	 * @param role
	 * @param type 2农田：粮食3居民：银币4酒馆：酒
	 * @return
	 * @author wcy
	 */
	private int getBaseHourIncomeValue(Role role, byte type) {
		int value = 0;
		if(type == InComeConstant.TYPE_MAIN_CITY){
			MainCityConfig config = MainCityConfigCache.getMainCityConfig((int)role.getLv());
			if(config == null)
				config = MainCityConfigCache.getMainCityConfig(80);
			value = config.getPrestigeYield();
		}
		if (type == InComeConstant.TYPE_HOUSE) {
			Map<Integer, Integer> houseMap = role.getBuild().getHouseMap();
			for (Map.Entry<Integer, Integer> entry : houseMap.entrySet()) {
				int tempLv = entry.getValue();
				BuildConfig tempBuildConfig = BuildConfigCache.getBuildConfig(tempLv, type);
				value += tempBuildConfig.getInCome();
			}
			// value += InComeConstant.ADD_VALUE;
		}
		if (type == InComeConstant.TYPE_FARM) {
			Map<Integer, Integer> farmMap = role.getBuild().getFarmLvMap();
			for (Map.Entry<Integer, Integer> entry : farmMap.entrySet()) {
				int tempLv = entry.getValue();
				BuildConfig tempBuildConfig = BuildConfigCache.getBuildConfig(tempLv, type);
				value += tempBuildConfig.getInCome();
			}
			// value += InComeConstant.ADD_VALUE;
		}
		if (type == InComeConstant.TYPE_PUB ) {
			Short buildLv = role.getBuild().getBuildLvMap().get(type);
			if (buildLv == null)
				return value;

			MainBuildingConfig tempMainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(type,
					(int) buildLv);
			value = tempMainBuildingConfig.getInCome();
		}
		
		if (type == InComeConstant.TYPE_FORGE ) {
			Short buildLv = role.getBuild().getBuildLvMap().get(type);
			if (buildLv == null)
				return value;

			MainBuildingConfig tempMainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(type,
					(int) buildLv);
			value = tempMainBuildingConfig.getInCome();
		}
		
		if(type == InComeConstant.TYPE_CAMP){
			Short buildLv = role.getBuild().getBuildLvMap().get(type);
			if (buildLv == null)
				return value;

			MainBuildingConfig tempMainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(type,
					(int) buildLv);
			value = tempMainBuildingConfig.getInCome();
			
//			int num = GeneralNumConstantCache.getValue("BARRACK_BUILD_LV_EXPLOIT");
//			value = buildLv * num;
		}
		
		

		return value;
	}

	@Override
	public int getHourIncomeValue(Role role, byte type) {
		int value = getBaseHourIncomeValue(role, type);

		Map<Byte, BuildInfo> attachHeroMap = role.getBuild().getAttachHeroMap();
		if (attachHeroMap == null) {
			return value;
		}

		Integer attachHeroId = attachHeroMap.containsKey(type)
			? attachHeroMap.get(type).getHeroId()
			: null;
		Hero hero = role.getHeroMap().get(attachHeroId);

		if (hero == null)
			return value;

		int power = HeroUtils.getPowerValue(hero);
		int captain = HeroUtils.getCaptainValue(hero);
		int intel = HeroUtils.getIntelValue(hero);
		if (type == InComeConstant.TYPE_MAIN_CITY) {
			value = (int) formula(value, captain, type, HeroAttrType.captain,hero.getRank());
		} else if (type == InComeConstant.TYPE_FARM) {
			value = (int) formula(value, power, type, HeroAttrType.power,hero.getRank());
		} else if (type == InComeConstant.TYPE_HOUSE) {
			value = (int) formula(value, intel, type, HeroAttrType.intel,hero.getRank());
		} else if (type == InComeConstant.TYPE_PUB) {
			value = (int) formula(value, intel, type, HeroAttrType.intel,hero.getRank());
		} else if (type == InComeConstant.TYPE_FORGE) {
			value = (int) formula(value, power, type, HeroAttrType.power,hero.getRank());
		} else if (type == InComeConstant.TYPE_TECH) {
			value = (int) formula(value, intel, type, HeroAttrType.intel,hero.getRank());
		} else if (type == InComeConstant.TYPE_CAMP) {
			value = (int) formula(value, captain, type, HeroAttrType.captain,hero.getRank());
		}

		return value;
	}

	public void virtualLevy(Role role, byte type) {
		// int levyNum = 0; // 征收值
		// long sysTime = System.currentTimeMillis() / 1000;
		// if (type == InComeConstant.TYPE_HOUSE) {
		// // int second = (int) (sysTime -
		// role.getBuild().getHouseLastIncomeTime());
		// levyNum = this.getHourIncomeValue(role, type);
		// // 每分钟总收益值
		// // levyNum = levyNum * second / (60 * 60);
		// //
		// role.getBuild().setHouseInComeCache(role.getBuild().getHouseInComeCache()
		// // + levyNum);
		// role.getBuild().getInComeCacheMap().put(type,
		// role.getBuild().getInComeCacheMap().get(type) + levyNum);
		// // role.getBuild().setHouseLastIncomeTime(sysTime);
		// } else if (type == InComeConstant.TYPE_FARM) {
		// // int second = (int) (sysTime -
		// role.getBuild().getFarmLastIncomeTime());
		// levyNum = this.getHourIncomeValue(role, type);
		// // levyNum = levyNum * second / (60 * 60);
		// //
		// role.getBuild().setFarmInComeCache(role.getBuild().getFarmInComeCache()
		// + levyNum);
		// role.getBuild().getInComeCacheMap().put(type,
		// role.getBuild().getInComeCacheMap().get(type) + levyNum);
		// // role.getBuild().setFarmLastIncomeTime(sysTime);
		// }

		// role.getBuild().setChange(true);
	}

	@Override
	public Message getIncomeData(Role role) {
		Message message = new Message();
		message.setType(InComeConstant.GET_INCOME_DATA);		

		ArrayList<IncomeLoopData> a= new ArrayList<>();
		
		Build build = role.getBuild();
		long nowTime = Utils.getNowTime();
		Map<Integer, LevyInfo> levyMap = build.getLevyMap();
		Map<Byte, Short> buildLvMap = build.getBuildLvMap();
		
		//检查武将体力
		this.checkHeroManual(role,(int) nowTime);

		byte mainType = InComeConstant.TYPE_MAIN_CITY;
		BuildInfo mainCityBuildInfo = build.getAttachHeroMap().containsKey(mainType) ? build
				.getAttachHeroMap().get(mainType) : null;
		Integer mainCityAttachHero = mainCityBuildInfo != null ? mainCityBuildInfo.getHeroId() : null;
		int mainCityAttachHeroLastAutoTime = 0;

		if (mainCityAttachHero != null) {
			mainCityAttachHeroLastAutoTime = mainCityBuildInfo.getLastAutoTime();
		} else {
			mainCityAttachHero = 0;
		}
		this.refreshLevyData(role, nowTime);

		roleService.refreshGetPrestige(role, (int) nowTime);
		int mainHourIncomeValue = this.getHourIncomeValue(role, mainType);
		int minShow = 50;
		String mainAutoIncomeRemainTime = this.getAutoIncomeRemainTimeAndCacheStrByType(role,mainType, nowTime, minShow);
		short mainCityLv = role.getLv();
//		message.putInt(mainCityAttachHero);
//		message.putShort(mainCityLv);
//		message.putInt(mainCityAttachHeroLastAutoTime);
//		message.putInt(mainHourIncomeValue);
//		message.putString(mainAutoIncomeRemainTime);
//		message.putInt(build.getBuildLvMap().size());
		
		a.add(this.createIncomeLoopData(InComeConstant.TYPE_MAIN_CITY, mainAutoIncomeRemainTime));
		

		StringBuffer scienceStr = new StringBuffer();
		for (Map.Entry<Byte, Short> buildLvEntry : buildLvMap.entrySet()) {
			byte type = buildLvEntry.getKey();
			short buildLv = buildLvEntry.getValue();
			// Hero attachHero = null;
			BuildInfo buildInfo = build.getAttachHeroMap().containsKey(type) ? build.getAttachHeroMap().get(type) : null;
			int attachHeroId = 0;
			int attachHeroLastAutoTime = 0;
			long deltaTime = 0;
			int cdTime = 0;
			StringBuffer levyStr = new StringBuffer();
			int scienceHourAddValue = 0;
			String scienceName = "";
			int hourIncomeValue = this.getHourIncomeValue(role, type);

			if (buildInfo != null) {
				attachHeroId = buildInfo.getHeroId();
				if (attachHeroId != 0) {
					attachHeroLastAutoTime = buildInfo.getLastAutoTime();
				}
			}

			// 获得配属增益
			for (Map.Entry<Integer, LevyInfo> levyInfoEntry : levyMap.entrySet()) {

				LevyInfo levyInfo = levyInfoEntry.getValue();
				byte levyInfoType = levyInfo.getType();
				if (levyInfoType != type)
					continue;
				// 获得献策带来的每小时增益
				scienceHourAddValue = this.getTechHourIncomeValue(role, hourIncomeValue, type);
				
				long startTime = levyInfo.getStartTime();
				cdTime = levyInfo.getCd();
				deltaTime = Utils.getRemainTime((int) nowTime, (int) startTime, cdTime);

				if (type == InComeConstant.TYPE_TECH) {// 如果是计策府还要返回使用的科技名称
					byte resType = (byte) levyInfo.getValue();
					Map<Integer, Integer> roleScienceMap = role.getRoleScienceMap();
					int scienceId = 0;
					if (resType == InComeConstant.TYPE_FARM) {
						scienceId = ScienceConstant.FOOD_SCIENCE_ID;
					} else if (resType == InComeConstant.TYPE_HOUSE) {
						scienceId = ScienceConstant.MONEY_SCIENCE_ID;
					}
					int scienceLv = roleScienceMap.get(scienceId);
					RoleScienceConfig roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(scienceId,
							scienceLv);
					scienceName = roleScienceConfig.getName();
					scienceStr.append(deltaTime).append(";").append(cdTime).append(";").append(scienceName).append(";");
				}

			}
			levyStr.append(deltaTime).append(";").append(cdTime).append(";").append(scienceName).append(";");
			
			minShow = 0;
			String cacheValue = null;
			String autoItemGetValue = null;
			if (type == InComeConstant.TYPE_FARM) {
				this.refreshGetFood(role, nowTime);
				cacheValue = this.getCacheValue(build, type) + "";
		        minShow = 500;
				autoItemGetValue = getAutoIncomeRemainTimeAndCacheStrByType(role, type, nowTime, minShow);
			} else if (type == InComeConstant.TYPE_HOUSE) {
				this.refreshGetMoney(role, nowTime);
				cacheValue = this.getCacheValue(build, type) + "";
				minShow = 500;
				autoItemGetValue = getAutoIncomeRemainTimeAndCacheStrByType(role, type, nowTime, minShow);
			} else if (type == InComeConstant.TYPE_PUB) {
				pubService.refreshGetWines(role, nowTime);
				cacheValue = role.getPub().getVisitData();
				minShow = 1;
				autoItemGetValue = getAutoIncomeRemainTimeAndCacheStrByType(role, type, nowTime, minShow);
			} else if (type == InComeConstant.TYPE_FORGE) {
				propService.refreshGetIron(role, nowTime);
				cacheValue = role.getBuild().getVisitTreasureData();
				minShow = 500;
				autoItemGetValue = getAutoIncomeRemainTimeAndCacheStrByType(role, type, nowTime, minShow);
			} else if (type == InComeConstant.TYPE_TECH) {
				cacheValue = role.getBuild().getVisitScienceData();
				autoItemGetValue = "";
			} else if (type == InComeConstant.TYPE_CAMP) {
				minShow = GeneralNumConstantCache.getValue("BARRACK_SHOW_EXPLOIT_VALUE");
				cacheValue = "";
				barrackService.refreshGetExploit(role, nowTime);				
				autoItemGetValue = getAutoIncomeRemainTimeAndCacheStrByType(role, type, nowTime, minShow);
			}else if(type == InComeConstant.TYPE_MARKET){
				cacheValue ="";
				autoItemGetValue = "";
			}
			
			
			
//			message.put(type);// 建筑类型
//			message.putInt((int) hourIncomeValue + scienceHourAddValue);
//			message.putInt(attachHeroId);
//			message.putInt(attachHeroLastAutoTime);
//			message.putString(levyStr.toString());
//			message.putString(cacheValue);// 可以收获的收益值
//			message.putShort(buildLv);// 建筑等级
//			message.putString(autoItemGetValue);// 自动物品
			a.add(this.createIncomeLoopData(type, autoItemGetValue));

		}

		build.setChange(true);
		
		message.put((byte) a.size());
		for (IncomeLoopData d : a) {
			message.putString(d.key);
			message.put(d.awardType);
			message.put((byte) (d.visible ? 1 : 0));
			message.putInt(d.income);
		}
		message.putString(scienceStr.toString());
		

		return message;
	}
	
	private IncomeLoopData createIncomeLoopData(byte type,String cacheInfo) {
		IconUnlockConfig config = this.getIconUnlockConfigByBuildType(type);
		String name = config.getName();
		byte award = 0;
		int cache = 0;
		boolean visible = false;
		int minShow = 0;
		if(cacheInfo == null||cacheInfo.equals("")){
			IncomeLoopData d = new IncomeLoopData();
			d.awardType = award;
			d.income = cache;
			d.key = name;
			d.visible = visible;
			return d;
		}
		String[] s = cacheInfo.split(",");
		

		cache = Integer.parseInt(s[1]);
		minShow = Integer.parseInt(s[2]);
		
		visible = cache >= minShow && cache != 0 ? true : false;
		if (type == InComeConstant.TYPE_MAIN_CITY) {
			award = ChapterConstant.AWARD_PRESTIGE;
		} else if (type == InComeConstant.TYPE_FARM) {
			award = ChapterConstant.AWARD_FOOD;
		} else if (type == InComeConstant.TYPE_HOUSE) {
			award = ChapterConstant.AWARD_MONEY;
		} else if (type == InComeConstant.TYPE_PUB) {
			award = ChapterConstant.AWARD_ITEM;
		} else if (type == InComeConstant.TYPE_FORGE) {
			award = ChapterConstant.AWARD_ITEM;
		} else if (type == InComeConstant.TYPE_TECH) {
		} else if (type == InComeConstant.TYPE_CAMP) {
			award = ChapterConstant.AWARD_EXPLOIT;
		} else if (type == InComeConstant.TYPE_MARKET) {

		}

		IncomeLoopData d = new IncomeLoopData();
		d.awardType = award;
		d.income = cache;
		d.key = name;
		d.visible = visible;
		return d;
	}

	/**
	 * 获得献策每小时增益
	 * @param role
	 * @param hourIncomeValue
	 * @param type
	 * @return
	 * @author wcy 2016年1月22日
	 */
	private int getTechHourIncomeValue(Role role,int hourIncomeValue,byte type){
		int scienceId = 0;
		if (type == InComeConstant.TYPE_HOUSE) {
			scienceId = ScienceConstant.MONEY_SCIENCE_ID;
		} else if (type == InComeConstant.TYPE_FARM) {
			scienceId = ScienceConstant.FOOD_SCIENCE_ID;			
		}
		if(scienceId == 0){
			return 0;
		}
		Integer scienceLv = role.getRoleScienceMap().get(scienceId);
		RoleScienceConfig scienceConfig = RoleScienceConfigCache.getRoleScienceConfig(scienceId, scienceLv);
		int scienceHourAddValue = (int) (hourIncomeValue * scienceConfig.getValue()/10000.0);
		return scienceHourAddValue;
	}
	
	/**
	 * 返回的字符串格式 "remainSecond,cache,minShowCache"
	 * 
	 * @param role
	 * @param type
	 * @param nowTime
	 * @return
	 * @author wcy
	 */
	private String getAutoIncomeRemainTimeAndCacheStrByType(Role role, byte type, long nowTime, int minShowCache) {
		Build build = role.getBuild();
		Map<Byte, Long> lastIncomeTimeMap = build.getBuildLastIncomeTimeMap();
		Map<Byte, Integer> incomeCacheMap = build.getInComeCacheMap();
		BuildInfo buildInfo = build.getAttachHeroMap().get(type);
		
		
		int cdTime = GeneralNumConstantCache.getValue("BUILD_INCOME_CD");

		// 得到上次存入仓库的时间
		long startTime = lastIncomeTimeMap.get(type);
		// 得到缓存数量
		int cache = incomeCacheMap.get(type);

		int remainValue = minShowCache - cache;
		// 计算剩余cd时间
		int remainShowFirstIconSecond = 0;
		int remainShowNextIconSecond = 0;
		
		//如果，没有配属武将则没有剩余时间
		if (buildInfo != null && buildInfo.getHeroId() != InComeConstant.NO_HERO) {
			int maxShowCache = 10000;
			int hourValue = this.getHourIncomeValue(role, type);
			double oneProductNeedTime = 1.0 / ((double) hourValue / cdTime);

			if (remainValue > 0) {
				int needTimeUnit = (int) Math.ceil(remainValue / (float) hourValue);
				int needTime = needTimeUnit * cdTime;
				// remainSecond = (int) (needTime - (nowTime - startTime));
				// 剩余显示第一个图标的时间
				int remainTime = (int) (remainValue * oneProductNeedTime);
				int endTime = (int) (remainTime + startTime);
				remainShowFirstIconSecond = (int) (endTime - nowTime);
				if (remainShowFirstIconSecond < 0) {
					remainShowFirstIconSecond = 0;
				}
			} else {
				remainValue = maxShowCache - (cache - minShowCache) % maxShowCache;
				int remainTime = (int) ((remainValue * oneProductNeedTime));
				int endTime = (int) (remainTime + startTime);

				// 弹出接下来一个图标（至少是第二个）所剩余的时间
				remainShowNextIconSecond = (int) (endTime - nowTime);
				// System.out.println("remainShowNextIconSecond:"+remainShowNextIconSecond);
			}
		}
		
		

		String str = new StringBuilder().append(remainShowFirstIconSecond).append(",").append(cache).append(",")
				.append(minShowCache).toString();

		return str;
	}

	public Message openNewBuild(Role role, byte type, byte buildId) {
		Message message = new Message();
		message.setType(InComeConstant.OPEN_NEW_BUILD);
		BuildingUnlock buildingUnlock = BuildingUnlockCache.getBuildingUnlockMaps().get(type).get(buildId);
		Build build = role.getBuild();
		int targetBuildTypeLv = buildingUnlock.getLvLimit();
		Short buildLv = build.getBuildLvMap().get(type);
		if (buildLv == null) {
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		int currentBuildTypeLv = (int) buildLv;
		int cid = buildingUnlock.getTroopData();

		Map<Integer, Integer> childBuildLvMap = null;
		if (type == InComeConstant.TYPE_FARM)
			childBuildLvMap = role.getBuild().getFarmLvMap();
		else if (type == InComeConstant.TYPE_HOUSE)
			childBuildLvMap = role.getBuild().getHouseMap();

		// 建筑已经开启
		if (childBuildLvMap.get((int) buildId) > 0) {
			message.putShort(ErrorCode.BUILD_ID_OPEN);
			return message;
		}

		// 检查等级

		if (currentBuildTypeLv < targetBuildTypeLv) {
			message.putShort(ErrorCode.BUILD_LV_NO_REACH);
			return message;
		}

		FormationData formationData = role.getFormationData().get(role.getUseFormationID());
		int tempAllHp = FightConstant.NULL_HERO;
		ResultData resultData = null;
		int fightNeedFood = 0;
		if (buildingUnlock.getTroopData() == InComeConstant.NO_HERO) {

		} else {
			int heroTotalLevel = 0;
			int heroNum = 0;
			List<Hero> heroList = new ArrayList<>();
			for (int x : formationData.getData().values()) {
				if (x <= FightConstant.NUM_USE_2)
					continue;
				Hero hero = role.getHeroMap().get(x);
//				tempAllHp += hero.getArmsNum();
//				heroTotalLevel += hero.getLv();
//				heroNum++;
				heroList.add(hero);
			}
//			tempAllHp = tempAllHp / 10;
			fightNeedFood = InComeUtils.fightNeedFood(heroList);
			if (role.getFood() < fightNeedFood) {
				message.putShort(ErrorCode.NO_FOOD);
				return message;
			}
			TroopData roleData = PVPUitls.getTroopDataByRole(role);
			TroopData aiData = PVPUitls.getTroopDataByChapterId(buildingUnlock.getTroopData());
			resultData = new Battle().fightPVP(roleData, aiData);
		}
		// 未发生战斗
		if (resultData == null) {
			message.putShort(ErrorCode.SUCCESS);
			message.putString("");
			message.putString("");
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putString("");
			message.putString("");
			message.putString("");
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putString("");
			message.putInt(FightConstant.NUM_0);
			// 开启建筑
			childBuildLvMap.put((int) buildId, 1);
			message.put(type);
			message.put(buildId);
			message.putInt(FightConstant.NUM_0);
			int temp = 0;
			for(int x : childBuildLvMap.values())
			{
				if(x>0)
				{
					temp++;
				}
			}
			this.taskService.checkBuildUpTask(role, type, (byte) 3, (byte) temp);
			this.taskService.checkInComeTask(role, type, TaskConstant.TYPE_BYTE_1);
			return message;
		}
		// 战斗失败
		if (resultData.winCamp != FightConstant.NUM_USE_1) {
			roleService.addRoleFood(role, (int) (- fightNeedFood* .2));
			message.putShort(ErrorCode.SUCCESS);
			message.putString(resultData.uuid);
			message.putString(resultData.time);
			message.putInt(resultData.winCamp);
			message.putInt(resultData.attPlayerId);
			message.putInt(resultData.defPlayerId);
			message.putString(resultData.attName);
			message.putString(resultData.defName);
			message.putString(resultData.report);
			message.putInt(resultData.attStars);
			message.putInt((int) (fightNeedFood * .2));
			message.putInt(resultData.defStars);
			message.putInt(resultData.defLost);
			message.putInt(resultData.fRound);
			message.putInt(FightConstant.NUM_0);
			message.putString("");
			message.putInt(FightConstant.NUM_0);
			message.put((byte) 0);
			message.put((byte) 0);
			message.putInt(FightConstant.NUM_0);
			return message;
		}

		ChapterConfig chapterConfig = ChapterConfigCache.getChapterConfigById(cid);
		fightService.returnStar(resultData, chapterConfig.getStarStrategy());
		// 粮草消耗
		roleService.addRoleMoney(role, chapterConfig.getCoins());
		roleService.addRoleFood(role, -fightNeedFood);
		roleService.addRoleExploit(role, chapterConfig.getExploit());
		IoSession is = SessionCache.getSessionById(role.getId());
		Set<ChapterReward> set = Utils.changStrToAward(chapterConfig.getFirstDrop());
		chapterService.getAward(role, set);
		// 关卡通关后获得武将
		boolean flag = false;
		if (chapterConfig.getUnLockHeroType() == FightConstant.NUM_USE_1) {
			HeroConfig config = HeroConfigCache.getHeroConfigById((chapterConfig == null)
				? 0
				: chapterConfig.getUnLockHero());
			if (config == null || role.getHeroMap().containsKey(config.getHeroId())) {
				// heroService.addHeroEmotion(role,
				// chapterConfig.getUnLockHero(), chapterConfig.getFavour());
			} else {
				int heroId = chapterConfig.getUnLockHero();
				Hero hero = heroService.createHero(role, heroId);
				heroService.addHero(is, hero);
				flag = true;
			}
		}
		int heroExp = chapterConfig.getHeroExp();
		String expStr = heroService.addHeroExp(role, heroExp);
		message.putShort(ErrorCode.SUCCESS);
		message.putString(resultData.uuid);
		message.putString(resultData.time);
		message.putInt(resultData.winCamp);
		message.putInt(resultData.attPlayerId);
		message.putInt(resultData.defPlayerId);
		message.putString(resultData.attName);
		message.putString(resultData.defName);
		message.putString(resultData.report);
		message.putInt(resultData.attStars);
		message.putInt(fightNeedFood);
		message.putInt(resultData.defStars);
		message.putInt(resultData.defLost);
		message.putInt(resultData.fRound);
		message.putInt(heroExp);
		message.putString(expStr);
		message.putInt(chapterConfig.getExploit());
		// 开启建筑
		childBuildLvMap.put((int) buildId, 1);
		message.put(type);
		message.put(buildId);
		if (flag) {
			message.putInt(chapterConfig.getUnLockHero());
		} else {
			message.putInt(InComeConstant.NO_HERO);
		}
		int temp = childBuildLvMap.get((int) buildId);
		this.taskService.checkBuildUpTask(role, type, (byte) 3, (byte) temp);
		this.taskService.checkInComeTask(role, type, TaskConstant.TYPE_BYTE_1);

		return message;
	}

	public void refreshBuildTime(Role role) {

		long nowTime = System.currentTimeMillis() / 1000;
		Map<Byte, BuildQueue> queueMap = new HashMap<Byte, BuildQueue>();
		queueMap = role.getBuildQueueMap();

		if (queueMap.size() <= 0)
			return;

		for (Map.Entry<Byte, BuildQueue> entry : queueMap.entrySet()) {
			// 当前日期 - 上一次更新时间
			int cdTime = (int) ((nowTime - entry.getValue().getLastUpTime()));
			if (cdTime <= 0)
				cdTime = 0;
			int nowBuildTime = entry.getValue().getTime() - cdTime;
			if (nowBuildTime <= 0) {
				nowBuildTime = 0;
				entry.getValue().setOpen(InComeConstant.OPEN_QUEUE);
			}
			entry.getValue().setTime(nowBuildTime);
			entry.getValue().setLastUpTime(nowTime);
		}

		role.getBuild().setChange(true);
	}

	// public Message getBuildData(Role role, byte type) {
	// Message message = new Message();
	// message.setType(InComeConstant.GET_BUILD_DATA);
	// Map<Integer, Integer> buildMap = new HashMap<Integer, Integer>();
	// int buildTypeLv = role.getBuild().getBuildLvMap().get(type);
	// int num = BuildingUnlockCache.getUnlockLocByLimitLv(type, buildTypeLv);
	//
	// if (type == InComeConstant.TYPE_HOUSE) { // 民居BuildQueue
	// buildMap = role.getBuild().getHouseMap();
	// }
	// if (type == InComeConstant.TYPE_FARM) {
	// buildMap = role.getBuild().getFarmLvMap();
	// }
	//
	// message.putShort(ErrorCode.SUCCESS);
	// message.put(type);
	// message.putInt(this.getHourIncomeValue(role, type));
	// message.put((byte) num);
	// for (int i = 1; i <= num; i++) {
	// message.put((byte) i);
	// int buildLv = buildMap.get(i).intValue();
	// message.put((byte) buildLv);
	// BuildConfig buildConfig = BuildConfigCache.getBuildConfig(buildLv, type);
	// BuildConfig buildConfig2 = BuildConfigCache.getBuildConfig((buildLv + 1),
	// type);
	// message.putInt(buildConfig.getInCome());
	// if (buildConfig2 != null) {
	// message.putInt(buildConfig2.getInCome());
	// message.putInt(buildConfig.getUpMoney());
	// message.putInt(buildConfig.getUpTime());
	// } else {
	// message.putInt(InComeConstant.NO_HERO);
	// message.putInt(InComeConstant.NO_HERO);
	// message.putInt(InComeConstant.NO_HERO);
	// }
	// int chapterId =
	// BuildingUnlockCache.getBuildingUnlockMaps().get(type).get((byte)
	// i).getTroopData();
	// ChapterConfig x = ChapterConfigCache.getChapterConfigById(chapterId);
	// if (x == null) {
	// message.putInt(InComeConstant.NO_HERO);
	// } else {
	// message.putInt(x.getImageid());
	// }
	// }
	// return message;
	// }

//	@Override
//	public Message getBuildData(Role role, byte type) {
//		Message message = new Message();
//		message.setType(InComeConstant.GET_BUILD_DATA);
//		Map<Integer, Integer> buildMap = new HashMap<Integer, Integer>();
//		Short buildTypeLv = role.getBuild().getBuildLvMap().get(type);
//		if (buildTypeLv == null) {
//			message.putShort(ErrorCode.SUCCESS);
//			message.put(type);
//			message.putInt(0);
//			message.putInt(0);
//			message.putInt(0);
//			message.putInt(0);
//			message.put((byte)0);
//			return message;
//		}
//		int num = BuildingUnlockCache.getUnlockLocByLimitLv(type, buildTypeLv);
//		byte maxUnlock = BuildingUnlockCache.getMaxBuildingUnlock(type);
//
//		if (type == InComeConstant.TYPE_HOUSE) { // 民居BuildQueue
//			buildMap = role.getBuild().getHouseMap();
//		}
//		if (type == InComeConstant.TYPE_FARM) {
//			buildMap = role.getBuild().getFarmLvMap();
//		}
//
//		int baseValue = this.getBaseHourIncomeValue(role, type);// 基础值
//		int attachAddDelta = this.getHourIncomeValue(role, type) - baseValue;// 配属增益
//		int mainCityAddDelta = 0;// 官邸增益
//
//		message.putShort(ErrorCode.SUCCESS);
//		message.put(type);
//		message.putInt(this.getHourIncomeValue(role, type) + mainCityAddDelta);
//		message.putInt(baseValue);
//		message.putInt(attachAddDelta);
//		message.putInt(mainCityAddDelta);
//		message.put(maxUnlock);
//		for (int i = 1; i <= maxUnlock; i++) {
//			message.put((byte) i);
//			int buildLv = buildMap.get(i);
//			message.put((byte) buildLv);
//			BuildConfig buildConfig = BuildConfigCache.getBuildConfig(buildLv, type);
//			BuildConfig buildConfig2 = BuildConfigCache.getBuildConfig((buildLv + 1), type);
//			message.putInt(buildConfig.getInCome());
//			if (buildConfig2 != null) {
//				message.putInt(buildConfig2.getInCome());
//				message.putInt(buildConfig.getUpMoney());
//				message.putInt(buildConfig.getUpTime());
//			} else {
//				message.putInt(InComeConstant.NO_HERO);
//				message.putInt(InComeConstant.NO_HERO);
//				message.putInt(InComeConstant.NO_HERO);
//			}
//			int chapterId = BuildingUnlockCache.getBuildingUnlockMaps().get(type).get((byte) i).getTroopData();
//			ChapterConfig x = ChapterConfigCache.getChapterConfigById(chapterId);
//			if (x == null) {
//				message.putInt(InComeConstant.NO_HERO);
//			} else {
//				message.putInt(x.getImageid());
//			}
//			int lvLimit = BuildingUnlockCache.getBuildingUnlockMaps().get(type).get((byte) i).getLvLimit();
//			message.putInt(lvLimit);
//		}
//		return message;
//	}
	
	@Override
	public Message getBuildData(Role role, byte type) {
		Message message = new Message();
		message.setType(InComeConstant.GET_BUILD_DATA);
		Map<Integer, Integer> buildMap = new HashMap<Integer, Integer>();
		Short buildTypeLv = role.getBuild().getBuildLvMap().get(type);
		if (buildTypeLv == null) {
			message.putShort(ErrorCode.SUCCESS);
			message.put(type);
			message.putInt(0);
			message.putInt(0);
			message.putInt(0);
			message.putInt(0);
			message.put((byte)0);
			message.put((byte)0);
			return message;
		}
		int num = BuildingUnlockCache.getUnlockLocByLimitLv(type, buildTypeLv);
		byte maxUnlock = BuildingUnlockCache.getMaxBuildingUnlock(type);

		if (type == InComeConstant.TYPE_HOUSE) { // 民居BuildQueue
			buildMap = role.getBuild().getHouseMap();
		}
		if (type == InComeConstant.TYPE_FARM) {
			buildMap = role.getBuild().getFarmLvMap();
		}

		int baseValue = this.getBaseHourIncomeValue(role, type);// 基础值
		int attachAddDelta = this.getHourIncomeValue(role, type) - baseValue;// 配属增益
		int mainCityAddDelta = 0;// 官邸增益

		message.putShort(ErrorCode.SUCCESS);
		message.put(type);
		message.putInt(this.getHourIncomeValue(role, type) + mainCityAddDelta);
		message.putInt(baseValue);
		message.putInt(attachAddDelta);
		message.putInt(mainCityAddDelta);
		message.put(maxUnlock);
		for (int i = 1; i <= maxUnlock; i++) {
			
			byte buildId = (byte)i;
			int buildLvInt = buildMap.get(i);
			byte buildLv = (byte)buildLvInt;
			int income = 0;
			int upIncome = 0;
			int upMoney = 0;
			int upTime = 0;
			int imgId = 0;
			int lvLimit = 0;
			byte status = 0;
			
			BuildConfig buildConfig = BuildConfigCache.getBuildConfig(buildLv, type);
			income = buildConfig.getInCome();
			
			BuildConfig buildConfig2 = BuildConfigCache.getBuildConfig((buildLv + 1), type);
			if (buildConfig2 != null) {
				upIncome = buildConfig2.getInCome();
				upMoney = buildConfig.getUpMoney();
				upTime = buildConfig.getUpTime();
			} else {
				upIncome = InComeConstant.NO_HERO;
				upMoney = InComeConstant.NO_HERO;
				upTime = InComeConstant.NO_HERO;
			}

			int chapterId = BuildingUnlockCache.getBuildingUnlockMaps().get(type).get((byte) i).getTroopData();
			ChapterConfig x = ChapterConfigCache.getChapterConfigById(chapterId);
			imgId = x == null ? InComeConstant.NO_HERO : x.getImageid();
			
			lvLimit = BuildingUnlockCache.getBuildingUnlockMaps().get(type).get((byte) i).getLvLimit();
			
			short mainBuildLvShort = buildTypeLv;
			byte mainBuildLv = (byte)mainBuildLvShort;
			//检查状态
			if(buildLv >0){//可升级
				status = 1;
			}else if(buildLv == 0 && mainBuildLv>=lvLimit){//可攻打
				status=2;
			}else{//不可攻打不可升级
				status = 3;
			}
			
			message.put(buildId);
			message.put(buildLv);
			message.putInt(income);
			message.putInt(upIncome);
			message.putInt(upMoney);
			message.putInt(upTime);
			message.putInt(imgId);
			message.putInt(lvLimit);
			message.put(status);
		}
		return message;
	}

	public Message getBuildQueueData(Role role) {
		Message message = new Message();
		message.setType(InComeConstant.GET_BUILD_QUEUE_DATA);
		this.refreshBuildTime(role);
		Map<Byte, BuildQueue> buildQueueMap = new HashMap<Byte, BuildQueue>();
		buildQueueMap = role.getBuildQueueMap();
		message.put((byte) buildQueueMap.size());
		for (byte j = 1; j <= buildQueueMap.size(); j++) {
			message.put((byte) buildQueueMap.get(j).getId());
			message.putInt(buildQueueMap.get(j).getTime());
			message.put(buildQueueMap.get(j).getOpen());
		}
		return message;
	}

	/**
	 * 获取空闲的建筑队列id
	 * 
	 * @param role
	 * @param type
	 * @return
	 */
	private byte getIdleBuildQueue(Role role) {
		byte queueID = 0;
		Map<Byte, BuildQueue> buildQueueMap = new HashMap<Byte, BuildQueue>();
		buildQueueMap = role.getBuildQueueMap();
		for (Map.Entry<Byte, BuildQueue> entry : buildQueueMap.entrySet()) {
			if (entry.getValue().getOpen() == 0) {
				queueID = (byte) entry.getValue().getId();
				break;
			}
		}
		return queueID;
	}

	public Message openNewBuildQueue(Role role) {
		Message message = new Message();
		message.setType(InComeConstant.OPEN_NEW_BUILD_QUEUE);
		Map<Byte, BuildQueue> buildQueueMap = new HashMap<Byte, BuildQueue>();
		buildQueueMap = role.getBuildQueueMap();
		// 达到最大建筑队列数量
		if (buildQueueMap.size() >= InComeConstant.MAX_QUEUE_NUM) {
			message.putShort(ErrorCode.MAX_BUILD_QUEUE_NUM);
			return message;
		}
		// 新的建筑队列ID
		byte newQueueId = (byte) (buildQueueMap.size() + 1);
		int needGold = BuildQueueConfigCache.getGoldById(newQueueId);

		if (role.getGold() < needGold) { // 金钱不足
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}

		BuildQueue newBuildQueue = new BuildQueue();
		newBuildQueue.setId(newQueueId);
		newBuildQueue.setLastUpTime(0);
		newBuildQueue.setOpen((byte) 0);
		newBuildQueue.setTime(0);
		role.getBuildQueueMap().put(newQueueId, newBuildQueue);
		// 用户扣除金钱
		roleService.addRoleGold(role, -needGold);
		role.setChange(true);
		role.getBuild().setChange(true);
		this.taskService.checkQueue(role, (byte) 2);

		message.putShort(ErrorCode.SUCCESS);
		message.put(newQueueId);
		message.putInt(needGold);
		return message;
	}

	/**
	 * 通过粮食补满人口
	 */
	public Message FillPopulation(Role role) {
		Message message = new Message();
		message.setType(InComeConstant.FILL_POPULATION);
		// 增加的人口数量 人口上限-当前人口
		int addPopulation = role.getPopulationLimit(role) - role.getPopulation(role);
		// 消耗的粮食
		int consumptionFood = (int) addPopulation * (int) InComeConstant.POPULATION_FOOD;
		// 粮食不足
		if (consumptionFood > role.getFood()) {
			message.putShort(ErrorCode.NO_FOOD);
		} else if ((int) addPopulation <= 0) {
			message.putShort(ErrorCode.POPULTION_OVER);
		} else {
			message.putShort(ErrorCode.SUCCESS);
			roleService.addRolePopulation(role, addPopulation);
			roleService.addRoleFood(role, -consumptionFood);
			message.putInt(addPopulation);
			message.putInt(consumptionFood);
			role.setChange(true);
		}
		return message;
	}

	@Override
	public Message clearBuildQueueTime(Role role, byte id) {
		Message message = new Message();
		message.setType(InComeConstant.CLEAR_BUILD_QUEUE_TIME);
		if (role.getBuildQueueMap().containsKey(id)) {
			// 获取建筑队列cd时间
			int time = role.getBuildQueueMap().get(id).getTime();
			if (time > 0) {
				// 消耗的金币
				int consumeGold = (time + InComeConstant.TIME_UNIT - 1) / InComeConstant.TIME_UNIT;
				if (role.getGold() >= consumeGold) {
					roleService.addRoleGold(role, -consumeGold);
					role.getBuildQueueMap().get(id).setTime(0);
					message.putShort(ErrorCode.SUCCESS);
				} else {
					message.putShort(ErrorCode.NO_GOLD);
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		this.taskService.checkQueue(role, (byte) 1);
		return message;
	}

	@Override
	public void checkGetBuild(Role role) {
		int lv = Integer.valueOf(role.getLv());
		LevelLimit LevelLimit1 = LevelLimitCache.getLevelLimitByLv(lv);
		LevelLimit LevelLimit2 = LevelLimitCache.getLevelLimitByLv((lv - 1));
		if (LevelLimit1 != null && LevelLimit2 != null) {

			if (LevelLimit1.getHouseNum() != LevelLimit2.getHouseNum()) {
				sendGetBuild(role, InComeConstant.TYPE_HOUSE, LevelLimit1.getHouseNum());
			}
			if (LevelLimit1.getFarmNum() != LevelLimit2.getFarmNum()) {
				sendGetBuild(role, InComeConstant.TYPE_FARM, LevelLimit1.getFarmNum());
			}
		}
	}

	private void sendGetBuild(Role role, byte type, int buildId) {
		Message message = new Message();
		message.setType(InComeConstant.SEND_GET_BUILD);
		int buildLv = role.getBuild().getHouseMap().get((int) buildId);
		message.put(type);
		message.putInt(buildId);
		message.putInt(buildLv);
		BuildConfig buildConfig = BuildConfigCache.getBuildConfig(buildLv, type);
		BuildConfig buildConfig2 = BuildConfigCache.getBuildConfig((buildLv + 1), type);
		message.putInt(buildConfig.getInCome());
		if (buildConfig2 != null) {
			message.putInt(buildConfig2.getInCome());
			message.putInt(buildConfig.getUpMoney());
			message.putInt(buildConfig.getUpTime());
		} else {
			message.putInt(0);
			message.putInt(0);
			message.putInt(0);
		}
		IoSession session = SessionCache.getSessionById(role.getId());
		session.write(message);
	}

	@Override
	public Message getNeedGold(Role role) {
		Message message = new Message();
		message.setType(InComeConstant.GET_NEED_GOLD);
		Map<Byte, BuildQueue> buildQueueMap = role.getBuildQueueMap();
		// 达到最大建筑队列数量
		if (buildQueueMap.size() >= InComeConstant.MAX_QUEUE_NUM) {
			message.putShort(ErrorCode.MAX_BUILD_QUEUE_NUM);
			return message;
		}
		// 新的建筑队列ID
		byte newQueueId = (byte) (buildQueueMap.size() + 1);
		int needGold = BuildQueueConfigCache.getGoldById(newQueueId);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(needGold);
		message.put(newQueueId);

		return message;
	}

	/**
	 * 配属武将
	 */
	public Message attachHero(Role role, int heroId, byte type) {
		Message message = new Message();
		message.setType(InComeConstant.ATTACH_HERO);
		int nowTime = Utils.getNowTime();
		/*
		 * 1有驻守则取消原驻守建筑的对应关系 2没有则直接添加配属关系
		 */
		int attachManualUse = GeneralNumConstantCache.getValue("ATTACH_MANUAL_USE");
		int attachManualReturnTime = GeneralNumConstantCache.getValue("ATTACH_MANUAL_RETURN_TIME");
		int attachManualReturnNum = GeneralNumConstantCache.getValue("ATTACH_MANUAL_RETURN_NUM");

		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null)
			return null;
		// 刷新武将体力和库存
		this.checkHeroManual(role,nowTime);
		// 检查体力
		if (hero.getManual() < attachManualUse) {
			message.putShort(ErrorCode.NO_MANUAL);
			return message;
		}
		if(type == InComeConstant.TYPE_MAIN_CITY){
			roleService.refreshGetPrestige(role, nowTime);
		}
		if (type == InComeConstant.TYPE_PUB) {
			pubService.refreshGetWines(role, nowTime);
		}
		if (type == InComeConstant.TYPE_FORGE) {
			propService.refreshGetIron(role, nowTime);
		}
		if(type == InComeConstant.TYPE_CAMP){
			barrackService.refreshGetExploit(role, nowTime);
		}
		if (type == InComeConstant.TYPE_FARM) {
			this.refreshGetFood(role, nowTime);
		}
		if (type == InComeConstant.TYPE_HOUSE) {
			this.refreshGetMoney(role, nowTime);
		}
		
		// 首次配属往库里塞资源
		if (type == InComeConstant.TYPE_MAIN_CITY || type == InComeConstant.TYPE_FARM
				|| type == InComeConstant.TYPE_HOUSE || type == InComeConstant.TYPE_CAMP) {
			if (!role.getBuild().getAttachHeroMap().containsKey(type)) {
//				role.getBuild().getInComeCacheMap().put(type, 500);
				role.getBuild().addInComeCacheMap(type, 500);
			}
		}
		
		if (hero.getBulidId() != InComeConstant.NO_ATTACH) {
			BuildInfo buildInfo = role.getBuild().getAttachHeroMap().get(hero.getBulidId());
			buildInfo.setHeroId(InComeConstant.NO_ATTACH);
			buildInfo.setLastAutoTime(nowTime);
		}
		
		//中途换配属还体力
		if (role.getBuild().getAttachHeroMap().get(type) != null
				&& role.getBuild().getAttachHeroMap().get(type).getHeroId() != InComeConstant.NO_HERO) {
			Hero hero2 = role.getHeroMap().get(role.getBuild().getAttachHeroMap().get(type).getHeroId());
			hero2.setBulidId(InComeConstant.NO_ATTACH);
			// 判断上次配属是否超过三个小时
			int lastTime = role.getBuild().getAttachHeroMap().get(type).getLastTime();
			if (nowTime - lastTime <= attachManualReturnTime) {
				hero2.addManual(attachManualReturnNum);
			}
			hero2.setChange(true);
		}
		
		hero.setBulidId(type);
		hero.addManual(-attachManualUse);
		int checkTime = GeneralNumConstantCache.getValue("ATTACH_AUTO_TIME");

		BuildInfo buildInfo = role.getBuild().getAttachHeroMap().get(type);
		if (buildInfo == null) {
			buildInfo = new BuildInfo();
			buildInfo.setType(type);
			role.getBuild().getAttachHeroMap().put(type, buildInfo);
		}
		buildInfo.setHeroId(heroId);
		buildInfo.setLastTime(nowTime);
		buildInfo.setLastAutoTime(nowTime + checkTime);
		
		//更新上次库存刷新时间
		role.getBuild().getBuildLastIncomeTimeMap().put(type, (long) nowTime);

		role.getBuild().setChange(true);
		hero.setChange(true);

		this.buildInfoChange(role, type, InComeConstant.CHANGE_TYPE_ATTACH, nowTime);
		this.buildInfoChange(role, type, InComeConstant.CHANGE_TYPE_INCOME, nowTime);
		
		taskService.checkInComeTask(role, InComeConstant.TYPE_HOUSE, TaskConstant.TYPE_BYTE_1);
		taskService.checkInComeTask(role, InComeConstant.TYPE_FARM, TaskConstant.TYPE_BYTE_1);
		this.taskService.checkAttach(role, type);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(heroId);
		message.put(type);
		
		return message;
	}

	/**
	 * 取消配属
	 */
	public Message cancelAttach(Role role, byte type) {
		Message message = new Message();
		message.setType(InComeConstant.CANCEL_ATTACH);
		int nowTime = Utils.getNowTime();
		this.checkHeroManual(role,nowTime);
		
		if(type == InComeConstant.TYPE_MAIN_CITY){
			roleService.refreshGetPrestige(role, nowTime);
		}
		if (type == InComeConstant.TYPE_PUB) {
			pubService.refreshGetWines(role, nowTime);
		}
		if (type == InComeConstant.TYPE_FORGE) {
			propService.refreshGetIron(role, nowTime);
		}		
		if(type == InComeConstant.TYPE_CAMP){
			barrackService.refreshGetExploit(role, nowTime);
		}
		if(type == InComeConstant.TYPE_FARM){
			this.refreshGetFood(role, nowTime);
		}
		if(type == InComeConstant.TYPE_HOUSE){
			this.refreshGetMoney(role, nowTime);
		}
		
		//刷新库存存储时间
		role.getBuild().getBuildLastIncomeTimeMap().put(type, (long) nowTime);
		
		if (role.getBuild().getAttachHeroMap().get(type) != null
				&& role.getBuild().getAttachHeroMap().get(type).getHeroId() != InComeConstant.NO_HERO) {
			role.getHeroMap().get(role.getBuild().getAttachHeroMap().get(type)).setBulidId(InComeConstant.NO_ATTACH);
		}
		role.getBuild().getAttachHeroMap().put(type, null);

		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

//	@Override
//	public Message getSimpleBuildData(Role role, byte type) {
//		Message message = new Message();
//		message.setType(InComeConstant.GET_SIMPLE_BUILD_DATA);
//		Build build = role.getBuild();
//		Map<Byte, Short> buildLvMap = build.getBuildLvMap();
//		// // 主城的配属武将
//		// Integer mainCityAttachHeroId
//		// =build.getAttachHeroMap().containsKey(InComeConstant.TYPE_MAIN_CITY)?
//		// build.getAttachHeroMap().get(InComeConstant.TYPE_MAIN_CITY).getHeroId()
//		// : null;
//		// Hero mainCityAttachHero = mainCityAttachHeroId == null
//		// ? null
//		// : role.getHeroMap().get(mainCityAttachHeroId);
//
//		Short buildLv = buildLvMap.get(type);
//		if (buildLv == null) {
//			return null;
//		}
//		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(type, (int) buildLv);
//		int money = mainBuildingConfig.getUpMoney();
//		int baseLvUpTime = mainBuildingConfig.getUpTime();
//
//		Integer heroId = 0;
//		BuildInfo buildInfo = build.getAttachHeroMap().containsKey(type)
//			? build.getAttachHeroMap().get(type)
//			: null;
//		if (buildInfo != null) {
//			heroId = buildInfo.getHeroId();
//		}
//
//		int rebirthLv = 0;
//		int lvUpTime = this.getBuildLvUpCdTime(build, baseLvUpTime);
//		int captainAttrValue = 0;
//		int reduceValue = 0;
//		int powerAttrValue = 0;
//		int powerHourValue = 0;
//		int intelAttrValue = 0;
//		int intelHourValue = 0;
//		int heroLastAttachTime = 0;
//		byte canLevy = (byte) (this.checkLevyOutOfRange(role, type)?1:0);
//		int remainLevyTimes = this.getRemainLevyTimes(role, type);
//		int needGold = this.getNextLevyNeedGold(role, type);
//		int remainFreeLevyTimes = this.getRemainFreeLevyTimes(role, type);
//
//		if (heroId == null || heroId == InComeConstant.NO_HERO) {
//			heroId = 0;
//		} else {
//			Hero hero = role.getHeroMap().get(heroId);
//			rebirthLv = hero.getRebirthLv();
//			heroLastAttachTime = buildInfo.getLastTime();
//
//			if (type == InComeConstant.TYPE_FARM) {
//				powerAttrValue = HeroUtils.getPowerValue(hero);
//				powerHourValue = this.getHourIncomeValue(role, type);
//			}
//			if (type == InComeConstant.TYPE_HOUSE) {
//				intelAttrValue = HeroUtils.getIntelValue(hero);
//				intelHourValue = this.getHourIncomeValue(role, type);
//			}
//			if (type == InComeConstant.TYPE_PUB) {
//				intelAttrValue = HeroUtils.getIntelValue(hero);
//				intelHourValue = this.getHourIncomeValue(role, type);
//
//				captainAttrValue = HeroUtils.getCaptainValue(hero);
//				reduceValue = getPubTalkReduceDelta(hero);
//			}
//			if (type == InComeConstant.TYPE_FORGE) {
//				powerAttrValue = HeroUtils.getPowerValue(hero);
//				powerHourValue = this.getHourIncomeValue(role, type);
//
//				intelAttrValue = HeroUtils.getIntelValue(hero);
//				intelHourValue = getPropStrengthAddDelta(hero);
//			}
//			if (type == InComeConstant.TYPE_TECH) {
//				intelAttrValue = HeroUtils.getIntelValue(hero);
//				intelHourValue = this.getHourIncomeValue(role, type);
//			}
//			if (type == InComeConstant.TYPE_CAMP) {
//				powerAttrValue = HeroUtils.getPowerValue(hero);
//				powerHourValue = this.getHourIncomeValue(role, type);
//			}
//			if(type == InComeConstant.TYPE_MARKET){
//				captainAttrValue = HeroUtils.getCaptainValue(hero);
//				reduceValue = formula(0, captainAttrValue, type, HeroAttrType.captain);
//				
//				intelAttrValue = HeroUtils.getIntelValue(hero);
//				intelHourValue = formula(0, intelAttrValue, type, HeroAttrType.intel);
//			}
//		}
//
//		message.putInt(lvUpTime);
//		message.putInt(money);
//		message.putInt(buildLv);
//		message.putInt(heroId);
//		message.putInt(rebirthLv);
//		message.putInt(captainAttrValue);
//		message.putInt(reduceValue);
//		message.putInt(intelAttrValue);
//		message.putInt(intelHourValue);
//		message.putInt(powerAttrValue);
//		message.putInt(powerHourValue);
//		message.putInt(heroLastAttachTime);
//		message.put(canLevy);
//		message.putInt(remainLevyTimes);
//		message.putInt(needGold);
//		message.putInt(remainFreeLevyTimes);
//		message.putString(this.getIconUnlockConfigByBuildType(type).getName());
//		
//
//		return message;
//	}
	
	@Override
	public Message getSimpleBuildData(Role role, byte type) {
		Message message = new Message();
		message.setType(InComeConstant.GET_SIMPLE_BUILD_DATA);
		Build build = role.getBuild();
		Map<Byte, Short> buildLvMap = build.getBuildLvMap();
		// // 主城的配属武将
		// Integer mainCityAttachHeroId
		// =build.getAttachHeroMap().containsKey(InComeConstant.TYPE_MAIN_CITY)?
		// build.getAttachHeroMap().get(InComeConstant.TYPE_MAIN_CITY).getHeroId()
		// : null;
		// Hero mainCityAttachHero = mainCityAttachHeroId == null
		// ? null
		// : role.getHeroMap().get(mainCityAttachHeroId);

		
		Short buildLv = buildLvMap.get(type);
		if (buildLv == null) {
			if (type == InComeConstant.TYPE_MAIN_CITY) {
				buildLv = role.getLv();
			} else {
				return null;
			}
		}
		int money = 0;
		int baseLvUpTime = 0;
		if (type != InComeConstant.TYPE_MAIN_CITY) {
			MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(type, (int) buildLv);
			money = mainBuildingConfig.getUpMoney();
			baseLvUpTime = mainBuildingConfig.getUpTime();
		}else{
			MainCityConfig roleExpConfig = MainCityConfigCache.getMainCityConfig((int) role.getLv());
			baseLvUpTime = roleExpConfig.getUpgradeTime();
			money = roleService.getRoleLvUpNeedGold((int)role.getLv(), roleExpConfig);
		}

		Integer heroId = 0;
		BuildInfo buildInfo = build.getAttachHeroMap().containsKey(type)
			? build.getAttachHeroMap().get(type)
			: null;
		if (buildInfo != null) {
			heroId = buildInfo.getHeroId();
		}

		int rebirthLv = 0;
		int lvUpTime = this.getBuildLvUpCdTime(build,baseLvUpTime);
		int captainAttrValue = 0;
		int reduceValue = 0;
		int powerAttrValue = 0;
		int powerHourValue = 0;
		int intelAttrValue = 0;
		int intelHourValue = 0;
		int heroLastAttachTime = 0;
//		byte canLevy = (byte) (this.checkLevyOutOfRange(role, type)?1:0);
		byte canLevy = 0;
//		int remainLevyTimes = this.getRemainLevyTimes(role, type);
		int remainLevyTimes = 0;
//		int needGold = this.getNextLevyNeedGold(role, type);
		int needGold = 0;
//		int remainFreeLevyTimes = this.getRemainFreeLevyTimes(role, type);		
		int remainFreeLevyTimes = 0;

		if (heroId == null || heroId == InComeConstant.NO_HERO) {
			heroId = 0;
		} else {
			Hero hero = role.getHeroMap().get(heroId);
			rebirthLv = hero.getRebirthLv();
			heroLastAttachTime = buildInfo.getLastTime();

			if(type == InComeConstant.TYPE_MAIN_CITY){
				captainAttrValue = HeroUtils.getCaptainValue(hero);
				powerHourValue = HeroUtils.getPowerValue(hero);
				intelHourValue = HeroUtils.getIntelValue(hero);
				reduceValue = this.getHourIncomeValue(role, InComeConstant.TYPE_MAIN_CITY);
				powerAttrValue = InComeUtils.getValuePower(role, hero);
				intelAttrValue = InComeUtils.getValueIn(role, hero);
				heroLastAttachTime = buildInfo.getLastTime();
			}
			if (type == InComeConstant.TYPE_FARM) {
				powerAttrValue = HeroUtils.getPowerValue(hero);
				powerHourValue = this.getHourIncomeValue(role, type);
			}
			if (type == InComeConstant.TYPE_HOUSE) {
				intelAttrValue = HeroUtils.getIntelValue(hero);
				intelHourValue = this.getHourIncomeValue(role, type);
			}
			if (type == InComeConstant.TYPE_PUB) {
				intelAttrValue = HeroUtils.getIntelValue(hero);
				intelHourValue = this.getHourIncomeValue(role, type);

				captainAttrValue = HeroUtils.getCaptainValue(hero);
				reduceValue = getPubTalkReduceDelta(hero);
			}
			if (type == InComeConstant.TYPE_FORGE) {
				powerAttrValue = HeroUtils.getPowerValue(hero);
				powerHourValue = this.getHourIncomeValue(role, type);

				intelAttrValue = HeroUtils.getIntelValue(hero);
				intelHourValue = getPropStrengthAddDelta(hero);
			}
			if (type == InComeConstant.TYPE_TECH) {
				intelAttrValue = HeroUtils.getIntelValue(hero);
				intelHourValue = this.getHourIncomeValue(role, type);
			}
			if (type == InComeConstant.TYPE_CAMP) {
//				powerAttrValue = HeroUtils.getPowerValue(hero);
//				powerHourValue = this.getHourIncomeValue(role, type);
				captainAttrValue = HeroUtils.getCaptainValue(hero);
				reduceValue = this.getHourIncomeValue(role,type);
			}
			if(type == InComeConstant.TYPE_MARKET){
				captainAttrValue = HeroUtils.getCaptainValue(hero);
				reduceValue = (int) formula(0, captainAttrValue, type, HeroAttrType.captain,hero.getRank());
				
				intelAttrValue = HeroUtils.getIntelValue(hero);
				intelHourValue = (int) formula(0, intelAttrValue, type, HeroAttrType.intel,hero.getRank());
			}
		}

		message.putInt(lvUpTime);
		message.putInt(money);
		message.putInt(buildLv);
		message.putInt(heroId);
		message.putInt(rebirthLv);
		message.putInt(captainAttrValue);
		message.putInt(reduceValue);
		message.putInt(intelAttrValue);
		message.putInt(intelHourValue);
		message.putInt(powerAttrValue);
		message.putInt(powerHourValue);
		message.putInt(heroLastAttachTime);
		message.put(canLevy);
		message.putInt(remainLevyTimes);
		message.putInt(needGold);
		message.putInt(remainFreeLevyTimes);
		message.putString(this.getIconUnlockConfigByBuildType(type).getName());
		

		return message;
	}

	@Override
	public Message getResource(Role role, byte type, int num) {
		Message message = new Message();
		message.setType(InComeConstant.GET_NUM_RESOURCE);
		int nowTime = Utils.getNowTime();
		this.refreshLevyData(role, nowTime);
		Build build = role.getBuild();
		int cacheValue = 0;
		
		
		Map<Byte, Integer> incomeCacheMap = build.getInComeCacheMap();

		cacheValue = incomeCacheMap.get(type);

		if (cacheValue < num) {
			message.putShort(ErrorCode.NO_RESOURCE);
			return message;
		}

		// 资源池更新
		addValueToCache(role, type, -num);
		
		int remainCacheValue = incomeCacheMap.get(type);
		ChapterConstant.Award resourceType = getResourceType(type);

		// 属城相关
		if (type == InComeConstant.TYPE_FARM || type == InComeConstant.TYPE_HOUSE) {
			if (role.getRoleCity().getMyLordRoleId() != InComeConstant.NO_HERO) {
				Role lordRole = roleService.getRoleById(role.getRoleCity().getMyLordRoleId());
				if (lordRole == null)
					return message;
				int disNum = (int) (num * GeneralNumConstantCache.getValue("PUMPED_INTO_NUM") / 100.0);
				num = num - disNum;
				int value = disNum * 2;
				lordRole.getRoleCity().addValue(role.getId(), value, type);
				switch (type) {
				case InComeConstant.TYPE_FARM:
					roleService.addRoleFood(lordRole, value);
					break;
				case InComeConstant.TYPE_HOUSE:
					roleService.addRoleMoney(lordRole, value);
					break;
				default:
					break;
				}
				// 记录上交信息
				History history = this.makeStr(lordRole, type, disNum);
				role.addPumHistoryStr(history);
			}
		}
		IoSession is = SessionCache.getSessionById(role.getId());
		if(type == InComeConstant.TYPE_MAIN_CITY){
			roleService.addRolePrestige(role, num);
		}if (type == InComeConstant.TYPE_FARM) {
			roleService.addRoleFood(role, num);
		} else if (type == InComeConstant.TYPE_HOUSE) {
			roleService.addRoleMoney(role, num);
		} else if(type == InComeConstant.TYPE_CAMP){
			roleService.addRoleExploit(role, num);
		}else if(type == InComeConstant.TYPE_FORGE){
			propService.addProp(role, PropConstant.PROP_ORE, PropConstant.FUNCTION_TYPE_2, num, is);
		}else if(type == InComeConstant.TYPE_PUB){
			propService.addProp(role, PubConstant.PROP_WINE_ID, PropConstant.FUNCTION_TYPE_2, num, is);
		}
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(remainCacheValue);// 剩余的数量
		message.putInt(num);// 征收的数量
		message.put(resourceType.getVal());// 征收类型
		
		this.recordIncomeRank(role, type, num, nowTime);
		
		return message;
	}

	@Override
	public void recordIncomeRank(Role role, byte type, int num, int nowTime) {
		Build build = role.getBuild();
		int lastDayIncomeValue = build.getIncomeLastDayCacheMap().get(type);
		rankService.addIncomeRank(role, nowTime, type, num - lastDayIncomeValue);
		build.getIncomeLastDayCacheMap().put(type, 0);
	}

	/**
	 * 自动检测配属武将体力 用于24小时自动扣除体力
	 * 如果已经到时间并取消了配属则刷新所有建筑的库存
	 * 
	 * @param role
	 */
	@Override
	public void checkHeroManual(Role role,int nowTime) {
		int checkTime = GeneralNumConstantCache.getValue("ATTACH_AUTO_TIME");
		for (BuildInfo buildInfo : role.getBuild().getAttachHeroMap().values()) {
			if (buildInfo == null || buildInfo.getHeroId() == InComeConstant.NO_HERO) {
				continue;
			}
			Hero hero = role.getHeroMap().get(buildInfo.getHeroId());
			// 判断时间是否达到
			if (Utils.getRemainTime(nowTime, buildInfo.getLastTime(), checkTime) == 0) {
				// 判断体力是否足够
//				if (hero.getManual() >= GeneralNumConstantCache.getValue("ATTACH_MANUAL_USE")) {
//					hero.addManual(-GeneralNumConstantCache.getValue("ATTACH_MANUAL_USE"));
//					x.setLastAutoTime(x.getLastAutoTime() + GeneralNumConstantCache.getValue("ATTACH_AUTO_TIME"));
//					hero.setChange(true);
//				} else {
					if(buildInfo.getType() == InComeConstant.TYPE_MAIN_CITY){
						roleService.refreshGetPrestige(role, buildInfo.getLastAutoTime());
					}
					if (buildInfo.getType() == InComeConstant.TYPE_PUB) {
						pubService.refreshGetWines(role, buildInfo.getLastAutoTime());
					}
					if (buildInfo.getType() == InComeConstant.TYPE_FORGE) {
						propService.refreshGetIron(role, buildInfo.getLastAutoTime());
					}
					if(buildInfo.getType() == InComeConstant.TYPE_CAMP){
						barrackService.refreshGetExploit(role, buildInfo.getLastAutoTime());
					}
					if (buildInfo.getType() == InComeConstant.TYPE_FARM) {
						this.refreshGetFood(role, buildInfo.getLastAutoTime());
					}
					if (buildInfo.getType() == InComeConstant.TYPE_HOUSE) {
						this.refreshGetMoney(role, buildInfo.getLastAutoTime());
					}
					
					hero.setBulidId(InComeConstant.NO_ATTACH);
					buildInfo.setHeroId(InComeConstant.NO_HERO);
					hero.setChange(true);
					role.getBuild().setChange(true);
//					Mail mail = new Mail();
//					mail.setTitle("系统邮件");
//					mail.setContext(HeroConfigCache.getHeroConfigById(hero.getHeroId()) + "由于体力不足配属中断了！");
//					mailService.sendSYSMail2(role, mail);
//				}
			}
		}
	}

	
	/**
	 * 刷新粮草库存
	 * @param role
	 * @param nowTime
	 * @author wcy 2016年1月19日
	 */
	@Override
	public void refreshGetFood(Role role, long nowTime) {
		byte type = InComeConstant.TYPE_FARM;
		this.refreshGetResources(role, nowTime, type);
	}

	/**
	 * 刷新银币库存
	 * @param role
	 * @param nowTime
	 * @author wcy 2016年1月19日
	 */
	@Override
	public void refreshGetMoney(Role role, long nowTime) {
		byte type = InComeConstant.TYPE_HOUSE;
		this.refreshGetResources(role, nowTime, type);
	}
	
	/**
	 * 刷新资源库存的统一流程算法（主城,农田,民居,酒馆,锻造坊,军事区）
	 * @param role
	 * @param nowTime
	 * @param type
	 * @author wcy 2016年1月19日
	 */
	public void refreshGetResources(Role role, long nowTime, byte type) {
		Build build = role.getBuild();
		// 没有配属则返回
		BuildInfo buildInfo = build.getAttachHeroMap().get(type);
		if (buildInfo == null || buildInfo.getHeroId() == InComeConstant.NO_HERO) {
			return;
		}

		int cdTime = GeneralNumConstantCache.getValue("BUILD_INCOME_CD");

		int inCome = this.getHourIncomeValue(role, type);
		Map<Byte, Long> lastIncomeTimeMap = build.getBuildLastIncomeTimeMap();
		// 获得上次刷新时间
		long startTime = lastIncomeTimeMap.get(type);

		// 有效收益的秒数
		int second = (int) (nowTime - startTime);

		// 以每秒钟为单位计算出的总收益
		double getNum = inCome * second / cdTime;
		Map<Byte, Integer> incomeCacheMap = build.getInComeCacheMap();
		Integer cache = incomeCacheMap.get(type);

		// 献策增益
		int techAdd = 0;
		LevyInfo techLevyInfo = InComeUtils.getLevyInfoByType(role, InComeConstant.TYPE_TECH);
		if (techLevyInfo != null) {
			if (type == (byte) techLevyInfo.getValue()) {
				int techCdTime = techLevyInfo.getCd();
				int techStartTime = (int) techLevyInfo.getStartTime();
				int lastRefreshTime = techLevyInfo.getLastRefreshTime();
				int heroId = techLevyInfo.getHeroId();
				Hero techHero = role.getHeroMap().get(heroId);
				int intel = HeroUtils.getIntelValue(techHero);

				int remainTime = Utils.getRemainTime((int) nowTime, techStartTime, techCdTime);
				int techDeltaSecond = 0;
				if (remainTime > 0) {// 还在献策范围内
					techDeltaSecond = (int) (nowTime - lastRefreshTime);
					// 记录时间
					techLevyInfo.setLastRefreshTime((int) nowTime);
				} else {// 已经过了献策时间
					int endTime = techStartTime + techCdTime;
					techDeltaSecond = endTime - lastRefreshTime;
					techLevyInfo.setLastRefreshTime((int) endTime);
				}
				// 献策增益				
				techAdd = this.getTechAdd(role, type, cdTime, inCome, techDeltaSecond);
				
			}
		}
		if ((int) getNum == 0) {
			return;
		}
		getNum += techAdd;
		//>>>>>>>>>>>>>>>>>>
//		getNum += cache;
//
//		cache = (int) getNum;
//		// 不可超过最大容量
//		if (cache < 0) {
//			cache = Integer.MAX_VALUE;
//		}

//		incomeCacheMap.put(type, cache);
		//<<<<<<<<<<<<<<<<<<<
		build.addInComeCacheMap(type,(int)getNum);
		lastIncomeTimeMap.put(type, (long) nowTime);
		build.setChange(true);
	}

	/**
	 * 获得献策增益
	 * @param role
	 * @param type 农田2 民居3
	 * @param cdTime
	 * @param inCome
	 * @param techDeltaSecond
	 * @return
	 * @author wcy 2016年1月22日
	 */
	private int getTechAdd(Role role,byte type, int cdTime, int inCome, int techDeltaSecond) {
		int scienceId = 0;
		if (type == InComeConstant.TYPE_HOUSE) {
			scienceId = ScienceConstant.MONEY_SCIENCE_ID;
		} else if (type == InComeConstant.TYPE_FARM) {
			scienceId = ScienceConstant.FOOD_SCIENCE_ID;			
		}
		if(scienceId == 0){
			return 0;
		}
		Integer scienceLv = role.getRoleScienceMap().get(scienceId);
		RoleScienceConfig scienceConfig = RoleScienceConfigCache.getRoleScienceConfig(scienceId, scienceLv);
		double buff = scienceConfig.getValue() / 10000.0;
		int techAdd = (int) (inCome * buff * techDeltaSecond / cdTime);
		return techAdd;
	}	

	
	/**
	 * 获得收益最后的有效时间，如果配属时间已经过了，则是最后取消配属的时间，否则为现在时间
	 * @param role 
	 * @param type
	 * @param nowTime 现在时间
	 * @return
	 * @author wcy 2016年1月21日
	 */
	private int getIncomeFinalValidTimeByType(Role role,byte type,int nowTime){
		Build build = role.getBuild();
		int resultTime = nowTime;
		
		BuildInfo buildInfo = build.getAttachHeroMap().get(type);
		if(buildInfo!=null){
			//武将取消配属时间
			int heroAttachLastTime = buildInfo.getLastAutoTime();
			if(nowTime > heroAttachLastTime){
				resultTime = heroAttachLastTime;
			}
		}
		return resultTime;
	}
	
	@Override
	public boolean checkCanSendHero(Hero hero, int value) {
		if (hero.getManual() < value) {
			return false;
		}
		return true;
	}

	/**
	 * 建筑对应资源类型，（与客户端一致）
	 * 
	 * @param buildType
	 * @return
	 * @author wcy
	 */
	// private Byte getResourceType(byte buildType) {
	// HashMap<Byte, Byte> map = new HashMap<>();
	// map.put(InComeConstant.TYPE_HOUSE, InComeConstant.RES_TYPE_SILVER);
	// map.put(InComeConstant.TYPE_FARM, InComeConstant.RES_TYPE_FOOD);
	// Byte resourceType = map.get(buildType);
	// return resourceType;
	// }

	/**
	 * 建筑对应资源（与客户端不一致）
	 * 
	 * @param buildType
	 * @return
	 * @author wcy
	 */
	private ChapterConstant.Award getResourceType(byte buildType) {
		ChapterConstant.Award award = null;
		if (buildType == InComeConstant.TYPE_FARM) {
			award = Award.FOOD;
		} else if (buildType == InComeConstant.TYPE_HOUSE) {
			award = Award.MONEY;
		} else if (buildType == InComeConstant.TYPE_MAIN_CITY) {
			award = Award.PRESTIGE;
		} else if (buildType == InComeConstant.TYPE_CAMP) {
			award = Award.EXPLOIT;
		} else if (buildType == InComeConstant.TYPE_FORGE) {
			award = Award.ITEM;
		} else if (buildType == InComeConstant.TYPE_PUB) {
			award = Award.ITEM;
		}
		return award;
	}

	/**
	 * 刷新征收数据
	 * 
	 * @param role
	 * @param nowTime
	 * @author wcy
	 */
	public void refreshLevyData(Role role, long nowTime) {
		Build build = role.getBuild();
		Map<Integer, LevyInfo> levyMap = build.getLevyMap();
		this.checkHeroManual(role,(int) nowTime);
		ArrayList<Integer> heroIdArray = new ArrayList<Integer>();
		for (LevyInfo levyInfo : levyMap.values()) {
			int heroId = levyInfo.getHeroId();
			byte type = levyInfo.getType();

			int value = levyInfo.getValue();
			int otherValue = levyInfo.getValueOther();

			long startTime = levyInfo.getStartTime();
			int cdTime = levyInfo.getCd();
			int deltaTime = Utils.getRemainTime((int)nowTime, (int)startTime, cdTime);

			if (deltaTime == 0) {
				heroIdArray.add(heroId);
				if (levyInfo.getType() == InComeConstant.TYPE_PUB) {
					addValueToCache(role.getPub(), levyInfo);
				} else if (levyInfo.getType() == InComeConstant.TYPE_FORGE) {
					addValueToTreasureCache(build, levyInfo);
				} else if (levyInfo.getType() == InComeConstant.TYPE_TECH) {
					addValueToScienceCache(role, build, levyInfo);
				} else if (levyInfo.getType() == InComeConstant.TYPE_MARKET) {
					addValueToSalesCache(role, build, levyInfo);
				} else {
					addValueToCache(role, type, value + otherValue);
				}
			}
			build.setChange(true);
		}

		// 时间到的从map中移除
		for (Integer heroId : heroIdArray) {
			levyMap.remove(heroId);
		}
	}

	
	public Message getBuildInfo(Role role) {
		Message message = new Message();
		message.setType(InComeConstant.GET_BUILD_INFO);
		Build build = role.getBuild();
		Map<String, IconUnlockConfig> map = IconUnlockConfigCache.getIconUnlockConfigMap();
		int attachAutoTime = GeneralNumConstantCache.getValue("ATTACH_AUTO_TIME");
		message.put((byte)map.size());
		for(IconUnlockConfig x :map.values())
		{
			byte state = this.checkStatBuild(x, role);
			message.put(state);
			message.putString(x.getName());
			byte type = (byte)x.getType();
			BuildInfo buildInfo = build.getAttachHeroMap().get(type);
			if(state == (byte)0 || state == (byte)1)
			{
				message.putInt(0);
				message.putInt(0);
				message.putInt(0);
				message.putInt(0);
				message.putInt(0);
			}else {
				if(x.getType() == 1)
				{
					message.putInt(role.getLv());
				}else {
					message.putInt(build.getBuildLvMap().get(type));
				}
				if(buildInfo == null)
				{
					message.putInt(0);
					message.putInt(0);
					message.putInt(0);
				}else {
					message.putInt(buildInfo.getHeroId());
					message.putInt(buildInfo.getLastTime());
					message.putInt(buildInfo.getLastTime() + attachAutoTime - Utils.getNowTime());
				}
				
				int hourIncomeValue = this.getHourIncomeValue(role, type);
				hourIncomeValue = this.getScienceHourAddValue(role, type, hourIncomeValue);
				message.putInt(hourIncomeValue);
			}
		}
		
		return message;
	}
	
	@Override
	public void buildInfoChange(Role role,byte type,byte changeType,int nowTime) {
		Message message = new Message();
		message.setType(InComeConstant.BUILD_INFO_CHANGE);
		Build build = role.getBuild();
		Map<Byte, Short> lvMap = build.getBuildLvMap();
		IconUnlockConfig config = this.getIconUnlockConfigByBuildType(type);
		byte status = this.checkStatBuild(config, role);
		int lv = 0;
		int heroId = 0;
		int hourIncomeValue = 0;
		int attachStartTime = 0;
		int attachRemainTime = 0;
		String key = config.getName();

		// 等级改变
		if (type == InComeConstant.TYPE_MAIN_CITY) {
			lv = role.getLv();
		} else {
			if (lvMap.containsKey(type)) {
				lv = lvMap.get(type);
			} else {
				//提示新建筑
				message.put(changeType);
				message.put(status);
				message.putString(key);
				message.putInt(lv);
				message.putInt(heroId);
				message.putInt(hourIncomeValue);
				message.putInt(attachStartTime);
				message.putInt(attachRemainTime);
				IoSession session = SessionCache.getSessionById(role.getId());
				session.write(message);
				return;
			}
		}

		// 收入改变
		hourIncomeValue = this.getHourIncomeValue(role, type);
		hourIncomeValue = this.getScienceHourAddValue(role, type, hourIncomeValue);
		// 配属改变
		int attachAutoTime = GeneralNumConstantCache.getValue("ATTACH_AUTO_TIME");
		BuildInfo buildInfo = build.getAttachHeroMap().get(type);
		if (buildInfo != null) {
			int attachHeroId = buildInfo.getHeroId();
			if (attachHeroId != 0) {
				attachStartTime = buildInfo.getLastTime();
				attachRemainTime = buildInfo.getLastTime() + attachAutoTime - nowTime;
				heroId = buildInfo.getHeroId();
			}
		}

		message.put(changeType);
		message.put(status);
		message.putString(key);
		message.putInt(lv);
		message.putInt(heroId);
		message.putInt(hourIncomeValue);
		message.putInt(attachStartTime);
		message.putInt(attachRemainTime);
		IoSession session = SessionCache.getSessionById(role.getId());
		session.write(message);
	}
		
	/**
	 * 如果有献策则加上科技加成
	 * @param role
	 * @param type
	 * @param hourIncomeValue
	 * @return
	 */
	private int getScienceHourAddValue(Role role, byte type, int hourIncomeValue) {
		Build build = role.getBuild();
		Map<Integer,LevyInfo> levyMap = build.getLevyMap();

		for ( LevyInfo levyInfo : levyMap.values()) {
			byte levyInfoType = levyInfo.getType();
			if (levyInfoType != type)
				continue;
			return this.getTechHourIncomeValue(role, hourIncomeValue, type);
		}
		return hourIncomeValue;
	}
	
	/**
	 * 征收
	 * 
	 * @param role 玩家
	 * @param type 征收的资源建筑类型
	 * @param heroId 征收的英雄id
	 * @return
	 * @author wcy
	 */
//	private void levy(Message message, Role role, byte type, int heroId) {
//		long nowTime = Utils.getNowTime();
////		this.refreshLevyData(role, nowTime);
//		Build build = role.getBuild();
//		// 得到配属武将
//		// Map<Byte, Integer> attachHeroMap = build.getAttachHeroMap();
//		// Integer attachHeroId = attachHeroMap.get(type);
//		// Hero attachHero = attachHeroId == null ? null :
//		// role.getHeroMap().get(attachHeroId);
//
//		Map<Integer, LevyInfo> heroLevyMap = build.getLevyMap();
//		// 检查武将是否在征收中
//		if (heroLevyMap.containsKey(heroId)) {
//			message.putShort(ErrorCode.HERO_IN_LEVY);
//			return;
//		}
//		// 检查该类型资源区是否已经在征收
//		boolean isLevy = false;
//		int scienceAddValue = 0;
//
//		for (LevyInfo levyInfo : heroLevyMap.values()) {
//			byte levyType = levyInfo.getType();
//			if (type == levyType) {
//				isLevy = true;
//			}
//			if (levyType == InComeConstant.TYPE_TECH) {
//				if (type == (byte) levyInfo.getValue()) {
//					scienceAddValue = levyInfo.getValueOther();
//				}
//			}
//		}
//
//		if (isLevy) {// 已经在征收
//			message.putShort(ErrorCode.HERO_IN_LEVY);
//			return;
//		}
//
//		Hero levyHero = role.getHeroMap().get(heroId);
//
//		if (!this.checkCanSendHero(levyHero, GeneralNumConstantCache.getValue("USE_MANUAL_1"))) {
//			message.putShort(ErrorCode.NO_MANUAL);
//			return;
//		}
//
//		// int baseCd = getBaseCdTime(type, buildLvMap.get(type));
//
//		// int value = (int) (getHourIncomeValue(role, type) / 3600.0 * baseCd)
//		// + scienceAddValue
//		// + InComeConstant.ADD_VALUE;
//		int oneTimeIncomeValue = getHourIncomeValue(role, type);
//		Random rand = new Random();
//		float randValue = rand.nextFloat();
//		double num = randValue > scienceAddValue / 10000.0
//			? 1
//			: GeneralNumConstantCache.getValue("SCIENCE_FOOD_MONEY_DELTA") / 10000.0;
//		int value = (int) (oneTimeIncomeValue * num);
//
//		int valueOther = getValueOther(role, type);
//		int cd = getCdTime(levyHero, 0, type);
//
//		// 新建征收消息
//		LevyInfo levyInfo = new LevyInfo();
//		levyInfo.setHeroId(heroId);
//		levyInfo.setStartTime(nowTime);
//		levyInfo.setType(type);
//		levyInfo.setValue(value);
//		levyInfo.setValueOther(valueOther);
//		levyInfo.setCd(cd);
//
//		heroLevyMap.put(heroId, levyInfo);
//		build.setChange(true);
//		message.putShort(ErrorCode.SUCCESS);
//		message.putInt(levyInfo.getCd());
//
//	}
	
	/**
	 * 农田民居征收
	 * @param message
	 * @param role
	 * @param type
	 * @param heroId
	 * @author wcy 2016年1月20日
	 */
	private void levy(Message message, Role role, byte type, int heroId,byte levyType) {
		Build build = role.getBuild();
//		Map<Integer, LevyInfo> heroLevyMap = build.getLevyMap();
//		// 检查武将是否在征收中
//		if (heroLevyMap.containsKey(heroId)) {
//			message.putShort(ErrorCode.HERO_IN_LEVY);
//			return;
//		}		
		
//		// 检查该类型资源区是否已经在征收
//		if (!InComeUtils.checkLevy(role, type, heroId)) {
//			message.putShort(ErrorCode.HERO_IN_LEVY);
//			return;
//		}		
		
		//检查是否超过征收次数
		if (this.checkLevyOutOfRange(role,type,levyType)) {
			message.putShort(ErrorCode.LEVY_TIMES_OUT_RANGE);
			return;
		}

		Hero levyHero = role.getHeroMap().get(heroId);
		
		int useManual = GeneralNumConstantCache.getValue("USE_MANUAL_1");
		if (!this.checkCanSendHero(levyHero, useManual)) {
			message.putShort(ErrorCode.NO_MANUAL);
			return;
		}
		
		int total = this.getLevyIncomeValue(role, type, heroId);
		if (levyType == InComeConstant.LEVYTYPE_GOLD) {
			// 检查金币
			int needGold = this.getNextLevyNeedGold(role, type, total);
			if (role.getGold() < needGold) {
				message.putShort(ErrorCode.NO_GOLD);
				return;
			}

			if (needGold != 0) {
				roleService.addRoleGold(role, -needGold);
			}
		}
		

		levyHero.addManual(-useManual);
		
		// 获得献策增益
		int scienceAddValue = 0;
		LevyInfo levyInfo = InComeUtils.getLevyInfoByType(role, InComeConstant.TYPE_TECH);
		if (levyInfo != null) {
			byte scienceType = (byte) levyInfo.getValue();
			if (scienceType == type) {
				scienceAddValue = levyInfo.getValueOther();
			}
		}
		
		//判别征收类型，花不花钱
		Map<Byte,Integer> levyTimeMap = null;
		if(levyType == InComeConstant.LEVYTYPE_FREE){
			levyTimeMap = build.getFreeLevyTimeMap();
		}else if(levyType == InComeConstant.LEVYTYPE_GOLD){
			levyTimeMap = build.getLevyTimeMap();
		}
		
		// int baseCd = getBaseCdTime(type, buildLvMap.get(type));

		// int value = (int) (getHourIncomeValue(role, type) / 3600.0 * baseCd)
		// + scienceAddValue
		// + InComeConstant.ADD_VALUE;
		// 考虑献策的暴击加成
//		int oneTimeIncomeValue = this.getHourIncomeValue(role, type);
//		int total = oneTimeIncomeValue * 4;
		Random rand = new Random();
		float randValue = rand.nextFloat();
		double ratio = GeneralNumConstantCache.getValue("SCIENCE_FOOD_MONEY_DELTA") / 10000.0;
		double num = randValue > scienceAddValue / 10000.0 ? 1 : ratio;
		int value = (int) (total * num);

//		int valueOther = this.getValueOther(role, type);
//		int cd = getCdTime(levyHero, 0, type);
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//		int cache = build.getInComeCacheMap().get(type);
//		cache += value;
//		cache = cache < 0 ? Integer.MAX_VALUE : cache;
//		build.getInComeCacheMap().put(type, cache);
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		build.addInComeCacheMap(type, value);
		
		// 征收次数加1
		int levyTimes = levyTimeMap.containsKey(type) ? levyTimeMap.get(type) : 0;
		levyTimeMap.put(type, levyTimes + 1);	

		build.setChange(true);
		message.putShort(ErrorCode.SUCCESS);
		
//		message.put((byte) (this.checkLevyOutOfRange(role, type, levyType) ? 1 : 0));
//		message.putInt(this.getRemainLevyTimes(role, type, levyType));
//		message.putInt(this.getLevyNeedGold(role, type, levyType));
//		message.put((byte) (num == ratio ? 0 : 1));
		
		int goldRemainLevyTimes= this.getRemainLevyTimes(role,type,InComeConstant.LEVYTYPE_GOLD);
		int nextGoldCost = 0;
		if (goldRemainLevyTimes != 0) {
			LevyConfig levyConfig = LevyConfigCache.getLevyConfigByTimes(build.getLevyTimeMap().get(type) + 1);
			nextGoldCost = levyConfig == null ? 0 : levyConfig.getGoldCost();
		}
		
		message.putInt(value);
		message.put((byte) this.getRemainLevyTimes(role, type, InComeConstant.LEVYTYPE_FREE));
		message.put((byte) goldRemainLevyTimes);
		message.putInt(nextGoldCost);
		message.putInt(total);
		
	}
	
	/**
	 * 获得征收可获得产量
	 * @param type 2农田,3民居
	 * @param heroId
	 * @return
	 * @author wcy 2016年1月25日
	 */
	private int getLevyIncomeValue(Role role, byte type, int heroId) {
		int value = 0;
		if (heroId == 0)
			return value;
		Hero levyHero = role.getHeroMap().get(heroId);
		if (levyHero == null) {
			return value;
		}

		int power = HeroUtils.getPowerValue(levyHero);
		int captain = HeroUtils.getCaptainValue(levyHero);
		int intel = HeroUtils.getIntelValue(levyHero);
		int baseHourIncomeValue = this.getBaseHourIncomeValue(role, type);
		if (type == InComeConstant.TYPE_FARM) {
			value = (int) (this.formula(baseHourIncomeValue, power, type, HeroAttrType.power, levyHero.getRank()) * 6);
		} else if (type == InComeConstant.TYPE_HOUSE) {
			value = (int) (this.formula(baseHourIncomeValue, intel, type, HeroAttrType.intel, levyHero.getRank()) * 6);
		}

		return value;
	}
	
	/**
	 * 检查是否可以征收
	 * 
	 * @param role
	 * @param type
	 * @return 不可以征收返回true,可以征收返回false
	 * @author wcy 2016年1月21日
	 */
	private boolean checkLevyOutOfRange(Role role, byte type,byte levyType) {
		boolean isTable = false;
		VipConfig config = VipConfigCache.getVipConfigByVipLv(role.getVipLv());
		if(isTable){			
			Build build = role.getBuild();
			Integer times = build.getLevyTimeMap().get(type);
			if (times == null) {
				build.getLevyTimeMap().put(type, 0);
				times = 0;
			}
			if (type == 2) {
				if (times >= 0 && times <= config.getLevyFarmTimes()) {
					return false;
				}
				if (times == 4 && role.getVipLv() >= 4) {
					return false;
				}
			} else if (type == 3) {
				if (times >= 0 && times <= config.getLevyCoinTimes()) {
					return false;
				}
				if (times == 4 && role.getVipLv() >= 4) {
					return false;
				}
			}
			return true;
		}
		Build build = role.getBuild();
		int vipLv = (int)role.getVipLv();
		int totalLevyTimes = this.getTotalLevyTimes(type, vipLv,levyType);
		
		
		Map<Byte,Integer> levyTimeMap = null;
		if(levyType == InComeConstant.LEVYTYPE_FREE){
			levyTimeMap = build.getFreeLevyTimeMap();
		}else if(levyType == InComeConstant.LEVYTYPE_GOLD){
			levyTimeMap = build.getLevyTimeMap();
		}
		
		
		Integer times = levyTimeMap.get(type);
		if (times == null) {
			levyTimeMap.put(type, 0);
			times = 0;
		}
		if(times>=totalLevyTimes){
			return true;
		}
		
		return false;
	}
	/**
	 * 
	 * @return
	 * @author wcy 2016年1月21日
	 */
	private int getNextLevyNeedGold(Role role, byte type,int levyValue) {
		boolean isTable = false;
		if(isTable){
			
			Build build = role.getBuild();
			Integer times = build.getLevyTimeMap().get(type);
			if (times == null) {
				build.getLevyTimeMap().put(type, 0);
				times = 0;
			}
			times+=1;
			if (role.getVipLv() == 4&&times == 4) {
				return 300;
			}
			return 0;
		}
		
		Build build = role.getBuild();
		Integer times = build.getLevyTimeMap().get(type);
		if (times == null) {
			build.getLevyTimeMap().put(type, 0);
			times = 0;
		}
//		times+=1;
//		LevyConfig levyConfig = LevyConfigCache.getLevyConfigByTimes(times);
//		if(levyConfig==null){
//			return 0;
//		}
//		int needGold = levyConfig.getGoldCost();
		times += 1;

		int needGold = (int) Math.ceil(levyValue / 1000.0 * Math.pow(1.022, times));
		return needGold;

	}
	
	/**
	 * 
	 * @return
	 * @author wcy 2016年1月21日
	 */
	private int getLevyNeedGold(Role role, byte type,byte levyType) {
		boolean isTable = false;
		if(isTable){
			
			Build build = role.getBuild();
			Integer times = build.getLevyTimeMap().get(type);
			if (times == null) {
				build.getLevyTimeMap().put(type, 0);
				times = 0;
			}
			if (role.getVipLv() == 4&&times == 4) {
				return 300;
			}
			return 0;
		}
		
		if (levyType == InComeConstant.LEVYTYPE_FREE) {
			return 0;
		}
		Build build = role.getBuild();
		Integer times = build.getLevyTimeMap().get(type);
		if (times == null) {
			build.getLevyTimeMap().put(type, 0);
			times = 0;
		}
		LevyConfig levyConfig = LevyConfigCache.getLevyConfigByTimes(times);
		if (levyConfig == null) {
			return 0;
		}
		int needGold = levyConfig.getGoldCost();

		return needGold;

	}
	
	/**
	 * 获取剩余征收次数
	 * 
	 * @param role
	 * @param type
	 * @return
	 * @author wcy 2016年1月21日
	 */
	private int getRemainLevyTimes(Role role, byte type,byte levyType) {
		boolean isTable = false;
		if (isTable) {

			Build build = role.getBuild();
			Integer times = build.getLevyTimeMap().get(type);
			if (times == null) {
				build.getLevyTimeMap().put(type, 0);
				times = 0;
			}

			int totalTimes = 4;
			if (role.getVipLv() < 4) {
				totalTimes = 3;
			}

			int remainTimes = totalTimes - times;
			return remainTimes;
		}
		Build build = role.getBuild();
		
		Map<Byte,Integer> levyTimeMap = null;
		if(levyType == InComeConstant.LEVYTYPE_FREE){
			levyTimeMap = build.getFreeLevyTimeMap();
		}else{
			levyTimeMap = build.getLevyTimeMap();
		}
		Integer times = levyTimeMap.get(type);
		if (times == null) {
			levyTimeMap.put(type, 0);
			times = 0;
		}
		int vipLv = (int)role.getVipLv();
		int levyTimes = this.getTotalLevyTimes(type, vipLv,levyType);
		int remainTimes = levyTimes - times;
		
		return remainTimes;
	}
	
	/**
	 * 剩余免费征收次数
	 * @param role
	 * @param type
	 * @return
	 * @author wcy 2016年1月22日
	 */
	private int getRemainFreeLevyTimes(Role role,byte type,byte levyType){
		Build build = role.getBuild();
		Integer times = build.getLevyTimeMap().get(type);
		if (times == null) {
			build.getLevyTimeMap().put(type, 0);
			times = 0;
		}
		times+=1;
		int vipLv = (int)role.getVipLv();
		int totalLevy = this.getTotalLevyTimes(type, vipLv,levyType);
		
		int freeTimes = 0;
		for(;times<=totalLevy;times++){
			LevyConfig levyConfig = LevyConfigCache.getLevyConfigByTimes(times);
			if(levyConfig.getGoldCost() == 0){
				freeTimes++;
			}
		}
		
		return freeTimes;
	}
	
	/**
	 * 获得总征收次数
	 * @param type
	 * @param vipLv
	 * @return
	 * @author wcy 2016年2月2日
	 */
	private int getTotalLevyTimes(byte type, int vipLv, byte levyType) {
		VipConfig vipConfig = VipConfigCache.getVipConfigByVipLv(vipLv);
		int totalLevy = 0;
		if (type == InComeConstant.TYPE_FARM) {
			if (levyType == InComeConstant.LEVYTYPE_FREE) {
				totalLevy = vipConfig.getFreeLevyFarmTimes();
			} else {
				totalLevy = vipConfig.getLevyFarmTimes();
			}
		} else if (type == InComeConstant.TYPE_HOUSE) {
			if (levyType == InComeConstant.LEVYTYPE_FREE) {
				totalLevy = vipConfig.getFreeLevyCoinTimes();
			} else {
				totalLevy = vipConfig.getLevyCoinTimes();
			}
		}
		return totalLevy;
	}

	/**
	 * 加入到对应的资源池
	 * 
	 * @param build
	 * @param type
	 * @param value
	 * @author wcy
	 */
	private void addValueToCache(Role role, byte type, int value) {
		Build build = role.getBuild();
		Short buildLv = null;
		if(type == InComeConstant.TYPE_MAIN_CITY){
			buildLv = role.getLv();
		}else{
			buildLv = (Short) build.getBuildLvMap().get(type);
		}
		if (buildLv == null)
			return;
//		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(type, (int) buildLv);
//		int maxCapacity = mainBuildingConfig.getCapacity();
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//		Map<Byte, Integer> inComeCacheMap = build.getInComeCacheMap();
//		int num = inComeCacheMap.get(type);
//		num += value;
////		if (num > maxCapacity) {
////			num = maxCapacity;
////		}
//		inComeCacheMap.put(type, num);
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		build.addInComeCacheMap(type, value);
	}

	/**
	 * 重载<br/>
	 * 将酒馆寻访事件加入资源池
	 * 
	 * @param pub
	 * @param info
	 */
	private void addValueToCache(Pub pub, LevyInfo info) {
		pub.getVisitDataMap().put((int) info.getStartTime(), new VisitData(info));
		pub.setChang(true);
	}

	/**
	 * 
	 * @param build
	 * @param info
	 * @author wcy
	 */
	private void addValueToTreasureCache(Build build, LevyInfo info) {
		build.getVisitTreasureDataMap().put((int) info.getStartTime(), new VisitTreasureData(info));
		build.setChange(true);
	}

	/**
	 * 
	 * @param build
	 * @param levyInfo
	 * @author wcy
	 */
	private void addValueToScienceCache(Role role, Build build, LevyInfo levyInfo) {
		Map<Integer, Integer> scienceLvMap = role.getRoleScienceMap();
		int heroId = levyInfo.getHeroId();
		int intel = HeroUtils.getIntelValue(role.getHeroMap().get(heroId));
		int exploitValue = 1000 + intel * 10;// 可获得战功
		byte resType = (byte) levyInfo.getValue();
		int scienceId = 0;
		if (resType == InComeConstant.TYPE_FARM) {
			scienceId = ScienceConstant.FOOD_SCIENCE_ID;
		} else if (resType == InComeConstant.TYPE_HOUSE) {
			scienceId = ScienceConstant.MONEY_SCIENCE_ID;
		}
		int lv = scienceLvMap.get(scienceId);
		RoleScienceConfig config = RoleScienceConfigCache.getRoleScienceConfig(scienceId, lv);
		String scienceName = config.getName();

		build.getVisitScienceDataMap().put((int) levyInfo.getStartTime(),
				new VisitScienceData(levyInfo, exploitValue, scienceName));
		build.setChange(true);
	}

	/**
	 * 
	 * @param build
	 * @param levyInfo
	 * @author wcy
	 */
	private void addValueToSalesCache(Role role, Build build, LevyInfo levyInfo) {
		// TODO
		// build.getVisitSaleDataMap().put((int) levyInfo.getStartTime(),
		// new VisitSaleData(levyInfo));
		// build.setChange(true);
		// Mail mail = new Mail();
		// mail.setTitle("");
		// mail.setContext("");
		//
		//
		// mailService.sendSYSMail(role, mail);
		int prestige = levyInfo.getValue();
		int price = levyInfo.getValueOther();

		roleService.addRolePrestige(role, prestige);
		roleService.addRoleMoney(role, price);

		role.setChange(true);
	}

	@Override
	public int getCdTime(Hero hero, int baseCdTime, byte type) {
		if (type == InComeConstant.TYPE_HOUSE || type == InComeConstant.TYPE_FARM) {
			if (hero != null) {
				int constant1 = 0;
				int constant2 = 0;
				if (type == InComeConstant.TYPE_HOUSE) {
					constant1 = GeneralNumConstantCache.getValue("HOUSE_LEVY_CD_VALUE1");
					constant2 = GeneralNumConstantCache.getValue("HOUSE_LEVY_CD_VALUE2");
				} else if (type == InComeConstant.TYPE_FARM) {
					constant1 = GeneralNumConstantCache.getValue("FARM_LEVY_CD_VALUE1");
					constant2 = GeneralNumConstantCache.getValue("FARM_LEVY_CD_VALUE2");
				}
				int captain = HeroUtils.getCaptainValue(hero);
				// baseCdTime = (int) (baseCdTime / Math.pow(1.005, captain));
				Role role = RoleCache.getRoleById(hero.getRoleId());
				int value = getHourIncomeValue(role, type);
				baseCdTime = (int) (value / (hero.getArmsNum() * Math.pow(constant1 / 10000.0f, captain) / constant2));
			}

			int farmAndHouseMinLevyTime = GeneralNumConstantCache.getValue("FARM_HOUSE_MIN_TIME");
			baseCdTime = baseCdTime < farmAndHouseMinLevyTime ? farmAndHouseMinLevyTime : baseCdTime;

		} else if (type == InComeConstant.TYPE_PUB) {
			if (hero != null) {
				int reduceDelta = getPubTalkReduceDelta(hero);
				baseCdTime = baseCdTime - reduceDelta;
			}
		}

		return baseCdTime;
	}

	/**
	 * 酒馆会谈缩减的时间
	 * 
	 * @param hero
	 * @return
	 * @author wcy
	 */
	@Override
	public int getPubTalkReduceDelta(Hero hero) {
		if (hero == null)
			return 0;
		int constant = 30;
		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
		int captain = HeroUtils.getCaptainValue(hero);
		return (int) (captain / 10) * constant;
	}

	@Override
	public int getVisitTreasureReduceTimeDelta(Hero hero) {
		if (hero == null)
			return 0;
		int captain = HeroUtils.getCaptainValue(hero);
		int value = captain * 30;
		return value;
	}

	@Override
	public int getPropStrengthAddDelta(Hero hero) {
		if (hero == null)
			return 0;
		int intel = HeroUtils.getIntelValue(hero);
		int num = GeneralNumConstantCache.getValue("EQUIP_STRENGTH_CONSTANT");
		int value = intel / num;
		return value;
	}

	/**
	 * 获得建筑升级cd时间
	 * 
	 * @param build
	 * @param baseCdTime
	 * @return
	 * @author wcy
	 */
	@Override
	public int getBuildLvUpCdTime(Build build, int baseCdTime) {
		Map<Byte, BuildInfo> attachHeroMap = build.getAttachHeroMap();
		if (attachHeroMap == null) {
			return baseCdTime;
		}

		Integer heroId = build.getAttachHeroMap().containsKey(InComeConstant.TYPE_MAIN_CITY)
			? build.getAttachHeroMap().get(InComeConstant.TYPE_MAIN_CITY).getHeroId()
			: null;
		if (heroId == null) {
			return baseCdTime;
		}
		int roleId = build.getRoleId();
		Role role = RoleCache.getRoleById(roleId);
		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null) {
			return baseCdTime;
		}

		// baseCdTime -= InComeUtils.getValueCaption(role, hero);
		int baseNumber = GeneralNumConstantCache.getValue("BUILD_LV_UP_BASEMENT");
		baseCdTime = (int) (baseCdTime / Math.pow(baseNumber/10000.0, HeroUtils.getCaptainValue(hero)));
		int minBuildLvUpTime = GeneralNumConstantCache.getValue("MIN_BUILD_LV_UP_TIME");
		if (baseCdTime < minBuildLvUpTime)
			baseCdTime = minBuildLvUpTime;
		return baseCdTime;
	}

	/**
	 * 获得基础cd值
	 * 
	 * @param type
	 * @param buildLv 建筑等级
	 * @return
	 * @author wcy
	 */
	private int getBaseCdTime(byte type, int buildLv) {
		int baseCd = 0;
		BuildConfig buildConfig = BuildConfigCache.getBuildConfig(buildLv, type);
		if (type == InComeConstant.TYPE_FARM) {
			baseCd = buildConfig.getCd();
		} else if (type == InComeConstant.TYPE_HOUSE) {
			baseCd = buildConfig.getCd();
		}
		return baseCd;
	}

	/**
	 * 获得主城配属武将后的资源增益值(只有农田和民居）
	 * 
	 * @param type
	 * @return
	 * @author wcy
	 */
	@Override
	public int getValueOther(Role role, byte type) {
		int resultValue = 0;

		Build build = role.getBuild();
		Integer mainCityHeroId = build.getAttachHeroMap().containsKey(InComeConstant.TYPE_MAIN_CITY)
			? build.getAttachHeroMap().get(InComeConstant.TYPE_MAIN_CITY).getHeroId()
			: null;
		if (mainCityHeroId != null && mainCityHeroId != 0) {
			Hero mainCityHero = role.getHeroMap().get(mainCityHeroId);
			if (type == InComeConstant.TYPE_FARM) {
				resultValue = InComeUtils.getValuePower(role, mainCityHero);
			} else if (type == InComeConstant.TYPE_HOUSE) {
				resultValue = InComeUtils.getValueIn(role, mainCityHero);
			}
		}

		return resultValue;

	}

	/**
	 * 获取对应的收益
	 * 
	 * @param type
	 * @return
	 * @author wcy
	 */
	private int getCacheValue(Build build, byte type) {
		Integer cacheValue = build.getInComeCacheMap().get(type);
		if (cacheValue == null) {
			cacheValue = 0;
		}
		return cacheValue;
	}

	/**
	 * 
	 * @param value
	 * @param attr
	 * @param type
	 * @param attrType 1统帅2武力3智力
	 * @return
	 * @author wcy
	 */
	private double formula(double value, int attr, byte type, HeroAttrType attrType, int star) {
		if (type == InComeConstant.TYPE_MAIN_CITY) {
//			double constant1 = GeneralNumConstantCache.getValue("MAIN_CITY_ATTACH_CONSTANT1") / 10000.0;
//			value = (int) (value * Math.pow(constant1, attr));
			value = value * (1.5 + 0.01 * (attr / 80 - 1));
		} else if (type == InComeConstant.TYPE_FARM) {
			// double constant1 =
			// GeneralNumConstantCache.getValue("FARM_ATTACH_CONSTANT1")/10000.0;
			// value = (int) (value * Math.pow(constant1, attr));
			// value = (int) Math.floor(value * ((attr - 40)/(5*Math.pow(2,
			// star-1))*0.1+1.5));
			value = value * (1.5 + 0.01 * (attr / 80 - 1));
		} else if (type == InComeConstant.TYPE_HOUSE) {
			// double constant1 =
			// GeneralNumConstantCache.getValue("HOUSE_ATTACH_CONSTANT1")/10000.0;
			// value = (int) (value * Math.pow(constant1, attr));
			// value = (int) Math.floor(value * ((attr - 40)/(5*Math.pow(2,
			// star-1))*0.1+1.5));
			value = value * (1.5 + 0.01 * (attr / 80 - 1));
		} else if (type == InComeConstant.TYPE_PUB) {
			// 产酒量
			int constant = 1;
			value = value + (int) (attr / 15) * constant;
		} else if (type == InComeConstant.TYPE_FORGE) {
			// 产铁定量
//			int constant = 1;
//			value = value + (int) (attr / 15) * constant;
			value = value * (1.5 + 0.01 * (attr / 80 - 1));
		} else if (type == InComeConstant.TYPE_TECH) {
			value = value + attr / 10;
		} else if (type == InComeConstant.TYPE_CAMP) {
			// value = value + attr *
			// GeneralNumConstantCache.getValue("BARRACK_ATTACH_EXPLOIT");
//			double constant1 = GeneralNumConstantCache.getValue("BARRACK_ATTACH_CONSTANT1") / 10000.0;
//			value = (int) (value * Math.pow(constant1, attr));
			value = value * (1.5 + 0.01 * (attr / 80 - 1));
		} else if (type == InComeConstant.TYPE_MARKET) {
			if (attrType == HeroAttrType.captain) {
				value = (int) ((1 - 0.5 / Math.pow(1.015, attr)) * 100.0);
				if (value > 100)
					value = 100;
			}
			if (attrType == HeroAttrType.intel) {
				double constant1 = GeneralNumConstantCache.getValue("BARGIN_CONSTANT1") / 10000.0;
				double constant2 = GeneralNumConstantCache.getValue("BARGIN_CONSTANT2") / 10000.0;
				if (value != 0) {
					value = (int) (value * constant1 * (Math.pow(constant2, attr) - 1));
				} else {
					double rate = constant1 * (Math.pow(constant2, attr) - 1);
					if (rate > 1)
						rate = 1;
					value = (int) (rate * 100);
				}
			}
		}
		return value;
	}

	/***
	 * 获取武将配属增益
	 * 
	 * @author xjd
	 */
	public Message getInfoAttach(Role role, int heroId, byte type) {
		Message message = new Message();
		message.setType(InComeConstant.GET_INFO_ATTACH);
		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null)
			return null;
		// 根据类型查询不同的增益值
		int cutCd = InComeConstant.NO_HERO, addFood = InComeConstant.NO_HERO, addMoney = InComeConstant.NO_HERO;

		HeroConfig use = HeroUtils.getCaptain(hero);
		switch (type) {
		case InComeConstant.TYPE_MAIN_CITY:
			int baseMainCityIncomeValue = this.getBaseHourIncomeValue(role, type);
			cutCd = (int) formula(baseMainCityIncomeValue, use.getCaptain(), type, HeroAttrType.captain,hero.getRank());
//			cutCd = InComeUtils.getValueCaption(role, hero);
			addFood = InComeUtils.getValuePower(role, hero);
			addMoney = InComeUtils.getValueIn(role, hero);
			break;
		case InComeConstant.TYPE_FARM:
			int baseFarmIncomeValue = this.getBaseHourIncomeValue(role, type);
			addFood = (int) (formula(this.getBaseHourIncomeValue(role, type), use.getCaptain(), type, HeroAttrType.captain,hero.getRank())
					- baseFarmIncomeValue);
			break;
		case InComeConstant.TYPE_HOUSE:
			int baseHouseIncomeValue = this.getBaseHourIncomeValue(role, type);
			addMoney = (int) (formula(this.getBaseHourIncomeValue(role, type), use.getIntel(), type, HeroAttrType.intel,hero.getRank())
					- baseHouseIncomeValue);
			break;
		case InComeConstant.TYPE_PUB:
			cutCd = getPubTalkReduceDelta(hero);
			addMoney = (int) formula(this.getBaseHourIncomeValue(role, type), use.getIntel(), type, HeroAttrType.intel,hero.getRank());
			break;
		case InComeConstant.TYPE_FORGE:
			cutCd = getVisitTreasureReduceTimeDelta(hero);
			addFood = (int) formula(this.getBaseHourIncomeValue(role, type), use.getPower(), type, HeroAttrType.intel,hero.getRank());
			addMoney = getPropStrengthAddDelta(hero);
			break;
		case InComeConstant.TYPE_TECH:
			addMoney = (int) formula(0, use.getIntel(), type, HeroAttrType.intel,hero.getRank());
			break;
		case InComeConstant.TYPE_CAMP:
			addFood = (int) formula(this.getBaseHourIncomeValue(role, type), use.getPower(), type, HeroAttrType.power,hero.getRank());
			break;
		case InComeConstant.TYPE_MARKET:
			cutCd = (int) formula(0, use.getCaptain(), type, HeroAttrType.captain,hero.getRank());
			addMoney = (int) formula(0, use.getIntel(), type, HeroAttrType.intel,hero.getRank());
			break;
		default:
			break;
		}

		message.putInt(cutCd);
		message.putInt(addFood);
		message.putInt(addMoney);

		return message;
	}

	@Override
	public Message getInfoLevy(Role role, int heroId, byte type) {
		Message message = new Message();
		message.setType(InComeConstant.GET_INFO_ATTACH);
		Build build = role.getBuild();
		Short buildLv = build.getBuildLvMap().get(type);
		if (buildLv == null) {
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null)
			return null;
		// 根据类型查询不同的增益值
		int cutCd = InComeConstant.NO_HERO, addFood = InComeConstant.NO_HERO, addMoney = InComeConstant.NO_HERO;

		int baseCdTime = getBaseCdTime(type, buildLv);
		HeroConfig use = HeroUtils.getCaptain(hero);
		switch (type) {
		case InComeConstant.TYPE_MAIN_CITY:
			cutCd = InComeUtils.getValueCaption(role, hero);
//			addFood = InComeUtils.getValuePower(role, hero);
//			addMoney = InComeUtils.getValueIn(role, hero);
			break;
		case InComeConstant.TYPE_FARM:
			cutCd = getCdTime(hero, baseCdTime, type);
//			addFood = formula(this.getBaseHourIncomeValue(role, type), use.getPower(), type, HeroAttrType.power);
			addFood = this.getLevyIncomeValue(role, type, heroId);
			break;
		case InComeConstant.TYPE_HOUSE:
			cutCd = getCdTime(hero, baseCdTime, type);
//			addMoney = formula(this.getBaseHourIncomeValue(role, type), use.getIntel(), type, HeroAttrType.intel);
			addMoney = this.getLevyIncomeValue(role, type, heroId);
			break;
		case InComeConstant.TYPE_PUB:
			cutCd = HeroUtils.getCaptainValue(hero) * 30;
			addMoney = (int) (0.002 * HeroUtils.getIntelValue(hero) * 100);
			break;
		case InComeConstant.TYPE_FORGE:
			cutCd = getVisitTreasureReduceTimeDelta(hero);
			break;
		case InComeConstant.TYPE_TECH:
			addMoney = (int) formula(0, use.getIntel(), type, HeroAttrType.intel,hero.getRank());
		case InComeConstant.TYPE_CAMP:
			break;
		case InComeConstant.TYPE_MARKET:
			break;
		default:
			break;
		}

		message.putInt(cutCd);
		message.putInt(addFood);
		message.putInt(addMoney);
		return message;
	}

//	@Override
//	public Message buildTypeLvUp(Role role, byte type) {
//		Message message = new Message();
//		message.setType(InComeConstant.BUILD_TYPE_LV_UP);
//
//		short roleLv = role.getLv(); // 官邸等级
//		Map<Byte, Short> buildLvMap = role.getBuild().getBuildLvMap();// 建筑等级表
//		Short buildLv = buildLvMap.get(type);// 建筑等级
//		if (buildLv == null) {
//			message.putShort(ErrorCode.BUILD_NO_LOCK);
//			return message;
//		}
//		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(type, buildLv);
//		int money = mainBuildingConfig.getUpMoney();// 消耗银币
//		int time = this.getBuildLvUpCdTime(role.getBuild(), mainBuildingConfig.getUpTime());// 消耗时间
//		int currentMoney = role.getMoney();// 银币
//
//		// 钱是否足够
//		if (currentMoney < money) {
//			message.putShort(ErrorCode.NO_MONEY);
//			return message;
//		}
//		// 等级不可超过官邸
//		if (buildLv >= roleLv) {
//			message.putShort(ErrorCode.LV_NOT_ENOUGH);
//			return message;
//		}
//
//		//
//		// 升级成功 更改建筑等级 扣除主角金钱 更新建筑队列
//		//
//		// 刷新建筑队列时间
//		this.refreshBuildTime(role);
//		// 获取空闲的ID
//		byte queueId = this.getIdleBuildQueue(role);
//		if (queueId <= 0) { // 没有空闲的建筑队列
//			message.putShort(ErrorCode.NO_BUILD_QUEUE);
//			return message;
//		}
//		// 允许升级
//		BuildQueue buildQueue = new BuildQueue();
//		buildQueue = role.getBuildQueueMap().get(queueId);
//		short lv = buildLvMap.get(type);
//		lv += 1;
//		buildLvMap.put(type, lv);
//		message.putShort(ErrorCode.SUCCESS);
//		// 建筑队列添加时间
//		buildQueue.setTime(buildQueue.getTime() + time);
//		if (buildQueue.getTime() >= InComeConstant.MAX_QUEUE_TIME) {
//			// 关闭建筑队列
//			buildQueue.setOpen(InComeConstant.CLOSE_QUEUE);
//		}
//
//		roleService.addRoleMoney(role, -money);
//		mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(type, (int) lv);
//
//		this.buildInfoChange(role, type, InComeConstant.CHANGE_TYPE_LV, Utils.getNowTime());
//		
//		// 如果是酒馆则加新英雄
//		if (type == InComeConstant.TYPE_PUB) {
//			pubService.pubLvUpAddHero(role, (int) buildLvMap.get(type));
//		}
//
//		// 计策解锁
//		if (type == InComeConstant.TYPE_TECH) {
//			String other = mainBuildingConfig.getOther();
//			String[] techs = other.split(",");
//			for (String tech : techs) {
//				Integer techId = Integer.valueOf(tech);
//				if (techId != 0)
//					role.getRoleScienceMap().put(techId, InComeConstant.SCIENCE_UNLOCK);
//			}
//		}
//
//		// 集市解锁
//		if (type == InComeConstant.TYPE_MARKET) {
//			MarketConfig marketConfig = MarketConfigCache.getMarketConfigByLv((int) lv);
//
//			Market market = role.getMarket();
//			Map<Integer, MarketItems> marketItemsMap = market.getItemsMap();
//			int index = marketItemsMap.size() + 1;
//			int award = marketConfig.getAward();
//			int price = marketConfig.getSalePrice();
//
//			for (int i = 0, num = marketConfig.getNum(); i < num; i++) {
//				MarketItems marketItem = new MarketItems();
//				marketItem.setId(index);
//				marketItem.setIsSell(MarketConstant.IS_NOT_SELL);
//				marketItem.setItemId(award);
//				marketItem.setSalePrice(price);
//				marketItem.setIsBarginBoolean(false);
//
//				marketItemsMap.put(index, marketItem);
//			}
//			market.setChange(true);
//
//		}
//
//		message.putShort(buildLvMap.get(type));
//		message.putInt(mainBuildingConfig.getUpMoney());
//		message.putInt(mainBuildingConfig.getUpTime());
//		message.putInt(money);
//		message.putInt(time);
//
//		// 查询任务
//		taskService.checkInComeTask(role, type, TaskConstant.TYPE_BYTE_1);
//		taskService.checkBulidLv(role, type);
//		
//		this.buildInfoChange(role, type,InComeConstant.CHANGE_TYPE_LV,Utils.getNowTime());
//
//		return message;
//	}
	
	@Override
	public Message buildTypeLvUp(Role role, byte type) {
		Message message = new Message();
		message.setType(InComeConstant.BUILD_TYPE_LV_UP);

		if(type == InComeConstant.TYPE_MAIN_CITY){
			roleService.roleLvUp(role, message);
			return message;
		}
		
		if(type == InComeConstant.TYPE_SHILIANYING){
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}
		
		short roleLv = role.getLv(); // 官邸等级
		Map<Byte, Short> buildLvMap = role.getBuild().getBuildLvMap();// 建筑等级表
		Short buildLv = buildLvMap.get(type);// 建筑等级
		if (buildLv == null) {
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(type, buildLv);
		int money = mainBuildingConfig.getUpMoney();// 消耗银币
		int time = this.getBuildLvUpCdTime(role.getBuild(), mainBuildingConfig.getUpTime());// 消耗时间
		int currentMoney = role.getMoney();// 银币

		// 钱是否足够
		if (currentMoney < money) {
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}
		// 等级不可超过官邸
		if (buildLv >= roleLv) {
			message.putShort(ErrorCode.LV_NOT_ENOUGH);
			return message;
		}

		//
		// 升级成功 更改建筑等级 扣除主角金钱 更新建筑队列
		//
		// 刷新建筑队列时间
		this.refreshBuildTime(role);
		// 获取空闲的ID
		byte queueId = this.getIdleBuildQueue(role);
		if (queueId <= 0) { // 没有空闲的建筑队列
			message.putShort(ErrorCode.NO_BUILD_QUEUE);
			return message;
		}
		// 允许升级
		BuildQueue buildQueue = new BuildQueue();
		buildQueue = role.getBuildQueueMap().get(queueId);
		short lv = buildLvMap.get(type);
		lv += 1;
		buildLvMap.put(type, lv);
		message.putShort(ErrorCode.SUCCESS);
		// 建筑队列添加时间
		buildQueue.setTime(buildQueue.getTime() + time);
		if (buildQueue.getTime() >= InComeConstant.MAX_QUEUE_TIME) {
			// 关闭建筑队列
			buildQueue.setOpen(InComeConstant.CLOSE_QUEUE);
		}

		roleService.addRoleMoney(role, -money);
		mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(type, (int) lv);

		this.buildInfoChange(role, type, InComeConstant.CHANGE_TYPE_LV, Utils.getNowTime());
		
		// 如果是酒馆则加新英雄
		if (type == InComeConstant.TYPE_PUB) {
			pubService.pubLvUpAddHero(role, (int) buildLvMap.get(type));
		}

		// 计策解锁
		if (type == InComeConstant.TYPE_TECH) {
			String other = mainBuildingConfig.getOther();
			String[] techs = other.split(",");
			for (String tech : techs) {
				Integer techId = Integer.valueOf(tech);
				if (techId != 0)
					role.getRoleScienceMap().put(techId, InComeConstant.SCIENCE_UNLOCK);
			}
		}

		// 集市解锁
		if (type == InComeConstant.TYPE_MARKET) {
			List<MarketConfig> marketConfigList = MarketConfigCache.getMarketConfigByLv((int) lv);

			Market market = role.getMarket();
			Map<Integer, MarketItems> marketItemsMap = market.getItemsMap();
			
			for(MarketConfig marketConfig:marketConfigList){
				int index = marketItemsMap.size();
				int award = marketConfig.getAward();
				int price = marketConfig.getSalePrice();
				
				for (int i = 0, num = marketConfig.getNum(); i < num; i++) {
					MarketItems marketItem = new MarketItems();
					marketItem.setId(index);
					marketItem.setIsSell(MarketConstant.IS_NOT_SELL);
					marketItem.setItemId(award);
					marketItem.setSalePrice(price);
					marketItem.setIsBarginBoolean(false);
					
					marketItemsMap.put(index, marketItem);
					index++;
				}
				
			}
			
			
			market.setChange(true);

		}

		message.putShort(buildLvMap.get(type));
		message.putInt(mainBuildingConfig.getUpMoney());
		message.putInt(mainBuildingConfig.getUpTime());
		message.putInt(money);
		message.putInt(time);

		// 查询任务
		taskService.checkInComeTask(role, type, TaskConstant.TYPE_BYTE_1);
		taskService.checkBulidLv(role, type);
		
		this.buildInfoChange(role, type,InComeConstant.CHANGE_TYPE_LV,Utils.getNowTime());

		return message;
	}
	

	/***
	 * 获取新开建筑的怪物信息
	 * 
	 * @author xjd
	 */
	public Message getFightInfo(byte type, byte id) {
		Message message = new Message();
		message.setType(InComeConstant.GET_FIGHT_INFO);
		BuildingUnlock unlock = BuildingUnlockCache.getBuildingUnlockMaps().get(type).get(id);
		if (unlock == null)
			return null;
		ChapterConfig info = ChapterConfigCache.getChapterConfigById(unlock.getTroopData());
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
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(info.getId());
		message.put(InComeConstant.NO_STARTS);
		message.putString(sb.toString());

		message.putInt(InComeConstant.NO_HERO);
		message.putInt(info.getExploit());
		message.putString("");
		message.putInt(InComeConstant.NO_HERO);
		message.putInt(InComeConstant.NO_HERO);
		message.putInt(InComeConstant.NO_HERO);
		message.putInt(InComeConstant.NO_HERO);
		message.putString(info.getTitle());
		return message;
	}

	@Override
	public Message showSpeedUpLevy(Role role, byte type) {
		Message message = new Message(InComeConstant.SHOW_SPEED_UP_LEVY);
		int nowTime = (int) (System.currentTimeMillis() / 1000);
		this.refreshLevyData(role, nowTime);
		LevyInfo levyInfo = getLevyInfoByType(role, type);
		if (levyInfo == null) {
			return null;
		}
		byte isFree = (byte) (isFreeSpeedLevy(role, type)
			? InComeConstant.LEVY_SPEED_UP_FREE
			: InComeConstant.LEVY_SPEED_UP_NOT_FREE);

		int levyNeedGold = 0;
		levyNeedGold = isFree == InComeConstant.LEVY_SPEED_UP_NOT_FREE
			? this.levySpeedUpNeedGold(nowTime, levyInfo)
			: 0;
		message.put(isFree);
		message.putInt(levyNeedGold);

		return message;
	}

	@Override
	public Message speedUpLevy(Role role, byte type, byte isFree) {
		Message message = new Message(InComeConstant.SPEED_UP_LEVY);
		Build build = role.getBuild();
		int nowTime = (int) (System.currentTimeMillis() / 1000);
		this.refreshLevyData(role, nowTime);
		LevyInfo levyInfo = getLevyInfoByType(role, type);
		if (levyInfo == null || type == InComeConstant.TYPE_CAMP || type == InComeConstant.TYPE_TECH
				|| type == InComeConstant.TYPE_MAIN_CITY) {
			return null;
		}

		// if (isFree == InComeConstant.LEVY_SPEED_UP_FREE) {
		// message.putShort(ErrorCode.SUCCESS);
		// levyInfo.setCd((int) (levyInfo.getCd() -
		// InComeConstant.LEVY_SPEED_UP_FREE_REDUECE_TIME));
		// build.setLevySpeedTimes(build.getLevySpeedTimes() + 1);
		// } else {
		int useGold = levySpeedUpNeedGold(nowTime, levyInfo);
		if (role.getGold() < useGold) {
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}

		levyInfo.setCd(0);
		build.setLevySpeedTimes(build.getLevySpeedTimes() + 1);
		roleService.addRoleGold(role, -useGold);

		message.putShort(ErrorCode.SUCCESS);
		// }
		this.refreshLevyData(role, nowTime);
		return message;
	}

	/**
	 * 根据建筑类型获得征收队列，如果没有返回null
	 * 
	 * @param role
	 * @param type
	 * @return
	 * @author wcy
	 */
	private LevyInfo getLevyInfoByType(Role role, byte type) {
		Map<Integer, LevyInfo> levyMap = role.getBuild().getLevyMap();
		LevyInfo levyInfo = null;
		for (LevyInfo v : levyMap.values()) {
			if (v.getType() == type) {
				levyInfo = v;
				break;
			}
		}

		return levyInfo;
	}

	/**
	 * 秒征收队列所需要的金币数
	 * 
	 * @param nowTime
	 * @param levyInfo
	 * @return
	 * @author wcy
	 */
	private int levySpeedUpNeedGold(int nowTime, LevyInfo levyInfo) {
		long startTime = levyInfo.getStartTime();
		int cd = levyInfo.getCd();
		long endTime = cd + startTime;
		long remainTime = endTime - nowTime;
		if (remainTime <= 0) {
			return 0;
		}

		// int range = InComeConstant.LEVY_SPEED_UP_RANGE;
		// int in7200Sec = InComeConstant.LEVY_SPEED_UP_INSIDE_RANGE;
		// int out7200Sec = InComeConstant.LEVY_SPEED_UP_OUTSIDE_RANGE;
		// // 7200 in seconds
		// int useGold = (int) (remainTime / in7200Sec);
		// useGold += remainTime % in7200Sec == 0 ? 0 : 1;
		// // 7200 out seconds
		// remainTime -= range;
		// if (remainTime > 0) {
		// useGold += (int) (remainTime / out7200Sec);
		// useGold += remainTime % out7200Sec == 0 ? 0 : 1;
		// }
		int useGold = 0;

		int levySpeedUpTimeInRange = GeneralNumConstantCache.getValue("LEVY_SPEED_UP_TIME_IN_RANGE");
		int cIn1 = GeneralNumConstantCache.getValue("LEVY_SPEED_UP_IN_RANGE_VALUE1");
		int cOut1 = GeneralNumConstantCache.getValue("LEVY_SPEED_UP_OUT_RANGE_VALUE1");
		int cOut2 = GeneralNumConstantCache.getValue("LEVY_SPEED_UP_OUT_RANGE_VALUE2");

		useGold = (int) (remainTime < levySpeedUpTimeInRange
			? Math.ceil(remainTime / (float) cIn1)
			: Math.ceil(cOut1 + ((remainTime - levySpeedUpTimeInRange) / cOut2)));

		return useGold;
	}

	/**
	 * 是否是免费加速
	 * 
	 * @param role
	 * @param type
	 * @return
	 * @author wcy
	 */
	private boolean isFreeSpeedLevy(Role role, byte type) {
		Build build = role.getBuild();
		int times = build.getLevySpeedTimes();
		short buildLv = role.getLv();
		// if ((buildLv >= 1 && buildLv <= 7) || times == 0) {
		// return false;
		// }
		return false;
	}

	@Override
	public int getMarketItemPrice(Role role, Hero taskHero, int id) {
		Market market = role.getMarket();
		MarketItems marketItems = market.getItemsMap().get(id);
		int salePrice = marketItems.getSalePrice();
		HeroConfig use = HeroUtils.getCaptain(taskHero);
		int price = salePrice - (int)formula(salePrice, use.getIntel(), InComeConstant.TYPE_MARKET, HeroAttrType.intel,taskHero.getRank());
		price = price < 0 ? 0 : price;
		return price;
	}

	@Override
	public int getMarketItemPriceSuccessRate(Role role, Hero taskHero) {
		HeroConfig use = HeroUtils.getCaptain(taskHero);
		int rate = (int) formula(0, use.getCaptain(), InComeConstant.TYPE_MARKET, HeroAttrType.captain,taskHero.getRank());
		return rate;
	}
	
	@Override
	public Message getPrestige(Role role) {
		Message message = new Message();
		message.setType(InComeConstant.GET_PRESTIGE);
		
		int nowTime = Utils.getNowTime();
		this.checkHeroManual(role,nowTime);
		Build build = role.getBuild();

		// 获取配属英雄缩减后的cd时间
		roleService.refreshGetPrestige(role, nowTime);

		Map<Byte, Integer> incomeCacheMap = build.getInComeCacheMap();
		int getPrestige = incomeCacheMap.get(InComeConstant.TYPE_MAIN_CITY);
		if (getPrestige == 0) {// 检查领取是否是空
			message.putShort(ErrorCode.NO_PRESTIGE);
			return message;
		}
		roleService.addRolePrestige(role, getPrestige);
		// 主城声望池缓存清空
		incomeCacheMap.put(InComeConstant.TYPE_MAIN_CITY, 0);
		this.recordIncomeRank(role, InComeConstant.TYPE_MAIN_CITY, getPrestige, nowTime);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(getPrestige);
		
		return message;
	}

	@Override
	public void refreshAllCache(Role role,int nowTime) {
		roleService.refreshGetPrestige(role, nowTime);
		pubService.refreshGetWines(role, nowTime);
		propService.refreshGetIron(role, nowTime);
		barrackService.refreshGetExploit(role, nowTime);
		this.refreshGetFood(role, nowTime);
		this.refreshGetMoney(role, nowTime);
	}	
	
	private History makeStr(Role role , byte type ,int value)
	{
		StringBuilder sb = new StringBuilder();
		History history = new History();
		history.setTime(Utils.getNowTime());
		history.setYear(ServerCache.getServer().getYear());
		switch (type) {
		case InComeConstant.TYPE_FARM:
			history.setType(InComeConstant.HIS_TYPE_FOOD);
			break;
		case InComeConstant.TYPE_HOUSE:
			history.setType(InComeConstant.HIS_TYPE_MONEY);
			break;
		default:
			break;
		}
		sb.append("masterName").append(",").append(role.getName()).append(";");
		sb.append("pumpedNum").append(",").append(value).append(";");
		history.setStr(sb.toString());
		return history;
	}
	
	@Override
	public Message showRankInfo(Role role,byte type) {		
		Message message = new Message();
		message.setType(InComeConstant.SHOW_RANK_INFO);
		
		// 所有人
		List<Rank> list = this.getRankList(type);
		int SHOW_NUM = 50;
		int len = list.size();
		len = len > SHOW_NUM ? SHOW_NUM : len;

		int privateValue = 0;
		int privateRankNum = 0;
		Rank privateRank = null;
		byte privateExist = 0;

		message.putInt(len);
		int index = 0;
		for (Rank rank : list) {
			String name = "";
			byte vip = 0;
			int value = 0;
			int rankNum = 0;
			if (rank.getRoleId() == role.getId()) {
				privateRank = rank;
				privateExist = 1;
				rank.setRankNum(index+1);
			}

			if (index < len) {// 显示前多少个玩家
				if (type == RankConstant.RANK_TYPE_ROLE_LV) {
					RoleLvRank roleLvRank = (RoleLvRank) rank;
					name = roleLvRank.getName();
					vip = roleService.getRoleById(roleLvRank.getRoleId()).getVipLv();
					value = roleLvRank.getRoleLv();
				} else if (type == RankConstant.RANK_TYPE_INCOME) {
					IncomeRank incomeRank = (IncomeRank) rank;
					name = incomeRank.getName();
					vip = roleService.getRoleById(incomeRank.getRoleId()).getVipLv();
					value = incomeRank.getIncomeNum();
				} else if (type == RankConstant.RANK_TYPE_FIGHT_VALUE) {
					HeroFightValueRank heroFightValueRank = (HeroFightValueRank) rank;
					name = heroFightValueRank.getName();
					vip = roleService.getRoleById(heroFightValueRank.getRoleId()).getVipLv();
					value = heroFightValueRank.getFightValue();
				} else if (type == RankConstant.RANK_TYPE_OWN_CITY) {
					OwnCityRank ownCityRank = (OwnCityRank) rank;
					name = ownCityRank.getName();
					vip = roleService.getRoleById(ownCityRank.getRoleId()).getVipLv();
					value = ownCityRank.getOwnCityCount();
				} else if (type == RankConstant.RANK_TYPE_KILL) {
					KillRank killRank = (KillRank) rank;
					name = killRank.getName();
					vip = roleService.getRoleById(killRank.getRoleId()).getVipLv();
					value = killRank.getValue();
				}
				rank.setRankNum(index+1);
				
				message.putString(name);
				message.put(vip);
				message.putInt(value);
				message.putInt(rank.getRankNum());
			}
			index++;
		}

		// 个人信息
		if (privateRank != null) {
			if (type == RankConstant.RANK_TYPE_ROLE_LV) {
				RoleLvRank roleLvRank = (RoleLvRank) privateRank;
				privateValue = roleLvRank.getRoleLv();
			} else if (type == RankConstant.RANK_TYPE_INCOME) {
				IncomeRank incomeRank = (IncomeRank) privateRank;
				privateValue = incomeRank.getIncomeNum();
			} else if (type == RankConstant.RANK_TYPE_FIGHT_VALUE) {
				HeroFightValueRank heroFightValueRank = (HeroFightValueRank) privateRank;
				privateValue = heroFightValueRank.getFightValue();
			} else if (type == RankConstant.RANK_TYPE_OWN_CITY) {
				OwnCityRank ownCityRank = (OwnCityRank) privateRank;
				privateValue = ownCityRank.getOwnCityCount();
			} else if (type == RankConstant.RANK_TYPE_KILL) {
				KillRank killRank = (KillRank) privateRank;
				privateValue = killRank.getValue();
			}

			privateRankNum = privateRank.getRankNum();
		}
		
		message.put(privateExist);
		message.putInt(privateValue);
		message.putInt(privateRankNum);
		
		return message;
	}

	private List<Rank> getRankList(byte type){
		int nowTime = Utils.getNowTime();
		List<Rank> list = null;
		RankType rankType = rankService.getRankType(type);
		if (type == RankConstant.RANK_TYPE_ROLE_LV)//等级
			list = rankService.sortRoleLvRank();
		else if (type == RankConstant.RANK_TYPE_INCOME)//经营
		{
//			list = RankCache.getRankRecord(rankType);
//			if(list == null)
//				list = new ArrayList<>();
			
			list = rankService.refreshAllIncomeCache(nowTime);
			rankService.sortIncomeRank(list);
		}
		else if (type == RankConstant.RANK_TYPE_FIGHT_VALUE)//战力
			list = rankService.sortHeroFightValueRank();
		else if (type == RankConstant.RANK_TYPE_OWN_CITY)//主城
			list = rankService.sortOwnCityRank();
		else if (type == RankConstant.RANK_TYPE_KILL)//击杀
			list = rankService.sortKillRank();
		return list;
	}

	private byte checkStatBuild(IconUnlockConfig config ,Role role)
	{
		Build build = role.getBuild();
		if(config.getType()==1
				||(build.getBuildLvMap().containsKey((byte)config.getType())
				&& !role.getIconList().contains(config.getName())))
		{
			byte type = (byte)config.getType();
			//表示已经领取 判断当前是否 配属
			BuildInfo buildInfo = build.getAttachHeroMap().get(type);
			if(buildInfo == null || buildInfo.getHeroId() == 0)
			{
				return (byte)2;
			}else {
				return (byte)3;
			}
		}else {
			//还未领取
			if(role.getIconList().contains(config.getName()))
			{
				return (byte)1;
			}else {
				return (byte)0;
			}
		}
		
		
	}
	
	private IconUnlockConfig getIconUnlockConfigByBuildType(byte type){
		Map<String,IconUnlockConfig> map = IconUnlockConfigCache.getIconUnlockConfigMap();
		IconUnlockConfig result = null;
		for(IconUnlockConfig config : map.values()){
			byte type1 = (byte)config.getType();
			if(type == type1){
				result = config;
				break;
			}
		}
		return result;
	}
}
