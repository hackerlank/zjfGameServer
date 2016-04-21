package byCodeGame.game.entity.po;
/**
 * 
 * @author wcy
 *
 */
public class VisitSaleData {

	private int startTime;
	private int cdTime;
	private int heroId;
	private int price;
	private int presitage;

	public VisitSaleData() {

	}

	public VisitSaleData(LevyInfo levyInfo) {
		this.startTime = (int) levyInfo.getStartTime();
		this.cdTime = levyInfo.getCd();
		this.price = levyInfo.getValueOther();
		this.setPresitage(levyInfo.getValue());
		this.heroId = levyInfo.getHeroId();
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the heroId
	 */
	public int getHeroId() {
		return heroId;
	}

	/**
	 * @param heroId the heroId to set
	 */
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	/**
	 * @return the cdTime
	 */
	public int getCdTime() {
		return cdTime;
	}

	/**
	 * @param cdTime the cdTime to set
	 */
	public void setCdTime(int cdTime) {
		this.cdTime = cdTime;
	}

	/**
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the presitage
	 */
	public int getPresitage() {
		return presitage;
	}

	/**
	 * @param presitage the presitage to set
	 */
	public void setPresitage(int presitage) {
		this.presitage = presitage;
	}

	
}
