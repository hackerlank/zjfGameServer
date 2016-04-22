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

			scheduler.start();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
