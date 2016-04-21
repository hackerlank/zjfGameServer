package byCodeGame.game.moudle.sign.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface SignService {
	/**
	 * 获取玩家签到信息
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getSignInfo(Role role);
	
	/**
	 * 当日签到
	 * @param role
	 * @return
	 * @author xjd
	 * 
	 */
	public Message signToday(Role role);
	
	/**
	 * 补签
	 * @param role
	 * @param day
	 * @return
	 * @author xjd
	 */
	public Message signRetroactive(Role role , byte day);
	
	/**
	 * 领取签到奖励
	 * @param role
	 * @param number
	 * @return
	 * @author xjd
	 */
	public Message getSignAward(Role role , int number);

	/**
	 * 获取签到武将
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getSignHero(Role role, IoSession is);
}
