package byCodeGame.game.entity.file;

public class SkillConfig {

	/** 技能ID	 */
	private int id;
	/** 该技能的类名	 */
	private String className;
	/** 施法时间	 */
	private int castTime;
	/** 技能施法时是否会暂停游戏	 */
	private int isPause;
	/** 暂停时间	 */
	private int pauseTime;
	private int buffId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getCastTime() {
		return castTime;
	}
	public void setCastTime(int castTime) {
		this.castTime = castTime;
	}
	public int getIsPause() {
		return isPause;
	}
	public void setIsPause(int isPause) {
		this.isPause = isPause;
	}
	public int getPauseTime() {
		return pauseTime;
	}
	public void setPauseTime(int pauseTime) {
		this.pauseTime = pauseTime;
	}
	public int getBuffId() {
		return buffId;
	}
	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}
}
