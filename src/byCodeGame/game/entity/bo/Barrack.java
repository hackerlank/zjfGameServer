package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import byCodeGame.game.cache.file.ArmyScienceCache;
import byCodeGame.game.entity.file.ArmyScience;
import byCodeGame.game.entity.po.BarrackData;
import byCodeGame.game.entity.po.BuildQueue;

/**
 * 玩家兵营数据库类
 * 
 * @author 王君辉
 *
 */
public class Barrack {

	private int roleId;
	/** 兵力 (兵种ID,数量;兵种ID,数量;....) */
	private String troops;
	/** 兵力map */
	private Map<Integer, Integer> troopMap = new HashMap<Integer, Integer>();
	/** 建筑队列数据 (时间,上一次更新时间,是否开启;.....) */
	private String queue;
	/** 建筑队列MAP */
	private Map<Byte, BuildQueue> queueMap = new HashMap<Byte, BuildQueue>();
	/** 建筑等级(类型,等级;类型,等级;类型,等级;....) */
	private String buildLv;
	/** 建筑等级Map key:id value:等级 */
	private Map<Byte, BarrackData> buildLvMap = new HashMap<Byte, BarrackData>();

	private String armySkillLv;

	private HashMap<Integer, Integer> armySkillLvMap = new HashMap<>();

	private int offensiveTimes;

	private boolean change;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getTroops() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, Integer> entry : this.getTroopMap().entrySet()) {
			sb.append(entry.getKey()).append(",").append(entry.getValue()).append(";");
		}
		this.troops = sb.toString();
		return troops;
	}

	public void setTroops(String troops) {
		if (troops != null && !troops.equals("")) {
			String[] strs = troops.split(";");
			for (String dataStr : strs) {
				String[] dataArr = dataStr.split(",");
				this.getTroopMap().put(Integer.valueOf(dataArr[0]), Integer.valueOf(dataArr[1]));
			}
		}
		this.troops = troops;
	}

	public Map<Integer, Integer> getTroopMap() {
		return troopMap;
	}

	/**
	 * 获取指定兵种ID的数量
	 * 
	 * @param id
	 * @return
	 */
	public int getArmsNumById(int id) {
		if (!this.troopMap.containsKey(id)) {
			this.troopMap.put(id, 0);
		}

		return troopMap.get(id);
	}

	/**
	 * 指定兵种增加兵力
	 * 
	 * @param id
	 * @param num
	 */
	public void addArmsNumById(int id, int num) {
		if (!this.troopMap.containsKey(id)) {
			this.troopMap.put(id, 0);
		}
		int newNum = troopMap.get(id) + num;
		troopMap.put(id, newNum);
	}

	public String getQueue() {
		StringBuilder sb = new StringBuilder();
		for (byte i = 1; i <= this.getQueueMap().size(); i++) {
			BuildQueue tempFarmQueue = getQueueMap().get(i);
			sb.append(tempFarmQueue.getTime()).append(",").append(tempFarmQueue.getLastUpTime()).append(",")
					.append(tempFarmQueue.getOpen()).append(";");
		}
		this.queue = sb.toString();
		return queue;
	}

	public void setQueue(String queue) {
		if (queue != null && !queue.equals("")) {
			String[] strs = queue.split(";");
			for (int i = 0; i < strs.length; i++) {
				String[] dataArr = strs[i].split(",");
				BuildQueue buildQueue = new BuildQueue();
				buildQueue.setId(i + 1);
				buildQueue.setTime(Integer.parseInt(dataArr[0]));
				buildQueue.setLastUpTime(Long.parseLong(dataArr[1]));
				buildQueue.setOpen(Byte.parseByte(dataArr[2]));
				this.getQueueMap().put((byte) buildQueue.getId(), buildQueue);
			}
		}
	}

	public Map<Byte, BuildQueue> getQueueMap() {
		return queueMap;
	}

	public void setQueueMap(Map<Byte, BuildQueue> queueMap) {
		this.queueMap = queueMap;
	}

	public String getBuildLv() {
		StringBuilder sb = new StringBuilder();
		for (byte i = 1; i <= this.getBuildLvMap().size(); i++) {
			BarrackData barrackData = getBuildLvMap().get(i);
			sb.append(barrackData.getType()).append(",").append(barrackData.getLv()).append(";");
		}
		this.buildLv = sb.toString();
		return buildLv;
	}

	public void setBuildLv(String buildLv) {
		if (buildLv != null && !buildLv.equals("")) {
			String[] strs = buildLv.split(";");
			for (byte i = 0; i < strs.length; i++) {
				String[] dataArr = strs[i].split(",");
				BarrackData barrackData = new BarrackData();
				barrackData.setId((byte) (i + 1));
				barrackData.setType(Byte.parseByte(dataArr[0]));
				barrackData.setLv(Short.valueOf(dataArr[1]));
				this.getBuildLvMap().put(barrackData.getId(), barrackData);
			}
		}
	}

	public Map<Byte, BarrackData> getBuildLvMap() {
		return buildLvMap;
	}

	public void setBuildLvMap(Map<Byte, BarrackData> buildLvMap) {
		this.buildLvMap = buildLvMap;
	}

	public void setTroopMap(Map<Integer, Integer> troopMap) {
		this.troopMap = troopMap;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	/**
	 * @return the armySkillLv
	 */
	public String getArmySkillLv() {
		StringBuilder sb = new StringBuilder();
		for (Entry<Integer, Integer> entry : armySkillLvMap.entrySet()) {
			int armySkillId = entry.getKey();
			int armySkillIdLv = entry.getValue();
			sb.append(armySkillId + "," + armySkillIdLv + ";");
		}
		armySkillLv = sb.toString();
		return armySkillLv;
	}

	/**
	 * @param armySkillLv the armySkillLv to set
	 */
	public void setArmySkillLv(String armySkillLv) {
		this.armySkillLv = armySkillLv;
		if (armySkillLv == null || armySkillLv.equals("")) {
			return;
		}
		String[] s = armySkillLv.split(";");
		for (String s1 : s) {
			String[] s2 = s1.split(",");
			int armySkillId = Integer.valueOf(s2[0]);
			int armySkillLvId = Integer.valueOf(s2[1]);
			armySkillLvMap.put(armySkillId, armySkillLvId);
		}
	}

	/**
	 * 获得兵击等级表<skillId,skillLvId>
	 * @return
	 * @author wcy
	 */
	public HashMap<Integer, Integer> getArmySkillLvMap() {
		return armySkillLvMap;
	}

	/**
	 * @return the offensiveTimes
	 */
	public int getOffensiveTimes() {
		return offensiveTimes;
	}

	/**
	 * @param offensiveTimes the offensiveTimes to set
	 */
	public void setOffensiveTimes(int offensiveTimes) {
		this.offensiveTimes = offensiveTimes;
	}

	/**
	 * 获得兵击等级
	 * @param armySkillId
	 * @return
	 * @author wcy
	 */
	public int getArmySkillLvById(int armySkillId){
		Integer armySkillLvId = getArmySkillLvMap().get(armySkillId);
		if (armySkillLvId == null) {
			return 0;
		}

		ArmyScience armyScience = ArmyScienceCache.getArmyScienceById(armySkillLvId);
		int lv = armyScience.getLv();
		if(lv == 1){
			return 0;
		}
		
		lv -= 1;
		return lv;
	}
}
