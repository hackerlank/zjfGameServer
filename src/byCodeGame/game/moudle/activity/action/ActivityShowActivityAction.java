package byCodeGame.game.moudle.activity.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.activity.service.ActivityService;
import byCodeGame.game.moudle.babel.service.BabelService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class ActivityShowActivityAction implements ActionSupport{
	private ActivityService activityService;
	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int activityId = message.getInt();
		msg = activityService.showActivity(role, activityId);
		
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
