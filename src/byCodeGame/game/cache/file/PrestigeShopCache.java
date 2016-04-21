package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.PrestigeShop;

/**
 * 声望商城缓存
 * 
 * @author wcy 2015年12月28日
 *
 */
public class PrestigeShopCache {
	private static Map<Integer, PrestigeShop> map = new HashMap<>();
	/** <售卖类型,<商品id,PO>> */
	private static Map<Integer, Map<Integer, PrestigeShop>> map2 = new HashMap<>();
	/**<商品唯一id，商品表>*/
	private static Map<Integer,PrestigeShop> map3 = new HashMap<>();

	public static void putPrestigeShop(PrestigeShop prestigeShop) {
		int itemId = prestigeShop.getItemID();
		int type = prestigeShop.getType();
		int rowId = prestigeShop.getRowId();
		map.put(itemId, prestigeShop);
		map3.put(rowId, prestigeShop);
		
		Map<Integer, PrestigeShop> m = map2.get(type);
		if (m == null) {
			m = new HashMap<>();
			map2.put(type, m);
		}
		m.put(itemId, prestigeShop);
	}
	
	public static Map<Integer,Map<Integer,PrestigeShop>> getTypeItemIdMap(){
		return map2;
	}
	
	public static Map<Integer,PrestigeShop> getAlwaysItems(){
		return map2.get(0);
	}
	
	public static Map<Integer,PrestigeShop> getPrivateItems(){
		return map2.get(1);
	}
	
	public static Map<Integer,PrestigeShop> getPublicItems(){
		return map2.get(2);
	}
	
	public static PrestigeShop getPrestigeShopById(int id){
		return map3.get(id);
	}

}
