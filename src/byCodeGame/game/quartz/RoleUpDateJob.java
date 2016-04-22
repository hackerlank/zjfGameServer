package byCodeGame.game.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleUpDateJob implements Job {
	private static final Logger jobFive = LoggerFactory.getLogger("jobFive");
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
//		MarketService marketService = (MarketService)SpringContext.getBean("marketService");
//		
//		ConcurrentMap<Integer, Role> roleMap = RoleCache.getRoleMap();
//		TaskService taskService = (TaskService)SpringContext.getBean("taskService");
//		PubService pubService = (PubService)SpringContext.getBean("pubService");
//		
//		for(Map.Entry<Integer, Role> entry : roleMap.entrySet()){
//			Role tempRole = entry.getValue();
//			//*****************每天重置玩家科技点数*******************
//			tempRole.setArmsResearchNum(RoleInitialValue.ARMS_RESEARCH_INIT);
//			//重置会谈次数
//			tempRole.getPub().setTalksNumber(0);
//			tempRole.getPub().setFreeExploitNum(0);
//			//军令购买次数重置
//			tempRole.setAreadyBuyArmyToken(0);
//			//更新市场商店数据
//			marketService.changMarket(tempRole);
//			tempRole.getMarket().setChange(true);;
//			//日常任务更新
//			taskService.initDailyTask(tempRole);
//			tempRole.getTasks().setChange(true);
//			//重置当日升级军团科技次数
//			tempRole.setUpLegionScience((byte)0);
//			//清空关卡次数
//			tempRole.getChapter().getTimesMap().clear();
//			tempRole.getChapter().getRefreshTimesMap().clear();
//			//重置酒馆
//			pubService.resetDeskHero(tempRole);
//			pubService.resetFreeChangeDeskHeroTimes(tempRole);
//			pubService.resetTalkSpeedUpTimes(tempRole);
//			tempRole.getBuild().setLevySpeedTimes(0);
//			//重置重修次数
//			tempRole.resetRetrainTimes();
//			tempRole.getFriendTipMap().clear();
//			//重置兵营征讨事件
//			tempRole.getBarrack().setOffensiveTimes(0);
//			tempRole.setScienceTime(0);
//			tempRole.setIsStrongHold((byte)0);
//			//每日俸禄
//			tempRole.setGetRankPay(CityConstant.NO);
//			//每日反抗次数
//			tempRole.setResistanceNum(0);
//			//可以放逐
//			List<VassalData> list = tempRole.getRoleCity().getVassalRoleId();
//			for(VassalData vassalData : list)
//				vassalData.setCanNotDrop(false);			
//			
//			//神兽
////			tempRole.getRoleArena().clearAnimalMap();
////			tempRole.getRoleArena().setTodayAnimal(0);
//			tempRole.setChange(true);			
//			//重置农田民居金币征收次数
//			tempRole.getBuild().getLevyTimeMap().put(InComeConstant.TYPE_FARM, 0);
//			tempRole.getBuild().getLevyTimeMap().put(InComeConstant.TYPE_HOUSE, 0);
//			//重置农田民居免费征收次数
//			tempRole.getBuild().getFreeLevyTimeMap().put(InComeConstant.TYPE_FARM, 0);
//			tempRole.getBuild().getFreeLevyTimeMap().put(InComeConstant.TYPE_HOUSE, 0);
//			//重置试练塔次数
//			tempRole.getTower().setTime(0);
//			tempRole.getTower().setReviveTime(0);
//			String str = Utils.getLogString(tempRole.getName(),tempRole.getAccount(),"jobDoen");
//			
//			jobFive.info(str);
//		}
		
		
	}

}
