package byCodeGame.game.cache.local;

import byCodeGame.game.entity.bo.Server;

public class ServerCache {
	private static Server server = null;

	public static Server getServer() {
		return server;
	}

	public static void setServer(Server server) {
		if (ServerCache.server == null) {
			ServerCache.server = server;
		}
	}
}
