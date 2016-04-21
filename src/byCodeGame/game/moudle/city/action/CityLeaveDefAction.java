package byCodeGame.game.moudle.city.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class CityLeaveDefAction implements ActionSupport {

	private CityService cityService;
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		String worldArmyId = message.getString(session);
		int cityId = message.getInt();
		int wallId = message.getInt();
		Message msg = cityService.leaveDefInfo(role, worldArmyId, cityId,wallId);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

}