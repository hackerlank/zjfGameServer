package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.OpenTrainSeatConfig;

public class OpenTrainSeatConfigCache {

	/**
	 * key:id  value:cost
	 */
	private static Map<Integer, Integer> maps = new HashMap<Integer, Integer>();
	
	public static void putOpenTrainSeatConfig(OpenTrainSeatConfig openTrainSeatConfig){
		maps.put(openTrainSeatConfig.getId(), openTrainSeatConfig.getCost());
	}
	
	public static int getCostById(int id){
		return maps.get(id);
	}
	
}
