package byCodeGame.game.moudle.city.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class CityMarchWorldArmyAction implements ActionSupport {

	private CityService cityService;
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		int worldFormationId = message.getInt();
		int heroCaptainId = message.getInt();

		String name = message.getString(session);
		Message msg = cityService.marchWorldArmyFromHome(role, worldFormationId, heroCaptainId,name);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

}