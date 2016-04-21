package byCodeGame.game.moudle.market.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.market.service.MarketService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class MarketShowWorldMarketItemsAction implements ActionSupport{
	private MarketService marketService;
	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}
	
	public void execute(Message message, IoSession session) {
		
		Role role = RoleCache.getRoleBySession(session);
		
		Message msg = marketService.showWorldMarketItems(role);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
