package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.EquiptPrefix;

public class EquiptPrefixCache {
	/** 从配置读取出来的初始数据*/
	private static Map<Integer, EquiptPrefix> equiptPrefixMap = new HashMap<Integer, EquiptPrefix>();
	private static Map<Integer, EquiptPrefix> jz_1_map = new HashMap<Integer, EquiptPrefix>();
	private static Map<Integer, EquiptPrefix> fn_2_map = new HashMap<Integer, EquiptPrefix>();
	private static Map<Integer, EquiptPrefix> yy_3_map = new HashMap<Integer, EquiptPrefix>();
	private static Map<Integer, EquiptPrefix> ss_4_map = new HashMap<Integer, EquiptPrefix>();
	
	
	
	public static void putEquiptPrefix(EquiptPrefix equiptPrefix){
		equiptPrefixMap.put(equiptPrefix.getId(), equiptPrefix);
		switch (equiptPrefix.getType()) {
		case 1:
			jz_1_map.put(equiptPrefix.getId(), equiptPrefix);
			break;
		case 2:
			fn_2_map.put(equiptPrefix.getId(), equiptPrefix);
			break;
		case 3:
			yy_3_map.put(equiptPrefix.getId(), equiptPrefix);
			break;
		case 4:
			ss_4_map.put(equiptPrefix.getId(), equiptPrefix);
			break;
		default:
			break;
		}
	}
			
	public static EquiptPrefix getEquipConfigById(int id){
		return equiptPrefixMap.get(id);
	}

	public static Map<Integer, EquiptPrefix> getJz_1_use() {
		return jz_1_map;
	}

	public static void setJz_1_use(Map<Integer, EquiptPrefix> jz_1_use) {
		EquiptPrefixCache.jz_1_map = jz_1_use;
	}

	public static Map<Integer, EquiptPrefix> getFn_2_use() {
		return fn_2_map;
	}

	public static void setFn_2_use(Map<Integer, EquiptPrefix> fn_2_use) {
		EquiptPrefixCache.fn_2_map = fn_2_use;
	}

	public static Map<Integer, EquiptPrefix> getYy_3_use() {
		return yy_3_map;
	}

	public static void setYy_3_use(Map<Integer, EquiptPrefix> yy_3_use) {
		EquiptPrefixCache.yy_3_map = yy_3_use;
	}

	public static Map<Integer, EquiptPrefix> getSs_4_use() {
		return ss_4_map;
	}

	public static void setSs_4_use(Map<Integer, EquiptPrefix> ss_4_use) {
		EquiptPrefixCache.ss_4_map = ss_4_use;
	}
	
	public static Map<Integer, EquiptPrefix> getAll()
	{
		return equiptPrefixMap;
	}
}
