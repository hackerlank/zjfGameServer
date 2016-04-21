package byCodeGame.game.entity.file;

/**
 * 
 * @author wcy 2015年12月28日
 *
 */
public class PrestigeShop {
	/** 唯一主键 */
	private int rowId;
	/** 等级段数据 */
	private int lvID;
	/** 当超过等级段时，是否把该商品从商品列表中移除，0表示常驻，1表示移除 */
	private int ifRemove;
	/** 售卖类型 0：不限定数量 1：玩家个人限定数量 2：全服限定数量 */
	private int type;
	/** 道具ID */
	private int itemID;
	/** 售卖数量 */
	private int num;
	/** 库存 */
	private int inventory;
	/** 价格 */
	private int price;
	/** 商品类型 */
	private int functionType;
	/** 商品细分类型 */
	private int itemType;
	/** 备注 */
	private String desc;

	public int getLvID() {
		return lvID;
	}

	public void setLvID(int lvID) {
		this.lvID = lvID;
	}

	public int getIfRemove() {
		return ifRemove;
	}

	public void setIfRemove(int ifRemove) {
		this.ifRemove = ifRemove;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the functionType
	 */
	public int getFunctionType() {
		return functionType;
	}

	/**
	 * @param functionType the functionType to set
	 */
	public void setFunctionType(int functionType) {
		this.functionType = functionType;
	}

	/**
	 * @return the rowId
	 */
	public int getRowId() {
		return rowId;
	}

	/**
	 * @param rowId the rowId to set
	 */
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	/**
	 * @return the itemType
	 */
	public int getItemType() {
		return itemType;
	}

	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

}
