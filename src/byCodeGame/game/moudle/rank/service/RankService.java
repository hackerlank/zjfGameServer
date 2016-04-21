package byCodeGame.game.moudle.rank.service;

import java.util.List;
import java.util.Map;

import byCodeGame.game.cache.local.RankCache.RankType;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.Rank;
import byCodeGame.game.entity.po.WorldArmy;

public interface RankService {

	public int addOffensive(Role role, int nowTime, int hurtValue);

	public List<Rank> sortOffensive();

	public void sendOffensiveAward();

	/**
	 * 刷新世界等级
	 * 
	 * @author wcy 2015年12月29日
	 */
	public void refreshWorldLv();

	/**
	 * 添加玩家等级
	 * 
	 * @param role
	 * @param nowTime
	 * @author wcy 2016年2月24日
	 */
	public void refreshRoleLvRank(Role role, int nowTime);

	/**
	 * 排序玩家等级
	 * 
	 * @return
	 * @author wcy 2016年2月24日
	 */
	public List<Rank> sortRoleLvRank();

	/**
	 * 发送官邸等级奖励
	 * 
	 * @author wcy 2016年2月24日
	 */
	public void sendRoleLvRankAward();

	/**
	 * 
	 * @param role
	 * @param nowTime
	 * @param incomeValue
	 * @return
	 * @author wcy 2016年2月24日
	 */
	public int addIncomeRank(Role role, int nowTime, byte type, int incomeValue);

	/**
	 * 经营排名
	 * 
	 * @author wcy 2016年2月24日
	 */
	public List<Rank> sortIncomeRank();

	/**
	 * 指定经营列表排名
	 * @param list
	 * @return
	 * @author wcy 2016年3月3日
	 */
	public void sortIncomeRank(List<Rank> list);

	/**
	 * 发放经营奖励
	 * 
	 * @author wcy 2016年2月24日
	 */
	public void sendIncomeRankAward();

	/**
	 * 计算玩家前heroCount位武将的战力和
	 * 
	 * @param role
	 * @param nowTime
	 * @param heroCount
	 * @author wcy 2016年2月25日
	 */
	public int refreshHeroFightValue(Role role, int nowTime, int heroCount);

	/**
	 * 武将战力排名
	 * 
	 * @return
	 * @author wcy 2016年2月25日
	 */
	public List<Rank> sortHeroFightValueRank();

	/**
	 * 发送武将战力奖励
	 * 
	 * @author wcy 2016年2月25日
	 */
	public void sendHeroFightValueAward();

	/**
	 * 更新城主
	 * 
	 * @author wcy 2016年2月25日
	 */
	public void refreshOwnCityNum(int nowTime, int winRoleId, int lossRoleId);

	/**
	 * 城主数量排序
	 * 
	 * @return
	 * @author wcy 2016年2月25日
	 */
	public List<Rank> sortOwnCityRank();

	/**
	 * 发送城主奖励
	 * 
	 * @author wcy 2016年2月25日
	 */
	public void sendOwnCityAward();

	/**
	 * 增加击杀排名
	 * 
	 * @param winArmy
	 * @param lossArmy
	 * @author wcy 2016年2月25日
	 */
	public void addKillRank(WorldArmy winArmy, WorldArmy lossArmy, int nowTime);

	/**
	 * 击杀排序
	 * 
	 * @return
	 * @author wcy 2016年2月25日
	 */
	public List<Rank> sortKillRank();

	/**
	 * 发送击杀排名奖励
	 * 
	 * @author wcy 2016年2月25日
	 */
	public void sendKillRankAward();

	/**
	 * 
	 * 
	 * @author wcy 2016年2月25日
	 */
	public void initRankMap();

	/**
	 * 获得排名类型
	 * 
	 * @param type
	 * @return
	 * @author wcy 2016年2月26日
	 */
	public RankType getRankType(byte type);

	/**
	 * 获得前count名
	 * 
	 * @param list
	 * @param count
	 * @return
	 * @author wcy 2016年2月26日
	 */
	public List<Rank> getTopCountList(List<Rank> list, int count);

	/**
	 * 如果前三名变化则发送系统通知
	 * 
	 * @param originList
	 * @param targetRank
	 * @return
	 * @author wcy 2016年2月26日
	 */
	public void ifTopRankChangeThenNotable(byte type, List<Rank> originTopList, List<Rank> targetList);

	/**
	 * 
	 * @param nowTime
	 * @author wcy 2016年3月3日
	 */
	public void recordAllRoleIncome(int nowTime);

	/**
	 * 刷新这一时刻所有经营信息
	 * 
	 * @author wcy 2016年3月3日
	 */
	public List<Rank> refreshAllIncomeCache(int nowTime);
}
