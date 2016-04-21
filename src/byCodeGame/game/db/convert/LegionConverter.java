package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Legion;

public class LegionConverter implements ResultConverter<Legion> {

	public Legion convert(ResultSet rs) throws SQLException{
		Legion legion = new Legion();
		legion.setLegionId(rs.getInt("legionId"));
		legion.setName(rs.getString("name"));
		legion.setFaceId(rs.getInt("faceId"));
		legion.setLv(rs.getByte("lv"));
		legion.setNotice(rs.getString("notice"));
		legion.setScience(rs.getString("science"));
		legion.setMaxPeopleNum(rs.getInt("maxPeopleNum"));
		legion.setMember(rs.getString("member"));
		legion.setCaptainId(rs.getInt("captainId"));
		legion.setDeputyCaptain(rs.getString("deputyCaptain"));
		legion.setApply(rs.getString("apply"));
		legion.setAppointScience(rs.getByte("appointScience"));
		legion.setNation(rs.getByte("nation"));
		legion.setAutoArgeeType(rs.getByte("autoArgeeType"));
		legion.setMinLv(rs.getInt("minLv"));
		legion.setHistoryStr(rs.getString("history"));
		legion.setCityId(rs.getInt("cityId"));
		legion.setLastCityTime(rs.getInt("lastCityTime"));
		legion.setShortName(rs.getString("shortName"));
		return legion;
	}
}
