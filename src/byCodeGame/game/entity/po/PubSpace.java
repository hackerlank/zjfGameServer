package byCodeGame.game.entity.po;

/**
 * 酒台
 * 
 * @author wcy 2016年5月3日
 *
 */
public class PubSpace {
	/**位置*/
	private byte position;
	/** 酒炉 */
	private int pubForgeId;
	/** 酒桶 */
	private int pubBucketId;

	public int getPubForgeId() {
		return pubForgeId;
	}

	public void setPubForgeId(int pubForgeId) {
		this.pubForgeId = pubForgeId;
	}

	public int getPubBucketId() {
		return pubBucketId;
	}

	public void setPubBucketId(int pubBucketId) {
		this.pubBucketId = pubBucketId;
	}

	public void setPosition(byte position) {
		this.position = position;
	}

	public byte getPosition() {
		return position;
	}

}
