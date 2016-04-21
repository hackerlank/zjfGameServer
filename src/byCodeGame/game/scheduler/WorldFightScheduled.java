package byCodeGame.game.scheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.entity.bo.City;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.util.SpringContext;

public class WorldFightScheduled {
	private static ScheduledExecutorService worldFightScheduled = new ScheduledThreadPoolExecutor(
			1);
	public static void startScheduled() {
		upWorldFight();
	}
	
	private static void upWorldFight() {
		worldFightScheduled.scheduleAtFixedRate(new Runnable() {
			public void run() {
				CityService cityService = (CityService)SpringContext.getBean("cityService");
				for(City city : CityCache.getAllCityMap().values())
				{
					try {
						cityService.handlerFight(city);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}, 0,1, TimeUnit.SECONDS);
	}
}
