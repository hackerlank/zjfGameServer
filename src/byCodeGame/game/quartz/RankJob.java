package byCodeGame.game.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import byCodeGame.game.moudle.rank.service.RankService;
import byCodeGame.game.moudle.server.service.ServerService;
import byCodeGame.game.util.SpringContext;
import byCodeGame.game.util.Utils;

public class RankJob implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		int nowTime = Utils.getNowTime();
		RankService rankService = (RankService) SpringContext.getBean("rankService");
		rankService.sendOffensiveAward();
		rankService.refreshWorldLv();
		ServerService serverService = (ServerService) SpringContext.getBean("serverService");
		serverService.serverChange();
		
		// 经营
		// 记录所有玩家的经营值
		rankService.recordAllRoleIncome(nowTime);
		rankService.sortIncomeRank();
		rankService.sendIncomeRankAward();
		// 战力
		rankService.sortHeroFightValueRank();
		rankService.sendHeroFightValueAward();
		// 官邸
		rankService.sortRoleLvRank();
		rankService.sendRoleLvRankAward();
		// 主城
		rankService.sortOwnCityRank();
		rankService.sendOwnCityAward();
		// 击杀
		rankService.sortKillRank();
		rankService.sendKillRankAward();
	}

//	private void recordAllRoleIncome(int nowTime) {
//		RankService rankService = (RankService) SpringContext.getBean("rankService");
//		InComeService incomeService = (InComeService) SpringContext.getBean("inComeService");
//
//		ConcurrentMap<Integer, Role> allRole = RoleCache.getRoleMap();
//		for (Role role : allRole.values()) {
//			// 记录每个建筑的库存
//			Build build = role.getBuild();
//			incomeService.checkHeroManual(role, nowTime);
//			incomeService.refreshAllCache(role, nowTime);
//			for (Map.Entry<Byte, Integer> entrySet : build.getInComeCacheMap().entrySet()) {
//				byte type = entrySet.getKey();
//				int cache = entrySet.getValue();
//				int remainCache = build.getIncomeLastDayCacheMap().get(type);
//				int recordCache = cache;
//				if (!this.isGetResourceBefore(role, type))
//					// 没有收过资源，则今天的量需要减去之前的
//					recordCache = cache - remainCache;
//				rankService.addIncomeRank(role, nowTime, type, recordCache);
//
//				build.getIncomeLastDayCacheMap().put(type, remainCache + cache);
//			}
//		}
//	}
//
//	/**
//	 * 是否收过资源
//	 * 
//	 * @param remainCache
//	 * @return
//	 * @author wcy 2016年2月29日
//	 */
//	private boolean isGetResourceBefore(Role role, byte type) {
//		Build build = role.getBuild();
//		int remainCache = build.getIncomeLastDayCacheMap().get(type);
//		if (remainCache != 0) {
//			return false;
//		}
//		return true;
//	}

}
