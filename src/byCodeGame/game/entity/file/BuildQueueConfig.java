package byCodeGame.game.entity.file;


/**
 * 建筑队列配置表 用于配置开启建筑队列所需金钱
 * @author 王君辉
 *
 */
public class BuildQueueConfig {

	private int id;
	
	private int needGold;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNeedGold() {
		return needGold;
	}

	public void setNeedGold(int needGold) {
		this.needGold = needGold;
	}
	
}
