package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Friend;

public class FriendConverter implements ResultConverter<Friend> {
	
	public Friend convert(ResultSet rs) throws SQLException {
		Friend friend = new Friend();
		friend.setRoleId(rs.getInt("roleId"));
		friend.setFriendList(rs.getString("friendList"));
		friend.setBlackList(rs.getString("blackList"));
		
		return friend;
	}
}
