package byCodeGame.game.moudle.mail.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class DeleteMailAction implements ActionSupport{
	private MailService mailService;
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		byte type = message.get();
		int id =0 ;
		if(type == 1)
		{
			 id = message.getInt();
		} 
		Message msg = mailService.DeleteMailById(role, type, id, session);
		
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
