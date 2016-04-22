package byCodeGame.game;

import java.net.InetSocketAddress;

import byCodeGame.game.navigation.Navigation;
import byCodeGame.game.quartz.QuartzManager;
import byCodeGame.game.remote.ClientHandler;
import byCodeGame.game.remote.WanServer;
import byCodeGame.game.safebox.SafeBoxServer;
import byCodeGame.game.scheduler.RoleUpdateScheduled;
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
		// 初始化配置
		ServerConfig.loadConfig();

		// 初始化导配置
		Navigation.init();		

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
		// 启动服务器socket
		InetSocketAddress inetSocketAddress = new InetSocketAddress(ServerConfig.getWanServerPort());
		WanServer.startIOServer(new ClientHandler(), inetSocketAddress);
	}

	/**
	 * java服务启动初始化数据
	 */
	private void ServiceStartInit() {
		
	}

	/**
	 * 定时任务初始化数据
	 */
	private void ScheduledAndQuartzStart() {
		RoleUpdateScheduled.startScheduled();
		QuartzManager.openQuartz();
	}
}
