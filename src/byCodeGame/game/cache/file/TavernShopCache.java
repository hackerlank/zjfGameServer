package byCodeGame.game.cache.file;

import java.util.ArrayList;
import java.util.HashMap;

import byCodeGame.game.entity.file.TavernShop;

public class TavernShopCache {
	private static HashMap<Integer, TavernShop> maps = new HashMap<Integer, TavernShop>();
	/** <国家，<每周英雄>> */
	private static HashMap<Integer, ArrayList<TavernShop>> countryMapping = new HashMap<Integer, ArrayList<TavernShop>>();

	public static void putTavernShop(TavernShop tavernShop) {
		if (countryMapping.size() == 0) {
			for (int i = 1; i <= 3; i++) {
				countryMapping.put(i, new ArrayList<TavernShop>());
			}
		}

		maps.put(tavernShop.getHeroID(), tavernShop);
		int country = tavernShop.getCountry();
		if (country == 0) {
			for (int i = 1; i <= 3; i++) {
				countryMapping.get(i).add(tavernShop);
			}
			return;
		}

		countryMapping.get(country).add(tavernShop);
	}

	public static TavernShop getTavernShop(int heroID) {
		return maps.get(heroID);
	}

	/**
	 * 获得每周英雄
	 * 
	 * @param country
	 * @return
	 * @author wcy
	 */
	public static ArrayList<TavernShop> getWeekHeros(int country) {
		return countryMapping.get(country);
	}

}
