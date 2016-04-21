package byCodeGame.game.entity.po;

/**
 * 关卡奖励数据
 * @author 王君辉
 *
 */
public class ChapterReward {
	/** 奖励类型(1经验  2银币 3粮草  4战功  5道具  6装备)	 */
	private byte type;
	/** 道具ID	 */
	private int itemId;
	/** 数量	 */
	private int num;
	
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}
