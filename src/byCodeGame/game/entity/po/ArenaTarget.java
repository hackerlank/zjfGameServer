package byCodeGame.game.entity.po;

/**
 * 竞技场对手
 * @author wjh
 *
 */
public class ArenaTarget {

	/** 目标roleId	 */
	private int roleId;
	/** 是否为机器人	 */
	private boolean isRobot;
	/** 英雄id	 */
	private int heroId;
	/** 天梯排名	 */
	private int ladderRank;
	/** 所属阶段	 */
	private byte phaseLv;
	/** 玩家等级 */
	private int lv;
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}

	
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public boolean isRobot() {
		return isRobot;
	}
	public void setRobot(boolean isRobot) {
		this.isRobot = isRobot;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public int getLadderRank() {
		return ladderRank;
	}
	public void setLadderRank(int ladderRank) {
		this.ladderRank = ladderRank;
	}
	public byte getPhaseLv() {
		return phaseLv;
	}
	public void setPhaseLv(byte phaseLv) {
		this.phaseLv = phaseLv;
	}
}
