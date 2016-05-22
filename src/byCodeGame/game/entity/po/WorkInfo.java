package byCodeGame.game.entity.po;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.bo.Prop;

/**
 * 工作信息’
 * 
 * @author AIM
 *
 */
public class WorkInfo {
	/** 工作id */
	private String workId;
	/** 宠物id */
	private int heroId;
	/** 开始时间 */
	private int startTime;
	/** 上次刷新时间 */
	private int lastRefreshTime;
	/** 所属玩家 */
	private int masterRoleId;
	/** 工作玩家 */
	private int workRoleId;
	/** 道具列表 */
	private Map<Integer, Prop> propMap = new HashMap<>();

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getLastRefreshTime() {
		return lastRefreshTime;
	}

	public int getMasterRoleId() {
		return masterRoleId;
	}

	public void setMasterRoleId(int masterRoleId) {
		this.masterRoleId = masterRoleId;
	}

	public int getWorkRoleId() {
		return workRoleId;
	}

	public void setWorkRoleId(int workRoleId) {
		this.workRoleId = workRoleId;
	}

	public void setLastRefreshTime(int lastRefreshTime) {
		this.lastRefreshTime = lastRefreshTime;
	}

	public Map<Integer, Prop> getPropMap() {
		return propMap;
	}

}
