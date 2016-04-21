package byCodeGame.game.entity.file;


/**
 * 
 * @author sht
 *
 */
public class ArmsResearchConfig {
	
	/** 兵种ID	 */
	private int id;
	/** 兵种类型  1步兵 2骑兵 3弓兵 4策士	*/
	private int functionType;
	/** 兵种阶级						*/
	private int armyTypeLv;
	/** 所需科技点*/
	private int needNum;
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
	public int getArmyTypeLv() {
		return armyTypeLv;
	}
	public void setArmyTypeLv(int armyTypeLv) {
		this.armyTypeLv = armyTypeLv;
	}
	public int getNeedNum() {
		return needNum;
	}
	public void setNeedNum(int needNum) {
		this.needNum = needNum;
	}
}
