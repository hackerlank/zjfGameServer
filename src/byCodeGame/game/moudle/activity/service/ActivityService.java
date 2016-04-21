package byCodeGame.game.moudle.activity.service;

import byCodeGame.game.entity.bo.Activity;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.ActivityMessage;
import byCodeGame.game.remote.Message;

/**
 * 
 * @author wcy 2016年4月19日
 *
 */
public interface ActivityService {
	/**
	 * 活动初始化
	 * 
	 * @author wcy 2016年4月19日
	 */
	void initActivity();
	/**
	 * 通知活动
	 * 
	 * @author wcy 2016年4月19日
	 */
	void noticeActivity(ActivityMessage message);
	
	/**
	 * 创建活动
	 * @param activityId 活动代号id
	 * @return
	 * @author wcy 2016年4月19日
	 */
	Activity createActivity(int activityId);
	
	/**
	 * 显示活动
	 * @param role
	 * @return
	 * @author wcy 2016年4月20日
	 */
	Message showActivity(Role role,int activityId);
}
