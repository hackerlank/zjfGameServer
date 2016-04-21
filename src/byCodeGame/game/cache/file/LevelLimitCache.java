package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.LevelLimit;


public class LevelLimitCache {

	
private static Map<Integer, LevelLimit>levelLimitMap = new HashMap<Integer, LevelLimit>();
	
	public static void putLevelLimit(LevelLimit levelLimit){
		levelLimitMap.put(levelLimit.getLv(), levelLimit);
	}
	
	public static LevelLimit getLevelLimitByLv(int lv){
		return levelLimitMap.get(lv);
	}
}
