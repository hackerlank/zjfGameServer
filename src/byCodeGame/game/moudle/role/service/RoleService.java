package byCodeGame.game.moudle.role.service;



import java.util.List;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.MainCityConfig;
import byCodeGame.game.remote.Message;

public interface RoleService {

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public Role getRoleById(int id);

	public Role getRoleByName(String name);

	public Role getRoleByAccount(String account);
	
	/**
	 * id获取数据库Role数据
	 * 
	 * @param id
	 * @return
	 */
	public Role getDbRoleById(int id);

	/**
	 * 根据name获取数据库Role数据
	 * 
	 * @param name
	 * @return
	 */
	public Role getDbRoleByName(String name);

	/** 获取所有用户昵称 服务器启动时调用 */
	public void selectAllName();

	/** 获取所有用户登录账号 服务器启动时调用 */
	public void selectAllAccount();

	/**
	 * 增加玩家粮食
	 * 
	 * @param role
	 * @param value
	 */
	public void addRoleFood(Role role, int value);

	/**
	 * 增加玩家金钱
	 * 
	 * @param role
	 * @param value
	 */
	public void addRoleMoney(Role role, int value);

	/**
	 * 玩家获得军令
	 * 
	 * @param role
	 * @param num
	 */
	public void getArmyToken(Role role, int num);

	/**
	 * 增加玩家人口
	 * 
	 * @param role
	 * @param value
	 */
	public void addRolePopulation(Role role, int value);

	/**
	 * 增加玩家经验
	 * 
	 * @param role
	 * @param value
	 */
	public void addRoleExp(Role role, int value);

	/**
	 * 玩家升级(已废）
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message roleLvUp(Role role);
	
	public void roleLvUp(Role role,Message message);

	/**
	 * 增加玩家战功
	 * 
	 * @param role
	 * @param value
	 */
	public void addRoleExploit(Role role, int value);

	/**
	 * 设定玩家的名字
	 * 
	 * @param role
	 * @param name
	 * @return
	 * @author xjd
	 */
	public Message setRoleName(String name);
	
	/**
	 * 随机获取roleId
	 * 
	 * @return
	 */
	public int randowRole();

	/**
	 * 设定玩家头像
	 * 
	 * @param role
	 * @param faceId 头像编号
	 * @return
	 * @author xjd
	 * 
	 */
	public Message setRoleFaceId(Role role, byte faceId);

	/***
	 * 获得金币
	 * 
	 * @param role
	 * @param value
	 * @author xjd
	 */
	public void addRoleGold(Role role, int value);

	/**
	 * 获得声望
	 * 
	 * @param role
	 * @param value
	 * @author xjd
	 */
	public void addRolePrestige(Role role, int value);

	/**
	 * 获取玩家数据
	 * 
	 * @param role
	 * @author wcy
	 */
	public Message getRoleLvData(Role role);

	/**
	 * 引导标识变更
	 * 
	 * @param role
	 * @param type
	 * @param info
	 * @return
	 * @author xjd
	 */
	public Message setRoleLead(Role role, byte type, String info);

	/**
	 * 完成新建筑流程
	 * @param role
	 * @param key
	 * @return
	 * @author xjd
	 */
	public Message finishNewBuild(Role role , String key);
	
	/**
	 * 每日友好提示
	 * @param role
	 * @param type
	 * @return
	 * @author xjd
	 */
	public Message friendTip(Role role ,byte type);
	
	/**
	 * 刷新声望
	 * @param role
	 * @return
	 * @author wcy 2016年1月20日
	 */
	public void refreshGetPrestige(Role role,int nowTime);
	
	/**
	 * 获得所有玩家
	 * @return
	 * @author wcy 2016年2月25日
	 */
	public List<Role> getAllRole();
	
	public int getRoleLvUpNeedGold(int roleLv,MainCityConfig mainCityConfig) ;
}
