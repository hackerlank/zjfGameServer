package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Tasks;

public interface TasksDao {

	public void insertTasks(Tasks tasks , Connection conn);
	
	public void updateTasks(Tasks tasks);
	
	public Tasks getTasks(int roleId);
}
