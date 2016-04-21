package byCodeGame.game.quartz;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.login.RoleInitialValue;
import byCodeGame.game.moudle.market.service.MarketService;
import byCodeGame.game.util.SpringContext;

/**
 * 已经失效    2016.1.18
 * @author win7n
 *
 */
public class MarketFirstRefreshJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		MarketService marketService = (MarketService)SpringContext.getBean("marketService");
		ConcurrentMap<Integer, Role> roleMap = RoleCache.getRoleMap();
		for(Map.Entry<Integer, Role> entry : roleMap.entrySet()){
			Role tempRole = entry.getValue();
			marketService.changMarket(tempRole);
			tempRole.setChange(true);
		}
	}

}
