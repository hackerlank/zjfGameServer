package byCodeGame.game.entity.po;

public class MarketItems {
	private int id;
	/** 配置表id */
	private int itemId;
	/** 是否已售出 */
	private int isSell;
	/** 售价 */
	private int salePrice;
	/** 是否议价 */
	private byte isBargin;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getIsSell() {
		return isSell;
	}

	public void setIsSell(int isSell) {
		this.isSell = isSell;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the 获得售价
	 */
	public int getSalePrice() {
		return salePrice;
	}

	/**
	 * @param 设置售价
	 */
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	/**
	 * @return the isBargin
	 */
	public boolean isBargin() {
		if (isBargin == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 * @author wcy
	 */
	public byte getIsBargin() {
		return isBargin;
	}

	/**
	 * @param isBargin the isBargin to set
	 */
	public void setIsBarginBoolean(boolean isBargin) {
		this.isBargin = (byte) (isBargin
			? 1
			: 0);
	}

	/**
	 * 
	 * @param isBargin
	 * @author wcy
	 */
	public void setIsBargin(byte isBargin) {
		this.isBargin = isBargin;
	}

}
