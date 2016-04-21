package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.Profession;

public class ProfessionCache {
	private static Map<Integer, Profession> pro_map = new HashMap<Integer, Profession>();
	
	public static void putProfession(Profession profession){
		pro_map.put(profession.getId(), profession);
	}
	
	public static Profession getProfessionById(int id){
		return pro_map.get(id);
	}
}
