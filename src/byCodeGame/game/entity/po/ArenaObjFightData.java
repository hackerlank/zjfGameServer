package byCodeGame.game.entity.po;

import java.util.HashSet;
import java.util.Set;

import byCodeGame.game.skill.BaseSkill;
import byCodeGame.game.skill.buff.BaseBuff;



/**
 * 竞技场角色战斗数据类
 * @author wjh
 *
 */
public class ArenaObjFightData {
	
	/** 每个角色的唯一识别号	 */
	private String UUID;
	
	private int roleId;
	/** 英雄ID(或者为小兵的ID)	 */
	private int heroId;
	/** 阵营	 */
	private byte camp;
	/** 状态 0产软 1移动 2攻击 3等待	 */
	private byte state;
	/** 等级	 */
	private short lv;
	/** 力量	 */
	private int power;
	/** 智力	 */
	private int intPower;
	/** 统帅	 */
	private int captain;
	/** 当前血量	 */
	private int hp;
	/** 血量最大值	 */
	private int maxHp;
	/** 移动速度	 */
	private double moveSpeed;
	/** X位置	 */
	private double posX;
	/** Y位置	 */
	private double posY;
	/** 视野	 */
	private int scan;
	/** 攻击距离	 */
	private int atkDistance;
	/** 攻击间隔	 */
	private int atkDelay;
	/** 攻击力	 */
	private int atk;
	/** 防御力	 */
	private int def;
	/** 魔法攻击力	 */
	private int matk;
	/** 魔法防御力	 */
	private int mdef;
	/** 技能ID	 */
	private int skillId;
	/** 技能类型	 */
	private byte skillType;
	/** 队列位置 	 */
	private byte index;
	/** 攻击目标	 */
	private ArenaObjFightData targetObj;
	/** 上一次的攻击时间	 */
	private long lastAtkTime = 0;
	/** 攻击方式	1普通攻击  2魔法攻击 */
	private byte atkType;
	/** 士气  用于判定技能释放	 */
	private int morale;
	/** 是否可使用技能	 */
	private boolean useSkill;
	/** 技能类	 */
	private BaseSkill skill;
	/** 影响玩家的buff集合	 */
	private Set<BaseBuff> buffSet = new HashSet<BaseBuff>();
	/** 是否眩晕	 */
	private boolean isXuanYun;
	
	
	
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public byte getCamp() {
		return camp;
	}
	public void setCamp(byte camp) {
		this.camp = camp;
	}
	public short getLv() {
		return lv;
	}
	public void setLv(short lv) {
		this.lv = lv;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getIntPower() {
		return intPower;
	}
	public void setIntPower(int intPower) {
		this.intPower = intPower;
	}
	public int getCaptain() {
		return captain;
	}
	public void setCaptain(int captain) {
		this.captain = captain;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	public double getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(double moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	public int getScan() {
		return scan;
	}
	public void setScan(int scan) {
		this.scan = scan;
	}
	public int getAtkDistance() {
		return atkDistance;
	}
	public void setAtkDistance(int atkDistance) {
		this.atkDistance = atkDistance;
	}
	public int getAtkDelay() {
		return atkDelay;
	}
	public void setAtkDelay(int atkDelay) {
		this.atkDelay = atkDelay;
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
	public byte getSkillType() {
		return skillType;
	}
	public void setSkillType(byte skillType) {
		this.skillType = skillType;
	}
	public byte getIndex() {
		return index;
	}
	public void setIndex(byte index) {
		this.index = index;
	}
	public byte getState() {
		return state;
	}
	public void setState(byte state) {
		this.state = state;
	}
	public ArenaObjFightData getTargetObj() {
		return targetObj;
	}
	public void setTargetObj(ArenaObjFightData targetObj) {
		this.targetObj = targetObj;
	}
	public long getLastAtkTime() {
		return lastAtkTime;
	}
	public void setLastAtkTime(long lastAtkTime) {
		this.lastAtkTime = lastAtkTime;
	}
	public byte getAtkType() {
		return atkType;
	}
	public void setAtkType(byte atkType) {
		this.atkType = atkType;
	}
	public int getMorale() {
		return morale;
	}
	public void setMorale(int morale) {
		this.morale = morale;
	}
	public boolean isUseSkill() {
		return useSkill;
	}
	public void setUseSkill(boolean useSkill) {
		this.useSkill = useSkill;
	}
	public BaseSkill getSkill() {
		return skill;
	}
	public void setSkill(BaseSkill skill) {
		this.skill = skill;
	}
	public Set<BaseBuff> getBuffSet() {
		return buffSet;
	}
	public void setBuffSet(Set<BaseBuff> buffSet) {
		this.buffSet = buffSet;
	}
	public boolean isXuanYun() {
		return isXuanYun;
	}
	public void setXuanYun(boolean isXuanYun) {
		this.isXuanYun = isXuanYun;
	}
}
