package byCodeGame.game.entity.bo;

import byCodeGame.game.entity.bo.base.RoleComponent;

/**
 * 家
 * 
 * @author wcy 2016年4月28日
 *
 */
public class Home extends RoleComponent {
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
	/** 大厅 */
	private Hall hall;

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

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Hall getHall() {
		return hall;
	}
}
