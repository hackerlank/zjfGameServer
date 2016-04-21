package byCodeGame.game.entity.file;

/**
 * 声望商城奖励的英雄
 * 
 * @author wcy 2016年3月5日
 *
 */
public class PrestigeHero {
	/**武将id*/
	private int ID;
	/**所需要的声望值*/
	private int prestige;
	/**库存*/
	private int limit;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getPrestige() {
		return prestige;
	}

	public void setPrestige(int prestige) {
		this.prestige = prestige;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
