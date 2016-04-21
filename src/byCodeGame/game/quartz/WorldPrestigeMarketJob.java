package byCodeGame.game.quartz;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.ServerCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.market.service.MarketService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.server.service.ServerService;
import byCodeGame.game.util.SpringContext;
import byCodeGame.game.util.Utils;

public class WorldPrestigeMarketJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		MarketService marketService = (MarketService)SpringContext.getBean("marketService");
		ServerService serverService	= (ServerService)SpringContext.getBean("serverService");
		
		int nowTime = Utils.getNowTime();
		serverService.refreshWorldMarket(nowTime);
		
		ConcurrentMap<Integer, Role> roleMap = RoleCache.getRoleMap();
		for(Map.Entry<Integer, Role> entry : roleMap.entrySet()){
			Role tempRole = entry.getValue();
			marketService.refreshPrestigeWorldMarketItems(tempRole,nowTime);
		}
	}

}
