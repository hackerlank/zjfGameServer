package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.PrestigeShopLv;

public class PrestigeShopLvCache {
	private static Map<Integer,PrestigeShopLv> map = new HashMap<>();
	public void putPrestigeShopLv(PrestigeShopLv prestigeShopLv){
		int lvId = prestigeShopLv.getLvId();
		map.put(lvId, prestigeShopLv);
	}
}
