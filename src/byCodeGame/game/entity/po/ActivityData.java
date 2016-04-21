package byCodeGame.game.entity.po;

/**
 * 活动数据记录基类
 * @author wcy 2016年4月20日
 *
 */
public class ActivityData {
	public ActivityData() {
	}

	public ActivityData(String str) {
		this.progressStr = str;
	}

	protected String progressStr;

	public String getProgressStr() {
		return progressStr;
	}
}
