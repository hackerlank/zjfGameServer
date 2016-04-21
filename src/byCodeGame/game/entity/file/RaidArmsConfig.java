package byCodeGame.game.entity.file;
/***
 * 团队战役配置表
 * @author xjd
 *
 */
public class RaidArmsConfig {
	/** 副本ID		*/
	private int raidId;
	/** 需要的等级		*/
	private int needLv;
	
	
	
	public int getRaidId() {
		return raidId;
	}
	public void setRaidId(int raidId) {
		this.raidId = raidId;
	}
	public int getNeedLv() {
		return needLv;
	}
	public void setNeedLv(int needLv) {
		this.needLv = needLv;
	}
}
