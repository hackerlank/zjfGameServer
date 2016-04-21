package byCodeGame.game.cache.local;

import byCodeGame.game.entity.bo.BattleLobby;

public class BattleLobbyCache {
	private static BattleLobby battleLobby;

	public static BattleLobby getBattleLobby() {
		return battleLobby;
	}

	public static void setBattleLobby(BattleLobby battleLobby) {
		BattleLobbyCache.battleLobby = battleLobby;
	}
}
