package byCodeGame.game.moudle.login.service;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.CityInfoConfigCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.LawlessCharsCache;
import byCodeGame.game.cache.file.ReputationShopUnlockCache;
import byCodeGame.game.cache.file.RoleExpConfigCache;
import byCodeGame.game.cache.file.TargetConfigCache;
import byCodeGame.game.cache.file.TavernStrategyConfigCache;
import byCodeGame.game.cache.local.ArenaCache;
import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.cache.local.LegionCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.ServerCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.BarrackDao;
import byCodeGame.game.db.dao.BuildDao;
import byCodeGame.game.db.dao.ChapterDao;
import byCodeGame.game.db.dao.CityDao;
import byCodeGame.game.db.dao.FriendDao;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.db.dao.LadderArenaDao;
import byCodeGame.game.db.dao.MailDao;
import byCodeGame.game.db.dao.MarketDao;
import byCodeGame.game.db.dao.PubDao;
import byCodeGame.game.db.dao.RoleArenaDao;
import byCodeGame.game.db.dao.RoleArmyDao;
import byCodeGame.game.db.dao.RoleDao;
import byCodeGame.game.db.dao.ServerDao;
import byCodeGame.game.db.dao.SignDao;
import byCodeGame.game.db.dao.TargetDao;
import byCodeGame.game.db.dao.TasksDao;
import byCodeGame.game.entity.bo.Barrack;
import byCodeGame.game.entity.bo.Build;
import byCodeGame.game.entity.bo.Chapter;
import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.Friend;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.LadderArena;
import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.entity.bo.Mail;
import byCodeGame.game.entity.bo.Market;
import byCodeGame.game.entity.bo.Pub;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.RoleArmy;
import byCodeGame.game.entity.bo.RoleCity;
import byCodeGame.game.entity.bo.Server;
import byCodeGame.game.entity.bo.Sign;
import byCodeGame.game.entity.bo.Target;
import byCodeGame.game.entity.bo.Tasks;
import byCodeGame.game.entity.file.LawlessChars;
import byCodeGame.game.entity.file.RoleExpConfig;
import byCodeGame.game.entity.file.TargetConfig;
import byCodeGame.game.entity.po.WorldArmy;
import byCodeGame.game.moudle.arena.service.ArenaService;
import byCodeGame.game.moudle.babel.service.BabelService;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.moudle.heart.service.HeartService;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.inCome.InComeConstant;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.moudle.login.LoginConstant;
import byCodeGame.game.moudle.login.RoleInitialValue;
import byCodeGame.game.moudle.market.service.MarketService;
import byCodeGame.game.moudle.nation.NationConstant;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.moudle.pub.PubConstant;
import byCodeGame.game.moudle.pub.service.PubService;
import byCodeGame.game.moudle.rank.RankConstant;
import byCodeGame.game.moudle.rank.service.RankService;
import byCodeGame.game.moudle.role.RoleConstant;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.tools.CacheLockUtil;
import byCodeGame.game.util.SysManager;
import byCodeGame.game.util.Utils;

public class LoginServiceImpl implements LoginService {

	private RoleDao roleDao;
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	private BuildDao buildDao;

	public void setBuildDao(BuildDao buildDao) {
		this.buildDao = buildDao;
	}

	private TasksDao tasksDao;

	public void setTasksDao(TasksDao tasksDao) {
		this.tasksDao = tasksDao;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private PropService propService;

	public void setPropService(PropService propService) {
		this.propService = propService;
	}

	private HeartService heartService;

	public void setHeartService(HeartService heartService) {
		this.heartService = heartService;
	}

	private MailDao mailDao;

	public void setMailDao(MailDao mailDao) {
		this.mailDao = mailDao;
	}

	private HeroService heroService;

	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}

	private HeroDao heroDao;

	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}

	private MarketDao marketDao;

	public void setMarketDao(MarketDao marketDao) {
		this.marketDao = marketDao;
	}

	private MarketService marketService;

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

	private PubDao pubDao;

	public void setPubDao(PubDao pubDao) {
		this.pubDao = pubDao;
	}

	private BarrackDao barrackDao;

	public void setBarrackDao(BarrackDao barrackDao) {
		this.barrackDao = barrackDao;
	}

	private ChapterDao chapterDao;

	public void setChapterDao(ChapterDao chapterDao) {
		this.chapterDao = chapterDao;
	}

	private CityDao cityDao;

	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}

	private FriendDao friendDao;

	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}

	private SignDao signDao;

	public void setSignDao(SignDao signDao) {
		this.signDao = signDao;
	}

	private ArenaService arenaService;

	public void setArenaService(ArenaService arenaService) {
		this.arenaService = arenaService;
	}

	private LadderArenaDao ladderArenaDao;

	public void setLadderArenaDao(LadderArenaDao ladderArenaDao) {
		this.ladderArenaDao = ladderArenaDao;
	}

	private RoleArenaDao roleArenaDao;

	public void setRoleArenaDao(RoleArenaDao roleArenaDao) {
		this.roleArenaDao = roleArenaDao;
	}

	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private CityService cityService;

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	private PubService pubService;
	public void setPubService(PubService pubService) {
		this.pubService = pubService;
	}

	private RoleArmyDao roleArmyDao;
	public void setRoleArmyDao(RoleArmyDao roleArmyDao) {
		this.roleArmyDao = roleArmyDao;
	}
	
	private InComeService inComeService;
	public void setInComeService(InComeService inComeService) {
		this.inComeService = inComeService;
	}
	
	private ServerDao serverDao;
	public void setServerDao(ServerDao serverDao){
		this.serverDao = serverDao;
	}
	
	private TargetDao targetDao;
	public void setTargetDao(TargetDao targetDao) {
		this.targetDao = targetDao;
	}
	
	private RankService rankService;
	public void setRankService(RankService rankService){
		this.rankService = rankService;
	}
	
	private BabelService babelService;
	public void setBabelService(BabelService babelService) {
		this.babelService = babelService;
	}
	
	public Message login(String account, IoSession ioSession) {
		ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class, account);
		reentrantLock.lock();
		Message message = new Message();
		message.setType(LoginConstant.LOGIN);
		if (!SysManager.isSERVICE_FLAG()) {
			message.putShort(ErrorCode.SERVICE_CLOSED);
			return message;
		}
		try {
			Set<String> accountSet = RoleCache.getAccountSet();
			if (accountSet.contains(account)) { // 有数据 登录成功
				message.putShort(ErrorCode.SUCCESS);
			} else { // 无数据 创建角色
				message.putShort(ErrorCode.SHORT_TWO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reentrantLock.unlock();
		}
		return message;
	}

	public Message creatRole(String account, IoSession session,byte nation ,String name) {
		if (!Utils.checkCountFromat(account)) {
			return null;
		}
		if(nation != (byte)1 && nation != (byte)2 && nation != (byte)3)
		{
			return null;
		}
		Message message = new Message();
		message.setType(LoginConstant.CREAT_ROLE);
		//判断长度
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
		ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class, account);
		reentrantLock.lock();
		try {
			int nowTime = Utils.getNowTime();
			Set<String> accountSet = RoleCache.getAccountSet();
			if (accountSet.contains(account)) { // 判定账号是否存在
				return null;
			}

			Connection conn = null;
			try { // mysql事务
				conn = dataSource.getConnection();
				conn.setAutoCommit(false);

				// 用户数据
				Role role = this.roleInit(account, conn, session ,nation ,name);
				this.serverInit(role, conn);
				this.buildInit(role, conn);
				this.tasksInit(role, conn);
//				this.heroInit(role, conn);
				// this.formationInit(role);
				this.marketInit(role, conn);
				// 初始酒馆
				this.pubInit(role, conn);
				// 初始道具
				this.propInit(role, conn);
				// 初始兵营
				this.barrackInit(role, conn);
				this.chapterInit(role, conn);
				// 玩家封地信息初始化
				this.roleCityInit(role, conn);
				// 初始玩家好友列表
				this.friendInit(role, conn);
				// 初始玩家签到信息
				this.signInit(role, conn);
				this.roleArenaInit(role, conn);
				// 兵种研究院
				this.roleArmyInit(role, conn);
				conn.commit(); // 提交JDBC事务
				conn.setAutoCommit(true); // 恢复JDBC事务的默认提交方式
				// 加入role缓存
				RoleCache.putRole(role);
				RoleCache.getAccountSet().add(account);
				RoleCache.getNameSet().add(role.getName());
				//加入服务器表
				ServerCache.getServer().getRoleLvMap().put(role.getId(), (int) role.getLv());
				//记录玩家排名
				rankService.refreshRoleLvRank(role, nowTime);
				message.putShort(ErrorCode.SUCCESS);
				return message;

			} catch (Exception e1) {
				e1.printStackTrace();
				try {
					conn.rollback();// 回滚JDBC事务
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				e1.printStackTrace();
				return null;
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			reentrantLock.unlock();
		}
	}

	/**
	 * 生成随机用户名
	 * 
	 * @return
	 */
	private String getRandowName() {
		Set<String> nameSet = RoleCache.getNameSet();
		StringBuilder sb = new StringBuilder();
		sb.append("用户");
		String rs = Utils.randowStr(6);
		sb.append(rs);

		if (nameSet.contains(sb.toString())) {
			this.getRandowName();
		}
		return sb.toString();
	}

	public Message getRoleData(String account, IoSession ioSession) {
		ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class, account);
		reentrantLock.lock();

		try {
			Message message = new Message();
			message.setType(LoginConstant.GET_ROLE_DATA);

			// 获取缓存中的role
			Role role = RoleCache.getRoleByAccount(account);

			if (role == null) { // 缓存中没有role数据
				role = roleDao.getRoleByAccount(account);
				this.loginRoleModuleDataInit(role);
			}
			String clientIP = ((InetSocketAddress) ioSession.getRemoteAddress()).getAddress().getHostAddress();
			long loginTime = Utils.getNowTime();
			role.setLoginTime(loginTime);
			role.setLastLoginIp(clientIP);
			int loginNum = role.getLoginNum();
			role.setLoginNum(loginNum + 1);
			// 计算离线军令数量
			heartService.updateArmyToken(role);
			// 检查时候需要更新市场
			marketService.checkLastChange(role);
//			marketService.refreshWorldMarketItems(role,Utils.getNowTime());
			//检测配属英雄
			inComeService.checkHeroManual(role,(int) loginTime);
			IoSession oldSession = SessionCache.getSessionById(role.getId());
			if (oldSession != null) { // 该账号已登录
				oldSession.setAttribute("roleId", null);
				oldSession.close(false);
			}

			// session绑定ID
			ioSession.setAttribute("roleId", role.getId());
			// session放入缓存
			SessionCache.addSession(role.getId(), ioSession);
			// System.out.println(ioSession + "放入缓存");

			message.putShort(ErrorCode.SUCCESS);
			message.putString(Long.toString(System.currentTimeMillis() / 1000));
			message.putInt(role.getId());
			message.putString(role.getName());
			RoleExpConfig expConfig = RoleExpConfigCache.getMap().get((int) role.getLv());
			int tempExp = role.getExp() - expConfig.getBaseExp();
			message.putInt(tempExp);
			message.putInt(expConfig.getNextExp() - expConfig.getBaseExp());
			message.putShort(role.getLv());
			message.putInt(role.getGold());
			message.putInt(role.getMoney());
			message.putInt(role.getFood());
			message.putInt(role.getExploit());
			message.putInt(role.getArmyToken());
			if (role.getLv() < LoginConstant.ARMY_TOKEN_LV) {
				message.putInt(LoginConstant.ARMY_TOKEN_LV);
			} else {
				message.putInt(role.getLv());
			}
			message.put(role.getVipLv());
			message.putInt(role.getLegionId());
			message.putShort(role.getMaxBagNum());
			message.putShort(role.getPub().getRecruitHeroNum());
			message.putInt(role.getChapter().getNowChapterId());
			Legion legion = LegionCache.getLegionById(role.getLegionId());
			if (legion != null) {
				message.putString(legion.getName());
			} else {
				message.putString(LoginConstant.NO_LEGION);
			}
			message.putInt(role.getPopulation(role));
			message.putInt(role.getPopulationLimit(role));
			message.putInt(0);
			City city = CityCache.getCityByCityId(CityInfoConfigCache.getBirthCityByNation(role.getNation()).getCityId());
			int key = 0;
			for(int i = 1 ;i <= city.getRoleMap().size();i++)
			{
				if(city.getRoleMap().get(i) == role.getId())
				{
					key = i;
					break;
				}
			}
			message.putInt(key);
			message.put(role.getNation());
			message.putInt(role.getPrestige());
			// 功能图标解锁star
			message.putString(role.getIconUnlock());
			// 功能图标解锁end
			message.putInt(role.getLeadPoint());
			message.putString(role.getLeadStr());
			message.putInt(ServerCache.getServer().getYear());
			message.put(ServerCache.getServer().getSeason());
			message.putInt(role.getRank());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			reentrantLock.unlock();
		}

	}

	/**
	 * 插入Build数据
	 * 
	 * @param role
	 */
	private void buildInit(Role role, Connection conn) {
		long nowTime = System.currentTimeMillis() / 1000;
		Build build = new Build();
		build.setRoleId(role.getId());
		build.setFarmLv(RoleInitialValue.FARM_LV);
		build.setHouseLv(RoleInitialValue.HOUSE_LV);
		build.setBuildLv(RoleInitialValue.BULID_LV);
		build.setInComeCacheStr(RoleInitialValue.INCOME_CACHE);
		build.setIncomeLastDayCacheStr(RoleInitialValue.LAST_DAY_INCOME_CACHE);
		
		//有库存的建筑
		List<Byte> buildIncomeList = new ArrayList<>();
		buildIncomeList.add(InComeConstant.TYPE_MAIN_CITY);
		buildIncomeList.add(InComeConstant.TYPE_FARM);
		buildIncomeList.add(InComeConstant.TYPE_HOUSE);
		buildIncomeList.add(InComeConstant.TYPE_PUB);
		buildIncomeList.add(InComeConstant.TYPE_FORGE);
		buildIncomeList.add(InComeConstant.TYPE_CAMP);		
		
		StringBuffer sb = new StringBuffer();
		for(Byte type : buildIncomeList)
			sb.append(type).append(",").append(nowTime).append(";");
		
		build.setBuildLastIncomeTimeStr(sb.toString());
		role.setBuild(build);

		buildDao.insertBuild(build, conn);
	}

	/**
	 * 初始化用户数据
	 * 
	 * @param account
	 * @param conn
	 * @return
	 */
	private Role roleInit(String account, Connection conn, IoSession session,
			byte nation ,String name) {
		int nowTime = Utils.getNowTime();
		// 创建用户
		Role role = new Role();
		role.setAccount(account);
		role.setMoney(GeneralNumConstantCache.getValue("ROLE_MONEY_INIT"));
		role.setFood(GeneralNumConstantCache.getValue("ROLE_FOOD_INIT"));
		if(	Utils.getNowTime() - ServerCache.getServer().getStartTime() 
				>
			GeneralNumConstantCache.getValue("NATION_AWARD_TIME")
			&&	nation == this.getMinNationNum())
		{
			role.setGold(GeneralNumConstantCache.getValue("NATION_AWARD_GOLD"));
		}else {
			role.setGold(GeneralNumConstantCache.getValue("ROLE_GOLD_INIT"));
		}
		
		role.setPrestige(GeneralNumConstantCache.getValue("ROLE_PRESTIGE_INIT"));
		role.setPopulation(RoleInitialValue.POPULATION);
		role.setPopulationLimit(RoleInitialValue.POPULATION_LIMIT);
		role.setArmyToken(RoleInitialValue.ARMY_TOKEN);
		role.setLv(RoleInitialValue.LV);
		role.setMaxBagNum(RoleInitialValue.MAX_BAG_NUM);
		role.setExploit(RoleInitialValue.EXPLOIT);
		role.setBuildQueue(RoleInitialValue.BUILD_QUEUE);
		role.setRoleScienceQueue(RoleInitialValue.ROLESCIENCE_QUEUE);
		role.setUseFormationID(RoleInitialValue.USEFORMATIONID);
		role.setArmsResearchStr(RoleInitialValue.ARMS_RESEACH);
//		String name = this.getRandowName();
		role.setName(name);
		role.setNation(nation);
		role.setRoleScience(RoleInitialValue.INIT_SCIENCE);
		role.setRaidTimes(RoleInitialValue.RAID_TIMES);
		role.setArmsResearchNum(RoleInitialValue.ARMS_RESEARCH_INIT);
		String clientIP = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
		role.setRegisterIp(clientIP);
		long registerTime = System.currentTimeMillis() / 1000;
		role.setRegisterTime(registerTime);
		role.setIconUnlock(RoleInitialValue.ICON_UNLOCK_INIT);
		role.setLeadPoint(RoleInitialValue.LEAD_POINT);
		role.setLeadStr(RoleInitialValue.LEAD_STR);
		role.setFormationStr(RoleInitialValue.FORMATION_STR);
		role.setWorldFormationStr(RoleInitialValue.WORLD_FORMATION);
		
		StringBuilder sb = new StringBuilder();
		sb.append(RankConstant.RANK_TYPE_FIGHT_VALUE).append(",").append(nowTime).append(";");
		sb.append(RankConstant.RANK_TYPE_INCOME).append(",").append(nowTime).append(";");
		sb.append(RankConstant.RANK_TYPE_KILL).append(",").append(nowTime).append(";");
		sb.append(RankConstant.RANK_TYPE_OWN_CITY).append(",").append(nowTime).append(";");
		sb.append(RankConstant.RANK_TYPE_ROLE_LV).append(",").append(nowTime).append(";");
		role.setRankRecordTimeStr(sb.toString());
		int strongHold = CityInfoConfigCache.getBirthCityByNation(nation).getCityId();
		role.setStrongHold(strongHold);
		int id = roleDao.insertRole(role, conn);
		role.setId(id);
		
		//初始化山村的npc数据
		int cityId = cityService.getVillageIdByNation(nation);
		City village = CityCache.getCityByCityId(cityId);
		String defInfo = village.getDefInfo();
		role.setVillageDefStr(defInfo);
		this.babelService.initTower(role);
		
//		role.setNation((byte) 1);
//		role.setStrongHold(CityInfoConfigCache.getBirthCityByNation(role.getNation()).getCityId());
		
		return role;
	}
	
	/**
	 * 插入玩家等级
	 * @param role
	 * @param conn
	 * @author wcy 2015年12月30日
	 */
	private void serverInit(Role role,Connection conn){
		int roleId = role.getId();
		int lv = role.getLv();
		Server server = ServerCache.getServer();
		ConcurrentHashMap<Integer, Integer> map = server.getRoleLvMap();
		map.put(roleId, lv);

		serverDao.updateServer(server);	
	}

	/**
	 * 用户任务数据初始化 包括目标
	 * 
	 * @param role
	 * @param conn
	 */
	private void tasksInit(Role role, Connection conn) {
		Tasks tasks = new Tasks();
		tasks.setRoleId(role.getId());
		tasks.setDoingTask(RoleInitialValue.DOING_TASK);
		tasksDao.insertTasks(tasks, conn);
		role.setTasks(tasks);
		
		Target target = new Target();
		target.setRoleId(role.getId());
		target.setStage(1);
		byte nation = role.getNation();
		TargetConfig targetConfig = TargetConfigCache.getTargetConfigByNationAndStage(target.getStage(), nation);
		int id = targetConfig.getId();
		String allTargetString = id + RoleInitialValue.TARGET_INIT;
		target.setAllTarget(allTargetString);
		targetDao.insertTarget(target, conn);
		role.setTarget(target);
	}

	/**
	 * 市场数据初始化
	 * 
	 * @param role
	 * @param conn
	 */
	private void marketInit(Role role, Connection conn) {
		Market market = new Market();
		market.setRoleId(role.getId());
		role.setMarket(market);
		market.setMarketLimit((byte) ReputationShopUnlockCache.getVip0Num());
		marketService.initMarket(role,Utils.getNowTime());
		marketDao.insertMarket(market, conn);
		marketService.changMarket(role);
	}

	/**
	 * 酒馆数据初始化
	 * 
	 * @param role
	 * @param conn
	 */
	private void pubInit(Role role, Connection conn) {
		Pub pub = new Pub();
		int totalTime = TavernStrategyConfigCache.getTavenStrategyConfig(PubConstant.TALK_SINGLE_GOLD).getCd();
		int time = totalTime - GeneralNumConstantCache.getValue("GOLD_SINGLE_FIRST_TIME");
		time = time < 0 ? 0 : time;
		pub.setFreeGoldStartTalkTime(Utils.getNowTime() - time);
		pub.setRoleId(role.getId());
		pub.setDesk(RoleInitialValue.PUB_DESK);
		pub.setRecruitHeroNum(RoleInitialValue.RECRUIT_HERO_NUM);
		pub.setMapInfo(RoleInitialValue.PRESTIG_VALUE);
		pub.setMissHero(GeneralNumConstantCache.getValue("PUB_TALK_FIRST"));
		role.setPub(pub);
		pubService.resetDeskHero(role);
		pubService.resetFreeChangeDeskHeroTimes(role);
		pubService.resetTalkSpeedUpTimes(role);
		pubService.initPubHero(role);
		pubService.initPub(role);
		pubDao.insertPub(pub, conn);
	}

	/**
	 * 好友初始化
	 * 
	 * @param role
	 * @param conn
	 * @author xjd
	 */
	private void friendInit(Role role, Connection conn) {
		Friend friend = new Friend();
		friend.setRoleId(role.getId());
		role.setFriend(friend);
		friendDao.insertFriend(friend, conn);
	}

	/**
	 * 签到初始化
	 * 
	 * @param role
	 * @param conn
	 * @author xjd
	 */
	private void signInit(Role role, Connection conn) {
		Sign sign = new Sign();
		sign.setRoleId(role.getId());
		role.setSign(sign);
		signDao.insertSign(sign, conn);
	}

	/**
	 * 初始化用户竞技场数据
	 * 
	 * @param role
	 * @param conn
	 */
	private void roleArenaInit(Role role, Connection conn) {
		ladderArenaDao.insterLadderArenaById(conn, role);
		LadderArena ladderArena = new LadderArena();
		/** 新用户竞技场排名是缓存中的所有的玩家的值+1 */
		ladderArena.setRank(ArenaCache.getLadderMap().size() + 1);
		ladderArena.setRoleId(role.getId());
		ArenaCache.getLadderMap().put(ladderArena.getRank(), ladderArena);
		ArenaCache.getLadderMap2().put(ladderArena.getRoleId(), ladderArena);
		// RoleArena roleArena = new RoleArena();
		// roleArena.setLv((byte)1);
		// roleArena.setExp(0);
		// roleArena.setRoleId(role.getId());
		// roleArenaDao.insterRoleArena(roleArena, conn);
		// role.setRoleArena(roleArena);
		// ArenaCache.getArenaSetMap().get(1).add(role.getId());
	}

	/**
	 * 初始道具
	 * 
	 * @param role
	 * @param conn
	 */
	private void propInit(Role role, Connection conn) {
		propService.initialProp(role, conn);
	}

	/**
	 * 初始军营
	 * 
	 * @param role
	 * @param conn
	 */
	private void barrackInit(Role role, Connection conn) {
		Barrack barrack = new Barrack();
		barrack.setRoleId(role.getId());
		barrack.setBuildLv(RoleInitialValue.BARRACK_LV);
		barrack.setQueue(RoleInitialValue.BARRACK_QUEUE);
		barrackDao.insertBarrack(barrack, conn);
		role.setBarrack(barrack);
	}

	/**
	 * 添加主角武将
	 * 
	 * @param role
	 * @param conn
	 */
	private void heroInit(Role role, Connection conn) {
		createHero(conn, role, RoleInitialValue.HERO_ID);
		// createHero(conn, role, RoleInitialValue.HERO2_ID);
	}

	/**
	 * 
	 * @param conn
	 * @param role
	 * @param hero
	 * @param heroId
	 * @param heroLv
	 * @param rank
	 * @param status
	 * @author wcy
	 */
	private void createHero(Connection conn, Role role, int heroId) {
		Hero hero = heroService.createHeroNotInsertDao(role, heroId);
		// 插入数据库
		heroDao.insertRoleHero(hero, conn);
		// 缓存增加
		role.getHeroMap().put(heroId, hero);
		// 放入英雄可用列表
		// role.getRecruitHeroMap().put(heroId, hero);
		hero.setChange(true);
	}

	/**
	 * 阵型初始化
	 * 
	 * @param role
	 */
	private void formationInit(Role role) {
		String str = "1001:1,1;2,2500;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!" + "1002:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!"
				+ "1003:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!" + "1004:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!"
				+ "1005:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!";
		// + "1006:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!"
		// + "1007:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!" +
		// "1008:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!";

		role.setFormationStr(str);
	}

	/**
	 * 关卡初始化
	 * 
	 * @param role
	 * @param conn
	 */
	private void chapterInit(Role role, Connection conn) {
		Chapter chapter = new Chapter();
		chapter.setRoleId(role.getId());
		chapterDao.insertChapter(chapter, conn);
		role.setChapter(chapter);
	}

	/**
	 * 玩家封地信息初始化
	 * 
	 * @param role
	 * @param conn
	 */
	private void roleCityInit(Role role, Connection conn) {
		RoleCity roleCity = new RoleCity();
		roleCity.setRoleId(role.getId());
		int cityId = CityInfoConfigCache.getBirthCityByNation(role.getNation()).getCityId();
		roleCity.setCityId(cityId);
		City city = CityCache.getCityByCityId(cityId);
		int key = city.addRole(role.getId());
		roleCity.setMapKey(key);
		roleCity.setMyLordRoleId(0);
		roleCity.setVassal("");
		role.setRoleCity(roleCity);
		
//		Map<Integer, Integer> cityKeymap = CityCache.getCityRoleKeyByCityIdMap(0);
//		int maxI = Utils.getMaxKeyByMap(cityKeymap);
//		for (int i = 1; i <= (maxI + 1); i++) {
//			if (!cityKeymap.containsKey(i)) {
//				cityKeymap.put(i, role.getId());
//				roleCity.setCityId(0);
//				roleCity.setMapKey(i);
//				roleCity.setRoleId(role.getId());
//				role.setRoleCity(roleCity);
//				break;
//			}
//		}
		cityDao.insertRoleCity(roleCity, conn);
	}

	/**
	 * 玩家兵种研究院信息
	 * 
	 * @param role
	 * @param conn
	 */
	private void roleArmyInit(Role role, Connection conn) {
		RoleArmy roleArmy = new RoleArmy();
		roleArmy.setRoleId(role.getId());
		roleArmyDao.insertRoleArmy(roleArmy, conn);
		role.setRoleArmy(roleArmy);
	}

	public void loginRoleModuleDataInit(Role role) {
		
		Build build = buildDao.getBuild(role.getId());
		role.setBuild(build);
		Tasks tasks = tasksDao.getTasks(role.getId());
		role.setTasks(tasks);
		Target target = targetDao.getiTargetById(role.getId());
		role.setTarget(target);
		List<Mail> mail = mailDao.getAllMailById(role.getName());
		role.setRoleMail(mail);
		// 初始化英雄数据
		heroService.heroDataLoginHandle(role);
		// 获取道具数据
		propService.propDataLoginHandle(role);
		// 市场
		Market market = marketDao.getMarketByRoleId(role.getId());
		role.setMarket(market);
		// 酒馆
		Pub pub = pubDao.getPubByRoleId(role.getId());
		role.setPub(pub);
		pubService.resetDeskHero(role);
		pubService.resetFreeChangeDeskHeroTimes(role);
		pubService.resetTalkSpeedUpTimes(role);
		// 兵营
		Barrack barrack = barrackDao.getBarrack(role.getId());
		role.setBarrack(barrack);
		// 关卡
		Chapter chapter = chapterDao.getChapter(role.getId());
		role.setChapter(chapter);
		// 玩家封地
		RoleCity roleCity = cityDao.getRoleCity(role.getId());
		role.setRoleCity(roleCity);
		// 好友
		Friend friend = friendDao.getFriendByRoleId(role.getId());
		role.setFriend(friend);
		// 签到
		Sign sign = signDao.getSignRoleId(role.getId());
		role.setSign(sign);
		// 竞技场数据
		// RoleArena roleArena = roleArenaDao.getRoleArena(role.getId());
		// role.setRoleArena(roleArena);
		// 兵种研究院
		RoleArmy roleArmy = roleArmyDao.getRoleArmyByRoleId(role.getId());
		role.setRoleArmy(roleArmy);
		// 服务器重启时重置每日任务列表
		this.taskService.initDailyTask(role);
		for(Hero hero : role.getHeroMap().values())
		{
			heroService.initHeroFightValue(role, hero);
		}
		this.babelService.initTower(role);
//		// 玩家科技点数 每天重置为10点
//		role.setArmsResearchNum(RoleInitialValue.ARMS_RESEARCH_INIT);
		// 将数据库中的数据放入缓存中
		RoleCache.putRole(role);
	}
	
	/**
	 * 获取最小人数阵营
	 * @return
	 */
	private byte getMinNationNum(){
		int shu=RoleCache.getShuMap().size();
		int wei=RoleCache.getWeiMap().size();
		int wu=RoleCache.getWuMap().size();
		int [] arr={shu,wei,wu};
		int min=arr[0];
		for (int i : arr) {
			min=min>i?i:min;
		}
		byte nation=1;
		if(min==shu){
			nation=1;
		}else if(min==wei){
			nation=2;
		}else{
			nation=3;
		}
		return nation;
	}
}
