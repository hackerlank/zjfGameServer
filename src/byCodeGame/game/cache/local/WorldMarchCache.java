package byCodeGame.game.cache.local;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import byCodeGame.game.entity.po.WorldMarch;

/**
 * 行军信息
 * 
 * @author xjd
 *
 */
public class WorldMarchCache {
	/** 全服行军信息 */
	private static ConcurrentHashMap<String, WorldMarch> allMarch = new ConcurrentHashMap<String, WorldMarch>();
	/** 快速检查队列 */
	private static Set<String> quickCheck = new HashSet<String>();
	private static boolean canMarch = false;
	private static ConcurrentHashMap<String, WorldMarch> guideAllMarch = new ConcurrentHashMap<String, WorldMarch>();

	public static ConcurrentHashMap<String, WorldMarch> getAllMarch() {
		return allMarch;
	}

	public static void setAllMarch(ConcurrentHashMap<String, WorldMarch> allMarch) {
		WorldMarchCache.allMarch = allMarch;
	}

	public static synchronized void addMarch(WorldMarch worldMarch) {
		allMarch.put(worldMarch.getId(), worldMarch);
	}

	public static synchronized void removeMarch(String id) {
		allMarch.remove(id);
	}

	public static synchronized void removeGuideMarch(String id) {
		guideAllMarch.remove(id);
	}

	public static WorldMarch getWorldMarch(String id) {
		WorldMarch w = allMarch.get(id);
		
		return w;
	}

	public static WorldMarch getGuideWorldMarch(String id) {
		return guideAllMarch.get(id);
	}

	public static synchronized void addGuideMarch(WorldMarch worldMarch) {
		guideAllMarch.put(worldMarch.getId(), worldMarch);
	}

	public static Set<String> getQuickCheck() {
		return quickCheck;
	}

	public static void setQuickCheck(Set<String> quickCheck) {
		WorldMarchCache.quickCheck = quickCheck;
	}

	/**
	 * @return the canMarch
	 */
	public static boolean isCanMarch() {
		return canMarch;
	}

	/**
	 * @param canMarch the canMarch to set
	 */
	public static void setCanMarch(boolean canMarch) {
		WorldMarchCache.canMarch = canMarch;
	}

	public static ConcurrentHashMap<String, WorldMarch> getGuideAllMarch() {
		return guideAllMarch;
	}

	public static void setGuideAllMarch(ConcurrentHashMap<String, WorldMarch> guideAllMarch) {
		WorldMarchCache.guideAllMarch = guideAllMarch;
	}
}
