package byCodeGame.game.entity.file;

public class Profession {
	/** 类型			*/
	private int id;
	/** 名字			*/
	private String name;
	/** 带兵量		*/
	private int hp;
	/** 每级增长		*/
	private int hpGrow;
	/** 可带兵种类型1	*/
	private int army1;
	/** 可带兵种类型2	*/
	private int army2;
	/** 普通攻击		*/
	private int attack;
	/** 普通防御		*/
	private int defence;
	/** 战法攻击		*/
	private int kongfuAtk;
	/** 战法防御		*/
	private int kongfuDef;
	/** 计策攻击		*/
	private int magicAtk;
	/** 计策防御		*/
	private int magicDef;
	/** 等级增量 普攻	*/
	private int attackGrow;
	/** 等级增量 普防	*/
	private int defenceGrow;
	/** 等级增量 战攻	*/
	private int kongfuAtkGrow;
	/** 等级增量 战防	*/
	private int kongfuDefGrow;
	/** 等级增量 策攻	*/
	private int magicAtkGrow;
	/** 等级增量 策防	*/
	private int magicDefGrow;
	/** 介绍			*/
	private String desc;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getHpGrow() {
		return hpGrow;
	}
	public void setHpGrow(int hpGrow) {
		this.hpGrow = hpGrow;
	}
	public int getArmy1() {
		return army1;
	}
	public void setArmy1(int army1) {
		this.army1 = army1;
	}
	public int getArmy2() {
		return army2;
	}
	public void setArmy2(int army2) {
		this.army2 = army2;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
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
	public int getKongfuAtk() {
		return kongfuAtk;
	}
	public void setKongfuAtk(int kongfuAtk) {
		this.kongfuAtk = kongfuAtk;
	}
	public int getKongfuDef() {
		return kongfuDef;
	}
	public void setKongfuDef(int kongfuDef) {
		this.kongfuDef = kongfuDef;
	}
	public int getMagicAtk() {
		return magicAtk;
	}
	public void setMagicAtk(int magicAtk) {
		this.magicAtk = magicAtk;
	}
	public int getMagicDef() {
		return magicDef;
	}
	public void setMagicDef(int magicDef) {
		this.magicDef = magicDef;
	}
	public double getAttackGrow() {
		return attackGrow/100;
	}
	public void setAttackGrow(int attackGrow) {
		this.attackGrow = attackGrow;
	}
	public double getDefenceGrow() {
		return defenceGrow/100;
	}
	public void setDefenceGrow(int defenceGrow) {
		this.defenceGrow = defenceGrow;
	}
	public double getKongfuAtkGrow() {
		return kongfuAtkGrow/100;
	}
	public void setKongfuAtkGrow(int kongfuAtkGrow) {
		this.kongfuAtkGrow = kongfuAtkGrow;
	}
	public double getKongfuDefGrow() {
		return kongfuDefGrow/100;
	}
	public void setKongfuDefGrow(int kongfuDefGrow) {
		this.kongfuDefGrow = kongfuDefGrow;
	}
	public double getMagicAtkGrow() {
		return magicAtkGrow/100;
	}
	public void setMagicAtkGrow(int magicAtkGrow) {
		this.magicAtkGrow = magicAtkGrow;
	}
	public double getMagicDefGrow() {
		return magicDefGrow/100;
	}
	public void setMagicDefGrow(int magicDefGrow) {
		this.magicDefGrow = magicDefGrow;
	}
	
}
