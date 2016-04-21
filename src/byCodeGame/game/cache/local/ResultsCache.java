package byCodeGame.game.cache.local;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class ResultsCache {
	private static ConcurrentMap<String, ResultData> roleMap = new ConcurrentHashMap<String, ResultData>();

	public static ConcurrentMap<String, ResultData> getRoleMap() {
		return roleMap;
	}

	public static ResultData getRoleMapByUUID(String uuid) {
		return roleMap.get(uuid);
	}
	public static void putRoleMap(ResultData resultData) {
		 roleMap.put(resultData.uuid, resultData);
	}
}
