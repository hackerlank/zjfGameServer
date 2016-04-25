package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.util.List;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.entity.bo.Hero;

public class HeroDaoImpl extends DataAccess implements HeroDao{

	
	@Override
	public List<Hero> getHerosByRoleId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertHeroNotCloseConn(Hero hero, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertHero(Hero hero) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHero(Hero hero) {
		// TODO Auto-generated method stub
		
	}

}
