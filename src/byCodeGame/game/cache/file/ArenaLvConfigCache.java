package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ArenaLvConfig;

public class ArenaLvConfigCache {

	private static Map<Integer, ArenaLvConfig> map = new HashMap<Integer, ArenaLvConfig>();
	
	public static void putArenaLvConfig(ArenaLvConfig arenaLvConfig){
		map.put(arenaLvConfig.getLv(), arenaLvConfig);
	}
	
	public static ArenaLvConfig getArenaLvConfig(int lv){
		return map.get(lv);
	}
}
