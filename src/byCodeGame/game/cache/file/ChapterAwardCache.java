package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ChapterAward;

/***
 * 需要时请添加Map_X
 * @author xjd
 *
 */
public class ChapterAwardCache {
	/** 已编号为主键	*/
	private static Map<Integer, ChapterAward> all_map = new HashMap<Integer, ChapterAward>();
	private static Map<Integer, HashMap<Integer, ChapterAward>> all = new HashMap<Integer, HashMap<Integer,ChapterAward>>();
	
	public static void putChapterAward(ChapterAward chapterAward){
		
		all_map.put(chapterAward.getRecordNo(), chapterAward);
		
		Map<Integer, ChapterAward> temp = all.get(chapterAward.getChapterId());
		if(temp == null) all.put(chapterAward.getChapterId(), new HashMap<Integer,ChapterAward>());
		all.get(chapterAward.getChapterId()).put(chapterAward.getProcess(), chapterAward);
	}
	
	public static ChapterAward getChapterAwardById(int id , int process){
		return all.get(id).get(process);
	}
	
	public static ChapterAward getChapterAwardById(int recId){
		return all_map.get(recId);
	}
	
	public static HashMap<Integer, ChapterAward> getChapterAwardMap(int id)
	{
		return all.get(id);
	}
	
	public static Map<Integer, ChapterAward> getAll()
	{
		return all_map;
	}
}
