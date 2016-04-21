package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Pub;

public class PubConverter implements ResultConverter<Pub> {
	@Override
	public Pub convert(ResultSet rs) throws SQLException {
		Pub pub = new Pub();
		pub.setRoleId(rs.getInt("roleId"));
		pub.setTalksNumber(rs.getInt("talksNumber"));
		pub.setMapInfo(rs.getString("mapInfo"));
		pub.setTalkMile(rs.getInt("talkMile"));
		pub.setRecruitHeroNum(rs.getShort("recruitHeroNum"));
		pub.setTotalTalksNumber(rs.getInt("totalTalksNumber"));
		pub.setTotalTalksNumber2(rs.getInt("totalTalksNumber2"));
		pub.setFreeMoneyStartTalkTime(rs.getInt("freeMoneyStartTalkTime"));
		pub.setFreeGoldStartTalkTime(rs.getInt("freeGoldStartTalkTime"));
		pub.setHeroMoney(rs.getString("heroMoney"));
		pub.setHeroGold(rs.getString("heroGold"));
		pub.setVisitData(rs.getString("visitData"));
		pub.setDesk(rs.getString("desk"));
		pub.setMissHero(rs.getInt("missHero"));
		return pub;
	}
}
