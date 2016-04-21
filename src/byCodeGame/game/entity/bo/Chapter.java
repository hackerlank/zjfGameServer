package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 玩家关卡类
 * @author 王君辉
 *
 */
public class Chapter {

	private int roleId;
	/** 当前已经完成的关卡ID	 */
	private int nowChapterId;
	/** 关卡星数 (关卡id,星数;关卡id,星数;........)	 */
	private String star;
	/** 关卡星数MAP  key:关卡ID  value:星数(1:1星  2:2星  3:3星)	 */
	private Map<Integer, Byte> starMap = new HashMap<Integer, Byte>();
	/** 每日所攻打的次数	 */
	private Map<Integer, Byte> timesMap = new HashMap<Integer, Byte>();
	/** 每日刷新关卡的次数	 */
	private Map<Integer, Byte> refreshTimesMap = new HashMap<Integer, Byte>();
	/** 存放每章节星星数量	 */
	private Map<Integer, Integer> allStarMap = new HashMap<Integer, Integer>();
	/** 存放已经领取的章节奖励 */
	private String alreadyGetAward ;
	/** 已经领取的奖励Map	 */
	private Set<Integer> alreadyGetAwardSet = new HashSet<Integer>();
	
	private boolean change;
	
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getNowChapterId() {
		return nowChapterId;
	}
	public void setNowChapterId(int nowChapterId) {
		this.nowChapterId = nowChapterId;
	}
	public String getStar() {
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<Integer, Byte> entry : this.getStarMap().entrySet()){
			sb.append(entry.getKey()).append(",").append(entry.getValue()).append(";");
		}
		this.star = sb.toString();
		return star;
	}
	public void setStar(String star) {
		if(star != null && !star.equals("")){
			String[] strs = star.split(";");
			for(String strValue : strs){
				String[] value = strValue.split(",");
				this.getStarMap().put(Integer.valueOf(value[0]), Byte.valueOf(value[1]));
			}
			this.setAllStarMap(this.starMap);
		}
	}
	public Map<Integer, Byte> getStarMap() {
		return starMap;
	}
	public boolean isChange() {
		return change;
	}
	public void setChange(boolean change) {
		this.change = change;
	}

	public Map<Integer, Byte> getTimesMap() {
		return timesMap;
	}

	public void setTimesMap(Map<Integer, Byte> timesMap) {
		this.timesMap = timesMap;
	}
	public Map<Integer, Byte> getRefreshTimesMap() {
		return refreshTimesMap;
	}
	public void setRefreshTimesMap(Map<Integer, Byte> refreshTimesMap) {
		this.refreshTimesMap = refreshTimesMap;
	}
	public Map<Integer, Integer> getAllStarMap() {
		return allStarMap;
	}
	public void setAllStarMap(Map<Integer, Byte> starMap) {
		this.allStarMap.clear();
		for(Entry<Integer, Byte> x : starMap.entrySet())
		{
			Integer temp = (x.getKey()-80000)/100;
			int tempStr = x.getValue();
			int allStr = 0;
			if(this.allStarMap.containsKey(temp))
			{
				allStr = this.allStarMap.get(temp) + tempStr;
			}else {
				allStr = tempStr;
			}
			this.allStarMap.remove(temp);
			this.allStarMap.put(temp, allStr);
		}
	}
	public Set<Integer> getAlreadyGetAwardSet() {
		return alreadyGetAwardSet;
	}
	public void setAlreadyGetAwardSet(Set<Integer> alreadyGetAwardSet) {
		this.alreadyGetAwardSet = alreadyGetAwardSet;
	}

	public String getAlreadyGetAward() {
		StringBuilder sb = new StringBuilder();
		for(Integer x : this.alreadyGetAwardSet){
			sb.append(x).append(",");
		}
		this.alreadyGetAward = sb.toString();
		return alreadyGetAward;
	}
	public void setAlreadyGetAward(String alreadyGetAward) {
		if(star != null && !star.equals("")){
			String[] strs = star.split(",");
			for(String strValue : strs){
				this.alreadyGetAwardSet.add(Integer.parseInt(strValue));
			}
		}
	}
}
