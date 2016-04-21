package byCodeGame.game.entity.file;

/**
 * 
 * @author wcy
 *
 */
public class WorldWarPath {
	/** 城市起点 */
	private int cityID;
	/** 出口城墙 */
	private int wallID;
	/** 目标城市id */
	private int linkCityID;
	/** 目标城墙id */
	private int linkWallID;
	/** 行军时间 */
	private int marchTime;
	/** 消耗粮草 */
	private int forageCost;

	public int getCityID() {
		return cityID;
	}

	public void setCityID(int cityID) {
		this.cityID = cityID;
	}

	public int getWallID() {
		return wallID;
	}

	public void setWallID(int wallID) {
		this.wallID = wallID;
	}

	public int getLinkCityID() {
		return linkCityID;
	}

	public void setLinkCityID(int linkCityID) {
		this.linkCityID = linkCityID;
	}

	public int getLinkWallID() {
		return linkWallID;
	}

	public void setLinkWallID(int linkWallID) {
		this.linkWallID = linkWallID;
	}

	public int getMarchTime() {
		return marchTime;
	}

	public void setMarchTime(int marchTime) {
		this.marchTime = marchTime;
	}

	public int getForageCost() {
		return forageCost;
	}

	public void setForageCost(int forageCost) {
		this.forageCost = forageCost;
	}

}
