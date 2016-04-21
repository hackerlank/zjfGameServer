package byCodeGame.game.moudle.prop.service;

import java.sql.Connection;
import java.util.Collection;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface PropService {

	/**
	 * 角色物品数据登录处理
	 * 
	 * @param role
	 */
	public void propDataLoginHandle(Role role);

	/**
	 * 角色物品数据下线处理
	 * 
	 * @param role
	 */
	public void propDataOffLineHandle(Role role);

	/**
	 * 获取道具列表
	 * 
	 * @param role
	 * @return
	 */
	public Message getPropList(Role role);

	/**
	 * 使用道具
	 * 
	 * @param role
	 * @param propId
	 * @param num
	 * @return
	 */
	public Message useProp(Role role, int propId, short num, IoSession is);

	/**
	 * 通知客户端增加道具
	 * 
	 * @param prop
	 * @param is
	 */
	public void addProp(Prop prop, IoSession is);

	/**
	 * 通知客户端增加道具</br> 方法中加入背包,也就是说调用这个方法之前需要判定背包是否足够或者背包中是否有相同的消耗品
	 * 
	 * @param itemId
	 * @param num
	 * @param is
	 */
	public void addProp(Role role, int itemId, byte type, int num, IoSession is);

	/**
	 * 装备道具
	 * 
	 * @param role
	 * @param heroId 英雄ID
	 * @param slotId 装备位置
	 * @param propId 道具ID
	 * @return
	 */
	public Message equipProp(Role role, int heroId, byte slotId, int propId);

	/**
	 * 卸载道具
	 * 
	 * @param role
	 * @param heroId
	 * @param slotId
	 * @param propId
	 * @return
	 */
	public Message uninstallEquip(Role role, int heroId, byte slotId, int propId);

	/**
	 * 初始道具
	 * 
	 * @param role
	 */
	public void initialProp(Role role, Connection conn);

//	/**
//	 * 一键换装
//	 * 
//	 * @param role
//	 * @param heroId1
//	 * @param heroId2
//	 * @return
//	 */
//	public Message quickDress(Role role, int heroId1, int heroId2);

	/**
	 * 单个装互换
	 * 
	 * @param role
	 * @param heroId1
	 * @param heroId2
	 * @param slot
	 * @return
	 */
	public Message singleDress(Role role, int heroId1, int heroId2, byte slot);

	/**
	 * 强化
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message strengthenEquip(IoSession is, Role role, int id, int num);

	/**
	 * 炼化装备
	 * 
	 * @return
	 */
	public Message recoveryEquip(Role role, String id, IoSession is);

	/***
	 * 精炼装备
	 * 
	 * @param role
	 * @param id
	 * @return
	 */
	public Message refineEquip(Role role, int id);

	public Message changePrefix(Role role, int id);

	/**
	 * 扩充玩家背包
	 * 
	 * @param role
	 * @param number
	 * @return
	 * @author xjd
	 */
	public Message addMaxBagNum(Role role, int number);

	// /**
	// * 获取剩余空闲的背包格子数</br>
	// * <font color="red">注意：当获得的物品为装备时才能使用此方法</font>
	// * @param role
	// * @return
	// */
	// public int getEmptyBackPack(Role role);

	/**
	 * 检查所要添加的消耗品背包内是否存在
	 * 
	 * @param role
	 * @param item
	 * @return
	 */
	public boolean checkItemInBackPack(Role role, int itemId);

	/**
	 * 背包有同种消耗品增加数量</br>
	 * 
	 * @param role
	 * @param itemId
	 * @param num
	 */
	public int sameItemAddNum(Role role, int itemId, int num);

	/**
	 * 自动脱下装备 单件仅在武将离队中使用
	 * 
	 * @param role
	 * @param heroId
	 * @param slotId
	 * @param propId
	 */
	public boolean uninstallEquipAuto(Role role, int heroId, Collection<Integer> set);

	/**
	 * 锻造铁锭
	 * 
	 * @param role
	 * @param ironOre
	 * @param is
	 * @return
	 * @author wcy
	 */
	public Message makeIronIngot(Role role, int oreNum, IoSession is);

	/**
	 * 显示炼铁界面
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message showMakeIron(Role role);

	/**
	 * 显示强化
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message showStrengthen(Role role, int equipId);
	
	/**
	 * 显示加了铁块后的新强化成功率
	 * @param role
	 * @param equipId
	 * @param ironNum
	 * @return
	 * @author wcy 2016年1月13日
	 */
	public Message showAddIronStrengthen(Role role,int equipId,int ironNum);

	/**
	 * 刷新缓存池铁定数量
	 * 
	 * @param role
	 * @param nowTime
	 * @author wcy
	 */
	public void refreshGetIron(Role role, long nowTime);

	/**
	 * 领铁锭
	 * 
	 * @param role
	 * @param is
	 * @return
	 * @author wcy
	 */
	public Message getIron(Role role, IoSession is);

	/**
	 * 获取寻宝奖励
	 * 
	 * @param role
	 * @param id
	 * @param is
	 * @return
	 * @author wcy
	 */
	public Message getVisitTreasureAward(Role role, int id, IoSession is);

	/**
	 * 寻宝
	 * 
	 * @param role
	 * @param heroId
	 * @return
	 * @author wcy
	 */
	public Message visitTreasure(Role role, int heroId);

	/**
	 * 显示装备
	 * 
	 * @param role
	 * @param heroId
	 * @return
	 * @author wcy
	 */
	public Message showEquip(Role role);

	/**
	 * 
	 * @param prop
	 * @return
	 * @author wcy
	 */
	public void initPropFightValue(Prop prop);

	/**
	 * 一键换装
	 * 
	 * @param role
	 * @param heroId
	 * @return
	 * @author wcy
	 */
	public Message quickDress(Role role, int heroId);

	/**
	 * 初始化锻造坊
	 * @param role
	 * @author wcy
	 */
	public void initForge(Role role);
}
