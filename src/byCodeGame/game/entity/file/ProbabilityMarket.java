package byCodeGame.game.entity.file;

/**
 * 控制市场出现物品概率的配置表
 * @author xjd
 *
 */
public class ProbabilityMarket {
	/** 随机数字		*/
	private int figure;
	
	/** 装备配置表ID	*/
	private int itemId;

	public int getFigure() {
		return figure;
	}

	public void setFigure(int figure) {
		this.figure = figure;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	
	
}
