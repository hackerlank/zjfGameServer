package byCodeGame.game.moudle.raid.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.moudle.raid.service.RaidService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetLobbyInfoAction implements ActionSupport{
	private RaidService raidService;
	public void setRaidService(RaidService raidService) {
		this.raidService = raidService;
	}
	
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		
		msg = raidService.getLobbyInfo();
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
