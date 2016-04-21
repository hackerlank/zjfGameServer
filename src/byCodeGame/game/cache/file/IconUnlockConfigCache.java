package byCodeGame.game.cache.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byCodeGame.game.entity.file.IconUnlockConfig;

public class IconUnlockConfigCache {
	
	private static Map<Integer,List<IconUnlockConfig> > chapterUnlockMap = new HashMap<Integer, List<IconUnlockConfig>>();
	
	private static Map<Integer, List<IconUnlockConfig>> levelUnlockMap = new HashMap<Integer, List<IconUnlockConfig>>();
	
	private static Map<String,IconUnlockConfig> map = new HashMap<>();
	
	public static void putIconUnlockConfig(IconUnlockConfig iconUnlockConfig){
		if("chapter".equals(iconUnlockConfig.getUnlockType())){
			List<IconUnlockConfig> list=chapterUnlockMap.get(iconUnlockConfig.getValue());
			if(null==list){
				list=new ArrayList<IconUnlockConfig>();
			}
			list.add(iconUnlockConfig);
			chapterUnlockMap.put(iconUnlockConfig.getValue(), list);
		}
		if("level".equals(iconUnlockConfig.getUnlockType())){
			List<IconUnlockConfig> list=levelUnlockMap.get(iconUnlockConfig.getValue());
			if(null==list){
				list=new ArrayList<IconUnlockConfig>();
			}
			list.add(iconUnlockConfig);
			levelUnlockMap.put(iconUnlockConfig.getValue(), list);
		}
		
		map.put(iconUnlockConfig.getName(), iconUnlockConfig);
	}

	public static Map<Integer, List<IconUnlockConfig>> getChapterUnlockMap() {
		return chapterUnlockMap;
	}

	public static void setChapterUnlockMap(
			Map<Integer, List<IconUnlockConfig>> chapterUnlockMap) {
		IconUnlockConfigCache.chapterUnlockMap = chapterUnlockMap;
	}

	public static Map<Integer, List<IconUnlockConfig>> getLevelUnlockMap() {
		return levelUnlockMap;
	}

	public static void setLevelUnlockMap(
			Map<Integer, List<IconUnlockConfig>> levelUnlockMap) {
		IconUnlockConfigCache.levelUnlockMap = levelUnlockMap;
	}
	
	public static List<IconUnlockConfig> getLevelUnlockList(int level) {
		return levelUnlockMap.get(level);
	}
	
	public static List<IconUnlockConfig> getChapterUnlockList(int chapterId) {
		return chapterUnlockMap.get(chapterId);
	}
	
	public static Map<String,IconUnlockConfig> getIconUnlockConfigMap(){
		return map;
	}
	
	public static IconUnlockConfig getIconUnlockConfigByName(String name){
		return map.get(name);
	}
}
