package byCodeGame.game.navigation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
//import byCodeGame.game.moudle.raid.action.ChangeHeroAction;
//import byCodeGame.game.moudle.raid.action.CreateNewRoomAction;
//import byCodeGame.game.moudle.raid.action.GetLobbyInfoAction;
//import byCodeGame.game.moudle.raid.action.GetRoomInfoAction;
//import byCodeGame.game.moudle.raid.action.JoinRoomAction;
//import byCodeGame.game.moudle.raid.action.KickRoleAction;
//import byCodeGame.game.moudle.raid.action.QuitRoomAction;
//import byCodeGame.game.moudle.raid.action.ReadyBattleAction;
//import byCodeGame.game.moudle.raid.action.SaveRoomRoleFormationAction;
//import byCodeGame.game.moudle.raid.action.SetLoadingProgressAction;
//import byCodeGame.game.moudle.raid.action.SetRoleStatusAction;
//import byCodeGame.game.moudle.raid.action.StartBattleAction;
//import byCodeGame.game.moudle.raid.action.UpdateLoadingAction;

/**
 * 通信指令导航
 * 
 */
public class Navigation {

	
	// 导航集合
	public static Map<Short, ActionSupport> navigate = new ConcurrentHashMap<Short, ActionSupport>();

	// 初始化导航
	public static void init() {

		
	}

	// 根据消息头获取导航
	public static ActionSupport getAction(short type) {
		return navigate.get(type);
	}

	

}
