package byCodeGame.game.entity.po;

/**
 * 
 * @author wcy
 *
 */
public class VisitTreasureData {
	private int startTime;// 开始时间
	private int itemId;// 道具id
	private int num;// 获得数量
	private int heroId;// 征收武将

	public VisitTreasureData() {

	}

	public VisitTreasureData(LevyInfo levyInfo) {
		this.startTime = (int) levyInfo.getStartTime();
		this.itemId = levyInfo.getValue();
		this.num = levyInfo.getValueOther();
		this.heroId = levyInfo.getHeroId();
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

}
