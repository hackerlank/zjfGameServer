package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Target;

public class TargetConverter implements ResultConverter<Target>{
	public Target convert(ResultSet rs) throws SQLException {
		Target target = new Target();
		target.setRoleId(rs.getInt("roleId"));
		target.setStage(rs.getByte("stage"));
		target.setAllTarget(rs.getString("allTarget"));
		target.setFirstRecharge(rs.getByte("firstRecharge"));
		return target;
	}
}
