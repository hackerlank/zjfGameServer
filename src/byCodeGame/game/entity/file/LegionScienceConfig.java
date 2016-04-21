package byCodeGame.game.entity.file;

/**
 * 军团科技配置类
 * @author 王君辉 
 *
 */
public class LegionScienceConfig {

	/** 科技类型	 1军团等级 2征税 3粮食 4角色经验 5战功等级*/
	private int type;
	/** 等级	 */
	private int lv;
	/** 下一级所需经验	 */
	private int nextLvExp;
	/** 加成值	 */
	private String value;
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public int getNextLvExp() {
		return nextLvExp;
	}
	public void setNextLvExp(int nextLvExp) {
		this.nextLvExp = nextLvExp;
	}
	public double getValue() {
		return Double.parseDouble(value);
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
