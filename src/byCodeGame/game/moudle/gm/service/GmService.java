package byCodeGame.game.moudle.gm.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface GmService {

	/**
	 * 获取在线玩家数据
	 * @return
	 */
	public Message getOnLineRoleData();
	/**
	 * GM指令
	 * @param role
	 * @param functionName
	 * @return
	 */
	public Message GMList(Role role,String functionName,int x,int y,int z,IoSession session);

	/***
	 * 停服指令
	 * @param gm
	 */
	public void stopApp(String gm);
	
	/**
	 * 使用特权码
	 * @param key
	 * @return
	 */
	public Message useKey(String key ,Role role);
}
