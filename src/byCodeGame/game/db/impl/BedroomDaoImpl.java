package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.access.DataAccessException;
import byCodeGame.game.db.convert.BedroomConverter;
import byCodeGame.game.db.convert.base.IntegerConverter;
import byCodeGame.game.db.dao.BedroomDao;
import byCodeGame.game.entity.bo.Bedroom;

/**
 * 
 * @author wcy 2016年4月29日
 *
 */
public class BedroomDaoImpl extends DataAccess implements BedroomDao {

	private final String insertSql = "insert into bedroom values(?,?,?)";
	private final String selectByRoldIdSql = "select * from bedroom where roleId=? limit 1";
	private final String updateSql = "update bedroom set bedSpaceStr=? where roleId=? limit 1";

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private IntegerConverter integerConverter;

	public void setIntegerConverter(IntegerConverter integerConverter) {
		this.integerConverter = integerConverter;
	}

	private BedroomConverter bedroomConverter;

	public void setBedroomConverter(BedroomConverter bedroomConverter) {
		this.bedroomConverter = bedroomConverter;
	}

	@Override
	public void insertBedroomNotCloseConnection(Bedroom bedroom, Connection conn) {
		try {
			this.insertNotCloseConn(insertSql, integerConverter, conn, bedroom.getRoleId(), bedroom.getBedSpaceStr());
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Bedroom getBedroomByRoleId(int roleId) {
		try {
			Connection conn = dataSource.getConnection();
			Bedroom bedroom = this.queryForObject(selectByRoldIdSql, bedroomConverter, conn, roleId);
			return bedroom;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void updateBedroom(Bedroom bedroom) {
		try {
			Connection conn = dataSource.getConnection();
			this.update(updateSql, conn, bedroom.getBedSpaceStr(), bedroom.getRoleId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
