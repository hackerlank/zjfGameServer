package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.Item;

public class ItemCache {

	
	private static Map<Integer, Item> itemMap = new HashMap<Integer, Item>();
	
	public static void putItem(Item item){
		itemMap.put(item.getId(), item);
	}
	
	public static Item getItemById(int id){
		return itemMap.get(id);
	}
	
}
