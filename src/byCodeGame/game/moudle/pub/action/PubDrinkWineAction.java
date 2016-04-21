package byCodeGame.game.moudle.pub.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.pub.service.PubService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

/**
 * 宴请
 * 
 * @author wcy
 *
 */
public class PubDrinkWineAction implements ActionSupport {
	private PubService pubService;

	public void setPubService(PubService pubService) {
		this.pubService = pubService;
	}

	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		byte deskIndex = message.get();
		Message msg = pubService.drinkWine(role, deskIndex);
		if (msg == null) {
			return;
		} else {
			session.write(msg);
		}
	}

}
