package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Market;

/**
 * 
 * @author xjd
 *
 */
public interface MarketDao {
	public void insertMarket(Market market,Connection conn);
	
	public void updateMarket(Market market);
	
	public Market getMarketByRoleId(int roleId);
}
