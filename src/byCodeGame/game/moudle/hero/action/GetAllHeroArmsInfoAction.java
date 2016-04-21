package byCodeGame.game.moudle.hero.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetAllHeroArmsInfoAction implements ActionSupport {
	private HeroService heroService;

	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}

	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		int heroId = message.getInt();
		Role role = RoleCache.getRoleBySession(session);
		Hero hero = role.getHeroMap().get(heroId);

		msg = heroService.getAllHeroArmsInfo(role,hero);
		if (msg == null) {
			return;
		} else {
			session.write(msg);
		}
	}

}
