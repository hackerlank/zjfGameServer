package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.RoleCity;

public class RoleCityConverter implements ResultConverter<RoleCity> {

	public RoleCity convert(ResultSet rs) throws SQLException{
		RoleCity roleCity = new RoleCity();
		roleCity.setRoleId(rs.getInt("roleId"));
		roleCity.setCityId(rs.getInt("cityId"));
		roleCity.setMapKey(rs.getInt("mapKey"));
		roleCity.setMyLordRoleId(rs.getInt("myLordRoleId"));
		roleCity.setVassal(rs.getString("vassal"));
		return roleCity;
	}
}
