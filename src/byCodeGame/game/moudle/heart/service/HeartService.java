package byCodeGame.game.moudle.heart.service;


import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface HeartService {

	public Message heart(Role role);
	
	/***
	 * 检查玩家军令数量（是否增加）
	 * @param role
	 * @author xjd
	 */
	public void addArmyToken(Role role);
	
	/**
	 * 计算玩家下线后的军令数量
	 * @param role
	 * @author xjd
	 */
	public void updateArmyToken(Role role);
	
	/**
	 * 玩家购买军令
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message buyArmyToken(Role role , int num);
	
}
