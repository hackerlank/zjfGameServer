package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.TargetConfig;

public class TargetConfigCache {
	private static HashMap<Integer, TargetConfig> maps = new HashMap<Integer, TargetConfig>();
	// <国籍，<stage,po>>
	public static Map<Integer, Map<Integer, TargetConfig>> maps2 = new HashMap<>();

	public static void putTargetConfig(TargetConfig targetConfig) {
		maps.put(targetConfig.getId(), targetConfig);

		int nation = targetConfig.getNation();
		int stage = targetConfig.getStage();
		Map<Integer, TargetConfig> targetMap = maps2.get(nation);
		if (targetMap == null) {
			targetMap = new HashMap<>();
			maps2.put(nation, targetMap);
		}
		targetMap.put(stage, targetConfig);
	}

	public static TargetConfig getTargetConfigById(int id) {
		return maps.get(id);
	}

	public static TargetConfig getTargetConfigByNationAndStage(int stage, byte nation) {
		int nationInt = (int)nation;
		return maps2.get(nationInt).get(stage);
	}

}
