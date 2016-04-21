package byCodeGame.game.entity.po;

public class History {
	private int time;
	private int year;
	private byte type;
	private String str;
	private String uuid;
	
	public History() {
		
	}
	
	public History(String[] value)
	{
		this.time = Integer.parseInt(value[0]);
		this.year = Integer.parseInt(value[1]);
		this.type = Byte.parseByte(value[2]);
		this.str = value[3];
		if(value.length < 5)
		{
			this.uuid = "";
		}else {
			this.uuid = value[4];
		}
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getUuid() {
		return this.uuid == null||this.uuid.equals("") ? "":uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
}
