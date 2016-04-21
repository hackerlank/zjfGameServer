package byCodeGame.game.moudle.legion.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.LegionCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.legion.LegionConstant;
import byCodeGame.game.moudle.legion.service.LegionService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class DeputyLegionAction implements ActionSupport{
	private LegionService legionService;
	public void setLegionService(LegionService legionService) {
		this.legionService = legionService;
	}
	
	public void execute(Message message, IoSession session) {
		byte type = message.get();
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		Legion legion = LegionCache.getLegionById(role.getLegionId());
		int targetId = message.getInt();
		if(type == LegionConstant.DEPUTY_TYPE_1)
		{
			msg = legionService.setDeputy(role, legion, targetId);
		} else if (type == LegionConstant.DEPUTY_TYPE_2) {
			msg = legionService.cancelDeputy(role, legion, targetId);
		} else if (type == LegionConstant.DEPUTY_TYPE_3) {
			msg = legionService.kickMember(role, legion, targetId);
		} else if (type == LegionConstant.DEPUTY_TYPE_4) {
			msg = legionService.changCaptain(role, legion, targetId);
		}
		
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
