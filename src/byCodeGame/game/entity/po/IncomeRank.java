package byCodeGame.game.entity.po;

/**
 * 资源排名
 * 
 * @author wcy 2016年2月24日
 *
 */
public class IncomeRank extends Rank {
	private String name;
	private int enterTime;
	private int incomeNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(int enterTime) {
		this.enterTime = enterTime;
	}

	public int getIncomeNum() {
		return incomeNum;
	}

	public void setIncomeNum(int incomeNum) {
		this.incomeNum = incomeNum;
	}

}
