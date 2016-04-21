package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;
import byCodeGame.game.entity.file.CityInfoConfig;


public class CityInfoConfigCache {

	private static Map<Integer, CityInfoConfig> CityInfoMap = new HashMap<Integer, CityInfoConfig>();

	private static Map<Byte, CityInfoConfig> birthCityMap = new HashMap<>();

	public static void putCityInfoConfig(CityInfoConfig city){
		int cityId = city.getCityId();
		byte nation = (byte)city.getNation();
		CityInfoMap.put(cityId, city);
		
		if (nation != 0) {
			birthCityMap.put(nation, city);
		}
	}
	
	public static CityInfoConfig getCityInfoConfigById(Integer cityId){
		return CityInfoMap.get(cityId);
	}
	
	public static Map<Integer, CityInfoConfig> getCityInfoMap(){
		return CityInfoMap;
	}
	
	public static CityInfoConfig getBirthCityByNation(byte nation){
		return birthCityMap.get(nation);
	}

	public static Map<Byte, CityInfoConfig> getBirthCityMap() {
		return birthCityMap;
	}
}
