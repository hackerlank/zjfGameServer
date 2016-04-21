package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;









import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.LegionConverter;
import byCodeGame.game.db.convert.StringConverter;
import byCodeGame.game.db.dao.LegionDao;
import byCodeGame.game.entity.bo.Legion;

public class LegionDaoImpl extends DataAccess implements LegionDao {
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int insertLegion(Legion legion){
		int id = 0;
		final String sql = "insert into legion(legionId,name,faceId,lv,maxPeopleNum,member,captainId,science,appointScience,nation,autoArgeeType,minLv,shortName)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			Connection conn = dataSource.getConnection();
			id = super.insert(sql, new IntegerConverter() , conn,null,legion.getName(),
					legion.getFaceId(),legion.getLv(),legion.getMaxPeopleNum(),legion.getMember(),
					legion.getCaptainId(),legion.getScience(),legion.getAppointScience(),legion.getNation(),
					legion.getAutoArgeeType(),legion.getMinLv(),legion.getShortName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public void updateLegion(Legion legion){
		final String sql = "update legion set name=?,faceId=?,lv=?,notice=?,science=?,maxPeopleNum=?,"
				+ "member=?,captainId=?,deputyCaptain=?,apply=?,appointScience=?,nation=?,"
				+ "autoArgeeType=?,minLv=?,history=?,cityId=?,lastCityTime=?,shortName=?"
				+ "where legionId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,legion.getName(),legion.getFaceId(),legion.getLv(),legion.getNotice(),
					legion.getScience(),legion.getMaxPeopleNum(),legion.getMember(),legion.getCaptainId(),
					legion.getDeputyCaptain(),legion.getApply(),legion.getAppointScience(),legion.getNation(),
					legion.getAutoArgeeType(),legion.getMinLv(),legion.getHistoryStr(),
					legion.getCityId(),legion.getLastCityTime(),legion.getShortName(),
					legion.getLegionId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Legion getLegionById(int id){
		return null;
	}

	public void removeLegion(int id){
		String sql = "delete from legion where legionId = ?";
		try {
			Connection conn = dataSource.getConnection();
			super.delete(sql, conn, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getAllLegionName(){
		String sql = "select name from legion";
		try {
			Connection conn = dataSource.getConnection();
			List<String> result = super.queryForList(sql, new StringConverter(),
					conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Legion> getAllLegion(){
		String sql = "select * from legion";
		try {
			Connection conn = dataSource.getConnection();
			List<Legion> result = super.queryForList(sql, new LegionConverter(),
					conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
