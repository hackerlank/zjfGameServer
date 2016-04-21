package byCodeGame.game.entity.po;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ChapterArmsConfig;
import byCodeGame.game.moudle.fight.FightConstant;


/**
 * 武将战斗数据
 * @author 王君辉
 *
 */
public class HeroFightData {

	/** 武将ID	 */
	private int heroId;
	/** 阵营 	 (-1:本方,1:敌方)*/
	private byte camp;
	/** 队列位置	 */
	private byte index;
	/** 武将等级	 */
	private short lv;
	/** 总血量	 */
	private int sumHp;
	/** 总武力	 */
	private int sumAtk;
	/** 总智力	 */
	private int sumInt;
	/** 总统帅	 */
	private int sumCaptain;
	/** 攻击速度	 */
	private int atkSpeed;
	/** 移动速度	 */
	private double moveSpeed;
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
	/** 
	 * 兵力配置<br/>
	 * 兵种ID,数量,血量,攻击速度,移动速度,攻击力,防御力,策略攻击,策略防御,技能id,攻击距离;
	 */
	private String arms;
	/** 兵力map	(这个属性只有阵营为友军才会使用,然后循环这个map获得arms) */
	private Map<Integer, ArmsFightData> armsMap = new HashMap<Integer, ArmsFightData>();
	
	public HeroFightData(){
		
	}
	/**
	 * 设置敌军战斗数据时使用
	 * @param chapterArmsConfig
	 */
	public HeroFightData(ChapterArmsConfig chapterArmsConfig,byte index){
//		this.camp = FightConstant.CAMP_ENEMY;
//		this.index = index;
//		this.heroId = chapterArmsConfig.getHeroId();
//		this.lv = (short)chapterArmsConfig.getLv();
//		this.sumHp = chapterArmsConfig.getSumHp();
//		this.sumAtk = chapterArmsConfig.getSumAtk();
//		this.sumInt = chapterArmsConfig.getSumInt();
//		this.sumCaptain = chapterArmsConfig.getSumCaptain();
//		this.atkSpeed = chapterArmsConfig.getAtkSpeed();
//		this.setMoveSpeed(chapterArmsConfig.getMoveSpeed());
//		this.atk = chapterArmsConfig.getAtk();
//		this.def = chapterArmsConfig.getDef();
//		this.matk = chapterArmsConfig.getMatk();
//		this.mdef = chapterArmsConfig.getMdef();
//		this.skillId = chapterArmsConfig.getSkillId();
//		this.arms = chapterArmsConfig.getArms();
//		this.atkDistance = chapterArmsConfig.getAtkDistance();
//		this.scan = chapterArmsConfig.getScan();
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
	public byte getIndex() {
		return index;
	}
	public void setIndex(byte index) {
		this.index = index;
	}
	public short getLv() {
		return lv;
	}
	public void setLv(short lv) {
		this.lv = lv;
	}
	public int getAtkSpeed() {
		return atkSpeed;
	}
	public void setAtkSpeed(int atkSpeed) {
		this.atkSpeed = atkSpeed;
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
	public String getArms() {
		return arms;
	}
	public void setArms(String arms) {
		this.arms = arms;
	}
	public Map<Integer, ArmsFightData> getArmsMap() {
		return armsMap;
	}
	public void setArmsMap(Map<Integer, ArmsFightData> armsMap) {
		this.armsMap = armsMap;
	}
	public int getSumHp() {
		return sumHp;
	}
	public void setSumHp(int sumHp) {
		this.sumHp = sumHp;
	}
	public int getSumAtk() {
		return sumAtk;
	}
	public void setSumAtk(int sumAtk) {
		this.sumAtk = sumAtk;
	}
	public int getSumInt() {
		return sumInt;
	}
	public void setSumInt(int sumInt) {
		this.sumInt = sumInt;
	}
	public int getSumCaptain() {
		return sumCaptain;
	}
	public void setSumCaptain(int sumCaptain) {
		this.sumCaptain = sumCaptain;
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
	public double getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(double moveSpeed) {
		this.moveSpeed = moveSpeed;
		
	}
}
