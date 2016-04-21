package byCodeGame.game.moudle.role.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class FriendTipAction implements ActionSupport{
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	

	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		byte type = message.get();
		
		msg = roleService.friendTip(role, type);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
