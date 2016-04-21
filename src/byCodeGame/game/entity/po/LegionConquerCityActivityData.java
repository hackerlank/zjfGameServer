package byCodeGame.game.entity.po;

import java.util.HashSet;
import java.util.Set;

/**
 * 活动进度
 * 
 * @author wcy 2016年4月18日
 *
 */
public class LegionConquerCityActivityData extends ActivityData {

	public LegionConquerCityActivityData() {
	}

	public LegionConquerCityActivityData(String s) {		
		String[] strs = s.split(",");
		this.legionId = Integer.parseInt(strs[0]);
		this.nowTime = Integer.parseInt(strs[1]);
		for(int i = 2;i<strs.length;i++){
			cityIdSet.add(Integer.parseInt(strs[i]));
		}
	}

	private int legionId;

	private int nowTime;
	
	private Set<Integer> cityIdSet = new HashSet<>();

	public void setLegionId(int legionId) {
		this.legionId = legionId;
	}

	public int getLegionId() {
		return legionId;
	}

	public void setNowTime(int nowTime) {
		this.nowTime = nowTime;
	}

	public int getNowTime() {
		return nowTime;
	}

	public void setCityIdSet(Set<Integer> cityIdSet) {
		this.cityIdSet = cityIdSet;
	}

	public Set<Integer> getCityIdSet() {
		return cityIdSet;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(legionId).append(",");
		sb.append(nowTime).append(",");
		for(Integer cityId:cityIdSet){
			sb.append(cityId).append(",");
		}
		super.progressStr = sb.toString();
		return progressStr;
	}
}
