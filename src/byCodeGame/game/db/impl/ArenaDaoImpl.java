package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.ArenaConverter;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.dao.ArenaDao;
import byCodeGame.game.entity.bo.Arena;

public class ArenaDaoImpl extends DataAccess implements ArenaDao {

	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insterArena(){
		final String sql = "insert into arena(id,lv1)"
				+ "values(?,?)";
		try {
			Connection conn = dataSource.getConnection();
			super.insertNotCloseConn(sql, new IntegerConverter() , conn,1,"");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	public void updateArena(Arena arena){
//		final String sql = "update arena set lv1=?,lv2=?,lv3=?,lv4=?,lv5=?,lv6=?"
//				+ " where id =? limit 1";
//		try {
//			Connection conn = dataSource.getConnection();
//			super.update(sql, conn,arena.getLv1(),arena.getLv2(),arena.getLv3(),arena.getLv4(),
//					arena.getLv5(),arena.getLv6(),arena.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public Arena getArena(){
		final String sql = "select * from arena limit 1 ";
		try {
			Connection conn = dataSource.getConnection();
			Arena result = super.queryForObject(sql, new ArenaConverter() , conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
