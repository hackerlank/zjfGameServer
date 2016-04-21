package byCodeGame.game.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzManager {

	public static void openQuartz(){
		SchedulerFactory schedulerfactory=new StdSchedulerFactory();
		Scheduler scheduler=null;
		
		try {
			scheduler=schedulerfactory.getScheduler();
			
			JobDetail roleUpDateJob=JobBuilder.newJob(RoleUpDateJob.class)
					.withIdentity("roleUpDateJob", "jgroup").build();
			Trigger roleZeroTrigger=TriggerBuilder.newTrigger()
					.withIdentity("roleUpDateTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 4 * * ?"))
					.startNow().build(); 
			scheduler.scheduleJob(roleUpDateJob, roleZeroTrigger);
			
			JobDetail arenaRewardJob = JobBuilder.newJob(ArenaRewardJob.class)
					.withIdentity("arenaRewardJob", "jgroup").build();
			Trigger arenaRewardTrigger = TriggerBuilder.newTrigger()
					.withIdentity("arenaRewardTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 9 * * ?"))
					.startNow().build();
			scheduler.scheduleJob(arenaRewardJob,arenaRewardTrigger);
			
			JobDetail zeroClockJob = JobBuilder.newJob(ZeroClockJob.class)
					.withIdentity("zeroClockJob", "jgroup").build();
			Trigger zeroClockTrigger = TriggerBuilder.newTrigger()
					.withIdentity("zeroClockTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
					.startNow().build();
			scheduler.scheduleJob(zeroClockJob,zeroClockTrigger);
			
			JobDetail sixClockJob = JobBuilder.newJob(SixClockJob.class)
					.withIdentity("sixClockJob", "jgroup").build();
			Trigger sixClockTrigger = TriggerBuilder.newTrigger()
					.withIdentity("sixClockTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 6 * * ?"))
					.startNow().build();
			scheduler.scheduleJob(sixClockJob,sixClockTrigger);
			
			JobDetail twelveClockJob = JobBuilder.newJob(TwelveClockJob.class)
					.withIdentity("twelveClockJob", "jgroup").build();
			Trigger twelveClockTrigger = TriggerBuilder.newTrigger()
					.withIdentity("twelveClockTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ?"))
					.startNow().build();
			scheduler.scheduleJob(twelveClockJob,twelveClockTrigger);
			
			JobDetail eighteenClockJob = JobBuilder.newJob(EighteenClockJob.class)
					.withIdentity("eighteenClockJob", "jgroup").build();
			Trigger eighteenClockTrigger = TriggerBuilder.newTrigger()
					.withIdentity("eighteenClockTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 18 * * ?"))
					.startNow().build();
			scheduler.scheduleJob(eighteenClockJob,eighteenClockTrigger);
			
			JobDetail manualUpdateJob = JobBuilder.newJob(ManualUpdateJob.class)
					.withIdentity("manualUpdateJob", "jgroup").build();
			Trigger manualUpdateTrigger=TriggerBuilder.newTrigger()
					.withIdentity("manualUpdateTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 * * * ?"))
					.startNow().build(); 
			scheduler.scheduleJob(manualUpdateJob, manualUpdateTrigger);
			
			JobDetail rankJob=JobBuilder.newJob(RankJob.class)
					.withIdentity("rankJob", "jgroup").build();
			Trigger rankJobTrigger=TriggerBuilder.newTrigger()
					.withIdentity("rankJobTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 4 * * ?"))
					.startNow().build(); 
			scheduler.scheduleJob(rankJob, rankJobTrigger);
			
			JobDetail worldMarchesCloseJob = JobBuilder.newJob(WorldMarchesCloseJob.class)
					.withIdentity("worldMarchesCloseJob","jgroup").build();
			Trigger worldMarchesCloseJobTrigger = TriggerBuilder.newTrigger()
					.withIdentity("worldMarchesCloseJobTrigger","triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 4 * * ?"))
					.startNow().build();
			scheduler.scheduleJob(worldMarchesCloseJob,worldMarchesCloseJobTrigger);			
			
			JobDetail worldMarchesOpenJob = JobBuilder.newJob(WorldMarchesOpenJob.class)
					.withIdentity("worldMarchesOpenJob","jgroup").build();
			Trigger worldMarchesOpenJobTrigger = TriggerBuilder.newTrigger()
					.withIdentity("worldMarchesOpenJobTrigger","triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 10 * * ?"))
					.startNow().build();
			scheduler.scheduleJob(worldMarchesOpenJob,worldMarchesOpenJobTrigger);
			
			JobDetail MineFarmJob = JobBuilder.newJob(MineFarmJob.class)
				.withIdentity("MineFarmJob","jgroup").build();
			Trigger MineFarmJobTrigger = TriggerBuilder.newTrigger()
					.withIdentity("MineFarmJobTrigger","triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 * * * ?"))
					.startNow().build();
			scheduler.scheduleJob(MineFarmJob,MineFarmJobTrigger);
			
			JobDetail worldPrestigeMarketJob = JobBuilder.newJob(WorldPrestigeMarketJob.class)
					.withIdentity("worldPrestigeMarketJob","jgroup").build();
				Trigger worldPrestigeMarketJobTrigger = TriggerBuilder.newTrigger()
						.withIdentity("worldPrestigeMarketJobTrigger","triggerGroup")
						.withSchedule(CronScheduleBuilder.cronSchedule("0 0 4 ? * 1"))
						.startNow().build();
				scheduler.scheduleJob(worldPrestigeMarketJob,worldPrestigeMarketJobTrigger);
			
//			JobDetail armyTokenGet = JobBuilder.newJob(ArmyTokenGetJob.class)
//					.withIdentity("armyTokenGet", "jgroup").build();
//			Trigger earmyTokenGetTrigger = TriggerBuilder.newTrigger()
//					.withIdentity("earmyTokenGetTrigger", "triggerGroup")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 * * * ?"))
//					.startNow().build();
//			scheduler.scheduleJob(armyTokenGet,earmyTokenGetTrigger);
//			
//			JobDetail marketFirstJob=JobBuilder.newJob(MarketFirstRefreshJob.class)
//					.withIdentity("marketFirstJob", "jgroup").build();
//			Trigger marketFirstTrigger=TriggerBuilder.newTrigger()
//					.withIdentity("marketFirstTrigger", "triggerGroup")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ?"))
//					.startNow().build(); 
//			scheduler.scheduleJob(marketFirstJob, marketFirstTrigger);
//			
//			JobDetail marketSecondJob=JobBuilder.newJob(MarketFirstRefreshJob.class).withIdentity("marketSecondJob", "jgroup").build();
//			Trigger marketSecondTrigger=TriggerBuilder.newTrigger().withIdentity("marketSecondTrigger", "triggerGroup")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 21 * * ?"))
//					.startNow().build(); 
//			scheduler.scheduleJob(marketSecondJob, marketSecondTrigger);
			
			scheduler.start();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
