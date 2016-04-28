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

	private final String insertSql = "insert into hero values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String selectIdSql = "select * from hero where roleId=?";
	private final String updateSql = "update set emotion=?,hungry=?,tired=?,effective=?,skillId=?,"
			+ "giftId=?,ageId=?,ageCount=?,rebirth=?,workRefreshTime=?,hotspot=? where heroId=? and roleId=?";

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
			this.insert(insertSql, integerConverter, conn, null, hero.getRoleId(), hero.getHeroId(), hero.getEmotion(),
					hero.getHungry(), hero.getTired(), hero.getEffective(), hero.getSkillId(), hero.getGiftId(),
					hero.getAgeId(), hero.getAgeCount(), hero.getRebirth(), hero.getHotspot());
			return hero;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void updateHero(Hero hero) {
		try {
			Connection conn = dataSource.getConnection();			
			int heroId = hero.getHeroId();
			int roleId = hero.getRoleId();
			this.update(updateSql, conn, hero.getRoleId(), hero.getHeroId(), hero.getEmotion(), hero.getHungry(),
					hero.getTired(), hero.getEffective(), hero.getSkillId(), hero.getGiftId(), hero.getAgeId(),
					hero.getAgeCount(), hero.getRebirth(), hero.getHotspot(), heroId, roleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
