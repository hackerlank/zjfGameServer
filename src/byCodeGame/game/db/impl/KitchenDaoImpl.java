package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.access.DataAccessException;
import byCodeGame.game.db.convert.KitchenConverter;
import byCodeGame.game.db.convert.RoleConverter;
import byCodeGame.game.db.convert.base.IntegerConverter;
import byCodeGame.game.db.dao.KitchenDao;
import byCodeGame.game.entity.bo.Kitchen;
import byCodeGame.game.entity.bo.Role;

public class KitchenDaoImpl extends DataAccess implements KitchenDao {

	private final String insertSql = "insert into kitchen values(?,?)";
	private final String selectSql = "select * from kitchen where roleId=? limit 1";
	private final String updateSql = "update kitchen set kitchenSpaceStr=? where roleId=? limit 1";

	private IntegerConverter integerConverter;

	public void setIntegerConverter(IntegerConverter integerConverter) {
		this.integerConverter = integerConverter;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private KitchenConverter kitchenConverter;

	public void setKitchenConverter(KitchenConverter kitchenConverter) {
		this.kitchenConverter = kitchenConverter;
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
		try {
			Connection conn = dataSource.getConnection();
			Kitchen kitchen = this.queryForObject(selectSql, kitchenConverter, conn, roleId);
			return kitchen;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void updateKitchen(Kitchen kitchen) {
		try {
			Connection conn = dataSource.getConnection();
			this.update(updateSql, conn, kitchen.getRoleId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
