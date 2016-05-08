package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.db.convert.base.ResultConverter;
import byCodeGame.game.entity.bo.Role;

/**
 * 
 * @author wcy 2016年4月28日
 *
 */
public class RoleConverter implements ResultConverter<Role>{

	@Override
	public Role convert(ResultSet rs) throws SQLException {
		Role role = new Role();
		role.setId(rs.getInt("id"));
		role.setAccount(rs.getString("account"));
		role.setName(rs.getString("name"));
		return role;
	}

}
