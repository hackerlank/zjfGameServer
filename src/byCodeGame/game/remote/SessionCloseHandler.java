package byCodeGame.game.remote;

import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.entity.bo.Role;



/**
 * session关闭角色数据处理
 * 
 */
public class SessionCloseHandler {

	/**
	 * 移除session缓存
	 * @param id
	 */
	public static void manipulate(Role role) {
		updateRoleData(role);
		SessionCache.removeSessionById(role.getId());
	}

	// 更新角色数据
	public static void updateRoleData(Role role) {
		//设置用户下线时间
		
	}
}
