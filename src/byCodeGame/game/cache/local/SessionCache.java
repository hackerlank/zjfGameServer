package byCodeGame.game.cache.local;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

/**
 * 对话缓存
 * @author wcy 2016年4月22日
 *
 */
public class SessionCache {
	private static Map<Integer,IoSession> allSessionMap = new ConcurrentHashMap<>();
	
	public static Map<Integer,IoSession> getAllSession(){
		return allSessionMap;
	}
	
	public static IoSession getSessionByRoleId(int roleId){
		return allSessionMap.get(roleId);
	}
	
	public static void removeSessionById(int roleId){
		allSessionMap.remove(roleId);
	}
	
	public static void addSession(int roleId,IoSession session){
		allSessionMap.put(roleId, session);
	}
}
