package byCodeGame.game.entity.file;

/**
 * 武将能力配置
 * @author 王君辉
 *
 */
public class HeroConfig {

	/** 武将ID	 */
	private int heroId;
	/** 图像ID	 */
	private int imgId;
	/** 武力	 */
	private int power;
	/** 智力	 */
	private int intel;
	/**生命值*/
	private int hp;
	/** 统帅	 				*/
	private int captain;
	/**普攻*/
	private int attack;
	/**普防*/
	private int defence;
	/**战攻*/
	private int zatk;
	/**战防*/
	private int zdef;
	/**策攻*/
	private int catk;
	/**策防*/
	private int cdef;
	/** 力量成长值	 			*/
	private int powerGrowValue;
	/** 智力成长值	 			*/
	private int intelGrowValue;
	/** 统帅成长值	 			*/
	private int captainGrowValue;
	/** 技能ID	 			*/
	private int skillId;
	/** 武将名字				*/
	private String name;
	/** 带兵种类				*/
	private int profession;
	/** 品质					*/
	private int quality;
	/** 初始转生				*/
	private int minR;
	/** 最大转生				*/
	private int maxR;
	/** 被动技能ID			*/
	private int skillBD;
	/** 奥义技能ID			*/
	private int skillAY;
	/** 武将体力最大值			*/
	private int maxMa;
	/** 国籍 */
	private int nation;
	/**武将统帅成长值*/
	private int heroCaptainGrowValue;
	/**武将武力成长值*/
	private int heroPowerGrowValue;
	/**武将智力成长值*/
	private int heroIntelGrowValue;
	
	
	
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
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
	public int getCaptain() {
		return captain;
	}
	public void setCaptain(int captain) {
		this.captain = captain;
	}
	public double getPowerGrowValue() {
		return (double)(powerGrowValue/(double)100);
	}
	public void setPowerGrowValue(int powerGrowValue) {
		this.powerGrowValue = powerGrowValue;
	}
	public double getIntelGrowValue() {
		return (double)(intelGrowValue/(double)100);
	}
	public void setIntelGrowValue(int intelGlowValue) {
		this.intelGrowValue = intelGlowValue;
	}
	public double getCaptainGrowValue() {
		return (double)(captainGrowValue/(double)100);
	}
	public void setCaptainGrowValue(int captainGrowValue) {
		this.captainGrowValue = captainGrowValue;
	}
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getImgId() {
		return imgId;
	}
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	public int getMinR() {
		return minR;
	}
	public void setMinR(int minR) {
		this.minR = minR;
	}
	public int getMaxR() {
		return maxR;
	}
	public void setMaxR(int maxR) {
		this.maxR = maxR;
	}
	public int getSkillBD() {
		return skillBD;
	}
	public void setSkillBD(int skillBD) {
		this.skillBD = skillBD;
	}
	public int getSkillAY() {
		return skillAY;
	}
	public void setSkillAY(int skillAY) {
		this.skillAY = skillAY;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public int getProfession() {
		return profession;
	}
	public void setProfession(int profession) {
		this.profession = profession;
	}
	public int getMaxMa() {
		return maxMa;
	}
	public void setMaxMa(int maxMa) {
		this.maxMa = maxMa;
	}
	public int getNation() {
		return nation;
	}
	public void setNation(int nation) {
		this.nation = nation;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDefence() {
		return defence;
	}
	public void setDefence(int defence) {
		this.defence = defence;
	}
	
	public int getZatk() {
		return zatk;
	}
	public void setZatk(int zatk) {
		this.zatk = zatk;
	}
	public int getZdef() {
		return zdef;
	}
	public void setZdef(int zdef) {
		this.zdef = zdef;
	}
	public int getCatk() {
		return catk;
	}
	public void setCatk(int catk) {
		this.catk = catk;
	}
	public int getCdef() {
		return cdef;
	}
	public void setCdef(int cdef) {
		this.cdef = cdef;
	}
	public int getHeroCaptainGrowValue() {
		return heroCaptainGrowValue;
	}
	public void setHeroCaptainGrowValue(int heroCaptainGrowValue) {
		this.heroCaptainGrowValue = heroCaptainGrowValue;
	}
	public int getHeroPowerGrowValue() {
		return heroPowerGrowValue;
	}
	public void setHeroPowerGrowValue(int heroPowerGrowValue) {
		this.heroPowerGrowValue = heroPowerGrowValue;
	}
	public int getHeroIntelGrowValue() {
		return heroIntelGrowValue;
	}
	public void setHeroIntelGrowValue(int heroIntelGrowValue) {
		this.heroIntelGrowValue = heroIntelGrowValue;
	}
}
