package byCodeGame.game.moudle.friend.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface FriendService {
	/**
	 * 获取玩家好友信息
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getFriendinfo(Role role);
	
	/**
	 * 增加好友名单
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message addFriendList(Role role, String targetName);
	
	/**
	 * 删除好友
	 * @param role
	 * @param targetId
	 * @return
	 * @author xjd
	 */
	public Message removeFriendList(Role role, String targetName);
	
	/**
	 * 增加黑名单
	 * @param role
	 * @param targetId
	 * @return
	 * @author xjd
	 */
	public Message addBlackList(Role role , String targetId);
	
	/**
	 * 删除黑名单
	 * @param role
	 * @param targetId
	 * @return
	 * @author xjd
	 */
	public Message removeBlackList(Role role , String targetId);
}
