package byCodeGame.game.moudle.market.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.market.service.MarketService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class MarketBuyWorldMarketItemsAction implements ActionSupport{
	private MarketService marketService;
	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}
	
	public void execute(Message message, IoSession session) {		
		Role role = RoleCache.getRoleBySession(session);
		String id = message.getString(session);
		
		Message msg = marketService.buyWorldMarketItems(role, id);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
