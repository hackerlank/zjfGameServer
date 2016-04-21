package byCodeGame.game.entity.file;

public class GoldMarketConfig {
	private int id;
	/** 配置表ID	*/
	private int itemId;
	/** 类型		*/
	private int type;
	/** 售价		*/
	private int cost;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
