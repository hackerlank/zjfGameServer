package byCodeGame.game.navigation;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.remote.Message;


public interface ActionSupport {
	void execute(Message message, IoSession session);
}