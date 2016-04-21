package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.AnimalConfig;

public class AnimalConfigCache {

	private static Map<Integer, AnimalConfig> maps = new HashMap<Integer, AnimalConfig>();
	
	public static void putAnimalConfig(AnimalConfig animalConfig){
		maps.put(animalConfig.getTimes(), animalConfig);
	}
	
	public static AnimalConfig getAnimalConfig(int times){
		return maps.get(times);
	}
}
