package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ZhuanshengConfig;

public class ZhuanshengConfigCache {
	private static Map<Integer, ZhuanshengConfig> map = new HashMap<>();

	public static void putZhuanshengConfig(ZhuanshengConfig zhuanshengConfig) {
		int lv = zhuanshengConfig.getLv();
		map.put(lv, zhuanshengConfig);		
	}

	public static ZhuanshengConfig getZhuanshengConfig(int lv) {
		return map.get(lv);
	}
	
}
