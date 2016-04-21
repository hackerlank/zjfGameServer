 package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.EquipConfig;

public class EquipConfigCache {

	private static Map<Integer, EquipConfig> equipConfigMap = new HashMap<Integer, EquipConfig>();
	
	public static void putEquipConfig(EquipConfig equipConfig){
		equipConfigMap.put(equipConfig.getId(), equipConfig);
	}
			
	public static EquipConfig getEquipConfigById(int equipId){
		return equipConfigMap.get(equipId);
	}
	
}
