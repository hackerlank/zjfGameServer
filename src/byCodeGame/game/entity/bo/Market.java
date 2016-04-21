package byCodeGame.game.entity.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byCodeGame.game.entity.po.MarketItems;
import byCodeGame.game.entity.po.WorldMarketItems;
import byCodeGame.game.entity.po.WorldTempMarketItem;
import byCodeGame.game.moudle.market.MarketConstant;

public class Market {
	/** 用户id */
	private int roleId;
	/** 当天刷新次数 */
	private byte freshNumber;
	/** 市场出现的格子数量 */
	private byte marketLimit;
	/** 市场里出售的物品配置id (配置表id,是否已售;配置表id,是否已售;) */
	private String items;
	/** 市场里出售的物品配置id集合 */
	private Map<Integer, MarketItems> itemsMap = new HashMap<Integer, MarketItems>();
	/** 上一次刷新时间 */
	private long lastFreshTime;
	/** 下一次刷新的时间差 */
	private long nextFreshNeed;
	/** 世界商品 */
	private String worldItems;
	/** 世界商品表 */
	private Map<Integer, WorldMarketItems> worldItemsMap = new HashMap<>();
	/**世界商品刷新时间*/
	private long worldItemsRefreshTime;
	/**临时的世界商城（每天会刷新，显示将魂）*/
	private Map<String,WorldTempMarketItem> tempWorldItemsMap = new HashMap<>();


	private boolean change;

	/** ------------------------------get&set---------------------------- */

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public byte getFreshNumber() {
		return freshNumber;
	}

	public void setFreshNumber(byte freshNumber) {
		this.freshNumber = freshNumber;
	}

	public byte getMarketLimit() {
		return marketLimit;
	}

	public void setMarketLimit(byte marketLimit) {
		this.marketLimit = marketLimit;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public Map<Integer, MarketItems> getItemsMap() {
		return itemsMap;
	}

	public void setItemsMap(Map<Integer, MarketItems> itemsMap) {
		this.itemsMap = itemsMap;
	}

	public void setItems(String items) {
		if (items != null && !items.equals("")) {
			String[] strs = items.split(";");
			for (int i = 0; i < strs.length; i++) {
				String[] itemStr = strs[i].split(",");
				MarketItems tempMarketItems = new MarketItems();
				tempMarketItems.setId(i);
				tempMarketItems.setItemId(Integer.parseInt(itemStr[0]));
				tempMarketItems.setIsSell(Integer.parseInt(itemStr[1]));
				tempMarketItems.setSalePrice(Integer.parseInt(itemStr[2]));
				tempMarketItems.setIsBargin(Byte.parseByte(itemStr[3]));

				this.itemsMap.put(tempMarketItems.getId(), tempMarketItems);
			}
		}
	}

	public String getItems() {
		StringBuilder sb = new StringBuilder();		
		for(int i = 0;i<itemsMap.size();i++){
			MarketItems tempMarketItems = itemsMap.get(i);
			if(tempMarketItems.getIsSell() == 1)
				continue;
			sb.append(tempMarketItems.getItemId()).append(",").append(tempMarketItems.getIsSell()).append(",")
			.append(tempMarketItems.getSalePrice()).append(",").append(tempMarketItems.getIsBargin())
			.append(";");
		}
		this.items = sb.toString();
		return items;
	}

	public long getLastFreshTime() {
		return lastFreshTime;
	}

	public void setLastFreshTime(long lastFreshTime) {
		this.lastFreshTime = lastFreshTime;
	}

	public long getNextFreshNeed() {
		return nextFreshNeed;
	}

	public void setNextFreshNeed(long nextFreshNeed) {
		this.nextFreshNeed = nextFreshNeed;
	}

	public String getWorldItems() {
		StringBuilder sb = new StringBuilder();
		for (WorldMarketItems m : worldItemsMap.values()) {
			int id = m.getId();
			int inventory = m.getInventory();
			sb.append(id).append(",").append(inventory).append(";");
		}
		this.worldItems = sb.toString();
		return worldItems;
	}

	public void setWorldItems(String worldItems) {
		if (worldItems == null || worldItems.equals("")) {
			return;
		}
		this.worldItems = worldItems;
		String[] worldItemsList = worldItems.split(";");
		
		for (String str : worldItemsList) {
			String[] items = str.split(",");
			int id = Integer.parseInt(items[0]);
			int inventory = Integer.parseInt(items[1]);
			
			WorldMarketItems w = new WorldMarketItems();
			w.setId(id);
			w.setInventory(inventory);

			worldItemsMap.put(id, w);
		}
	}

	public Map<Integer, WorldMarketItems> getWorldItemsMap() {
		return worldItemsMap;
	}

	public void setWorldItemsMap(Map<Integer, WorldMarketItems> worldItemsMap) {
		this.worldItemsMap = worldItemsMap;
	}

	public long getWorldItemsRefreshTime() {
		return worldItemsRefreshTime;
	}

	public void setWorldItemsRefreshTime(long worldItemsRefreshTime) {
		this.worldItemsRefreshTime = worldItemsRefreshTime;
	}

	public Map<String,WorldTempMarketItem> getTempWorldItemsMap() {
		return tempWorldItemsMap;
	}

	public void setTempWorldItemsMap(Map<String,WorldTempMarketItem> tempWorldItemsMap) {
		this.tempWorldItemsMap = tempWorldItemsMap;
	}



	
}
