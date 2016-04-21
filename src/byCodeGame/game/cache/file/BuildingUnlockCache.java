package byCodeGame.game.cache.file;

import java.util.HashMap;

import byCodeGame.game.entity.file.BuildingUnlock;

public class BuildingUnlockCache {
	// <建筑类型,<子资源建筑位置id,信息>>
	private static HashMap<Byte, HashMap<Byte, BuildingUnlock>> maps2 = new HashMap<>();
	// <建筑类型,<限制等级,信息>>
	private static HashMap<Byte, HashMap<Integer, BuildingUnlock>> maps3 = new HashMap<>();

	private static HashMap<Byte, Integer> minLevelMap = new HashMap<>();
	private static HashMap<Byte, BuildingUnlock> maxLimitLevelMap = new HashMap<>();

	public static void putBuildingUnlock(BuildingUnlock buildingUnlock) {
		byte type = (byte) buildingUnlock.getType();
		byte buildLocation = (byte) buildingUnlock.getUnlockLoc();
		int lvLimit = buildingUnlock.getLvLimit();

		BuildingUnlock maxBuildingUnlock = maxLimitLevelMap.get(type);
		if (maxBuildingUnlock == null)
			maxLimitLevelMap.put(type, buildingUnlock);
		else if (maxBuildingUnlock.getLvLimit() < lvLimit)
			maxLimitLevelMap.put(type, buildingUnlock);

		// <建筑类型,<子资源建筑位置id,信息>>
		HashMap<Byte, BuildingUnlock> map2 = maps2.get(type);
		if (map2 == null)
			map2 = new HashMap<>();
		map2.put(buildLocation, buildingUnlock);
		maps2.put(type, map2);

		// <建筑类型,<限制等级,信息>>
		HashMap<Integer, BuildingUnlock> m = maps3.get(type);
		if (m == null) {
			m = new HashMap<Integer, BuildingUnlock>();
			maps3.put(type, m);
			minLevelMap.put(type, buildingUnlock.getLvLimit());
		}
		m.put(lvLimit, buildingUnlock);
		add(lvLimit, type);
	}

	/**
	 * 填充该资源等级与上个资源等级当中缺失的资源等级
	 * 
	 * @param limitLv
	 * @param type
	 * @return
	 * @author wcy
	 */
	private static BuildingUnlock add(int limitLv, byte type) {
		if (limitLv == minLevelMap.get(type)) {
			return null;
		}

		int tempLimitLv = limitLv - 1;
		BuildingUnlock tb = maps3.get(type).get(tempLimitLv);
		if (tb == null) {
			tb = add(tempLimitLv, type);
			maps3.get(type).put(tempLimitLv, tb);
		}
		return tb;

	}

	/**
	 * 获得子建筑表 <建筑类型,<子资源建筑位置id,信息>>
	 * 
	 * @return
	 * @author wcy
	 */
	public static HashMap<Byte, HashMap<Byte, BuildingUnlock>> getBuildingUnlockMaps() {
		return maps2;
	}

	/**
	 * 
	 * @param type
	 * @param lv
	 * @return
	 * @author wcy
	 */
	public static byte getUnlockLocByLimitLv(byte type, int lv) {
		BuildingUnlock buildingUnlock = maps3.get(type).get(lv);
		if (buildingUnlock == null) {
			BuildingUnlock maxBuildingUnlock = maxLimitLevelMap.get(type);
			if (lv > maxBuildingUnlock.getLvLimit()) {
				return (byte) maxBuildingUnlock.getUnlockLoc();
			}
			return 0;
		}
		return buildingUnlock.getUnlockLoc();
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 * @author wcy
	 */
	public static byte getMaxBuildingUnlock(byte type){
		return maxLimitLevelMap.get(type).getUnlockLoc();
	}
}
