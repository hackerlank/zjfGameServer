package byCodeGame.game.entity.file;

public class FreshLimit {
	//vip的等级
	private int vipLv;
	//总共可以刷新的次数
	private int totalNumber;
	//当前次数
	private int thisTime;
	//当前次数花费的金币数
	private int cost;
	
	/**-------------------------get&set--------------------------------**/
	
	public int getVipLv() {
		return vipLv;
	}
	public void setVipLv(int vipLv) {
		this.vipLv = vipLv;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getThisTime() {
		return thisTime;
	}
	public void setThisTime(int thisTime) {
		this.thisTime = thisTime;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
}
