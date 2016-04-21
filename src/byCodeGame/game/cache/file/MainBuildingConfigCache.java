package byCodeGame.game.cache.file;

import java.util.HashMap;

import byCodeGame.game.entity.file.MainBuildingConfig;

/**
 * 
 * @author wcy
 *
 */
public class MainBuildingConfigCache {
	private static HashMap<Byte, HashMap<Integer, MainBuildingConfig>> maps = new HashMap<>();

	public static void putMainBuildingConfig(MainBuildingConfig mainBuildingConfig) {
		byte type = (byte) mainBuildingConfig.getType();
		int lv = mainBuildingConfig.getLv();
		HashMap<Integer, MainBuildingConfig> map = maps.get(type);
		if (map == null) {
			map = new HashMap<>();
			maps.put(type, map);
		}

		map.put(lv, mainBuildingConfig);
	}

	public static MainBuildingConfig getMainBuildingConfig(byte type, int lv) {
		return maps.get(type).get(lv);
	}
	
	/**
	 * 
	 * @return HashMap<type,<lv,config>>
	 * @author wcy
	 */
	public static HashMap<Byte,HashMap<Integer,MainBuildingConfig>> getMaps(){
		return maps;
	}
}
