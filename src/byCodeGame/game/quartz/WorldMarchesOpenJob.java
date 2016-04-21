package byCodeGame.game.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import byCodeGame.game.cache.local.WorldMarchCache;

public class WorldMarchesOpenJob implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		WorldMarchCache.setCanMarch(true);
		
	}
}
