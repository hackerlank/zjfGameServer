package byCodeGame.game.moudle.nation.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.nation.service.NationService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class NationAction implements ActionSupport{
	private NationService nationService;
	public void setNationService(NationService nationService) {
		this.nationService = nationService;
	}

	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		byte nation = message.get();
		Message msg = nationService.setNation(role, nation);
		if(msg == null)
		{
			return ;
		}else {
			session.write(msg);
		}
	}
}
