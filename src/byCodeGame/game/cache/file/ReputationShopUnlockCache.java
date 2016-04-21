package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ReputationShopUnlock;

/**
 * 
 * @author wcy 2016年3月7日
 *
 */
public class ReputationShopUnlockCache {
	/**<viplv,po>*/
	private static Map<Byte, ReputationShopUnlock> map = new HashMap<>();
	/**<格子数量,po>*/
	private static Map<Integer,ReputationShopUnlock> map2 = new HashMap<>();
	
	private static int max = 0;
	private static int vip0Num = 0;

	public static void putReputationShopUnlock(ReputationShopUnlock reputationShopUnlock) {
		byte vip = (byte) reputationShopUnlock.getVipLevel();
		int num = reputationShopUnlock.getOpenNum();
		int cost = reputationShopUnlock.getCost();
		if (max < num) {
			max = num;
		}
		if (vip == 0 && cost == 0) {
			vip0Num++;
		}
		map.put(vip, reputationShopUnlock);
		map2.put(num, reputationShopUnlock);
	}

	public static ReputationShopUnlock getByVipLv(byte vipLv) {
		return map.get(vipLv);
	}
	
	public static ReputationShopUnlock getByOpenNum(int num){
		return map2.get(num);
	}
	/**
	 * 最大格子数量
	 * @return
	 * @author wcy 2016年3月7日
	 */
	public static int getMaxUnlock() {
		return max;
	}
	
	/**
	 * vip0情况下的免费格子数
	 * @return
	 * @author wcy 2016年3月7日
	 */
	public static int getVip0Num(){
		return vip0Num;
	}
}
