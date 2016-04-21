package byCodeGame.game.entity.po;


public class HeroSoldier {
	/** 兵种id */
	private int soldierId;
	/** 兵种经验值 */
	private int exp;
	/** 兵种等级 */
	private int lv;
	/** 兵种技能等级 */
	private int skillLv;
	/** 是否解锁（未解锁为0，解锁为1，不可用2） */
	private byte status;

	public HeroSoldier() {
		super();
	}

	public HeroSoldier(int soldierId, int exp, int skillScale, byte status,int lv) {
		super();
		this.soldierId = soldierId;
		this.exp = exp;
		this.skillLv = skillScale;
		this.status = status;
		this.lv = lv;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * 获得技能等级
	 * 
	 * @return
	 * @author wcy
	 */
	public int getSkillLv() {
		return skillLv;
	}

	/**
	 * 设置技能等级
	 * 
	 * @param skillScale
	 * @author wcy
	 */
	public void setSkillLv(int skillScale) {
		this.skillLv = skillScale;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getSoldierId() {
		return soldierId;
	}

	public void setSoldierId(int soldierId) {
		this.soldierId = soldierId;
	}

	/**
	 * 获得兵种等级
	 * 
	 * @return
	 * @author wcy
	 */
	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

}
