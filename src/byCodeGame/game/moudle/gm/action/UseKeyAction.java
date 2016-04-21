package byCodeGame.game.moudle.gm.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.gm.service.GmService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class UseKeyAction implements ActionSupport{
	private GmService gmService;
	public void setGmService(GmService gmService) {
		this.gmService = gmService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		String key = message.getString(session);
		msg = gmService.useKey(key,role);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}

}
