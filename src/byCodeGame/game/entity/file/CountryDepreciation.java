package byCodeGame.game.entity.file;

public class CountryDepreciation {
	/** 城市数量		*/
	private int city;
	/** 兵力递减万分制	*/
	private int hp;
	/** 普攻递减万分制	*/
	private int generalAtk;
	/** 普防递减万分制	*/
	private int generalDef;
	/** 战功递减万分制	*/
	private int kongfuAtk;
	/** 战防递减万分制	*/
	private int kongfuDef;
	/** 策攻递减万分制	*/
	private int magicAtk;
	/** 策防递减万分制	*/
	private int magicDef;
	/** 图标ID		*/
	private int imgID;
	/** 说明			*/
	private String desc;
	
	
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public double getHp() {
		return hp/10000.0;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public double getGeneralAtk() {
		return generalAtk/10000.0;
	}
	public void setGeneralAtk(int generalAtk) {
		this.generalAtk = generalAtk;
	}
	public double getGeneralDef() {
		return generalDef/10000.0;
	}
	public void setGeneralDef(int generalDef) {
		this.generalDef = generalDef;
	}
	public double getKongfuAtk() {
		return kongfuAtk/10000.0;
	}
	public void setKongfuAtk(int kongfuAtk) {
		this.kongfuAtk = kongfuAtk;
	}
	public double getKongfuDef() {
		return kongfuDef/10000.0;
	}
	public void setKongfuDef(int kongfuDef) {
		this.kongfuDef = kongfuDef;
	}
	public double getMagicAtk() {
		return magicAtk/10000.0;
	}
	public void setMagicAtk(int magicAtk) {
		this.magicAtk = magicAtk;
	}
	public double getMagicDef() {
		return magicDef/10000.0;
	}
	public void setMagicDef(int magicDef) {
		this.magicDef = magicDef;
	}
	public int getImgID() {
		return imgID;
	}
	public void setImgID(int imgID) {
		this.imgID = imgID;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
