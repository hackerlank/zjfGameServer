package byCodeGame.game.moudle.city.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class OccupyCityMineOrFarmAction implements ActionSupport {

	private CityService cityService;
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		int cityId= message.getInt();
		int buildId= message.getInt();
		String worldArmyId= message.getString(session);
		Message msg = cityService.OccupyCityMineOrFarm(role, cityId, buildId, worldArmyId);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

}