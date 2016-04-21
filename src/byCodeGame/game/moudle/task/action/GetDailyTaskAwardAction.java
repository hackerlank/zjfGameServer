package byCodeGame.game.moudle.task.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetDailyTaskAwardAction implements ActionSupport{
	private TaskService taskService;
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int id = message.getInt();
		
		msg = taskService.getDailyTaskAward(role, id);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
