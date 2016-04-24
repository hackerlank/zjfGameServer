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
	private static Map<String, Role> accountMap = new ConcurrentHashMap<>();

	public static Map<Integer, Role> getAllRole() {
		return roleCache;
	}

	public static void putRole(Role role) {
		String name = role.getName();
		String account = role.getAccount();
		roleCache.put(role.getId(), role);
		accountMap.put(account, role);
	}

	public static Set<String> getAccountSet() {
		return accountCache;
	}

	public static Set<String> getNameSet() {
		return nameCache;
	}

	public static Role getRoleByAccount(String account) {
		return accountMap.get(account);
	}
}
