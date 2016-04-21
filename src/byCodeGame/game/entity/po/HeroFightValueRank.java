package byCodeGame.game.entity.po;
/**
 * 武将战力和
 * @author wcy 2016年2月25日
 *
 */
public class HeroFightValueRank extends Rank {
	private String name;
	private int fightValue;
	private int enterTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFightValue() {
		return fightValue;
	}

	public void setFightValue(int fightValue) {
		this.fightValue = fightValue;
	}

	public int getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(int enterTime) {
		this.enterTime = enterTime;
	}

}
