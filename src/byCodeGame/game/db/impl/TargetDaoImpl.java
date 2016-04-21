package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.TargetConverter;
import byCodeGame.game.db.dao.TargetDao;
import byCodeGame.game.entity.bo.Target;

public class TargetDaoImpl extends DataAccess implements TargetDao{
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public void insertTarget(Target target ,Connection conn) {
		final String sql = "insert into target(roleId,stage,allTarget,firstRecharge)"
				+ "values(?,?,?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter() , conn,target.getRoleId(),target.getStage()
					,target.getAllTarget(),target.getFirstRecharge());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void updateTarget(Target target) {
		final String sql = "update target set allTarget=?,stage=?,firstRecharge=?"
				+ " where roleId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,target.getAllTarget(),target.getStage(),target.getFirstRecharge(),
					target.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Target getiTargetById(int id) {
		final String sql = "select * from target where roleId=? ";
		try {
			Connection conn = dataSource.getConnection();
			Target result = super.queryForObject(sql, new TargetConverter(), conn, id);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
