package byCodeGame.game.moudle.vip.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.vip.service.VipService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class RechargeAction implements ActionSupport {

	private VipService vipService;
	public void setVipService(VipService vipService) {
		this.vipService = vipService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		
		int num = message.getInt();
		String account = message.getString(session);
		int serverId = message.getInt();
		String parterId = message.getString(session);
		String orderNo = message.getString(session);
		Message msg = vipService.recharge(account, num,serverId,parterId,orderNo);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

	

}
