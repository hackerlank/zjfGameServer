package byCodeGame.game.moudle.inCome.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetFightInfoAction implements ActionSupport{
	private InComeService inComeService;
	public void setInComeService(InComeService inComeService) {
		this.inComeService = inComeService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		byte type = message.get();
		byte id = message.get();
		
		msg = inComeService.getFightInfo(type, id);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
