package byCodeGame.game.entity.file;

/**
 * 排行榜奖励区间
 * @author wcy 2016年3月5日
 *
 */
public class LeaderBoard {
	private int type;/*排行榜类型*/
	private String leader;/*区间段*/
	private int index;/*奖励索引*/
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
}
