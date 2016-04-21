package byCodeGame.game.entity.file;

/**
 * 
 * @author wcy 2016年4月18日
 *
 */
public class ActivityConfig {
	// 活动id
	private int activityId;
	// 奖励内容
	private String award;
	// 活动类型
	private int activityType;
	// 活动内容
	private String activityContent;
	// 活动开始时间
	private int startTime;
	// 活动结束时间
	private int endTime;
	// 邮件标题
	private String mailTitle;
	// 邮件内容
	private String mailContext;
	// 活动名称
	private String name;

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getActivityId() {
		return activityId;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}

	public String getActivityContent() {
		return activityContent;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailContext(String mailContext) {
		this.mailContext = mailContext;
	}

	public String getMailContext() {
		return mailContext;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
