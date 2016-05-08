package byCodeGame.game.entity.file;

public class PropConfig {
	/**道具id*/
	private int id;
	/**道具类型*/
	private int type;
	/**道具名*/
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

}
