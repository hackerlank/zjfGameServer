package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Market;

public class MarketConverter implements ResultConverter<Market>{
	public Market convert(ResultSet rs) throws SQLException {
		Market market = new Market();
		market.setRoleId(rs.getInt("roleId"));
		market.setFreshNumber(rs.getByte("freshNumber"));
		market.setMarketLimit(rs.getByte("marketLimit"));
		market.setItems(rs.getString("items"));
		market.setLastFreshTime(rs.getLong("lastFreshTime"));
		market.setNextFreshNeed(rs.getLong("nextFreshNeed"));
		market.setWorldItems(rs.getString("worldItems"));
		market.setWorldItemsRefreshTime(rs.getLong("worldItemsRefreshTime"));
		
		return market;
	}
}
