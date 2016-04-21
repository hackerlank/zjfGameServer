package byCodeGame.game.cache.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byCodeGame.game.entity.bo.Legion;

public class LegionCache {

	/** 军团缓存 key:军团ID value:军团类	 */
	private static Map<Integer, Legion> legionMap = new HashMap<Integer, Legion>();
	/** 军团名缓存	 		 */
	private static List<String> legionNameList = new ArrayList<String>();
	/** 军团旗号缓存		 */
	private static Map<Integer, String> legionShortName = new HashMap<Integer, String>();
	
	/**
	 * 获取军团类缓存
	 * @param legionId
	 * @return
	 */
	public static Legion getLegionById(int legionId){
		return getLegionMap().get(legionId);
	}
	
	/**
	 * 判断军团名是否存在
	 * @param legionName
	 * @return
	 */
	public static boolean checkLegionName(String legionName){
		return getLegionNameList().contains(legionName);
	}

	public static List<String> getLegionNameList() {
		return legionNameList;
	}

	public static void setLegionNameList(List<String> legionNameList) {
		LegionCache.legionNameList = legionNameList;
	}
	
	public static void addLegionInCache(Legion legion){
		LegionCache.getLegionMap().put(legion.getLegionId(), legion);
	}

	public static Map<Integer, Legion> getLegionMap() {
		return legionMap;
	}

	public static Map<Integer, String> getLegionShortName() {
		return legionShortName;
	}

	public static void setLegionShortName(Map<Integer, String> legionShortName) {
		LegionCache.legionShortName = legionShortName;
	}
}
