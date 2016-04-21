package byCodeGame.game.entity.po;

/**
 * 会谈结果类
 * 
 * @author wcy
 *
 */
public class TalkResult {

	/** 结果的id */
	private int id;
	/** 类型（0无 1 银币 2金币 3战功 4粮食 5军令 6武将 ） */
	private byte type;
	/** 数量（上方类型若为0则为0） */
	private int number;
	/**
	 * 是否第一次获得（1是第一次获得，0不是第一次获得）
	 */
	private int firstGet;
	/**
	 * 结果名称
	 */
	private String name;

	/**
	 * 获得抽到的奖品类型（0无 1 银币 2金币 3战功 4粮食 5军令 6武将 ）
	 * 
	 * @return
	 * @author wcy
	 */
	public byte getType() {
		return type;
	}

	/**
	 * 获得数量
	 * 
	 * @return
	 * @author wcy
	 */
	public int getNumber() {
		return number;
	}

	public void setType(int type) {
		this.type = (byte) type;
	}

	public void setNumber(int number) {
		this.number = number;
	}

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

	public int getFirstGet() {
		return firstGet;
	}

	public void setFirstGet(int firstGet) {
		this.firstGet = firstGet;
	}
}