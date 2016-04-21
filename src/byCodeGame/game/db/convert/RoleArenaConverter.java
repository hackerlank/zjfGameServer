package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.RoleArena;

public class RoleArenaConverter implements ResultConverter<RoleArena> {

	public RoleArena convert(ResultSet rs) throws SQLException{
		RoleArena roleArena = new RoleArena();
		roleArena.setRoleId(rs.getInt("roleId"));
		roleArena.setExp(rs.getInt("exp"));
		roleArena.setLv(rs.getByte("lv"));
		return roleArena;
	}
}
