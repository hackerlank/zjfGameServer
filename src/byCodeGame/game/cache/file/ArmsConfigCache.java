package byCodeGame.game.cache.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ArmsConfig;

public class ArmsConfigCache {

	private static Map<Integer, ArmsConfig> maps = new HashMap<Integer, ArmsConfig>();
	// <兵种类型号,<兵种品阶号,兵种信息>>
	private static HashMap<Integer, HashMap<Integer, ArrayList<ArmsConfig>>> armsTypeQualityMapping = new HashMap<Integer, HashMap<Integer, ArrayList<ArmsConfig>>>();

	public static void putArmsConfig(ArmsConfig armsConfig) {
		maps.put(armsConfig.getId(), armsConfig);
		initMapping(armsConfig);
	}

	private static void initMapping(ArmsConfig armsConfig) {
		int armsType = armsConfig.getFunctionType();
		int armsQuality = armsConfig.getArmyTypeLv();
		HashMap<Integer, ArrayList<ArmsConfig>> hashmap = armsTypeQualityMapping.get(armsType);
		if (hashmap == null) {
			hashmap = new HashMap<Integer, ArrayList<ArmsConfig>>();
			ArrayList<ArmsConfig> array = new ArrayList<ArmsConfig>();
			hashmap.put(armsQuality, array);
			armsTypeQualityMapping.put(armsType, hashmap);
		}
		ArrayList<ArmsConfig> array = hashmap.get(armsQuality);
		if (array == null) {
			array = new ArrayList<ArmsConfig>();
			hashmap.put(armsQuality, array);
		}
		hashmap.get(armsQuality).add(armsConfig);

	}

	public static ArmsConfig getArmsConfigById(int id) {
		return maps.get(id);
	}

	public static Map<Integer, ArmsConfig> getArmsConfigMap() {
		return maps;
	}

	public static HashMap<Integer, HashMap<Integer, ArrayList<ArmsConfig>>> getArmsTypeQualityMapping() {
		return armsTypeQualityMapping;
	}

}
