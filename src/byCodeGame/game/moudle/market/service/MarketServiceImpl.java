package byCodeGame.game.moudle.market.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.CityInfoConfigCache;
import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.FreshLimitCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.GoldMarketConfigCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.ItemCache;
import byCodeGame.game.cache.file.PrestigeHeroCache;
import byCodeGame.game.cache.file.ReputationShopRefreshCache;
import byCodeGame.game.cache.file.ReputationShopUnlockCache;
import byCodeGame.game.cache.file.StrengthenConfigCache;
import byCodeGame.game.cache.local.AuctionCache;
import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.AuctionDao;
import byCodeGame.game.db.dao.PropDao;
import byCodeGame.game.entity.bo.Auction;
import byCodeGame.game.entity.bo.Build;
import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Mail;
import byCodeGame.game.entity.bo.Market;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.CityInfoConfig;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.FreshLimit;
import byCodeGame.game.entity.file.GoldMarketConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.Item;
import byCodeGame.game.entity.file.PrestigeHero;
import byCodeGame.game.entity.file.PrestigeShop;
import byCodeGame.game.entity.file.ReputationShopRefresh;
import byCodeGame.game.entity.file.ReputationShopUnlock;
import byCodeGame.game.entity.file.StrengthenConfig;
import byCodeGame.game.entity.po.BuildInfo;
import byCodeGame.game.entity.po.LevyInfo;
import byCodeGame.game.entity.po.MarketItems;
import byCodeGame.game.entity.po.WorldMarketItems;
import byCodeGame.game.entity.po.WorldTempMarketItem;
import byCodeGame.game.moudle.chapter.ChapterConstant;
import byCodeGame.game.moudle.hero.HeroConstant;
import byCodeGame.game.moudle.inCome.InComeConstant;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.moudle.market.MarketConstant;
import byCodeGame.game.moudle.prop.PropConstant;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.HeroUtils;
import byCodeGame.game.util.InComeUtils;
import byCodeGame.game.util.Utils;

public class MarketServiceImpl implements MarketService {
	private PropDao propDao;
	private PropService propService;

	public void setPropDao(PropDao propDao) {
		this.propDao = propDao;
	}

	public void setPropService(PropService propService) {
		this.propService = propService;
	}

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private InComeService inComeService;

	public void setInComeService(InComeService inComeService) {
		this.inComeService = inComeService;
	}

	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private AuctionDao auctionDao;

	public void setAuctionDao(AuctionDao auctionDao) {
		this.auctionDao = auctionDao;
	}

	private MailService mailService;

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
	

	/**
	 * 市场收购功能(玩家出售)
	 * 
	 * @author xjd
	 */
	public Message sell(Role role, int id, short number) {
		// //等级过滤
		// if(role.getLv() < MarketConstant.FIRST_FRESH_MARKET)
		// {
		// return null;
		// }
		Message message = new Message();
		message.setType(MarketConstant.SELL_TO_MARKET);
		// 获取出售的道具
		Prop prop = role.getPropMap().get(id);
		// 判断道具是否合法
		if (prop == null) {
			message.putShort(ErrorCode.NO_PROP);
			return message;
		}
		// //判断玩家是否持有该道具
		// Map<Integer, Prop> map = role.getBackPack();
		// if(!map.containsKey(prop.getId( )))
		// {
		// message.putShort(ErrorCode.NO_PROP);
		// return message;
		// }
		// 判断玩家是否持有足够数量的道具
		if (number > prop.getNum()) {
			message.putShort(ErrorCode.ERR_NUMBER_SELL);
			return message;
		}
		int getMoney = 0;
		// 判断道具类型
		if (prop.getFunctionType() == PropConstant.FUNCTION_TYPE_1) {
			// 从配置表中取出对应的道具配置 装备
			EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());
			// 本次出售获取的银币
			// if(equipConfig.getCanSale() == MarketConstant.CAN_NOT_SELL)
			// {
			// message.putShort(ErrorCode.ERR_CAN_NOT_SELL);
			// return message;
			// }
			
			//检查该装备是否正在使用，使用中的装备不可买
			if (prop.getUseID() != PropConstant.USE_0) {
				message.putShort(ErrorCode.PROP_IS_EQUIP);
				return message;
			}
			StrengthenConfig strengthenConfig = StrengthenConfigCache.getStrengthenConfigByLv(prop.getLv());
			getMoney = equipConfig.getSellPrice() + strengthenConfig.getCost(equipConfig.getQuality());
			// 从背包和数据库中删除该数量的道具
			propDao.removeProp(prop.getId());
			// role.getBackPack().remove(prop.getId());
			role.getPropMap().remove(prop.getId());
		} else if (prop.getFunctionType() == PropConstant.FUNCTION_TYPE_2) {
			// 此处应从配置表中取出对应的道具配置 消耗品
			Item item = ItemCache.getItemById(prop.getItemId());
			// 判断道具是否可售
			if (item.getCanSell() == MarketConstant.CAN_NOT_SELL) {
				message.putShort(ErrorCode.ERR_CAN_NOT_SELL);
				return message;
			}
			// 本次出售获得的银币
			getMoney = item.getSellPrice() * number;
			// 从背包和数据库中删除该数量的道具
			if (prop.getNum() == number) {
				// 全部出售
				// role.getBackPack().remove(prop.getId());
				role.getPropMap().get(prop.getId()).setNum(MarketConstant.SELL_SURPLUS);
			} else {
				// 部分出售
				short newNum = (short) (prop.getNum() - number);
				// role.getBackPack().get(prop.getId()).setNum(newNum);
				role.getPropMap().get(prop.getId()).setNum(newNum);
			}

		}
		// 增加玩家的银币数
		roleService.addRoleMoney(role, getMoney);

		role.setChange(true);
		prop.setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(getMoney);
		return message;
	}

	/**
	 * 市场出售功能(玩家购买)
	 * 
	 * @author xjd
	 */
	public Message buy(Role role, int id, IoSession is) {
		// 等级过滤
		// if(role.getLv() < MarketConstant.FIRST_FRESH_MARKET)
		// {
		// return null;
		// }
		Message message = new Message();
		message.setType(MarketConstant.BUY_MARKET);
		// 本次购买花费的数量(银币)
		int costMoney = 0;
		// 判断是否可售
		Map<Integer, MarketItems> map = role.getMarket().getItemsMap();
		if (!map.containsKey(id)) {
			return null;
		}
		MarketItems marketItems = map.get(id);
		EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(marketItems.getItemId());
		// 判定道具是否是装备
		if (equipConfig == null) {
			message.putShort(ErrorCode.ERR_TYPE_MARKET_BUY);
			return message;
		}

		if (marketItems.getIsSell() != MarketConstant.IS_NOT_SELL) {
			message.putShort(ErrorCode.ERR_CAN_NOT_SELL);
			return message;
		}

		// 应该使用银币购买
		costMoney = marketItems.getSalePrice();
		if (role.getMoney() < costMoney) {
			message.putShort(ErrorCode.MONEY_NOT_ENOUGH);
			return message;
		}
		// 判断背包是否有空余
		// if(role.getMaxBagNum() - role.getBackPack().size() <
		// MarketConstant.BACKPACKAGE_IDLE)
		// {
		// message.putShort(ErrorCode.BACKPACK_ERR);
		// return message;
		// }
		// 扣除玩家银币
		roleService.addRoleMoney(role, -costMoney);
		// 新建装备
		Prop newEquip = new Prop();
		newEquip.setRoleId(role.getId());
		newEquip.setFunctionType(PropConstant.FUNCTION_TYPE_1);
		newEquip.setItemId(equipConfig.getId());
		newEquip.setNum(PropConstant.NUM_1);
		newEquip.setLv(PropConstant.LV_1);
		// newEquip.setSlotId((byte)equipConfig.getSlot());
		newEquip.setSlotId(PropConstant.SLOT_0);
		int newEquipId = propDao.insertProp(newEquip);
		newEquip.setId(newEquipId);
		// 加入缓存
		role.getPropMap().put(newEquipId, newEquip);
		// role.getBackPack().put(newEquipId, newEquip);
		propService.addProp(newEquip, is);

		//为了保证服务器运行时的商品id唯一性，不能从表中移除商品
		marketItems.setIsSell(MarketConstant.IS_SELL);

		role.setChange(true);
		role.getMarket().setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(costMoney);
		return message;
	}

	/**
	 * 市场刷新（玩家消耗与元宝）<br/>
	 * <font color="red"><b>此方法已经弃用!!<b/> <br/>
	 * <font/>
	 * 
	 * @author xjd
	 */
	public Message fresh(Role role) {
		Message message = new Message();
		message.setType(MarketConstant.FRESH_MARKET);
		// 根据玩家当前vip等级和本日已刷新次数取出配置表
		FreshLimit freshLimit = FreshLimitCache.getFreshLimit(role.getVipLv(), role.getMarket().getFreshNumber());
		// 判断玩家是否还可以刷新市场
		if (freshLimit == null) {
			message.putShort(ErrorCode.ERR_TIME_FRESH);
			return message;
		}
		if (role.getMarket().getFreshNumber() >= freshLimit.getTotalNumber()) {
			message.putShort(ErrorCode.ERR_TIME_FRESH);
			return message;
		}
		// 判断用户当前金币数是否足够刷新
		if (role.getGold() < freshLimit.getCost()) {
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		// 扣除玩家金币
		roleService.addRoleGold(role, -freshLimit.getCost());
		// 玩家当日刷新次数+1
		role.getMarket().setFreshNumber((byte) (role.getMarket().getFreshNumber() + MarketConstant.ADD_FRESH_NUMBER));
		// 此处应该调用刷新接口刷新市场里的道具
		this.changMarket(role);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(freshLimit.getCost());
		// 取出新的市场装备集合
		Map<Integer, MarketItems> newMap = role.getMarket().getItemsMap();
		message.putInt(newMap.size());
		// 循环每一个装备的配置id放入message中
		for (MarketItems items : newMap.values()) {
			message.putInt(items.getItemId());
		}
		return message;
	}

	/**
	 * 获取玩家的市场信息
	 * 
	 * @author xjd
	 */
	public Message getMarketInfo(Role role) {
		Message message = new Message();
		message.setType(MarketConstant.GET_MARKET_INFO);
		Market market = role.getMarket();
		if (market == null) {
			return null;
		}
		
		Map<Integer,MarketItems> itemsMap = market.getItemsMap();
		List<MarketItems> list = new ArrayList<>();
		for(int i = 0;i<itemsMap.size();i++){
			MarketItems marketItems = itemsMap.get(i);
			if(marketItems.getIsSell() == MarketConstant.IS_SELL){
				continue;
			}

			list.add(marketItems);
		}		

		message.put((byte) list.size());		
		for(MarketItems marketItems:list){
			int id = marketItems.getId();
			int itemId = marketItems.getItemId();
			byte itemType = itemId > 20000 ? MarketConstant.TYPE_ITEM : MarketConstant.TYPE_EQUIP;
			int isSell = marketItems.getIsSell();
			int salePrice = marketItems.getSalePrice();
			byte bargin = marketItems.getIsBargin();

			message.putInt(id);
			message.putInt(itemId);
			message.put(itemType);
			message.putInt(isSell);
			message.putInt(salePrice);
			message.put(bargin);
		}	
		
		return message;
	}

	/**
	 * 获取藏宝阁信息
	 * 
	 * @author xjd
	 */
	public Message getGoldMarketInfo(Role role) {
		Message message = new Message();
		message.setType(MarketConstant.GET_GOLD_MARKET_INFO);
		Map<Integer, GoldMarketConfig> temp = GoldMarketConfigCache.getMapInfo();
		message.put((byte) temp.size());
		for (GoldMarketConfig x : temp.values()) {
			message.putInt(x.getId());
			message.putInt(x.getItemId());
			message.put((byte) x.getType());
			message.putInt(x.getCost());
		}
		return message;
	}

	/**
	 * 藏宝阁购买
	 * 
	 * @author xjd
	 */
	public Message buyGoldMarket(Role role, int id, IoSession is) {
		Message message = new Message();
		message.setType(MarketConstant.BUY_GOLD_MARKET);
		GoldMarketConfig goldMarketConfig = GoldMarketConfigCache.getGoldMarketConfig(id);
		if (goldMarketConfig == null) {
			return null;
		}
		// 判断元宝
		if (role.getGold() < goldMarketConfig.getCost()) {
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}

		// 购买成功
		roleService.addRoleGold(role, -goldMarketConfig.getCost());
		propService.addProp(role, goldMarketConfig.getItemId(), (byte) goldMarketConfig.getType(),
				MarketConstant.MARKET_ADD_NUM_2, is);

		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 更换市场的道具
	 * 
	 * @param role
	 * @author xjd
	 */
	public void changMarket(Role role) {
		// 过滤玩家等级
		// if(role.getLv() < MarketConstant.FIRST_FRESH_MARKET)
		// {
		// return;
		// }
		// 获取玩家当前市场
		Market market = role.getMarket();
		// //清空当前玩家市场的道具集合
		// market.getItemsMap().clear();
		// //计算市场的格子数
		// int num = MarketConstant.MARKET_NUM_STAR;
		// int tempVipNum = MarketConstant.MARKET_NUM_VIP;
		// if(role.getVipLv() >= MarketConstant.VIP_LV_3)
		// {
		// tempVipNum += MarketConstant.MARKET_ADD_NUM;
		// }
		// if (role.getVipLv() >= MarketConstant.VIP_LV_5) {
		// tempVipNum += MarketConstant.MARKET_ADD_NUM;
		// }
		// if(role.getVipLv() >= MarketConstant.VIP_LV_7)
		// {
		// tempVipNum += MarketConstant.MARKET_ADD_NUM_2;
		// }
		// if(role.getVipLv() >= MarketConstant.VIP_LV_9)
		// {
		// tempVipNum += MarketConstant.MARKET_ADD_NUM_2;
		// }
		//
		// num += tempVipNum;
		// //更新市场的格子数
		// market.setMarketLimit((byte)num);
		// //向集合中存入新的随机道具
		// for(int i = 0 ; i < num ; i++)
		// {
		// //根据玩家等级取出市场出现的道具
		// RandomNumberMarket randomNumberMarket =
		// RandomNumberMarketCache.getNumberByLv(role.getLv());
		// if(randomNumberMarket == null)
		// {
		// return;
		// }
		// //根据配置表取出概率
		// int r =
		// Utils.getRandomNum(randomNumberMarket.getMinNumber(),randomNumberMarket.getMaxNumber());
		// ProbabilityMarket probabilityMarket =
		// ProbabilityMarketCache.getNumberByLv(r);
		// int rr = probabilityMarket.getItemId();
		// EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(rr);
		// //判断装备是否存在
		// if(equipConfig == null)
		// {
		// i--;
		// continue;
		// }
		// //向集合中增加装备配置id
		// MarketItems marketItems = new MarketItems();
		// marketItems.setId(i);
		// marketItems.setItemId(equipConfig.getId());
		// marketItems.setIsSell(MarketConstant.IS_NOT_SELL);
		// market.getItemsMap().put(marketItems.getId(), marketItems);
		// }
		// 更新时间
		market.setLastFreshTime(System.currentTimeMillis());
		market.setNextFreshNeed(Utils.getNextDayTwelveTime());
		role.getMarket().setChange(true);
	}

	/**
	 * 检查下线再次登录时是否需要刷新市场
	 * 
	 * @author xjd
	 */
	public void checkLastChange(Role role) {
		Market market = role.getMarket();
		// 判断是否需要更新
		if (System.currentTimeMillis() - market.getLastFreshTime() >= market.getNextFreshNeed()) {
			this.changMarket(role);
		}
	}

	@Override
	public Message visitSale(Role role, int heroId, int itemSqlId, int num) {
		Message message = new Message();
		message.setType(MarketConstant.VISIT_SALE);
		long nowTime = System.currentTimeMillis() / 1000;
		inComeService.refreshLevyData(role, nowTime);

		// 上次寻访未结束
		if (!InComeUtils.checkLevy(role, InComeConstant.TYPE_MARKET, heroId)) {
			message.putShort(ErrorCode.VISIT_NOT);
			return message;
		}

		// 检测体力
		// TODO
		int manual = GeneralNumConstantCache.getValue("USE_SALEITEM_MANUAL");
		if (!inComeService.checkCanSendHero(role.getHeroMap().get(heroId), manual)) {
			message.putShort(ErrorCode.NO_MANUAL);
			return message;
		}

		// 检查是否有足够道具
		Prop prop = role.getPropMap().get(itemSqlId);
		if (prop.getNum() < num) {
			message.putShort(ErrorCode.ERR_NUMBER_SELL);
			return message;
		}

		// 使用道具
		int itemId = prop.getItemId();

		prop.setNum((short) (prop.getNum() - num));

		// 创建 派遣事件
		Build build = role.getBuild();
		Hero levyHero = role.getHeroMap().get(heroId);

		// 获得cd时间
		int cd = 7200;
		// 基础价格怎么取
		int itemBaseSales = 0;
		int presitage = 0;

		if (itemId > 10000 && itemId < 20000) {
			// 装备
			EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(itemId);
			itemBaseSales = equipConfig.getSellPrice();
			presitage = equipConfig.getPrestigeAward();
		} else {// 道具
			Item item = ItemCache.getItemById(itemId);
			itemBaseSales = item.getSellPrice();
			presitage = item.getPrestigeAward();
		}
		int price = (int) (itemBaseSales / 2.5 * Math.pow(1.05, HeroUtils.getIntelValue(levyHero)));

		LevyInfo levyInfo = new LevyInfo();
		levyInfo.setType(InComeConstant.TYPE_MARKET);
		levyInfo.setStartTime(nowTime);
		levyInfo.setHeroId(heroId);
		levyInfo.setCd(cd);
		levyInfo.setValue(presitage);// 声望值
		levyInfo.setValueOther(price);// 价格

		build.getLevyMap().put(heroId, levyInfo);
		build.setChange(true);
		this.taskService.checkVisit(role, InComeConstant.TYPE_MARKET);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(levyInfo.getCd());

		return message;
	}

	@Override
	public Message bargainItems(Role role, int id) {
		Message message = new Message();
		message.setType(MarketConstant.BARGAIN_ITEMS);

		int nowTime = Utils.getNowTime();
		Market market = role.getMarket();
		BuildInfo buildInfo = role.getBuild().getAttachHeroMap().get(InComeConstant.TYPE_MARKET);

		if (buildInfo == null) {
			message.putShort(ErrorCode.NO_HERO);
			return message;
		}
		int attachHeroId = buildInfo.getHeroId();
		Hero taskHero = role.getHeroMap().get(attachHeroId);

		Map<Integer, MarketItems> itemsMap = market.getItemsMap();
		MarketItems marketItems = itemsMap.get(id);
		int barginManualUse = GeneralNumConstantCache.getValue("BARGIN_MANUAL_USE");

		inComeService.checkHeroManual(role,nowTime);
		// 检查体力
		if (taskHero.getManual() < barginManualUse) {
			message.putShort(ErrorCode.NO_MANUAL);
			return message;
		}

		// 检查是否议价
		if (marketItems.isBargin()) {
			message.putShort(ErrorCode.MARKET_HAS_BARGIN);
			return message;
		}

		int successRate = inComeService.getMarketItemPriceSuccessRate(role, taskHero);
		byte isBarginSuccess = 0;

		Random rand = new Random();
		int randInt = rand.nextInt(101);
		if (randInt < successRate) {// 议价成功
			isBarginSuccess = 1;
			int price = inComeService.getMarketItemPrice(role, taskHero, id);
			marketItems.setSalePrice(price);
			marketItems.setIsBarginBoolean(true);
		}

		taskHero.addManual(-barginManualUse);

		taskHero.setChange(true);
		market.setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		message.put(isBarginSuccess);
		message.putInt(marketItems.getSalePrice());

		return message;
	}

	@Override
	public Message showBargainItems(Role role, int id) {
		Message message = new Message();
		message.setType(MarketConstant.SHOW_BARGIN_ITEMS);

		Market market = role.getMarket();
		BuildInfo buildInfo = role.getBuild().getAttachHeroMap().get(InComeConstant.TYPE_MARKET);

		byte canBargin = 0;
		int barginPrice = 0;
		int successRate = 0;
		Hero hero = null;
		int attachHeroId = 0;

		Map<Integer, MarketItems> marketItemMap = market.getItemsMap();
		MarketItems marketItem = marketItemMap.get(id);

		int salePrice = marketItem.getSalePrice();
		byte isBargin = marketItem.getIsBargin();

		if (buildInfo != null) {
			attachHeroId = buildInfo.getHeroId();
			hero = role.getHeroMap().get(attachHeroId);
			barginPrice = inComeService.getMarketItemPrice(role, hero, id);
			successRate = inComeService.getMarketItemPriceSuccessRate(role, hero);
			canBargin = 1;
		} else {
			barginPrice = salePrice;
		}

		// 是否已经议价，若已经议价则出售价和议价相同
		if (marketItem.isBargin()) {
			canBargin = 0;
			barginPrice = salePrice;
		}

		message.put(canBargin);
		message.put(isBargin);
		message.putInt(salePrice);
		message.putInt(barginPrice);
		message.putInt(successRate);

		return message;
	}

	/**
	 * 初始化拍卖行信息
	 * 
	 * @author xjd
	 */
	public void initAuction() {
		for (Auction auction : auctionDao.getAllAuction()) {

			if (auction.getIsDispose() != MarketConstant.IS_DISPOSE
					&& auction.getStartTime() + auction.getPassTime() > Utils.getNowTime()) {
				auction.setCanSee(true);
				AuctionCache.addAuctionCache(auction);
				if (auction.getStartTime() + auction.getPassTime() - Utils.getNowTime() <= 1800) {
					AuctionCache.addAuctionList(auction.getUuid());
				}
			} else {
				clearAuction(auction);
			}
		}
	}

	public void clearAuction(Auction auction) {
		if (auction.getIsDispose() == MarketConstant.IS_DISPOSE) {
			// 移除该拍卖物品
			auctionDao.removeAuction(auction.getUuid());
		} else {
			// 处理卖出方收益
			Role role = roleService.getRoleById(auction.getRoleId());
			this.mailAuctionFail(auction, role);
			auctionDao.removeAuction(auction.getUuid());
		}
	}

	/**
	 * 调度器使用检查<br/>
	 * 将拍卖失败的物品 isDispose更改 处理邮件,将auction移动到offTimeMap，并从allMap中移除
	 */
	public void checkAuctionTime(Auction auction) {
		if (auction.getStartTime() + auction.getPassTime() <= Utils.getNowTime()) {
			auction.setCanSee(false);
			auction.setIsDispose(MarketConstant.IS_DISPOSE);
			// 处理卖出方收益
			Role role = roleService.getRoleById(auction.getRoleId());
			this.mailAuctionFail(auction, role);
			AuctionCache.addOffTime(auction);
			AuctionCache.removeAuction(auction);
		}

	}

	/**
	 * 获取拍卖行信息
	 * 
	 * @author xjd
	 */
	public Message showAuctionInfo(Role role) {
		Message message = new Message();
		message.setType(MarketConstant.SHOW_AUCTION);
		role.getAuctionInfo().clear();
		message.putInt(AuctionCache.getAllMap().size());
		for (Auction auction : AuctionCache.getAllMap().values()) {
			if (auction.getRoleId() == role.getId()) {
				role.getAuctionInfo().add(auction.getUuid());
			}
			message.putString(auction.getUuid());
			Role auctionRole = roleService.getRoleById(auction.getRoleId());
			message.putString(auctionRole.getName());
			message.put(auction.getType());
			message.putInt(auction.getItemId());
			message.putInt(auction.getNum());
			message.putInt(auction.getCost());
			message.putInt(auction.getStartTime());
			message.putInt(auction.getPassTime());
		}
		return message;
	}

	/***
	 * 拍卖物品
	 * 
	 * @author xjd
	 */
	public Message sellAuction(Role role, byte type, int serviceId, int num, int cost, int time) {
		Message message = new Message();
		message.setType(MarketConstant.SELL_AUCTION);
		// 检查类型
		if (type < MarketConstant.TYPE_FOOD || type > MarketConstant.TYPE_ITEM || num <= MarketConstant.IS_NOT_SELL) {
			return null;
		}
		// 检查价格
		if (cost < MarketConstant.LOWEST_PRICE_AUCTION) {
			message.putShort(ErrorCode.LOWEST_AUCTION_PRICE);
			return message;
		}
		// 检查是否达到拍卖上限
		if (role.getAuctionInfo().size() >= MarketConstant.FIRST_FRESH_MARKET) {
			message.putShort(ErrorCode.MAX_NUM_AUCTION);
			return message;
		}
		// 检查保证金
		int keepCost = GeneralNumConstantCache.getValue("AUCTION_" + time);
		if (role.getMoney() < keepCost) {
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}
		Prop prop = role.getPropMap().get(serviceId);
		int itemId = MarketConstant.CAN_NOT_SELL;
		if (prop == null && type != MarketConstant.TYPE_FOOD) {
			return null;
		} else if (prop != null) {
			itemId = prop.getItemId();
		}

		// 检查物品是否含有词缀并且有过强化
		if (prop != null && prop.getFunctionType() == MarketConstant.TYPE_EQUIP
				&& (prop.getLv() > MarketConstant.SELL_SURPLUS || prop.getPrefixId() != MarketConstant.CAN_NOT_SELL)) {
			message.putShort(ErrorCode.ERR_CAN_NOT_SELL);
			return message;
		}
		if (prop != null && prop.getUseID() != 0) {
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		if (prop != null && (int) prop.getNum() < num) {
			message.putShort(ErrorCode.ERR_NUMBER_SELL);
			return message;
		}
		// 可以拍卖
		Auction auction = new Auction(Utils.getUUID(), role.getId(), type, itemId, num, Utils.getNowTime(), time, cost);
		auctionDao.insterAuction(auction);
		AuctionCache.addAuctionCache(auction);
		// 拍卖玩家扣除花费及拍卖物品
		roleService.addRoleMoney(role, -keepCost);
		// 更具拍卖类型处理
		this.handlerRoleAuction(role, type, prop, num);
		role.getAuctionInfo().add(auction.getUuid());
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 购买拍卖物品
	 * 
	 * @author xjd
	 */
	public Message buyAuction(Role role, String uuid) {
		Message message = new Message();
		message.setType(MarketConstant.BUY_AUCTION);
		// 检测是否过期
		Auction auction = AuctionCache.getAuctionById(uuid);
		if (auction == null || auction.getStartTime() + auction.getPassTime() <= Utils.getNowTime()) {
			message.putShort(ErrorCode.ERR_TIME_AUCTION);
			return message;
		}
		// 检测金币是否足够
		if (role.getGold() < auction.getCost()) {
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		// 购买成功
		try {
			auction.getLock().lock();
			roleService.addRoleGold(role, -auction.getCost());
			auction.setIsDispose(MarketConstant.IS_DISPOSE);
			AuctionCache.addOffTime(auction);
			AuctionCache.removeAuction(auction);
			;
		} catch (Exception e) {
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		} finally {
			auction.getLock().unlock();
		}
		// 给买卖双发发送邮件
		this.mailAuctionToBuyer(auction, role);
		this.mailAuctionToSeller(auction, roleService.getRoleById(auction.getRoleId()));

		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/***
	 * 下架拍卖物品
	 * 
	 * @author xjd
	 * 
	 */
	public Message cancelAuction(Role role, String uuid) {
		Message message = new Message();
		message.setType(MarketConstant.CANCEL_AUCTION);
		// 检测拍卖物
		Auction auction = AuctionCache.getAuctionById(uuid);
		if (auction == null || auction.getIsDispose() == MarketConstant.IS_DISPOSE) {
			message.putShort(ErrorCode.ERR_TIME_AUCTION);
			return message;
		}
		// 检测是否有权限操作
		if (auction.getRoleId() != role.getId()) {
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		try {
			auction.getLock().lock();
			auction.setIsDispose(MarketConstant.IS_DISPOSE);
			AuctionCache.addOffTime(auction);
			AuctionCache.removeAuction(auction);
		} catch (Exception e) {
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		} finally {
			auction.getLock().unlock();
		}
		this.mailAuctionCancel(auction, role);
		role.getAuctionInfo().remove(uuid);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	public Message showAuctionSelf(Role role) {
		Message message = new Message();
		message.setType(MarketConstant.SHOW_AUCTION_SELF);
		Set<Auction> temp = new HashSet<Auction>();
		for (String x : role.getAuctionInfo()) {
			Auction auction = AuctionCache.getAuctionById(x);
			if (auction != null) {
				temp.add(auction);
			}
		}

		message.putInt(temp.size());
		for (Auction auction : temp) {
			message.putString(auction.getUuid());
			message.putString(role.getName());
			message.put(auction.getType());
			message.putInt(auction.getItemId());
			message.putInt(auction.getNum());
			message.putInt(auction.getCost());
			message.putInt(auction.getStartTime());
			message.putInt(auction.getPassTime());
		}
		return message;
	}

	private void mailAuctionFail(Auction auction, Role role) {
		// 返回物品
		Mail mail = new Mail();
		mail.setTitle("拍卖行");
		mail.setContext("很遗憾，拍卖失败了，请提取附件");
		mail.setTime(auction.getStartTime() + auction.getPassTime());
		mail.setAttached(Utils.changeAuctionToStr(auction));
		this.mailService.sendSYSMail(role, mail);
	}

	private void mailAuctionToBuyer(Auction auction, Role role) {
		Mail mail = new Mail();
		mail.setTitle("拍卖行");
		mail.setContext("恭喜您，购买成功了，请提取附件");
		mail.setTime(Utils.getNowTime());
		mail.setAttached(Utils.successAuctionToStr(auction));
		this.mailService.sendSYSMail(role, mail);
	}

	private void mailAuctionToSeller(Auction auction, Role role) {
		Mail mail = new Mail();
		mail.setTitle("拍卖行");
		mail.setContext("恭喜您，在拍卖行的商品成交了，请提取附件");
		mail.setTime(Utils.getNowTime());
		mail.setAttached(Utils.successAuction2(auction));
		this.mailService.sendSYSMail(role, mail);
	}

	private void mailAuctionCancel(Auction auction, Role role) {
		Mail mail = new Mail();
		mail.setTitle("拍卖行");
		mail.setContext("商品下架成功，请提取附件,保证金已扣除");
		mail.setTime(Utils.getNowTime());
		mail.setAttached(Utils.successAuctionToStr(auction));
		mailService.sendSYSMail(role, mail);
	}

	private void handlerRoleAuction(Role role, byte type, Prop prop, int num) {
		switch (type) {
		case MarketConstant.TYPE_FOOD:
			roleService.addRoleFood(role, -num);
			role.setChange(true);
			break;
		case MarketConstant.TYPE_ITEM:
			prop.setNum((short) (prop.getNum() - num));
			prop.setChange(true);
			break;
		case MarketConstant.TYPE_EQUIP:
			propDao.removeProp(prop.getId());
			role.getPropMap().remove(prop.getId());
			break;
		default:
			break;
		}
	}

	@Override
	public void initMarket(Role role,int nowTime) {
		Market market = role.getMarket();

		//初始化声望商城个人限定物品
//		Map<Integer, PrestigeShop> privateMap = PrestigeShopCache.getPrivateItems();
//		Map<Integer, WorldMarketItems> worldItemsMap = market.getWorldItemsMap();
//		addWorldMarketItems(privateMap, worldItemsMap);

//		Map<Integer, PrestigeShop> alwaysMap = PrestigeShopCache.getAlwaysItems();
//		addWorldMarketItems(alwaysMap, worldItemsMap);
		
//		market.setWorldItemsRefreshTime(nowTime);
		
		byte nation = role.getNation();
		List<WorldTempMarketItem> cityHeroIdMap = this.getOccupyCityTotalHeroIdByNation(nation);
		
		//获得该玩家解锁的格子数量
		int count = market.getMarketLimit();
		
		market.getTempWorldItemsMap().clear();
		
		//随机生成后加入market中的列表		
		for (int i = 0; i < count; i++) {
			int randIndex = Utils.getRandomNum(cityHeroIdMap.size());
			WorldTempMarketItem w = cityHeroIdMap.get(randIndex);
			WorldTempMarketItem w1 = new WorldTempMarketItem(w);
			market.getTempWorldItemsMap().put(role.getId() + "#" + nowTime + "#" + i, w1);
		}

		market.setChange(true);
	}
	
	
	
	/**
	 * 获得全地图已解锁的本国及群雄将id列表
	 * 
	 * @param nation
	 * @return <城市id,
	 * @author wcy 2016年3月4日
	 */
	private List<WorldTempMarketItem> getOccupyCityTotalHeroIdByNation(byte nation) {
		List<WorldTempMarketItem> heroIdMap = new ArrayList<>();

		ConcurrentHashMap<Integer, City> allCity = CityCache.getAllCityMap();
		for (City city : allCity.values()) {
			// if (city.getFirstOccupyNation() == 0)
			// continue;
			int cityId = city.getCityId();
			CityInfoConfig cityInfoConfig = CityInfoConfigCache.getCityInfoConfigById(cityId);
			int heroId = cityInfoConfig.getGarrisonHero();
			HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(heroId);
			if (heroConfig != null) {
				int heroNation = heroConfig.getNation();
				if (heroNation == nation || heroNation == 0) {
					WorldTempMarketItem w = new WorldTempMarketItem();
					w.setCityId(cityId);
					w.setId(heroId);
					// 库存数量
					PrestigeHero prestigeHero = PrestigeHeroCache.getPrestigeHeroByID(heroId);
					if (prestigeHero == null) {
//						System.err.println("声望商城没有这个武将");
					}
					w.setInventory(prestigeHero.getLimit());
					heroIdMap.add(w);
				}

			}

		}

		return heroIdMap;
	}
	
	/**
	 * 
	 * @param map
	 * @param targetMap
	 * @author wcy 2015年12月28日
	 */
	private void addWorldMarketItems(Map<Integer,PrestigeShop> map,Map<Integer,WorldMarketItems> targetMap){
		for (PrestigeShop prestigeShop : map.values()) {			
			int id = prestigeShop.getRowId();
			int inventory = prestigeShop.getInventory();
			
			WorldMarketItems worldMarketItems = new WorldMarketItems();
			worldMarketItems.setId(id);
			worldMarketItems.setInventory(inventory);
			
			targetMap.put(id, worldMarketItems);
		}
	}

	@Override
	public Message showWorldMarketItems(Role role) {
		Message message = new Message();
		message.setType(MarketConstant.SHOW_WORLD_MARKET_ITEMS);

		Market market = role.getMarket();
//		Server server = ServerCache.getServer();
//		// 常驻
//		Map<Integer, PrestigeShop> alwaysMap = PrestigeShopCache.getAlwaysItems();
//		int size = alwaysMap.size();
//		message.putInt(size);
//		for (PrestigeShop items : alwaysMap.values()) {
//			int id = items.getRowId();
//			int itemId = items.getItemID();
//			int price = items.getPrice();
//			int num = items.getNum();
//			byte itemType = (byte) items.getItemType();
//
//			message.putInt(id);
//			message.putInt(price);
//			message.putInt(itemId);
//			message.putInt(num);
//			message.put(itemType);
//		}
//		// 个人
//		Map<Integer, WorldMarketItems> map = market.getWorldItemsMap();
//		size = map.size();
//		message.putInt(size);
//		for (WorldMarketItems worldMarketItems : map.values()) {
//			int id = worldMarketItems.getId();
//			int inventory = worldMarketItems.getInventory();
//
//			PrestigeShop prestigeShop = PrestigeShopCache.getPrestigeShopById(id);
//			int itemId = prestigeShop.getItemID();
//			int price = prestigeShop.getPrice();
//			int num = prestigeShop.getNum();
//			byte itemType = (byte) prestigeShop.getItemType();
//
//			message.putInt(id);
//			message.putInt(inventory * num);
//			message.putInt(itemId);
//			message.putInt(num);
//			message.putInt(price);
//			message.put(itemType);
//		}
//		// 世界
//		ConcurrentHashMap<Integer, WorldMarketItems> serverMap = server.getWorldItemsMap();
//		size = serverMap.size();
//		message.putInt(size);
//		for (WorldMarketItems worldMarketItems : serverMap.values()) {
//			int id = worldMarketItems.getId();
//			PrestigeShop prestigeShop = PrestigeShopCache.getPrestigeShopById(id);
//			int inventory = worldMarketItems.getInventory();
//
//			int itemId = prestigeShop.getItemID();
//			int price = prestigeShop.getPrice();
//			int num = prestigeShop.getNum();
//			byte itemType = (byte) prestigeShop.getItemType();
//
//			message.putInt(id);
//			message.putInt(inventory * num);
//			message.putInt(itemId);
//			message.putInt(num);
//			message.putInt(price);
//			message.put(itemType);
//		}
		
		//显示将魂
		Map<String, WorldTempMarketItem> worldItemMap = market.getTempWorldItemsMap();
		int size = worldItemMap.size();
		if(size == 0)
			initMarket(role, Utils.getNowTime());
		worldItemMap = market.getTempWorldItemsMap();	

		byte vipLv = role.getVipLv();
		int max = ReputationShopUnlockCache.getMaxUnlock();
		int currentMax = ReputationShopUnlockCache.getByVipLv(vipLv).getOpenNum();
		ArrayList<String> itemIdList = new ArrayList<>(worldItemMap.keySet());
		message.putInt(max);
		for (int i = 0; i < max; i++) {
			byte canOpen = 0;//是否可开启
			byte alreadyOpen = 0;//是否已经开启
			byte openNeedVip = 0;//开启需要vip等级
			int openNeedGold = 0;//开启需要花费
			int itemCityId = 0;//奖励对应的城市
			String shopId = "";//商品id
			StringBuilder sb = new StringBuilder();
			byte canBuy = 0;//是否可以购买
			int prestige = 0;//需要的声望			
			
			if (i < itemIdList.size()) {
				shopId = itemIdList.get(i);
				WorldTempMarketItem item = worldItemMap.get(shopId);
				canOpen = 1;
				alreadyOpen = 1;
				openNeedVip = (byte) ReputationShopUnlockCache.getByOpenNum(i + 1).getVipLevel();
				openNeedGold = 0;
				itemCityId = item.getCityId();
				int itemId = item.getId() + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX;
				sb.append(ChapterConstant.AWARD_ITEM).append(",").append(itemId).append(",")
						.append(item.getInventory());
				City city = CityCache.getCityByCityId(itemCityId);
				canBuy = (byte) (city.getNation() == role.getNation() ? 1 : 0);
				prestige = PrestigeHeroCache.getPrestigeHeroByID(item.getId()).getPrestige();
			} else if (i < currentMax && i < market.getMarketLimit()) {
				// 已解锁但没东西
				alreadyOpen = 1;
			}
			else {
				//未解锁
				ReputationShopUnlock unlock = ReputationShopUnlockCache.getByOpenNum(i + 1);
				openNeedVip = (byte) unlock.getVipLevel();
				openNeedGold = unlock.getCost();
				if(i<currentMax){
					//可解锁
					canOpen = 1;
				}
			}
			
			message.put(canOpen);// 是否可开启
			message.put(alreadyOpen);// 是否已经开启
			message.put(openNeedVip);// 开启需要vip等级
			message.putInt(openNeedGold);// 开启需要花费
			message.putInt(itemCityId);// 奖励对应的城市
			message.putString(shopId);// 商品id
			message.putString(sb.toString());
			message.put(canBuy);// 是否可以购买
			message.putInt(prestige);// 需要的声望		
			
		}
		int gold = this.getRefreshPrestigeWorldMarketNeedGold(role);
		message.putInt(gold);

		return message;
	}

	@Override
	public Message buyWorldMarketItems(Role role, String id) {
		Message message = new Message();
		message.setType(MarketConstant.BUY_WORLD_MARKET_ITEMS);

//		Server server = ServerCache.getServer();
		Market market = role.getMarket();
//		PrestigeShop prestigeShop = PrestigeShopCache.getPrestigeShopById(id);
//		int price = prestigeShop.getPrice();
//		int num = prestigeShop.getNum();
//		int itemId = prestigeShop.getItemID();
//		int type = prestigeShop.getType();
//		byte functionType = (byte) prestigeShop.getFunctionType();
//		
//		
//		WorldMarketItems worldMarketItems = null;
//		
//		int inventory  = 0;
//		switch(type){
//		case MarketConstant.WORLD_MARKET_ITEMS_ALWAYS:
//			break;
//		case MarketConstant.WORLD_MARKET_ITEMS_PRIVATE:
//			Map<Integer, WorldMarketItems> privateMap = market.getWorldItemsMap();
//			worldMarketItems=privateMap.get(id);
//			inventory = worldMarketItems.getInventory();
//			break;
//		case MarketConstant.WORLD_MARKET_ITEMS_PUBLIC:
//			ConcurrentHashMap<Integer, WorldMarketItems> publicMap = server.getWorldItemsMap();
//			worldMarketItems = publicMap.get(id);
//			inventory = worldMarketItems.getInventory();
//			break;
//		}
//		
//
//		if (type != MarketConstant.WORLD_MARKET_ITEMS_ALWAYS && inventory <= 0) {
//			message.putShort(ErrorCode.NO_INVENTORY);
//			return message;
//		}
//
//		int prestige = role.getPrestige();
//		if (prestige < price) {
//			message.putShort(ErrorCode.NO_PRESTIGE);
//			return message;
//		}
//		
//		//减库存
//		if (type == MarketConstant.WORLD_MARKET_ITEMS_PUBLIC) {
//			if (!server.buyWorldItemsMap(id)) {
//				message.putShort(ErrorCode.NO_INVENTORY);
//				return message;
//			}
//		} else if (type == MarketConstant.WORLD_MARKET_ITEMS_PRIVATE) {
//			worldMarketItems.setInventory(inventory - 1);
//		}
//
//		roleService.addRolePrestige(role, -price);
//		IoSession is = SessionCache.getSessionById(role.getId());
//		if (functionType == PropConstant.FUNCTION_TYPE_2) {
//			propService.addProp(role, itemId, functionType, num, is);
//		} else {
//			for (int i = 0; i < num; i++) {
//				propService.addProp(role, itemId, functionType, 1, is);
//			}
//		}
		
		
//
//		message.putShort(ErrorCode.SUCCESS);
//		message.putInt(worldMarketItems!=null?worldMarketItems.getInventory()*num:-1);

		Map<String, WorldTempMarketItem> map = market.getTempWorldItemsMap();
		WorldTempMarketItem item = map.get(id);
		
		int heroId = item.getId();
		int inventory = item.getInventory();
		int cityId = item.getCityId();
		
		//检查该城市是否被占领
		City city = CityCache.getCityByCityId(cityId);
		if(this.checkCityOccupyByDifferentNation(role, city)){
			message.putShort(ErrorCode.NOT_YOUR_NATION);
			return message;
		}
		
		//检查库存
		if (inventory <= 0) {
			message.putShort(ErrorCode.NO_INVENTORY);
			return message;
		}
		
		int prestige = PrestigeHeroCache.getPrestigeHeroByID(heroId).getPrestige();
		//检查声望值
		if(role.getPrestige()<prestige){
			message.putShort(ErrorCode.NO_PRESTIGE);
			return message;
		}
		
		//可以购买
		roleService.addRolePrestige(role, -prestige);
		IoSession is = SessionCache.getSessionById(role.getId());
		item.setInventory(inventory - 1);
		propService.addProp(role, heroId + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX, PropConstant.FUNCTION_TYPE_2,
				1, is);
		
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(item.getInventory());

		return message;
	}
	
	/**
	 * 检查城市被不同国家占领,true表示被不同国家占领
	 * @param role
	 * @param city
	 * @return 
	 * @author wcy 2016年3月5日
	 */
	private boolean checkCityOccupyByDifferentNation(Role role, City city) {
		if (city.getNation() != role.getNation()) {
			return true;
		}
		return false;
	}

	@Override
	public Message refreshPrestigeWorldMarketItem(Role role) {
		Message message = new Message();
		message.setType(MarketConstant.REFRESH_PRESTIGE_WORLD_MARKET);
		
		//检查是否可以刷新
		if(!this.checkPrestigeWorldMarketRefresh(role)){
			message.putShort(ErrorCode.NO_VIP_LV);
			return message;
		}
		
		//刷新声望商城所需的金币数
		int gold = this.getRefreshPrestigeWorldMarketNeedGold(role);
		if(role.getGold()<gold){
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		
		roleService.addRoleGold(role, -gold);
		
		Market market = role.getMarket();
		market.setFreshNumber((byte) (market.getFreshNumber()+1));
		//刷新声望商城
		this.initMarket(role,Utils.getNowTime());
		
		message.putShort(ErrorCode.SUCCESS);
//		//返回结果
//		Map<String, WorldTempMarketItem> worldItemList = market.getTempWorldItemsMap();
//		message.putInt(worldItemList.size());
//		for (Entry<String, WorldTempMarketItem> entrySet : worldItemList.entrySet()) {
//			String id = entrySet.getKey();
//			WorldTempMarketItem w = entrySet.getValue();
//
//			int heroId = w.getId();
//			int inventory = w.getInventory();
//			int cityId = w.getCityId();
//			City city = CityCache.getCityByCityId(cityId);
//			byte visible = 0;
//			if (!this.checkCityOccupyByDifferentNation(role, city)) {
//				visible = 1;
//			}
//
//			
//			int prestige = PrestigeHeroCache.getPrestigeHeroByID(heroId).getPrestige();
//			int itemId = heroId + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX;
//			message.putString(id);
//			message.putString(ChapterConstant.AWARD_ITEM + "," + itemId + "," + inventory);
//			message.put(visible);
//			message.putInt(prestige);
//		}	
		
		return message;
	}
	
	@Override
	public void refreshPrestigeWorldMarketItems(Role role,int nowTime) {
//		Server server = ServerCache.getServer();
//		Market market = role.getMarket();
//		if (market.getWorldItemsRefreshTime() < server.getWorldItemsRefreshTime()) {
//			Map<Integer, WorldMarketItems> map = role.getMarket().getWorldItemsMap();
//			map.clear();
//			initMarket(role,nowTime);
//		}
		
		initMarket(role,nowTime);
	}
	
	@Override
	public Message unlockPrestigeMarketLimit(Role role) {
		Message message = new Message();
		message.setType(MarketConstant.UNLOCK_PRESTIGE_LIMIT);

		int nowTime = Utils.getNowTime();
		Market market = role.getMarket();
		int originCount = market.getMarketLimit();

		
		// 检查是否可以解锁
		if (!this.checkUnlock(role)) {
			message.putShort(ErrorCode.NO_VIP_LV);
			return message;
		}
		// 解锁需要的金币
		int gold = ReputationShopUnlockCache.getByOpenNum(originCount + 1).getCost();

		// 检查金币
		if (role.getGold() < gold) {
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}

		roleService.addRoleExp(role, -gold);
		// 集市格子增加
		market.setMarketLimit((byte) (originCount + 1));

		byte nation = role.getNation();
		List<WorldTempMarketItem> cityHeroIdMap = this.getOccupyCityTotalHeroIdByNation(nation);

		String shopId = "";
		byte canBuy = 0;
		int itemId = 0;
		int prestige = 0;
		StringBuilder sb = new StringBuilder();
		// 随机生成后加入market中的列表
		int randIndex = Utils.getRandomNum(cityHeroIdMap.size());
		WorldTempMarketItem w = cityHeroIdMap.get(randIndex);
		WorldTempMarketItem w1 = new WorldTempMarketItem(w);
		shopId = role.getId() + "#" + nowTime + "#" + market.getTempWorldItemsMap().size();
		market.getTempWorldItemsMap().put(shopId, w1);
		City city = CityCache.getCityByCityId(w1.getCityId());
		if (!this.checkCityOccupyByDifferentNation(role, city)) {
			canBuy = 1;
		}

		prestige = PrestigeHeroCache.getPrestigeHeroByID(w1.getId()).getPrestige();
		itemId = w.getId() + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX;
		sb.append(ChapterConstant.AWARD_ITEM).append(",").append(itemId).append(",").append(w1.getInventory());

		message.putShort(ErrorCode.SUCCESS);
		message.putString(shopId);
		message.putString(sb.toString());
		message.put(canBuy);
		message.putInt(prestige);

		return message;
	}
	
	/**
	 * 检查是否可以解锁
	 * @param role
	 * @return
	 * @author wcy 2016年3月5日
	 */
	private boolean checkUnlock(Role role){
		int vipLv = role.getVipLv();
		byte limit = role.getMarket().getMarketLimit();

		ReputationShopUnlock unlock = ReputationShopUnlockCache.getByOpenNum(limit + 1);
		if(unlock.getVipLevel()<=vipLv){
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * 获得刷新所需要的金币数
	 * @param role
	 * @return
	 * @author wcy 2016年3月5日
	 */
	private int getRefreshPrestigeWorldMarketNeedGold(Role role){
		
		Market market = role.getMarket();
		int freshNumber = market.getFreshNumber();
		ReputationShopRefresh r = ReputationShopRefreshCache.getByRefreshTimes(freshNumber+1);
		if(r == null){
			return ReputationShopRefreshCache.getMaxGold();
		}
		
		return r.getCost();
		
	}
	/**
	 * 检查是否可以刷新声望商城
	 * @param role
	 * @return
	 * @author wcy 2016年3月5日
	 */
	private boolean checkPrestigeWorldMarketRefresh(Role role){
		return true;
	}
}
