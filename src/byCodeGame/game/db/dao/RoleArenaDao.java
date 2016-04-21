package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.RoleArena;


public interface RoleArenaDao {

	public void insterRoleArena(RoleArena roleArena,Connection conn);
	
	public void updateRoleArena(RoleArena roleArena);
	
	public RoleArena getRoleArena(int roleId);
	
}
