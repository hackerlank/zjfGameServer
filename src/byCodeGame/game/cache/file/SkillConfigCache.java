package byCodeGame.game.cache.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byCodeGame.game.entity.file.SkillConfig;

/**
 * 技能表
 * 
 * @author AIM
 *
 */
public class SkillConfigCache {
	private static Map<Integer, SkillConfig> map = new HashMap<>();
	private static List<Integer> idList = new ArrayList<>();

	public static void putSkillConfig(SkillConfig config) {
		int id = config.getSkillId();
		map.put(id, config);
	}

	public static SkillConfig getSkillConfigById(int id) {
		return map.get(id);
	}

	/**
	 * 所有技能的id列表
	 * 
	 * @return
	 */
	public static List<Integer> getIdList() {
		return idList;
	}
}
