package byCodeGame.game.moudle.arms.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.arms.service.ArmsService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class AddArmsResearchLvAction implements ActionSupport {

	private ArmsService armsService;
	
	public void setArmsService(ArmsService armsService) {
		this.armsService = armsService;
	}


	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		int armsId = message.getInt();
		Message msg = armsService.addArmsResearchLv(role, armsId);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

}
