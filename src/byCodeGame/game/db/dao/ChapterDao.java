package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Chapter;


public interface ChapterDao {

	public void insertChapter(Chapter chapter,Connection conn);
	
	public void updateChapter(Chapter chapter);
	
	public Chapter getChapter(int roleId);
}
