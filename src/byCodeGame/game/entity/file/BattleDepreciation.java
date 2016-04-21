package byCodeGame.game.entity.file;

/***
 * 世界大战战斗buff
 * @author win7n
 *
 */
public class BattleDepreciation {
	/** 数量		*/
	private int kill;
	/** 图标ID	*/
	private int imgID;
	/** 血量递减万分制	*/
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
	/** 描述			*/
	private String desc;
	
	
	
	public int getKill() {
		return kill;
	}
	public void setKill(int kill) {
		this.kill = kill;
	}
	public int getImgID() {
		return imgID;
	}
	public void setImgID(int imgID) {
		this.imgID = imgID;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
