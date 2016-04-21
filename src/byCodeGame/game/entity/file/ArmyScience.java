package byCodeGame.game.entity.file;
/**
 * 
 * @author wcy
 *
 */
public class ArmyScience {
	private int id;
	private int needLv;
	private int cost;
	private int lv;
	private int nextId;//下一等级编号
	private int armySkillID;
	private String desc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNeedLv() {
		return needLv;
	}

	public void setNeedLv(int needLv) {
		this.needLv = needLv;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}

	public int getArmySkillID() {
		return armySkillID;
	}

	public void setArmySkillID(int armySkillID) {
		this.armySkillID = armySkillID;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
