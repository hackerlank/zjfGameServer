package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.db.convert.base.ResultConverter;
import byCodeGame.game.entity.bo.Hero;

public class HeroConverter implements ResultConverter<Hero>{

	@Override
	public Hero convert(ResultSet rs) throws SQLException {
		Hero hero = new Hero();
		hero.setHeroId(rs.getInt("heroId"));
		hero.setRoleId(rs.getInt("roleId"));
		hero.setAgeCount(rs.getInt("ageCount"));
		hero.setAgeId(rs.getInt("ageId"));
		hero.setEffective(rs.getInt("effective"));
		hero.setEmotion(rs.getInt("emotion"));
		hero.setGiftId(rs.getInt("giftId"));
		hero.setHungry(rs.getInt("hungry"));
		hero.setHotspot(rs.getInt("hotspot"));
		hero.setRebirth(rs.getInt("rebirth"));
		hero.setSkillId(rs.getInt("skillId"));
		hero.setTired(rs.getInt("tired"));
		return hero;
	}

}
