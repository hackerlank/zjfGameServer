package byCodeGame.game.moudle.city.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

/**
 * 进村
 * @author wcy 2016年3月4日
 *
 */
public class CityGoToVillageAction implements ActionSupport {

	private CityService cityService;

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		Integer cityId = message.getInt();
		Message msg = cityService.goToVillage(role, cityId);
		if (msg == null) {
			return;
		} else {
			session.write(msg);
		}
	}

}