package byCodeGame.game.module.role.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface RoleService {

	/**
	 * 用roleId获得玩家
	 * 
	 * @param roleId
	 * @return
	 */
	Role getRoleById(int roleId);

	/**
	 * 
	 * @param account
	 * @return
	 */
	Role getRoleByAccount(String account);

}
