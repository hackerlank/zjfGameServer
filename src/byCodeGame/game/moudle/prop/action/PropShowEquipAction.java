package byCodeGame.game.moudle.prop.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class PropShowEquipAction implements ActionSupport{
	private PropService propService;
	public void setPropService(PropService propService) {
		this.propService = propService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);	
		msg = propService.showEquip(role);
		if(msg ==null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
