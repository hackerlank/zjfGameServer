package byCodeGame.game.entity.file;

/**
 * 关卡武将配置表
 * @author 王君辉
 *
 */
public class ChapterArmsConfig {

	private String id;
	/** 英雄ID	 		*/
	private int heroId;
	/** 兵种ID			*/
	private int armyId;
	/** 转生次数*/
	private int rank;
	/** 军团物理攻击		*/
	private int generalAttack;
	/** 军团物理防御		*/
	private int generalDefence;
	/** 军团战法攻击		*/
	private int powerAttack;
	/** 军团战法防御		*/
	private int powerDefence;
	/** 军团计策攻击		*/
	private int magicalAttack;
	/** 军团计策防御		*/
	private int magicalDefence;
	/** 兵力				*/
	private int hp;
	/** 攻击 普系数			*/
	private int atkRate;
	/** 防御 普系数			*/
	private int defRate;
	/** 攻击 战系数			*/
	private int tacticsAtkRate;
	/** 防御 战系数			*/
	private int tacticsDefRate;
	/** 攻击 策系数			*/
	private int trickAtkRate;
	/** 防御 策系数			*/
	private int trickDefRate;
	/** 技能ID			*/
	private int skillID;
	/** 兵击				*/
	private int bingji;
	/** 兵击等级			*/
	private int armySkillLv;
	/** 三维统帅			*/
	private int captain;
	/** 三维武力			*/
	private int power;
	/** 三维智力			*/
	private int intel;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public int getArmyId() {
		return armyId;
	}
	public void setArmyId(int armyId) {
		this.armyId = armyId;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getGeneralAttack() {
		return generalAttack;
	}
	public void setGeneralAttack(int generalAttack) {
		this.generalAttack = generalAttack;
	}
	public int getGeneralDefence() {
		return generalDefence;
	}
	public void setGeneralDefence(int generalDefence) {
		this.generalDefence = generalDefence;
	}
	public int getPowerAttack() {
		return powerAttack;
	}
	public void setPowerAttack(int powerAttack) {
		this.powerAttack = powerAttack;
	}
	public int getPowerDefence() {
		return powerDefence;
	}
	public void setPowerDefence(int powerDefence) {
		this.powerDefence = powerDefence;
	}
	public int getMagicalAttack() {
		return magicalAttack;
	}
	public void setMagicalAttack(int magicalAttack) {
		this.magicalAttack = magicalAttack;
	}
	public int getMagicalDefence() {
		return magicalDefence;
	}
	public void setMagicalDefence(int magicalDefence) {
		this.magicalDefence = magicalDefence;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public double getAtkRate() {
		return atkRate/10000;
	}
	public void setAtkRate(int atkRate) {
		this.atkRate = atkRate;
	}
	public int getDefRate() {
		return defRate;
	}
	public void setDefRate(int defRate) {
		this.defRate = defRate;
	}
	public double getTacticsAtkRate() {
		return tacticsAtkRate/10000;
	}
	public void setTacticsAtkRate(int tacticsAtkRate) {
		this.tacticsAtkRate = tacticsAtkRate;
	}
	public int getTacticsDefRate() {
		return tacticsDefRate;
	}
	public void setTacticsDefRate(int tacticsDefRate) {
		this.tacticsDefRate = tacticsDefRate;
	}
	public double getTrickAtkRate() {
		return trickAtkRate/10000;
	}
	public void setTrickAtkRate(int trickAtkRate) {
		this.trickAtkRate = trickAtkRate;
	}
	public float getTrickDefRate() {
		return 100/(100+trickDefRate);
	}
	public void setTrickDefRate(int trickDefRate) {
		this.trickDefRate = trickDefRate;
	}
	public int getSkillID() {
		return skillID;
	}
	public void setSkillID(int skillID) {
		this.skillID = skillID;
	}
	public int getBingji() {
		return bingji;
	}
	public void setBingji(int bingji) {
		this.bingji = bingji;
	}
	public int getArmySkillLv() {
		return armySkillLv;
	}
	public void setArmySkillLv(int armySkillLv) {
		this.armySkillLv = armySkillLv;
	}
	public int getCaptain() {
		return captain;
	}
	public void setCaptain(int captain) {
		this.captain = captain;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getIntel() {
		return intel;
	}
	public void setIntel(int intel) {
		this.intel = intel;
	}
}
