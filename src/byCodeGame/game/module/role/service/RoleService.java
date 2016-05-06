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

	/**
	 * 设置昵称
	 * 
	 * @param role
	 * @param nickname
	 * @return
	 * @author wcy 2016年5月6日
	 */
	Message setNickname(Role role, String nickname);

}
