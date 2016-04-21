package byCodeGame.game.moudle.inCome.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Build;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

/**
 * 经营服务类
 * 
 * @author xjd
 *
 */
public interface InComeService {

	/**
	 * 升级时检查有没新获得建筑
	 * 
	 * @param role
	 * @return
	 */
	public void checkGetBuild(Role role);

	/**
	 * 征收
	 * 
	 * @param type 征收类型 1民居 2农田 3兵营
	 * @return
	 */
	public Message levy(byte type, Role role, int heroId, byte levyType);

	/**
	 * 升级建筑(内部小建筑)
	 * 
	 * @param type 建筑类型
	 * @param buildId 建筑ID
	 * @return
	 */
	public Message upBuild(Role role, byte type, byte buildId);

	/**
	 * 重新计算征收值 用于建筑升级，角色升级等情况 此方法仅将收益加入缓存中并更新收益时间
	 * 
	 * @param role
	 * @param type 1民居 2农田
	 */
	public void virtualLevy(Role role, byte type);

	/**
	 * 获取征收数据
	 * 
	 * @param role
	 */
	public Message getIncomeData(Role role);

	/**
	 * 开启一个新农场&民居
	 * 
	 * @param type
	 * @param buildId
	 * @return
	 */
	public Message openNewBuild(Role role, byte type, byte buildId);

	/**
	 * 刷新建筑队列时间
	 * 
	 * @param role
	 * @param type 类型 1民居 2农田
	 */
	public void refreshBuildTime(Role role);

	/**
	 * 获取建筑数据
	 * 
	 * @param role
	 * @return
	 */
	public Message getBuildData(Role role, byte type);

	/**
	 * 获取建筑队列数据
	 * 
	 * @param role
	 * @return
	 */
	public Message getBuildQueueData(Role role);

	/**
	 * 清除队列数据
	 * 
	 * @param role
	 * @param id 建筑队列id
	 * @return
	 */
	public Message clearBuildQueueTime(Role role, byte id);

	/**
	 * 开启新的建筑队列
	 * 
	 * @param role
	 * @param type
	 * @return
	 */
	public Message openNewBuildQueue(Role role);

	/**
	 * 获取建筑每小时收入值</br> 如果是民居农田则为一次可以征收的值
	 * 
	 * @param role
	 * @param type
	 * @return
	 */
	public int getHourIncomeValue(Role role, byte type);

	/**
	 * 消耗粮草补满人口
	 * 
	 * @param role
	 * @param type
	 * @return
	 */
	public Message FillPopulation(Role role);

	/**
	 * 获取开启新建筑的元宝数量
	 * 
	 * @param role
	 * @return
	 */
	public Message getNeedGold(Role role);

	/**
	 * 配属武将
	 * 
	 * @param role
	 * @param heroId
	 * @param type
	 * @return
	 * @author xjd
	 */
	public Message attachHero(Role role, int heroId, byte type);

	/**
	 * 取消配属
	 * 
	 * @param role
	 * @param type
	 * @return
	 * @author xjd
	 */
	public Message cancelAttach(Role role, byte type);

	/**
	 * 领取征收的值
	 * 
	 * @param role
	 * @param type
	 * @param nums
	 * @return
	 * @author wcy
	 */
	public Message getResource(Role role, byte type, int nums);

	/**
	 * 获得简要建筑信息
	 * 
	 * @param role
	 * @param type
	 * @return
	 * @author wcy
	 */
	public Message getSimpleBuildData(Role role, byte type);

	/**
	 * 获取武将配属增益
	 * 
	 * @param heroId
	 * @param type
	 * @return
	 * @author xjd
	 */
	public Message getInfoAttach(Role role, int heroId, byte type);

	/**
	 * 获取武将征收增益
	 * 
	 * @param role
	 * @param heroId
	 * @param type
	 * @param action
	 * @return
	 * @author wcy
	 */
	public Message getInfoLevy(Role role, int heroId, byte type);

	/**
	 * 建筑类型升级
	 * 
	 * @return
	 * @author wcy
	 */
	public Message buildTypeLvUp(Role role, byte type);

	/***
	 * 获取新开建筑的怪物信息
	 * 
	 * @param type
	 * @param id
	 * @return
	 * @author xjd
	 */
	public Message getFightInfo(byte type, byte id);

	/**
	 * 获得增益后的cd时间
	 * 
	 * @param hero
	 * @param baseCdTime
	 * @param type (2农田，3居民，4酒馆)
	 * @return
	 * @author wcy
	 */
	public int getCdTime(Hero hero, int baseCdTime, byte type);

	/***
	 * 刷新派遣事件队列
	 * 
	 * @param role
	 * @param nowTime
	 */
	public void refreshLevyData(Role role, long nowTime);

	/**
	 * 得到酒馆会谈缩减的时间
	 * 
	 * @param hero
	 * @return
	 * @author wcy
	 */
	public int getPubTalkReduceDelta(Hero hero);

	/**
	 * 得到寻宝减少时间
	 * 
	 * @param hero
	 * @return
	 * @author wcy
	 */
	public int getVisitTreasureReduceTimeDelta(Hero hero);

	/**
	 * 得到道具强化的增益成功率
	 * 
	 * @param hero
	 * @return
	 * @author wcy
	 */
	public int getPropStrengthAddDelta(Hero hero);

	/**
	 * 获得建筑升级cd时间
	 * 
	 * @param build
	 * @param baseCdTime
	 * @return
	 * @author wcy
	 */
	public int getBuildLvUpCdTime(Build build, int baseCdTime);

	/**
	 * 显示加速征收
	 * 
	 * @param type
	 * @return
	 * @author wcy
	 */
	public Message showSpeedUpLevy(Role role, byte type);

	/**
	 * 自动检测配属武将体力
	 * 
	 * @author xjd
	 */
	public void checkHeroManual(Role role, int nowTime);

	/**
	 * 检测武将体力是否足够派遣事件
	 * 
	 * @param hero
	 * @return
	 */
	public boolean checkCanSendHero(Hero hero, int value);

	/**
	 * 加速征收队列
	 * 
	 * @param role
	 * @param type
	 * @param isFree
	 * @return
	 * @author wcy
	 */
	public Message speedUpLevy(Role role, byte type, byte isFree);

	/**
	 * 获得资源增益值(只有农田和民居）
	 * 
	 * @param role
	 * @param type
	 * @return
	 * @author wcy
	 */
	public int getValueOther(Role role, byte type);

	/**
	 * 获得议价后的价格
	 * 
	 * @param taskHero
	 * @param id
	 * @return
	 * @author wcy
	 */
	public int getMarketItemPrice(Role role, Hero taskHero, int id);

	/**
	 * 获得议价成功率
	 * 
	 * @param role
	 * @param taskHero
	 * @return
	 * @author wcy
	 */
	public int getMarketItemPriceSuccessRate(Role role, Hero taskHero);

	/**
	 * 刷新粮草库
	 * 
	 * @param role
	 * @param nowTime
	 * @author wcy 2016年1月20日
	 */
	public void refreshGetFood(Role role, long nowTime);

	/**
	 * 刷新银币库
	 * 
	 * @param role
	 * @param nowTime
	 * @author wcy 2016年1月20日
	 */
	public void refreshGetMoney(Role role, long nowTime);

	/**
	 * 根据建筑刷新库存
	 * 
	 * @param role
	 * @param nowTime
	 * @param type
	 * @author wcy 2016年1月20日
	 */
	public void refreshGetResources(Role role, long nowTime, byte type);

	/**
	 * 领取声望
	 * 
	 * @param role
	 * @param is
	 * @return
	 * @author wcy 2016年1月21日
	 */
	public Message getPrestige(Role role);

	/**
	 * 刷新所有库存
	 * 
	 * @param role
	 * @author wcy 2016年1月22日
	 */
	public void refreshAllCache(Role role, int nowTime);

	/**
	 * 获得排名信息
	 * 
	 * @param role
	 * @author wcy 2016年2月25日
	 */
	public Message showRankInfo(Role role, byte type);

	/**
	 * 记录经营排行
	 * 
	 * @param role
	 * @param type
	 * @param num
	 * @param nowTime
	 * @author wcy 2016年2月26日
	 */
	public void recordIncomeRank(Role role, byte type, int num, int nowTime);
	
	/**
	 * 323
	 * 获取建筑信息
	 * @param role
	 * @return
	 */
	public Message getBuildInfo(Role role);
	
	/**
	 * 324
	 * 建筑信息改变
	 * @param role
	 * @author wcy 2016年3月9日
	 */
	public void buildInfoChange(Role role,byte type,byte changeType,int nowTime);
	
	/**
	 * 显示征收信息
	 * @param heroId
	 * @author wcy 2016年3月21日
	 */
	public Message showLevyInfo(Role role,byte type,int heroId);
}
