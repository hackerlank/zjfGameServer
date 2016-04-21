package byCodeGame.game.moudle.city.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.WorldArmy;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class SaveWorldFormationAction implements ActionSupport{
	private CityService cityService;
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int useFormationID = message.getInt();
		byte key = message.get();
		int heroId = message.getInt();
		String armyId = message.getString(session);
		WorldArmy temp = role.getWorldArmyById(armyId);
		if(temp != null)
		{
			msg = cityService.changeWorldArmyFormation(role, useFormationID, key, heroId,temp);
		}else {
			msg = cityService.saveWorldFormation(role, useFormationID, key, heroId);
		}
		
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
