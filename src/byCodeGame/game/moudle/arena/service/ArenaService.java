package byCodeGame.game.moudle.arena.service;

import java.util.List;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.LadderArena;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;


public interface ArenaService {

	/**
	 * 天梯初始化
	 */
	public void ladderInit();
	
	/**
	 * 竞技场组别初始化
	 */
	public void arenaInit();
	
	/**
	 * 获取对手
	 * @param role
	 * @return
	 */
	public Message getTarget(Role role);
	
	/**
	 * 挑战对手
	 * @param role
	 * @param index
	 * @return
	 */
	public Message dareTarget(Role role,byte index,IoSession is);
	
	/**
	 * 获取天梯排名
	 */
	public Message getLadder();
	/**
	 * 获取当前品阶排名
	 */
	public Message getNowLadders(Role role);
	/**
	 * 刷新竞技场目标
	 * @param role
	 */
	public void refreshTarget(Role role);
	
	/**
	 * 获取玩家竞技场数据
	 * @param role
	 */
	public Message getArenaData(Role role);
	
	/**
	 * 竞技场战斗开始
	 * @param role
	 * @return
	 */
	public Message arenaFightBegin(Role role,int targetId,IoSession is);
	
	/**
	 * 设置出场武将
	 * @param role
	 * @param heroId
	 * @return
	 */
	public Message setFightHero(Role role,int heroId);
	
	/**
	 * 发送竞技场预加载信息
	 */
	public Message getFightLoad(Role role,String uuid);
	
	/**
	 * 根据排名获取机器人品阶
	 * @param rank
	 * @return
	 */
	public int getLvByRank(int rank);
	/**
	 * 根据roleId获取玩家竞技场数据信息
	 * @param roleId
	 * @return
	 */
	public  LadderArena getLvAndRank(int roleId);
	/**
	 * 根据玩家等阶获取当前等阶玩家排名
	 * @param lv
	 * @return
	 */
	public  List<LadderArena> getNowLadder(int lv);
	/**
	 * 获取全服排名详细信息
	 * @param lv
	 * @return
	 */
	public  Map<Integer, LadderArena> getAllLadderArena();
}
