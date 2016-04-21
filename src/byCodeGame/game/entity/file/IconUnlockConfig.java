package byCodeGame.game.entity.file;

/**
 * 图标解锁
 * @author shao
 *
 */
public class IconUnlockConfig {
	private int id;
	private String name;
	private String unlockType;
	private int value;
	private int type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnlockType() {
		return unlockType;
	}
	public void setUnlockType(String unlockType) {
		this.unlockType = unlockType;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
}
