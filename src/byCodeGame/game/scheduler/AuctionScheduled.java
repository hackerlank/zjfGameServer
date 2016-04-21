package byCodeGame.game.scheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import byCodeGame.game.cache.local.AuctionCache;
import byCodeGame.game.entity.bo.Auction;
import byCodeGame.game.moudle.market.service.MarketService;
import byCodeGame.game.util.SpringContext;
import byCodeGame.game.util.Utils;

public class AuctionScheduled {
	private static ScheduledExecutorService auctionScheduled = new ScheduledThreadPoolExecutor(
			1);
	
	public static void startScheduled() {
		upAuctionDB();
		upAuction();
		clearAuction();
	}
	
	private static void upAuctionDB() {
		auctionScheduled.scheduleAtFixedRate(new Runnable() {
			public void run() {
//				System.out.println("已经检查拍卖剩余时间小于30分的拍卖物");
				AuctionCache.getQuickCheckList().clear();
				for(Auction auction:AuctionCache.getAllMap().values())
				{
					if((auction.getStartTime() + auction.getPassTime() - Utils.getNowTime())
							<= 1800)
					{
						AuctionCache.addAuctionList(auction.getUuid());
					}
				}
			}
		}, 0, 30, TimeUnit.MINUTES);
	}
	private static void clearAuction() {
		auctionScheduled.scheduleAtFixedRate(new Runnable() {
			public void run() {
//				System.out.println("已经删除过期记录");
				MarketService  marketService = (MarketService)SpringContext.getBean("marketService");
				for(Auction auction:AuctionCache.getOffTimeMap().values())
				{
					marketService.clearAuction(auction);
				}
				AuctionCache.getOffTimeMap().clear();
			}
		}, 0, 12, TimeUnit.HOURS);
	}
	private static void upAuction() {
		auctionScheduled.scheduleAtFixedRate(new Runnable() {
			MarketService  marketService = (MarketService)SpringContext.getBean("marketService");
			public void run() {
//				System.out.println("5秒调度正在运行"+AuctionCache.getQuickCheckList().size());
				for(String str:AuctionCache.getQuickCheckList())
				{
					Auction temp = AuctionCache.getAllMap().get(str);
					if(temp == null) continue;
					marketService.checkAuctionTime(temp);
				}
			}
		}, 0, 5, TimeUnit.SECONDS);
	}
}
