package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Recharge;

public class RechargeConverter implements ResultConverter<Recharge> {
	public Recharge convert(ResultSet rs) throws SQLException {
		Recharge recharge = new Recharge();
		recharge.setAccount(rs.getString("account"));
		recharge.setOrderNo(rs.getString("orderNo"));
		recharge.setNum(rs.getInt("num"));
		recharge.setTime(rs.getString("time"));
		recharge.setPartnerId(rs.getInt("partnerId"));
		recharge.setServerId(rs.getInt("serverId"));
		recharge.setRoleId(rs.getInt("roleId"));
		return recharge;
	}
}
