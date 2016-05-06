package byCodeGame.game.module.market.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

/**
 * 
 * @author wcy 2016年5月6日
 *
 */
public interface MarketService {
	void init();

	/**
	 * 显示商场所有物品
	 * @param role
	 * @return
	 * @author wcy 2016年5月6日
	 */
	Message showMarketGoods();

	/**
	 * 购买商品
	 * @param role
	 * @param propConfigId
	 * @param num
	 * @return
	 * @author wcy 2016年5月6日
	 */
	Message buyMarketGoods(Role role, int marketId, int num);
}
