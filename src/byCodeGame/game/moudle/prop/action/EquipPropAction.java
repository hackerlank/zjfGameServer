package byCodeGame.game.moudle.prop.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class EquipPropAction implements ActionSupport {

	private PropService propService;
	public void setPropService(PropService propService) {
		this.propService = propService;
	}

	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		int heroId = message.getInt();
		byte slotId = message.get();
		int propId = message.getInt();
		
		Message msg = propService.equipProp(role, heroId, slotId, propId);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

	
}
