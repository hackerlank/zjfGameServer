package byCodeGame.game.module.register.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.module.register.service.RegisterService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

/**
 * 注册
 * @author wcy 2016年4月29日
 *
 */
public class RegisterAction implements ActionSupport {
	private RegisterService registerService;

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	@Override
	public void execute(Message message, IoSession session) {
		String account = message.getString(session);
		String name= message.getString(session);
		
		message = registerService.register(account,name);
		if(message != null){
			session.write(message);
		}
	}

}
