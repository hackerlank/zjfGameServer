package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ChapterArmsConfig;

public class ChapterArmsConfigCache {

	private static Map<String, ChapterArmsConfig> maps = new HashMap<String, ChapterArmsConfig>();
	
	public static void putChapterArmsConfig(ChapterArmsConfig chapterArmsConfig){
		maps.put(chapterArmsConfig.getId(), chapterArmsConfig);
	}
	
	public static ChapterArmsConfig getChapterArmsConfig(String id){
		return maps.get(id);
	}
	
}
