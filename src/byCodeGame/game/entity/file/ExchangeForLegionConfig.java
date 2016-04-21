package byCodeGame.game.entity.file;

public class ExchangeForLegionConfig {
	/** 道具配置表ID（武将ID）   	*/
	private int itemId;
	/** 花费贡献		  	   	*/
	private int cost;
	/** 道具类型 1装备 2道具 3武将	*/
	private int type;
	/** 道具的数量			    */
	private int	number;
	/** 排序					*/
	private int id;
	
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
	public int getNumber() {
		return number;
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
	
	
	
}
