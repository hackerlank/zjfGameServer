package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.SkillConfig;
import byCodeGame.game.entity.file.SkillLvConfig;

/**
 * 
 * @author AIM
 *
 */
public class SkillLvConfigCache {
	private static Map<Integer, SkillLvConfig> map = new HashMap<>();
	private static Map<Integer,SkillLvConfig> lvMap = new HashMap<>();
	private static int minSkillLvId;

	public static void putSkillLvConfig(SkillLvConfig skillLvConfig) {
		int id = skillLvConfig.getId();
		int lv = skillLvConfig.getLv();
		map.put(id, skillLvConfig);
		lvMap.put(lv, skillLvConfig);
		if(skillLvConfig.getLv() == 1){
			minSkillLvId = id;
		}
	}
	
	public static SkillLvConfig getSkillLvConfigById(int id){
		return map.get(id);
	}
	
	public static SkillLvConfig getSkillLvConfigByLv(int lv){
		return lvMap.get(lv);
	}
	
	public static int getMinSkillLvId(){
		return minSkillLvId;
	}
}
