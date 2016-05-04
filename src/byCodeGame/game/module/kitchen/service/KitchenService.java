package byCodeGame.game.module.kitchen.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;
/**
 * 
 * @author AIM
 *
 */
public interface KitchenService {
	/**
	 * 料理
	 * @param role
	 * @param position
	 * @return
	 */
	Message cooking(Role role, byte position);

	Message putKitchenSpaceProp(Role role, byte position, byte propPosition, int propServerId);

	Message removeKitchenSpaceProp(Role role, byte position, byte propPosition);

}
