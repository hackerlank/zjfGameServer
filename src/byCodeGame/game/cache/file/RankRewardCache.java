package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.po.RankReward;
/**
 * 
 * @author wcy 2016年3月5日
 *
 */
public class RankRewardCache {
	/**<类型,<排名号,po>>*/
	public static Map<Byte,Map<Integer,RankReward>> map = new HashMap<>();
	public static void putRankReward(RankReward rankReward){
		byte type = rankReward.getType();
		int num = rankReward.getRankNum();
		Map<Integer,RankReward> map2 = map.get(type);
		if(map2 == null){
			map2 = new HashMap<>();
			map.put(type,map2);
		}
		map2.put(num, rankReward);
	}
	
	public static Map<Byte,Map<Integer,RankReward>> getMap(){
		return map;
	}
}
