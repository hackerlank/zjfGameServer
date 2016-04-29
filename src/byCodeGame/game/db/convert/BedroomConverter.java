package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.db.convert.base.ResultConverter;
import byCodeGame.game.entity.bo.Bedroom;

public class BedroomConverter implements ResultConverter<Bedroom>{

	@Override
	public Bedroom convert(ResultSet rs) throws SQLException {
		Bedroom bedroom = new Bedroom();
		bedroom.setRoleId(rs.getInt("roleId"));
		bedroom.setBedSpaceStr(rs.getString("bedSpace"));
		return bedroom;
	}

}
