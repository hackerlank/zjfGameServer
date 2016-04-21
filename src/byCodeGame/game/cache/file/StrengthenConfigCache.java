package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.StrengthenConfig;

public class StrengthenConfigCache {
	private static Map<Integer, StrengthenConfig> maps = new HashMap<Integer, StrengthenConfig>();
	
	
	public void putStrengthenConfig(StrengthenConfig strengthenConfig){
		maps.put(strengthenConfig.getLv(), strengthenConfig);
	}
	
	public static StrengthenConfig getStrengthenConfigByLv(int lv){
		return maps.get(lv);
	}
}
