package byCodeGame.game.entity.po;

/**
 * RoleCity下  子类  3级子类
 * 属臣信息
 * @author sht
 */
public class VassalData {
	public VassalData(){}
	public VassalData(int roleId, int lastTime, int moeny, int food,
			int population) {
		super();
		this.roleId = roleId;
		this.lastTime = lastTime;
		this.money = moeny;
		this.food = food;
		this.population = population;
		this.canNotDrop = true;
	}
	/**
	 *属臣玩家Id 
	 */
	private int roleId;
	/**最后征收时间*/
	private int lastTime;
	/**征收银币总和 */
	private int money;
	/**征收粮草总和*/
	private int food;
	/**增收人口总和	*/
	private int population;
	/**是否不能放逐*/
	private boolean canNotDrop;
	
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getLastTime() {
		return lastTime;
	}
	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getFood() {
		return food;
	}
	public void setFood(int food) {
		this.food = food;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	/**
	 * @return the canNotDrop
	 */
	public boolean isCanNotDrop() {
		return canNotDrop;
	}
	/**
	 * @param canNotDrop the canNotDrop to set
	 */
	public void setCanNotDrop(boolean canNotDrop) {
		this.canNotDrop = canNotDrop;
	}
}
