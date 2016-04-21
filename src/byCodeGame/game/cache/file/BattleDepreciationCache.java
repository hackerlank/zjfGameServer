package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.BattleDepreciation;

public class BattleDepreciationCache {
	private static Map<Integer, BattleDepreciation> maps = new HashMap<Integer, BattleDepreciation>();
	
	
	
	public static void putBattleDepreciation(BattleDepreciation battleDepreciation){
		maps.put(battleDepreciation.getKill(), battleDepreciation);
	}
	
	public static BattleDepreciation getBattleDepreciation(int kill){
		if(maps.containsKey(kill))
		{
			return maps.get(kill);
		}else {
			return maps.get(maps.size()-1);
		}
		
	}
}
