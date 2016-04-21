package byCodeGame.game.entity.bo;

import java.util.HashSet;
import java.util.Set;

/**
 * 玩家的签到信息
 * @author xjd
 *
 */
public class Sign {
	/**  玩家ID		*/
	private int roleId;
	/**	 签到月份		*/
	private int signMonth;
	/** 签到天数		*/
	private String signDays;
	/** 签到天数Set	*/
	private Set<Integer> signDaysSet = new HashSet<Integer>();
	/** 签到奖励		*/
	private String signAward;
	/** 签到奖励Set	*/
	private Set<Integer> signAwardSet = new HashSet<Integer>();
	/** 补签次天数		*/
	private String signRetroactive;
	/** 补签Set		*/
	private Set<Integer> signRetroactiveSet = new HashSet<Integer>();
	
	private boolean chang;
	
	
	
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getSignMonth() {
		return signMonth;
	}
	public void setSignMonth(int signMonth) {
		this.signMonth = signMonth;
	}
	public String getSignDays() {
		StringBuilder sb = new StringBuilder();
		for(Integer signDay : this.signDaysSet){
			sb.append(signDay).append(",");
		}
		this.signDays = sb.toString();
		return signDays;
	}

	public void setSignDays(String signDays) {
		if(signDays != null && !signDays.equals("")){
			String[] strs = signDays.split(",");
			for(String signDay : strs){
				this.signDaysSet.add(Integer.valueOf(signDay));
			}
		}
	}
	public Set<Integer> getSignDaysSet() {
		return signDaysSet;
	}
	public void setSignDaysSet(Set<Integer> signDaysSet) {
		this.signDaysSet = signDaysSet;
	}
	public boolean isChang() {
		return chang;
	}
	public void setChang(boolean chang) {
		this.chang = chang;
	}
	public Set<Integer> getSignAwardSet() {
		return signAwardSet;
	}
	public void setSignAwardSet(Set<Integer> signAwardSet) {
		this.signAwardSet = signAwardSet;
	}
	
	public String getSignAward() {
		StringBuilder sb = new StringBuilder();
		for(Integer awardId : this.signAwardSet){
			sb.append(awardId).append(",");
		}
		this.signAward = sb.toString();
		return signAward;
	}

	public void setSignAward(String signAward) {
		if(signAward != null && !signAward.equals("")){
			String[] strs = signAward.split(",");
			for(String awardId : strs){
				this.signAwardSet.add(Integer.valueOf(awardId));
			}
		}
	}
	public Set<Integer> getSignRetroactiveSet() {
		return signRetroactiveSet;
	}
	public void setSignRetroactiveSet(Set<Integer> signRetroactiveSet) {
		this.signRetroactiveSet = signRetroactiveSet;
	}
	public String getSignRetroactive() {
		StringBuilder sb = new StringBuilder();
		for(Integer signRetroactiveDay : this.signRetroactiveSet){
			sb.append(signRetroactiveDay).append(",");
		}
		this.signRetroactive = sb.toString();
		return signRetroactive;
	}
	public void setSignRetroactive(String signRetroactive) {
		if(signRetroactive != null && !signRetroactive.equals("")){
			String[] strs = signRetroactive.split(",");
			for(String awardId : strs){
				this.signRetroactiveSet.add(Integer.valueOf(awardId));
			}
		}
	}
}
