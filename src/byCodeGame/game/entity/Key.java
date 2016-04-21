package byCodeGame.game.entity;

public class Key {
	private String id;
	private byte isUsed;
	private int useTime;
	private String award;
	private byte type;

	public String getKey() {
		return id;
	}

	public void setKey(String key) {
		this.id = key;
	}

	public byte getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(byte isUsed) {
		this.isUsed = isUsed;
	}

	public int getUseTime() {
		return useTime;
	}

	public void setUseTime(int useTime) {
		this.useTime = useTime;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}
	
}
