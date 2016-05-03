package byCodeGame.game.entity.po;

/**
 * 厨房位
 * 
 * @author wcy 2016年5月3日
 *
 */
public class KitchenSpace {
	/** 位置 */
	private byte position;
	/** 电磁炉 */
	private int electricForgeId;
	/** 锅 */
	private int panId;

	public int getElectricForgeId() {
		return electricForgeId;
	}

	/**
	 * 设置电磁炉id
	 * 
	 * @param electricForgeId
	 * @author wcy 2016年5月3日
	 */
	public void setElectricForgeId(int electricForgeId) {
		this.electricForgeId = electricForgeId;
	}

	public int getPanId() {
		return panId;
	}

	/**
	 * 设置锅的id
	 * 
	 * @param panId
	 * @author wcy 2016年5月3日
	 */
	public void setPanId(int panId) {
		this.panId = panId;
	}

	public byte getPosition() {
		return position;
	}

	public void setPosition(byte position) {
		this.position = position;
	}

}
