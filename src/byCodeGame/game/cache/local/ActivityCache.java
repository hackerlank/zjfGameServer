package byCodeGame.game.cache.local;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.bo.Activity;

/**
 * 
 * @author wcy 2016年4月19日
 *
 */
public class ActivityCache {
	// 所有活动<配置表id,活动>
	private static Map<Integer, Activity> allActivityMap = new HashMap<>();
	
	public static void addActivity(Activity activity) {
		int activityId = activity.getActivityId();
		allActivityMap.put(activityId, activity);		
	}

	public static Map<Integer,Activity> getAllActivityMap(){
		return allActivityMap;
	}

}
