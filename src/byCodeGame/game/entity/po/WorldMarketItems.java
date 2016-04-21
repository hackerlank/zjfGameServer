package byCodeGame.game.entity.po;

/**
 * 世界声望商城商品类
 * 
 * @author wcy 2015年12月28日
 *
 */
public class WorldMarketItems {
	/**唯一主键*/
	private int id;
	/** 库存 */
	private int inventory;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
}
