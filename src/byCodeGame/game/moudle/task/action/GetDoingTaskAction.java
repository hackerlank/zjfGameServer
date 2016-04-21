package byCodeGame.game.moudle.task.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetDoingTaskAction implements ActionSupport {

	private TaskService taskService;
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);

		Message msg = taskService.getDoningTask(role);
		
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}
	

}
