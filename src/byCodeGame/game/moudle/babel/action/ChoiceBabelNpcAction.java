package byCodeGame.game.moudle.babel.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.babel.service.BabelService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class ChoiceBabelNpcAction implements ActionSupport{
	private BabelService babelService;
	public void setBabelService(BabelService babelService) {
		this.babelService = babelService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int userId = message.getInt();
		String userHero = message.getString(session);
		msg = babelService.choiceBabelNpc(role, userId ,userHero);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
