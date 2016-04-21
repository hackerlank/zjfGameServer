package byCodeGame.game.entity.file;
/**
 * 
 * @author wcy
 *
 */
public class ArmyLvConfig {
	private int lv;
	private int baseExp;
	private int nextExp;
	private int skillCost;

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

	public int getSkillCost() {
		return skillCost;
	}

	public void setSkillCost(int skillCost) {
		this.skillCost = skillCost;
	}

}
