package byCodeGame.game.entity.file;

public class HeroLvConfig {

	private int lv;
	/** 基础经验值	 */
	private int baseExp;
	/** 下一级升级经验值	 */
	private int nextExp;
	/**主动技能花费*/
	private int initiativeSkillCost;
	/**被动技能花费*/
	private int passiveSkillCost;
	
	
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public int getBaseExp() {
		return baseExp;
	}
	public void setBaseExp(int baseExp) {
		this.baseExp = baseExp;
	}
	public int getNextExp() {
		return nextExp;
	}
	public void setNextExp(int nextExp) {
		this.nextExp = nextExp;
	}
	public int getInitiativeSkillCost() {
		return initiativeSkillCost;
	}
	public void setInitiativeSkillCost(int initiativeSkillCost) {
		this.initiativeSkillCost = initiativeSkillCost;
	}
	public int getPassiveSkillCost() {
		return passiveSkillCost;
	}
	public void setPassiveSkillCost(int passiveSkillCost) {
		this.passiveSkillCost = passiveSkillCost;
	}
}
