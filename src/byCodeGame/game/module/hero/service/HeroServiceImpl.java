package byCodeGame.game.module.hero.service;

import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.HeroConfig;
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
		HeroConfig config = HeroConfigCache.getHeroConfigById(heroId);
		int ageId = config.getAgeId();
		int effective = config.getInitEffective();
		int talentJobId = config.getTalentId();

		Hero hero = new Hero();
		hero.setHeroId(heroId);
		hero.setEffective(effective);
		hero.setTalentLv((byte) 1);

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

	public Message realize(Role role, int heroServerId) {
		Message message = new Message();

		Hero hero = role.getHeroMap().get(heroServerId);

		if (this.checkMaxTalentLv(hero)) {
			message.putShort(ErrorCode.HERO_MAX_TALENT);
			return message;
		}
		if (!this.isRealizeSuccess()) {
			message.putShort(ErrorCode.HERO_REALIZE_FAILED);
			return message;
		}
		byte src = hero.getTalentLv();
		hero.setTalentLv(src++);

		return message;
	}

	/**
	 * 检查是否最大天赋等级
	 * 
	 * @param hero
	 * @return
	 * @author wcy 2016年5月9日
	 */
	private boolean checkMaxTalentLv(Hero hero) {
		// TODO Auto-generated method stub

		return false;
	}

	/**
	 * 是否领悟成功
	 * 
	 * @return
	 * @author wcy 2016年5月9日
	 */
	private boolean isRealizeSuccess() {
		return true;
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
