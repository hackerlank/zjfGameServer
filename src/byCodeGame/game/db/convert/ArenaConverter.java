package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Arena;


public class ArenaConverter implements ResultConverter<Arena> {

	public Arena convert(ResultSet rs) throws SQLException{
		Arena arena = new Arena();
//		arena.setId(rs.getInt("id"));
//		arena.setLv1(rs.getString("lv1"));
//		arena.setLv2(rs.getString("lv2"));
//		arena.setLv3(rs.getString("lv3"));
//		arena.setLv4(rs.getString("lv4"));
//		arena.setLv5(rs.getString("lv5"));
//		arena.setLv6(rs.getString("lv6"));
		return arena;
	}
	
}
