package byCodeGame.game.moudle.target.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.TargetBean;
import byCodeGame.game.remote.Message;

public interface TargetService {
	/** 获取目标信息		*/
	public Message getTargetInfo(Role role);
	
	/** 领取目标奖励		*/
	public Message getAwardTarget(Role role,int stage);
	
	/** 检查目标进度		*/
	public void checkLvTarget(Role role);
	
	/** 更新目标			*/
	public void upTargetInfo(TargetBean target ,Role role);
	
	
	/** 领取首冲活动		*/
	public Message getFirstRecharge(Role role);
	/** 领取连续登陆		*/
	public Message getCheckInAward(Role role);
	/** 获取活动信息		*/
	public Message getActiveInfo(int type ,Role role);
}
