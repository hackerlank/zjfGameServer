package byCodeGame.game.navigation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import byCodeGame.game.module.register.action.RegisterAction;

/**
 * 通信指令导航
 * 
 */
public class Navigation {

	private static RegisterAction registerAction;

	// 导航集合
	public static Map<Short, ActionSupport> navigate = new ConcurrentHashMap<Short, ActionSupport>();

	// 初始化导航
	public static void init() {
		// 注册与登陆
		navigate.put(NavigationModule.REGISTER_ACTION, registerAction);
		
	}

	// 根据消息头获取导航
	public static ActionSupport getAction(short type) {
		return navigate.get(type);
	}

	public static void setRegisterAction(RegisterAction registerAction) {
		Navigation.registerAction = registerAction;
	}

}
