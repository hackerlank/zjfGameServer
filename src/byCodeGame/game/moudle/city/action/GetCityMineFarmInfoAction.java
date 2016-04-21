package byCodeGame.game.moudle.city.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetCityMineFarmInfoAction implements ActionSupport {

	private CityService cityService;
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		int buildId = message.getInt();
		int cityId = message.getInt();
		Role role = RoleCache.getRoleBySession(session);
		Message msg = cityService.getMineFarmInfo(role,cityId, buildId);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}
}