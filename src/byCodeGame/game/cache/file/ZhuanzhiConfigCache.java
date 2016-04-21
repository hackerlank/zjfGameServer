package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ZhuanzhiConfig;

public class ZhuanzhiConfigCache {
	private static Map<Integer, Map<Integer, ZhuanzhiConfig>> zz_map = new HashMap<Integer, Map<Integer, ZhuanzhiConfig>>();
	
	public static void putZhuanzhiConfig(ZhuanzhiConfig zhuanzhiConfig){
		Map<Integer, ZhuanzhiConfig> temp = zz_map.get(zhuanzhiConfig.getProfessionID());
		if(temp == null) zz_map.put(zhuanzhiConfig.getProfessionID(), new HashMap<Integer,ZhuanzhiConfig>());
		zz_map.get(zhuanzhiConfig.getProfessionID()).put(zhuanzhiConfig.getRank(), zhuanzhiConfig);
	}
	
	public static ZhuanzhiConfig getZhuanzhiConfig(int id ,int rank){
		return zz_map.get(id).get(rank);
	}
}
