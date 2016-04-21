package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import byCodeGame.game.entity.file.VipConfig;
import byCodeGame.game.entity.po.ChapterReward;

public class VipConfigCache {
private static Map<Integer, VipConfig> vipConfigMap = new HashMap<Integer, VipConfig>();

private static Map<Integer, Set<ChapterReward>> awardMap = new HashMap<Integer, Set<ChapterReward>>();
	
	public static void putVipConfig(VipConfig vipConfig){
		 getVipConfigMap().put(vipConfig.getVipLv() , vipConfig);
	}
	
	public static VipConfig getVipConfigByVipLv(int vipLv){
		return getVipConfigMap().get(vipLv);
	}

	public static Map<Integer, VipConfig> getVipConfigMap() {
		return vipConfigMap;
	}

	public static Map<Integer, Set<ChapterReward>> getAwardMap() {
		return awardMap;
	}

	public static void setAwardMap(Map<Integer, Set<ChapterReward>> awardMap) {
		VipConfigCache.awardMap = awardMap;
	}
}
