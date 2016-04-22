package byCodeGame.game.module.login.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.module.base.Service;
import byCodeGame.game.remote.Message;

public interface LoginService extends Service{
	public Message login(String account,IoSession session);
}
