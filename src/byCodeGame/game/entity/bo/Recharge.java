package byCodeGame.game.entity.bo;

/**
 * 充值信息
 * @author xjd
 *
 */
public class Recharge {
	/**	账号				*/
	private String account;
	/** 订单号			*/
	private String orderNo;
	/** 数量				*/
	private int num;
	/** 时间				*/
	private String time;
	/** 合作平台号			*/
	private int partnerId;
	/** 服务器ID			*/
	private int serverId;
	/** 玩家ID			*/
	private int roleId;
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	
	
}
