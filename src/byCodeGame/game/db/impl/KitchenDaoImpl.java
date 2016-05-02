package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.access.DataAccessException;
import byCodeGame.game.db.convert.base.IntegerConverter;
import byCodeGame.game.db.dao.KitchenDao;
import byCodeGame.game.entity.bo.Kitchen;

public class KitchenDaoImpl extends DataAccess implements KitchenDao {

	private final String insertSql = "insert into kitchen values(?)";
	private final String selectSql = "select * from kitchen where roleId=? limit 1";

	private IntegerConverter integerConverter;

	public void setIntegerConverter(IntegerConverter integerConverter) {
		this.integerConverter = integerConverter;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insertKitchenNotCloseConnection(Kitchen kitchen, Connection conn) {
		try {
			this.insertNotCloseConn(insertSql, integerConverter, conn, kitchen.getRoleId());
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Kitchen getKitchenByRoleId(int roleId) {
		return null;
	}

	@Override
	public void updateKitchen(Kitchen kitchen) {
		// TODO Auto-generated method stub

	}

}
