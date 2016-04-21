package byCodeGame.game.moudle.pub.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface PubService {
	/**
	 * 单次会谈功能
	 * 
	 * @param role
	 * @param is
	 * @param currencyType 1：战功，2：金币
	 * @return
	 * @author wcy
	 */
	public Message singleTalk(Role role, IoSession is, byte currencyType);

	/**
	 * 携带武将扩充功能
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message pubExpandHero(Role role);

	/**
	 * 酒馆登用功能
	 * 
	 * @param role
	 * @param id 武将ID
	 * @return
	 * @author xjd
	 */
	public Message pubUsedHero(Role role, int id);

	/**
	 * 酒馆 武将离队功能
	 * 
	 * @param role
	 * @param id 武将id
	 * @return
	 * @author xjd
	 */
	public Message pubHeroDequeue(Role role, int id);

	/**
	 * 获取玩家当前酒馆信息
	 * 
	 * @return
	 * @author xjd
	 */
	public Message getPubInfo(Role role);

	/**
	 * 连续会谈
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message multipleTalk(Role role, IoSession is, byte currencyType);

	/**
	 * 进入酒馆
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message enterPub(Role role);

	/**
	 * 兑换英雄
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message mileConvertHero(Role role, IoSession is, int heroId);

	/**
	 * 显示本周兑换英雄
	 * 
	 * @param role
	 * @param is
	 * @return
	 * @author wcy
	 */
	public Message showPubWeekHeros(Role role, IoSession is);

	/**
	 * 酿酒
	 * 
	 * @param role
	 * @param num
	 * @return
	 * @author wcy
	 */
	public Message makeWine(Role role, int num, IoSession is);

	/**
	 * 重置酒桌武将
	 * 
	 * @author wcy
	 */
	public void resetDeskHero(Role role);

	/**
	 * 重置加速次数
	 * 
	 * @param roleId
	 * @author wcy
	 */
	public void resetTalkSpeedUpTimes(Role role);

	/**
	 * 重置免费换一批宴请武将次数
	 * 
	 * @param roleId
	 * @author wcy
	 */
	public void resetFreeChangeDeskHeroTimes(Role role);

	/**
	 * 酒桌换一批武将
	 * 
	 * @param roleId
	 * @param deskIndex
	 * @author wcy
	 */
	public Message changeDeskHeroByDeskNum(Role role, byte deskIndex);

	/**
	 * 寻访
	 * 
	 * @param role
	 * @param mapId
	 * @return
	 * @author xjd
	 */
	public Message visitMap(Role role, int mapId, int heroId);

	/**
	 * 显示酒桌
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message showWineDesk(Role role);

	/**
	 * 显示酿酒界面
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message showMakeWine(Role role);

	/**
	 * 宴请
	 * 
	 * @param role
	 * @param deskNum（0，1，2）
	 * @return
	 * @author wcy
	 */
	public Message drinkWine(Role role, byte deskNum);

	/**
	 * 领酒
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message getWine(Role role, IoSession is);

	/**
	 * 获取寻访奖励
	 * 
	 * @param role
	 * @param id
	 * @return
	 */
	public Message getVisitAward(Role role, int id, IoSession is);

	/**
	 * 获取寻访城市信息
	 * 
	 * @param role
	 * @return
	 */
	public Message getMapInfo(Role role);

	/**
	 * 初始化寻访地图数据
	 * 
	 * @param role
	 * @author xjd
	 */
	public void initPubMap();

	/**
	 * 刷新领酒时间和现在可领酒数量
	 * 
	 * @param role
	 * @param nowTime
	 * @author wcy
	 */
	public void refreshGetWines(Role role, long nowTime);
	
	/**
	 * 解锁酒桌
	 * @param role
	 * @author wcy
	 */
	public Message unlockWineDesk(Role role,byte deskIndex);
	
	/**
	 * 初始化酒馆英雄
	 * @param role
	 * @author wcy
	 */
	public void initPubHero(Role role);
	
	/**
	 * 酒馆升级加新武将
	 * @param role
	 * @author wcy
	 */
	public void pubLvUpAddHero(Role role,int lv);
	
	
	public Message speedUpFreeGoldTalkTime(Role role);
	
	/**
	 * 初始化酒馆
	 * @param role
	 * @author wcy
	 */
	public void initPub(Role role);
	
}
