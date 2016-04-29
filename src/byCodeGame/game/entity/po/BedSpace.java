package byCodeGame.game.entity.po;

/**
 * 床
 * 
 * @author wcy 2016年4月29日
 *
 */
public class BedSpace {
	// 床
	private int bed;
	// 床单
	private int bedSheet;
	// 枕头1
	private int pillow1;
	// 枕头2
	private int pillow2;
	// 被套
	private int beddingBag;

	public void setBed(int bed) {
		this.bed = bed;
	}

	public int getBed() {
		return bed;
	}

	public void setBeddingBag(int beddingBag) {
		this.beddingBag = beddingBag;
	}

	public int getBeddingBag() {
		return beddingBag;
	}

	public void setBedSheet(int bedSheet) {
		this.bedSheet = bedSheet;
	}

	public int getBedSheet() {
		return bedSheet;
	}

	public void setPillow1(int pillow1) {
		this.pillow1 = pillow1;
	}

	public int getPillow1() {
		return pillow1;
	}

	public void setPillow2(int pillow2) {
		this.pillow2 = pillow2;
	}

	public int getPillow2() {
		return pillow2;
	}
}
