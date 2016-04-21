package byCodeGame.game.entity.po;

/**
 * 建筑队列数据类
 * @author 王君辉
 *
 */

public class RoleScienceQueue {
	/** 队列ID	 */
	private int id;
	/** 时间	 */
	private int time;
	/**	科技类型  */
	private byte type;
	/** 当前科技队列是否开启 0开启 1关闭	 */
	private byte open;
	/** 上一次更新时间	 */
	private long lastUpTime;
	
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public byte getOpen() {
		return open;
	}
	public void setOpen(byte open) {
		this.open = open;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getLastUpTime() {
		return lastUpTime;
	}
	public void setLastUpTime(long lastUpTime) {
		this.lastUpTime = lastUpTime;
	}
}
