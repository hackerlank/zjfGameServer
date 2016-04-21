package byCodeGame.game.moudle.prop.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class SingleDressAction implements ActionSupport {

	private PropService propService;
	public void setPropService(PropService propService) {
		this.propService = propService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		int heroId1 = message.getInt();
		int heroId2 = message.getInt();
		byte slot = message.get();
		Message msg = propService.singleDress(role, heroId1, heroId2, slot);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

}
