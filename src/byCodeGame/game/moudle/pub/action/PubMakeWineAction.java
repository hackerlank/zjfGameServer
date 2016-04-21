package byCodeGame.game.moudle.pub.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.pub.service.PubService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;
/**
 * 酿酒
 * @author wcy
 *
 */
public class PubMakeWineAction implements ActionSupport {
	private PubService pubService;
	public void setPubService(PubService pubService) {
		this.pubService = pubService;
	}
	@Override
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int num = message.getInt();
		msg = pubService.makeWine(role, num, session);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}

}
