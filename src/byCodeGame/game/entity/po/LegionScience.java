package byCodeGame.game.entity.po;

/**
 * 军团科技类
 * @author 王君辉
 *
 */
public class LegionScience {

	/** 科技编号 1军团等级 2征税 3兵营 4粮食 5声望	 */
	private int id;
	/** 科技等级	 */
	private int lv;
	/** 科技当前经验	 */
	private int exp;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
}
