package byCodeGame.game.db.dao;

import java.sql.Connection;
import java.util.List;

import byCodeGame.game.entity.bo.Hero;

public interface HeroDao {

	public void insertHero(Hero hero);
	
	public void updateHero(Hero hero);
	
	public List<Hero> getAllheroByRoleId(int roleId);
	
	public void removeHero(Hero hero);
	
	public void insertRoleHero(Hero hero,Connection conn);
	
}
