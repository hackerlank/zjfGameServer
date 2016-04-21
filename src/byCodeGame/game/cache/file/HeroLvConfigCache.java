package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.HeroLvConfig;

public class HeroLvConfigCache {

	private static Map<Integer, HeroLvConfig> heroLvConfigMap = new HashMap<Integer, HeroLvConfig>();
	
	public static void putHeroLvConfig(HeroLvConfig heroLvConfig){
		getHeroLvConfigMap().put(heroLvConfig.getLv(), heroLvConfig);
	}
	
	public static HeroLvConfig getHeroLvConfig(int lv){
		return getHeroLvConfigMap().get(lv);
	}

	public static Map<Integer, HeroLvConfig> getHeroLvConfigMap() {
		return heroLvConfigMap;
	}

}
