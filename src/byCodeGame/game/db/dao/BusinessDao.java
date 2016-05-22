package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Business;

public interface BusinessDao {
	public Business getBusinessByRoleId(int roleId);
	
	public void insertBusinessNotCloseConnection(Business business,Connection conn);
}
