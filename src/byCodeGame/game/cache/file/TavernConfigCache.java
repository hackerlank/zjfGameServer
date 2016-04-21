package byCodeGame.game.cache.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.TavernConfig;

public class TavernConfigCache {
	private static Map<Integer, TavernConfig> maps = new HashMap<Integer, TavernConfig>();
	/* <setId,<country,count>> */
	private static Map<Integer, HashMap<Integer, Integer>> probWeightCountMapping = new HashMap<Integer, HashMap<Integer, Integer>>();
	/* <setId,<countryType,<TavernConfig>>> */
	private static Map<Integer, HashMap<Integer, ArrayList<TavernConfig>>> tavernConfigMapping = new HashMap<Integer, HashMap<Integer, ArrayList<TavernConfig>>>();

	public static void putTavernConfig(TavernConfig tavernConfig) {
		// 如果id是0则不加入表
		if (tavernConfig.getId() == 0) {
			return;
		}
		maps.put(tavernConfig.getRowID(), tavernConfig);
		initMappings(tavernConfig);
	}

	public static TavernConfig getTavernConfig(int rowID) {
		return maps.get(rowID);
	}

	public static int getProbWeightCount(int setId, int country) {		
		HashMap<Integer, Integer> temp = probWeightCountMapping.get(setId);
		if (temp != null)
			if (temp.get(country) != null)
				return probWeightCountMapping.get(setId).get(country);
			else
				return 0;
		else
			return 0;
	}

	public static ArrayList<TavernConfig> getTavernConfigMapBySet(int setId, int country) {
		if(tavernConfigMapping.get(setId)!=null){
			return tavernConfigMapping.get(setId).get(country);
		}else{
			return null;
		}
		
	}

	private static void initMappings(TavernConfig tavernConfig) {
		initProbWeightCountMapping(tavernConfig);
		initTavernConfigMapping(tavernConfig);
	}

	private static void initTavernConfigMapping(TavernConfig tavernConfig) {
		int setId = tavernConfig.getSetID();
		int country = tavernConfig.getCountry();
		HashMap<Integer, ArrayList<TavernConfig>> hashMap = tavernConfigMapping.get(setId);
		if (hashMap == null) {
			hashMap = new HashMap<Integer, ArrayList<TavernConfig>>();
			ArrayList<TavernConfig> array = new ArrayList<TavernConfig>();

			hashMap.put(country, array);
			tavernConfigMapping.put(setId, hashMap);

		}
		ArrayList<TavernConfig> array = hashMap.get(country);
		if (array == null) {
			array = new ArrayList<TavernConfig>();
			hashMap.put(country, array);
		}
		array.add(tavernConfig);
	}

	private static void initProbWeightCountMapping(TavernConfig tavernConfig) {
		int setId = tavernConfig.getSetID();
		int country = tavernConfig.getCountry();
		HashMap<Integer, Integer> countryMap = probWeightCountMapping.get(setId);
		if (countryMap == null) {
			countryMap = new HashMap<Integer, Integer>();
			countryMap.put(country, 0);

			probWeightCountMapping.put(setId, countryMap);
		}
		Integer probWeightCount = countryMap.get(country);
		if (probWeightCount == null) {
			probWeightCount = 0;
		}
		countryMap.put(country, probWeightCount + tavernConfig.getProbWeight());
	}

}
