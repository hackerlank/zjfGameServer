package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ArenaRobot;

public class ArenaRobotCache {

	private static Map<Integer, ArenaRobot> map = new HashMap<Integer, ArenaRobot>();
	
	public static void putArenaRobot(ArenaRobot arenaRobot){
		map.put(arenaRobot.getLv(), arenaRobot);
	}
	
	public static ArenaRobot getArenaRobot(int lv){
		return map.get(lv);
	}
}
