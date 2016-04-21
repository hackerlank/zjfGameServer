package byCodeGame.game.cache.file;

import java.util.HashMap;

import byCodeGame.game.entity.file.MainCityConfig;

public class MainCityConfigCache {
	private static HashMap<Integer,MainCityConfig> maps = new HashMap<Integer,MainCityConfig>();
	
	public static void putMainCityConfig(MainCityConfig mainCityConfig){
		maps.put(mainCityConfig.getLevel(), mainCityConfig);
	}
	
	public static MainCityConfig getMainCityConfig(int level){
		return maps.get(level) == null ? maps.get(80):maps.get(level);
	}
}
