package byCodeGame.game.moudle.arena.msg;

public class BuffArenaFightMsg extends ArenaFightMsg {

	private int buffId;
	/** 1加上buff  -1消除buff  2作用	 */
	private int buffType;
	
	private String str;

	public int getBuffId() {
		return buffId;
	}

	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}


	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getBuffType() {
		return buffType;
	}

	public void setBuffType(int buffType) {
		this.buffType = buffType;
	}
	
	
}
