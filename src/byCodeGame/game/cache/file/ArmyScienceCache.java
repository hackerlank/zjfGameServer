package byCodeGame.game.cache.file;

import java.util.HashMap;

import byCodeGame.game.entity.file.ArmyScience;

/**
 * 
 * @author wcy
 *
 */
public class ArmyScienceCache {
	private static HashMap<Integer, ArmyScience> map = new HashMap<>();
	private static HashMap<Integer, ArmyScience> map2 = new HashMap<>();

	public static void putArmyScience(ArmyScience armyScience) {
		int id = armyScience.getId();
		int lv = armyScience.getLv();
		int armySkillId = armyScience.getArmySkillID();
		if (lv == 1) {
			map2.put(armySkillId, armyScience);
		}
		map.put(id, armyScience);
	}

	public static ArmyScience getMinArmyScience(int armySkillId) {
		return map2.get(armySkillId);
	}

	public static ArmyScience getArmyScienceById(int id) {
		return map.get(id);
	}

}
