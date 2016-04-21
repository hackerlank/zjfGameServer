package byCodeGame.game.entity.po;


/**
 * 城主每日抽成记录数量
 * @author xjd
 *
 */
public class CityOwGet {
	private int coinIncome;
	private int foodIncome;
	private int riceIncome;
	private int mineralIncome;
	private int goldIncome;
	
	
	public void addCoin(int value)
	{
		this.coinIncome += value;
	}
	public void addFood(int value)
	{
		this.foodIncome += value;
	}
	public void addRice(int value)
	{
		this.riceIncome += value;
	}
	public void addMineralIncome(int value)
	{
		this.mineralIncome += value;
	}
	
	public void addGold(int value)
	{
		this.goldIncome += value;
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
	
	public int getNum()
	{
		int num = 0;
		if(this.coinIncome > 0)
		{
			num = this.coinIncome;
		}else if (this.foodIncome > 0) {
			num = this.foodIncome;
		}else if (this.riceIncome > 0){
			num = this.riceIncome;
		}else if (this.mineralIncome > 0) {
			num = this.mineralIncome;
		}else if (this.goldIncome > 0) {
			num = this.goldIncome;
		}
		
		return num;
	}
}
