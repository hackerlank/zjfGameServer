package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.RankConfig;

public class RankConfigCache {
	private static Map<Integer, RankConfig> maps = new HashMap<Integer, RankConfig>();
	
	public static void putRankConfig(RankConfig rankConfig){
		maps.put(rankConfig.getId(), rankConfig);
	}
	
	public static RankConfig getRankConfigById(int id){
		return maps.get(id);
	}
}
