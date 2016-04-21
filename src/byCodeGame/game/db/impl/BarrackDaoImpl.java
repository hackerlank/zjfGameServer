package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.BarrackConverter;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.dao.BarrackDao;
import byCodeGame.game.entity.bo.Barrack;

public class BarrackDaoImpl extends DataAccess implements BarrackDao {

	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insertBarrack(Barrack barrack, Connection conn) {
		final String sql = "insert into barrack(roleId,troops,queue,buildLv,armySkillLv)"
				+ "values(?,?,?,?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter() , conn,barrack.getRoleId(),barrack.getTroops(),
					barrack.getQueue(),barrack.getBuildLv(),barrack.getArmySkillLv());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updataBarrack(Barrack barrack) {
		final String sql = "update barrack set troops=?,queue=?,buildLv=?,armySkillLv=?"
				+ " where roleId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,barrack.getTroops(),barrack.getQueue(),barrack.getBuildLv(),barrack.getArmySkillLv(),barrack.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Barrack getBarrack(int id) {
		final String sql = "select * from barrack where roleId=? ";
		try {
			Connection conn = dataSource.getConnection();
			Barrack result = super.queryForObject(sql, new BarrackConverter() , conn, id);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
