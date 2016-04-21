package byCodeGame.game.quartz;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.MineFarm;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.util.SpringContext;

public class MineFarmJob implements Job{
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		CityService cityService = (CityService)SpringContext.getBean("cityService");
		for(Map<Integer, MineFarm> allMap :CityCache.getAllMine().values())
		{
			for(MineFarm mf : allMap.values())
			{
				cityService.sendMineFramAward(mf);
			}
		}

		ConcurrentMap<Integer, Role> roleMap = RoleCache.getRoleMap();
		
		for (Role role : roleMap.values()) {
			cityService.sendMineFarmAward(role);
		}
	}
}
