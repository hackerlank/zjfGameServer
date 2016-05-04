/**
 * 
 */
/**
 * @author AIM
 *
 */
package byCodeGame.game.module.pub.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;
/**
 * 
 * @author AIM
 *
 */
public interface PubService {
	/**
	 * 酿酒
	 * @param role
	 * @param position
	 * @return
	 */
	Message makeWine(Role role,byte position);
	
	/**
	 * 
	 * @param role
	 * @param position
	 * @param propPosition
	 * @param propServerId
	 * @return
	 */
	Message putPubSpaceProp(Role role,byte position,byte propPosition,int propServerId);
	
	/**
	 * 
	 * @param role
	 * @param positoin
	 * @param propPosition
	 * @return
	 */
	Message removePubSpaceProp(Role role,byte positoin,byte propPosition);
}

