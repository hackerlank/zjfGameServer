package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.po.ArenaFightData;
import byCodeGame.game.entity.po.ArenaTarget;

public class RoleArena {

	private int roleId;
	/** 竞技场等级	 */
	private byte lv;
	/** 竞技场排名	 */
	private int exp;
	/** 上场武将	 */
	private int heroId;
	/** 竞技场是否达到升级条件	 */
	private boolean arenaUp;
	/** 竞技场目标集合	 */
	private Map<Byte, ArenaTarget> targetMap = new HashMap<Byte, ArenaTarget>();
	/** 最后一次刷新目标用户的时间	 */
	private long lastRefreshTargetTime;
	/** 上一次竞技场战斗时间	 */
	private long lastFightTime;
	/** 今日挑战次数	 */
	private byte fightTimes;
	/** 增加次数	 */
	private byte addFightTimes;
	/** 连续胜利	 */
	private byte continuousWinTimes;
	/** 上一场竞技场战斗数据	 */
	private ArenaFightData lastArenaFightData;
	
	
	/** 神兽 key:id  value:次数	 */
	private Map<Integer, Byte> animalMap = new HashMap<Integer, Byte>(){
		private static final long serialVersionUID = 1L;
		{
			put(1,(byte)0);
			put(2,(byte)0);
			put(3,(byte)0);
		}
	};
	/** 今日喂养的神兽	 */
	private int todayAnimal;
	
	private boolean change;

	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public boolean isChange() {
		return change;
	}
	public void setChange(boolean change) {
		this.change = change;
	}
	public void clearAnimalMap(){
		getAnimalMap().clear();
		getAnimalMap().put(1,(byte)0);
		getAnimalMap().put(2,(byte)0);
		getAnimalMap().put(3,(byte)0);
	}
	public boolean isArenaUp() {
		return arenaUp;
	}
	public void setArenaUp(boolean arenaUp) {
		this.arenaUp = arenaUp;
	}
	public int getTodayAnimal() {
		return todayAnimal;
	}
	public void setTodayAnimal(int todayAnimal) {
		this.todayAnimal = todayAnimal;
	}
	public Map<Integer, Byte> getAnimalMap() {
		return animalMap;
	}
	public void setAnimalMap(Map<Integer, Byte> animalMap) {
		this.animalMap = animalMap;
	}
	public Map<Byte, ArenaTarget> getTargetMap() {
		return targetMap;
	}
	public void setTargetMap(Map<Byte, ArenaTarget> targetMap) {
		this.targetMap = targetMap;
	}
	public byte getLv() {
		return lv;
	}
	public void setLv(byte lv) {
		this.lv = lv;
	}
	public long getLastRefreshTargetTime() {
		return lastRefreshTargetTime;
	}
	public void setLastRefreshTargetTime(long lastRefreshTargetTime) {
		this.lastRefreshTargetTime = lastRefreshTargetTime;
	}
	public long getLastFightTime() {
		return lastFightTime;
	}
	public void setLastFightTime(long lastFightTime) {
		this.lastFightTime = lastFightTime;
	}
	public byte getFightTimes() {
		return fightTimes;
	}
	public void setFightTimes(byte fightTimes) {
		this.fightTimes = fightTimes;
	}
	public byte getAddFightTimes() {
		return addFightTimes;
	}
	public void setAddFightTimes(byte addFightTimes) {
		this.addFightTimes = addFightTimes;
	}
	public byte getContinuousWinTimes() {
		return continuousWinTimes;
	}
	public void setContinuousWinTimes(byte continuousWinTimes) {
		this.continuousWinTimes = continuousWinTimes;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public ArenaFightData getLastArenaFightData() {
		return lastArenaFightData;
	}
	public void setLastArenaFightData(ArenaFightData lastArenaFightData) {
		this.lastArenaFightData = lastArenaFightData;
	}
}
