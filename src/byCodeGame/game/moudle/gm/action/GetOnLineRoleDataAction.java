package byCodeGame.game.moudle.gm.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.moudle.gm.service.GmService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.SysManager;

public class GetOnLineRoleDataAction implements ActionSupport {

	private GmService gmService;
	public void setGmService(GmService gmService) {
		this.gmService = gmService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		
		String gmpass = message.getString(session);
//		if(!gmpass.equals(SysManager.getGM_PWD())){
//			return;
//		}
		Message msg = gmService.getOnLineRoleData();
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

	

}
