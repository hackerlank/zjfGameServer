package byCodeGame.game.entity.po;

public class VisitData {
	/** 寻访时间&&ID		*/
	private int id;
	/** 城市ID			*/
	private int cityId;
	/** 声望				*/
	private int prestige;
	/** 武将ID			*/
	private int heroId;
	
	public VisitData() {
		// TODO Auto-generated constructor stub
	}
	
	public VisitData(LevyInfo info) {
		this.id = (int)info.getStartTime();
		this.cityId = info.getValue();
		this.prestige = info.getValueOther();
		this.heroId = info.getHeroId();
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getPrestige() {
		return prestige;
	}
	public void setPrestige(int prestige) {
		this.prestige = prestige;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
}
