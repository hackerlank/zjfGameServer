package byCodeGame.game.moudle.arena.msg;

public class AtkArenaFightMsg extends ArenaFightMsg {

	/** 目标的唯一识别号	 */
	private String targetUuid;
	/** 扣除的血量	 */
	private int hurtVal;
	
	
	public String getTargetUuid() {
		return targetUuid;
	}
	public void setTargetUuid(String targetUuid) {
		this.targetUuid = targetUuid;
	}
	public int getHurtVal() {
		return hurtVal;
	}
	public void setHurtVal(int hurtVal) {
		this.hurtVal = hurtVal;
	}
	
}
