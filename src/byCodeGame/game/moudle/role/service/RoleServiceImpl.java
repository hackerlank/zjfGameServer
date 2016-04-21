package byCodeGame.game.moudle.role.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.ChapterConfigCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.IconUnlockConfigCache;
import byCodeGame.game.cache.file.LawlessCharsCache;
import byCodeGame.game.cache.file.MainCityConfigCache;
import byCodeGame.game.cache.file.MarketConfigCache;
import byCodeGame.game.cache.file.RoleExpConfigCache;
import byCodeGame.game.cache.local.RankCache.RankType;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.ServerCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.RoleDao;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Market;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.Server;
import byCodeGame.game.entity.file.ChapterConfig;
import byCodeGame.game.entity.file.IconUnlockConfig;
import byCodeGame.game.entity.file.LawlessChars;
import byCodeGame.game.entity.file.MainCityConfig;
import byCodeGame.game.entity.file.MarketConfig;
import byCodeGame.game.entity.file.RoleExpConfig;
import byCodeGame.game.entity.po.BuildInfo;
import byCodeGame.game.entity.po.BuildQueue;
import byCodeGame.game.entity.po.LvCheck;
import byCodeGame.game.entity.po.MarketItems;
import byCodeGame.game.entity.po.Rank;
import byCodeGame.game.moudle.barrack.service.BarrackService;
import byCodeGame.game.moudle.chat.ChatConstant;
import byCodeGame.game.moudle.chat.service.ChatService;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.inCome.InComeConstant;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.moudle.login.service.LoginService;
import byCodeGame.game.moudle.market.MarketConstant;
import byCodeGame.game.moudle.market.service.MarketService;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.moudle.pub.service.PubService;
import byCodeGame.game.moudle.rank.RankConstant;
import byCodeGame.game.moudle.rank.service.RankService;
import byCodeGame.game.moudle.role.RoleConstant;
import byCodeGame.game.moudle.target.service.TargetService;
import byCodeGame.game.moudle.task.TaskConstant;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.HeroUtils;
import byCodeGame.game.util.InComeUtils;
import byCodeGame.game.util.Utils;

public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private HeroService heroService;

	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}

	private MarketService marketService;

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

	private InComeService inComeService;

	public void setInComeService(InComeService inComeService) {
		this.inComeService = inComeService;
	}
	
	private BarrackService barrackService;
	
	public void setBarrackService(BarrackService barrackService){
		this.barrackService = barrackService;
	}
	
	private PubService pubService ;
	public void setPubService(PubService pubService){
		this.pubService = pubService;
	}
	
	private PropService propService;	
	public void setPropService(PropService propService){
		this.propService = propService;
	}
	
	private ChatService chatService;
	public void setChatService(ChatService chatService){
		this.chatService = chatService;
	}

	private TargetService targetService; 
	public void setTargetService(TargetService targetService) {
		this.targetService = targetService;
	}
	
	private RankService rankService;
	public void setRankService(RankService rankService){
		this.rankService = rankService;
	}
	
	public Role getRoleById(int id) {
		Role role = RoleCache.getRoleById(id);
		if (role == null) {
			role = this.getDbRoleById(id);
		}
		return role;
	}

	public Role getRoleByName(String name) {
		Role role = RoleCache.getRoleByName(name);
		if (role == null) {
			role = this.getDbRoleByName(name);
		}
		return role;
	}

	public Role getDbRoleById(int id) {
		Role role = roleDao.getRoleById(id);
		if (role == null)
			return null;
		loginService.loginRoleModuleDataInit(role);
		RoleCache.putRole(role);
		return role;
	}

	public Role getDbRoleByName(String name) {
		Role role = roleDao.getRoleByName(name);
		if (role == null)
			return null;
		loginService.loginRoleModuleDataInit(role);
		RoleCache.putRole(role);
		return role;
	}

	public Role getRoleByAccount(String account) {
		Role role = RoleCache.getRoleByAccount(account);
		
		if(role != null)
		{
			return role;
		}else {
			role = roleDao.getRoleByAccount(account);
			if(role == null) return null;
		}
		loginService.loginRoleModuleDataInit(role);
		RoleCache.putRole(role);
		return role;
	}
	
	public void selectAllName() {
		List<String> nameList = roleDao.getAllName();
		Set<String> set = new HashSet<String>(nameList);
		RoleCache.setNameSet(set);
	}

	public void selectAllAccount() {
		List<String> accountList = roleDao.getAllAccount();
		Set<String> set = new HashSet<String>(accountList);
		RoleCache.setNameSet(set);
	}

	public void addRoleFood(Role role, int value) {
		int originTotal = role.getFood();		
		int total = role.getFood() + value;
		if (total < 0) {
			if (value >= 0) {
				total = Integer.MAX_VALUE;
			} else {
				total = 0;
			}
		}
		value = total - originTotal;
		role.setFood(total);
		IoSession ioSession = SessionCache.getSessionById(role.getId());
		Message message = new Message();
		message.setType(RoleConstant.ADD_FOOD_ROLE);
		message.putInt(value);
		if (ioSession != null) {
			ioSession.write(message);
		}
	}

	public void addRoleMoney(Role role, int value) {
		int originTotal = role.getMoney();
		int total = role.getMoney() + value;
		if (total < 0) {
			if (value >= 0) {
				total = Integer.MAX_VALUE;
			} else {
				total = 0;
			}
		}
		value = total - originTotal;
		role.setMoney(total);
		IoSession ioSession = SessionCache.getSessionById(role.getId());
		Message message = new Message();
		message.setType(RoleConstant.ADD_MONEY_ROLE);
		message.putInt(value);
		if (ioSession != null) {
			ioSession.write(message);
		}

	}

	public void addRolePopulation(Role role, int value) {
		role.setPopulation(role.getPopulation(role) + value);
		IoSession ioSession = SessionCache.getSessionById(role.getId());
		Message message = new Message();
		message.setType(RoleConstant.ADD_POPULATION_ROLE);
		message.putInt(value);
		if (ioSession != null) {
			ioSession.write(message);
		}
	}

	public void addRoleExp(Role role, int value) {

		// role.setExp(role.getExp() + value);
		// this.checkHeroLv(role);
	}

	public void addRoleExploit(Role role, int value) {
		int originTotal = role.getExploit();
		int total = role.getExploit()+value;
		if (total < 0) {
			if (value >= 0) {
				total = Integer.MAX_VALUE;
			} else {
				total = 0;
			}
		}
		value = total - originTotal;
		role.setExploit(total);
		IoSession ioSession = SessionCache.getSessionById(role.getId());
		Message message = new Message();
		message.setType(RoleConstant.ADD_EXPLOIT_ROLE);
		message.putInt(value);
		if (ioSession != null) {
			ioSession.write(message);
		}
	}

	public void addRoleGold(Role role, int value) {
		int originTotal = role.getGold();
		int total = role.getGold()+value;
		if (total < 0) {
			if (value >= 0) {
				total = Integer.MAX_VALUE;
			} else {
				total = 0;
			}
		}
		value = total-originTotal;
		role.setGold(total);
		IoSession ioSession = SessionCache.getSessionById(role.getId());
		Message message = new Message();
		message.setType(RoleConstant.ADD_GOLD_ROLE);
		message.putInt(value);
		if (ioSession != null) {
			ioSession.write(message);
		}
	}

	public void addRolePrestige(Role role, int value) {
		int originTotal = role.getPrestige();
		int total = role.getPrestige() + value;
		if (total < 0) {
			if (value >= 0) {
				total = Integer.MAX_VALUE;
			} else {
				total = 0;
			}
		}
		value = total-originTotal;
		role.setPrestige(total);
		IoSession ioSession = SessionCache.getSessionById(role.getId());
		Message message = new Message();
		message.setType(RoleConstant.ADD_PRESTIGE_ROLE);
		message.putInt(value);
		if (ioSession != null) {
			ioSession.write(message);
		}
	}

	/**
	 * 获得军令（服务器主动推送）1508
	 * 
	 * @param role
	 * @param num
	 */
	public void getArmyToken(Role role, int num) {
		role.setArmyToken(role.getArmyToken() + num);
		Message message = new Message();
		message.setType(RoleConstant.ADD_ARMYTOKEN_ROLE);
		message.putInt(num);
		IoSession ioSession = SessionCache.getSessionById(role.getId());
		if (ioSession != null) {
			ioSession.write(message);
		}
	}

	/**
	 * 设定玩家名字
	 * 停用
	 * @author xjd
	 */
	public Message setRoleName(Role role, String name) {
		Message message = new Message();
		message.setType(RoleConstant.SET_ROLE_NAME);
		// 判断字符串长度 最大6字节 最小2字节
		if (name.length() > RoleConstant.MAX_LENGTH_NAME || name.length() < RoleConstant.MIN_LENGTH_NAME) {
			message.putShort(ErrorCode.NAME_OVER_LENGTH);
			return message;
		}
		// 判断名字是否和谐
		LawlessChars chars = LawlessCharsCache.getLawlessCharsByString(name);
		if (chars != null) {
			message.putShort(ErrorCode.ERR_NAME_FORMAT);
			return message;
		}
		// 判断玩家名字是否重复
		if (RoleCache.getNameSet().contains(name)) {
			message.putShort(ErrorCode.NAME_IS_AREADY_HAS);
			return message;
		}
		// 设定玩家的名字 缓存
		RoleCache.getNameSet().remove(role.getName());
		RoleCache.getNameIdMap().remove(role.getName());
		RoleCache.getRoleNameMap().remove(role.getName());
		role.setName(name);
		RoleCache.getNameSet().add(role.getName());
		RoleCache.getNameIdMap().put(role.getName(), role.getId());
		RoleCache.getRoleNameMap().put(role.getName(), role);
		role.setChange(true);
		this.taskService.checkRoleNameTask(role, (byte) 1);

		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 判断玩家名字是否和谐
	 */
	public Message setRoleName(String name) {
		Message message = new Message();
		message.setType(RoleConstant.SET_ROLE_NAME);
		// 判断字符串长度 最大6字节 最小2字节
		if (name.length() > RoleConstant.MAX_LENGTH_NAME || name.length() < RoleConstant.MIN_LENGTH_NAME) {
			message.putShort(ErrorCode.NAME_OVER_LENGTH);
			return message;
		}
		// 判断名字是否和谐
		LawlessChars chars = LawlessCharsCache.getLawlessCharsByString(name);
		if (chars != null) {
			message.putShort(ErrorCode.ERR_NAME_FORMAT);
			return message;
		}
		// 判断玩家名字是否重复
		if (RoleCache.getNameSet().contains(name)) {
			message.putShort(ErrorCode.NAME_IS_AREADY_HAS);
			return message;
		}
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	
	/**
	 * 设定玩家的头像 1506
	 * 
	 * @author xjd
	 */
	public Message setRoleFaceId(Role role, byte faceId) {
		Message message = new Message();
		message.setType(RoleConstant.SET_ROLE_FACE);
		// 判断传入的头像编号是否合法
		if (faceId < RoleConstant.MIN_LENGTH_NAME || faceId > RoleConstant.MAX_SIZE_FACEID) {
			return null;
		}
		// 设定玩家的头像
		role.setFaceId(faceId);
		role.setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 主角获得经验后检查是否升级
	 * 
	 * @param role
	 */
	private void checkHeroLv(Role role) {
		RoleExpConfig roleExpConfig = RoleExpConfigCache.getMap().get((int) role.getLv());
		if (roleExpConfig == null) {
			return;
		}
		if (role.getExp() < roleExpConfig.getNextExp()) {
			this.roleAddExp(role);
			return;
		}
		if (role.getExp() >= roleExpConfig.getNextExp()) {
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

			if (role.getLv() >= (short) (maxLv)) {
				role.setExp(roleExpConfig.getBaseExp());
				this.checkHeroLv(role);
			} else {
				this.roleLvUp(role);
				this.checkHeroLv(role);
			}
		}
	}

	/**
	 * 获取官邸信息 1520
	 */
	@Override
	public Message getRoleLvData(Role role) {
		Message message = new Message();
		message.setType(RoleConstant.GET_ROLE_DATA);

		short roleLv = role.getLv();
		MainCityConfig roleExpConfig = MainCityConfigCache.getMainCityConfig((int) roleLv);
		int time = roleExpConfig.getUpgradeTime();
//		int money = roleExpConfig.getCostCoins();
		int money = this.getRoleLvUpNeedGold((int)roleLv, roleExpConfig);

		Map<Byte, BuildInfo> attachHeroMap = role.getBuild().getAttachHeroMap();
		
		BuildInfo mainBuildInfo = attachHeroMap.containsKey(InComeConstant.TYPE_MAIN_CITY)
			? attachHeroMap.get(InComeConstant.TYPE_MAIN_CITY)
			: null;
			
		Hero hero = null;
		Integer attachHeroId = 0;
		if (mainBuildInfo == null){			
			attachHeroId = 0;			
		}else{
			attachHeroId = mainBuildInfo.getHeroId();
			hero = role.getHeroMap().get(attachHeroId);
		}
		int captain = InComeConstant.NO_HERO;
		int power = InComeConstant.NO_HERO;
		int in = InComeConstant.NO_HERO;
		int captainValue = InComeConstant.NO_HERO;
		int powerValue = InComeConstant.NO_HERO;
		int intelValue = InComeConstant.NO_HERO;		
		int lastAttachTime = 0;
		
		if (hero != null) {
			captainValue = HeroUtils.getCaptainValue(hero);
			powerValue = HeroUtils.getPowerValue(hero);
			intelValue = HeroUtils.getIntelValue(hero);
			captain = InComeUtils.getValueCaption(role, hero);
			captain = inComeService.getHourIncomeValue(role, InComeConstant.TYPE_MAIN_CITY);
			power = InComeUtils.getValuePower(role, hero);
			in = InComeUtils.getValueIn(role, hero);
			lastAttachTime = mainBuildInfo.getLastTime();
		}

		message.putInt(money);
		message.putInt(time);
		message.putShort(roleLv);
		message.putInt(captainValue);
		message.putInt(captain);
		message.putInt(powerValue);
		message.putInt(power);
		message.putInt(intelValue);
		message.putInt(in);
		message.putInt(attachHeroId);
		message.putInt(lastAttachTime);

		return message;

	}

	/***
	 * 引导表示变更
	 * @author xjd
	 */
	public Message setRoleLead(Role role, byte type, String info) {
		Message message = new Message();
		message.setType(RoleConstant.SET_LEAD_POINT);
		switch (type) {
		case RoleConstant.LEAD_S:
			role.setLeadPoint(Integer.parseInt(info));
			break;
		case RoleConstant.LEAD_R:
			role.setLeadStr(info);
			break;
		default:
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		role.setChange(true);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	public Message finishNewBuild(Role role, String key) {
		Message message = new Message();
		message.setType(RoleConstant.FINISH_NEW_BUILD);
		Map<Byte,Short> buildLvMap = role.getBuild().getBuildLvMap();
		
		if(!role.getIconList().contains(key))
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		
		IconUnlockConfig iconUnlockConfig = IconUnlockConfigCache.getIconUnlockConfigByName(key);
		byte type = (byte)iconUnlockConfig.getType();
		
		buildLvMap.put(type, (short) 1);		
		
		
		
//		if(type == InComeConstant.TYPE_PUB){			
//			pubService.initPub(role);
//		}
		if(type == InComeConstant.TYPE_FORGE){
			propService.initForge(role);
		}
		if(type==InComeConstant.TYPE_CAMP){
			barrackService.initBarrack(role);
		}
		// 集市解锁
		if (type == InComeConstant.TYPE_MARKET) {
			List<MarketConfig> marketConfigList = MarketConfigCache.getMarketConfigByLv((int) 1);

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
		
		role.getIconList().remove(key);
		message.putShort(ErrorCode.SUCCESS);
		message.putString(key);
		inComeService.buildInfoChange(role,(byte) iconUnlockConfig.getType(),(byte) 0, Utils.getNowTime());
		return message;
	}
	
	
	public int getRoleLvUpNeedGold(int roleLv,MainCityConfig mainCityConfig) {
		Server server = ServerCache.getServer();
		int worldLv = server.getWorldLv();

		int needMoney = mainCityConfig.getCostCoins();
		int key1 = GeneralNumConstantCache.getValue("WORLD_CONSTANT_KEY1");
		int value1 = GeneralNumConstantCache.getValue("WORLD_CONSTANT_VALUE1");
		int key2 = GeneralNumConstantCache.getValue("WORLD_CONSTANT_KEY2");
		int value2 = GeneralNumConstantCache.getValue("WORLD_CONSTANT_VALUE2");
		int key3 = GeneralNumConstantCache.getValue("WORLD_CONSTANT_KEY3");
		int value3 = GeneralNumConstantCache.getValue("WORLD_CONSTANT_VALUE3");
		
		int v = 0;
		if (roleLv + key1 <= worldLv) {
			v = value1;
		} else if (roleLv + key2 <= worldLv) {
			v = value2;
		} else if (roleLv + key3 <= worldLv) {
			v = value3;
		}

		if (v != 0) {
			needMoney = (int) Math.ceil(needMoney - needMoney * (v * 0.01f));
		}

		return needMoney;
	}
	
	@Override
	public Message roleLvUp(Role role) {
		/*****************************************/
		Message message = new Message();
		message.setType(RoleConstant.ROLE_CITY_LV_UP);
		MainCityConfig roleExpConfig = MainCityConfigCache.getMainCityConfig((int) role.getLv());
//		int needMoney = roleExpConfig.getCostCoins();
		int needMoney = getRoleLvUpNeedGold((int)role.getLv(), roleExpConfig);
		
		if (role.getMoney() < needMoney) { // 升级所需金钱不足
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}
		//
		// 升级成功 更改建筑等级 扣除主角金钱 更新建筑队列
		//
		// 刷新建筑队列时间
		int nowTime = Utils.getNowTime();
		inComeService.refreshBuildTime(role);
		// 获取空闲的ID
		Map<Byte, BuildQueue> buildQueueMap = role.getBuildQueueMap();
		byte queueId = 0;
		for (Map.Entry<Byte, BuildQueue> entry : buildQueueMap.entrySet()) {
			if (entry.getValue().getOpen() == 0) {
				queueId = (byte) entry.getValue().getId();
				break;
			}
		}
		if (queueId <= 0) { // 没有空闲的建筑队列
			message.putShort(ErrorCode.NO_BUILD_QUEUE);
			return message;
		}
		Map<Byte, BuildQueue> map = role.getBuildQueueMap();
		BuildQueue buildQueue = map.get(queueId);

		// 检查章节过关程度是否可以升级
		LvCheck lc = checkChapterAllowRoleLvUp(role);
		if (!lc.isFlag()) {
			message.putShort(ErrorCode.CHAPTER_NO_ALLOW_LEVEL_UP);
			message.putString(lc.getChapterName());
			return message;
		}

		// 建筑队列添加时间
		int cdTime = inComeService.getBuildLvUpCdTime(role.getBuild(), roleExpConfig.getUpgradeTime());
		buildQueue.setTime(buildQueue.getTime() + cdTime);
		int buildQueneTime = buildQueue.getTime();
		if (buildQueneTime >= InComeConstant.MAX_QUEUE_TIME) {
			// 关闭建筑队列
			buildQueue.setOpen(InComeConstant.CLOSE_QUEUE);
		}
		role.setLv((short) (role.getLv() + 1));
		addRoleMoney(role, -needMoney);
		role.setChange(true);

		this.taskService.lvUpTaskCheck(role);
		this.taskService.checkRoleLv(role, 1); // 是否开启每日任务功能
		if (role.getLv() == RoleConstant.MIN_DAILY_TASK_LV) {
			this.taskService.initDailyTask(role);
		} // 是否开启市场
		if (role.getLv() == RoleConstant.MIN_MARKET_LV) {
			this.marketService.changMarket(role);
		}
		
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(roleExpConfig.getCostCoins());
		message.putInt(roleExpConfig.getUpgradeTime());

		short roleLv = role.getLv();
		roleExpConfig = MainCityConfigCache.getMainCityConfig((int) roleLv);
		int time = roleExpConfig.getUpgradeTime();
		int money = getRoleLvUpNeedGold((int)roleLv, roleExpConfig);
		Server server = ServerCache.getServer();
		server.getRoleLvMap().put(role.getId(), (int) roleLv);

		message.putInt(money);
		message.putInt(time);
		message.putShort(roleLv);
		message.putInt(cdTime);
		
		taskService.checkInComeTask(role, InComeConstant.TYPE_FARM, TaskConstant.TYPE_BYTE_1);
		taskService.checkInComeTask(role, InComeConstant.TYPE_HOUSE, TaskConstant.TYPE_BYTE_1);
		
		this.targetService.checkLvTarget(role);
		
		inComeService.buildInfoChange(role, InComeConstant.TYPE_MAIN_CITY,InComeConstant.CHANGE_TYPE_LV, nowTime);
		inComeService.buildInfoChange(role, InComeConstant.TYPE_MAIN_CITY,InComeConstant.CHANGE_TYPE_INCOME, nowTime);
		
		//记录排行榜
		this.recordRankAndSendNotable(role, nowTime);
		
		if(chatService.checkSystemChat(ChatConstant.MESSAGE_TYPE_ROLE_LV, role.getLv())){			
			StringBuilder sb = this.getRoleLvStringBuilder(role);
			chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_ROLE_LV, sb.toString());
		}
		
		// 查询任务，是否解锁新建筑，
		List<IconUnlockConfig> list = IconUnlockConfigCache.getLevelUnlockList((int) role.getLv());
		if (list == null) {
			return message;
		}
		// 图标解锁
		this.sendMessageNewOpen(role, list);					
		
		return message;
		/*******************************************/

		// short nextLv = (short) (role.getLv() + 1);
		// role.setLv(nextLv);
		// this.taskService.lvUpTaskCheck(role);
		// this.taskService.checkRoleLv(role, 1); // 是否开启每日任务功能
		// if (nextLv == RoleConstant.MIN_DAILY_TASK_LV) {
		// this.taskService.initDailyTask(role);
		// } // 是否开启市场
		// if (nextLv == RoleConstant.MIN_MARKET_LV) {
		// this.marketService.changMarket(role);
		// }
		// inComeService.checkGetBuild(role); // 玩家当前等级当前经验
		// RoleExpConfig expConfig = RoleExpConfigCache.getMap().get((int)
		// role.getLv());
		// int tempExp = role.getExp() - expConfig.getBaseExp();
		// IoSession is = SessionCache.getSessionById(role.getId());
		// Message message = new Message();
		// message.setType(RoleConstant.ROLE_LV_UP);
		// message.putShort(role.getLv());
		// message.putInt(tempExp);
		// message.putInt(expConfig.getNextExp() - expConfig.getBaseExp());
		// is.write(message);
		// List<IconUnlockConfig> list =
		// IconUnlockConfigCache.getLevelUnlockList((int) role.getLv());
		// if (list == null) {
		// return;
		// } // 图标解锁 this.sendMessage1704(role, list);

	}
	
	@Override
	public void roleLvUp(Role role,Message message) {
		/*****************************************/
		
		MainCityConfig roleExpConfig = MainCityConfigCache.getMainCityConfig((int) role.getLv());
//		int needMoney = roleExpConfig.getCostCoins();
		int needMoney = getRoleLvUpNeedGold((int)role.getLv(), roleExpConfig);
		
		short nextLv = 0;
		int nextMoney = 0;
		int nextUpTime = 0;
		int currentMoney = 0;
		int currentUpTime = 0;
		if (role.getMoney() < needMoney) { // 升级所需金钱不足
			message.putShort(ErrorCode.NO_MONEY);
			return ;
		}
		//
		// 升级成功 更改建筑等级 扣除主角金钱 更新建筑队列
		//
		// 刷新建筑队列时间
		int nowTime = Utils.getNowTime();
		inComeService.refreshBuildTime(role);
		// 获取空闲的ID
		Map<Byte, BuildQueue> buildQueueMap = role.getBuildQueueMap();
		byte queueId = 0;
		for (Map.Entry<Byte, BuildQueue> entry : buildQueueMap.entrySet()) {
			if (entry.getValue().getOpen() == 0) {
				queueId = (byte) entry.getValue().getId();
				break;
			}
		}
		if (queueId <= 0) { // 没有空闲的建筑队列
			message.putShort(ErrorCode.NO_BUILD_QUEUE);
			return ;
		}
		Map<Byte, BuildQueue> map = role.getBuildQueueMap();
		BuildQueue buildQueue = map.get(queueId);

		// 检查章节过关程度是否可以升级
		LvCheck lc = checkChapterAllowRoleLvUp(role);
		if (!lc.isFlag()) {
			message.putShort(ErrorCode.CHAPTER_NO_ALLOW_LEVEL_UP);
			message.putString(lc.getChapterName());
			return ;
		}

		// 建筑队列添加时间
		currentUpTime = inComeService.getBuildLvUpCdTime(role.getBuild(), roleExpConfig.getUpgradeTime());
		buildQueue.setTime(buildQueue.getTime() + currentUpTime);
		int buildQueneTime = buildQueue.getTime();
		if (buildQueneTime >= InComeConstant.MAX_QUEUE_TIME) {
			// 关闭建筑队列
			buildQueue.setOpen(InComeConstant.CLOSE_QUEUE);
		}
		role.setLv((short) (role.getLv() + 1));
		addRoleMoney(role, -needMoney);
		role.setChange(true);

		this.taskService.lvUpTaskCheck(role);
		this.taskService.checkRoleLv(role, 1); // 是否开启每日任务功能
		if (role.getLv() == RoleConstant.MIN_DAILY_TASK_LV) {
			this.taskService.initDailyTask(role);
		} // 是否开启市场
		if (role.getLv() == RoleConstant.MIN_MARKET_LV) {
			this.marketService.changMarket(role);
		}
		
		message.putShort(ErrorCode.SUCCESS);
		currentMoney = roleExpConfig.getCostCoins();
		currentUpTime = roleExpConfig.getUpgradeTime();

		short roleLv = role.getLv();
		nextLv = roleLv;
		roleExpConfig = MainCityConfigCache.getMainCityConfig((int) roleLv);
		nextUpTime = roleExpConfig.getUpgradeTime();
		nextMoney= getRoleLvUpNeedGold((int)roleLv, roleExpConfig);
		Server server = ServerCache.getServer();
		server.getRoleLvMap().put(role.getId(), (int) roleLv);

		
		taskService.checkInComeTask(role, InComeConstant.TYPE_FARM, TaskConstant.TYPE_BYTE_1);
		taskService.checkInComeTask(role, InComeConstant.TYPE_HOUSE, TaskConstant.TYPE_BYTE_1);
		
		this.targetService.checkLvTarget(role);
		
		inComeService.buildInfoChange(role, InComeConstant.TYPE_MAIN_CITY,InComeConstant.CHANGE_TYPE_LV, nowTime);
		inComeService.buildInfoChange(role, InComeConstant.TYPE_MAIN_CITY,InComeConstant.CHANGE_TYPE_INCOME, nowTime);
		
		message.putShort(nextLv);
		message.putInt(nextMoney);
		message.putInt(nextUpTime);
		message.putInt(currentMoney);
		message.putInt(currentUpTime);
		//记录排行榜
		this.recordRankAndSendNotable(role, nowTime);
		
		if(chatService.checkSystemChat(ChatConstant.MESSAGE_TYPE_ROLE_LV, role.getLv())){			
			StringBuilder sb = this.getRoleLvStringBuilder(role);
			chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_ROLE_LV, sb.toString());
		}
		
		// 查询任务，是否解锁新建筑，
		List<IconUnlockConfig> list = IconUnlockConfigCache.getLevelUnlockList((int) role.getLv());
		if (list == null) {
			return ;
		}
		
		
		// 图标解锁
		this.sendMessageNewOpen(role, list);	
		
		for (IconUnlockConfig iconUnlockConfig : list) {
			inComeService.buildInfoChange(role,(byte) iconUnlockConfig.getType(),(byte) 0, Utils.getNowTime());			
		}
		
		return ;
	}
	

	private void recordRankAndSendNotable(Role role, int nowTime) {
		List<Rank> roleLvRanklist = rankService.sortRoleLvRank();
		List<Rank> originTopRankList = rankService.getTopCountList(roleLvRanklist, 1);	
		
		rankService.refreshRoleLvRank(role, nowTime);
		List<Rank> targetRankList = rankService.sortRoleLvRank();
		//发送走马灯
		rankService.ifTopRankChangeThenNotable(RankConstant.RANK_TYPE_ROLE_LV, originTopRankList, targetRankList);
	}

	private StringBuilder getRoleLvStringBuilder(Role role) {
		StringBuilder sb = new StringBuilder();
		sb.append(Utils.getNationName(role.getNation())).append(",");
		sb.append(role.getLv()).append(",");
		sb.append(role.getId()).append(",");
		sb.append(role.getName()).append(",");
		return sb;
	}

	/**
	 * 每日友好提
	 * @author xjd
	 */
	public Message friendTip(Role role, byte type) {
		Message message = new Message();
		message.setType(RoleConstant.FRIEND_TIP);
		int num = RoleConstant.NUM_0;
		if(role.getFriendTipMap().containsKey(type))
		{
			num = role.getFriendTipMap().get(type);
			role.getFriendTipMap().put(type, num + RoleConstant.NUM_1);
		}else {
			role.getFriendTipMap().put(type,RoleConstant.NUM_1);
		}
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(num);
		return message;
	}
	
	
	private void sendMessageNewOpen(Role role, List<IconUnlockConfig> list) {
		IoSession is = SessionCache.getSessionById(role.getId());
		Message message = new Message();
		message.setType(RoleConstant.OPEN_NEW_BUILD);

		for (IconUnlockConfig iconUnlockConfig : list) {
			role.getIconList().add(iconUnlockConfig.getName());		
		}
		role.setChange(true);
		message.putString(role.getIconUnlock());
		
		is.write(message);
		
	}

	/**
	 * 检查章节过关程度是否允许升级
	 * 
	 * @param roleLv
	 * @return
	 * @author wcy
	 */
	private LvCheck checkChapterAllowRoleLvUp(Role role) {
		LvCheck lc = new LvCheck();
		MainCityConfig x = MainCityConfigCache.getMainCityConfig(role.getLv());
		ChapterConfig chapterConfig = ChapterConfigCache.getChapterConfigById(x.getChapterLimit());
		if (x.getChapterLimit() == 0 || role.getChapter().getStarMap().containsKey(x.getChapterLimit())) {
			lc.setFlag(true);
		} else if (x.getChapterLimit() == -1) {
			lc.setChapterName("已到版本最高级别");
		} else {
			lc.setChapterName(chapterConfig.getTitle());
		}

		return lc;
	}

	/**
	 * 主角获得经验
	 * 
	 * @author xjd
	 * 
	 */
	private void roleAddExp(Role role) {
		IoSession is = SessionCache.getSessionById(role.getId());
		RoleExpConfig expConfig = RoleExpConfigCache.getMap().get((int) role.getLv());
		int tempExp = role.getExp() - expConfig.getBaseExp();
		Message message = new Message();
		message.setType(RoleConstant.ADD_EXP_ROLE);
		message.putShort(role.getLv());
		message.putInt(tempExp);
		message.putInt(expConfig.getNextExp() - expConfig.getBaseExp());
		if (is != null) {
			is.write(message);
		}
	}

	public int randowRole() {
		int roleId = 0;
		Integer[] keys = RoleCache.getRoleMap().keySet().toArray(new Integer[0]);
		Random random = new Random();
		roleId = keys[random.nextInt(keys.length)];

		return roleId;
	}

	private boolean checkIsHero(Role role) {
		boolean flag = false;
		Map<Byte,BuildInfo> attachHeroMap = role.getBuild().getAttachHeroMap();
		BuildInfo mainCityHero = attachHeroMap.get(InComeConstant.TYPE_MAIN_CITY);
		if (mainCityHero != null && mainCityHero.getHeroId() != InComeConstant.NO_HERO)
			flag = true;

		return flag;
	}

	@Override
	public void refreshGetPrestige(Role role,int nowTime) {
		inComeService.refreshGetResources(role, nowTime, InComeConstant.TYPE_MAIN_CITY);
	}

	
	@Override
	public List<Role> getAllRole() {
		List<Role> roleList = roleDao.getAllRole();
		for(Role role:roleList){			
			loginService.loginRoleModuleDataInit(role);
		}
		return roleList;
	}

	/**
	 * 获得建筑升级cd时间
	 * 
	 * @param build
	 * @param baseCdTime
	 * @return
	 * @author wcy
	 */
//	private int getBuildLvUpCdTime(Build build, int baseCdTime) {
//		Map<Byte, Integer> attachHeroMap = build.getAttachHeroMap();
//		if (attachHeroMap == null) {
//			return baseCdTime;
//		}
//		Integer heroId = attachHeroMap.get(InComeConstant.TYPE_MAIN_CITY);
//		if (heroId == null) {
//			return baseCdTime;
//		}
//		int roleId = build.getRoleId();
//		Role role = RoleCache.getRoleById(roleId);
//		Hero hero = role.getHeroMap().get(heroId);
//		if (hero == null) {
//			return baseCdTime;
//		}
//
//		int captain = HeroUtils.getCaptainValue(hero);
//		baseCdTime = (int) (baseCdTime / Math.pow(1.005, captain));
//		return baseCdTime;
//
//	}
}
