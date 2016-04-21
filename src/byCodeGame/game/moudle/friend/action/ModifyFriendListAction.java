package byCodeGame.game.moudle.friend.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.friend.service.FriendService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class ModifyFriendListAction implements ActionSupport{
	private FriendService friendService;
	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		byte type = message.get();
		Role role = RoleCache.getRoleBySession(session);
		String targetName = message.getString(session);
		
		if(type == 0 )
		{
			msg = friendService.addFriendList(role, targetName);
		}else if (type == 1) {
			msg = friendService.removeFriendList(role, targetName);
		}
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
