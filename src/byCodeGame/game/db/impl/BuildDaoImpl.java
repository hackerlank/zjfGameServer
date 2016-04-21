package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.BuildConverter;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.dao.BuildDao;
import byCodeGame.game.entity.bo.Build;

public class BuildDaoImpl extends DataAccess implements BuildDao {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insertBuild(Build build, Connection conn) {
		final String sql = "insert into build(roleId,farmLv,houseLv,"
				+ "attachHero,levyStr,buildLv,inComeCache,buildLastIncomeTime,visitTreasureData,visitScienceData,incomeLastDayCache)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter(), conn, build.getRoleId(), build.getFarmLv(),
					build.getHouseLv(), build.getAttachHero(), build.getLevyStr(), build.getBuildLv(),
					build.getInComeCacheStr(), build.getBuildLastIncomeTimeStr(), build.getVisitTreasureData(),build.getVisitScienceData(),build.getIncomeLastDayCacheStr());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateBuild(Build build) {
		final String sql = "update build set farmLv=?,houseLv=?,attachHero=?,levyStr=?,"
				+ "buildLv=?,inComeCache=?,buildLastIncomeTime=?,visitTreasureData=?,visitScienceData=?,incomeLastDayCache=?" + " where roleId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, build.getFarmLv(), build.getHouseLv(), build.getAttachHero(), build.getLevyStr(),
					build.getBuildLv(), build.getInComeCacheStr(), build.getBuildLastIncomeTimeStr(),
					build.getVisitTreasureData(),build.getVisitScienceData(),build.getIncomeLastDayCacheStr(),build.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Build getBuild(int roleId) {
		final String sql = "select * from build where roleId=? ";
		try {
			Connection conn = dataSource.getConnection();
			Build result = super.queryForObject(sql, new BuildConverter(), conn, roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
