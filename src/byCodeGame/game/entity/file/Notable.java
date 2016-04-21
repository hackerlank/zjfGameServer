package byCodeGame.game.entity.file;


/**
 * 走马灯信息
 * 
 * @author wcy 2016年2月18日
 *
 */
public class Notable {
	private int id;//编号（没啥用）
	private int type;//类型
	private String limit;//现实等级（等级，等级）
	private int sequence;//1延续显示0特定显示

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLimit() {		
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

}
