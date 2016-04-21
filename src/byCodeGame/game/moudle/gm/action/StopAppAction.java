package byCodeGame.game.moudle.gm.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.moudle.gm.service.GmService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class StopAppAction implements ActionSupport{
	private GmService gmService;
	public void setGmService(GmService gmService) {
		this.gmService = gmService;
	}
	
	public void execute(Message message, IoSession session) {
		String gm = message.getString(session);
		System.err.println(session.getLocalAddress());
		gmService.stopApp(gm);
	}
}
