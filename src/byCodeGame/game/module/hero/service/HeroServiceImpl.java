package byCodeGame.game.module.hero.service;

import java.util.List;

import byCodeGame.game.cache.file.SkillConfigCache;
import byCodeGame.game.cache.file.SkillLvConfigCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.module.hero.HeroConstant;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.Utils;

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
	public Hero createHero(byte sex) {
		Hero hero = new Hero();
		//性别
		hero.setSex(sex);
		//年龄
		int age = 0;
		if (sex == HeroConstant.BOY)
			age = HeroConstant.INIT_BOY_AGE;
		else if (sex == HeroConstant.GIRL)
			age = HeroConstant.INIT_GIRL_AGE;
		hero.setAge(age);
		
		//喜好
		List<Integer> idList = SkillConfigCache.getIdList();
		int index = Utils.getRandomNum(idList.size());
		int loveSkillId = idList.get(index);		
		hero.setLoveSkillId(loveSkillId);
		
		hero.setEmotion(HeroConstant.INIT_EMOTION);
		hero.setHungry(HeroConstant.INIT_HUNGRY);
		hero.setEffective(HeroConstant.INIT_EFFECTIVE);
		hero.setSkillLv(HeroConstant.INIT_SKILL_LV);
		hero.setSkillId(HeroConstant.INIT_SKILL_ID);
		
		hero.setTalentLv((byte) SkillLvConfigCache.getSkillLvConfigByLv(hero.getSkillLv()).getTalentLv());	
		
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
