package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.AgeConfig;

public class AgeConfigCache {
	private static Map<Integer, AgeConfig> map = new HashMap<>();

	public static void putAgeConfig(AgeConfig config) {
		int ageId = config.getAgeId();
		map.put(ageId, config);
	}

	public static AgeConfig getAgeConfigById(int ageId) {
		return map.get(ageId);
	}

}
