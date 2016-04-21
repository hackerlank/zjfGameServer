package byCodeGame.game.entity.file;

/**
 * 
 * @author wcy
 *
 */
public class MainBuildingConfig {
	private int lv;
	private int type;
	private int upMoney;
	private int upTime;
	private int inCome;
	private int capacity;
	/** 征收的时间 */
	private int cd;

	private String other;

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * 获得征收时间
	 * @return
	 * @author wcy
	 */
	public int getCd() {
		return cd;
	}

	public void setCd(int cd) {
		this.cd = cd;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
}
