package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import byCodeGame.game.entity.file.Notable;

/**
 * 走马灯
 * 
 * @author wcy 2016年2月18日
 *
 */
public class NotableCache {
	private static Map<Integer, Notable> map = new HashMap<>();

	/** 判断类型是否特定显示 */
	private static Map<Byte, Integer> typeMap = new HashMap<>();
	/**特定显示<类型，集>*/
	private static Map<Byte,Set<Integer>> specialMap = new HashMap<>();
	/**连续显示<类型，数字>*/
	private static Map<Byte,Integer> sequcenceMap = new HashMap<>();

	public static void putNotable(Notable notable) {
		int id = notable.getId();
		byte type = (byte) notable.getType();
		String limit = notable.getLimit();
		int sequence = notable.getSequence();
		map.put(id, notable);
		typeMap.put(type, sequence);
		if(sequence == 0){
			//特定显示
			Set<Integer> set = specialMap.get(type);
			if(set == null){
				set = new HashSet<>();
				specialMap.put(type, set);
			}
			
			String[] list = limit.split(",");
			for(String lv:list){
				set.add(Integer.parseInt(lv));
			}
			
		}else{
			//延续显示			
			int lv = Integer.parseInt(limit.split(",")[0]);
			sequcenceMap.put(type, lv);
		}		
	}

	public static Map<Byte,Integer> getTypeMap(byte type) {
		return typeMap;
	}
	
	public static Set<Integer> getSpecialMap(byte type){
		return specialMap.get(type);
	}
	
	public static Integer getSequenceNum(byte type){
		return sequcenceMap.get(type);
	}

}
