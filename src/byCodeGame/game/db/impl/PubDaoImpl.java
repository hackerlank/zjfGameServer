package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.access.DataAccessException;
import byCodeGame.game.db.convert.PubConverter;
import byCodeGame.game.db.convert.base.IntegerConverter;
import byCodeGame.game.db.dao.PubDao;
import byCodeGame.game.entity.bo.Pub;

public class PubDaoImpl extends DataAccess implements PubDao {

	private final String insertSql = "insert into pub values(?,?,?)";
	private final String selectSql = "select * from pub where roleId=? limit 1";
	private final String updateSql = "update pub set pubSpaceStr=? where roleId=? limit 1";

	private IntegerConverter integerConverter;

	public void setIntegerConverter(IntegerConverter integerConverter) {
		this.integerConverter = integerConverter;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private PubConverter pubConverter;

	public void setPubConverter(PubConverter pubConverter) {
		this.pubConverter = pubConverter;
	}

	@Override
	public void insertPubNotCloseConnection(Pub pub, Connection conn) {
		try {
			this.insertNotCloseConn(insertSql, integerConverter, conn, pub.getRoleId(),pub.getPubSpaceStr());
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Pub getPubByRoleId(int roleId) {
		try {
			Connection conn = dataSource.getConnection();
			Pub Pub = this.queryForObject(selectSql, pubConverter, conn, roleId);
			return Pub;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void updatePub(Pub Pub) {
		try {
			Connection conn = dataSource.getConnection();
			this.update(updateSql, conn, Pub.getRoleId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
