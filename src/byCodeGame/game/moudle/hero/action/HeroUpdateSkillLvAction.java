package byCodeGame.game.moudle.hero.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class HeroUpdateSkillLvAction implements ActionSupport {

	private HeroService heroService;
	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		int heroId = message.getInt();
		int skillType = message.getInt();
		Message msg = heroService.updateSkill(role, heroId, skillType);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}
}
