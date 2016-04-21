package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.RoleArmyConverter;
import byCodeGame.game.db.dao.RoleArmyDao;
import byCodeGame.game.entity.bo.RoleArmy;

public class RoleArmyDaoImpl extends DataAccess implements RoleArmyDao{
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public void insertRoleArmy(RoleArmy roleArmy, Connection conn) {
		final String sql = "insert into rolearmy(roleId,roleArmyStr,usedPoint)"
				+ "values(?,?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter(), conn,roleArmy.getRoleId(),roleArmy.getRoleArmyStr()
					,roleArmy.getUsedPoint());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateRoleArmy(RoleArmy roleArmy) {
		final String sql = "update rolearmy set roleArmyStr = ? , usedPoint=?"
				+ " where roleId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, roleArmy.getRoleArmyStr() ,roleArmy.getUsedPoint()
					,roleArmy.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public RoleArmy getRoleArmyByRoleId(int roleId) {
		String sql = "select * from rolearmy where roleId=?";
		try {
			Connection conn = dataSource.getConnection();
			RoleArmy result = super.queryForObject(sql, new RoleArmyConverter(), conn, roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
