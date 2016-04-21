package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.LeaderReward;

/**
 * 排行榜奖励
 * @author wcy 2016年3月5日
 *
 */
public class LeaderRewardCache {
	private static Map<Integer, LeaderReward> map = new HashMap<>();

	public static void putLeaderReward(LeaderReward leaderReward) {
		int id = leaderReward.getID();
		map.put(id, leaderReward);
	}

	public static LeaderReward getLeaderRewardById(int id) {
		return map.get(id);
	}
}
