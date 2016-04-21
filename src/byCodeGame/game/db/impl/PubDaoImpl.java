package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.PubConverter;
import byCodeGame.game.db.dao.PubDao;
import byCodeGame.game.entity.bo.Pub;

public class PubDaoImpl extends DataAccess implements PubDao {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insertPub(Pub pub, Connection conn) {
		final String sql = "insert into pub(roleId,talksNumber,mapInfo,talkMile,recruitHeroNum,totalTalksNumber,totalTalksNumber2,freeMoneyStartTalkTime,freeGoldStartTalkTime,heroMoney,heroGold,"
				+ "visitData,desk,missHero)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			super.insertNotCloseConn(sql, new IntegerConverter(), conn, pub.getRoleId(), pub.getTalksNumber(),
					pub.getMapInfo(), pub.getTalkMile(), pub.getRecruitHeroNum(), pub.getTotalTalksNumber(),
					pub.getTotalTalksNumber2(), pub.getFreeMoneyStartTalkTime(), pub.getFreeGoldStartTalkTime(),
					pub.getHeroMoney(), pub.getHeroGold(), pub.getVisitData(), pub.getDesk(),pub.getMissHero());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updatePub(Pub pub) {
		final String sql = "update pub set talksNumber=?,mapInfo=?,talkMile=?,recruitHeroNum=?,totalTalksNumber=?,totalTalksNumber2=?,freeMoneyStartTalkTime=?,freeGoldStartTalkTime=?,"
				+ "heroMoney=?,heroGold=?,visitData=?,desk=?,missHero=?"
				+ " where roleId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, pub.getTalksNumber(), pub.getMapInfo(), pub.getTalkMile(), pub.getRecruitHeroNum(),
					pub.getTotalTalksNumber(), pub.getTotalTalksNumber2(), pub.getFreeMoneyStartTalkTime(),
					pub.getFreeGoldStartTalkTime(), pub.getHeroMoney(), pub.getHeroGold(), pub.getVisitData(),
					pub.getDesk(),pub.getMissHero(),pub.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Pub getPubByRoleId(int roleId) {
		String sql = "select * from pub where roleId=?";
		try {
			Connection conn = dataSource.getConnection();
			Pub result = super.queryForObject(sql, new PubConverter(), conn, roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
