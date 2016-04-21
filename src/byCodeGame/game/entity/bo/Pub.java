package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import byCodeGame.game.entity.po.PubMapInfo;
import byCodeGame.game.entity.po.VisitData;
import byCodeGame.game.entity.po.WineDesk;
import byCodeGame.game.moudle.pub.PubConstant;

/**
 * 酒馆类
 * 
 * @author xjd
 *
 */
public class Pub {
	/** 玩家ID */
	private int roleId;
	/** 玩家当日会谈次数 */
	private int talksNumber;
	/** 玩家地图信息 */
	private String mapInfo;
	/** 玩家地图集合 */
	private Map<Integer, PubMapInfo> pubMap = new HashMap<Integer, PubMapInfo>();
	/** 玩家当前里程数 */
	private int talkMile;
	/** 可招募的武将最大数量 */
	private short recruitHeroNum;
	/** 总共会谈次数战功 */
	private int totalTalksNumber;
	/** 上次单次会谈开始时间 */
	private int freeMoneyStartTalkTime;
	/** 上次多次会谈开始时间 */
	private int freeGoldStartTalkTime;

	/** <酒桌号,酒桌> */
	private HashMap<Byte, WineDesk> deskHeroMap = new HashMap<>();
	/** 加速会谈的次数<会谈的类型,会谈的次数> */
	private HashMap<Byte, Integer> freeTalkTimeMap = new HashMap<>();
	private int freeChangeDeskHeroTimes;
	/** 桌子状态字符串 */
	private String desk;
	/** 酒桌状态表 */
	private HashMap<Byte, Byte> deskMap = new HashMap<>();

	/** 银币寻访武将 */
	private String heroMoney;
	/** 银币武将Set */
	private Set<Integer> heroMoneySet = new HashSet<Integer>();
	/** 金币寻访武将 */
	private String heroGold;
	/** 金币武将Set */
	private Set<Integer> heroGoldSet = new HashSet<Integer>();

	/** 寻访结果 */
	private String visitData;
	/** 寻访结果集合 */
	private Map<Integer, VisitData> visitDataMap = new HashMap<Integer, VisitData>();

	/** 免费战功抽 次数 */
	private int freeExploitNum;
	/** 免费金币抽 次数 */
	private int freeGoldNum;
	/** 总共会谈次数金币*/
	private int totalTalksNumber2;
	/** 未抽中战功武将的次数		*/
	private int missHero;
	
	private boolean chang;

	/** ---------------------------get&set--------------------------------- */
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getTalksNumber() {
		return talksNumber;
	}

	public void setTalksNumber(int talksNumber) {
		this.talksNumber = talksNumber;
	}

	public Map<Integer, PubMapInfo> getPubMap() {
		return pubMap;
	}

	public void setPubMap(Map<Integer, PubMapInfo> pubMap) {
		this.pubMap = pubMap;
	}

	public String getMapInfo() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= pubMap.size(); i++) {
			PubMapInfo tempMapInfo = pubMap.get(i);
			sb.append(tempMapInfo.getMapId()).append(",").append(tempMapInfo.getPrestige()).append(";");
		}
		this.mapInfo = sb.toString();
		return mapInfo;
	}

	public void setMapInfo(String mapInfo) {
		if (mapInfo != null && !mapInfo.equals("")) {
			String[] strs = mapInfo.split(";");
			for (int i = 0; i < strs.length; i++) {
				String[] mapStr = strs[i].split(",");
				PubMapInfo tempPubMapInfo = new PubMapInfo();
				tempPubMapInfo.setMapId(Integer.parseInt(mapStr[0]));
				tempPubMapInfo.setPrestige(Integer.parseInt(mapStr[1]));
				this.pubMap.put(tempPubMapInfo.getMapId(), tempPubMapInfo);
			}
		}
	}

	public boolean isChang() {
		return chang;
	}

	public void setChang(boolean chang) {
		this.chang = chang;
	}

	public int getTalkMile() {
		return talkMile;
	}

	public void setTalkMile(int talkMile) {
		this.talkMile = talkMile;
	}

	public short getRecruitHeroNum() {
		return recruitHeroNum;
	}

	public void setRecruitHeroNum(short recruitHeroNum) {
		this.recruitHeroNum = recruitHeroNum;
	}

	public int getTotalTalksNumber() {
		return totalTalksNumber;
	}

	public void setTotalTalksNumber(int totalTalksNumber) {
		this.totalTalksNumber = totalTalksNumber;
	}

	public int getFreeMoneyStartTalkTime() {
		return freeMoneyStartTalkTime;
	}

	public void setFreeMoneyStartTalkTime(int freeMoneyStartTalkTime) {
		this.freeMoneyStartTalkTime = freeMoneyStartTalkTime;
	}

	public int getFreeGoldStartTalkTime() {
		return freeGoldStartTalkTime;
	}

	public void setFreeGoldStartTalkTime(int freeGoldStartTalkTime) {
		this.freeGoldStartTalkTime = freeGoldStartTalkTime;
	}

	/**
	 * 获得银币免费单次会谈剩余时间
	 * 
	 * @return
	 */
	public int getRemainMoneyTalkTime() {
		if (freeMoneyStartTalkTime == 0) {
			return freeMoneyStartTalkTime;
		} else {
			int nowTime = (int) (System.currentTimeMillis() / 1000);
			int passTime = nowTime - freeMoneyStartTalkTime;

			return passTime <= 0 ? 0 : PubConstant.FREE_MONEY_SINGLE_TALK_TIME - passTime;
		}
	}

	/**
	 * 获得金币免费单次会谈剩余时间
	 * 
	 * @return
	 */
	public int getRemainGoldTalkTime() {
		if (freeGoldStartTalkTime == 0) {
			return freeGoldStartTalkTime;
		} else {
			int nowTime = (int) (System.currentTimeMillis() / 1000);
			int passTime = nowTime - freeGoldStartTalkTime;

			return passTime <= 0 ? 0 : PubConstant.FREE_GOLD_SINGLE_TALK_TIME - passTime;
		}
	}

	/**
	 * 获得银币免费单次会谈结束时间
	 * 
	 * @return
	 */
	public int getFreeMoneyEndTalkTime() {
		return freeMoneyStartTalkTime == 0 ? freeMoneyStartTalkTime : freeMoneyStartTalkTime
				+ PubConstant.FREE_MONEY_SINGLE_TALK_TIME;
	}

	/**
	 * 获得银币免费单次会谈结束时间
	 * 
	 * @return
	 */
	public int getFreeGoldEndTalkTime() {
		return freeGoldStartTalkTime == 0 ? freeGoldStartTalkTime : freeGoldStartTalkTime
				+ PubConstant.FREE_GOLD_SINGLE_TALK_TIME;
	}

	/**
	 * 获得酒桌上的武将<酒桌号,酒桌>
	 * 
	 * @return
	 * @author wcy
	 */
	public HashMap<Byte, WineDesk> getWineDeskMap() {
		return deskHeroMap;
	}

	/**
	 * 加速会谈的次数<会谈的类型,会谈的次数>
	 * 
	 * @return
	 * @author wcy
	 */
	public HashMap<Byte, Integer> getFreeTalkTimeMap() {
		return freeTalkTimeMap;
	}

	/**
	 * 获得酒桌的状态
	 * 
	 * @return
	 * @author wcy
	 */
	public HashMap<Byte, Byte> getDeskMap() {
		return deskMap;
	}

	/**
	 * @return the deskStr 1正在使用，2可用，3不可用
	 */
	public String getDesk() {
		StringBuffer sb = new StringBuffer();
		for (Entry<Byte, Byte> entry : deskMap.entrySet()) {
			byte deskIndex = entry.getKey();
			byte status = entry.getValue();
			sb.append(deskIndex + "," + status + ";");
		}
		desk = sb.toString();
		return desk;
	}

	/**
	 * @param desk the deskStr to set
	 */
	public void setDesk(String desk) {
		this.desk = desk;
		String[] s = desk.split(";");
		for (String s1 : s) {
			String[] s3 = s1.split(",");
			byte deskIndex = Byte.valueOf(s3[0]);
			byte status = Byte.valueOf(s3[1]);
			deskMap.put(deskIndex, status);
		}
	}

	public Set<Integer> getHeroMoneySet() {
		return heroMoneySet;
	}

	public void setHeroMoneySet(Set<Integer> heroMoneySet) {
		this.heroMoneySet = heroMoneySet;
	}

	public String getHeroMoney() {
		StringBuilder sb = new StringBuilder();
		for (Integer x : heroMoneySet) {
			sb.append(x).append(",");
		}
		this.heroMoney = sb.toString();
		return heroMoney;
	}

	public void setHeroMoney(String heroMoney) {
		if (heroMoney != null && !heroMoney.equals("")) {
			String[] strs = heroMoney.split(",");
			for (int i = 0; i < strs.length; i++) {
				this.heroMoneySet.add(Integer.parseInt(strs[i]));
			}
		}
		this.heroMoney = heroMoney;
	}

	public Set<Integer> getHeroGoldSet() {
		return heroGoldSet;
	}

	public void setHeroGoldSet(Set<Integer> heroGoldSet) {
		this.heroGoldSet = heroGoldSet;
	}

	public String getHeroGold() {
		StringBuilder sb = new StringBuilder();
		for (Integer x : heroGoldSet) {
			sb.append(x).append(",");
		}
		this.heroGold = sb.toString();
		return heroGold;
	}

	public void setHeroGold(String heroGold) {
		if (heroGold != null && !heroGold.equals("")) {
			String[] strs = heroGold.split(",");
			for (int i = 0; i < strs.length; i++) {
				this.heroGoldSet.add(Integer.parseInt(strs[i]));
			}
		}
		this.heroGold = heroGold;
	}

	/**
	 * @return the freeChangeDeskHeroTimes
	 */
	public int getFreeChangeDeskHeroTimes() {
		return freeChangeDeskHeroTimes;
	}

	/**
	 * @param freeChangeDeskHeroTimes the freeChangeDeskHeroTimes to set
	 */
	public void setFreeChangeDeskHeroTimes(int freeChangeDeskHeroTimes) {
		this.freeChangeDeskHeroTimes = freeChangeDeskHeroTimes;
	}

	public Map<Integer, VisitData> getVisitDataMap() {
		return visitDataMap;
	}

	public void setVisitDataMap(Map<Integer, VisitData> visitDataMap) {
		this.visitDataMap = visitDataMap;
	}

	public String getVisitData() {
		StringBuilder sb = new StringBuilder();
		for (VisitData x : this.visitDataMap.values()) {
			sb.append(x.getId()).append(",").append(x.getHeroId()).append(",").append(x.getPrestige()).append(",")
					.append(x.getCityId()).append(";");
		}
		this.visitData = sb.toString();
		return visitData;
	}

	public void setVisitData(String visitData) {
		if (visitData != null && !visitData.equals("")) {
			String[] strs = visitData.split(";");
			for (int i = 0; i < strs.length; i++) {
				String[] mapStr = strs[i].split(",");
				VisitData data = new VisitData();
				data.setId(Integer.parseInt(mapStr[0]));
				data.setHeroId(Integer.parseInt(mapStr[1]));
				data.setPrestige(Integer.parseInt(mapStr[2]));
				data.setCityId(Integer.parseInt(mapStr[3]));
				this.visitDataMap.put(data.getId(), data);
			}
		}
		this.visitData = visitData;
	}

	/**
	 * @return the freeExploitNum
	 */
	public int getFreeExploitNum() {
		return freeExploitNum;
	}

	/**
	 * @param freeExploitNum the freeExploitNum to set
	 */
	public void setFreeExploitNum(int freeExploitNum) {
		this.freeExploitNum = freeExploitNum;
	}

	/**
	 * @return the freeGoldNum
	 */
	public int getFreeGoldNum() {
		return freeGoldNum;
	}

	/**
	 * @param freeGoldNum the freeGoldNum to set
	 */
	public void setFreeGoldNum(int freeGoldNum) {
		this.freeGoldNum = freeGoldNum;
	}

	/**
	 * @return the totalTalksNumber2
	 */
	public int getTotalTalksNumber2() {
		return totalTalksNumber2;
	}

	/**
	 * @param totalTalksNumber2 the totalTalksNumber2 to set
	 */
	public void setTotalTalksNumber2(int totalTalksNumber2) {
		this.totalTalksNumber2 = totalTalksNumber2;
	}

	public int getMissHero() {
		return missHero;
	}

	public void setMissHero(int missHero) {
		this.missHero = missHero;
	}

}
