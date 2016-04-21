package byCodeGame.game.entity.po;

import java.util.HashMap;

public class WineDesk {
	private HashMap<Integer, Integer> deskHeroMap = new HashMap<>();
	/**酒桌的座位的数量*/
	private int seatNum;
	/** 是否已经宴请（0否1是） */
	private byte isDrink;
	/**需要消耗多少酒*/
	private int needWinesNum;

	/**
	 * @return the deskHeroMap
	 */
	public HashMap<Integer, Integer> getDeskHeroMap() {
		return deskHeroMap;
	}

	/**
	 * @return 酒桌的座位的数量
	 */
	public int getSeatNum() {
		return seatNum;
	}

	/**
	 * 酒桌的座位的数量
	 * @param seatNum
	 * @author wcy
	 */
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	/**
	 * @return the needWinesNum
	 */
	public int getNeedWinesNum() {
		return needWinesNum;
	}

	/**
	 * @param needWinesNum the needWinesNum to set
	 */
	public void setNeedWinesNum(int needWinesNum) {
		this.needWinesNum = needWinesNum;
	}

	/**
	 * @return the isDrink
	 */
	public byte getIsDrink() {
		return isDrink;
	}

	/**
	 * @param isDrink the isDrink to set
	 */
	public void setIsDrink(byte isDrink) {
		this.isDrink = isDrink;
	}

}
