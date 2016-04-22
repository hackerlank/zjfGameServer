package byCodeGame.game.cache.local;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.util.ConcurrentHashSet;

import byCodeGame.game.entity.bo.Role;

/**
 * 玩家缓存
 * 
 * @author wcy 2016年4月22日
 *
 */
public class RoleCache {
	private static Map<Integer, Role> roleCache = new ConcurrentHashMap<>();
	private static Set<String> accountCache = new ConcurrentHashSet<>();
	private static Set<String> nameCache = new ConcurrentHashSet<>();

	public static Map<Integer, Role> getAllRole() {
		return roleCache;
	}

	public static void putRole(Role role) {
		roleCache.put(role.getId(), role);
	}

	public static Set<String> getAccountSet() {
		return accountCache;
	}

	public static Set<String> getNameSet() {
		return nameCache;
	}
}
