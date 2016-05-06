package byCodeGame.game.module.hero.service;

import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

/**
 * 
 * @author wcy 2016年4月28日
 *
 */
public class HeroServiceImpl implements HeroService {

	private HeroDao heroDao;

	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}

	@Override
	public Hero createHero(int heroId) {
		Hero hero = new Hero();

		hero.setHeroId(heroId);

		heroDao.insertHero(hero);
		return hero;
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

	public Message realize(Role role, int heroId) {
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
