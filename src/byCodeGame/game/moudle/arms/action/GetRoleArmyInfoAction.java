package byCodeGame.game.moudle.arms.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.arms.service.ArmsService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetRoleArmyInfoAction implements ActionSupport{
	private ArmsService armsService;
	public void setArmsService(ArmsService armsService) {
		this.armsService = armsService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int type = message.getInt();
		
		msg = armsService.getRoleArmyInfo(role, type);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
