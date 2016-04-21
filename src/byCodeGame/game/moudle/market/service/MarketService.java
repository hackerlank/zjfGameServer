package byCodeGame.game.moudle.market.service;

import java.util.Map;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Auction;
import byCodeGame.game.entity.bo.Market;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.PrestigeShop;
import byCodeGame.game.entity.po.WorldMarketItems;
import byCodeGame.game.remote.Message;

public interface MarketService {
	/**
	 * 市场收购（玩家出售）
	 * 
	 * @param role
	 * @param id 道具id（数据库id）
	 * @param number 道具数量
	 * @return
	 * @author xjd
	 */
	public Message sell(Role role, int id, short number);

	/**
	 * 市场出售（玩家购买）
	 * 
	 * @param role
	 * @param id 道具配置表id
	 * @return
	 * @author xjd
	 */
	public Message buy(Role role, int id, IoSession is);

	/**
	 * 市场刷新（玩家主动点击，消耗元宝）
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message fresh(Role role);

	/**
	 * 获取玩家的市场信息
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getMarketInfo(Role role);

	/**
	 * 获取藏宝阁信息
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getGoldMarketInfo(Role role);

	/***
	 * 藏宝阁购买
	 * 
	 * @param role
	 * @return
	 */
	public Message buyGoldMarket(Role role, int id, IoSession is);

	/**
	 * 自动刷新市场 用于调度器
	 * 
	 * @param role
	 */
	public void changMarket(Role role);

	/**
	 * 检查下线玩家再次登录时是否需要刷新市场
	 * 
	 * @param role
	 * @author xjd
	 */
	public void checkLastChange(Role role);

	/**
	 * 委派武将倒卖
	 * 
	 * @param propId
	 * @param num
	 * @return
	 * @author wcy
	 */
	public Message visitSale(Role role, int heroId, int itemSqlId, int num);

	/**
	 * 议价
	 * 
	 * @param role
	 * @param heroId
	 * @param id
	 * @return
	 * @author wcy
	 */
	public Message bargainItems(Role role, int id);

	/**
	 * 显示议价
	 * 
	 * @param role
	 * @param heroId
	 * @param id
	 * @return
	 * @author wcy
	 */
	public Message showBargainItems(Role role, int id);

	/**
	 * 初始化服务器拍卖行信息
	 * 
	 * @author xjd
	 */
	public void initAuction();

	/**
	 * <font color="red">清理拍卖物品请勿手动调用！<font/>
	 * 
	 * @author xjd
	 */
	public void clearAuction(Auction auction);

	/**
	 * 调度器使用检查<br/>
	 * 将拍卖失败的物品 isDispose更改 处理邮件,将auction移动到offTimeMap，并从allMap中移除
	 */
	public void checkAuctionTime(Auction auction);

	/**
	 * 获取拍卖行信息
	 * 
	 * @return
	 */
	public Message showAuctionInfo(Role role);

	/**
	 * 拍卖物品
	 * 
	 * @param role
	 * @param type
	 * @param serviceId
	 * @param num
	 * @return
	 * @author xjd
	 */
	public Message sellAuction(Role role, byte type, int serviceId, int num, int cost, int time);

	/**
	 * 购买拍卖物
	 * 
	 * @param role
	 * @param uuid
	 * @return
	 * @author xjd
	 */
	public Message buyAuction(Role role, String uuid);

	/**
	 * 下架拍卖物品
	 * 
	 * @param role
	 * @param uuid
	 * @return
	 * @author xjd
	 */
	public Message cancelAuction(Role role, String uuid);

	/**
	 * 显示本人的拍卖物品
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message showAuctionSelf(Role role);
	
	/**
	 * 初始化market
	 * @return
	 * @author wcy 2015年12月28日
	 */
	public void initMarket(Role role,int nowTime);
	
	/**
	 * 显示声望商城
	 * @param role
	 * @return
	 * @author wcy 2015年12月28日
	 */
	public Message showWorldMarketItems(Role role);
	
	/**
	 * 购买世界商品
	 * @param role
	 * @param id
	 * @return
	 * @author wcy 2015年12月28日
	 */
	public Message buyWorldMarketItems(Role role,String id);
	
	/**
	 * 刷新声望商城的物品
	 * @param role
	 * @author wcy 2015年12月28日
	 */
	public void refreshPrestigeWorldMarketItems(Role role,int nowTime);
	
	/**
	 * 解锁声望商城的数量
	 * @param role
	 * @return
	 * @author wcy 2016年3月5日
	 */
	public Message unlockPrestigeMarketLimit(Role role);
	
	/**
	 * 刷新声望商城的物品
	 * @param role
	 * @return
	 * @author wcy 2016年3月5日
	 */
	public Message refreshPrestigeWorldMarketItem(Role role);
}
