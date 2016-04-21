package byCodeGame.game.db.dao;

import java.sql.Connection;
import java.util.List;

import byCodeGame.game.entity.bo.Role;

public interface RoleDao {

	/**
	 * 新建用户
	 * @param role
	 */
	public int insertRole(Role role,Connection conn);
	
	/**
	 * 更新用户
	 * @param role
	 */
	public void updateRole(Role role);
	
	/**
	 * 根据ID获取Role用户
	 * @param id
	 * @return
	 */
	public Role getRoleById(int id);
	
	/**
	 * 根据游戏昵称获取用户
	 * @param name
	 * @return
	 */
	public Role getRoleByName(String name);
	
	public Role getRoleByAccount(String account);
	
	/**
	 * 获取所有的账号集合
	 * @return
	 */
	public List<String> getAllAccount();
	
	/**
	 * 获取所有的姓名集合
	 * @return
	 */
	public List<String> getAllName();
	
	/**
	 * 服务器数据缓存
	 */
	public void serverStartInit();
	
	/**
	 * 获得所有玩家
	 * @return
	 * @author wcy 2016年2月25日
	 */
	public List<Role> getAllRole();
}
