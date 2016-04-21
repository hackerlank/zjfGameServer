package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.MailConverter;
import byCodeGame.game.db.dao.MailDao;
import byCodeGame.game.entity.bo.Mail;

public class MailDaoImpl extends DataAccess implements MailDao {
	
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public int insertMail(Mail mail){
		int id = 0;
		final String sql = "insert into mail(id,sendName,targetName,type,title,context,attached,checked,time)"
				+ "values(?,?,?,?,?,?,?,?,?)";
		try {
			Connection conn = dataSource.getConnection();
			id = super.insert(sql, new IntegerConverter() , conn,null,mail.getSendName(),mail.getTargetName(),
					mail.getType(),mail.getTitle(),mail.getContext(),mail.getAttached(),mail.getChecked(),
					mail.getTime());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public void updateMail(Mail mail){
		final String sql = "update mail set sendName=?,targetName=?,type=?,title=?,context=?,attached=?,"
				+ "checked=? where id =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,mail.getSendName(),mail.getTargetName(),mail.getType(),mail.getTitle(),
					mail.getContext(),mail.getAttached(),mail.getChecked(),mail.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeMail(int mailId){
		String sql = "delete from mail where id = ?";
		try {
			Connection conn = dataSource.getConnection();
			super.delete(sql, conn, mailId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Mail> getAllMailById(String targetName){
		String sql = "select * from mail where targetName=?";
		try {
			Connection conn = dataSource.getConnection();
			List<Mail> result = super.queryForList(sql, new MailConverter() ,conn,targetName);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Mail getiMailById(int id) {
		String sql = "select * from mail where id=?";
		try {
			Connection conn = dataSource.getConnection();
			Mail result =super.queryForObject(sql, new MailConverter(), conn, id);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
