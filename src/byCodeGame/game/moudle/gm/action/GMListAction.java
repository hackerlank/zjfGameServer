package byCodeGame.game.moudle.gm.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.gm.service.GmService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GMListAction implements ActionSupport {

	private GmService gmService;
	public void setGmService(GmService gmService) {
		this.gmService = gmService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		String gmpass = message.getString(session);
		if(!gmpass.equals("iH29VcAlXg8Y3bK0ikAn")){
		return;
		}
		String functionName=message.getString(session);
		int x=message.getInt();
		int y=message.getInt();
		int z=message.getInt();

		Message msg = gmService.GMList(role, functionName, x,y,z,session);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

	

}
