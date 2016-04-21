package byCodeGame.game.moudle.mail.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class SetMailCheckedAction implements ActionSupport{
	private MailService mailService;
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
	public void execute(Message message, IoSession session) {
		int id = message.getInt();
		Role role = RoleCache.getRoleBySession(session);
		Message msg = mailService.SetMailChecked(role, id, session);
		
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
