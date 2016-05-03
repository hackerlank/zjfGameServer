package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.access.DataAccessException;
import byCodeGame.game.db.convert.FarmConverter;
import byCodeGame.game.db.convert.base.IntegerConverter;
import byCodeGame.game.db.dao.FarmDao;
import byCodeGame.game.entity.bo.Farm;

public class FarmDaoImpl extends DataAccess implements FarmDao {

	private final String insertSql = "insert into farm values(?,?)";
	private final String selectSql = "select * from farm where roleId=? limit 1";
	private final String updateSql = "update farm set farmSpaceStr=? where roleId=? limit 1";

	private IntegerConverter integerConverter;

	public void setIntegerConverter(IntegerConverter integerConverter) {
		this.integerConverter = integerConverter;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private FarmConverter farmConverter;

	public void setFarmConverter(FarmConverter farmConverter) {
		this.farmConverter = farmConverter;
	}

	@Override
	public void insertFarmNotCloseConnection(Farm farm, Connection conn) {
		try {
			this.insertNotCloseConn(insertSql, integerConverter, conn, farm.getRoleId());
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Farm getFarmByRoleId(int roleId) {
		try {
			Connection conn = dataSource.getConnection();
			Farm farm = this.queryForObject(selectSql, farmConverter, conn, roleId);
			return farm;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void updateFarm(Farm farm) {
		try {
			Connection conn = dataSource.getConnection();
			this.update(updateSql, conn, farm.getRoleId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
