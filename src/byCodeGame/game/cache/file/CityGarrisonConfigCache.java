package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.CityGarrisonConfig;

public class CityGarrisonConfigCache {
	private static Map<Integer, CityGarrisonConfig> garrisonConfigMap = new HashMap<Integer, CityGarrisonConfig>();
	
	public static void putCityGarrisonConfig(CityGarrisonConfig garrisonConfig){
		garrisonConfigMap.put(garrisonConfig.getCityId(), garrisonConfig);
	}
			
	public static CityGarrisonConfig getCityGarrisonConfigById(int cityId){
		return garrisonConfigMap.get(cityId);
	}
}
