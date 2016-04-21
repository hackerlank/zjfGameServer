package byCodeGame.game.moudle.prop.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetPropListAction implements ActionSupport {

	private PropService propService;
	public void setPropService(PropService propService) {
		this.propService = propService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		Message msg = propService.getPropList(role);

		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

	

}
