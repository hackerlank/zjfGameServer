package byCodeGame.game.scheduler;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import byCodeGame.game.cache.local.ArenaCache;
import byCodeGame.game.cache.local.LegionCache;
import byCodeGame.game.db.dao.ArenaDao;
import byCodeGame.game.db.dao.LadderArenaDao;
import byCodeGame.game.db.dao.LegionDao;
import byCodeGame.game.entity.bo.LadderArena;
import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.util.SpringContext;

public class OrderUpdateScheduled {
	
	private static ScheduledExecutorService legionUpdate = new ScheduledThreadPoolExecutor(
			1);
	
	public static void startScheduled() {
		legionUpdating();
	}
	
	/**
	 * 每75分钟更军团数据与排行榜数据
	 */
	private static void legionUpdating() {
		legionUpdate.scheduleAtFixedRate(new Runnable() {
			public void run() {
				LegionDao legionDao = (LegionDao)SpringContext.getBean("legionDao");
				ArenaDao arenaDao = (ArenaDao)SpringContext.getBean("arenaDao");
				LadderArenaDao ladderArenaDao = 
						(LadderArenaDao)SpringContext.getBean("ladderArenaDao");
				
				Map<Integer, Legion> allLegion = LegionCache.getLegionMap();
				for(Map.Entry<Integer, Legion> entry : allLegion.entrySet()){
					Legion tempLegion = entry.getValue();
					if(tempLegion.isChange() == true){
						legionDao.updateLegion(tempLegion);
						tempLegion.setChange(false);
					}
				}
				//arenaDao.updateArena(ArenaCache.getArena());
				Map<Integer, LadderArena> ladderMap = ArenaCache.getLadderMap();
				for(Map.Entry<Integer, LadderArena> entry : ladderMap.entrySet()){
					LadderArena tempLadderArena = entry.getValue();
					if(tempLadderArena.isChange()){
						ladderArenaDao.updateLadderArena(tempLadderArena);
						tempLadderArena.setChange(false);
					}
				}
			}
		}, 1, 75, TimeUnit.MINUTES);
	}
}
