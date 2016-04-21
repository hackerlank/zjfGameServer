package byCodeGame.game.entity.file;

import byCodeGame.game.entity.bo.Role;


/**
 * 配置表- 玩家科技
 * @author xjd
 *
 */
public class RoleScienceConfig {
	private int id;
	/** 名字			*/
	private String name;
	/** 类型			*/
	private int type;
	/** 科技等级		*/
	private int lv;
	/** 需求建筑等级		*/
	private int needLv;
	/** 花费的战功		*/
	private int cost;
	/** 物理攻击加成	*/
	private int atk;
	/** 战法攻击加成	*/
	private int zatk; 
	/** 计策攻击加成	*/
	private int jatk; 
	/** 物理防御		*/
	private int def;
	/** 战法防御		*/
	private int zdef; 
	/** 计策防御		*/
	private int jdef; 
	/** 血量			*/
	private int hp;
	/** 其他			*/
	private int value;
	
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	public void setNeedLv(int needLv) {
		this.needLv = needLv;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	
	public int getZatk() {
		return zatk;
	}
	public void setZatk(int zatk) {
		this.zatk = zatk;
	}
	public int getJatk() {
		return jatk;
	}
	public void setJatk(int jatk) {
		this.jatk = jatk;
	}
	public int getZdef() {
		return zdef;
	}
	public void setZdef(int zdef) {
		this.zdef = zdef;
	}
	public int getJdef() {
		return jdef;
	}
	public void setJdef(int jdef) {
		this.jdef = jdef;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}	
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getNeedLv() {
		return needLv;
	}
	public int getCost() {
		return cost;
	}
}
