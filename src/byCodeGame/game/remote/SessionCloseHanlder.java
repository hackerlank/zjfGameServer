package byCodeGame.game.remote;

import java.util.Map;

import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.db.dao.BarrackDao;
import byCodeGame.game.db.dao.BuildDao;
import byCodeGame.game.db.dao.ChapterDao;
import byCodeGame.game.db.dao.CityDao;
import byCodeGame.game.db.dao.FriendDao;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.db.dao.MailDao;
import byCodeGame.game.db.dao.MarketDao;
import byCodeGame.game.db.dao.PropDao;
import byCodeGame.game.db.dao.PubDao;
import byCodeGame.game.db.dao.RoleArenaDao;
import byCodeGame.game.db.dao.RoleArmyDao;
import byCodeGame.game.db.dao.RoleDao;
import byCodeGame.game.db.dao.SignDao;
import byCodeGame.game.db.dao.TargetDao;
import byCodeGame.game.db.dao.TasksDao;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Mail;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.Target;
import byCodeGame.game.moudle.raid.service.RaidService;
import byCodeGame.game.util.SpringContext;



/**
 * session关闭角色数据处理
 * 
 */
public class SessionCloseHanlder {

	/**
	 * 移除session缓存
	 * @param id
	 */
	public static void manipulate(Role role) {
		RaidService raidService = (RaidService)SpringContext.getBean("raidService");
		raidService.clearRoleFromRoomData(role);
		updateRoleData(role);
		SessionCache.removeSessionById(role.getId());
	}

	// 更新角色数据
	public static void updateRoleData(Role role) {
		//设置用户下线时间
		long time=System.currentTimeMillis() / 1000;
		role.setLastOffLineTime(time);
		long onLineTime=time-role.getLoginTime();
		long onLineSumTime=role.getOnLineSumTime()+onLineTime;
		role.setOnLineSumTime(onLineSumTime);
		RoleDao roleDao = (RoleDao)SpringContext.getBean("roleDao");
		BuildDao buildDao = (BuildDao)SpringContext.getBean("buildDao");
		MailDao mailDao = (MailDao)SpringContext.getBean("mailDao");
		PropDao propDao = (PropDao)SpringContext.getBean("propDao");
		HeroDao heroDao = (HeroDao)SpringContext.getBean("heroDao");
		TasksDao tasksDao = (TasksDao)SpringContext.getBean("tasksDao");
		MarketDao marketDao = (MarketDao)SpringContext.getBean("marketDao");
		PubDao pubDao = (PubDao)SpringContext.getBean("pubDao");
		BarrackDao barrackDao = (BarrackDao)SpringContext.getBean("barrackDao");
		ChapterDao chapterDao = (ChapterDao)SpringContext.getBean("chapterDao");
		FriendDao friendDao = (FriendDao)SpringContext.getBean("friendDao");
		SignDao signDao = (SignDao)SpringContext.getBean("signDao");
		RoleArenaDao roleArenaDao = (RoleArenaDao)SpringContext.getBean("roleArenaDao");
		CityDao cityDao = (CityDao)SpringContext.getBean("cityDao");
		RoleArmyDao roleArmyDao = (RoleArmyDao)SpringContext.getBean("roleArmyDao");
		TargetDao targetDao = (TargetDao)SpringContext.getBean("targetDao");
		roleDao.updateRole(role);
		//更新道具
		for(Map.Entry<Integer, Prop> entry : role.getPropMap().entrySet()){
			Prop tempProp = entry.getValue();
			if(tempProp.isChange()){
				propDao.updateProp(tempProp);
				tempProp.setChange(false);
			}
		}

		//更新邮件数据
		if(role.getRoleMail() !=null && role.getRoleMail().size() > 0){
			for(Mail mail : role.getRoleMail()){
				if(mail.isChange()){
					mailDao.updateMail(mail);
					mail.setChange(false);
				}
			}
		}

		buildDao.updateBuild(role.getBuild());
		role.getBuild().setChange(false);

		//更新英雄
		for(Map.Entry<Integer, Hero> entry : role.getHeroMap().entrySet()){
			if(entry.getValue().isChange())
				heroDao.updateHero(entry.getValue());
			entry.getValue().setChange(false);
		}
		//任务
		if(role.getTasks().isChange()){
			tasksDao.updateTasks(role.getTasks());
			role.getTasks().setChange(false);
		}

		//市场
		if(role.getMarket().isChange()){
			marketDao.updateMarket(role.getMarket());
			role.getMarket().setChange(false);
		}
		//酒馆
		if(role.getPub().isChang()){
			pubDao.updatePub(role.getPub());
			role.getPub().setChang(false);
		}
		if(role.getBarrack().isChange()){
			barrackDao.updataBarrack(role.getBarrack());
			role.getBarrack().setChange(false);
		}
		if(role.getChapter().isChange()){
			chapterDao.updateChapter(role.getChapter());
			role.getChapter().setChange(false);
		}
		//封地
		if(null!=role.getRoleCity()&&role.getRoleCity().isChange()){
			cityDao.updateRoleCity(role.getRoleCity());
			role.getRoleCity().setChange(false);
		}
		//好友
		if(role.getFriend().isChang())
		{
			friendDao.updateFriend(role.getFriend());
			role.getFriend().setChang(false);
		}
		//签到
		if(role.getSign().isChang())
		{
			signDao.updateSign(role.getSign());
			role.getSign().setChang(false);
		}
//		if(role.getRoleArena().isChange()){
//			roleArenaDao.updateRoleArena(role.getRoleArena());
//			role.getRoleArena().setChange(false);
//		}
		
		if(role.getRoleArmy().isChange())
		{
			roleArmyDao.updateRoleArmy(role.getRoleArmy());
			role.getRoleArmy().setChange(false);
		}
		
		if(role.getTarget().isChange())
		{
			targetDao.updateTarget(role.getTarget());
			role.getTarget().setChange(false);
		}
	}
}
