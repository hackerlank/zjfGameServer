package byCodeGame.game.entity.file;

public class CityWallConfig {
	/** 城墙ID	*/
	private int id;
	/** 城市编号	*/
	private int cityID;
	/** 城墙编号	*/
	private int wallID;
	/** 方位		*/
	private int dir;
	/** 名字		*/
	private String wallName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}
	public String getWallName() {
		return wallName;
	}
	public void setWallName(String wallName) {
		this.wallName = wallName;
	}
}
