package byCodeGame.game.scheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import byCodeGame.game.cache.local.WorldMarchCache;
import byCodeGame.game.entity.po.WorldMarch;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.util.SpringContext;
import byCodeGame.game.util.Utils;

public class WorldMarchScheduled {
	private static ScheduledExecutorService worldMarchScheduled = new ScheduledThreadPoolExecutor(
			1);
	
	public static void startScheduled() {
		upWorldMarchAll();
		upWorldMarchQuick();
	}
	
	private static void upWorldMarchAll() {
		worldMarchScheduled.scheduleAtFixedRate(new Runnable() {
			public void run() {
				WorldMarchCache.getQuickCheck().clear();
				CityService cityService = (CityService)SpringContext.getBean("cityService");
				if(!WorldMarchCache.isCanMarch())
				{
					cityService.closeWorldMarch();
					return;
				}
				for(WorldMarch march:WorldMarchCache.getAllMarch().values())
				{
					if(march.getMarchTime() - Utils.getNowTime() <= 60)
					{
						WorldMarchCache.getQuickCheck().add(march.getId());
					}
				}
			}
		}, 0,1, TimeUnit.MINUTES);
	}
	
	private static void upWorldMarchQuick() {
		worldMarchScheduled.scheduleAtFixedRate(new Runnable() {
			public void run() {
			CityService cityService = (CityService)SpringContext.getBean("cityService");
				for(String id:WorldMarchCache.getQuickCheck())
				{					
					if(!WorldMarchCache.isCanMarch())
					{
						return;
					}
					WorldMarch march = WorldMarchCache.getWorldMarch(id);
					if(march == null) continue;
					if(Utils.getNowTime() >= march.getMarchStartTime() + march.getMarchTime())
					{
						try {
							cityService.handlerArrive(march);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					
					}
				}
				
				
				//新手引导去小坑村的引导
				for (WorldMarch march : WorldMarchCache.getGuideAllMarch().values()) {
					if (march == null)
						continue;
					if (Utils.getNowTime() >= march.getMarchStartTime() + march.getMarchTime()) {
						try {
							cityService.handlerGuideArrive(march);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				
			}
		}, 1,2, TimeUnit.SECONDS);
	}
}
