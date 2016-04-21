package byCodeGame.game.entity.po;

/**
 * 
 * @author wcy 2016年3月4日
 *
 */
public class WorldTempMarketItem extends WorldMarketItems {
	public WorldTempMarketItem() {
	}

	public WorldTempMarketItem(WorldTempMarketItem w) {
		this.cityId = w.cityId;
		this.setInventory(w.getInventory());
		this.setId(w.getId());
	}

	/** 城市id */
	private int cityId;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

}
