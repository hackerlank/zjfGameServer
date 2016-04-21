package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StringConverter implements ResultConverter<String> {
	public String convert(ResultSet rs) throws SQLException {
		return rs.getString(1);
	}
}
