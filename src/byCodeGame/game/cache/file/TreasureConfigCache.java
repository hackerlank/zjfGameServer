package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.TreasureConfig;

/**
 * 
 * @author wcy
 *
 */
public class TreasureConfigCache {
	private static Map<Integer, TreasureConfig> maps = new HashMap<>();
	private static int weight = 0;

	public static void putTreasureConfig(TreasureConfig treasureConfig) {
		int id = treasureConfig.getId();
		int weight = treasureConfig.getWeight();
		maps.put(id, treasureConfig);

		TreasureConfigCache.weight += weight;
	}

	public static int getTreasureWeight() {
		return weight;
	}
	
	public static TreasureConfig getTreasureConfigById(int id){
		return maps.get(id);
	}
	
	public static Map<Integer,TreasureConfig> getMaps(){
		return maps;
	}
}
