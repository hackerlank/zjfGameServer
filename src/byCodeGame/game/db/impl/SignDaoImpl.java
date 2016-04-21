package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.SignConverter;
import byCodeGame.game.db.dao.SignDao;
import byCodeGame.game.entity.bo.Sign;

public class SignDaoImpl extends DataAccess implements SignDao {
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insertSign(Sign sign, Connection conn) {
		final String sql = "insert into sign(roleId,signMonth,signDays,signAward,signRetroactive)"
				+ "values(?,?,?,?,?)";
		
		try {
			super.insertNotCloseConn(sql, new IntegerConverter() , conn,sign.getRoleId(),sign.getSignMonth(),sign.getSignDays(),
					sign.getSignAward(),sign.getSignRetroactive());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateSign(Sign sign) {
		final String sql = "update sign set signMonth=?,signDays=?,signAward=?,signRetroactive=?"
				+ " where roleId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,sign.getSignMonth(),sign.getSignDays(),sign.getSignAward(),sign.getSignRetroactive(),sign.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Sign getSignRoleId(int roleId) {
		String sql = "select * from sign where roleId=?";
		try {
			Connection conn = dataSource.getConnection();
			Sign result = super.queryForObject(sql, new SignConverter(), conn, roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
