package byCodeGame.game.moudle.pub.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.pub.service.PubService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class PubVisitAction implements ActionSupport{
	private PubService pubService;
	public void setPubService(PubService pubService) {
		this.pubService = pubService;
	}	
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int mapId = message.getInt();
		int heroId = message.getInt();
		
		msg = pubService.visitMap(role, mapId, heroId);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
