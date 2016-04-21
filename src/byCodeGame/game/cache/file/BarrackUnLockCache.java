package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.BarrackUnLock;

public class BarrackUnLockCache {

	private static Map<Integer, BarrackUnLock> footMap = new HashMap<Integer, BarrackUnLock>();
	private static Map<Integer, BarrackUnLock> cavalryMap = new HashMap<Integer, BarrackUnLock>();
	private static Map<Integer, BarrackUnLock> arrowMap = new HashMap<Integer, BarrackUnLock>();
	private static Map<Integer, BarrackUnLock> masterMap = new HashMap<Integer, BarrackUnLock>();
	
	public static void putBarrackUnLock(BarrackUnLock barrackUnLock){
		if(barrackUnLock.getFunctionType() == 1){
			footMap.put(barrackUnLock.getLv(), barrackUnLock);
		}else if(barrackUnLock.getFunctionType() == 2){
			cavalryMap.put(barrackUnLock.getLv(), barrackUnLock);
		}else if(barrackUnLock.getFunctionType() == 3){
			arrowMap.put(barrackUnLock.getLv(), barrackUnLock);
		}else if(barrackUnLock.getFunctionType() == 4){
			masterMap.put(barrackUnLock.getLv(), barrackUnLock);
		}
	}
	
	public static BarrackUnLock getBarrackUnLock(byte type,short lv){
		BarrackUnLock barrackUnLock = null;
		if(type == 1){
			barrackUnLock = footMap.get((int)lv);
		}else if(type == 2){
			barrackUnLock = cavalryMap.get((int)lv);
		}else if(type == 3){
			barrackUnLock = arrowMap.get((int)lv);
		}else if(type == 4){
			barrackUnLock = masterMap.get((int)lv);
		}
		
		return barrackUnLock;
	}
}
