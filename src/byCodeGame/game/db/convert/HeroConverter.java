package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Hero;

public class HeroConverter implements ResultConverter<Hero> {

	public Hero convert(ResultSet rs) throws SQLException {
		Hero hero = new Hero();
		hero.setRoleId(rs.getInt("roleId"));
		hero.setHeroId(rs.getInt("heroId"));
		hero.setTroopsConfig(rs.getString("troopsConfig"));
		hero.setLv(rs.getShort("lv"));
		hero.setExp(rs.getInt("exp"));
		hero.setRebirthLv(rs.getShort("rebirthLv"));
		hero.setStatus(rs.getByte("status"));
		hero.setRank(rs.getInt("rank"));
		hero.setArmyInfo(rs.getString("armyInfo"));
		hero.setUseArmyId(rs.getInt("useArmyId"));
		hero.setEmotion(rs.getInt("emotion"));
		hero.setMaxArmyQuality(rs.getInt("maxArmyQuality"));
		hero.setSkillId(rs.getInt("skillId"));
		hero.setSkillLv(rs.getInt("skillLv"));
		hero.setSkillBD(rs.getInt("skillBD"));
		hero.setSkillBDLv(rs.getInt("skillBDLv"));
		hero.setEmotionLv(rs.getInt("emotionLv"));
		hero.setBulidId(rs.getByte("bulidId"));
		hero.setInsteadArmy(rs.getString("insteadArmy"));
		hero.setUsingArmy(rs.getString("usingArmy"));
		hero.setDutyLv(rs.getInt("dutyLv"));
		hero.setManual(rs.getInt("manual"));
		hero.setLastGetMa(rs.getInt("lastGetMa"));
		hero.setTired(rs.getInt("tired"));
		return hero;
	}
}
