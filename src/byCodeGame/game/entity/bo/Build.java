package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import byCodeGame.game.entity.po.BuildQueue;
import byCodeGame.game.entity.po.BuildInfo;
import byCodeGame.game.entity.po.LevyInfo;
import byCodeGame.game.entity.po.LvCheck;
import byCodeGame.game.entity.po.VisitSaleData;
import byCodeGame.game.entity.po.VisitScienceData;
import byCodeGame.game.entity.po.VisitTreasureData;

public class Build {

	private int roleId;

	/** 农田等级 */
	private String farmLv;
	/** 农田等级map */
	private Map<Integer, Integer> farmLvMap = new HashMap<Integer, Integer>();
	// /** 农田上一次征收时间 */
	// private long farmLastIncomeTime;
	/** 农田征收数量缓存 */
	// private int farmInComeCache;
	// /** 农田建筑队列 */
	// private String farmBuildQueue;
	// /** 农田建筑队列map key:队列id value:buildQueue对象 */
	// private Map<Byte,BuildQueue> farmBuildQueueMap = new HashMap<Byte,
	// BuildQueue>();

	/** 民居等级 */
	private String houseLv;
	/** 民居等级map */
	private Map<Integer, Integer> houseMap = new HashMap<Integer, Integer>();
	/** 民居上一次征收时间 */
	// private long houseLastIncomeTime;
	/** 民居征收数量缓存 */
	// private int houseInComeCache;
	// /** 民居建筑队列 */
	// private String houseBuildQueue;
	// /** 民居建筑队列map key:队列id value:buildQueue对象 */
	// private Map<Byte, BuildQueue> houseBuildQueueMap = new HashMap<Byte,
	// BuildQueue>();

	/** 兵营等级 */
	// private String barrackLv;
	/** 兵营等级map */
	// private Map<Integer, Integer> barrackLvMap = new HashMap<Integer,
	// Integer>();
	/** 兵营上一次征收时间 */
	// private long barrackLastIncomeTime;
	/** 兵营征收数量缓存 */
	// private int barrackInComeCache;
	// /** 兵营建筑队列 */
	// private String barrackBuildQueue;
	// /** 兵营建筑队列map key:队列id value:buildQueue对象 */
	// private Map<Byte, BuildQueue> barrackBuildQueueMap = new HashMap<Byte,
	// BuildQueue>();
	/** 配属武将Str */
	private String attachHero;
	/** 配属武将Map <资源地类型,武将id> */
	private Map<Byte, BuildInfo> attachHeroMap = new HashMap<Byte, BuildInfo>();

	/** 征收数据<heroId,LevyInfo> */
	private Map<Integer, LevyInfo> levyMap = new HashMap<Integer, LevyInfo>();
	/** 征收Str */
	private String levyStr;
	/** 建筑等级 */
	private String buildLv;
	/** 建筑等级Map */
	private Map<Byte, Short> buildLvMap = new HashMap<Byte, Short>();
	/** 征收缓存 */
	private Map<Byte, Integer> incomeCacheMap = new HashMap<>();
	/** 征收缓存字符串 */
	private String incomeCacheStr;
	/** 建筑上次征收时间 */
	private Map<Byte, Long> buildLastIncomeTimeMap = new HashMap<>();
	/** 建筑上次征收时间字符串 */
	private String buildLastIncomeTimeStr;
	/** 寻宝字符串 */
	private String visitTreasureData;
	/** 寻宝表 */
	private HashMap<Integer, VisitTreasureData> visitTreasureDataMap = new HashMap<>();
	/** 战功表 */
	private String visitScienceData;
	/** 战功表 */
	private HashMap<Integer, VisitScienceData> visitScienceDataMap = new HashMap<>();
	/** 金币征收次数 */
	private Map<Byte, Integer> levyTimeMap = new HashMap<>();
	/** 免费征收次数*/
	private Map<Byte,Integer> freeLevyTimeMap = new HashMap<>();
	/** 征收前天缓存字符串 */
	private String incomeLastDayCacheStr;
	/** 之前缓存里的容量 */
	private Map<Byte, Integer> incomeLastDayCacheMap = new HashMap<>();

	private int levySpeedTimes;

	private boolean change;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getFarmLv() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= farmLvMap.size(); i++) {
			int temeLv = farmLvMap.get(i);
			sb.append(temeLv).append(",");
		}
		this.farmLv = sb.toString();
		return farmLv;
	}

	public void setFarmLv(String farmLv) {
		if (farmLv != null && !farmLv.equals("")) {
			String[] strs = farmLv.split(",");
			for (int i = 0; i < strs.length; i++) {
				String string = strs[i];
				this.farmLvMap.put(i + 1, Integer.parseInt(string));
			}
		}
	}

	// public long getFarmLastIncomeTime() {
	// return farmLastIncomeTime;
	// }
	//
	// public void setFarmLastIncomeTime(long farmLastIncomeTime) {
	// this.farmLastIncomeTime = farmLastIncomeTime;
	// }
	//
	// public int getFarmInComeCache() {
	// return farmInComeCache;
	// }
	//
	// public void setFarmInComeCache(int farmInComeCache) {
	// this.farmInComeCache = farmInComeCache;
	// }

	public Map<Integer, Integer> getFarmLvMap() {
		return farmLvMap;
	}

	public String getHouseLv() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= houseMap.size(); i++) {
			int temeLv = houseMap.get(i);
			sb.append(temeLv).append(",");
		}
		this.houseLv = sb.toString();
		return houseLv;
	}

	public void setHouseLv(String houseLv) {
		if (houseLv != null && !houseLv.equals("")) {
			String[] strs = houseLv.split(",");
			for (int i = 0; i < strs.length; i++) {
				String string = strs[i];
				this.houseMap.put(i + 1, Integer.parseInt(string));
			}
		}
	}

	public Map<Integer, Integer> getHouseMap() {
		return houseMap;
	}

	public void setHouseMap(Map<Integer, Integer> houseMap) {
		this.houseMap = houseMap;
	}

	// public long getHouseLastIncomeTime() {
	// return houseLastIncomeTime;
	// }
	//
	// public void setHouseLastIncomeTime(long houseLastIncomeTime) {
	// this.houseLastIncomeTime = houseLastIncomeTime;
	// }
	//
	// public int getHouseInComeCache() {
	// return houseInComeCache;
	// }
	//
	// public void setHouseInComeCache(int houseInComeCache) {
	// this.houseInComeCache = houseInComeCache;
	// }

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	// public String getFarmBuildQueue() {
	// StringBuilder sb = new StringBuilder();
	// for (byte i = 1; i <= getFarmBuildQueueMap().size(); i++) {
	// BuildQueue tempFarmQueue = getHouseBuildQueueMap().get(i);
	// sb.append(tempFarmQueue.getTime()).append(",").append(tempFarmQueue.getLastUpTime())
	// .append(",").append(tempFarmQueue.getOpen()).append(";");
	// }
	// this.farmBuildQueue = sb.toString();
	// return farmBuildQueue;
	// }
	// public void setFarmBuildQueue(String farmBuildQueue) {
	// if(farmBuildQueue != null && !farmBuildQueue.equals("")){
	// String[] strs = farmBuildQueue.split(";");
	// for (int i = 0; i < strs.length; i++) {
	// String[] strBuild = strs[i].split(",");
	// BuildQueue tempBuildQueue = new BuildQueue();
	// tempBuildQueue.setId(i+1);
	// tempBuildQueue.setTime(Integer.parseInt(strBuild[0]));
	// tempBuildQueue.setLastUpTime(Long.parseLong(strBuild[1]));
	// tempBuildQueue.setOpen(Byte.parseByte(strBuild[2]));
	// this.getFarmBuildQueueMap().put((byte)tempBuildQueue.getId(),
	// tempBuildQueue);
	// }
	// }
	// }
	// public String getHouseBuildQueue() {
	// StringBuilder sb = new StringBuilder();
	// for (byte i = 1; i <= getHouseBuildQueueMap().size(); i++) {
	// BuildQueue tempHouseQueue = getHouseBuildQueueMap().get(i);
	// sb.append(tempHouseQueue.getTime()).append(",").append(tempHouseQueue.getLastUpTime())
	// .append(",").append(tempHouseQueue.getOpen()).append(";");
	// }
	// this.houseBuildQueue = sb.toString();
	// return houseBuildQueue;
	// }
	// public void setHouseBuildQueue(String houseBuildQueue) {
	// if(houseBuildQueue != null && !houseBuildQueue.equals("")){
	// String[] strs = houseBuildQueue.split(";");
	// for (int i = 0; i < strs.length; i++) {
	// String[] strBuild = strs[i].split(",");
	// BuildQueue tempHouseQueue = new BuildQueue();
	// tempHouseQueue.setId(i+1);
	// tempHouseQueue.setTime(Integer.parseInt(strBuild[0]));
	// tempHouseQueue.setLastUpTime(Long.parseLong(strBuild[1]));
	// tempHouseQueue.setOpen(Byte.parseByte(strBuild[2]));
	// this.getHouseBuildQueueMap().put((byte)tempHouseQueue.getId(),
	// tempHouseQueue);
	// }
	// }
	// }
	// public Map<Byte,BuildQueue> getFarmBuildQueueMap() {
	// return farmBuildQueueMap;
	// }
	// public void setFarmBuildQueueMap(Map<Byte,BuildQueue> farmBuildQueueMap)
	// {
	// this.farmBuildQueueMap = farmBuildQueueMap;
	// }
	// public Map<Byte, BuildQueue> getHouseBuildQueueMap() {
	// return houseBuildQueueMap;
	// }
	// public void setHouseBuildQueueMap(Map<Byte, BuildQueue>
	// houseBuildQueueMap) {
	// this.houseBuildQueueMap = houseBuildQueueMap;
	// }
	//

	// public String getBarrackLv() {
	// StringBuilder sb = new StringBuilder();
	// for (int i = 1; i <= barrackLvMap.size(); i++) {
	// int temeLv = barrackLvMap.get(i);
	// sb.append(temeLv).append(",");
	// }
	// this.barrackLv = sb.toString();
	// return barrackLv;
	// }
	//
	// public void setBarrackLv(String barrackLv) {
	// if (barrackLv != null && !barrackLv.equals("")) {
	// String[] strs = barrackLv.split(",");
	// for (int i = 0; i < strs.length; i++) {
	// String string = strs[i];
	// this.barrackLvMap.put(i + 1, Integer.parseInt(string));
	// }
	// }
	// }
	//
	// public Map<Integer, Integer> getBarrackLvMap() {
	// return barrackLvMap;
	// }
	//
	// public void setBarrackMap(Map<Integer, Integer> barrackLvMap) {
	// this.barrackLvMap = barrackLvMap;
	// }
	//
	// public long getBarrackLastIncomeTime() {
	// return barrackLastIncomeTime;
	// }
	//
	// public void setBarrackLastIncomeTime(long barrackLastIncomeTime) {
	// this.barrackLastIncomeTime = barrackLastIncomeTime;
	// }
	//
	// public int getBarrackInComeCache() {
	// return barrackInComeCache;
	// }
	//
	// public void setBarrackInComeCache(int barrackInComeCache) {
	// this.barrackInComeCache = barrackInComeCache;
	// }

	// public String getBarrackBuildQueue() {
	// StringBuilder sb = new StringBuilder();
	// for (byte i = 1; i <= getBarrackBuildQueueMap().size(); i++) {
	// BuildQueue tempFarmQueue = getBarrackBuildQueueMap().get(i);
	// sb.append(tempFarmQueue.getTime()).append(",").append(tempFarmQueue.getLastUpTime())
	// .append(",").append(tempFarmQueue.getOpen()).append(";");
	// }
	// this.barrackBuildQueue = sb.toString();
	// return barrackBuildQueue;
	// }
	// public void setBarrackBuildQueue(String barrackBuildQueue) {
	// if(barrackBuildQueue != null && !barrackBuildQueue.equals("")){
	// String[] strs = barrackBuildQueue.split(";");
	// for (int i = 0; i < strs.length; i++) {
	// String[] strBuild = strs[i].split(",");
	// BuildQueue tempBarrackQueue = new BuildQueue();
	// tempBarrackQueue.setId(i+1);
	// tempBarrackQueue.setTime(Integer.parseInt(strBuild[0]));
	// tempBarrackQueue.setLastUpTime(Long.parseLong(strBuild[1]));
	// tempBarrackQueue.setOpen(Byte.parseByte(strBuild[2]));
	// this.getBarrackBuildQueueMap().put((byte)tempBarrackQueue.getId(),
	// tempBarrackQueue);
	// }
	// }
	// }
	// public Map<Byte, BuildQueue> getBarrackBuildQueueMap() {
	// return barrackBuildQueueMap;
	// }
	// public void setBarrackBuildQueueMap(Map<Byte, BuildQueue>
	// barrackBuildQueueMap) {
	// this.barrackBuildQueueMap = barrackBuildQueueMap;
	// }
	public Map<Byte, BuildInfo> getAttachHeroMap() {
		return attachHeroMap;
	}

	public void setAttachHeroMap(Map<Byte, BuildInfo> attachHeroMap) {
		this.attachHeroMap = attachHeroMap;
	}

	public String getAttachHero() {
		StringBuilder sb = new StringBuilder();
		for (Entry<Byte, BuildInfo> x : attachHeroMap.entrySet()) {
			sb.append(x.getKey()).append(",").append(x.getValue().getHeroId()).append(",")
					.append(x.getValue().getLastTime()).append(",").append(x.getValue().getLastAutoTime()).append(";");
		}
		this.attachHero = sb.toString();
		return attachHero;
	}

	public void setAttachHero(String attachHero) {
		if (attachHero != null && !attachHero.equals("")) {
			String[] strs = attachHero.split(";");
			for (String x : strs) {
				String[] string = x.split(",");
				BuildInfo temp = new BuildInfo(string);
				this.attachHeroMap.put(Byte.parseByte(string[0]), temp);
			}
		}
	}

	public Map<Integer, LevyInfo> getLevyMap() {
		return levyMap;
	}

	public void setLevyMap(Map<Integer, LevyInfo> levyMap) {
		this.levyMap = levyMap;
	}

	public String getLevyStr() {
		StringBuilder sb = new StringBuilder();
		for (LevyInfo x : levyMap.values()) {
			sb.append(x.getHeroId()).append(",").append(x.getType()).append(",").append(x.getStartTime()).append(",")
					.append(x.getCd()).append(",").append(x.getValue()).append(",").append(x.getValueOther())
					.append(",").append(x.getLastRefreshTime()).append(";");
		}
		this.levyStr = sb.toString();
		return levyStr;
	}

	public void setLevyStr(String levyStr) {
		if (levyStr != null && !levyStr.equals("")) {
			String[] strs = levyStr.split(";");
			for (String x : strs) {
				String[] temp = x.split(",");
				LevyInfo levyInfo = new LevyInfo(temp);
				this.levyMap.put(levyInfo.getHeroId(), levyInfo);
			}
		}
	}

	public Map<Byte, Short> getBuildLvMap() {
		return buildLvMap;
	}

	public void setBuildLvMap(Map<Byte, Short> buildLvMap) {
		this.buildLvMap = buildLvMap;
	}

	public String getBuildLv() {
		StringBuilder sb = new StringBuilder();
		for (Entry<Byte, Short> entry : buildLvMap.entrySet()) {
			sb.append(entry.getKey()).append(",").append(entry.getValue()).append(";");
		}
		this.buildLv = sb.toString();
		return buildLv;
	}

	public void setBuildLv(String buildLv) {
		if (buildLv != null && !buildLv.equals("")) {
			String[] strs = buildLv.split(";");
			for (String x : strs) {
				String[] temp = x.split(",");
				this.buildLvMap.put(Byte.parseByte(temp[0]), Short.parseShort(temp[1]));
			}
		}
	}

	/**
	 * @return the incomeCacheMap
	 */
	public Map<Byte, Integer> getInComeCacheMap() {
		return incomeCacheMap;
	}
	
	/**
	 * 修改暂存表 <br/>
	 * 'deltaValue'表示差值，可能是正值，可能是负值<br/>
	 * 
	 * @param type
	 * @param deltaValue
	 * @author wcy 2016年4月15日
	 */
	public void addInComeCacheMap(byte type,int deltaValue){
		Integer origin = incomeCacheMap.get(type);

		int total = origin + deltaValue;
		if (origin >= 0 && deltaValue >= 0 && total < 0) {
			total = Integer.MAX_VALUE;
		}
		incomeCacheMap.put(type, total);		
	}

	/**
	 * @return the incomeCacheStr
	 */
	public String getInComeCacheStr() {
		StringBuffer sb = new StringBuffer();
		for (Entry<Byte, Integer> entry : incomeCacheMap.entrySet()) {
			byte type = entry.getKey();
			int cache = entry.getValue();
			sb.append(type + "," + cache + ";");
		}
		incomeCacheStr = sb.toString();
		return incomeCacheStr;
	}

	/**
	 * @param incomeCacheStr the incomeCacheStr to set
	 */
	public void setInComeCacheStr(String incomeCacheStr) {
		if (incomeCacheStr != null && !incomeCacheStr.equals("")) {
			this.incomeCacheStr = incomeCacheStr;
			String[] s1 = incomeCacheStr.split(";");
			for (String s : s1) {
				String[] s3 = s.split(",");
				byte type = Byte.valueOf(s3[0]);
				int cache = Integer.valueOf(s3[1]);
				incomeCacheMap.put(type, cache);
			}

		}

	}

	/**
	 * @return the incomeLastTimeMap
	 */
	public Map<Byte, Long> getBuildLastIncomeTimeMap() {
		return buildLastIncomeTimeMap;
	}

	/**
	 * @return the incomeLastTimeStr
	 */
	public String getBuildLastIncomeTimeStr() {
		StringBuffer sb = new StringBuffer();
		for (Entry<Byte, Long> entry : buildLastIncomeTimeMap.entrySet()) {
			byte type = entry.getKey();
			long time = entry.getValue();
			sb.append(type + "," + time + ";");
		}
		this.buildLastIncomeTimeStr = sb.toString();
		return buildLastIncomeTimeStr;
	}

	/**
	 * @param incomeLastTimeStr the incomeLastTimeStr to set
	 */
	public void setBuildLastIncomeTimeStr(String incomeLastTimeStr) {
		if (incomeLastTimeStr != null && !incomeLastTimeStr.equals("")) {
			this.buildLastIncomeTimeStr = incomeLastTimeStr;
			String[] s1 = incomeLastTimeStr.split(";");
			for (String s : s1) {
				String[] s2 = s.split(",");
				byte type = Byte.valueOf(s2[0]);
				long time = Long.valueOf(s2[1]);
				buildLastIncomeTimeMap.put(type, time);
			}
		}
	}

	/**
	 * @return the visitTreasureData
	 */
	public String getVisitTreasureData() {
		StringBuffer sb = new StringBuffer();
		for (VisitTreasureData visitTreasureData : visitTreasureDataMap.values()) {
			int startTime = visitTreasureData.getStartTime();
			int num = visitTreasureData.getNum();
			int itemId = visitTreasureData.getItemId();
			int heroId = visitTreasureData.getHeroId();
			sb.append(startTime + "," + itemId + "," + num + "," + heroId + ";");
		}
		visitTreasureData = sb.toString();
		return visitTreasureData;
	}

	/**
	 * @param visitTreasureData the visitTreasureData to set
	 */
	public void setVisitTreasureData(String visitTreasureData) {
		this.visitTreasureData = visitTreasureData;
		if (visitTreasureData == null || visitTreasureData.equals(""))
			return;

		String[] s = visitTreasureData.split(";");
		for (String s1 : s) {
			String[] s2 = s1.split(",");
			int startTime = Integer.parseInt(s2[0]);
			int itemId = Integer.parseInt(s2[1]);
			int num = Integer.parseInt(s2[2]);
			int heroId = Integer.parseInt(s2[3]);

			VisitTreasureData data = new VisitTreasureData();
			data.setHeroId(heroId);
			data.setItemId(itemId);
			data.setNum(num);
			data.setStartTime(startTime);

			visitTreasureDataMap.put(startTime, data);
		}
	}

	/**
	 * @return the visitTreasureDataMap
	 */
	public HashMap<Integer, VisitTreasureData> getVisitTreasureDataMap() {
		return visitTreasureDataMap;
	}

	/**
	 * @param visitTreasureDataMap the visitTreasureDataMap to set
	 */
	public void setVisitTreasureDataMap(HashMap<Integer, VisitTreasureData> visitTreasureDataMap) {
		this.visitTreasureDataMap = visitTreasureDataMap;
	}

	public String getVisitScienceData() {
		StringBuffer sb = new StringBuffer();
		for (VisitScienceData visitScienceData : visitScienceDataMap.values()) {
			int startTime = visitScienceData.getStartTimeId();
			int num = visitScienceData.getNum();
			int heroId = visitScienceData.getHeroId();
			String scienceName = visitScienceData.getScienceName();
			sb.append(startTime + "," + num + "," + heroId + "," + scienceName + ";");
		}
		this.visitScienceData = sb.toString();
		return visitScienceData;
	}

	public void setVisitScienceData(String visitScienceData) {
		this.visitScienceData = visitScienceData;

		if (visitScienceData == null || visitScienceData.equals(""))
			return;

		String[] s = visitScienceData.split(";");
		for (String s1 : s) {
			String[] s2 = s1.split(",");
			int startTime = Integer.parseInt(s2[0]);
			int num = Integer.parseInt(s2[1]);
			int heroId = Integer.parseInt(s2[2]);
			String scienceName = s2[3];

			VisitScienceData data = new VisitScienceData();
			data.setStartTimeId(startTime);
			data.setHeroId(heroId);
			data.setNum(num);
			data.setScienceName(scienceName);

			visitScienceDataMap.put(startTime, data);
		}
	}

	public HashMap<Integer, VisitScienceData> getVisitScienceDataMap() {
		return visitScienceDataMap;
	}

	public void setVisitScienceDataMap(HashMap<Integer, VisitScienceData> visitScienceDataMap) {
		this.visitScienceDataMap = visitScienceDataMap;
	}

	/**
	 * @return the levySpeedTimes
	 */
	public int getLevySpeedTimes() {
		return levySpeedTimes;
	}

	/**
	 * @param levySpeedTimes the levySpeedTimes to set
	 */
	public void setLevySpeedTimes(int levySpeedTimes) {
		this.levySpeedTimes = levySpeedTimes;
	}

	/**
	 * @return the levyTimeMap
	 */
	public Map<Byte, Integer> getLevyTimeMap() {
		return levyTimeMap;
	}

	/**
	 * @param levyTimeMap the levyTimeMap to set
	 */
	public void setLevyTimeMap(Map<Byte, Integer> levyTimeMap) {
		this.levyTimeMap = levyTimeMap;
	}

	public String getIncomeLastDayCacheStr() {
		StringBuilder sb = new StringBuilder();
		for (Entry<Byte, Integer> entry : incomeLastDayCacheMap.entrySet()) {
			byte type = entry.getKey();
			int value = entry.getValue();

			sb.append(type);
			sb.append(",");
			sb.append(value);
			sb.append(";");
		}
		this.incomeLastDayCacheStr = sb.toString();
		return incomeLastDayCacheStr;
	}

	public void setIncomeLastDayCacheStr(String incomeLastDayCacheStr) {
		if (incomeLastDayCacheStr == null || incomeLastDayCacheStr.equals("")) {
			return;
		}

		this.incomeLastDayCacheStr = incomeLastDayCacheStr;
		
		String[] typeAndCacheList = incomeLastDayCacheStr.split(";");
		for (String typeAndCacheStr : typeAndCacheList) {
			String[] typeAndCache = typeAndCacheStr.split(",");
			byte type = Byte.parseByte(typeAndCache[0]);
			int value = Integer.parseInt(typeAndCache[1]);
			
			this.incomeLastDayCacheMap.put(type,value);			
		}
	}

	public Map<Byte, Integer> getIncomeLastDayCacheMap() {
		return incomeLastDayCacheMap;
	}

	public Map<Byte,Integer> getFreeLevyTimeMap() {
		return freeLevyTimeMap;
	}

	public void setFreeLevyTimeMap(Map<Byte,Integer> freeLevyTimeMap) {
		this.freeLevyTimeMap = freeLevyTimeMap;
	}

}
