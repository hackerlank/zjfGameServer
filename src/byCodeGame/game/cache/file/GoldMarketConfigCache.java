package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.GoldMarketConfig;

public class GoldMarketConfigCache {
private static Map<Integer, GoldMarketConfig> map = new HashMap<Integer, GoldMarketConfig>();
	
	public static void putGoldMarketConfig(GoldMarketConfig goldMarketConfig)
	{
		map.put(goldMarketConfig.getId(), goldMarketConfig);
	}
	
	public static GoldMarketConfig getGoldMarketConfig(int id)
	{
		return map.get(id);
	}
	
	public static Map<Integer, GoldMarketConfig> getMapInfo()
	{
		return map;
	}
}
