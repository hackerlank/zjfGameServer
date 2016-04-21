package byCodeGame.game.moudle.market.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.market.service.MarketService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetGoldMarketInfoAction implements ActionSupport{
	private MarketService marketService;
	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}
		

	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		
		msg = marketService.getGoldMarketInfo(role);
		if(msg == null)
		{
			return;	
		}else {
			session.write(msg);
		}
	}
}
