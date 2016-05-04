package byCodeGame.game.module.farm.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

/**
 * 农场服务
 * 
 * @author AIM
 *
 */
public interface FarmSerivce {
	/**
	 * 
	 * @param role
	 * @param position
	 * @param propPosition
	 * @return
	 */
	Message putFarmSpaceProp(Role role, byte position, byte propPosition,int propServerId);

	/**
	 * 
	 * @param role
	 * @param position
	 * @param propPosition
	 * @return
	 */
	Message removeFarmSpaceProp(Role role, byte position, byte propPosition);
}
