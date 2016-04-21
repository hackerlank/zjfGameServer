package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import cn.bycode.game.battle.data.CorpData;
import cn.bycode.game.battle.data.TroopData;
import byCodeGame.game.entity.file.ChapterConfig;
import byCodeGame.game.util.PVPUitls;

public class ChapterConfigCache {

	private static Map<Integer, ChapterConfig> maps = new HashMap<Integer, ChapterConfig>();
	private static Map<Integer, TroopData> corpMap = new HashMap<Integer, TroopData>();
	
	public static void putChapterConfig(ChapterConfig chapterConfig){
		maps.put(chapterConfig.getId(), chapterConfig);
	}
	
	public static ChapterConfig getChapterConfigById(int id){
		return maps.get(id);
	}
	
	public static Map<Integer, ChapterConfig> getchapterData()
	{
		return maps;
	}
	
	public static Map<Integer, TroopData> getCorpMap()
	{
		return corpMap;
	}
	public static TroopData getTroopDataById(int id)
	{
		return corpMap.get(id);
	}
}
