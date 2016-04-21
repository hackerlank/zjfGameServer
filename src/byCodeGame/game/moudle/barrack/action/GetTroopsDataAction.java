package byCodeGame.game.moudle.barrack.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.barrack.service.BarrackService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetTroopsDataAction implements ActionSupport {

	private BarrackService barrackService;
	public void setBarrackService(BarrackService barrackService) {
		this.barrackService = barrackService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		
		Message msg = barrackService.getTroopsData(role);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

}
