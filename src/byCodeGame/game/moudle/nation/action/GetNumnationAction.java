package byCodeGame.game.moudle.nation.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.moudle.nation.service.NationService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetNumnationAction implements ActionSupport{
	private NationService nationService;
	public void setNationService(NationService nationService) {
		this.nationService = nationService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		
		msg = nationService.getNumnation();
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
