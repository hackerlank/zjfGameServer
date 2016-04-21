package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.LevelUnLock;

public class LevelUnLockCache {

	private static Map<Integer, LevelUnLock> levelUnLockMap = new HashMap<Integer, LevelUnLock>();
	
	public void putLevelUnLock(LevelUnLock levelUnLock){
		levelUnLockMap.put(levelUnLock.getLv(), levelUnLock);
	}
	
	public static LevelUnLock getLevelUnLockByLv(int lv){
		return levelUnLockMap.get(lv);
	}
}
