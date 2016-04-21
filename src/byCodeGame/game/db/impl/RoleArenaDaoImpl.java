package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.RoleArenaConverter;
import byCodeGame.game.db.dao.RoleArenaDao;
import byCodeGame.game.entity.bo.RoleArena;

public class RoleArenaDaoImpl extends DataAccess implements RoleArenaDao {

	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insterRoleArena(RoleArena roleArena,Connection conn) {
		final String sql = "insert into roleArena(roleId,exp,lv)"
				+ "values(?,?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter() , conn,
					roleArena.getRoleId(),roleArena.getExp(),roleArena.getLv());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateRoleArena(RoleArena roleArena) {
		final String sql = "update roleArena set exp=?,lv=?,heroId=? where roleId=? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,roleArena.getExp(),roleArena.getLv(),roleArena.getHeroId()
					,roleArena.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public RoleArena getRoleArena(int roleId) {
		final String sql = "select * from roleArena where roleId=1 ";
		try {
			Connection conn = dataSource.getConnection();
			RoleArena result = super.queryForObject(sql, new RoleArenaConverter() , conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
