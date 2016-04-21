package byCodeGame.game.moudle.login.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.moudle.login.service.LoginService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetRoleDataAction implements ActionSupport {

	private LoginService loginService;
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		String account = message.getString(session) ;
		Message msg = loginService.getRoleData(account,session);

		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

}
