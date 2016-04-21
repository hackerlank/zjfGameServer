package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Build;

public class BuildConverter implements ResultConverter<Build> {

	public Build convert(ResultSet rs) throws SQLException{
		Build build = new Build();
		
		build.setRoleId(rs.getInt("roleId"));
		build.setFarmLv(rs.getString("farmlv"));
//		build.setFarmLastIncomeTime(rs.getLong("farmLastIncomeTime"));
//		build.setFarmInComeCache(rs.getInt("farmInComeCache"));
		build.setHouseLv(rs.getString("houseLv"));
//		build.setHouseLastIncomeTime(rs.getLong("houseLastIncomeTime"));
//		build.setHouseInComeCache(rs.getInt("houseInComeCache"));
//		build.setBarrackLastIncomeTime(rs.getLong("barrackLastIncomeTime"));
//		build.setBarrackInComeCache(rs.getInt("barrackInComeCache"));
//		build.setBarrackLv(rs.getString("barrackLv"));
		build.setAttachHero(rs.getString("attachHero"));
		build.setLevyStr(rs.getString("levyStr"));
		build.setBuildLv(rs.getString("buildLv"));
		build.setInComeCacheStr(rs.getString("inComeCache"));
		build.setBuildLastIncomeTimeStr(rs.getString("buildLastIncomeTime"));
		build.setVisitTreasureData(rs.getString("visitTreasureData"));
		build.setVisitScienceData(rs.getString("visitScienceData"));
		build.setIncomeLastDayCacheStr(rs.getString("incomeLastDayCache"));

		return build;
	}
}
