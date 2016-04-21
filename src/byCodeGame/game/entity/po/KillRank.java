package byCodeGame.game.entity.po;

/**
 * 击杀排名
 * 
 * @author wcy 2016年2月25日
 *
 */
public class KillRank extends Rank {
	private String name;
	private int enterTime;
	private int value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(int enterTime) {
		this.enterTime = enterTime;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
