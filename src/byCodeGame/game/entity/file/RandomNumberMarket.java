package byCodeGame.game.entity.file;
/**
 * 市场每个等级会出现的装备配置表
 * @author xjd
 *
 */
public class RandomNumberMarket {
	/** 玩家的等级				*/
	private int roleLv;
	/** 控制出现物品的最小数字	*/
	private int minNumber;
	/** 控制出现武平的最大数字	*/
	private int maxNumber;
	
	
	
	
	public int getRoleLv() {
		return roleLv;
	}
	public void setRoleLv(int roleLv) {
		this.roleLv = roleLv;
	}
	public int getMinNumber() {
		return minNumber;
	}
	public void setMinNumber(int minNumber) {
		this.minNumber = minNumber;
	}
	public int getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}
	
	
	
}
