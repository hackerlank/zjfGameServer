package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.HeroConfig;

public class HeroConfigCache {

	private static Map<Integer, HeroConfig> heroConfigMap = new HashMap<Integer, HeroConfig>();
	
	public static void putHeroConfig(HeroConfig heroConfig){
		heroConfigMap.put(heroConfig.getHeroId(), heroConfig);
	}
	
	public static HeroConfig getHeroConfigById(int heroId){
		return heroConfigMap.get(heroId);
	}
	
}
