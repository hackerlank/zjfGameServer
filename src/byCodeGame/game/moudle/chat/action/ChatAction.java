package byCodeGame.game.moudle.chat.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.chat.service.ChatService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class ChatAction implements ActionSupport {

	private ChatService chatService;
	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		Message msg =null;
		byte messageType = message.get();
		String context = message.getString(session);
		
		msg = chatService.chat(role,messageType, context,session);
		
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

}
