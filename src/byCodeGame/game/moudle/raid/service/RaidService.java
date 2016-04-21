package byCodeGame.game.moudle.raid.service;

import byCodeGame.game.entity.bo.Role;
//import byCodeGame.game.entity.po.RoomData;
import byCodeGame.game.remote.Message;

public interface RaidService {
	/** 战役大厅初始化	
	 * 	@author xjd
	 * 
	 */
	public void initBattleLobby();
	
	/**
	 * 获取大厅信息
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getLobbyInfo();
	
	/**
	 * 创建新房间
	 * @param role
	 * @param raidId
	 * @return
	 * @author xjd
	 * 
	 */
	public Message creatNewRoom(Role role , int raidId);
	
	/**
	 * 加入房间间
	 * @param role
	 * @param uuid
	 * @return
	 * @author xjd
	 */
	public Message joinRoom(Role role , String uuid);
	
	/**
	 * 清除玩家在房间中的所有信息
	 * @param role
	 */
	public void clearRoleFromRoomData(Role role);
	
	/**
	 * 更新房间信息
	 * @param role
	 * @author xjd
	 */
//	public void updataRoomInfo(RoomData roomData);
	
	/**
	 * 获取房间信息
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getRoomInfo(Role role);
	
	/***
	 * 退出房间
	 * @return
	 * @param role
	 * @author xjd
	 * 
	 */
	public Message quitRoom(Role role);
	
	/**
	 * 踢出玩家
	 * @param index 目标玩家的下标
	 * @return
	 * @author xjd
	 */
	public Message kickRole(Role role ,int index);
	
	/**
	 * 设置上阵武将
	 * @param role
	 * @param useingHeroID
	 * @param canChangeId
	 * @param canChangeId2
	 * @return
	 * @author xjd
	 */
	public Message saveRoomRoleFormation(Role role , int useingHeroID ,
			int canChangeId ,int canChangeId2);
	
	/**
	 * 准备完毕 / 取消准备
	 * @param role
	 * @param status
	 * @return
	 * @author xjd
	 */
	public Message setRoleStatus(Role role , byte status);
	
	/***
	 * 开始游戏
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message startBattle(Role role);
	
	/**
	 * 设置玩家加载进度
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message setLoadingProgress(Role role , double progress);
	
	/***
	 * 更新其他玩家加载进度
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message updateLoadingProgress(Role role);
	
}
