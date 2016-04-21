package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.WorldWarArmsConfig;

public class WorldWarArmsConfigCache {
	private static Map<Integer, WorldWarArmsConfig> worldWarArmsConfigMap = new HashMap<Integer, WorldWarArmsConfig>();
	
	public static void putWorldWarArmsConfig(WorldWarArmsConfig worldWarArmsConfig){
		worldWarArmsConfigMap.put(worldWarArmsConfig.getId(), worldWarArmsConfig);
	}
			
	public static WorldWarArmsConfig getCityGarrisonConfigById(int id){
		return worldWarArmsConfigMap.get(id);
	}
}
