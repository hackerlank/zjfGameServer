package byCodeGame.game.module.market.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.module.market.service.MarketService;
import byCodeGame.game.module.register.service.RegisterService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

/**
 * 注册
 * @author wcy 2016年4月29日
 *
 */
public class MarketBuyGoodsAction implements ActionSupport {
	private MarketService marketService;
	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		int marketId = message.getInt();
		int num = message.getInt();
		
		message = marketService.buyMarketGoods(role, marketId, num);
		if(message != null){
			session.write(message);
		}
	}

}
