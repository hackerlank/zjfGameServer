package byCodeGame.game.moudle.market.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.market.service.MarketService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class SellAuctionAction implements ActionSupport{
	private MarketService marketService;
	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int serviceId = message.getInt();
		byte type = message.get();
		int num = message.getInt();
		int cost = message.getInt();
		int time = message.getInt();
		
		msg = marketService.sellAuction(role, type, serviceId, num, cost, time);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
