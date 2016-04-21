package byCodeGame.game.moudle.vip.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.vip.service.VipService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetVipInfoAction implements ActionSupport{
	private VipService vipService;
	public void setVipService(VipService vipService) {
		this.vipService = vipService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		
		msg = vipService.getVipInfo(role);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
		
	}
}
