package byCodeGame.game.moudle.babel.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

/**
 * 通天塔模块
 * @author xjd
 * 
 */
public interface BabelService {
	/**
	 * 获取通天塔信息
	 * @return
	 */
	public Message getBabelInfo(Role role);
	
	/**
	 * 挑战通天塔
	 * @param id
	 * @return
	 */
	public Message FightBabel(Role role ,int id);
	
	/**
	 * 初始化玩家通天塔NPC部队信息	
	 * @param userId
	 * @return
	 */
	public Message choiceBabelNpc(Role role,int userId,String userHero);
	
	/**
	 * 更改阵型
	 * @param role
	 * @param heroId
	 * @return
	 */
	public Message changeTroopData(Role role ,int local, int heroId);
	
	/**
	 * 复活部队
	 * @param role
	 * @param heroId
	 * @return
	 */
	public Message reviveHero(Role role , int heroId);
	
	/**
	 * 获取武将当前信息
	 * @param role
	 * @return
	 */
	public Message getHeroInfo(Role role);
	
	/**
	 * 重置通天塔
	 * @param role
	 * @return
	 */
	public Message resetTower(Role role);
	
	/**
	 * 初始化通天塔
	 * @param role
	 */
	public void initTower(Role role);
	
	/**
	 * 初始化配置表
	 * 
	 * @author wcy 2016年4月15日
	 */
	public void initTrialConfig();
}
