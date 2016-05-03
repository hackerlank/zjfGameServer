package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Farm;

public interface FarmDao {
	void insertFarmNotCloseConnection(Farm farm, Connection conn);

	Farm getFarmByRoleId(int roleId);

	void updateFarm(Farm farm);
}
