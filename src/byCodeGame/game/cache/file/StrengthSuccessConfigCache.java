package byCodeGame.game.cache.file;

import java.util.HashMap;

import byCodeGame.game.entity.file.StrengthSuccessConfig;

public class StrengthSuccessConfigCache {
	private static HashMap<Integer, StrengthSuccessConfig> maps = new HashMap<>();

	public static void putStrengthSuccessConfig(StrengthSuccessConfig strengthSuccessConfig) {
		int lv = strengthSuccessConfig.getLv();
		maps.put(lv, strengthSuccessConfig);
	}

	public static StrengthSuccessConfig getStrengthSuccessConfigByLv(int lv) {
		return maps.get(lv);
	}
}
