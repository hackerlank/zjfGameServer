package byCodeGame.game.moudle.heart.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.heart.service.HeartService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class BuyArmyTokenAction implements ActionSupport{
	private HeartService heartService;
	public void setHeartService(HeartService heartService) {
		this.heartService = heartService;
	}

	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int num = message.getInt();
		
		msg = heartService.buyArmyToken(role, num);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
