package byCodeGame.game.entity.file;

public class TroopCounterConfig {
	private int id;
	/** 步			*/
	private int footman;
	/** 骑			*/
	private int lancer;
	/** 弓			*/
	private int knight;
	/** 法			*/
	private int archer;
	/** 枪			*/
	private int mage;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFootman() {
		return footman;
	}
	public void setFootman(int footman) {
		this.footman = footman;
	}
	public int getLancer() {
		return lancer;
	}
	public void setLancer(int lancer) {
		this.lancer = lancer;
	}
	public int getKnight() {
		return knight;
	}
	public void setKnight(int knight) {
		this.knight = knight;
	}
	public int getArcher() {
		return archer;
	}
	public void setArcher(int archer) {
		this.archer = archer;
	}
	public int getMage() {
		return mage;
	}
	public void setMage(int mage) {
		this.mage = mage;
	}

	/***
	 * 根据自身类型和敌方类型取出克制值
	 * @param troopType
	 * @return
	 */
	public double getCount(int troopType)
	{
		double b = 0.0;
		switch (troopType) {
		case 1:
			b = (double)footman/100;
			break;
		case 2:
			b = (double)knight/100;
			break;
		case 3:
			b = (double)archer/100;
			break;
		case 4:
			b = (double)mage/100;
			break;
		case 5:
			b = (double)lancer/100;
			break;
		default:
			break;
		}
		
		return b;
	}
}
