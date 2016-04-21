package byCodeGame.game.moudle.server.service;

import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.MineFarm;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.WorldArmy;

public interface ServerService {
	/**
	 * 服务器初始化
	 * 
	 * @author wcy 2015年12月28日
	 */
	public void serverInit();

	/**
	 * 调度器每天调度
	 * 
	 * @author xjd
	 */
	public void serverChange();
	
	/**
	 * 
	 * 
	 * @author wcy 2015年12月28日
	 */
	public void refreshWorldMarket(int nowTime);
	
	/**
	 * 记录世界大战记录 玩家信息
	 */
	public void saveHistoryRole(byte type,WorldArmy army,String uuid);
	
	/**
	 * 记录世界大战记录 玩家信息
	 * 重载方法
	 */
	public void saveHistoryRole(byte type,Role role,City city);
	
	/**
	 * 记录世界大战记录 玩家信息
	 * 重载方法
	 */
	public void saveHistoryRole(byte type,Role role,MineFarm mineFarm,WorldArmy worldArmy,String uuid);

	/**
	 * 记录世界大战记录 玩家信息
	 * 城墙建筑
	 */
	public void saveHistoryDefRole(byte type,WorldArmy army,String name);
	
	/**
	 * 记录世界大战记录 公会信息
	 * @param type
	 * @param army
	 */
	public void saveHistoryLegion(byte type,WorldArmy army);
	
	/**
	 * 记录世界大战记录 公会信息
	 * 城墙建筑
	 * @param type
	 * @param army
	 * @param name
	 * 
	 */
	public void saveHistoryLegion(byte type,WorldArmy army,String name);
	
	/**
	 * 记录世界大战记录 公会信息
	 * 重载方法
	 * @param type
	 * @param army
	 */
	public void saveHistoryLegion(byte type,City city);
	
	/**
	 * 记录世界大战记录	势力信息
	 * @param type
	 * @param city
	 */
	public void saveHistoryService(byte type,City city);
}
