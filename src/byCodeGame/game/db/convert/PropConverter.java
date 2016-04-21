package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Prop;


public class PropConverter implements ResultConverter<Prop> {

	public Prop convert(ResultSet rs) throws SQLException{
		Prop prop = new Prop();
		prop.setId(rs.getInt("id"));
		prop.setRoleId(rs.getInt("roleId"));
		prop.setFunctionType(rs.getByte("functionType"));
		prop.setItemId(rs.getInt("itemId"));
		prop.setNum(rs.getShort("num"));
		prop.setLv(rs.getShort("lv"));
		prop.setSlotId(rs.getByte("slotId"));
		prop.setUseID(rs.getInt("useId"));
		prop.setPrefixId(rs.getInt("prefixId"));
		return prop;
	}
}
