package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.db.convert.base.ResultConverter;
import byCodeGame.game.entity.bo.Farm;
import byCodeGame.game.entity.bo.Pub;

public class FarmConverter implements ResultConverter<Farm> {

	@Override
	public Farm convert(ResultSet rs) throws SQLException {
		Farm farm = new Farm();
		farm.setRoleId(rs.getInt("roleId"));
		farm.setFarmSpaceStr(rs.getString("farmSpaceStr"));
		
		return farm;
	}

}