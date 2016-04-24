package byCodeGame.game.module.hero.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public class HeroServiceImpl implements HeroService {

	private HeroDao heroDao;

	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}

	@Override
	public List<Hero> createHeros(int count, Connection conn) {
		List<Hero> list = new ArrayList<>();
		for(int i = 0;i<count;i++){
			Hero hero = new Hero();
			this.heroCreateDataInit(hero);
			heroDao.insertHeroNotCloseConn(hero, conn);
			list.add(hero);
		}
		return list;
	}

	@Override
	public Hero createHero() {
		Hero hero = new Hero();
		this.heroCreateDataInit(hero);
		heroDao.insertHero(hero);
		return hero;
	}
	
	private void heroCreateDataInit(Hero hero){
		
	}

	@Override
	public Message changeSkill(int skillId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message showHeroSkill(Role role, int heroId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message work(Role role, int heroId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Message realize(Role role,int heroId){
		return null;
	}

	@Override
	public Message rebirth(Role role, int heroId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message learnSkill(Role role, int heroId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Message showHero(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

}
