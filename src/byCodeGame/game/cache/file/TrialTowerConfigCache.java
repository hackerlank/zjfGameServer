package byCodeGame.game.cache.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byCodeGame.game.entity.file.TrialTowerConfig;

public class TrialTowerConfigCache {
	private static Map<Integer, HashMap<Integer, TrialTowerConfig>> map = new HashMap<Integer, HashMap<Integer, TrialTowerConfig>>();
	private static List<TrialTowerConfig> allConfigList = new ArrayList<>();

	public static void putTrialTowerConfig(TrialTowerConfig trialTowerConfig) {
		Map<Integer, TrialTowerConfig> temp = map.get(trialTowerConfig.getUseId());
		if (temp == null) {
			map.put(trialTowerConfig.getUseId(), new HashMap<Integer, TrialTowerConfig>());
		}
		map.get(trialTowerConfig.getUseId()).put(trialTowerConfig.getProcess(), trialTowerConfig);
		allConfigList.add(trialTowerConfig);
	}

	public static TrialTowerConfig getTrialTowerConfigById(int id, int process) {
		return map.get(id).get(process);
	}

	public static Map<Integer, TrialTowerConfig> getMap(int userId) {
		return map.get(userId);
	}

	public static Map<Integer, HashMap<Integer, TrialTowerConfig>> getAllMap() {
		return map;
	}
	
	public static List<TrialTowerConfig> getAllTrialTowerConfigList(){
		return allConfigList;
	}
}
