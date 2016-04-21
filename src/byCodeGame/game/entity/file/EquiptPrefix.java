package byCodeGame.game.entity.file;

public class EquiptPrefix {
	/** id				*/
	private int id;
	/** 类型				*/
	private int type;
	/** 名字				*/
	private String name;
	/** 等级				*/
	private int lv;
	/** 带兵增益			*/
	private int hp;
	/** 兵击增益			*/
	private int bj;
	/** 增伤				*/
	private int addHurt;
	/** 减伤				*/
	private int rmHurt;
	/** 权重				*/
	private int weight;
	/** 炼化返还			*/
	private int returnStone;
	
	
	
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
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getBj() {
		return bj;
	}
	public void setBj(int bj) {
		this.bj = bj;
	}
	public int getAddHurt() {
		return addHurt;
	}
	public void setAddHurt(int addHurt) {
		this.addHurt = addHurt;
	}
	public int getRmHurt() {
		return rmHurt;
	}
	public void setRmHurt(int rmHurt) {
		this.rmHurt = rmHurt;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getReturnStone() {
		return returnStone;
	}
	public void setReturnStone(int returnStone) {
		this.returnStone = returnStone;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
