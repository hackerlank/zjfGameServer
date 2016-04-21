package byCodeGame.game.cache.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

import byCodeGame.game.entity.bo.Arena;
import byCodeGame.game.entity.bo.LadderArena;

public class ArenaCache {

	private static Arena arena;
	private static Map<Integer, Set<Integer>> arenaSetMap = new HashMap<Integer, Set<Integer>>();
	private static Map<Integer, Lock> lockMap = new HashMap<Integer, Lock>();
	/**
	 * 根据名次存储天梯排名
	 */
	private static Map<Integer, LadderArena> ladderMap = new ConcurrentHashMap<Integer, LadderArena>();
	/**
	 * 根据roleId存储天梯排名
	 */
	private static Map<Integer, LadderArena> ladderMap2 = new ConcurrentHashMap<Integer, LadderArena>();
	/**
	 * 锁定已经被挑战的玩家 不可被挑战
	 */
	private static List<Integer> ladderLock=new ArrayList<Integer>();
	
	public static Arena getArena() {
		return arena;
	}

	public static void setArena(Arena arena) {
		ArenaCache.arena = arena;
//		arenaSetMap.put(1, arena.getLv1Set());
//		arenaSetMap.put(2, arena.getLv2Set());
//		arenaSetMap.put(3, arena.getLv3Set());
//		arenaSetMap.put(4, arena.getLv4Set());
//		arenaSetMap.put(5, arena.getLv5Set());
//		arenaSetMap.put(6, arena.getLv6Set());
//		
//		lockMap.put(1, arena.getLv1Lock());
//		lockMap.put(2, arena.getLv2Lock());
//		lockMap.put(3, arena.getLv3Lock());
//		lockMap.put(4, arena.getLv4Lock());
//		lockMap.put(5, arena.getLv5Lock());
//		lockMap.put(6, arena.getLv6Lock());
	}

	public static Map<Integer, Set<Integer>> getArenaSetMap() {
		return arenaSetMap;
	}

	public static Map<Integer, Lock> getLockMap() {
		return lockMap;
	}

	public static Map<Integer, LadderArena> getLadderMap() {
		return ladderMap;
	}

	public static void setLadderMap(Map<Integer, LadderArena> ladderMap) {
		ArenaCache.ladderMap = ladderMap;
	}

	public static Map<Integer, LadderArena> getLadderMap2() {
		return ladderMap2;
	}

	public static void setLadderMap2(Map<Integer, LadderArena> ladderMap2) {
		ArenaCache.ladderMap2 = ladderMap2;
	}

	public static List<Integer> getLadderLock() {
		return ladderLock;
	}

	public static void setLadderLock(List<Integer> ladderLock) {
		ArenaCache.ladderLock = ladderLock;
	}

}
