package byCodeGame.game.entity.file;

public class MineFarmConfig {
	/** 索引ID*/
	private int buildingID;
	/**城市ID */
	private int cityId;
	/** 银币收入	*/
	private int coinIncome;
	/** 粮草收入	*/
	private int foodIncome;
	/** 大米收入	*/
	private int riceIncome;
	/** 铁矿收入	*/
	private int mineralIncome;
	/** 金币收入	*/
	private int goldIncome;
	/** 英雄碎片	*/
	private int heroDebris;
	/** 碎片掉落概率*/
	private int probiblity;
	/** 碎片补偿金币*/
	private int remedyCoins;
	/** 需要贡献	 */
	private int contribute;
	/** 名字		 */
	private String name;
	
	
	public int getBuildingID() {
		return buildingID;
	}
	public void setBuildingID(int buildingID) {
		this.buildingID = buildingID;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getCoinIncome() {
		return coinIncome;
	}
	public void setCoinIncome(int coinIncome) {
		this.coinIncome = coinIncome;
	}
	public int getFoodIncome() {
		return foodIncome;
	}
	public void setFoodIncome(int foodIncome) {
		this.foodIncome = foodIncome;
	}
	public int getRiceIncome() {
		return riceIncome;
	}
	public void setRiceIncome(int riceIncome) {
		this.riceIncome = riceIncome;
	}
	public int getMineralIncome() {
		return mineralIncome;
	}
	public void setMineralIncome(int mineralIncome) {
		this.mineralIncome = mineralIncome;
	}
	public int getGoldIncome() {
		return goldIncome;
	}
	public void setGoldIncome(int goldIncome) {
		this.goldIncome = goldIncome;
	}
	public int getHeroDebris() {
		return heroDebris;
	}
	public void setHeroDebris(int heroDebris) {
		this.heroDebris = heroDebris;
	}
	public int getProbiblity() {
		return probiblity;
	}
	public void setProbiblity(int probiblity) {
		this.probiblity = probiblity;
	}
	public int getContribute() {
		return contribute;
	}
	public void setContribute(int contribute) {
		this.contribute = contribute;
	}
	public int getRemedyCoins() {
		return remedyCoins;
	}
	public void setRemedyCoins(int remedyCoins) {
		this.remedyCoins = remedyCoins;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
