package byCodeGame.game.db.dao;

import byCodeGame.game.entity.bo.Role;

public interface RoleDao {
	public Role getRoleByAccount(String account);
	
	public void insertRole(Role role);
}
