package byCodeGame.game.cache.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byCodeGame.game.entity.bo.Auction;

/**
 * 全服拍卖行商品缓存
 * @author xjd
 *
 */
public class AuctionCache {
	private static Map<String, Auction> allMap = new HashMap<String, Auction>();
	/** 本次服务器启动间产生的过期或者已经处理的拍卖物品		*/
	private static Map<String, Auction> offTimeMap = new HashMap<String, Auction>();
	/** 每5秒被检查一次的List							*/
	private static List<String> quickCheckList = new ArrayList<String>();
	
	public static Auction getAuctionById(String uuid)
	{
		return getAllMap().get(uuid);
	}
	
	public static synchronized void  addAuctionCache(Auction auction)
	{
		AuctionCache.allMap.put(auction.getUuid(), auction);
	}
	
	public static synchronized void removeAuction(Auction auction)
	{
		AuctionCache.allMap.remove(auction.getUuid());
	}
	
	public static synchronized void addOffTime(Auction auction)
	{
		AuctionCache.offTimeMap.put(auction.getUuid(), auction);
	}
	
	public static void addAuctionList(String uuid)
	{
		AuctionCache.quickCheckList.add(uuid);
	}
	
	public static Map<String, Auction> getAllMap() {
		return allMap;
	}

	public static void setAllMap(Map<String, Auction> allMap) {
		AuctionCache.allMap = allMap;
	}

	public static Map<String, Auction> getOffTimeMap() {
		return offTimeMap;
	}

	public static void setOffTimeMap(Map<String, Auction> offTimeMap) {
		AuctionCache.offTimeMap = offTimeMap;
	}

	public static List<String> getQuickCheckList() {
		return quickCheckList;
	}

	public static void setQuickCheckList(List<String> quickCheckList) {
		AuctionCache.quickCheckList = quickCheckList;
	}
	
	
}
