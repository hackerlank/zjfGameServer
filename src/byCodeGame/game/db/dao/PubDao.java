package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Pub;

public interface PubDao {
	
	public void insertPub(Pub pub,Connection conn);
	
	public void updatePub(Pub pub);
	
	public Pub getPubByRoleId(int roleId);
}
