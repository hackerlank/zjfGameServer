package byCodeGame.game.moudle.target.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.target.service.TargetService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetTargetInfoAction implements ActionSupport {
	private TargetService targetService;
	public void setTargetService(TargetService targetService) {
		this.targetService = targetService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		
		msg = targetService.getTargetInfo(role);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
