package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.MarketGoodsConfig;

/**
 * 
 * @author AIM
 *
 */
public class MarketGoodsConfigCache {
	private static Map<Integer, MarketGoodsConfig> map = new HashMap<>();

	public static void putMarketGoodsConfig(MarketGoodsConfig config) {
		int id = config.getId();
		map.put(id, config);
	}
}
