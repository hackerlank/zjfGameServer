package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ArmsResearchConfig;

public class ArmsResearchConfigCache {

	private static Map<Integer, ArmsResearchConfig> maps = new HashMap<Integer, ArmsResearchConfig>();
	
	public static void putArmsResearchConfig(ArmsResearchConfig armsResearchConfig){
		maps.put(armsResearchConfig.getId(), armsResearchConfig);
	}
	
	public static ArmsResearchConfig getArmsResearchConfigById(int id){
		return maps.get(id);
	}
	public static Map<Integer, ArmsResearchConfig> getArmsResearchConfigMap(){
		return maps;
	}
}
