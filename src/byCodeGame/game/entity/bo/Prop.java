package byCodeGame.game.entity.bo;

public class Prop {

	private int id;
	/** 所属玩家id */
	private int roleId;
	/** 装备所在武将ID 2500为主角 其他为武将ID(配置表ID) */
	private int useID;
	/**
	 * 道具类型 1装备 2消耗品
	 */
	private byte functionType;
	/** 配置表ID */
	private int itemId;
	/** 道具数量 */
	private short num;
	/** 道具等级 */
	private short lv;
	/** 装备位置 0未装备 1头部 2身体 3腰带 4鞋子 5武器 6饰品 */
	private byte slotId;
	/** 词缀ID */
	private int prefixId;
	/** 战力值 */
	private int fightValue;

	private boolean change;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public byte getFunctionType() {
		return functionType;
	}

	public void setFunctionType(byte functionType) {
		this.functionType = functionType;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public short getNum() {
		return num;
	}

	public void setNum(short num) {
		this.num = num;
	}

	public short getLv() {
		return lv;
	}

	public void setLv(short lv) {
		this.lv = lv;
	}

	public byte getSlotId() {
		return slotId;
	}

	public void setSlotId(byte slotId) {
		this.slotId = slotId;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public int getUseID() {
		return useID;
	}

	public void setUseID(int useID) {
		this.useID = useID;
	}

	public int getPrefixId() {
		return prefixId;
	}

	public void setPrefixId(int prefixId) {
		this.prefixId = prefixId;
	}

	/**
	 * @return the fightValue
	 */
	public int getFightValue() {
		return fightValue;
	}

	/**
	 * @param fightValue the fightValue to set
	 */
	public void setFightValue(int fightValue) {
		this.fightValue = fightValue;
	}

}
