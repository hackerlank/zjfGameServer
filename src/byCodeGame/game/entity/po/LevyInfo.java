package byCodeGame.game.entity.po;

public class LevyInfo {
	/** 类型				*/
	private byte type;
	/** 开始时间			*/
	private long startTime;
	/** CD 秒			*/
	private int cd;
	/** 收益值||城市ID		*/
	private int value;
	/** 额外收益||当时声望	*/
	private int valueOther;
	/** 执行武将ID	*/
	private int heroId;
	/**上次更新时间*/
	private int lastRefreshTime;
	
	public LevyInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public LevyInfo(String[] str) {
		this.heroId = Integer.parseInt(str[0]);
		this.type = Byte.parseByte(str[1]);
		this.startTime = Long.parseLong(str[2]);
		this.cd = Integer.parseInt(str[3]);
		this.value = Integer.parseInt(str[4]);
		this.valueOther = Integer.parseInt(str[5]);
		this.lastRefreshTime = Integer.parseInt(str[6]);
	}
	
	
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public int getCd() {
		return cd;
	}
	public void setCd(int cd) {
		this.cd = cd;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public int getValueOther() {
		return valueOther;
	}

	public void setValueOther(int valueOther) {
		this.valueOther = valueOther;
	}

	public int getLastRefreshTime() {
		return lastRefreshTime;
	}

	public void setLastRefreshTime(int lastRefreshTime) {
		this.lastRefreshTime = lastRefreshTime;
	}
}
