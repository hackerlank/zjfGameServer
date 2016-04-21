package byCodeGame.game.entity.file;

public class BuildConfig {

	/** 农场等级 */
	private int lv;
	/** 1为民居 2为农田 3兵营 */
	private int type;
	/** 升级所需金钱 */
	private int upMoney;
	/** 升级所需粮食 */
	private int upFood;
	/** 升级所需时间 单位 秒 */
	private int upTime;
	/** 每小时收益 */
	private int inCome;
	/** 容量(只有为兵营时才会使用) */
	private int capacity;
	/** 卖出价格 (只有为兵营时才会使用) */
	private int sellMoney;
	/** 当前等级更改兵营所需金钱 (只有为兵营时才会使用) */
	private int changeMoney;
	/** 冷却时间 */
	private int cd;

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getUpMoney() {
		return upMoney;
	}

	public void setUpMoney(int upMoney) {
		this.upMoney = upMoney;
	}

	public int getUpTime() {
		return upTime;
	}

	public void setUpTime(int upTime) {
		this.upTime = upTime;
	}

	public int getInCome() {
		return inCome;
	}

	public void setInCome(int inCome) {
		this.inCome = inCome;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	// public int getCapacity() {
	// return capacity;
	// }
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getSellMoney() {
		return sellMoney;
	}

	public void setSellMoney(int sellMoney) {
		this.sellMoney = sellMoney;
	}

	public int getChangeMoney() {
		return changeMoney;
	}

	public void setChangeMoney(int changeMoney) {
		this.changeMoney = changeMoney;
	}

	public int getUpFood() {
		return upFood;
	}

	public void setUpFood(int upFood) {
		this.upFood = upFood;
	}

	public int getCd() {
		return cd;
	}

	public void setCd(int cd) {
		this.cd = cd;
	}
}
