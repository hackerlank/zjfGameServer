package byCodeGame.game.entity.file;
/**
 * 签到奖励
 * @author xjd
 */
public class AwardSign {
	/** 箱子的编号		*/
	private int number;
	/** 需要的签到次数	*/
	private int needTimes;
	/** 奖励			*/
	private String award;
	
	
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNeedTimes() {
		return needTimes;
	}
	public void setNeedTimes(int needTimes) {
		this.needTimes = needTimes;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
}	
