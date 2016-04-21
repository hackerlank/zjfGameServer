package byCodeGame.game.moudle.nation.service;


import byCodeGame.game.remote.Message;
import byCodeGame.game.entity.bo.Role;

public interface NationService {
	/**
	 * 设置国籍
	 * @param role 		账号信息
	 * @param nation	国籍ID
	 * @param session
	 * @return
	 */
	public Message setNation(Role role,byte nation);
	
	/***
	 * 获取当前服务器阵营人数
	 * @return
	 * @author xjd
	 */
	public Message getNumnation();
}
