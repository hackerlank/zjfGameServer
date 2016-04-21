package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.RoleExpConfig;

public class RoleExpConfigCache {

	private static Map<Integer, RoleExpConfig> map = new HashMap<Integer, RoleExpConfig>();
	
	public static void putRoleExpConfig(RoleExpConfig roleExpConfig){
		getMap().put(roleExpConfig.getLv(), roleExpConfig);
	}

	public static Map<Integer, RoleExpConfig> getMap() {
		return map;
	}
}
