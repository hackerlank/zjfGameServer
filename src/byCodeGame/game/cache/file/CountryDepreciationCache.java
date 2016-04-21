package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.CountryDepreciation;

public class CountryDepreciationCache {
	private static Map<Integer, CountryDepreciation> maps = new HashMap<Integer, CountryDepreciation>();
	public static void putCountryDepreciation(CountryDepreciation countryDepreciation){
		maps.put(countryDepreciation.getCity(), countryDepreciation);
	}
	
	public static CountryDepreciation getCountryDepreciation(int city){
		if(maps.containsKey(city))
		{
			return maps.get(city);
		}else {
			return maps.get(maps.size());
		}
		
	}
}
