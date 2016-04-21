package byCodeGame.game.entity.file;

public class LevelLimit {

	/** 等级 */
	private int lv;
	/** 可拥有的农场数量	 */
	private int farmNum;
	/** 可拥有的民居数量	 */
	private int houseNum;
	/** 可拥有的兵营数量 */
	private int barrackNum;
	/** 可开启的背包格子数量	 */
	private int backNum;
	
	
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public int getFarmNum() {
		return farmNum;
	}
	public void setFarmNum(int farmNum) {
		this.farmNum = farmNum;
	}
	public int getHouseNum() {
		return houseNum;
	}
	public void setHouseNum(int houseNum) {
		this.houseNum = houseNum;
	}
	public int getBarrackNum() {
		return barrackNum;
	}
	public void setBarrackNum(int barrackNum) {
		this.barrackNum = barrackNum;
	}
	public int getBackNum() {
		return backNum;
	}
	public void setBackNum(int backNum) {
		this.backNum = backNum;
	}
	
	
}
