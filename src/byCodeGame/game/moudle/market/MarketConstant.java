package byCodeGame.game.moudle.market;


public class MarketConstant {
	/** 市场收购（玩家出售）			*/
	public static final short SELL_TO_MARKET = 21001;
	/** 市场出售 （玩家购买）			*/
	public static final short BUY_MARKET = 21002;
	/** 市场刷新 （玩家消耗元宝）		*/
	public static final short FRESH_MARKET = 21003;
	/** 获取市场信息				*/
	public static final short GET_MARKET_INFO = 21004;
	/** 获取藏宝阁信息				*/
	public static final short GET_GOLD_MARKET_INFO = 21005;
	/** 藏宝阁购买物品				*/
	public static final short BUY_GOLD_MARKET = 21006;
	/** 获取拍卖行信息				*/
	public static final short SHOW_AUCTION = 21007;
	/** 拍卖物品					*/
	public static final short SELL_AUCTION = 21008;
	/** 购买拍卖物					*/
	public static final short BUY_AUCTION = 21009;
	/** 下架拍卖物					*/
	public static final short CANCEL_AUCTION = 21010;

	/**倒卖*/
	public static final short VISIT_SALE = 21011;
	/**议价*/
	public static final short BARGAIN_ITEMS = 21012;
	/**显示议价*/
	public static final short SHOW_BARGIN_ITEMS = 21013;
	/**显示声望商城*/
	public static final short SHOW_WORLD_MARKET_ITEMS = 21014;
	/**购买声望商城*/
	public static final short BUY_WORLD_MARKET_ITEMS = 21015;
	/**刷新声望商城*/
	public static final short REFRESH_PRESTIGE_WORLD_MARKET = 21016;
	/**解锁声望商城格子*/
	public static final short UNLOCK_PRESTIGE_LIMIT = 21017;
	/** 显示自己的拍卖物品			*/
	public static final short SHOW_AUCTION_SELF = 21050;


	
	/** 物品是否可售   道具		*/
	public static final int CAN_NOT_SELL = 0;
	/** 物品是否可售   装备		*/
	public static final int CAN_SELL = 1;
	/** 物品是否已经出售	未售	*/
	public static final int IS_NOT_SELL = 0;
	/** 物品是否已经出售	已售	*/
	public static final int	IS_SELL = 1;
	
	/** 背包空余数量  是否可购买	*/
	public static final short BACKPACKAGE_IDLE = 1;
	/** 刷新后当日刷新次数+1	*/
	public static final int ADD_FRESH_NUMBER = 1;
	
	/** 物品出售后剩余0 		*/
	public static final short SELL_SURPLUS = 0;
//	/** 装备的品阶是否使用金币	*/
//	public static final int QUALITY_BUY = 4;
	
	/** 市场格子数量 玩家等级	*/
	public static final int MARKET_NUM_LV = 5;
	/** 市场格子数量 vip等级	*/
	public static final int MARKET_NUM_VIP = 0;
	/** 市场格子数量 增加值 2	*/
	public static final int MARKET_ADD_NUM = 2;
	/** 市场格子数量 增加值 1	*/
	public static final int MARKET_ADD_NUM_2 = 1;
	/** VIP 3			*/
	public static final int VIP_LV_3 = 3;
	/** VIP 5			*/
	public static final int VIP_LV_5 = 5;
	/** VIP 7			*/
	public static final int VIP_LV_7 = 7;
	/** VIP 9			*/
	public static final int VIP_LV_9 = 9;
	/** 市场格子数量 起始值	*/
	public static final int MARKET_NUM_STAR = 4;
	/** 道具随机的最小配置表编号*/
	public static final int PROP_RANDOM_MIN = 10001;
	/** 道具随机的最大配置表编号*/
	public static final int PROP_RANDOM_MAX = 10040;

	/** 市场第一次刷新的等级		*/
	public static final short FIRST_FRESH_MARKET = 10;
	
	/** 物品分类 装备:1			*/
	public static final byte TYPE_EQUIP = 9;
	/** 物品分类 道具:2			*/
	public static final byte TYPE_ITEM = 10;
	/** 物品分类 粮草:5			*/
	public static final byte TYPE_FOOD = 5;
	
	/** 拍卖物品是否已经处理		*/
	public static final byte IS_DISPOSE = 1;
	/** 拍卖最低价				*/
	public static final int LOWEST_PRICE_AUCTION = 10;
	
	public static final int WORLD_MARKET_ITEMS_ALWAYS = 0;
	public static final int WORLD_MARKET_ITEMS_PRIVATE = 1;
	public static final int WORLD_MARKET_ITEMS_PUBLIC = 2;

	
	
	
}
