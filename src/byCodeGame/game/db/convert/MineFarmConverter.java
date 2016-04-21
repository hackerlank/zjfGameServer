package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.MineFarm;

public class MineFarmConverter implements ResultConverter<MineFarm> {

	public MineFarm convert(ResultSet rs) throws SQLException{
		MineFarm mineFarm = new MineFarm();
		mineFarm.setCityId(rs.getInt("cityId"));
		mineFarm.setBuildingID(rs.getInt("buildingID"));
//		mineFarm.setCrazyStarTime(rs.getInt("crazyStarTime"));
//		mineFarm.setMapKey(rs.getInt("mapKey"));
		mineFarm.setRoleId(rs.getInt("roleId"));
		mineFarm.setStarTime(rs.getInt("starTime"));
		mineFarm.setWorldArmyId(rs.getString("worldArmyId"));
//		mineFarm.setResourceType(rs.getInt("resourceType"));
		mineFarm.setDoubleP(rs.getString("doubleP"));
		return mineFarm;
	}
}
