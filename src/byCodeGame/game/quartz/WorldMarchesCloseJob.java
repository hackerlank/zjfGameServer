package byCodeGame.game.quartz;

import java.util.concurrent.ConcurrentHashMap;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.cache.local.WorldMarchCache;
import byCodeGame.game.db.dao.CityDao;
import byCodeGame.game.entity.bo.City;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.util.SpringContext;

public class WorldMarchesCloseJob implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		WorldMarchCache.setCanMarch(false);
//		CityService cityService = (CityService)SpringContext.getBean("cityService");
//		cityService.closeWorldMarch();
		
		CityDao cityDao = (CityDao) SpringContext.getBean("CityDao");
		ConcurrentHashMap<Integer, City> allCity = CityCache.getAllCityMap();
		for (City city : allCity.values()) {
			cityDao.updateCity(city);
		}
	}
}
