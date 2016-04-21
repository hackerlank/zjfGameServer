package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.RandomNumberMarket;


public class RandomNumberMarketCache {
	private static Map<Integer, RandomNumberMarket> randomNumberMarketMap = new HashMap<Integer, RandomNumberMarket>();
	
	public static void putRandomNumberMarket(RandomNumberMarket randomNumberMarket){
		randomNumberMarketMap.put(randomNumberMarket.getRoleLv(), randomNumberMarket);
	}
	
	public static RandomNumberMarket getNumberByLv(int lv){
		return randomNumberMarketMap.get(lv);
	}
}
