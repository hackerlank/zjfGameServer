package byCodeGame.game.moudle.prop.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

/**
 * 
 * @author wcy 2016年1月13日
 *
 */
public class PropShowAddIronStrengthenAction implements ActionSupport{
	
	private PropService propService;

	public void setPropService(PropService propService) {
		this.propService = propService;
	}

	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int equipId = message.getInt();
		int ironNum = message.getInt();
		msg = propService.showAddIronStrengthen(role, equipId, ironNum);
		if (msg == null) {
			return;
		} else {
			session.write(msg);
		}
	}

}
