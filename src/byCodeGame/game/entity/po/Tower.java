package byCodeGame.game.entity.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bycode.game.battle.data.CorpData;
import cn.bycode.game.battle.data.TroopData;

/**
 * 试练塔 — role下属性
 * @author xjd
 *
 */
public class Tower {
	/** 玩家ID						*/
	private int roleId;
	/** 今日次数						*/
	private int time;
	/** 今日复活次数					*/
	private int reviveTime;
	/** 试炼类型						*/
	private int useId;
	/** 试炼阶段						*/
	private int process;
	/** 上一次试炼通过阶段				*/
	private int lastProcess;
	/** 当前使用的阵型					*/
	private TroopData troopData;
	/** 记录当前准备状态				*/
	private byte status;
	
	
	/** 玩家当前部队状态	key:heroId 	*/
	Map<Integer, CorpData> useData = new HashMap<Integer, CorpData>();
	
	/** NPC当前状态		key:process	*/
	private Map<Integer, TroopData> npcData = new HashMap<Integer, TroopData>();
	
	/** 当前选择的武将ID结合				*/
	private List<Integer> useHero = new ArrayList<Integer>();

	
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getUseId() {
		return useId;
	}

	public void setUseId(int useId) {
		this.useId = useId;
	}


	public Map<Integer, CorpData> getUseData() {
		return useData;
	}

	public void setUseData(Map<Integer, CorpData> useData) {
		this.useData = useData;
	}

	public Map<Integer, TroopData> getNpcData() {
		return npcData;
	}

	public void setNpcData(Map<Integer, TroopData> npcData) {
		this.npcData = npcData;
	}

	public List<Integer> getUseHero() {
		return useHero;
	}

	public void setUseHero(List<Integer> useHero) {
		this.useHero = useHero;
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}

	public int getLastProcess() {
		return lastProcess;
	}

	public void setLastProcess(int lastProcess) {
		this.lastProcess = lastProcess;
	}

	public TroopData getTroopData() {
		return troopData;
	}

	public void setTroopData(TroopData troopData) {
		this.troopData = troopData;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getReviveTime() {
		return reviveTime;
	}

	public void setReviveTime(int reviveTime) {
		this.reviveTime = reviveTime;
	}
}
