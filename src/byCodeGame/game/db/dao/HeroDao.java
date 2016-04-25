package byCodeGame.game.db.dao;

import java.sql.Connection;
import java.util.List;

import byCodeGame.game.entity.bo.Hero;

public interface HeroDao {
	/**
	 * 获取玩家的所有宠物
	 * @param id
	 * @return
	 */
	List<Hero> getHerosByRoleId(int id);

	/**
	 * 
	 * @param hero
	 * @param conn
	 */
	void insertHeroNotCloseConn(Hero hero, Connection conn);

	/**
	 * 
	 * @param hero
	 */
	void insertHero(Hero hero);
	
	/**
	 * 
	 * @param hero
	 * @author wcy 2016年4月25日
	 */
	void updateHero(Hero hero);
}
