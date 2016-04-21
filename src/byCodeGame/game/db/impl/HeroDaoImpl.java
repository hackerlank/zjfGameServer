package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.HeroConverter;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.entity.bo.Hero;

public class HeroDaoImpl extends DataAccess implements HeroDao {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insertHero(Hero hero) {
		final String sql = "insert into hero(roleId,heroId,troopsConfig,lv,exp,rebirthLv,status,rank,armyInfo,useArmyId,emotion,maxArmyQuality,skillId,skillLv,skillBD,skillBDLv,emotionLv,bulidId,insteadArmy,usingArmy,dutyLv,manual,lastGetMa)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			Connection conn = dataSource.getConnection();
			super.insert(sql, new IntegerConverter(), conn, hero.getRoleId(), hero.getHeroId(), hero.getTroopsConfig(),
					hero.getLv(), hero.getExp(), hero.getRebirthLv(), hero.getStatus(), hero.getRank(),
					hero.getArmyInfo(), hero.getUseArmyId(), hero.getEmotion(), hero.getMaxArmyQuality(),
					hero.getSkillId(), hero.getSkillLv(), hero.getSkillBD(), hero.getSkillBDLv(), hero.getEmotionLv(),
					hero.getBulidId(), hero.getInsteadArmy(), hero.getUsingArmy(), hero.getDutyLv(),
					hero.getManual(),hero.getLastGetMa());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateHero(Hero hero) {
		final String sql = "update hero set troopsConfig=?,lv=?,exp=?,rebirthLv=?,status=?,rank=?,armyInfo=?,useArmyId=?,emotion=?,maxArmyQuality=?,skillId=?,skillLv=?,skillBD=?,skillBDLv=?,"
				+ "emotionLv=? ,bulidId=?,insteadArmy=?,usingArmy=?,dutyLv=?,manual=?,lastGetMa=?,tired=?" 
				+ " where roleId =? and heroId=? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, hero.getTroopsConfig(), hero.getLv(), hero.getExp(), hero.getRebirthLv(),
					hero.getStatus(), hero.getRank(), hero.getArmyInfo(), hero.getUseArmyId(), hero.getEmotion(),
					hero.getMaxArmyQuality(), hero.getSkillId(), hero.getSkillLv(), hero.getSkillBD(),
					hero.getSkillBDLv(), hero.getEmotionLv(), hero.getBulidId(), hero.getInsteadArmy(),
					hero.getUsingArmy(), hero.getDutyLv(),hero.getManual(),hero.getLastGetMa(),hero.getTired(),
					hero.getRoleId(), hero.getHeroId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Hero> getAllheroByRoleId(int roleId) {
		String sql = "select * from hero where roleId=?";
		try {
			Connection conn = dataSource.getConnection();
			List<Hero> result = super.queryForList(sql, new HeroConverter(), conn, roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void removeHero(Hero hero) {
		String sql = "delete from prop where roleId=? and heroId=? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.delete(sql, conn, hero.getRoleId(), hero.getHeroId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertRoleHero(Hero hero, Connection conn) {
		final String sql = "insert into hero(roleId,heroId,troopsConfig,lv,exp,rebirthLv,status,rank,armyInfo,useArmyId,emotion,maxArmyQuality,skillId,skillLv,skillBD,skillBDLv,emotionLv,bulidId,insteadArmy,usingArmy,dutyLv,manual,lastGetMa)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter(), conn, hero.getRoleId(), hero.getHeroId(),
					hero.getTroopsConfig(), hero.getLv(), hero.getExp(), hero.getRebirthLv(), hero.getStatus(),
					hero.getRank(), hero.getArmyInfo(), hero.getUseArmyId(), hero.getEmotion(),
					hero.getMaxArmyQuality(), hero.getSkillId(), hero.getSkillLv(), hero.getSkillBD(),
					hero.getSkillBDLv(), hero.getEmotionLv(), hero.getBulidId(), hero.getInsteadArmy(),
					hero.getUsingArmy(), hero.getDutyLv(),hero.getManual(),hero.getLastGetMa());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
