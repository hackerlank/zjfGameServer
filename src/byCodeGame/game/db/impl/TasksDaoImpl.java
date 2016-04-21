package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.TaskConverter;
import byCodeGame.game.db.dao.TasksDao;
import byCodeGame.game.entity.bo.Tasks;

public class TasksDaoImpl extends DataAccess implements TasksDao{

	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void insertTasks(Tasks tasks,Connection conn){
		final String sql = "insert into tasks(roleId,doingTask)"
				+ "values(?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter() , conn,tasks.getRoleId(),tasks.getDoingTask());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateTasks(Tasks tasks){
		final String sql = "update tasks set doingTask=?"
				+ " where roleId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,tasks.getDoingTask(),tasks.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Tasks getTasks(int roleId){
		final String sql = "select * from tasks where roleId=? ";
		try {
			Connection conn = dataSource.getConnection();
			Tasks result = super.queryForObject(sql, new TaskConverter(), conn, roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
