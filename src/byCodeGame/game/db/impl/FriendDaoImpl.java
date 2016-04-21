package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.FriendConverter;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.dao.FriendDao;
import byCodeGame.game.entity.bo.Friend;

public class FriendDaoImpl extends DataAccess implements FriendDao{
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void insertFriend(Friend friend,Connection conn) {
		final String sql = "insert into friend(roleId,friendList,blackList)"
				+ "values(?,?,?)";
		
		try {
			super.insertNotCloseConn(sql, new IntegerConverter() , conn,friend.getRoleId(),friend.getFriendList(),friend.getBlackList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateFriend(Friend friend) {
		final String sql = "update friend set friendList=?,blackList=?"
				+ " where roleId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,friend.getFriendList(),friend.getBlackList(),friend.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Friend getFriendByRoleId(int roleId) {
		String sql = "select * from friend where roleId=?";
		try {
			Connection conn = dataSource.getConnection();
			Friend result = super.queryForObject(sql, new FriendConverter(), conn, roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
