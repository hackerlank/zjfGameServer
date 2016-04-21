package byCodeGame.game.entity.file;


/**
 * 兵种配置表类
 * @author xjd
 *
 */
public class ArmsConfig {
	
	/** 兵种ID	 					*/
	private int id;
	/** 兵种类型  1步兵 2骑兵 3弓兵 4策士	*/
	private int functionType;
	/** 兵种物理攻击系数				*/
	private int atkRate;
	/** 兵种物理防御系数				*/
	private int defRate;
	/** 兵种战法攻击系数				*/
	private int tacticsAtkRate;
	/** 兵种战法防御系数				*/
	private int tacticsDefRate;
	/** 兵种计策攻击系数				*/
	private int trickAtkRate;
	/** 兵种计策防御系数				*/
	private int trickDefRate;
	/** 基础暴击						*/
	private int generalCrit;
	/** 基础反击						*/
	private int generalBack;
	/** 兵种阶级						*/
	private int armyTypeLv;
	/** 需求武力					    */
	private int powerNeed;
	/** 需求智力 						*/
	private int intelNeed;
	/** 需求统帅 						*/
	private int captainNeed;
	/** 攻击方式 						*/
	private int attackType;
	/** 兵种技能 						*/
	private int armSkillID;
	/**品阶*/
	private int rank;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFunctionType() {
		return functionType;
	}
	public void setFunctionType(int functionType) {
		this.functionType = functionType;
	}
	public double getAtkRate() {
		return atkRate/(double)10000;
	}
	public void setAtkRate(int atkRate) {
		this.atkRate = atkRate;
	}
	public double getDefRate() {
		return defRate/10000.0;
	}
	public void setDefRate(int defRate) {
		this.defRate = defRate;
	}
	public double getTacticsAtkRate() {
		return tacticsAtkRate/(double)10000;
	}
	public void setTacticsAtkRate(int tacticsAtkRate) {
		this.tacticsAtkRate = tacticsAtkRate;
	}
	public double getTacticsDefRate() {
		return tacticsDefRate/10000.0;
	}
	public void setTacticsDefRate(int tacticsDefRate) {
		this.tacticsDefRate = tacticsDefRate;
	}
	public double getTrickAtkRate() {
		return trickAtkRate/(double)10000;
	}
	public void setTrickAtkRate(int trickAtkRate) {
		this.trickAtkRate = trickAtkRate;
	}
	public double getTrickDefRate() {
		return trickDefRate/10000.0;
	}
	public void setTrickDefRate(int trickDefRate) {
		this.trickDefRate = trickDefRate;
	}
	public int getGeneralCrit() {
		return generalCrit;
	}
	public void setGeneralCrit(int generalCrit) {
		this.generalCrit = generalCrit;
	}
	public int getGeneralBack() {
		return generalBack;
	}
	public void setGeneralBack(int generalBack) {
		this.generalBack = generalBack;
	}
	public int getArmyTypeLv() {
		return armyTypeLv;
	}
	public void setArmyTypeLv(int armyTypeLv) {
		this.armyTypeLv = armyTypeLv;
	}
	public int getPowerNeed() {
		return powerNeed;
	}
	public void setPowerNeed(int powerNeed) {
		this.powerNeed = powerNeed;
	}
	public int getIntelNeed() {
		return intelNeed;
	}
	public void setIntelNeed(int intelNeed) {
		this.intelNeed = intelNeed;
	}
	public int getCaptainNeed() {
		return captainNeed;
	}
	public void setCaptainNeed(int captainNeed) {
		this.captainNeed = captainNeed;
	}
	public int getAttackType() {
		return attackType;
	}
	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}
	public int getArmSkillID() {
		return armSkillID;
	}
	public void setArmSkillID(int armSkillID) {
		this.armSkillID = armSkillID;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
}
