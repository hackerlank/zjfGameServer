package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.City;

public class CityConverter implements ResultConverter<City> {

	public City convert(ResultSet rs) throws SQLException{
		City city = new City();
		city.setCityId(rs.getInt("cityId"));
		city.setRoleIds(rs.getString("roleIds"));
		city.setCityOw(rs.getInt("cityOw"));
		city.setNation(rs.getByte("nation"));
		city.setLegionId(rs.getInt("legionId"));
		city.setDefInfo(rs.getString("defInfo"));
		city.setLazyInfo(rs.getString("lazyInfo"));
		city.setCoreDefInfo(rs.getString("coreDefInfo"));
		city.setContribute(rs.getString("contribute"));
		return city;
	}
}
