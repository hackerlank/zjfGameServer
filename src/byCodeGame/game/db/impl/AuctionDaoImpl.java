package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.AuctionConverter;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.dao.AuctionDao;
import byCodeGame.game.entity.bo.Auction;

public class AuctionDaoImpl extends DataAccess implements AuctionDao{
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Auction> getAllAuction() {
		String sql = "select * from auction";
		try {
			Connection conn = dataSource.getConnection();
			List<Auction> result = super.queryForList(sql, new AuctionConverter(),
					conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void insterAuction(Auction auction) {
		final String sql = "insert into auction(uuid,roleId,type,itemId,num,startTime,passTime,cost,isDispose)"
				+ "values(?,?,?,?,?,?,?,?,?)";
		try {
			Connection conn = dataSource.getConnection();
			super.insert(sql, new IntegerConverter() ,conn,auction.getUuid(),
					auction.getRoleId(),auction.getType(),auction.getItemId(),auction.getNum(),
					auction.getStartTime(),auction.getPassTime(),auction.getCost(),auction.getIsDispose());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeAuction(String uuid) {
		String sql = "delete from auction where uuid = ?";
		try {
			Connection conn = dataSource.getConnection();
			super.delete(sql, conn, uuid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateAuction(Auction auction) {
		final String sql = "update auction set isDispose=? where uuid=? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,auction.getIsDispose(),auction.getUuid());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
