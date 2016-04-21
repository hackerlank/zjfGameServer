package byCodeGame.game.moudle.mail.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Mail;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface MailService {
	/**
	 * 玩家发送邮件
	 * @param role
	 * @param targetName
	 * @param title
	 * @param context
	 * @param ioSession
	 * @return
	 */
	public Message sendMail(Role role , String targetName , 
			String title , String context , IoSession ioSession);
	/**
	 * 获取当前玩家所有的邮件
	 * @param role
	 * @param ioSession
	 * @return
	 */
	public Message getAllMail(Role role ,IoSession ioSession);
	/**
	 * 提取附件
	 * @param role
	 * @param id
	 * @param attached
	 * @param ioSession
	 * @return
	 */
	public Message takeItemMail(Role role ,int id ,IoSession ioSession );
	/**
	 * 删除邮件
	 * @param role 
	 * @param type
	 * @param id
	 * @param ioSession
	 * @return
	 */
	public Message DeleteMailById(Role role , byte type , int id ,IoSession ioSession);
	/**
	 * 设置邮件是否已读
	 * @param id
	 * @param ioSession
	 * @return
	 */
	public Message SetMailChecked(Role role ,int id,IoSession ioSession);
	/**
	 * 奖励邮件<br/>
	 * 服务器主动推送邮件<br/> 
	 * 邮件需要添加主题<br/>
	 * 邮件需要添加正文<br/>
	 * 
	 * 传入附件的格式为: 配置表ID,类型，数量;
	 * @param targetRole
	 * @param mail
	 * @author xjd
	 */
	public void sendSYSMail(Role targetRole , Mail mail);
	
	/**
	 * 系统邮件(不含奖励)
	 * 服务器主动推送邮件<br/> 
	 * 邮件需要添加主题<br/>
	 * 邮件需要添加正文<br/>
	 * @param targetRole
	 * @param mail
	 */
	public void sendSYSMail2(Role targetRole , Mail mail);
}
