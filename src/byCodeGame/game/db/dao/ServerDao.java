package byCodeGame.game.db.dao;

import byCodeGame.game.entity.bo.Server;

public interface ServerDao {
	public void insertServer(Server server);
	
	public void updateServer(Server server);
	
	public Server getServer(int serverId);
}
