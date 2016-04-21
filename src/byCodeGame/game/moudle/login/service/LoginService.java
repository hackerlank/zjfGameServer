package byCodeGame.game.moudle.login.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface LoginService {

	/**
	 * 登录
	 * @param account
	 * @return
	 */
	public Message login(String account,IoSession ioSession);
	
	/**
	 * 创建游戏角色
	 * @param account
	 * @return
	 */
	public Message creatRole(String account,IoSession session,byte nation ,String name);
	
	/**
	 * 获取玩家数据
	 * @param role
	 * @return
	 */
	public Message getRoleData(String account,IoSession ioSession);
	
	/**
	 * 角色登陆模块数据初始化
	 * 
	 * @param role
	 */
	public void loginRoleModuleDataInit(Role role);
}
