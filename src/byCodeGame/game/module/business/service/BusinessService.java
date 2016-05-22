package byCodeGame.game.module.business.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

/**
 * 
 * @author AIM
 *
 */
public interface BusinessService {
	/**
	 * 为自己工作
	 * 
	 * @param role
	 * @param heroId
	 * @return
	 */
	public Message workForSelf(Role role, int heroId, String materials);

	/**
	 * 为别人工作
	 * 
	 * @param role
	 * @param heroId
	 * @return
	 */
	public Message workForOther(Role role, int targetRoleId, int heroId, String materials);
}
