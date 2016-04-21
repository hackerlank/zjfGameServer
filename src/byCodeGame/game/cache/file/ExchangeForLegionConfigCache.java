package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ExchangeForLegionConfig;

public class ExchangeForLegionConfigCache {
private static Map<Integer, ExchangeForLegionConfig> equip_Map =new HashMap<Integer, ExchangeForLegionConfig>();
private static Map<Integer, ExchangeForLegionConfig> item_Map =new HashMap<Integer, ExchangeForLegionConfig>();
private static Map<Integer, ExchangeForLegionConfig> hero_Map =new HashMap<Integer, ExchangeForLegionConfig>();
private static Map<Integer, ExchangeForLegionConfig> hero_id_Map =new HashMap<Integer, ExchangeForLegionConfig>();
	public static void putExchangeForLegionConfig(ExchangeForLegionConfig exchangeForLegionConfig){
		if(exchangeForLegionConfig.getType() == 1)
		{
			equip_Map.put(exchangeForLegionConfig.getItemId(), exchangeForLegionConfig);
		}else if (exchangeForLegionConfig.getType() == 2) {
			item_Map.put(exchangeForLegionConfig.getItemId(), exchangeForLegionConfig);
		}else if (exchangeForLegionConfig.getType() == 3) {
			hero_Map.put(exchangeForLegionConfig.getItemId(), exchangeForLegionConfig);
			hero_id_Map.put(exchangeForLegionConfig.getId(), exchangeForLegionConfig);
		}
	}
			
	public static ExchangeForLegionConfig getExchangeForLegionConfig(int itemId,int type){
		ExchangeForLegionConfig exchangeForLegionConfig = new ExchangeForLegionConfig();
		if(type == 1)
		{
			exchangeForLegionConfig = equip_Map.get(itemId);
		}else if (type == 2) {
			exchangeForLegionConfig = item_Map.get(itemId);
		}else if (type == 3) {
			exchangeForLegionConfig = hero_Map.get(itemId);
		}
		
		return exchangeForLegionConfig;
	}
	/**
	 * 装备配置表
	 * @return
	 */
	public static Map<Integer, ExchangeForLegionConfig> getEMap()
	{
		return equip_Map;
	}
	
	/**
	 * 道具配置表
	 * @return
	 */
	public static Map<Integer, ExchangeForLegionConfig> getIMap()
	{
		return item_Map;
	}
	
	/**
	 * 英雄配置表
	 * @return
	 */
	public static Map<Integer, ExchangeForLegionConfig> getHMap()
	{
		return hero_Map;
	}
	
	/***
	 * 	根据序号返回英雄
	 *	仅当兑换物品为英雄时可以调用
	 */
	public static ExchangeForLegionConfig getHeroById(int id)
	{
		return hero_id_Map.get(id);
	}
	
}
