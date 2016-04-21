package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Server;

/**
 * 
 * @author wcy
 *
 */
public class ServerConverter implements ResultConverter<Server> {

	@Override
	public Server convert(ResultSet rs) throws SQLException {
		Server server = new Server();
		server.setServerId(rs.getInt("serverId"));
		server.setStartTime(rs.getInt("startTime"));
		server.setSeason(rs.getByte("season"));
		server.setYear(rs.getInt("year"));
		server.setWorldItems(rs.getString("worldItems"));
		server.setWorldItemsRefreshTime(rs.getLong("worldItemsRefreshTime"));
		server.setRoleLvInfo(rs.getString("roleLvInfo"));
		server.setWorldLv(rs.getInt("worldLv"));
		server.setHistoryStr(rs.getString("history"));
		return server;
	}

}
