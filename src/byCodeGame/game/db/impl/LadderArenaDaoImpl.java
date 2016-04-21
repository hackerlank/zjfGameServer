package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.LadderArenaConverter;
import byCodeGame.game.db.dao.LadderArenaDao;
import byCodeGame.game.entity.bo.LadderArena;
import byCodeGame.game.entity.bo.Role;

public class LadderArenaDaoImpl extends DataAccess implements LadderArenaDao {

	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insterLadderArena(Connection conn) {
		final String sql = "insert into ladderarena(rank,roleId)"
				+ "values(?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter() , conn,null,-1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insterLadderArenaById(Connection conn,Role role) {
		final String sql = "insert into ladderarena(rank,roleId)"
				+ "values(?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter() , conn,null,role.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateLadderArena(LadderArena ladderArena) {
		final String sql = "update ladderarena set roleId=? where rank=? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, ladderArena.getRoleId(),ladderArena.getRank());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<LadderArena> getAllLadderArena() {
		String sql = "select * from ladderarena ";
		try {
			Connection conn = dataSource.getConnection();
			List<LadderArena> result = super.queryForList(sql, new LadderArenaConverter(),
					conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
