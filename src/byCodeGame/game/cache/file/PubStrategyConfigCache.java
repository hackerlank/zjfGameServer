package byCodeGame.game.cache.file;

import java.util.ArrayList;
import java.util.HashMap;

import byCodeGame.game.entity.file.PubStrategyConfig;

public class PubStrategyConfigCache {
	private static HashMap<Integer, ArrayList<PubStrategyConfig>> maps = new HashMap<>();
	private static HashMap<Integer, Integer> weightMap = new HashMap<>();

	public static void putPubStrategyConfig(PubStrategyConfig pubStrategyConfig) {
		int drawType = pubStrategyConfig.getDrawType();
		int weight = pubStrategyConfig.getWeight();
		ArrayList<PubStrategyConfig> array = maps.get(drawType);
		Integer totalWeight = weightMap.get(drawType);
		if (array == null) {
			array = new ArrayList<PubStrategyConfig>();
			maps.put(drawType, array);
		}
		array.add(pubStrategyConfig);

		if (totalWeight == null) {
			weightMap.put(drawType, weight);
		} else {
			totalWeight += weight;
			weightMap.put(drawType, totalWeight);
		}

	}

	public static ArrayList<PubStrategyConfig> getPubStrategyConfigByDrawType(int drawType) {
		return maps.get(drawType);
	}

	public static Integer getTotalWeightByDrawType(int drawType) {
		return weightMap.get(drawType);
	}

	public static Integer getCdByDrawType(int drawType) {
		return maps.get(drawType).get(0).getCd();
	}

	public static Integer getCostByDrawType(int drawType) {
		return maps.get(drawType).get(0).getCost();
	}

	public static Integer getNodeByDrawType(int drawType) {
		return maps.get(drawType).get(0).getNode();
	}
}
