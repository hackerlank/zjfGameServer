package byCodeGame.game.entity.file;

public class DailyTask {
	/** 每日任务奖励编号*/
	private int number;
	/** 所需完成度		*/
	private int needs;
	/** 奖励			*/
	private String award;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNeeds() {
		return needs;
	}
	public void setNeeds(int needs) {
		this.needs = needs;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	
	
	
}
