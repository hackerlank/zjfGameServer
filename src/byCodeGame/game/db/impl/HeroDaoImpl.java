package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.access.DataAccessException;
import byCodeGame.game.db.convert.HeroConverter;
import byCodeGame.game.db.convert.base.IntegerConverter;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.entity.bo.Hero;

public class HeroDaoImpl extends DataAccess implements HeroDao {

	private final String insertSql = "insert into hero values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String selectIdSql = "select * from hero where id=?";
	private final String updateSql = "update set roleId=?,emotion=?,hungry=?,tired=?,effective=?,skillId=?,"
			+ "talentJobId=?,talentLv=?,realize=?,ageId=?,age=?,rebirth=?,hotspot=?,loveJobId=? where id=? limit 1";

	private IntegerConverter integerConverter;

	public void setIntegerConverter(IntegerConverter integerConverter) {
		this.integerConverter = integerConverter;
	}

	private HeroConverter heroConverter;

	public void setHeroConverter(HeroConverter heroConverter) {
		this.heroConverter = heroConverter;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Hero> getHerosByRoleId(int roleId) {
		try {
			Connection conn = dataSource.getConnection();

			List<Hero> heroList = this.queryForList(selectIdSql, heroConverter, conn, roleId);
			return heroList;
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Hero insertHero(Hero hero) {
		try {
			Connection conn = dataSource.getConnection();
			Integer id = this.insert(insertSql, integerConverter, conn, null, hero.getRoleId(), hero.getHeroId(), hero.getEmotion(),
					hero.getHungry(), hero.getTired(), hero.getEffective(), hero.getSkillId(), hero.getTalentJobId(),
					hero.getTalentLv(), hero.getRealize(), hero.getAgeId(), hero.getAge(), hero.getRebirth(),
					hero.getHotspot(), hero.getLoveJobId());
			if (id != null) {
				hero.setId(id);
				return hero;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void updateHero(Hero hero) {
		try {
			Connection conn = dataSource.getConnection();
			this.update(updateSql, conn, hero.getRoleId(), hero.getEmotion(), hero.getHungry(),
					hero.getTired(), hero.getEffective(), hero.getSkillId(), hero.getTalentJobId(), hero.getTalentLv(),
					hero.getRealize(), hero.getAgeId(), hero.getAge(), hero.getRebirth(), hero.getHotspot(),
					hero.getLoveJobId(), hero.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
