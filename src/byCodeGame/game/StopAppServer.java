package byCodeGame.game;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.ArenaCache;
import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.cache.local.LegionCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.db.dao.ArenaDao;
import byCodeGame.game.db.dao.LadderArenaDao;
import byCodeGame.game.db.dao.LegionDao;
import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.LadderArena;
import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.util.SpringContext;

public class StopAppServer {

	public static void main(String[] args) {
		try {
			Collection<IoSession> allSession = SessionCache.getAllSession();
			Iterator<IoSession> it = allSession.iterator();
			while(it.hasNext()){
				it.next().close(true);
			}
			
			LegionDao legionDao = (LegionDao)SpringContext.getBean("legionDao");
			ArenaDao arenaDao = (ArenaDao)SpringContext.getBean("arenaDao");
			LadderArenaDao ladderArenaDao = 
					(LadderArenaDao)SpringContext.getBean("ladderArenaDao");
			
			Map<Integer, Legion>  legionMap = LegionCache.getLegionMap();
			for(Map.Entry<Integer, Legion> entry : legionMap.entrySet()){
				Legion tempLegion = entry.getValue();
				legionDao.updateLegion(tempLegion);
			}
			//arenaDao.updateArena(ArenaCache.getArena());
			
			Map<Integer, LadderArena> ladderMap = ArenaCache.getLadderMap();
			for(Map.Entry<Integer, LadderArena> entry : ladderMap.entrySet()){
				LadderArena tempLadderArena = entry.getValue();
				ladderArenaDao.updateLadderArena(tempLadderArena);
			}
			/**
			 * City数据跟新
			 */
//			CityService cityService = (CityService)SpringContext.getBean("cityService");
//			ConcurrentMap<Integer, City> cityMap = CityCache.getCityMap();
//			for(Map.Entry<Integer, City> entry : cityMap.entrySet()){
//				City tempCity = entry.getValue();
//				if(tempCity.isChange()){
//					cityService.updateCityInfo(tempCity);
//					tempCity.setChange(false);
//				}
//			}
		} finally {
			System.exit(0);
		}
	}

}
