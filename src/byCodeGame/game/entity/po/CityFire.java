package byCodeGame.game.entity.po;

/**
 * 城市冒火信息
 * 
 * @author wcy 2016年1月12日
 *
 */
public class CityFire {
	private int startTime;
	private byte nation;

	/**
	 * @return the nowTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the nowTime to set
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the nation
	 */
	public byte getNation() {
		return nation;
	}

	/**
	 * @param nation the nation to set
	 */
	public void setNation(byte nation) {
		this.nation = nation;
	}

}
