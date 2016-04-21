package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.ArenaConverter;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.ResultsConverter;
import byCodeGame.game.db.dao.ResultsDao;
import byCodeGame.game.entity.bo.Arena;
import cn.bycode.game.battle.data.ResultData;

public class ResultsDaoImpl extends DataAccess implements ResultsDao {

	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

//	public void insterArena(){
//		final String sql = "insert into results(uuid,time,winCamp,attPlayerId,defPlayerId,attPlayerIsNpc,defPlayerIsNpc,"
//				+ "report,attStars,attLost,defStars,defLost,fRound,attName,defName)"
//				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//		try {
//			Connection conn = dataSource.getConnection();
//			super.insert(sql, new IntegerConverter() , conn,1,"");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

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

	@Override
	public void insertResults(ResultData resultData) {
		final String sql = "insert into results(uuid,time,winCamp,attPlayerId,defPlayerId,attPlayerIsNpc,defPlayerIsNpc,"
				+ "report,attStars,attLost,defStars,defLost,fRound,attName,defName)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			Connection conn = dataSource.getConnection();
			super.insert(sql, new IntegerConverter() , conn,resultData.uuid,resultData.time,resultData.winCamp,
					resultData.attPlayerId,resultData.defPlayerId,resultData.attPlayerIsNpc,resultData.defPlayerIsNpc,resultData.report,
					resultData.attStars,resultData.attLost,resultData.defStars,resultData.defLost,resultData.fRound,resultData.attName,
					resultData.defName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultData getResults(String uuid) {
		String sql = "select * from results where uuid=?";
		try {
			Connection conn = dataSource.getConnection();
			ResultData resultData =super.queryForObject(sql, new ResultsConverter(), conn, uuid);
			return resultData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
