package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.db.convert.base.ResultConverter;
import byCodeGame.game.entity.bo.Hero;

public class HeroConverter implements ResultConverter<Hero>{

	@Override
	public Hero convert(ResultSet rs) throws SQLException {
		Hero hero = new Hero();
		hero.setId(rs.getInt("id"));
		hero.setHeroId(rs.getInt("heroId"));
		hero.setRoleId(rs.getInt("roleId"));
		hero.setAge(rs.getInt("age"));
		hero.setAgeId(rs.getInt("ageId"));
		hero.setEffective(rs.getInt("effective"));
		hero.setEmotion(rs.getInt("emotion"));
		hero.setTalentJobId(rs.getInt("talentJobId"));
		hero.setTalentLv(rs.getByte("talentLv"));
		hero.setHungry(rs.getInt("hungry"));
		hero.setHotspot(rs.getInt("hotspot"));
		hero.setRebirth(rs.getInt("rebirth"));
		hero.setSkillId(rs.getInt("skillId"));
		hero.setTired(rs.getInt("tired"));
		hero.setRealize(rs.getInt("realize"));
		hero.setLoveJobId(rs.getInt("loveJobId"));
		return hero;
	}

}
