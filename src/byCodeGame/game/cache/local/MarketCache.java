package byCodeGame.game.cache.local;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.po.MarketGoods;

/**
 * 
 * @author wcy 2016年5月6日
 *
 */
public class MarketCache {
	private static Map<Integer, MarketGoods> marketGoodsMap = new HashMap<>();

	public static Map<Integer, MarketGoods> getAllGoods() {
		return marketGoodsMap;
	}

	public static MarketGoods getMarketGoodsById(int configId) {
		return marketGoodsMap.get(configId);
	}
}
