package byCodeGame.game.module.bedroom.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface BedroomService {
	/**
	 * 睡觉
	 * 
	 * @param role
	 * @param heroId
	 * @param position
	 * @return
	 */
	Message sleep(Role role, int heroId, int position);

	/**
	 * 放置床位对应位置的道具
	 * 
	 * @param role
	 * @param position
	 *            床位号
	 * @param propPosition
	 *            床位对应道具位置
	 * @param propServerId
	 *            道具id
	 * @return
	 */
	Message putBedSpaceProp(Role role, byte position, byte propPosition, int propServerId);

	/**
	 * 移除床位对应位置的道具
	 * 
	 * @param role
	 * @param position
	 * @param propPosition
	 * @return
	 */
	Message removeBedSpaceProp(Role role, byte position, byte propPosition);

}
