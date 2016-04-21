package byCodeGame.game.moudle.pub.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.pub.service.PubService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class PubHeroDequeueAction implements ActionSupport{
	private PubService pubService;
	public void setPubService(PubService pubService) {
		this.pubService = pubService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int id = message.getInt();
		
		msg = pubService.pubHeroDequeue(role, id);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
