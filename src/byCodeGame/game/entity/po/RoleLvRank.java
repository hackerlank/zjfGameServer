package byCodeGame.game.entity.po;

/**
 * 玩家等级排名
 * 
 * @author wcy 2016年2月24日
 *
 */
public class RoleLvRank extends Rank {
	private String name;
	private int roleLv;
	private int enterTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoleLv() {
		return roleLv;
	}

	public void setRoleLv(int roleLv) {
		this.roleLv = roleLv;
	}

	public int getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(int enterTime) {
		this.enterTime = enterTime;
	}

}
