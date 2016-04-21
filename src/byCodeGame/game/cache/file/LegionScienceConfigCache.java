package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.LegionScienceConfig;

public class LegionScienceConfigCache {

	private static Map<Integer, LegionScienceConfig> lvMap = new HashMap<Integer, LegionScienceConfig>();
	private static Map<Integer, LegionScienceConfig> moneyMap = new HashMap<Integer, LegionScienceConfig>();
	private static Map<Integer, LegionScienceConfig> foodMap = new HashMap<Integer, LegionScienceConfig>();
	private static Map<Integer, LegionScienceConfig> expMap = new HashMap<Integer, LegionScienceConfig>();
	private static Map<Integer, LegionScienceConfig> exploitMap = new HashMap<Integer, LegionScienceConfig>();
	
	public static void putLegionScienceConfig(LegionScienceConfig legionScienceConfig){
		if(legionScienceConfig.getType() == 1){
			lvMap.put(legionScienceConfig.getLv(), legionScienceConfig);
		}else if(legionScienceConfig.getType() == 2){
			moneyMap.put(legionScienceConfig.getLv(), legionScienceConfig);
		}else if(legionScienceConfig.getType() == 3){
			foodMap.put(legionScienceConfig.getLv(), legionScienceConfig);
		}else if(legionScienceConfig.getType() == 4){
			expMap.put(legionScienceConfig.getLv(), legionScienceConfig);
		}else if(legionScienceConfig.getType() == 5){
			exploitMap.put(legionScienceConfig.getLv(), legionScienceConfig);
		}
	}
	
	public static LegionScienceConfig getLegionScienceConfig(int type , int lv){
		LegionScienceConfig legionScienceConfig = new LegionScienceConfig();
		if(type == 1){
			legionScienceConfig = lvMap.get(lv);
		}else if(type == 2){
			legionScienceConfig = moneyMap.get(lv);
		}else if(type == 3){
			legionScienceConfig = foodMap.get(lv);
		}else if(type == 4){
			legionScienceConfig = expMap.get(lv);
		}else if(type == 5){
			legionScienceConfig = exploitMap.get(lv);
		}
		return legionScienceConfig;
	}
}
