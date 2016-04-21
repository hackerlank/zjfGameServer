package byCodeGame.game.cache.file;

import java.util.HashMap;

import byCodeGame.game.entity.file.WorldWarPath;
import byCodeGame.game.util.Utils;

/**
 * 
 * @author wcy
 *
 */
public class WorldWarPathCache {
	private static HashMap<Integer, WorldWarPath> map = new HashMap<>();
	/** <城市编号,<城门号,数据>> */
	private static HashMap<Integer, HashMap<Integer, WorldWarPath>> map2 = new HashMap<>();
	/**<城市1编号，<城市2编号，数据>>*/
	private static HashMap<Integer,HashMap<Integer,WorldWarPath>> map3 = new HashMap<>();
	/**<城市1编号，<路径编号，数据>>*/
	private static HashMap<Integer,HashMap<Integer,WorldWarPath>> map4 = new HashMap<>();

	public static void putWorldWarPath(WorldWarPath worldWarPath) {
		int cityId = worldWarPath.getCityID();
		int wallId = worldWarPath.getWallID();
		int linkedCityId = worldWarPath.getLinkCityID();

		map.put(cityId, worldWarPath);

		HashMap<Integer, WorldWarPath> m1 = map2.get(cityId);
		if (m1 == null) {
			m1 = new HashMap<>();
			map2.put(cityId, m1);
		}

		m1.put(wallId, worldWarPath);
		
		HashMap<Integer, WorldWarPath> m2 = map3.get(cityId);
		if (m2 == null) {
			m2 = new HashMap<>();
			map3.put(cityId, m2);
		}

		m2.put(linkedCityId, worldWarPath);
		
		HashMap<Integer, WorldWarPath> m3 = map4.get(cityId);
		if (m3 == null) {
			m3 = new HashMap<>();
			map4.put(cityId, m3);
		}
		int pathId = Utils.getCityPathNum(cityId, linkedCityId);
		m3.put(pathId, worldWarPath);
		
	}

	public static WorldWarPath getWorldWarPathByCityId(int cityId) {
		return map.get(cityId);
	}

	public static HashMap<Integer, WorldWarPath> getWorldWarPathMap() {
		return map;
	}
	
	public static WorldWarPath getWorldWarPathByCities(int city1Id,int city2Id){
		return map3.get(city1Id).get(city2Id);
	}
	
	public static WorldWarPath getWorldWarPathByCityPath(int cityId,int path){
		return map4.get(cityId).get(path);
	}
	public static WorldWarPath getWorldWarPathByCityWall(int cityId,int wallId){
		return map2.get(cityId).get(wallId);
	}

}
