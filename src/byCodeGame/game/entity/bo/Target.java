package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;




import byCodeGame.game.entity.po.TargetBean;

/**
 * 目标类
 * @author xjd
 *
 */
public class Target {
	private int roleId;
	/** 阶段			*/
	private int stage;
	/** 当前所有目标	*/
	@SuppressWarnings("unused")
	private String allTarget;
	/** 当前所有目标实体*/
	private Map<Integer, TargetBean> allTargetMap = new HashMap<Integer, TargetBean>();
	/** 是否领取首冲活动*/
	private byte firstRecharge;
	
	
	private boolean isChange;
	
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public Map<Integer, TargetBean> getAllTargetMap() {
		return allTargetMap;
	}
	public void setAllTargetMap(Map<Integer, TargetBean> allTargetMap) {
		this.allTargetMap = allTargetMap;
	}
	
	public String getAllTarget() {
		StringBuilder sb = new StringBuilder();
		for(TargetBean x : this.allTargetMap.values())
		{
			sb.append(x.getId()).append("-");
			sb.append(x.getNowStuts()).append("-");
			sb.append(x.getIsComplete()).append("]");
		}
		return sb.toString();
	}
	public void setAllTarget(String allTarget) {
		String[] strs = allTarget.split("]");
		for(String str : strs)
		{
			String[] temp = str.split("-");
			TargetBean bean = new TargetBean(temp);
			this.allTargetMap.put(bean.getId(), bean);
		}
		this.allTarget = allTarget;
	}
	public boolean isChange() {
		return isChange;
	}
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}
	public byte getFirstRecharge() {
		return firstRecharge;
	}
	public void setFirstRecharge(byte firstRecharge) {
		this.firstRecharge = firstRecharge;
	} 
}
