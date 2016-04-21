package byCodeGame.game.scheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.util.SpringContext;

/**
 * 2015.12.24 停用
 * @author xjd
 *
 */
public class MineFarmUpdateScheduled {
	private static ScheduledExecutorService armyTokenUpdate = new ScheduledThreadPoolExecutor(
			1);
	
	public static void startScheduled() {
		upMineFarmDB();
//		upMineFarm();
	}
	
	private static void upMineFarmDB() {
		armyTokenUpdate.scheduleAtFixedRate(new Runnable() {
			public void run() {
				CityService cityService = (CityService)SpringContext.getBean("cityService");
				cityService.upMineFarmDB();
			}
		}, 0, 30, TimeUnit.MINUTES);
	}
	private static void upMineFarm() {
		armyTokenUpdate.scheduleAtFixedRate(new Runnable() {
			public void run() {
				CityService cityService = (CityService)SpringContext.getBean("cityService");
				cityService.upMineFarm();
			}
		}, 0, 3, TimeUnit.SECONDS);
	}
}
