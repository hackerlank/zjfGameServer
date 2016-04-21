package byCodeGame.game.entity.file;

public class CityInfoConfig {
	/** 城市ID */
	private int cityId;
	/** 城市名 */
	private String cityName;
	/** 城市类型 */
	private int cityType;
	/** 城市所在国籍1蜀2魏3吴 */
	private int nation;
	/** 城墙数量 */
	private int wallNum;
	/** 战斗id */
	private int battleID;
	/** 初始化时候的城主 */
	private int cityOw;
	/** 加入等级限制 */
	private int lvNum;
	/** 加入最大等级限制 超出会使粮食矿资源收益减半 */
	private int lvMaxNeed;
	/** 奖励 */
	private String award;
	/** 武将 */
	private int garrisonHero;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getNation() {
		return nation;
	}

	public void setNation(int nation) {
		this.nation = nation;
	}

	public int getLvNum() {
		return lvNum;
	}

	public void setLvNum(int lvNum) {
		this.lvNum = lvNum;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getLvMaxNeed() {
		return lvMaxNeed;
	}

	public void setLvMaxNeed(int lvMaxNeed) {
		this.lvMaxNeed = lvMaxNeed;
	}

	/**
	 * @return the cityType
	 */
	public int getCityType() {
		return cityType;
	}

	/**
	 * @param cityType the cityType to set
	 */
	public void setCityType(int cityType) {
		this.cityType = cityType;
	}

	/**
	 * @return the wallNum
	 */
	public int getWallNum() {
		return wallNum;
	}

	/**
	 * @param wallNum the wallNum to set
	 */
	public void setWallNum(int wallNum) {
		this.wallNum = wallNum;
	}

	/**
	 * @return the battleID
	 */
	public int getBattleID() {
		return battleID;
	}

	/**
	 * @param battleID the battleID to set
	 */
	public void setBattleID(int battleID) {
		this.battleID = battleID;
	}

	public int getCityOw() {
		return cityOw;
	}

	public void setCityOw(int cityOw) {
		this.cityOw = cityOw;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public int getGarrisonHero() {
		return garrisonHero;
	}

	public void setGarrisonHero(int garrisonHero) {
		this.garrisonHero = garrisonHero;
	}
}
