package byCodeGame.game.moudle.chat.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface ChatService {

	/**
	 * 世界聊天
	 * @param role	账号信息
	 * @param type	 频道 
	 * @param context 文本
	 * @return
	 */
	public Message chat(Role role , byte messageType , String context,IoSession ioSession);
	/**
	 * 私聊
	 * @param role 账号信息
	 * @param type 频道
	 * @param context 文本
	 * @param ioSession 
	 * @param targartId 私聊目标id
	 * @return
	 */
	public Message privateChat(Role role , byte messageType , String context , IoSession ioSession , String targetName);
	/**
	 * 系统推送
	 * @param role
	 * @param type
	 * @param context
	 * @param ioSession
	 * @return
	 */
	public Message systemChat(Role role , byte messagetype , String context);
	
	/**
	 * 军团聊天
	 * @param role
	 * @param messageType
	 * @param context
	 * @param ioSession
	 * @return
	 */
	public Message legionChat(Role role , byte messageType , String context , IoSession ioSession);
	
	
	public Message countryChat(Role role , byte messageType , String context , IoSession ioSession);
//	/**
//	 * 世界聊天
//	 * @param role
//	 * @param msg
//	 * @return
//	 */
//	public Message worldChat(Role role ,byte type, String context);
	
	/**
	 * 检查是否发送系统消息
	 * @param type
	 * @param value
	 * @return
	 * @author wcy 2016年2月18日
	 */
	public boolean checkSystemChat(byte type,int value);
}
