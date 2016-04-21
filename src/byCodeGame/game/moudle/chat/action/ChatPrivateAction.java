package byCodeGame.game.moudle.chat.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.chat.service.ChatService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class ChatPrivateAction implements ActionSupport{

	private ChatService chatService;
	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		String targetName=message.getString(session);
		byte messageType = message.get();
		String context = message.getString(session);
		
		
		msg = chatService.privateChat(role, messageType, context, session, targetName);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
