package byCodeGame.game.module.role.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface RoleService {
	/**
	 * 喜欢英雄
	 * 
	 * @param role
	 * @param heroId
	 * @return
	 */
	Message loveHero(Role role, int heroId);

}
