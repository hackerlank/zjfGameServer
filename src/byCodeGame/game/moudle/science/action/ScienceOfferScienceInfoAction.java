package byCodeGame.game.moudle.science.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.science.service.ScienceService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class ScienceOfferScienceInfoAction implements ActionSupport{
	private ScienceService scienceService;
	public void setScienceService(ScienceService scienceService) {
		this.scienceService = scienceService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int heroId = message.getInt();
		int scienceId = message.getInt();
		msg = scienceService.offerScience(role, scienceId, heroId);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
