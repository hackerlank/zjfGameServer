package byCodeGame.game.moudle.city.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class ChangeCoreInfoAction implements ActionSupport{
	private CityService cityService;
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int cityId = message.getInt();
		int id1 = message.getInt();
		int id2 = message.getInt();
		msg = cityService.changeCoreInfo(cityId, role, id1, id2);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
