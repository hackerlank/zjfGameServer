package byCodeGame.game.db.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.MineFarm;
import byCodeGame.game.entity.bo.RoleCity;

/**
 * 2015/4/28
 * @author shao
 */
public interface CityDao {
	
	/**
	 * 获取所有城市信息
	 * @return
	 * @author xjd
	 */
	public Map<Integer,City> getAllCity();
	
	/**
	 * 插入城市信息
	 * @param city
	 */
	public void insertCity(City city);
	
	/**
	 * 更新城市信息
	 * @param city
	 */
	public void updateCity(City city);
	
	/**
	 * 在程序启动时获取城市中玩家Id集合
	 */
	public List<RoleCity> getListRoleCity();
	
	/**
	 * 在程序启动时初始化矿藏农田信息
	 * @return
	 */
	public List<MineFarm> getListMineFarm();
	
	/**
	 * 玩家创建账号时封地信息初始化
	 * @param roleCity  玩家封地信息
	 * @param conn
	 */
	public void insertRoleCity(RoleCity roleCity,Connection conn);
	
	/**
	 * 程序启动时根据配置表初始化数据
	 * @param mineFarm
	 * @param conn
	 */
	public void insertMineFarm(MineFarm mineFarm);
	/**
	 * 玩家下线时更新玩家封地数据到数据库
	 * @param city
	 */
	public void updateRoleCity(RoleCity roleCity);

	/**
	 * 根据玩家id查询该封地的信息
	 * @param roleId
	 * @return
	 */
	public RoleCity getRoleCity(int roleId); 
	/**
	 * 在程序启动时获取城市中玩家Id集合
	 
	*public List<RoleCity> getListRoleCity();
	*/
	
	/**
	 * 修改矿藏农田资源点信息
	 * @param mineFarm
	 */
	public void updateMineFarm(MineFarm mineFarm);
	
	
}
