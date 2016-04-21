package byCodeGame.game.moudle.city.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.MineFarm;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.WorldArmy;
import byCodeGame.game.entity.po.WorldMarch;
import byCodeGame.game.remote.Message;

/**
 * 2015/4/28
 * 
 * @author shao
 *
 */
public interface CityService {

	/**
	 * 城市初始化(服务器启动时启用)
	 */
	public void CityInit();

	/**
	 * 起服的时候转化数据库信息 并且检查是否有新的城市出现
	 * 
	 */
	public void initWorldCity();
	
	/**
	 * 初始化矿信息
	 * new
	 */
	public void initMineFram();

	/**
	 * 获取所有世界阵型数据
	 * 
	 * @return
	 * @author xjd
	 */
	public Message getAllWorldFormation(Role role);

	/**
	 * 保存世界阵型
	 * 
	 * @param role
	 * @param useFormationID
	 * @param key
	 * @param heroId
	 * @return
	 * @author xjd
	 */
	public Message saveWorldFormation(Role role, int useFormationID, byte key, int heroId);

	/**
	 * 布防（更换驻守城门）
	 * @param armyId
	 * @param id
	 * @return
	 * @author xjd
	 */
	public Message changeDefInfo(Role role,int cityId,String id,int newId);
	
	/**
	 * 城主布防只有城主可用排序
	 * @param role
	 * @param id1 旧位置
	 * @param id2  新位置
	 * @return
	 */
	public Message changeCoreInfo(int cityId ,Role role ,int id1 ,int id2);
	
	/***
	 * 加入护都府
	 * @param cityId
	 * @param worldArmyId
	 * @return
	 */
	public Message joinCoreInfo(int cityId , String worldArmyId ,Role role);
	
	/**
	 * 离开护都府
	 * @param role
	 * @param worldArmyId
	 * @param cityId
	 * @return
	 */
	public Message leaveCoreInfo(Role role , String worldArmyId, int cityId);
	/***
	 * 获取英雄当前的位置
	 * 
	 * @return
	 */
	public Message getHeroLocation(Role role);

	/**
	 * 调度器用于处理行军到达的节点
	 * 
	 * @author xjd
	 */
	public void handlerArrive(WorldMarch worldMarch);
	
	/***
	 * 调度器处理战斗
	 * @param city
	 */
	public void handlerFight(City city);

	/**
	 * 矿藏和农田资源初始化
	 */
	public void MineFarmInit();

	/**
	 * 每半小时将缓存中的数据跟新到数据库 并且将只剩一小时时间就能自动收货资源的 矿藏和农田信息加入到缓存
	 */
	public void upMineFarmDB();

	/**
	 * 每5秒钟将剩一小时就会到期的数据进行判定是否到期 到期则自动发邮件提醒并收获资源
	 */
	public void upMineFarm();

	/**
	 * 获取世界大战记录
	 * 
	 * @param role
	 * @return
	 */
	public Message getHistoryInfo(Role role);

	/**
	 * 进城
	 * 
	 * @param cityId 城市id
	 * @return
	 */
	public Message goToCity(int cityId ,Role role);

	
	/**
	 * 进村（坑）
	 * @param role
	 * @return
	 * @author wcy 2016年3月4日
	 */
	public Message goToVillage(Role role,int cityId);
	/**
	 * 迁入
	 * 
	 * @param role 用户
	 * @param cityId 城市id
	 * @return
	 */
	public Message joinCity(Role role, Integer cityId);

	/**
	 * 获取世界信息
	 * 
	 * @param role 玩家信息
	 * @return
	 */
	public Message worldInfo(Role role);

	/**
	 * 获取护都府信息
	 * 
	 * @param cityId 城市id
	 * @param mapKey 封地所在位置
	 * @param role 玩家信息
	 * @return
	 */
	public Message getCoreInfo(Role role, int cityId);

	/**
	 * 获取封地资源信息 矿藏和农田
	 * 
	 * @param role
	 * @param cityId 城市id
	 * @return
	 */
	public Message getCityMineFarm(Role role, Integer cityId);
	
	/**
	 * 获取单一资源信息
	 * @param id
	 * @return
	 */
	public Message getMineFarmInfo(Role role,int cityId ,int buildId);

	/***
	 * 获取玩家占矿可用部队信息
	 * @param role
	 * @param cityId
	 * @return
	 * @author xjd
	 */
	public Message canUseArmyMine(Role role ,int cityId);
	
	/**
	 * 占领
	 * 
	 * @param role
	 * @param cityId
	 * @param type
	 * @param key
	 * @return
	 */
	public Message OccupyCityMineOrFarm(Role role,int cityId,int buildId ,String worldArmyId);

	/**
	 * 半点发送奖励
	 * @param mineFarm
	 */
	public void sendMineFramAward(MineFarm mineFarm);
	
	/**半点发送奖励*/
	public void sendMineFarmAward(Role role);
	
	/**
	 * 玩家对该位置执行疯狂模式(概率翻倍)
	 * 
	 * @param role
	 * @param cityId
	 * @param type
	 * @param key
	 * @return
	 */
	public Message crazyCityMineFarm(Role role, Integer cityId, Integer bulidId);

	/**
	 * 放弃占领
	 * 
	 * @param role
	 * @param cityId
	 * @param type
	 * @param key
	 * @return
	 */
	public Message dropCityMineOrFarm(Role role, Integer cityId, Integer buildId);

	/**
	 * 征服
	 * 
	 * @param role
	 * @param cityId
	 * @param type
	 * @param key
	 * @param roleId
	 * @return
	 */
	public Message Conquer(Role role, Integer cityId, Integer key, Integer roleId);

	/**
	 * 提升官阶
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message upGradeRank(Role role, IoSession is);

	/**
	 * 攻打（他势力）
	 * 
	 * @param role
	 * @param targetId
	 * @return
	 * @author xjd
	 */
	public Message pvpFight(Role role, int targetId);

	/**
	 * 获取官职信息
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getRankInfo(Role role);

	/**
	 * 领取俸禄
	 * 
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getRankAward(Role role);

	/**
	 * 获取属臣信息详情
	 * 
	 * @param role
	 * @return
	 */
	public Message getVassalInfo(Role role);

	/**
	 * 放弃不想要的属臣
	 * 
	 * @param role
	 * @return
	 */
	public Message dropVassal(Role role, int roleId);

	/**
	 * 2015-12-17 创建部队
	 * 
	 * @return
	 * @author wcy
	 */
	public Message marchWorldArmyFromHome(Role role, int worldFormationId,int heroCaptainId,String name);

	/**
	 * 2015-12-23 开始行军(不是从新手城)
	 * @param role
	 * @param id
	 * @param cityLinkStr
	 * @return
	 * @author wcy
	 */
	public Message marchWorldArmyFromCity(Role role,String id,String cityLinkStr);
	/**
	 * 2015-12-18 显示城市信息
	 * 
	 * @param role
	 * @param cityId
	 * @return
	 * @author wcy
	 */
	public Message showWorldCityInfo(Role role, int cityId);
	
	/**
	 * 加速行军
	 * @param role
	 * @param marchId
	 * @return
	 * @author wcy
	 */
	public Message speedUpWorldMarch(Role role,String marchId);
	
	/**
	 * 撤军
	 * @param role
	 * @param marchId
	 * @return
	 * @author wcy
	 */
	public Message retreatWorldMarch(Role role,String id);
	/**
	 * 显示加速行军
	 * @param role
	 * @param marchId
	 * @return
	 * @author wcy
	 */
	public Message showSpeedUpWorldMarch(Role role,String marchId);
	
	/**
	 * 显示驻守的城
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message showStayWorldArmyCity(Role role);
	
	/**
	 * 获得贡献值排名
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message getContributeRank(Role role,int cityId);
	
	/**
	 * 获得行军信息
	 * @param pathLinkStr
	 * @return
	 * @author wcy
	 */
	public Message showWorldMarchInfo(Role role,String id,String pathLinkStr);
	
	/**
	 * 
	 * @param role
	 * @param cityId
	 * @return
	 * @author wcy
	 */
	public Message showWorldCityWallInfo(Role role,int cityId,int wallId);
	
	/**
	 * 显示玩家情报
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message showWorldMarchRoleInfo(Role role);
	
	/**
	 * 显示一支行军的信息
	 * @param id
	 * @return
	 * @author wcy
	 */
	public Message showOneWorldMarch(String id);
	
	/**
	 * 显示据点
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message showStrongHold(Role role);
	
	/**
	 * 设置据点
	 * @param role
	 * @param cityId
	 * @return
	 * @author wcy
	 */
	public Message setStrongHold(Role role ,int cityId);
	
	/**
	 * 解散部队
	 * @param role
	 * @param id
	 * @return
	 * @author wcy
	 */
	public Message dismissArmy(Role role,String id);
	
	/***
	 * 获取本城玩家自己所有部队
	 * @param role
	 * @param cityId
	 * @return
	 * @author xjd
	 */
	public Message getAllArmyCity(Role role,int cityId);
	
	/**
	 * 离开防守墙
	 * @param role
	 * @param worldArmyId
	 * @param cityId
	 * @return
	 * @author wcy 2016年1月4日
	 */
	public Message leaveDefInfo(Role role, String worldArmyId, int cityId,int wallId);

	/**
	 * 开始编辑
	 * @param role
	 * @param id
	 * @return
	 */
	public Message startWorldFormation(Role role , int id);
	
	/**
	 * 修改部队阵型
	 * @param role
	 * @param armyId 部队编号
	 * @param key	  位置
	 * @param heroId 英雄
	 * @return
	 * @author xjd
	 */
	public Message changeWorldArmyFormation(Role role , int armyId,byte key ,int heroId
			,WorldArmy worldArmy);
	
	/**
	 * 获取单一世界大战阵型
	 * @param role
	 * @param id
	 * @return
	 */
	public Message getFormation(Role role , int id);
	
	/**
	 * 加速世界部队的复活时间
	 * @param role
	 * @param id
	 * @return
	 * @author wcy 2016年1月7日
	 */
	public Message speedUpWorldArmyAlive(Role role,String id);
	
	/**
	 * 显示复活部队
	 * @param role
	 * @param id
	 * @return
	 * @author wcy 2016年1月7日
	 */
	public Message showSpeedUpWorldArmyAlive(Role role,String id);
	
	/**
	 * 清理行军
	 * 
	 * @author wcy 2016年1月7日
	 */
	public void closeWorldMarch();
	
	/**
	 * 获得主公信息
	 * @param role
	 * @return
	 * @author wcy 2016年1月28日
	 */
	public Message getMyLordInfo(Role role);
	
	/**
	 * 清空进贡信息
	 * @param role
	 * @return
	 * @author wcy 2016年1月29日
	 */
	public Message clearPumHistory(Role role);
	
	/**
	 * 新手引导到达
	 * @param worldMarch
	 * @author wcy 2016年3月1日
	 */
	public void handlerGuideArrive(WorldMarch worldMarch);
	
	/**
	 * 
	 * @param type
	 * @return
	 * @author wcy 2016年3月1日
	 */
	public int getVillageIdByNation(byte type);
	
	
	/**
	 * 显示公会城市信息
	 * @param role
	 * @return
	 * @author wcy 2016年3月8日
	 */
	public Message showLegionCity(Role role);
}
