package byCodeGame.game.entity.po;

public class VisitScienceData {
	private int startTimeId;
	private int num;
	private int heroId;
	private String scienceName;
	
	public VisitScienceData(){
		
	}
	public VisitScienceData(LevyInfo levyInfo,int value,String scienceName) {
		this.startTimeId = (int) levyInfo.getStartTime();
		this.num = value;
		this.heroId = levyInfo.getHeroId();
		this.scienceName = scienceName;
	}
	public int getStartTimeId() {
		return startTimeId;
	}
	public void setStartTimeId(int startTimeId) {
		this.startTimeId = startTimeId;
	}
	/**
	 * 可获得战功
	 * @return
	 * @author wcy
	 */
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	
	public String getScienceName() {
		return scienceName;
	}
	/**
	 * 设置科技名
	 * @param scienceName
	 * @author wcy
	 */
	public void setScienceName(String scienceName) {
		this.scienceName = scienceName;
	}

	
}
