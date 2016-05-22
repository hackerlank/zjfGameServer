/**
 * 
 */
/**
 * @author AIM
 *
 */
package byCodeGame.game.module.hero.service;

import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface HeroService{

	/**
	 * 创建单个英雄
	 * @return
	 */
	Hero createHero(byte sex);

	/**
	 * 改变技能
	 * @param skillId
	 * @return
	 */
	Message changeSkill(int skillId);

	/**
	 * 显示武将技能
	 * @param role
	 * @param heroId
	 * @return
	 */
	Message showHeroSkill(Role role, int heroId);

	/**
	 * 领悟
	 * @param role
	 * @param heroId
	 * @return
	 */
	Message realize(Role role,int heroServerId);
	
	/**
	 * 学习技能
	 * @param role
	 * @param heroId
	 * @return
	 */
	Message learnSkill(Role role,int heroId);
	
	/**
	 * 显示英雄
	 * @param role
	 * @return
	 */
	Message showHero(Role role);
}