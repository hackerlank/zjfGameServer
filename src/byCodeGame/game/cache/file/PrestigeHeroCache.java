package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.PrestigeHero;

/**
 * 声望商城
 * @author wcy 2016年3月5日
 *
 */
public class PrestigeHeroCache {
	private static Map<Integer, PrestigeHero> map = new HashMap<>();

	public static void putPrestigeHero(PrestigeHero prestigeHero) {
		int heroId = prestigeHero.getID();
		map.put(heroId, prestigeHero);
	}

	public static PrestigeHero getPrestigeHeroByID(int id) {
		return map.get(id);
	}
}
