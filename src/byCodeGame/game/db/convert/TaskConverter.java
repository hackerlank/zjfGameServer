package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Tasks;

public class TaskConverter implements ResultConverter<Tasks> {

	public Tasks convert(ResultSet rs) throws SQLException{
		Tasks tasks = new Tasks();
		tasks.setRoleId(rs.getInt("roleId"));
		tasks.setDoingTask(rs.getString("doingTask"));
//		tasks.setCompleteTask(rs.getString("completeTask"));
		return tasks;
	}
}
