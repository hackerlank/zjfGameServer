package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.KeyConverter;
import byCodeGame.game.db.dao.KeyDao;
import byCodeGame.game.entity.Key;
import byCodeGame.game.entity.bo.Recharge;

public class KeyDaoImpl extends DataAccess implements KeyDao{
	private DataSource dataSource2;
	public void setDataSource2(DataSource dataSource) {
		this.dataSource2 = dataSource;
	}
	
	
	public Key getKey(String id) {
		String sql = "select * from privilege where id=?";
		try {
			Connection conn = dataSource2.getConnection();
			Key result =super.queryForObject(sql, new KeyConverter(), conn, id);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void updateKey(Key key) {
		String sql = "update  privilege set isUsed=?,useTime=? where id =?";
		try {
			Connection conn = dataSource2.getConnection();
			super.update(sql, conn,key.getIsUsed(),key.getUseTime(),key.getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void insterRecharge(Recharge recharge) {
		final String sql = "insert into recharge(account,orderNo,num,time,partnerId,serverId,roleId)"
				+ "values(?,?,?,?,?,?,?)";
		try {
			Connection conn = dataSource2.getConnection();
			super.insert(sql, new IntegerConverter(), conn,recharge.getAccount(),
					recharge.getOrderNo(),recharge.getNum(),recharge.getTime(),recharge.getPartnerId(),
					recharge.getServerId(),recharge.getRoleId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
