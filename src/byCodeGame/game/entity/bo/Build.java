package byCodeGame.game.entity.bo;

/**
 * 建筑信息
 * 
 * @author wcy 2016年4月28日
 *
 */
public class Build {

	private int roleId;

	private Bedroom bedroom;

	private Kitchen kitchen;

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleId() {
		return roleId;
	}

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

}
