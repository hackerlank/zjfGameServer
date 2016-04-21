package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ProbabilityMarket;

public class ProbabilityMarketCache {
	private static Map<Integer, ProbabilityMarket> probabilityMarketMap = new HashMap<Integer, ProbabilityMarket>();
	
	public static void putProbabilityMarket(ProbabilityMarket probabilityMarket){
		probabilityMarketMap.put(probabilityMarket.getFigure(), probabilityMarket);
	}
	
	public static ProbabilityMarket getNumberByLv(int figure){
		return probabilityMarketMap.get(figure);
	}
}
