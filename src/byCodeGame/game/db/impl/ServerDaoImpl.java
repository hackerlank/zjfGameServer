package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.ServerConverter;
import byCodeGame.game.db.dao.ServerDao;
import byCodeGame.game.entity.bo.Server;

/**
 * 
 * @author wcy
 *
 */
public class ServerDaoImpl extends DataAccess implements ServerDao {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insertServer(Server server) {
		final String sql = "insert into server(serverId,startTime,season,year,worldItems,worldItemsRefreshTime,worldLv,roleLvInfo) values(?,?,?,?,?,?,?,?)";
		try {
			Connection conn = dataSource.getConnection();
			super.insert(sql, new IntegerConverter(), conn, server.getServerId(), server.getStartTime(),server.getSeason(),server.getYear(),server.getWorldItems(),server.getWorldItemsRefreshTime(),server.getWorldLv(),server.getRoleLvInfo());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateServer(Server server) {
		final String sql = "update server set startTime=?,season=?,year=?,worldItems=?,worldItemsRefreshTime=?,worldLv=?,roleLvInfo=?,history=?  where serverId = ? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, server.getStartTime(),server.getSeason(),server.getYear(),server.getWorldItems(),server.getWorldItemsRefreshTime(),server.getWorldLv(),server.getRoleLvInfo(),
					server.getHistoryStr(),
					server.getServerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Server getServer(int serverId) {
		final String sql = "select * from server where serverId = ? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			Server result = super.queryForObject(sql, new ServerConverter(), conn, serverId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
