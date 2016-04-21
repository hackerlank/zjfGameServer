package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.CityConverter;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.MineFarmConverter;
import byCodeGame.game.db.convert.RoleCityConverter;
import byCodeGame.game.db.dao.CityDao;
import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.MineFarm;
import byCodeGame.game.entity.bo.RoleCity;

public class CityDaoImpl extends DataAccess implements CityDao {

	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Map<Integer , City> getAllCity() {
		final String sql = "select * from city";
		try {
			Connection conn = dataSource.getConnection();
			List<City> result = super.queryForList(sql, new CityConverter() , conn);
			Map<Integer, City> temp = new ConcurrentHashMap<Integer, City>();
			for(City city :result)
			{
				temp.put(city.getCityId(), city);
			}
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public void insertCity(City city) {
		final String sql = "insert into city (cityId,roleIds,cityOw,nation,defInfo,lazyInfo,coreDefInfo,contribute) values(?,?,?,?,?,?,?,?)";
		try {
			Connection conn = dataSource.getConnection();
			super.insert(sql, new IntegerConverter() , conn,city.getCityId(),city.getRoleIds(),
					city.getCityOw(),city.getNation(),city.getDefInfo(),city.getLazyInfo(),city.getCoreDefInfo(),city.getContribute());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateCity(City city) {
		final String sql = "update city set roleIds=?,"
				+ "cityOw=?,nation=? ,legionId=? ,defInfo=?,lazyInfo=?,coreDefInfo=?,contribute=?"
				+ " where cityId =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, city.getRoleIds(),city.getCityOw(),city.getNation(),
					city.getLegionId(),city.getDefInfo(),city.getLazyInfo(),city.getCoreDefInfo(),city.getContribute(),
					city.getCityId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<RoleCity> getListRoleCity() {
		final String sql = "select * from rolecity";
		try {
			Connection conn = dataSource.getConnection();
			List<RoleCity> result = super.queryForList(sql, new RoleCityConverter() , conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<MineFarm> getListMineFarm() {
		final String sql = "select * from minefarm";
		try {
			Connection conn = dataSource.getConnection();
			List<MineFarm> result = super.queryForList(sql, new MineFarmConverter() , conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public void insertRoleCity(RoleCity roleCity,Connection conn) {
		final String sql = "insert into rolecity(roleId,cityId,mapKey,myLordRoleId,vassal) values(?,?,?,?,?)";
		try {
			super.insertNotCloseConn(sql, new IntegerConverter() , conn,roleCity.getRoleId(),roleCity.getCityId(),roleCity.getMapKey(),
					roleCity.getMyLordRoleId(),roleCity.getVassal());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertMineFarm(MineFarm mineFarm) {
		final String sql = "insert into minefarm (cityId,buildingID,roleId,starTime) values(?,?,?,?)";
		try {
			Connection conn = dataSource.getConnection();
			super.insert(sql, new IntegerConverter() , conn,mineFarm.getCityId(),mineFarm.getBuildingID(),
					mineFarm.getRoleId(),mineFarm.getStarTime());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public RoleCity getRoleCity(int roleId) {
		final String sql = "select * from rolecity where roleId=? ";
		try {
			Connection conn = dataSource.getConnection();
			RoleCity result = super.queryForObject(sql, new RoleCityConverter() , conn, roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateRoleCity(RoleCity roleCity) {
		final String sql = "update rolecity set cityId=?,mapKey=?,"
				+ "myLordRoleId=?,vassal=?"
				+ " where roleId =?";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,roleCity.getCityId(),roleCity.getMapKey(),
					roleCity.getMyLordRoleId(),roleCity.getVassal(),
					roleCity.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateMineFarm(MineFarm mineFarm) {
		final String sql = "update minefarm set roleId=?,starTime=?,worldArmyId=?,doubleP=?"
				+ " where buildingID = ?";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn,mineFarm.getRoleId(),mineFarm.getStarTime(),
					mineFarm.getWorldArmyId(),mineFarm.getDoubleP(),
					mineFarm.getBuildingID());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
