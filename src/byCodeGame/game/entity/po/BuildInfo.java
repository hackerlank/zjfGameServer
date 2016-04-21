package byCodeGame.game.entity.po;

public class BuildInfo {
	/** 建筑类型 */
	private byte type;
	/** 英雄ID */
	private int heroId;
	/** 上一次配属时间 */
	private int lastTime;
	/** 上一次自动扣除体力时间 */
	private int lastAutoTime;

	public BuildInfo() {

	}

	public BuildInfo(String[] strs) {
		this.type = Byte.parseByte(strs[0]);
		this.heroId = Integer.parseInt(strs[1]);
		this.lastTime = Integer.parseInt(strs[2]);
		this.lastAutoTime = Integer.parseInt(strs[3]);
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	/**
	 * 上一次配属时间 
	 * @return
	 * @author wcy 2016年3月14日
	 */
	public int getLastTime() {
		return lastTime;
	}

	/**
	 * 上一次配属时间 
	 * 
	 * @param lastTime
	 * @author wcy 2016年3月14日
	 */
	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * 上一次自动扣除体力时间
	 * 
	 * @return
	 * @author wcy 2016年3月14日
	 */
	public int getLastAutoTime() {
		return lastAutoTime;
	}

	/**
	 * 上一次自动扣除体力时间
	 * @param lastAutoTime
	 * @author wcy 2016年3月14日
	 */
	public void setLastAutoTime(int lastAutoTime) {
		this.lastAutoTime = lastAutoTime;
	}

}
