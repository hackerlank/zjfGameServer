package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.CityWallConfig;

public class CityWallConfigCache {
	private static Map<Integer, CityWallConfig>allMap = new HashMap<Integer, CityWallConfig>();
	private static Map<Integer, Map<Integer, CityWallConfig>> oneMap = new HashMap<Integer, Map<Integer,CityWallConfig>>();
	
	public static void putCityWallConfig(CityWallConfig cityWallConfig){
		allMap.put(cityWallConfig.getId(), cityWallConfig);
		Map<Integer, CityWallConfig> temp = oneMap.get(cityWallConfig.getCityID());
		if(temp == null) oneMap.put(cityWallConfig.getCityID(), new HashMap<Integer,CityWallConfig>());
		oneMap.get(cityWallConfig.getCityID()).put(cityWallConfig.getWallID(), cityWallConfig);
	}
	
	public static CityWallConfig getCityWallConfig(int id){
		return allMap.get(id);
	}
	
	public static CityWallConfig getCityWallConfig(int cityId ,int wallId)
	{
		return oneMap.get(cityId).get(wallId);
	}
}
