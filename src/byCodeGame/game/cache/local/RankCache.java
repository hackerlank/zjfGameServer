package byCodeGame.game.cache.local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.Rank;

/**
 * 
 * @author wcy
 *
 */
public class RankCache {
	public enum RankType {
		OFFENSIVE, ROLE_LV, INCOME, FIGHT_VALUE, OWN_CITY, KILL
	}

	private static HashMap<RankType, List<Rank>> recordRankMap = new HashMap<>();
	private static HashMap<RankType, ConcurrentHashMap<Integer, Rank>> currentRankMap = new HashMap<>();

	/**
	 * 根据类型获得当前排名
	 * 
	 * @param rankType
	 * @return
	 * @author wcy
	 */
	public static Map<Integer,Rank> getCurrentRankMapByRankType(RankType rankType) {
		ConcurrentHashMap<Integer, Rank> map = currentRankMap.get(rankType);
		if (map == null) {
			map = new ConcurrentHashMap<>();
			currentRankMap.put(rankType, map);
		}
		return map;
	}

	/**
	 * 设置排名记录
	 * 
	 * @param rankType
	 * @param rankList
	 * @author wcy
	 */
	public static void setRankRecord(RankType rankType, List<Rank> rankList) {
		recordRankMap.put(rankType, rankList);
	}

	/**
	 * 获得排名记录
	 * 
	 * @param rankType
	 * @return
	 * @author wcy
	 */
	public static List<Rank> getRankRecord(RankType rankType) {
		return recordRankMap.get(rankType);
	}

}
