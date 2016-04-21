package byCodeGame.game.entity.bo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 拍卖物品
 * @author xjd
 *
 */
public class Auction {
	/** 编号			*/
	private String uuid;
	/** 玩家编号		*/
	private int roleId;
	/** 类型			*/
	private byte type;
	/** 物品编号		*/
	private int itemId;
	/** 数量			*/
	private int num;
	/** 开始时间		*/
	private int startTime;
	/** 拍卖的时间		*/
	private int passTime;
	/** 拍卖价格		*/
	private int cost;
	/** 是否处理		*/
	private byte isDispose;
	/** 是否可见		*/
	private boolean isCanSee;
	/** 锁			*/
	private Lock lock = new ReentrantLock();

	public Auction() {
	}
	
	
	
	public Auction(String uuid, int roleId, byte type, int itemId, int num,
			int startTime, int passTime, int cost) {
		super();
		this.uuid = uuid;
		this.roleId = roleId;
		this.type = type;
		this.itemId = itemId;
		this.num = num;
		this.startTime = startTime;
		this.passTime = passTime;
		this.cost = cost;
	}



	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getPassTime() {
		return passTime;
	}
	public void setPassTime(int passTime) {
		this.passTime = passTime;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public Lock getLock() {
		return lock;
	}
	public void setLock(Lock lock) {
		this.lock = lock;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public boolean isCanSee() {
		return isCanSee;
	}
	public void setCanSee(boolean isCanSee) {
		this.isCanSee = isCanSee;
	}
	public byte getIsDispose() {
		return isDispose;
	}
	public void setIsDispose(byte isDispose) {
		this.isDispose = isDispose;
	}
}
