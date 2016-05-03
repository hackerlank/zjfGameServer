package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.db.convert.base.ResultConverter;
import byCodeGame.game.entity.bo.Bin;
import byCodeGame.game.entity.bo.Farm;
import byCodeGame.game.entity.bo.Pub;

public class BinConverter implements ResultConverter<Bin> {

	@Override
	public Bin convert(ResultSet rs) throws SQLException {
		Bin bin = new Bin();
		bin.setRoleId(rs.getInt("roleId"));
		bin.setBinSpaceStr(rs.getString("binSpaceStr"));

		return bin;
	}

}