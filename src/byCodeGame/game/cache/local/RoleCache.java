package byCodeGame.game.cache.local;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Role;

/**
 * 角色数据缓存
 * @author Lenovo
 *
 */
public class RoleCache {
	private static ConcurrentMap<Integer, Role> roleMap = new ConcurrentHashMap<Integer, Role>();
	private static ConcurrentMap<String, Role> roleNameMap = new ConcurrentHashMap<String, Role>();
	private static ConcurrentMap<String, Role> roleAccountMap = new ConcurrentHashMap<String, Role>();
	/** key:玩家姓名    value:roleId	 */
	private static Map<String, Integer> nameIdMap = new HashMap<String, Integer>();
	/** 洛阳玩家集合(新手玩家)	 */
	private static ConcurrentMap<Integer, Role> luoMap = new ConcurrentHashMap<Integer, Role>();
	/** 蜀国玩家集合				 */
	private static ConcurrentMap<Integer, Role> shuMap = new ConcurrentHashMap<Integer, Role>();
	/** 魏国玩家集合				 */
	private static ConcurrentMap<Integer, Role> weiMap = new ConcurrentHashMap<Integer, Role>();
	/** 吴国玩家集合				 */
	private static ConcurrentMap<Integer, Role> wuMap = new ConcurrentHashMap<Integer, Role>();
	/** 姓名集合 */
	private static Set<String> nameSet = new HashSet<>();
	/** 登录账号集合 */
	private static Set<String> accountSet = new HashSet<>();
	
	
	/**
	 * 根据id获取角色缓存
	 * @param id
	 * @return
	 */
	public static Role getRoleById(int id){
		return getRoleMap().get(id);
	}
	
	public static Role getRoleBySession(IoSession ioSession){
		try {
			int roleId = (int)ioSession.getAttribute("roleId");
			return getRoleMap().get(roleId);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 更具名字获取角色缓存
	 * @param name
	 * @return
	 */
	public static Role getRoleByName(String name){
		if(name != null )
		{
			return getRoleNameMap().get(name);
		}
		return null;
	}
	
	/**
	 * 更具账号获取角色缓存
	 * @param name
	 * @return
	 */
	public static Role getRoleByAccount(String account){
		return roleAccountMap.get(account);
	}
	
	/**
	 * 讲角色放入缓存
	 * @param role
	 */
	public static void putRole(Role role){
		getRoleMap().put(role.getId(), role);
		getRoleNameMap().put(role.getName(), role);
		roleAccountMap.put(role.getAccount(), role);
		nameIdMap.put(role.getName(), role.getId());
		if(role.getNation() == (byte)1)
		{
			getShuMap().put(role.getId(), role);
		}else if (role.getNation() == (byte)2) {
			getWeiMap().put(role.getId(), role);
		}else if (role.getNation() == (byte)3) {
			getWuMap().put(role.getId(), role);
		}
	}
	
	/**
	 * 将国籍放入缓存 
	 * @param role
	 */
	public static void putRoleNation(Role role){
		if(role.getNation() == 0){
			getLuoMap().put(role.getId(), role);
		}else if(role.getNation() == 1)
		{
			getShuMap().put(role.getId(), role);
		}else if (role.getNation() == 2) {
			getWeiMap().put(role.getId(), role);
		}else if (role.getId() == 3) {
			getWuMap().put(role.getId(), role);
		}
	}
	
	public static  ConcurrentMap<Integer, Role> getRoleMapByNation(byte nation){
		ConcurrentMap<Integer, Role> map = null;
		if(nation == 1){
			map = getShuMap();
		}else if(nation == 2){
			map = getWeiMap();
		}else if(nation == 3){
			map = getWuMap();
		}else{
			map = getLuoMap();
		}
		return map;
	}

	
	
	public static Set<String> getNameSet() {
		return nameSet;
	}

	public static void setNameSet(Set<String> nameSet) {
		RoleCache.nameSet = nameSet;
	}

	public static Set<String> getAccountSet() {
		return accountSet;
	}

	public static void setAccountSet(Set<String> accountSet) {
		RoleCache.accountSet = accountSet;
	}

	public static void test(){
		System.out.println(123);
	}

	public static ConcurrentMap<Integer, Role> getRoleMap() {
		return roleMap;
	}

	public static Map<String, Integer> getNameIdMap() {
		return nameIdMap;
	}

	public static void setNameIdMap(Map<String, Integer> nameIdMap) {
		RoleCache.nameIdMap = nameIdMap;
	}

	public static ConcurrentMap<String, Role> getRoleNameMap() {
		return roleNameMap;
	}

	public static void setRoleNameMap(ConcurrentMap<String, Role> roleNameMap) {
		RoleCache.roleNameMap = roleNameMap;
	}

	public static ConcurrentMap<Integer, Role> getShuMap() {
		return shuMap;
	}

	public static void setShuMap(ConcurrentMap<Integer, Role> shuMap) {
		RoleCache.shuMap = shuMap;
	}

	public static ConcurrentMap<Integer, Role> getWeiMap() {
		return weiMap;
	}

	public static void setWeiMap(ConcurrentMap<Integer, Role> weiMap) {
		RoleCache.weiMap = weiMap;
	}

	public static ConcurrentMap<Integer, Role> getWuMap() {
		return wuMap;
	}

	public static void setWuMap(ConcurrentMap<Integer, Role> wuMap) {
		RoleCache.wuMap = wuMap;
	}

	public static ConcurrentMap<Integer, Role> getLuoMap() {
		return luoMap;
	}

	public static void setLuoMap(ConcurrentMap<Integer, Role> luoMap) {
		RoleCache.luoMap = luoMap;
	}
}
