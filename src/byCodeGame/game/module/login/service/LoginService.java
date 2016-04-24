package byCodeGame.game.module.login.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.module.Service;
import byCodeGame.game.remote.Message;

public interface LoginService extends Service {
	/**
	 * 登录
	 * @param account
	 * @param session
	 * @return
	 */
	public Message login(String account, IoSession session);

	/**
	 * 初始化用户数据
	 * @param account
	 * @param ioSession
	 * @return
	 */
	public Message getRoleData(String account, IoSession ioSession);
}
