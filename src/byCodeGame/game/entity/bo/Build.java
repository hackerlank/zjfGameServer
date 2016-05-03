package byCodeGame.game.entity.bo;

import byCodeGame.game.entity.bo.base.RoleComponent;

/**
 * 建筑信息
 * 
 * @author wcy 2016年4月28日
 *
 */
public class Build extends RoleComponent {
	/** 卧室 */
	private Bedroom bedroom;
	/** 厨房 */
	private Kitchen kitchen;
	/** 农场 */
	private Farm farm;
	/** 酒馆 */
	private Pub pub;
	/** 回收站 */
	private Bin bin;

	public void setBedroom(Bedroom bedroom) {
		this.bedroom = bedroom;
	}

	public Bedroom getBedroom() {
		return bedroom;
	}

	public void setKitchen(Kitchen kitchen) {
		this.kitchen = kitchen;
	}

	public Kitchen getKitchen() {
		return kitchen;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setPub(Pub pub) {
		this.pub = pub;
	}

	public Pub getPub() {
		return pub;
	}

	public void setBin(Bin bin) {
		this.bin = bin;
	}

	public Bin getBin() {
		return bin;
	}

}
