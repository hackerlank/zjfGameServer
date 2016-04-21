package byCodeGame.game.moudle.inCome.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetInfoAttach implements ActionSupport{
	private InComeService inComeService;
	public void setInComeService(InComeService inComeService) {
		this.inComeService = inComeService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int heroId = message.getInt();
		byte type = message.get();
		byte action = message.get();
		if(action == 1){
			msg = inComeService.getInfoAttach(role, heroId, type);
		}else if (action == 2) {
			msg = inComeService.getInfoLevy(role, heroId, type);
		}
		
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
