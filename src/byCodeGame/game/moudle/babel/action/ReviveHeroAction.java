package byCodeGame.game.moudle.babel.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.babel.service.BabelService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class ReviveHeroAction implements ActionSupport{
	private BabelService babelService;
	public void setBabelService(BabelService babelService) {
		this.babelService = babelService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int heroId = message.getInt();
		
		msg = babelService.reviveHero(role, heroId);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
