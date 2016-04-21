package byCodeGame.game.entity.file;

/**
 * 声望商城解锁
 * @author wcy 2016年3月7日
 *
 */
public class ReputationShopUnlock {
	/**格子数量*/
	private int openNum;
	/**vip等级*/
	private int vipLevel;
	/**花费*/
	private int cost;
	public int getOpenNum() {
		return openNum;
	}
	public void setOpenNum(int openNum) {
		this.openNum = openNum;
	}
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	
}
