package byCodeGame.game.moudle.arms.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface ArmsService {
	/**
	 * 获取武将列表
	 * 
	 * @param role
	 * @return
	 */
	public Message getArmsByType(Role role, int type);

	/**
	 * 兵种科技等级+1
	 * 
	 * @param role
	 * @param armsId
	 * @return
	 */
	public Message addArmsResearchLv(Role role, int armsId);

	/**
	 * 升级天赋
	 * 
	 * @param id
	 * @return
	 * @author xjd
	 */
	public Message upRoleArmy(Role role, int id);

	/**
	 * 重置天赋
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message reSetRoleArmy(Role role);

	/**
	 * 获取天赋信息
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getRoleArmyInfo(Role role, int type);

	/**
	 * 使用兵符
	 * 
	 * @param role
	 * @param heroId
	 * @param heroSoldierId
	 * @param type 使用兵符类型 1：1阶兵符 2：2阶兵符 3：3阶兵符
	 * @return
	 * @author wcy
	 */
	public Message useSoldierTally(Role role, int heroId, int heroSoldierId, int type);

	/**
	 * 升级英雄兵种技能
	 * 
	 * @param role
	 * @param heroId
	 * @param heroSoldierId
	 * @return
	 * @author wcy
	 */
	public Message updateHeroSoldierSkill(Role role, int heroId, int heroSoldierId);

	/**
	 * 
	 * @param role
	 * @param heroId
	 * @param propId
	 * @param requestQuality
	 * @return
	 * @author wcy
	 */
	public Message updateHeroSoldierQuality(Role role, int heroId,int propId,int requestQuality);

	/**
	 * 更换英雄兵种
	 * 
	 * @param role
	 * @param heroId
	 * @param heroSoldierId
	 * @return
	 * @author wcy
	 */
	public Message changeHeroSoldier(Role role, int heroId, int heroSoldierId);
}
