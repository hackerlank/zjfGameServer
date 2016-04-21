package byCodeGame.game.entity.file;

public class StarStrategy {
	private int id;
	/** 策略类型		*/
	private int strategyID;
	/** 1星条件		*/
	private int star1;
	/** 2星条件		*/
	private int star2;
	/** 3星条件		*/
	private int star3;
	/** 说明			*/
	private String desc;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStrategyID() {
		return strategyID;
	}
	public void setStrategyID(int strategyID) {
		this.strategyID = strategyID;
	}
	public int getStar1() {
		return star1;
	}
	public void setStar1(int star1) {
		this.star1 = star1;
	}
	public int getStar2() {
		return star2;
	}
	public void setStar2(int star2) {
		this.star2 = star2;
	}
	public int getStar3() {
		return star3;
	}
	public void setStar3(int star3) {
		this.star3 = star3;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
