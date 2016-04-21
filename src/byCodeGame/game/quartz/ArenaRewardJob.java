package byCodeGame.game.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import byCodeGame.game.moudle.arena.service.ArenaService;
import byCodeGame.game.util.SpringContext;

public class ArenaRewardJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ArenaService arenaService = (ArenaService)SpringContext.getBean("arenaService");
		
	}

}
