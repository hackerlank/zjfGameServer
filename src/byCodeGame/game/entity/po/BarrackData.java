package byCodeGame.game.entity.po;

/**
 * 兵营建筑数据类  用于保存建筑的类型与等级
 * @author wjh 
 *
 */
public class BarrackData {

	private byte id;
	/** 建筑类型 1步兵 2骑兵 3弓兵 4策士	 */
	private byte type;
	/** 等级	 */
	private short lv;
	
	
	public byte getId() {
		return id;
	}
	public void setId(byte id) {
		this.id = id;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public short getLv() {
		return lv;
	}
	public void setLv(short lv) {
		this.lv = lv;
	}
	
}
