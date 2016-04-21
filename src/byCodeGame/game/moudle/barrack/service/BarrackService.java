package byCodeGame.game.moudle.barrack.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface BarrackService {

	/**
	 * 建造兵营
	 * 
	 * @param role
	 * @param buildId
	 * @return
	 */
	public Message buildBarrack(Role role, Byte buildId, byte type);

	/**
	 * 升级兵营
	 * 
	 * @param role
	 * @return
	 */
	public Message upBarrack(Role role, byte id);

	/**
	 * 出售兵营
	 * 
	 * @param role
	 * @param id 位置ID
	 * @return
	 */
	public Message sellBarrack(Role role, byte id);

	/**
	 * 更改兵营
	 * 
	 * @param role
	 * @param id 位置ID
	 * @param type 兵种类型
	 * @return
	 */
	public Message changeBarrack(Role role, byte id, byte type);

	/**
	 * 获取指定兵种最大存储兵力
	 * 
	 * @param role
	 * @param type
	 * @return
	 */
	public int getMaxCapacity(Role role, byte type);

	/**
	 * 获取当前兵种的数量
	 * 
	 * @param role
	 * @param type
	 * @return
	 */
	public int getNowArmsNum(Role role, byte type);

	/**
	 * 招募兵力
	 * 
	 * @param role
	 * @param armsId
	 * @param num
	 * @return
	 */
	public Message recruitArms(Role role, int armsId, int num);

	/**
	 * 获取兵营建筑数据
	 * 
	 * @param role
	 * @return
	 */
	public Message getBarrackData(Role role);

	/**
	 * 获取兵力数据
	 * 
	 * @param role
	 * @return
	 */
	public Message getTroopsData(Role role);

	/**
	 * 显示征讨
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message showMakeExploit(Role role);

	/**
	 * 刷新领取战功
	 * 
	 * @param role
	 * @param nowTime
	 * @author wcy
	 */
	public void refreshGetExploit(Role role, long nowTime);

	/**
	 * 获得战功
	 * 
	 * @param role
	 * @param is
	 * @return
	 * @author wcy
	 */
	public Message getExploit(Role role, IoSession is);

	/**
	 * 初始化玩家兵营
	 * 
	 * @param role
	 * @author wcy
	 */
	public void initBarrack(Role role);

	/**
	 * 升级兵击技能
	 * 
	 * @param role
	 * @param armySkillId
	 * @return
	 * @author wcy
	 */
	public Message upArmySkill(Role role, int armySkillId);

	/**
	 * 显示兵击技能
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message showArmySkill(Role role);

	/**
	 * 显示征讨
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message showVisitOffensive(Role role);
	
	/**
	 * 
	 * @param role
	 * @param heroId
	 * @return
	 * @author wcy
	 */
	public Message visitOffensive(Role role, IoSession is);

	/**
	 * 获得征讨排名
	 * @return
	 * @author wcy
	 */
	public Message getRank();
}
