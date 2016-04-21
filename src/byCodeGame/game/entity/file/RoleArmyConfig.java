package byCodeGame.game.entity.file;

public class RoleArmyConfig {
	private int id;
	/** 类型			*/
	private int type;
	/** 阶			*/
	private int rank;
	/** 最大等级		*/
	private int maxLv;
	/** 天赋点		*/
	private int point;
	/** 前置需求点数	*/
	private int needPoint;
	/** 天赋作用兵种	*/
	private int armyId;
	/** 数值			*/
	private int value;
	/** 物理攻击系数	*/
	private int atk;
	/** 物理防御系数	*/
	private int def;
	/** 战功			*/
	private int zatk;
	/** 战防			*/
	private int zdef;
	/** 策攻			*/
	private int catk;
	/** 策防			*/
	private int cdef;
	
	/**************************************************************************************/
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getMaxLv() {
		return maxLv;
	}
	public void setMaxLv(int maxLv) {
		this.maxLv = maxLv;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getNeedPoint() {
		return needPoint;
	}
	public void setNeedPoint(int needPoint) {
		this.needPoint = needPoint;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getArmyId() {
		return armyId;
	}
	public void setArmyId(int armyId) {
		this.armyId = armyId;
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
	
}
