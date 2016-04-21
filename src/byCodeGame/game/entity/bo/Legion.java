package byCodeGame.game.entity.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import byCodeGame.game.entity.po.History;
import byCodeGame.game.entity.po.LegionScience;

/**
 * 军团类
 * @author 王君辉
 *
 */
public class Legion {

	/** 军团ID	 */
	private int legionId;
	/** 军团名称	 */
	private String name;
	/** 军团头像ID	 */
	private int faceId;
	/** 军团等级	 */
	private byte lv;
	/** 军团告示 */
	private String notice;
	/** 军团科技(等级,经验;等级,经验)	 */
	private String science;
	/** 军团科技MAP	 */
	private Map<Integer, LegionScience> scienceMap = new HashMap<Integer, LegionScience>();
	
	/** 成员最大数量	 */
	private int maxPeopleNum;
	/** 成员ID (玩家ID,玩家ID)	 */
	private String member;
	/** 成员ID集合 	 */
	private Set<Integer> memberSet = new HashSet<Integer>();
	/** 军团长ID	 */
	private int captainId;
	/** 副团长ID (玩家ID,玩家ID)	 */
	private String deputyCaptain;
	/** 副团长ID集合	 */
	private Set<Integer> deputyCaptainSet = new HashSet<Integer>();
	/** 申请中的玩家ID (玩家ID,玩家ID)	 */
	private String apply;
	/** 申请中的玩家ID集合	 		*/
	private List<Integer> applyList = new ArrayList<Integer>();
	/** 军团长设定的科技升级类型		*/
	private byte appointScience = 1;
	/** 军团的国籍设定				*/
	private byte nation;
	/** 自动接受申请功能的状态		*/
	private byte autoArgeeType;
	/** 自动接受申请的最小等级		*/
	private int minLv;
	/** 世界大战历史记录			*/
	private String historyStr;
	/** 世界大战历史记录List		*/
	private List<History> historyList = new ArrayList<History>();
	/** 集结旗编号					*/
	private int cityId;
	/** 上次插旗时间				*/
	private int lastCityTime;
	/** 旗号						*/
	private String shortName;
	
	
	/** 军团线程锁					*/
	private Lock lock = new ReentrantLock();
	
	private boolean change;
	
	
	public int getLegionId() {
		return legionId;
	}
	public void setLegionId(int legionId) {
		this.legionId = legionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFaceId() {
		return faceId;
	}
	public void setFaceId(int faceId) {
		this.faceId = faceId;
	}
	public byte getLv() {
		return lv;
	}
	public void setLv(byte lv) {
		this.lv = lv;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public int getMaxPeopleNum() {
		return maxPeopleNum;
	}
	public void setMaxPeopleNum(int maxPeopleNum) {
		this.maxPeopleNum = maxPeopleNum;
	}
	public int getCaptainId() {
		return captainId;
	}
	public void setCaptainId(int captainId) {
		this.captainId = captainId;
	}
	public boolean isChange() {
		return change;
	}
	public void setChange(boolean change) {
		this.change = change;
	}
	public Map<Integer, LegionScience> getScienceMap() {
		return scienceMap;
	}
	public void setScienceMap(Map<Integer, LegionScience> scienceMap) {
		this.scienceMap = scienceMap;
	}
	public Set<Integer> getMemberSet() {
		return memberSet;
	}
	public void setMemberSet(Set<Integer> memberSet) {
		this.memberSet = memberSet;
	}
	public Set<Integer> getDeputyCaptainSet() {
		return deputyCaptainSet;
	}
	public void setDeputyCaptainSet(Set<Integer> deputyCaptainSet) {
		this.deputyCaptainSet = deputyCaptainSet;
	}
	public List<Integer> getApplyList() {
		return applyList;
	}
	public void setApplyList(List<Integer> applyList) {
		this.applyList = applyList;
	}
	public String getScience() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= scienceMap.size(); i++) {
			LegionScience tempLegionScience = scienceMap.get(i);
			sb.append(tempLegionScience.getLv()).append(",")
			.append(tempLegionScience.getExp()).append(";");
		}
		this.science = sb.toString();
		return science;
	}
	public void setScience(String science) {
		if(science != null && !science.equals("")){
			String[] strs = science.split(";");
			for (int i = 0; i < strs.length; i++) {
				String[] scienceStr = strs[i].split(",");
				LegionScience tempLegionScience = new LegionScience();
				tempLegionScience.setId(i + 1);
				tempLegionScience.setLv(Integer.parseInt(scienceStr[0]));
				tempLegionScience.setExp(Integer.parseInt(scienceStr[1]));
				this.scienceMap.put(tempLegionScience.getId(), tempLegionScience);
			}
		}
	}
	
	public String getMember() {
		StringBuilder sb = new StringBuilder();
		for(Integer roleId : memberSet){
			sb.append(roleId).append(",");
		}
		this.member = sb.toString();
		return member;
	}
	public void setMember(String member) {
		if(member != null && !member.equals("")){
			String[] strs = member.split(",");
			for(String roleId : strs){
				this.memberSet.add(Integer.valueOf(roleId));
			}
		}
	}
	
	public String getDeputyCaptain() {
		StringBuilder sb = new StringBuilder();
		for(Integer roleId : deputyCaptainSet){
			sb.append(roleId).append(",");
		}
		this.deputyCaptain = sb.toString();
		return deputyCaptain;
	}
	public void setDeputyCaptain(String deputyCaptain) {
		if(deputyCaptain != null && !deputyCaptain.equals("")){
			String[] strs = deputyCaptain.split(",");
			for(String roleId : strs){
				this.deputyCaptainSet.add(Integer.valueOf(roleId));
			}
		}
	}
	
	public String getApply() {
		StringBuilder sb = new StringBuilder();
		for(Integer roleId : applyList){
			sb.append(roleId).append(",");
		}
		this.apply = sb.toString();
		return apply;
	}
	public void setApply(String apply) {
		if(apply != null && !apply.equals("")){
			String[] strs = apply.split(",");
			for(String roleId : strs){
				this.applyList.add(Integer.getInteger(roleId));
			}
		}
	}
	public byte getAppointScience() {
		return appointScience;
	}
	public void setAppointScience(byte appointScience) {
		this.appointScience = appointScience;
	}
	public Lock getLock() {
		return lock;
	}
	public void setLock(Lock lock) {
		this.lock = lock;
	}
	public byte getNation() {
		return nation;
	}
	public void setNation(byte nation) {
		this.nation = nation;
	}
	public byte getAutoArgeeType() {
		return autoArgeeType;
	}
	public void setAutoArgeeType(byte autoArgeeType) {
		this.autoArgeeType = autoArgeeType;
	}
	public int getMinLv() {
		return minLv;
	}
	public void setMinLv(int minLv) {
		this.minLv = minLv;
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
		return sb.toString();
	}
	public void setHistoryStr(String historyStr) {
		if(historyStr != null && !historyStr.equals(""))
		{
			String[] strs = historyStr.split("!");
			for(String x : strs)
			{
				String temp[] = x.split("~");
				History history = new History(temp);
				this.historyList.add(history);
			}
		}
		this.historyStr = historyStr;
	}
	
	/**
	 * 增加世界大战记录 带锁
	 * @param history
	 */
	public synchronized void addHistory(History history)
	{
		if(this.historyList.size() >= 50)
		{
			this.historyList.remove(0);
		}
		this.historyList.add(history);
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getLastCityTime() {
		return lastCityTime;
	}
	public void setLastCityTime(int lastCityTime) {
		this.lastCityTime = lastCityTime;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}
