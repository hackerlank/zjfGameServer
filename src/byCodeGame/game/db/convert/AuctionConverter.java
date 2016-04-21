package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Auction;

public class AuctionConverter implements ResultConverter<Auction>{
	
	public Auction convert(ResultSet rs) throws SQLException {
		Auction auction = new Auction();
		auction.setUuid(rs.getString("uuid"));
		auction.setRoleId(rs.getInt("roleId"));
		auction.setType(rs.getByte("type"));
		auction.setItemId(rs.getInt("itemId"));
		auction.setStartTime(rs.getInt("startTime"));
		auction.setPassTime(rs.getInt("passTime"));
		auction.setCost(rs.getInt("cost"));
		auction.setIsDispose(rs.getByte("isDispose"));
		
		
		return auction;
	}
}
