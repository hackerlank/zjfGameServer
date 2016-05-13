package byCodeGame.game.entity.file;

public class AgeConfig {
	/** 唯一编号 */
	private int ageId;
	/** 成长阶段名称 */
	private String name;
	/** 成长点数 */
	private int value;
	public int getAgeId() {
		return ageId;
	}
	public void setAgeId(int ageId) {
		this.ageId = ageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
