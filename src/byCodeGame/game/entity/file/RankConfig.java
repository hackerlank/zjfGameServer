package byCodeGame.game.entity.file;

public class RankConfig {
	/** 官阶编号		*/
	private int id;
	/** 名字			*/
	private String name;
	/** 需要声望		*/
	private int needP;
	/** 英雄ID		*/
	private int heroId;
	/** 最大属城		*/
	private int maxCity;
	/** 每日俸禄		*/
	private int pay;
	/** 最大资源矿		*/
	private int maxMine;
	/** 最大资源田		*/
	private int maxFarm;
	/** 伤害增益		*/
	private int addHurt;
	/** 减伤			*/
	private int rmHurt;
	/** 最大部队数量	*/
	private int maxWorldArmy;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNeedP() {
		return needP;
	}
	public void setNeedP(int needP) {
		this.needP = needP;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public int getMaxCity() {
		return maxCity;
	}
	public void setMaxCity(int maxCity) {
		this.maxCity = maxCity;
	}
	public int getPay() {
		return pay;
	}
	public void setPay(int pay) {
		this.pay = pay;
	}
	public int getMaxMine() {
		return maxMine;
	}
	public void setMaxMine(int maxMine) {
		this.maxMine = maxMine;
	}
	public int getMaxFarm() {
		return maxFarm;
	}
	public void setMaxFarm(int maxFarm) {
		this.maxFarm = maxFarm;
	}
	public int getAddHurt() {
		return addHurt;
	}
	public void setAddHurt(int addHurt) {
		this.addHurt = addHurt;
	}
	public int getRmHurt() {
		return rmHurt;
	}
	public void setRmHurt(int rmHurt) {
		this.rmHurt = rmHurt;
	}
	public int getMaxWorldArmy() {
		return maxWorldArmy;
	}
	public void setMaxWorldArmy(int maxWorldArmy) {
		this.maxWorldArmy = maxWorldArmy;
	}
}
