package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.db.convert.base.ResultConverter;
import byCodeGame.game.entity.bo.Kitchen;

/**
 * 
 * @author wcy 2016年5月3日
 *
 */
public class KitchenConverter implements ResultConverter<Kitchen> {

	@Override
	public Kitchen convert(ResultSet rs) throws SQLException {
		Kitchen kitchen = new Kitchen();
		kitchen.setRoleId(rs.getInt("roleId"));
		kitchen.setKitchenSpaceStr(rs.getString("kitchenSpaceStr"));
		
		return kitchen;
	}

}
