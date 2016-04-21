package byCodeGame.game.entity.file;

public class ExpandHero {
	/** 玩家vip等级			*/
	private int vipLv;
	/** 扩充队列的数字			*/
	private int expandNumber;
	/** 花费的元宝				*/
	private int cost;
	/** vip等级下最高队列数字	*/
	private int maxNumber;
	
	
	public int getVipLv() {
		return vipLv;
	}
	public void setVipLv(int vipLv) {
		this.vipLv = vipLv;
	}
	public int getExpandNumber() {
		return expandNumber;
	}
	public void setExpandNumber(int expandNumber) {
		this.expandNumber = expandNumber;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}
	
	
}
