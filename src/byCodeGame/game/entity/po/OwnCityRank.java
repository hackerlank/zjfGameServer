package byCodeGame.game.entity.po;

/**
 * 
 * @author wcy 2016年2月25日
 *
 */
public class OwnCityRank extends Rank {
	private String name;
	private int enterTime;
	private int ownCityCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(int enterTime) {
		this.enterTime = enterTime;
	}

	public int getOwnCityCount() {
		return ownCityCount;
	}

	public void setOwnCityCount(int ownCityCount) {
		this.ownCityCount = ownCityCount;
	}

}
