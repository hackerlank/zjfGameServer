package byCodeGame.game.quartz;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.heart.service.HeartService;
import byCodeGame.game.util.SpringContext;

public class ArmyTokenGetJob implements Job{
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		HeartService heartService = (HeartService)SpringContext.getBean("heartService");
		ConcurrentMap<Integer, Role> roleMap = RoleCache.getRoleMap();
		for(Map.Entry<Integer, Role> entry : roleMap.entrySet()){
			Role tempRole = entry.getValue();
			heartService.addArmyToken(tempRole);
			tempRole.setChange(true);
		}
	}
}
