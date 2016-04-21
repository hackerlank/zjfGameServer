package byCodeGame.game.moudle.target.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.target.TargetConstant;
import byCodeGame.game.moudle.target.service.TargetService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetActiveAwardAction implements ActionSupport{
	private TargetService targetService;
	public void setTargetService(TargetService targetService) {
		this.targetService = targetService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		int type = message.get();
		Role role = RoleCache.getRoleBySession(session);
		
		switch (type) {
		case TargetConstant.ACTIVE_FIRST_RECHARGE:
			msg = targetService.getFirstRecharge(role);
			break;
		default:
			break;
		}
		
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
