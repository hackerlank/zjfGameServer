package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Bedroom;

public interface BedroomDao {
	void insertBedroomNotCloseConnection(Bedroom bedroom,Connection conn);
	
	Bedroom getBedroomByRoleId(int roleId);
	
	void updateBedroom(Bedroom bedroom);
}
