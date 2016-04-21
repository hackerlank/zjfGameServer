package byCodeGame.game.moudle.inCome.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class LevyAction implements ActionSupport {

	private InComeService inComeService;

	public void setInComeService(InComeService inComeService) {
		this.inComeService = inComeService;
	}

	@Override
	public void execute(Message message, IoSession session) {
		byte type = message.get();
		int heroId = message.getInt();
		Role role = RoleCache.getRoleBySession(session);
		byte levyType = message.get();

		Message msg = inComeService.levy(type, role, heroId, levyType);
		if (msg == null) {
			return;
		} else {
			session.write(msg);
		}
	}

}
