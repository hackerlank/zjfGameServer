package byCodeGame.game.entity.file;
/**
 * 排行奖励内容
 * @author wcy 2016年3月5日
 *
 */
public class LeaderReward {
	private int ID;
	private String item;
	private String mailTile;
	private String mailContent;
	public String getMailContent() {
		return mailContent;
	}
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getMailTile() {
		return mailTile;
	}
	public void setMailTile(String mailTile) {
		this.mailTile = mailTile;
	}
}
