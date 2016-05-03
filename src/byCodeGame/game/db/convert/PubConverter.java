package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.db.convert.base.ResultConverter;
import byCodeGame.game.entity.bo.Pub;

public class PubConverter implements ResultConverter<Pub> {

	@Override
	public Pub convert(ResultSet rs) throws SQLException {
		Pub pub = new Pub();
		pub.setRoleId(rs.getInt("roleId"));
		pub.setPubSpaceStr(rs.getString("pubSpaceStr"));
		
		return pub;
	}

}