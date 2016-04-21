package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.Key;

public class KeyConverter implements ResultConverter<Key>{

	public Key convert(ResultSet rs) throws SQLException {
		Key key = new Key();
		key.setKey(rs.getString("id"));
		key.setIsUsed(rs.getByte("isUsed"));
		key.setUseTime(rs.getInt("useTime"));
		key.setAward(rs.getString("award"));
		key.setType(rs.getByte("type"));
		return key;
	}
}
