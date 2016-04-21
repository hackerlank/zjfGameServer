package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.FormationConfig;

public class FormationConfigCache {

	private static Map<Integer, FormationConfig> maps = new HashMap<Integer, FormationConfig>();
	
	public static void putFormationConfig(FormationConfig formationConfig){
		getMaps().put(formationConfig.getId(), formationConfig);
	}
	
	public static  FormationConfig getFormationConfigById(int id){
		return getMaps().get(id);
	}

	public static Map<Integer, FormationConfig> getMaps() {
		return maps;
	}
}
