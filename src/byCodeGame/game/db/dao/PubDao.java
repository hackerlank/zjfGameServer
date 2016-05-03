package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Pub;

public interface PubDao {
	void insertPubNotCloseConnection(Pub pub, Connection conn);

	Pub getPubByRoleId(int roleId);

	void updatePub(Pub pub);
}
