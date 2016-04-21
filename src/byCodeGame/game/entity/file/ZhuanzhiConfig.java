package byCodeGame.game.entity.file;

public class ZhuanzhiConfig {
	/** 职业ID			*/
	private int professionID;
	/** 等级				*/
	private int rank;
	/** 花费(印绶)		*/
	private int cost;
	/** 需求等级			*/
	private int needLv;
	public int getProfessionID() {
		return professionID;
	}
	public void setProfessionID(int professionID) {
		this.professionID = professionID;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getNeedLv() {
		return needLv;
	}
	public void setNeedLv(int needLv) {
		this.needLv = needLv;
	}
}
