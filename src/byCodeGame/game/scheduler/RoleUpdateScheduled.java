package byCodeGame.game.scheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 每30分钟向数据库更新用户数据与家build数据
 * 
 * @author 王君辉
 *
 */
public class RoleUpdateScheduled {

	private static ScheduledExecutorService roleUpdate = new ScheduledThreadPoolExecutor(1);

	/**
	 * 启动调度任务
	 */
	public static void startScheduled() {
		System.out.println("每半小时更新用户");
		roleUpdating();
		serverUpdating();
	}

	/**
	 * 每30分钟更新用户数据
	 */
	private static void roleUpdating() {
		roleUpdate.scheduleAtFixedRate(new Runnable() {
			public void run() {
				
			}
		}, 1, 30, TimeUnit.MINUTES);
	}

	/**
	 * 每30分钟更新用户数据
	 */
	private static void serverUpdating() {
		roleUpdate.scheduleAtFixedRate(new Runnable() {
			public void run() {
				
			}
		}, 1, 30, TimeUnit.MINUTES);
	}
}