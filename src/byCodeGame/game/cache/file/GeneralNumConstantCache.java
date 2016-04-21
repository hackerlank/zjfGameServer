package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.GeneralNumConstant;

public class GeneralNumConstantCache {
	private static Map<String, GeneralNumConstant> maps = new HashMap<String, GeneralNumConstant>();

	public static void putGeneralNumConstant(GeneralNumConstant generalNumConstant) {
		maps.put(generalNumConstant.getKey(), generalNumConstant);
	}

	public static int getValue(String key) {
		return maps.get(key).getValue();
	}
}
