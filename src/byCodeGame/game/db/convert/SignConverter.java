package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;


import byCodeGame.game.entity.bo.Sign;

public class SignConverter implements ResultConverter<Sign>{
	
	public Sign convert(ResultSet rs) throws SQLException {
		Sign sign = new Sign();
		sign.setRoleId(rs.getInt("roleId"));
		sign.setSignMonth(rs.getByte("signMonth"));
		sign.setSignDays(rs.getString("signDays"));
		sign.setSignAward(rs.getString("signAward"));
		sign.setSignRetroactive(rs.getString("signRetroactive"));
		
		return sign;
	}
}
