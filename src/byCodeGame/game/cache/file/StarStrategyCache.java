package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.StarStrategy;

public class StarStrategyCache {
	private static Map<Integer, StarStrategy> map = new HashMap<Integer, StarStrategy>();
	
	
	public static void putStarStrategy(StarStrategy starStrategy)
	{
		map.put(starStrategy.getId(), starStrategy);
	}
	
	public static StarStrategy getStarStrategyById(int id)
	{
		return map.get(id);
	}
}
