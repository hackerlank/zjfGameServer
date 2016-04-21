package byCodeGame.game.cache.file;

import java.util.HashMap;

import byCodeGame.game.entity.file.BarrackDrillAward;

/**
 * 
 * @author wcy
 *
 */
public class BarrackDrillAwardCache {
	/** <id,po> */
	private static HashMap<Integer, BarrackDrillAward> map = new HashMap<>();
	private static HashMap<Integer, BarrackDrillAward> map2 = new HashMap<>();
	private static BarrackDrillAward minBarrackDrillAward;

	public static void putBarrackDrillAward(BarrackDrillAward barrackDrillAward) {
		int filesId = barrackDrillAward.getFilesID();
		int min = barrackDrillAward.getMin();
		map.put(filesId, barrackDrillAward);
		map2.put(min, barrackDrillAward);

		if (min == 1) {
			minBarrackDrillAward = barrackDrillAward;
		}

	}

	public static HashMap<Integer, BarrackDrillAward> getBarrackDrillAwardMap() {
		return map;
	}

	/**
	 * 获得第一名
	 * 
	 * @param filesId
	 * @return
	 * @author wcy
	 */
	public static BarrackDrillAward getFirstBarrackDrillAward() {
		return minBarrackDrillAward;
	}

	/**
	 * 获得排名的区间段
	 * 
	 * @param rank 起始排名数
	 * @return
	 * @author wcy
	 */
	public static BarrackDrillAward getBarrackDrillAwardByRank(int rank) {
		return map2.get(rank);
	}
}
