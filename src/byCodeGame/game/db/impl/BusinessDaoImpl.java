package byCodeGame.game.db.impl;

import java.sql.Connection;

import byCodeGame.game.db.dao.BusinessDao;
import byCodeGame.game.entity.bo.Business;

public class BusinessDaoImpl implements BusinessDao {
	@Override
	public Business getBusinessByRoleId(int roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertBusinessNotCloseConnection(Business business, Connection conn) {
		// TODO Auto-generated method stub
		
	}
}
