package byCodeGame.game.moudle.city.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class ChangeDefInfoAction implements ActionSupport{
	private CityService cityService;
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int cityId = message.getInt();
		String id  = message.getString(session);
		int newId = message.getInt();
		msg = cityService.changeDefInfo(role, cityId, id, newId);
		if(msg == null)
		{
			return ;
		}else {
			session.write(msg);
		}
	}
}
