package byCodeGame.game.cache.file;

import java.util.HashMap;

import byCodeGame.game.entity.file.ArmyLvConfig;

public class ArmyLvConfigCache {
	private static HashMap<Integer,ArmyLvConfig> maps = new HashMap<Integer,ArmyLvConfig>();
	
	public static void putArmyLvConfig(ArmyLvConfig armyLvConfig){
		maps.put(armyLvConfig.getLv(), armyLvConfig);
	}
	public static ArmyLvConfig getArmyLvConfig(int lv){
		return maps.get(lv);
	}
	
	public static HashMap<Integer,ArmyLvConfig> getMaps(){
		return maps;
	}
}
