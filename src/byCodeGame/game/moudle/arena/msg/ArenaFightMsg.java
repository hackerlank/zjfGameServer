package byCodeGame.game.moudle.arena.msg;

public class ArenaFightMsg {

	/** 行为类型 0产卵 1移动  2攻击 3等待 4死亡 5技能 6BUFF	 */
	private byte type;
	/** 当前帧数	 */
	private int nowFrame;
	/** 角色唯一识别号	 */
	private String uuid;

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getNowFrame() {
		return nowFrame;
	}

	public void setNowFrame(int nowFrame) {
		this.nowFrame = nowFrame;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
