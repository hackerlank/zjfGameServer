package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.TavernStrategyConfig;

/**
 * 
 * @author wcy
 *
 */
public class TavernStrategyConfigCache {
	private static Map<Integer, TavernStrategyConfig> maps = new HashMap<Integer, TavernStrategyConfig>();

	public static void putTavernStrategyConfig(TavernStrategyConfig tavernStrategyConfig) {
		maps.put(tavernStrategyConfig.getDrawType(), tavernStrategyConfig);
	}

	public static TavernStrategyConfig getTavenStrategyConfig(int drawType) {
		return maps.get(drawType);
	}
}
