package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.SkillConfig;

public class SkillConfigCache {

	private static Map<Integer, SkillConfig> map = new HashMap<Integer, SkillConfig>();
	
	public static void putSkillConfig(SkillConfig skillConfig){
		map.put(skillConfig.getId(), skillConfig);
	}
	
	public static SkillConfig getSkillConfig(int id){
		return map.get(id);
	}
}
