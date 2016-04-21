package byCodeGame.game.moudle.pub.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.pub.service.PubService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class PubShowWineDeskAction implements ActionSupport {
	private PubService pubService;

	public void setPubService(PubService pubService) {
		this.pubService = pubService;
	}
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		Message msg = pubService.showWineDesk(role);
		if (msg == null) {
			return;
		} else {
			session.write(msg);
		}
	}

}
