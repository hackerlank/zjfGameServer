package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Hall;

public interface HallDao {
	void insertHallNotCloseConnection(Hall Hall, Connection conn);

	Hall getHallByRoleId(int roleId);

	void updateHall(Hall hall);
}
