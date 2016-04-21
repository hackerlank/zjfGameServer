package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.po.StartRole;

/**
 * 服务器启动时获取所有玩家名、账号、ID
 * @author 王君辉
 *
 */
public class StartRoleConverter implements ResultConverter<StartRole> {

	public StartRole convert(ResultSet rs) throws SQLException{
		StartRole startRole = new StartRole();
		startRole.setId(rs.getInt("id"));
		startRole.setAccount(rs.getString("account"));
		startRole.setName(rs.getString("name"));
		startRole.setNation(rs.getByte("nation"));
		return startRole;
	}
//	                        .::::.
//	                      .::::::::.
//	                     ::::::::::::
//	                  ..::::::::::::'
//	               ':::::::::::::
//	                 .:::::::::::
//	
//	
//	
//	
//	
//	
//	
//	
	
}
