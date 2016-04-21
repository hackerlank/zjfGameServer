package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.LevyConfig;

/**
 * 
 * @author wcy 2016年1月22日
 *
 */
public class LevyConfigCache {
	private static Map<Integer, LevyConfig> map = new HashMap<>();

	public static void putLevyConfig(LevyConfig levyConfig) {
		int times = levyConfig.getTimes();
		map.put(times, levyConfig);
	}

	public static LevyConfig getLevyConfigByTimes(int times) {
		return map.get(times);
	}
}
