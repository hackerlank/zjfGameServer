package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Chapter;

public class ChapterConverter implements ResultConverter<Chapter> {

	public Chapter convert(ResultSet rs) throws SQLException{
		Chapter chapter = new Chapter();
		chapter.setRoleId(rs.getInt("roleId"));
		chapter.setNowChapterId(rs.getInt("nowChapterId"));
		chapter.setStar(rs.getString("star"));
		chapter.setAlreadyGetAward(rs.getString("alreadyGetAward"));
		return chapter;
	}
	
}
