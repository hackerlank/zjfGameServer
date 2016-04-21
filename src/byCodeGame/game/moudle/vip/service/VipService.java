package byCodeGame.game.moudle.vip.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface VipService {
	/**
	 * 充值
	 * @param role
	 * @param num
	 * @return
	 */
	public Message recharge(String account,int num ,int serverId ,String partnerId ,String orderNo);


	/**
	 * 获取玩家VIP信息
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getVipInfo(Role role);
	
	/**
	 * 获取Vip礼包信息
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getVipRawardInfo(Role role);
	
	/**
	 * 领取Vip礼包
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getVipRaward(Role role,int id);
	
	
	/**
	 * 服务器启动时读取Vip礼包
	 */
	public void initAward();
}
