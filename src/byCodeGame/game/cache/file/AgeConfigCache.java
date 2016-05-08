package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.AgeConfig;

public class AgeConfigCache {
	private static Map<Integer, AgeConfig> map = new HashMap<>();

	public static void putAgeConfig(AgeConfig ageConfig) {
		int ageId = ageConfig.getAgeId();
		map.put(ageId, ageConfig);
	}

	public static AgeConfig getAgeConfigById(int ageId) {
		AgeConfig config = map.get(ageId);
		if (config == null) {
			System.out.println(ageId);
		}

		return config;
	}
}
