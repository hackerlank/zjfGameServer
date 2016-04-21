package byCodeGame.game.skill;

import byCodeGame.game.entity.po.ArenaFightData;
import byCodeGame.game.entity.po.ArenaObjFightData;

public class BaseSkill {

	
	/** 技能ID	 */
	protected int skillId;
	/** 使用类型 1进如战斗即使用	 */
	protected int useType;
	
	protected ArenaObjFightData objData;
	protected ArenaFightData arenaFightData;
	
	public BaseSkill(){
		
	}
	
	/**
	 * 检查释放条件
	 * @return
	 */
	public boolean checkCondition(){
		return false;
	};
	
	/**
	 * 释放目标选择
	 */
	public void getTarget(){};
	
	/**
	 * 使用技能
	 */
	public void use(){};
	
	/**
	 * 检查当前的使用效果并更改效果 用于持续性的技能
	 * @param fightData
	 * @param obj
	 */
	public void checkNowEffect(){};

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public ArenaFightData getArenaFightData() {
		return arenaFightData;
	}

	public void setArenaFightData(ArenaFightData arenaFightData) {
		this.arenaFightData = arenaFightData;
	}

	public ArenaObjFightData getObjData() {
		return objData;
	}

	public void setObjData(ArenaObjFightData objData) {
		this.objData = objData;
	}

	public int getUseType() {
		return useType;
	}

	public void setUseType(int useType) {
		this.useType = useType;
	}
}
