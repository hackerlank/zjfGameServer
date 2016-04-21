package byCodeGame.game.entity.po;

/**
 *  礼包道具
 * @author 王君辉 
 *
 */
public class Package {
	/** 道具ID	 */
	private int id;
	/** 功能类型  1装备 2消耗品	 */
	private byte functionType;
	/** 数量	 */
	private int value;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public byte getFunctionType() {
		return functionType;
	}
	public void setFunctionType(byte functionType) {
		this.functionType = functionType;
	}
}
