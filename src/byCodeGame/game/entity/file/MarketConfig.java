package byCodeGame.game.entity.file;

/**
 * 
 * @author wcy 2016年5月13日
 *
 */
public class MarketConfig {
	/** 唯一标识符 */
	private int id;
	/** 道具id */
	private int itemId;
	/** 商品价格 */
	private int price;

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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
