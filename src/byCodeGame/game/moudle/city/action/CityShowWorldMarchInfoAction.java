package byCodeGame.game.moudle.city.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class CityShowWorldMarchInfoAction implements ActionSupport {

	private CityService cityService;
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		String id = message.getString(session);
		String cityLinkStr = message.getString(session);
		
		Message msg = cityService.showWorldMarchInfo(role,id,cityLinkStr);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

}