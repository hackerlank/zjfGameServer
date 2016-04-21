package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ReputationShopRefresh;
/**
 * 
 * @author wcy 2016年3月7日
 *
 */
public class ReputationShopRefreshCache {
	/**<刷新次数,po>*/
	private static Map<Integer, ReputationShopRefresh> map = new HashMap<>();
	
	private static int maxGold = 0;;

	public static void putReputationShopRefresh(ReputationShopRefresh reputationShopRefresh) {
		int times = reputationShopRefresh.getRefreshTimes();
		int gold = reputationShopRefresh.getCost();
		map.put(times, reputationShopRefresh);
		
		if (gold > maxGold) {
			maxGold = gold;
		}
	}

	public static ReputationShopRefresh getByRefreshTimes(int times) {
		return map.get(times);
	}
	
	public static int getMaxGold(){
		return maxGold;
	}
}
