package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.BuildQueueConfig;

public class BuildQueueConfigCache {

	private static Map<Integer, Integer> configMap = new HashMap<Integer, Integer>();
	
	public static void putBuildQueueConfig(BuildQueueConfig buildQueueConfig){
		configMap.put(buildQueueConfig.getId(), buildQueueConfig.getNeedGold());
	}
	
	/**
	 * 获取所需金钱
	 * @param id
	 * @return
	 */
	public static int getGoldById(byte id){
		return configMap.get((int)id);
	}
	
}
