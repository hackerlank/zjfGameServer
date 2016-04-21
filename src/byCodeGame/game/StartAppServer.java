package byCodeGame.game;

import java.net.InetSocketAddress;

import byCodeGame.game.db.dao.RoleDao;
import byCodeGame.game.moudle.activity.service.ActivityService;
import byCodeGame.game.moudle.babel.service.BabelService;
import byCodeGame.game.moudle.chapter.service.ChapterService;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.moudle.fight.service.FightService;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.legion.service.LegionService;
import byCodeGame.game.moudle.market.service.MarketService;
import byCodeGame.game.moudle.pub.service.PubService;
import byCodeGame.game.moudle.raid.service.RaidService;
import byCodeGame.game.moudle.rank.service.RankService;
import byCodeGame.game.moudle.server.service.ServerService;
import byCodeGame.game.moudle.vip.service.VipService;
import byCodeGame.game.navigation.Navigation;
import byCodeGame.game.quartz.QuartzManager;
import byCodeGame.game.remote.ClientHandler;
import byCodeGame.game.remote.WanServer;
import byCodeGame.game.safebox.SafeBoxServer;
import byCodeGame.game.scheduler.AuctionScheduled;
import byCodeGame.game.scheduler.OrderUpdateScheduled;
import byCodeGame.game.scheduler.RoleUpdateScheduled;
import byCodeGame.game.scheduler.WorldFightScheduled;
import byCodeGame.game.scheduler.WorldMarchScheduled;
import byCodeGame.game.util.ServerConfig;
import byCodeGame.game.util.SpringContext;
import byCodeGame.game.util.SysManager;
import byCodeGame.game.util.Utils;

public class StartAppServer {

	private static final String START_CONFIG_FILE = "ApplicationContext.xml";

	public static void main(String[] args) {

		// 读取excel中的配置数据
//		 ExcelReader.readExcel2Cache(null);

		// 启动安全沙箱
		SafeBoxServer.start();

		// 加载spring配置
		SpringContext.inizSpringCtx(START_CONFIG_FILE);
		// 初始化配�?
		ServerConfig.loadConfig();

		// 初始化导�?
		Navigation.init();

		// 启动服务器socket
		InetSocketAddress inetSocketAddress = new InetSocketAddress(ServerConfig.getWanServerPort());
		WanServer.startIOServer(new ClientHandler(), inetSocketAddress);

		StartAppServer startAppServer = new StartAppServer();

		// java服务启动初始化数据
		startAppServer.ServiceStartInit();

		// 定时任务初始化数据
		startAppServer.ScheduledAndQuartzStart();

		System.gc();
		// GM指令
		String GM_COMMAND = Utils.randowStr(20);
		SysManager.setGM_PWD(GM_COMMAND);
		SysManager.setSERVICE_FLAG(true);
		System.out.println("GM_COMMAND:" + GM_COMMAND);
	}

	/**
	 * java服务启动初始化数据
	 */
	private void ServiceStartInit() {
		RoleDao roleDao = (RoleDao) SpringContext.getBean("roleDao");
		LegionService legionService = (LegionService) SpringContext.getBean("legionService");
		RaidService raidService = (RaidService) SpringContext.getBean("raidService");
		CityService cityService = (CityService) SpringContext.getBean("cityService");
		FightService fightService = (FightService) SpringContext.getBean("fightService");
		VipService vipService = (VipService) SpringContext.getBean("vipService");
		PubService pubService = (PubService) SpringContext.getBean("pubService");
		HeroService heroService = (HeroService)SpringContext.getBean("heroService");
		MarketService marketService = (MarketService)SpringContext.getBean("marketService");
		ServerService serverService = (ServerService)SpringContext.getBean("serverService");
		RankService rankService = (RankService)SpringContext.getBean("rankService");
		ChapterService chapterService = (ChapterService)SpringContext.getBean("chapterService");
		BabelService babelService = (BabelService) SpringContext.getBean("babelService");
		ActivityService activityService = (ActivityService) SpringContext.getBean("activityService");
		//数据库初始信息初始化
		serverService.serverInit();
		// 读取数据库 将姓名与账号写入缓存
		roleDao.serverStartInit();
		// 读取数据库 将所有军团姓名加入缓存
		legionService.getAllLegionName();
		// 将所有军团加入缓存
		legionService.getAllLegion();
		// arenaService.ladderInit();
		// arenaService.arenaInit();
		raidService.initBattleLobby();
		// raidService.initGenerator();
		// 将封地数据加入缓存第一次运行时创建city数据,
		cityService.initWorldCity();
		cityService.CityInit();
		cityService.initMineFram();
		fightService.initTroopCounter();
		vipService.initAward();	
		pubService.initPubMap();
		heroService.initHeroService();
		marketService.initAuction();
		rankService.initRankMap();
		chapterService.initChapterConfig();
		babelService.initTrialConfig();
		activityService.initActivity();
	}

	/**
	 * 定时任务初始化数据
	 */
	private void ScheduledAndQuartzStart() {
		RoleUpdateScheduled.startScheduled();
		OrderUpdateScheduled.startScheduled();
		// 每半小时跟新City数据
//		MineFarmUpdateScheduled.startScheduled();
		// ArmyTokenUpdateScheduled.startScheduled();
//		ManualUpdateScheduled.startScheduled();
		AuctionScheduled.startScheduled();
		WorldMarchScheduled.startScheduled();
		WorldFightScheduled.startScheduled();
		QuartzManager.openQuartz();
	}
}
