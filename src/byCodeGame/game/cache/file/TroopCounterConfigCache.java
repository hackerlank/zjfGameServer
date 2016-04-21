package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.TroopCounterConfig;

public class TroopCounterConfigCache {
private static Map<Integer, TroopCounterConfig> map = new HashMap<Integer, TroopCounterConfig>();
	
	public static void putTroopCounterConfig(TroopCounterConfig troopCounterConfig){
		map.put(troopCounterConfig.getId(), troopCounterConfig);
	}
	
	public static TroopCounterConfig getTroopCounterConfig(int id){
		return map.get(id);
	}
	
	public static Map<Integer, TroopCounterConfig> getAllmap()
	{
		return map;
	}
}
