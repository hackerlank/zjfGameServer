package byCodeGame.game.module.market.service;

import java.util.Map;

import byCodeGame.game.cache.local.MarketCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.MarketGoods;
import byCodeGame.game.module.market.MarketConstant;
import byCodeGame.game.remote.Message;

/**
 * 
 * @author wcy 2016年5月6日
 *
 */
public class MarketServiceImpl implements MarketService {

	@Override
	public void init() {

	}

	@Override
	public Message buyMarketGoods(Role role, int marketId, int num) {
		Message message = new Message();
		message.setType(MarketConstant.MARKET_BUY_GOODS);
		MarketGoods marketGoods = MarketCache.getMarketGoodsById(marketId);
		synchronized (marketGoods) {
			int currentNum = marketGoods.getNum();
			if (currentNum < num || currentNum == 0) {
				message.putShort(ErrorCode.MAKET_GOODS_LACK);
				return message;
			}
			int remainNum = currentNum - num;
			marketGoods.setNum(remainNum);
			message.putShort(ErrorCode.SUCCESS);
		}
		return message;
	}

	@Override
	public Message showMarketGoods() {
		Message message = new Message();
		message.setType(MarketConstant.MARKET_SHOW_GOODS);
		Map<Integer, MarketGoods> allMarketGoods = MarketCache.getAllGoods();
		int size = allMarketGoods.size();
		message.putInt(size);
		for (MarketGoods marketGoods : allMarketGoods.values()) {
			int marketId = marketGoods.getMarketId();
			int propId = marketGoods.getPropId();
			int num = marketGoods.getNum();
			
			message.putInt(marketId);
			message.putInt(propId);
			message.putInt(num);
		}
		return message;
	}

}
