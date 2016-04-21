package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Target;

public interface TargetDao {
	public void insertTarget(Target target,Connection conn);
	
	public void updateTarget(Target target);
	
	public Target getiTargetById(int id);
}
