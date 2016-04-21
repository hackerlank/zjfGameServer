package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.PropConverter;
import byCodeGame.game.db.dao.PropDao;
import byCodeGame.game.entity.bo.Prop;

public class PropDaoImpl extends DataAccess implements PropDao {

	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public int insertProp(Prop prop) {
		int id = 0;
		final String sql = "insert into prop(id,roleId,functionType,itemId,num,lv,slotId,prefixId)"
				+ "values(?,?,?,?,?,?,?,?)";
		try {
			Connection conn = dataSource.getConnection();
			id = super.insert(sql, new IntegerConverter() , conn,null,prop.getRoleId(),prop.getFunctionType(),
					prop.getItemId(),prop.getNum(),prop.getLv(),prop.getSlotId(),prop.getPrefixId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public int insertProp(Prop prop , Connection conn){
		int id = 0;
		final String sql = "insert into prop(id,roleId,functionType,itemId,num,lv,slotId,prefixId)"
				+ "values(?,?,?,?,?,?,?,?)";
		try {
			id = super.insertNotCloseConn(sql, new IntegerConverter() , conn,null,prop.getRoleId(),prop.getFunctionType(),
					prop.getItemId(),prop.getNum(),prop.getLv(),prop.getSlotId(),prop.getPrefixId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void updateProp(Prop prop) {
		final String sql = "update prop set roleId=?,functionType=?,itemId=?,num=?,lv=?,slotId=?,useId=?,prefixId=? where id =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,prop.getRoleId(),prop.getFunctionType(),prop.getItemId(),prop.getNum(),
					prop.getLv(),prop.getSlotId(),prop.getUseID(),prop.getPrefixId(),prop.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void removeProp(int propId) {
		String sql = "delete from prop where id = ?";
		try {
			Connection conn = dataSource.getConnection();
			super.delete(sql, conn, propId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Prop> getAllPropByRoleId(int roleId) {
		String sql = "select * from prop where roleId=?";
		try {
			Connection conn = dataSource.getConnection();
			List<Prop> result = super.queryForList(sql, new PropConverter() ,conn,roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
