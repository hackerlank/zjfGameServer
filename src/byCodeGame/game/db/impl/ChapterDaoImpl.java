package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.ChapterConverter;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.dao.ChapterDao;
import byCodeGame.game.entity.bo.Chapter;

public class ChapterDaoImpl extends DataAccess implements ChapterDao {

	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insertChapter(Chapter chapter, Connection conn) {
		final String sql = "insert into chapter(roleId,nowChapterId,star,alreadyGetAward)"
				+ "values(?,?,?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter() , conn,chapter.getRoleId(),chapter.getNowChapterId(),
					chapter.getStar(),chapter.getAlreadyGetAward());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateChapter(Chapter chapter) {
		final String sql = "update chapter set nowChapterId=?,star=?,alreadyGetAward=?"
				+ " where roleId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,chapter.getNowChapterId(),chapter.getStar(),chapter.getAlreadyGetAward(),chapter.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Chapter getChapter(int roleId) {
		final String sql = "select * from chapter where roleId=? ";
		try {
			Connection conn = dataSource.getConnection();
			Chapter result = super.queryForObject(sql, new ChapterConverter() , conn, roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
