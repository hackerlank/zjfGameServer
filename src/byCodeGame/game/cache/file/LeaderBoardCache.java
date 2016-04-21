package byCodeGame.game.cache.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byCodeGame.game.entity.file.LeaderBoard;

/**
 * 
 * @author wcy 2016年3月5日
 *
 */
public class LeaderBoardCache {
	/** <排名类型,区间列表> */
	private static Map<Integer, List<LeaderBoard>> map = new HashMap<>();

	public static void putLeaderBoard(LeaderBoard leaderBoard) {
		int type = leaderBoard.getType();
		List<LeaderBoard> list = map.get(type);
		if (list == null) {
			list = new ArrayList<>();
			map.put(type, list);
		}

		list.add(leaderBoard);
	}

	public static Map<Integer, List<LeaderBoard>> getLeaderBoardMap() {
		return map;
	}
}
