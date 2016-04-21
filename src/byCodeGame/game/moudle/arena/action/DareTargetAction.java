package byCodeGame.game.moudle.arena.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.arena.service.ArenaService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class DareTargetAction implements ActionSupport {

	private ArenaService arenaService;
	public void setArenaService(ArenaService arenaService) {
		this.arenaService = arenaService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		byte index = message.get();
		Message msg = arenaService.dareTarget(role, index, session);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

}
