package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ActivityConfig;
import byCodeGame.game.entity.po.LegionConquerCityActivityData;

/**
 * 
 * @author wcy 2016年4月18日
 *
 */
public class ActivityConfigCache {
	private static Map<Integer, ActivityConfig> allActivityMap = new HashMap<>();

	public static void putActivityConfig(ActivityConfig config) {
		int id = config.getActivityId();
		allActivityMap.put(id, config);
	}

	public static Map<Integer, ActivityConfig> getAllActivityMap() {
		return allActivityMap;
	}
	
	public static ActivityConfig getActivityConfigByActivityId(int activityId){
		return allActivityMap.get(activityId);
	}
	
	public static int getActivityTypeByActivityId(int activityId){
		ActivityConfig config = allActivityMap.get(activityId);
		int type = config.getActivityType();
		return type;		
	}
}
