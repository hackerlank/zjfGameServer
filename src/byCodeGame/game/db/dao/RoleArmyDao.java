package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.RoleArmy;

public interface RoleArmyDao {
	
	public void insertRoleArmy(RoleArmy roleArmy , Connection conn);
	
	public void updateRoleArmy(RoleArmy roleArmy);
	
	public RoleArmy getRoleArmyByRoleId(int roleId); 
}
