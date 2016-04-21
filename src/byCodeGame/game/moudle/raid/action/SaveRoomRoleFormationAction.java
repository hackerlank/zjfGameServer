package byCodeGame.game.moudle.raid.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.raid.service.RaidService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class SaveRoomRoleFormationAction implements ActionSupport{
	private RaidService raidService;

	public void setRaidService(RaidService raidService) {
		this.raidService = raidService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int useingHeroID = message.getInt();
		int canChangeId = message.getInt();
		int canChangeId2 = message.getInt();
		
		msg = raidService.saveRoomRoleFormation(role, useingHeroID, canChangeId, canChangeId2);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
