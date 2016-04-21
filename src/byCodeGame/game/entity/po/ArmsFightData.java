package byCodeGame.game.entity.po;


/**
 * 小兵战斗数据
 * @author 王君辉
 *
 */
public class ArmsFightData {

	/** 兵种ID	 */
	private int id;
	/** 兵力数量	 */
	private int num;
	/** 总血量	 */
	private int hp;
	/** 攻击速度	 */
	private int atkSpeed;
	/** 移动速度	 */
	private int moveSpeed;
	/** 攻击力	 */
	private int atk;
	/** 防御力	 */
	private int def;
	/** 策略攻击	 */
	private int matk;
	/** 策略防御	 */
	private int mdef;
	/** 技能ID	 */
	private int skillId;
	/** 攻击距离	 */
	private int atkDistance;
	/** 视野	 */
	private int scan;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getAtkSpeed() {
		return atkSpeed;
	}
	public void setAtkSpeed(int atkSpeed) {
		this.atkSpeed = atkSpeed;
	}
	public int getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getMatk() {
		return matk;
	}
	public void setMatk(int matk) {
		this.matk = matk;
	}
	public int getMdef() {
		return mdef;
	}
	public void setMdef(int mdef) {
		this.mdef = mdef;
	}
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public int getAtkDistance() {
		return atkDistance;
	}
	public void setAtkDistance(int atkDistance) {
		this.atkDistance = atkDistance;
	}
	public int getScan() {
		return scan;
	}
	public void setScan(int scan) {
		this.scan = scan;
	}
}
