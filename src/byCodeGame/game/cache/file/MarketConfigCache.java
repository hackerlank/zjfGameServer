package byCodeGame.game.cache.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import byCodeGame.game.entity.file.MarketConfig;

public class MarketConfigCache {
	private static HashMap<Integer, List<MarketConfig>> map = new HashMap<>();
	

	public static void putMarketConfig(MarketConfig marketConfig) {
		int lv = marketConfig.getLv();
		List<MarketConfig> list = map.get(lv);
		if (list == null) {
			list = new ArrayList<>();
			map.put(lv, list);
		}
		list.add(marketConfig);
	}

	public static List<MarketConfig> getMarketConfigByLv(int lv) {
		return map.get(lv);
	}
}
