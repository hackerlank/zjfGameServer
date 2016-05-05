package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.access.DataAccessException;
import byCodeGame.game.db.convert.BinConverter;
import byCodeGame.game.db.convert.base.IntegerConverter;
import byCodeGame.game.db.dao.BinDao;
import byCodeGame.game.entity.bo.Bin;

public class BinDaoImpl extends DataAccess implements BinDao {

	private final String insertSql = "insert into bin values(?,?,?)";
	private final String selectSql = "select * from bin where roleId=? limit 1";
	private final String updateSql = "update Bin set binSpaceStr=? where roleId=? limit 1";

	private IntegerConverter integerConverter;

	public void setIntegerConverter(IntegerConverter integerConverter) {
		this.integerConverter = integerConverter;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private BinConverter binConverter;

	public void setBinConverter(BinConverter binConverter) {
		this.binConverter = binConverter;
	}

	@Override
	public void insertBinNotCloseConnection(Bin bin, Connection conn) {
		try {
			this.insertNotCloseConn(insertSql, integerConverter, conn, bin.getRoleId(),bin.getBinSpaceStr());
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Bin getBinByRoleId(int roleId) {
		try {
			Connection conn = dataSource.getConnection();
			Bin bin = this.queryForObject(selectSql, binConverter, conn, roleId);
			return bin;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void updateBin(Bin bin) {
		try {
			Connection conn = dataSource.getConnection();
			this.update(updateSql, conn, bin.getRoleId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
