package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.RoleScienceConfig;

/**
 * 配置表- 玩家科技
 * @author xjd
 *
 */
public class RoleScienceConfigCache {
	/**
	 * map key 类型
	 * map value 缓存信息
	 */
	private static Map<Integer, Map<Integer, RoleScienceConfig>> type_Map =new HashMap<Integer,Map<Integer, RoleScienceConfig>>();
	
	
	
	public static void putRoleScienceConfig(RoleScienceConfig roleScienceConfig){
		Map<Integer, RoleScienceConfig> temp = type_Map.get(roleScienceConfig.getId());
		if(temp == null) type_Map.put(roleScienceConfig.getId(), new HashMap<Integer , RoleScienceConfig>());
		type_Map.get(roleScienceConfig.getId()).put(roleScienceConfig.getLv(), roleScienceConfig);
	}
			
	public static RoleScienceConfig getRoleScienceConfig(int id ,int lv){
		return type_Map.get(id).get(lv);
	}	
}
