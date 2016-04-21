package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import byCodeGame.game.entity.file.MapInfo;
import byCodeGame.game.entity.po.MapInfoData;

public class MapInfoCache {
	private static Set<MapInfo> set_all = new HashSet<MapInfo>();
	private static Map<Integer, MapInfo> map_1 = new HashMap<Integer, MapInfo>();
	private static Map<Integer, MapInfo> map_2 = new HashMap<Integer, MapInfo>();
	private static Map<Integer, MapInfo> map_3 = new HashMap<Integer, MapInfo>();

	private static Map<Integer, MapInfoData> map_1_use = new HashMap<Integer, MapInfoData>();
	private static Map<Integer, MapInfoData> map_2_use = new HashMap<Integer, MapInfoData>();
	private static Map<Integer, MapInfoData> map_3_use = new HashMap<Integer, MapInfoData>();
	
	public static void putMapInfo(MapInfo mapInfo){
		set_all.add(mapInfo);
		
		if(mapInfo.getReputationID()==1)
		{
			map_1.put(mapInfo.getId(),mapInfo);
		}else if (mapInfo.getReputationID()==2) {
			map_2.put(mapInfo.getId(),mapInfo);
		}else if (mapInfo.getReputationID()==3) {
			map_3.put(mapInfo.getId(), mapInfo);
		}
	}
			
	public static MapInfo getMapInfo(int id,int reputationID){
		MapInfo mapInfo = new MapInfo();
		if(reputationID == 1)
		{
			mapInfo = map_1.get(id);
		}else if (reputationID == 2) {
			mapInfo = map_2.get(id);
		}else if (reputationID == 3) {
			mapInfo = map_3.get(id);
		}
		
		return mapInfo;
	}

	public static Map<Integer, MapInfoData> getMap_1_use() {
		return map_1_use;
	}

	public static void setMap_1_use(Map<Integer, MapInfoData> map_1_use) {
		MapInfoCache.map_1_use = map_1_use;
	}

	public static Map<Integer, MapInfoData> getMap_2_use() {
		return map_2_use;
	}

	public static void setMap_2_use(Map<Integer, MapInfoData> map_2_use) {
		MapInfoCache.map_2_use = map_2_use;
	}

	public static Map<Integer, MapInfoData> getMap_3_use() {
		return map_3_use;
	}

	public static void setMap_3_use(Map<Integer, MapInfoData> map_3_use) {
		MapInfoCache.map_3_use = map_3_use;
	}
	
	public static Map<Integer, MapInfo> getMapInfo(int reputationID)
	{
		switch (reputationID) {
		case 1:
			return map_1;
		case 2:
			return map_2;
		case 3:
			return map_3;
		default:
			return null;
		}
	}
}