package byCodeGame.game.entity.po;

import byCodeGame.game.entity.po.base.Space;

/**
 * 床
 * 
 * @author wcy 2016年4月29日
 *
 */
public class BedSpace extends Space{
	/** 床 */
	private int bedId;
	/** 床单 */
	private int bedSheetId;
	/** 枕头 */
	private int pillowId;
	/** 被套 */
	private int beddingBagId;

	public int getBedId() {
		return bedId;
	}

	public void setBedId(int bedId) {
		this.bedId = bedId;
	}

	public int getBedSheetId() {
		return bedSheetId;
	}

	public void setBedSheetId(int bedSheetId) {
		this.bedSheetId = bedSheetId;
	}

	public int getPillowId() {
		return pillowId;
	}

	public void setPillowId(int pillowId) {
		this.pillowId = pillowId;
	}

	public int getBeddingBagId() {
		return beddingBagId;
	}

	public void setBeddingBagId(int beddingBagId) {
		this.beddingBagId = beddingBagId;
	}

}
