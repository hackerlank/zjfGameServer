package byCodeGame.game.db.dao;

import java.util.List;

import byCodeGame.game.entity.bo.Auction;

/**
 * 拍卖行
 * @author xjd
 *
 */
public interface AuctionDao {
	/***
	 * 获取所有服务器排行商品信息
	 * @return
	 */
	public List<Auction> getAllAuction();
	
	/**
	 * 插入拍卖商品
	 * @param auction
	 */
	public void insterAuction(Auction auction);
	
	/**
	 * 清除拍卖商品
	 * @param uuid
	 */
	public void removeAuction(String uuid);
	
	/***
	 * 更新拍卖商品信息
	 * @param auction
	 */
	public void updateAuction(Auction auction);
}
