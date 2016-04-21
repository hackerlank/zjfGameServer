package byCodeGame.game.moudle.fight.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.fight.service.FightService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class RequestAttackChapterAction implements ActionSupport {

	private FightService fightService;
	public void setFightService(FightService fightService) {
		this.fightService = fightService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		int cid = message.getInt();
		Message msg = fightService.requestAttackChapter(role, cid);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}
}
