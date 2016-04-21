package byCodeGame.game.entity.po;

import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.entity.bo.Role;

/**
 * 活动信息
 * 
 * @author wcy 2016年4月19日
 *
 */
public class ActivityMessage {
	public static final int CHANGE_CITY = 1;
	public static final int DISMISS_LEGION = 2;
	private Role role = null;
	private Legion legion = null;
	/** 攻占1公会解散2 */
	private int action = -1;
	
	private int value = -1;

	private int nowTime = -1;

	public void setNowTime(int nowTime) {
		this.nowTime = nowTime;
	}

	public int getNowTime() {
		return nowTime;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setLegion(Legion legion) {
		this.legion = legion;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getAction() {
		return action;
	}

	public Legion getLegion() {
		return legion;
	}

	public Role getRole() {
		return role;
	}

	public int getValue() {
		return value;
	}
}
