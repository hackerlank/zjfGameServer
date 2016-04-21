package byCodeGame.game.moudle.prop.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class PropVisitTreasureAction implements ActionSupport{
	private PropService propService;
	public void setPropService(PropService propService) {
		this.propService = propService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);	
		int heroId = message.getInt();
		msg = propService.visitTreasure(role, heroId);
		if(msg ==null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
