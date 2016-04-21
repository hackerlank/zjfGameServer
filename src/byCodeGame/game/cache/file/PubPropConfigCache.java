package byCodeGame.game.cache.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import byCodeGame.game.entity.file.PubPropConfig;

public class PubPropConfigCache {
	private static HashMap<Integer,PubPropConfig> maps = new HashMap<>();
	private static List<PubPropConfig> list = new ArrayList<>();
	public static void putPubPropConfig(PubPropConfig pubPropConfig){
		maps.put(pubPropConfig.getPropId(),pubPropConfig);
		list.add(pubPropConfig);
	}
	public static PubPropConfig getPubPropConfigByPropId(int propId){
		return maps.get(propId);
	}
	public static List<PubPropConfig> getPubPropConfigList(){
		return list;
	}
}
