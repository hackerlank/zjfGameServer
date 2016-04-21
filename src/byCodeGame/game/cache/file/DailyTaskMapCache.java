package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.DailyTaskMap;

public class DailyTaskMapCache {
	private static Map<Integer, DailyTaskMap> map = new HashMap<Integer, DailyTaskMap>();
	
	public static void putDailyTaskMap(DailyTaskMap dailyTaskMap){
		getMap().put(dailyTaskMap.getNumber(), dailyTaskMap);
	}

	public static Map<Integer, DailyTaskMap> getMap() {
		return map;
	}
}
