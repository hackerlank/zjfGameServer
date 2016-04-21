package byCodeGame.game.entity.file;

public class TowerConfig {
	/** 剧本ID	*/
	private int userId;
	/** 需求等级	*/
	private int lv;
	/** 显示奖励	*/
	private String award;
	
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
}
