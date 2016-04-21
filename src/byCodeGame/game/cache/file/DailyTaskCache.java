package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.DailyTask;

public class DailyTaskCache {
	private static Map<Integer, DailyTask> dailyTaskMap = new HashMap<Integer, DailyTask>();
	
	public static void putDailyTask(DailyTask dailyTask)
	{
		dailyTaskMap.put(dailyTask.getNumber(), dailyTask);
	}
	
	public static DailyTask getDailyTaskByNumber(int number)
	{
		return dailyTaskMap.get(number);
	}
	
	public static Map<Integer, DailyTask> getDailyTaskMap()
	{
		return dailyTaskMap;
	}
}
