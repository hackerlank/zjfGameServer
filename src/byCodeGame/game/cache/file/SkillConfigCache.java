package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.SkillConfig;

public class SkillConfigCache {
	private static Map<Integer, SkillConfig> map = new HashMap<>();

	public static void putSkillConfig(SkillConfig config) {
		int skillId = config.getSkillId();
		map.put(skillId, config);
	}

	public static SkillConfig getSkillConfigById(int skillId){
		SkillConfig skillConfig = map.get(skillId);
		return skillConfig;
	}
}
