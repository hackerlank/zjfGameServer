package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.TowerConfig;

public class TowerConfigCache {
	private static Map<Integer, TowerConfig> map = new HashMap<Integer, TowerConfig>();

	public static void putTowerConfig(TowerConfig towerConfig) {
		map.put(towerConfig.getUserId(), towerConfig);
	}

	public static TowerConfig getTowerConfigById(int id) {
		return map.get(id);
	}
}
