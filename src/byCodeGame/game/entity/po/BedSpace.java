package byCodeGame.game.entity.po;

/**
 * 床
 * 
 * @author wcy 2016年4月29日
 *
 */
public class BedSpace {
	/** 位置 */
	private byte position;
	/** 床 */
	private int bedId;
	/** 床单 */
	private int bedSheetId;
	/** 枕头1 */
	private int pillow1Id;
	/** 枕头2 */
	private int pillow2Id;
	/** 被套 */
	private int beddingBagId;

	public byte getPosition() {
		return position;
	}

	public void setPosition(byte position) {
		this.position = position;
	}

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

	public int getPillow1Id() {
		return pillow1Id;
	}

	public void setPillow1Id(int pillow1Id) {
		this.pillow1Id = pillow1Id;
	}

	public int getPillow2Id() {
		return pillow2Id;
	}

	public void setPillow2Id(int pillow2Id) {
		this.pillow2Id = pillow2Id;
	}

	public int getBeddingBagId() {
		return beddingBagId;
	}

	public void setBeddingBagId(int beddingBagId) {
		this.beddingBagId = beddingBagId;
	}

}
