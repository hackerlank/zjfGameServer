package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.RoleArmy;

public class RoleArmyConverter implements ResultConverter<RoleArmy>{
	
	public RoleArmy convert(ResultSet rs) throws SQLException {
		RoleArmy roleArmy = new RoleArmy();
		
		roleArmy.setRoleId(rs.getInt("roleId"));
		roleArmy.setRoleArmyStr(rs.getString("roleArmyStr"));
		roleArmy.setUsedPoint(rs.getInt("usedPoint"));
		return roleArmy;
	}
}
