package byCodeGame.game.db.dao;

import java.sql.Connection;
import java.util.List;

import byCodeGame.game.entity.bo.Role;

public interface RoleDao {
	public Role getRoleByAccount(String account);

	public Role insertRoleNotCloseConnection(Role role, Connection conn);

	void updateRole(Role role);

	public List<String> getAllAccount();

	public List<String> getAllName();
}
