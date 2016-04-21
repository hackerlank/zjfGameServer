package byCodeGame.game.quartz;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.util.Utils;

public class ManualUpdateJob implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ConcurrentMap<Integer, Role> roleMap = RoleCache.getRoleMap();
		for(Map.Entry<Integer, Role> entry : roleMap.entrySet()){
			for(Hero hero : entry.getValue().getHeroMap().values())
			{
				hero.addManual(1);
				hero.setLastGetMa(Utils.getNowTime());
				hero.setChange(true);
			}
		}
	}
}
