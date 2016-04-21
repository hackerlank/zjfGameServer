package byCodeGame.game.entity.bo;

public class Mail {
	/** 邮件ID*/
	private int id;
	/** 接收方	 */
	private String targetName;
	/** 发送方	 */
	private String sendName;
	/** 邮件类型*/
	private byte type;
	/** 邮件主题*/
	private String title;
	/** 邮件内容*/
	private String context;
	/** 附件*/
	private String attached;
	/** 是否已读*/
	private byte checked;
	/** 邮件发送时间*/
	private long time;
	
	private boolean change;
	
	
	
	
	
	
	
	
	/*****************************get & set*********************************************/
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getAttached() {
		return attached;
	}
	public void setAttached(String attached) {
		this.attached = attached;
	}
	public byte getChecked() {
		return checked;
	}
	public void setChecked(byte checked) {
		this.checked = checked;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	public boolean isChange() {
		return change;
	}
	public void setChange(boolean change) {
		this.change = change;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	
	
}
