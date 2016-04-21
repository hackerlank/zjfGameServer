package byCodeGame.game.entity.bo;

public class LadderArena {

	/** 当前排名 */
	private int nowRank;
	/** 排名	 */
	private int rank;
	/** 用户ID	 */
	private int roleId;
	/** 品阶 */
	private int lv;
	/** 昵称 */
	private String roleName;
	/** 等级  */
	private int roleLv;
	/** 英雄Id */
	private int heroId;
	
	private boolean change;
	
	public int getNowRank() {
		return nowRank;
	}
	public void setNowRank(int nowrank) {
		this.nowRank = nowrank;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getRoleLv() {
		return roleLv;
	}
	public void setRoleLv(int roleLv) {
		this.roleLv = roleLv;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public boolean isChange() {
		return change;
	}
	public void setChange(boolean change) {
		this.change = change;
	}
	
}
