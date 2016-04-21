package byCodeGame.game.moudle.science.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface ScienceService {
	/**
	 * 获取玩家科技信息
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getRoleScienceInfo(Role role);

	/**
	 * 获取玩家队列信息
	 * 
	 * @param role
	 * @return
	 */
	public Message getRoleScienceQueue(Role role);

	/**
	 * 清除玩家队列信息
	 * 
	 * @param role
	 * @return
	 */
	public Message clearRoleScienceQueueTime(Role role);

	/**
	 * 升级科技
	 * 
	 * @param role
	 * @param type 科技类型
	 * @return
	 * @author xjd
	 */
	public Message upgradeRoleScience(Role role, int type);

	/**
	 * 根据科技编号获取玩家科技等级<br/>
	 * 1:骑兵攻击 2:骑兵防御 3:骑兵移动速度<br/>
	 * 4:步兵攻击 5:步兵防御 6:步兵血量<br/>
	 * 7:弓兵攻击 8:弓兵射程 9:弓兵防御<br/>
	 * 10:作战单位血量 <br/>
	 * 11法师攻击 12:法师防御<br/>
	 * 13:长蛇阵(攻击) 14:方圆阵(防御) 15:鹤翼阵(魔攻) 16:锥形阵(魔防)<br/>
	 * 17:鱼鳞阵(血量) 18:雁型阵(移速) 19:风火阵(攻速)<br/>
	 * 
	 * 
	 * @param scienceId 需要查询的科技编号
	 * @return
	 * @author xjd
	 */
	public int getRoleScienceById(Role role, int scienceId);

	/**
	 * 献策
	 * 
	 * @param role
	 * @param scienceId
	 * @param heroId
	 * @return
	 * @author wcy
	 */
	public Message offerScience(Role role, int scienceId, int heroId);

	/**
	 * 领取献策奖励
	 * 
	 * @param role
	 * @param startTimeId
	 * @return
	 * @author wcy
	 */
	public Message getOfferScienceAward(Role role, int startTimeId);
}
