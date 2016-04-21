package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Build;

public interface BuildDao {

	public void insertBuild(Build build,Connection conn);
	
	public void updateBuild(Build build);
	
	public Build getBuild(int roleId);
	
}
