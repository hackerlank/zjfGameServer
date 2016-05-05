package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.access.DataAccessException;
import byCodeGame.game.db.convert.HallConverter;
import byCodeGame.game.db.convert.base.IntegerConverter;
import byCodeGame.game.db.dao.HallDao;
import byCodeGame.game.entity.bo.Hall;

public class HallDaoImpl extends DataAccess implements HallDao {

	private final String insertSql = "insert into hall values(?,?,?,?)";
	private final String selectSql = "select * from hall where roleId=? limit 1";
	private final String updateSql = "update Hall set hallSpaceStr=?,hallPhotoSpaceStr=? where roleId=? limit 1";

	private IntegerConverter integerConverter;

	public void setIntegerConverter(IntegerConverter integerConverter) {
		this.integerConverter = integerConverter;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private HallConverter hallConverter;

	public void setHallConverter(HallConverter hallConverter) {
		this.hallConverter = hallConverter;
	}

	@Override
	public void insertHallNotCloseConnection(Hall hall, Connection conn) {
		try {
			this.insertNotCloseConn(insertSql, integerConverter, conn, hall.getRoleId(), hall.getHallSpaceStr(),
					hall.getHallPhotoSpaceStr());
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Hall getHallByRoleId(int roleId) {
		try {
			Connection conn = dataSource.getConnection();
			Hall hall = this.queryForObject(selectSql, hallConverter, conn, roleId);
			return hall;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void updateHall(Hall hall) {
		try {
			Connection conn = dataSource.getConnection();
			this.update(updateSql, conn, hall.getRoleId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
