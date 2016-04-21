package byCodeGame.game.moudle.fight.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.moudle.fight.service.FightService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetReportAction implements ActionSupport{
	private FightService fightService;
	public void setFightService(FightService fightService) {
		this.fightService = fightService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		String uuid = message.getString(session);
		
		msg = fightService.getReportByUUID(uuid);
		if(msg == null)
		{
			return ;
		}else {
			session.write(msg);
		}
	}
}
