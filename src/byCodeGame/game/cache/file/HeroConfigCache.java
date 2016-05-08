package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.HeroConfig;

public class HeroConfigCache {
	private static Map<Integer, HeroConfig> map = new HashMap<>();

	public static void putHeroConfig(HeroConfig heroConfig) {
		int heroId = heroConfig.getHeroId();
		map.put(heroId, heroConfig);
	}

	public static HeroConfig getHeroConfigById(int heroId) {
		HeroConfig config = map.get(heroId);
		if (config == null) {
			System.out.println(heroId);
		}
		return config;
	}
}
