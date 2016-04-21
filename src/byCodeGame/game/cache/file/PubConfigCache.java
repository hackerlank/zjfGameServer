package byCodeGame.game.cache.file;

import java.util.HashMap;

import byCodeGame.game.entity.file.PubConfig;

public class PubConfigCache {
	private static HashMap<Integer,PubConfig> maps = new HashMap<>();
	public static void putPubConfig(PubConfig pubConfig){
		maps.put(pubConfig.getLv(), pubConfig);
	}
	
	public static PubConfig getPubConfigByLv(int lv){
		return maps.get(lv);
	}
}
