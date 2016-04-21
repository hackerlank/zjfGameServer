package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Mail;


public class MailConverter implements ResultConverter<Mail> {
	
	public Mail convert(ResultSet rs) throws SQLException{
		Mail mail = new Mail();
		mail.setId(rs.getInt("id"));
		mail.setSendName(rs.getString("sendName"));
		mail.setTargetName(rs.getString("targetName"));
		mail.setType(rs.getByte("type"));
		mail.setTitle(rs.getString("title"));
		mail.setContext(rs.getString("context"));
		mail.setAttached(rs.getString("attached"));
		mail.setChecked(rs.getByte("checked"));
		mail.setTime(rs.getLong("time"));
		return mail;
	}
}
