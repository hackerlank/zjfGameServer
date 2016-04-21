package byCodeGame.game.entity.file;

/**
 * 抽奖概率类
 * 
 * @author wcy
 *
 */
public class TavernStrategyConfig {
	/* 抽取的类型（1银币单次，2银币10次，3金币单次，4金币10次 */
	private int drawType;
	/** 花费 */
	private int cost;
	/** 等待时间 */
	private int cd;
	/** 每天免费抽奖次数 */
	private int daytime;
	/** 抽到道具的概率 */
	private int propProb;
	/** 道具概率的配置编号 */
	private int propSet;
	/** 抽到英雄的概率 */
	private int heroProb;
	/** 英雄概率的配置编号 */
	private int heroSet;
	/** 节点抽的集编号 */
	private int nodeSet;
	/** 节点数 */
	private int node;
	/** 保底抽的集编号 */
	private int remedySet;
	/** 第一次抽 */
	private int firstDrawSet;
	/** 将魂概率 */
	private int debrisProb;
	/** 将魂集 */
	private int debrisSet;
	/**云游将概率*/
	private int visitProb;
	/**云游将的集*/
	private int visitSet;

	public int getDrawType() {
		return drawType;
	}

	public void setDrawType(int drawType) {
		this.drawType = drawType;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCd() {
		return cd;
	}

	public void setCd(int cd) {
		this.cd = cd;
	}	

	public int getPropSet() {
		return propSet;
	}

	public void setPropSet(int propSet) {
		this.propSet = propSet;
	}

	public int getHeroSet() {
		return heroSet;
	}

	public void setHeroSet(int heroSet) {
		this.heroSet = heroSet;
	}

	public int getNodeSet() {
		return nodeSet;
	}

	public void setNodeSet(int nodeSet) {
		this.nodeSet = nodeSet;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	public int getRemedySet() {
		return remedySet;
	}

	public void setRemedySet(int remedySet) {
		this.remedySet = remedySet;
	}

	public int getDaytime() {
		return daytime;
	}

	public void setDaytime(int daytime) {
		this.daytime = daytime;
	}

	/**
	 * @return the propProb
	 */
	public int getPropProb() {
		return propProb;
	}

	/**
	 * @param propProb the propProb to set
	 */
	public void setPropProb(int propProb) {
		this.propProb = propProb;
	}

	/**
	 * @return the heroProb
	 */
	public int getHeroProb() {
		return heroProb;
	}

	/**
	 * @param heroProb the heroProb to set
	 */
	public void setHeroProb(int heroProb) {
		this.heroProb = heroProb;
	}

	/**
	 * @return the firstDrawSet
	 */
	public int getFirstDrawSet() {
		return firstDrawSet;
	}

	/**
	 * @param firstDrawSet the firstDrawSet to set
	 */
	public void setFirstDrawSet(int firstDrawSet) {
		this.firstDrawSet = firstDrawSet;
	}

	/**
	 * @return the debrisProb
	 */
	public int getDebrisProb() {
		return debrisProb;
	}

	/**
	 * @param debrisProb the debrisProb to set
	 */
	public void setDebrisProb(int debrisProb) {
		this.debrisProb = debrisProb;
	}

	/**
	 * @return the debrisSet
	 */
	public int getDebrisSet() {
		return debrisSet;
	}

	/**
	 * @param debrisSet the debrisSet to set
	 */
	public void setDebrisSet(int debrisSet) {
		this.debrisSet = debrisSet;
	}

	/**
	 * @return the visitProb
	 */
	public int getVisitProb() {
		return visitProb;
	}

	/**
	 * @param visitProb the visitProb to set
	 */
	public void setVisitProb(int visitProb) {
		this.visitProb = visitProb;
	}

	/**
	 * @return the visitSet
	 */
	public int getVisitSet() {
		return visitSet;
	}

	/**
	 * @param visitSet the visitSet to set
	 */
	public void setVisitSet(int visitSet) {
		this.visitSet = visitSet;
	}

}
