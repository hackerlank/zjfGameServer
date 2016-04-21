package byCodeGame.game.util.events;

import java.util.EventObject;
/**
 * 金币变化事件
 * @author xjd
 *
 */
public class GoldChangEvent extends EventObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int type = 1;
	private int value;
	private int roleId;
	
	public int getType() {
		return type;
	}
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public GoldChangEvent(Object obj , int value ,int roleId) {
		super(obj);
		this.setValue(value);
		this.setRoleId(roleId);
		
	}
	public GoldChangEvent(Object obj) {
		super(obj);
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
