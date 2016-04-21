package byCodeGame.game.entity.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import byCodeGame.game.entity.po.History;
import byCodeGame.game.entity.po.WorldMarketItems;

public class Server {
	/** 开服时间 */
	private int startTime;
	/** 主键 */
	private int serverId;
	/** 服务器当前显示年份 */
	private int year;
	/** 当前季节 */
	private byte season;

	private String worldItems;
	/** 声望商品表 */
	private ConcurrentHashMap<Integer, WorldMarketItems> worldItemsMap = new ConcurrentHashMap<>();
	/**世界商城刷新时间*/
	private long worldItemsRefreshTime;
	/**玩家等级*/
	private String roleLvInfo;
	
	private ConcurrentHashMap<Integer,Integer> roleLvMap = new ConcurrentHashMap<>();
	/**世界等级*/
	private int worldLv;
	/** 世界大战历史记录		*/
	private String historyStr;
	/** 世界大战历史记录List	*/
	private List<History> historyList = new ArrayList<History>();
	/** 国家城市数量 */
	private Map<Byte,Integer> nationCityNumMap = new HashMap<>();
	
	private boolean change;

	/**
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the serverId
	 */
	public int getServerId() {
		return serverId;
	}

	/**
	 * @param serverId the serverId to set
	 */
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	/**
	 * @param change the change to set
	 */
	public void setChange(boolean change) {
		this.change = change;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public byte getSeason() {
		return season;
	}

	public void setSeason(byte season) {
		this.season = season;
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
    //old
	public synchronized boolean buyWorldItemsMap(int id) {
		WorldMarketItems worldMarketItems = worldItemsMap.get(id);
		int inventory = worldMarketItems.getInventory();
		
		if (inventory > 0) {
			worldMarketItems.setInventory(inventory - 1);
			return true;
		}
		return false;
	}
	
	//new 
//	public boolean buyWorldItemsMap(int id) {
//		WorldMarketItems worldMarketItems = worldItemsMap.get(id);
//		boolean buySuccess = false;
//		synchronized (this) {
//			int inventory = worldMarketItems.getInventory();
//			if (inventory > 0) {
//				worldMarketItems.setInventory(inventory - 1);
//				buySuccess = true;
//			}else{
//				buySuccess = false;
//			}
//		}
//		return buySuccess;		
//	}
	

	public ConcurrentHashMap<Integer, WorldMarketItems> getWorldItemsMap() {
		return worldItemsMap;
	}

	public void setWorldItemsMap(ConcurrentHashMap<Integer, WorldMarketItems> worldItemsMap) {
		this.worldItemsMap = worldItemsMap;
	}

	/**
	 * @return the worldItemsRefreshTime
	 */
	public long getWorldItemsRefreshTime() {
		return worldItemsRefreshTime;
	}

	/**
	 * @param worldItemsRefreshTime the worldItemsRefreshTime to set
	 */
	public void setWorldItemsRefreshTime(long worldItemsRefreshTime) {
		this.worldItemsRefreshTime = worldItemsRefreshTime;
	}

	/**
	 * @return the roleLvInfo
	 */
	public String getRoleLvInfo() {
		StringBuffer sb = new StringBuffer();
		for (Entry<Integer,Integer> entry : roleLvMap.entrySet()) {
			int roleId = entry.getKey();
			int lv = entry.getValue();
			
			sb.append(roleId).append(",").append(lv).append(";");		
		}
		this.roleLvInfo = sb.toString();
		return roleLvInfo;
	}

	/**
	 * @param roleLvInfo the roleLvInfo to set
	 */
	public void setRoleLvInfo(String roleLvInfo) {
		if(roleLvInfo == null||roleLvInfo.equals("")){
			return;
		}
		this.roleLvInfo = roleLvInfo;
		String[] roleArrays = roleLvInfo.split(";");
		for(String roleInfo:roleArrays){
			String[] roleInfoStr = roleInfo.split(",");
			int roleId = Integer.parseInt(roleInfoStr[0]);
			int lv = Short.parseShort(roleInfoStr[1]);
			roleLvMap.put(roleId, lv);
		}
	}

	/**
	 * @return the roleLvMap
	 */
	public ConcurrentHashMap<Integer,Integer> getRoleLvMap() {
		return roleLvMap;
	}

	/**
	 * @param roleLvMap the roleLvMap to set
	 */
	public void setRoleLvMap(ConcurrentHashMap<Integer,Integer> roleLvMap) {
		this.roleLvMap = roleLvMap;
	}

	/**
	 * @return the worldLv
	 */
	public int getWorldLv() {
		return worldLv;
	}

	/**
	 * @param worldLv the worldLv to set
	 */
	public void setWorldLv(int worldLv) {
		this.worldLv = worldLv;
	}

	public List<History> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<History> historyList) {
		this.historyList = historyList;
	}

	public String getHistoryStr() {
		StringBuilder sb = new StringBuilder();
		for(History x : this.historyList)
		{
			sb.append(x.getTime()).append("~").append(x.getYear()).append("~")
				.append(x.getType()).append("~").append(x.getStr()).append("~")
				.append(x.getUuid()).append("!");
		}
		return historyStr;
	}

	public void setHistoryStr(String historyStr) {
		if(historyStr != null && !historyStr.equals(""))
		{
			String[] strs = historyStr.split("!");
			for(String x : strs)
			{
				String[] temp = x.split("~");
				History history = new History(temp);
				this.historyList.add(history);
			}
		}
		this.historyStr = historyStr;
	}
	
	public void addHistory(History history)
	{
		if(this.historyList.size() >= 50)
		{
			this.historyList.remove(0);
		}
		this.historyList.add(history);
	}

	/**
	 * @return the nationCityNumMap
	 */
	public Map<Byte,Integer> getNationCityNumMap() {
		return nationCityNumMap;
	}

	/**
	 * @param nationCityNumMap the nationCityNumMap to set
	 */
	public void setNationCityNumMap(Map<Byte,Integer> nationCityNumMap) {
		this.nationCityNumMap = nationCityNumMap;
	}

	

	
}
