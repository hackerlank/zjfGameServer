package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.RoleArmyConfig;

public class RoleArmyConfigCache {
	private static Map<Integer, RoleArmyConfig> maps = new HashMap<Integer, RoleArmyConfig>();
	private static Map<Integer, RoleArmyConfig> bb_maps = new HashMap<Integer, RoleArmyConfig>();
	private static Map<Integer, RoleArmyConfig> qq_maps = new HashMap<Integer, RoleArmyConfig>();
	private static Map<Integer, RoleArmyConfig> gb_maps = new HashMap<Integer, RoleArmyConfig>();
	private static Map<Integer, RoleArmyConfig> fs_maps = new HashMap<Integer, RoleArmyConfig>();
	private static Map<Integer, RoleArmyConfig> qb_maps = new HashMap<Integer, RoleArmyConfig>();
	
	public static void putRoleArmyConfig(RoleArmyConfig roleArmyConfig){
		maps.put(roleArmyConfig.getId(), roleArmyConfig);
		switch (roleArmyConfig.getType()) {
		case 1:
			bb_maps.put(roleArmyConfig.getId(), roleArmyConfig);
			break;
		case 2:
			qq_maps.put(roleArmyConfig.getId(), roleArmyConfig);
			break;
		case 3:
			gb_maps.put(roleArmyConfig.getId(), roleArmyConfig);
			break;
		case 4:
			fs_maps.put(roleArmyConfig.getId(), roleArmyConfig);
			break;
		case 5:
			qb_maps.put(roleArmyConfig.getId(), roleArmyConfig);
			break;
		default:
			break;
		}
	}
	
	public static RoleArmyConfig getRoleArmyConfigById(int id){
		return maps.get(id);
	}
	
	/**
	 * 根据类型获取天赋Map
	 * @param type 0为全部 之后为 步骑弓法抢
	 * @return
	 */
	public static Map<Integer, RoleArmyConfig> getMapInfo(int type)
	{
		switch (type) {
		case 0:
			return maps;
		case 1:
			return bb_maps;
		case 2:
			return qq_maps;
		case 3:
			return gb_maps;
		case 4:
			return fs_maps;
		case 5:
			return qb_maps;
		default:
			return null;
		}
	}
}
