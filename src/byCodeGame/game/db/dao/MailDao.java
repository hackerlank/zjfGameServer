package byCodeGame.game.db.dao;

import java.util.List;

import byCodeGame.game.entity.bo.Mail;

public interface MailDao {

	public int insertMail(Mail mail);
	
	public void updateMail(Mail mail);
	
	public void removeMail(int mailId);
	
	/**
	 * 根据roleId获取所有的邮件
	 * @param receiveId
	 * @return
	 */
	public List<Mail> getAllMailById(String targetName);
	/**
	 * 根据邮件id获取邮件
	 * @param id
	 * @return
	 */
	public Mail getiMailById(int id);
}
