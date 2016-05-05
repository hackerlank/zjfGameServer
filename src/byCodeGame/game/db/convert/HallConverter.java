package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.db.convert.base.ResultConverter;
import byCodeGame.game.entity.bo.Hall;

public class HallConverter implements ResultConverter<Hall> {

	@Override
	public Hall convert(ResultSet rs) throws SQLException {
		Hall hall = new Hall();
		hall.setRoleId(rs.getInt("roleId"));
		hall.setHallSpaceStr(rs.getString("hallSpaceStr"));
		hall.setHallPhotoSpaceStr(rs.getString("hallPhotoSpaceStr"));
		
		return hall;
	}

}