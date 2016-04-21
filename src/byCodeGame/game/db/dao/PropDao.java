package byCodeGame.game.db.dao;

import java.sql.Connection;
import java.util.List;

import byCodeGame.game.entity.bo.Prop;

public interface PropDao {

	public int insertProp(Prop prop);
	
	public int insertProp(Prop prop , Connection conn);
	
	public void updateProp(Prop prop);
	
	/**
	 * 删除道具
	 * @param propId
	 */
	public void removeProp(int propId);
	
	/**
	 * 获取用户所有的道具
	 * @param roleId
	 * @return
	 */
	public List<Prop> getAllPropByRoleId(int roleId);
	
}
