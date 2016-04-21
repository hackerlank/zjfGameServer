package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.BuffConfig;

public class BuffConfigCache {

	private static Map<Integer, BuffConfig> maps = new HashMap<Integer, BuffConfig>();
	
	
	
	public static void putBuffConfig(BuffConfig buffConfig){
		maps.put(buffConfig.getId(), buffConfig);
	}
	
	public static BuffConfig getBuffConfig(int id){
		return maps.get(id);
	}
}
