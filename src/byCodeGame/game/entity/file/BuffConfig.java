package byCodeGame.game.entity.file;

public class BuffConfig {

	private int id;
	private String className;
	/** 持续时间(毫秒)	 */
	private int continueTime;
	
	private int value1;
	private int value2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getContinueTime() {
		return continueTime;
	}

	public void setContinueTime(int continueTime) {
		this.continueTime = continueTime;
	}

	public double getValue1() {
		double a = value1/(double)100;
		return a;
	}

	public void setValue1(int value1) {
		this.value1 = value1;
	}

	public double getValue2() {
		double a = value2/(double)100;
		return a;
	}

	public void setValue2(int value2) {
		this.value2 = value2;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
