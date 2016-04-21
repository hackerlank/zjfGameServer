package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Friend;

public interface FriendDao {
	/**
	 * 新建好友列表
	 * @param friend
	 * @return
	 * @author xjd
	 */
	public void insertFriend(Friend friend , Connection conn);
	
	/**
	 * 更新好友列表
	 * @param friend
	 * @author xjd
	 */
	public void updateFriend(Friend friend);
	
	public Friend getFriendByRoleId(int roleId); 
}
