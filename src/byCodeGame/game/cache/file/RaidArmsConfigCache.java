package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.RaidArmsConfig;

public class RaidArmsConfigCache {
	private static Map<Integer, RaidArmsConfig> raidMap = new HashMap<Integer, RaidArmsConfig>();
	
	
	public static void putRaidArmsConfig(RaidArmsConfig raidArmsConfig){
		raidMap.put(raidArmsConfig.getRaidId() , raidArmsConfig);
	}
	
	
	public static RaidArmsConfig getRiadArmsConfig(int raidId){
		return raidMap.get(raidId);
	}
}
