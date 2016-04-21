package byCodeGame.game.entity.file;

public class AnimalConfig {

	/** 次数	 */
	private int times;
	/** 需求银币	此处对应的是当前次数的喂养需求银币数量 */
	private int needMoney;
	/** 加值	 */
	private int value;
	
	
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public int getNeedMoney() {
		return needMoney;
	}
	public void setNeedMoney(int needMoney) {
		this.needMoney = needMoney;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
