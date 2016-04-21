package byCodeGame.game.moudle.sign.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.sign.service.SignService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetSignInfoAciton implements ActionSupport{
	private SignService signService;
	public void setSignService(SignService signService) {
		this.signService = signService;
	}
	
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		
		msg = signService.getSignInfo(role);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}

	
}
