package byCodeGame.game.entity.po;

import byCodeGame.game.entity.bo.Role;

/**
 * 
 * @author wcy
 *
 */
public class OffensiveData extends Rank {
	private int offensiveTime;// 参与时间
	private int hurtValue;// 伤害值

	public int getOffensiveTime() {
		return offensiveTime;
	}

	public void setOffensiveTime(int nowTime) {
		this.offensiveTime = nowTime;
	}

	public int getHurtValue() {
		return hurtValue;
	}

	public void setHurtValue(int hurtValue) {
		this.hurtValue = hurtValue;
	}


}
