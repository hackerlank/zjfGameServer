package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.MarketConverter;
import byCodeGame.game.db.dao.MarketDao;
import byCodeGame.game.entity.bo.Market;

public class MarketDaoImpl extends DataAccess implements MarketDao{
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void insertMarket(Market market,Connection conn) {
		final String sql = "insert into market(roleId,freshNumber,marketLimit,items,lastFreshTime,nextFreshNeed,worldItems,worldItemsRefreshTime)"
				+ "values(?,?,?,?,?,?,?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter() , conn, market.getRoleId(),market.getFreshNumber(),
					market.getMarketLimit(),market.getItems(),market.getLastFreshTime(),market.getNextFreshNeed(),market.getWorldItems(),market.getWorldItemsRefreshTime());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateMarket(Market market) {
		final String sql = "update market set freshNumber=?,marketLimit=?,items=?,lastFreshTime=?,nextFreshNeed=?,worldItems=?,worldItemsRefreshTime=? "
				+ "where roleId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,market.getFreshNumber(),market.getMarketLimit(),market.getItems(),market.getLastFreshTime(),market.getNextFreshNeed(),market.getWorldItems(),market.getWorldItemsRefreshTime(),
					market.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Market getMarketByRoleId(int roleId) {
		String sql = "select * from market where roleId=?";
		try {
			Connection conn = dataSource.getConnection();
			Market result =super.queryForObject(sql, new MarketConverter(), conn, roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
