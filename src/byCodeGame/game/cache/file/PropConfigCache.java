package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.PropConfig;

public class PropConfigCache {
	private static Map<Integer, PropConfig> map = new HashMap<>();

	public static void putPropConfig(PropConfig config) {
		int id = config.getId();
		map.put(id, config);
	}
}
