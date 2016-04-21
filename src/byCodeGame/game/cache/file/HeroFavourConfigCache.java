package byCodeGame.game.cache.file;

import java.util.HashMap;

import byCodeGame.game.entity.file.HeroFavourConfig;

public class HeroFavourConfigCache {
	private static HashMap<Integer, HeroFavourConfig> maps = new HashMap<Integer, HeroFavourConfig>();

	public static void putHeroFavourConfig(HeroFavourConfig heroFavourConfig) {
		maps.put(heroFavourConfig.getLv(), heroFavourConfig);
	}

	public static HeroFavourConfig getHeroFavourConfig(int lv) {
		return maps.get(lv);
	}

	public static HashMap<Integer, HeroFavourConfig> getHeroFavourConfigMaps() {
		return maps;
	}
}
