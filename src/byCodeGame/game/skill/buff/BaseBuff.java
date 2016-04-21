package byCodeGame.game.skill.buff;

import byCodeGame.game.cache.file.BuffConfigCache;
import byCodeGame.game.entity.file.BuffConfig;
import byCodeGame.game.entity.po.ArenaFightData;
import byCodeGame.game.entity.po.ArenaObjFightData;


public class BaseBuff {

	protected int buffId;
	
	protected BuffConfig buffConfig;
	
	protected ArenaFightData arenaFightData;
	/** BUFF施放者	 */
	protected ArenaObjFightData arenaObjFightData;
	/** BUFF影响者	 */
	protected ArenaObjFightData targetObjData;
	/** 使用时间	 */
	protected int useTime;
	/** 起始时间	 */
	protected long startTime;
	/** buff加成的值	 */
	protected int intValue;
	protected double doubleValue;
	/** buff是否有效0	 */
	protected boolean canUse;
	
	public BaseBuff(int buffId,ArenaFightData arenaFightData,
			ArenaObjFightData arenaObjFightData,ArenaObjFightData targetData){
		this.arenaObjFightData = arenaObjFightData;
		this.targetObjData = targetData;
		this.arenaFightData = arenaFightData;
		this.buffId = buffId;
		BuffConfig buffConfig = BuffConfigCache.getBuffConfig(buffId);
		this.buffConfig = buffConfig;
		this.startTime = arenaFightData.getNowTime();
		this.useTime = buffConfig.getContinueTime();
	}

	
	public void update(){};
	
	public void quit(){};
	
	
	public ArenaObjFightData getArenaObjFightData() {
		return arenaObjFightData;
	}

	public void setArenaObjFightData(ArenaObjFightData arenaObjFightData) {
		this.arenaObjFightData = arenaObjFightData;
	}


	public int getUseTime() {
		return useTime;
	}


	public void setUseTime(int useTime) {
		this.useTime = useTime;
	}


	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	public int getValue() {
		return intValue;
	}


	public void setValue(int intValue) {
		this.intValue = intValue;
	}


	public boolean isCanUse() {
		return canUse;
	}


	public void setCanUse(boolean canUse) {
		this.canUse = canUse;
	}


	public ArenaObjFightData getTargetObjData() {
		return targetObjData;
	}


	public void setTargetObjData(ArenaObjFightData targetObjData) {
		this.targetObjData = targetObjData;
	}


	public ArenaFightData getArenaFightData() {
		return arenaFightData;
	}


	public void setArenaFightData(ArenaFightData arenaFightData) {
		this.arenaFightData = arenaFightData;
	}


	public int getBuffId() {
		return buffId;
	}


	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}

}
