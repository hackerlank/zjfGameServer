package byCodeGame.game.scheduler;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.ServerCache;
import byCodeGame.game.db.dao.BarrackDao;
import byCodeGame.game.db.dao.BuildDao;
import byCodeGame.game.db.dao.ChapterDao;
import byCodeGame.game.db.dao.FriendDao;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.db.dao.MailDao;
import byCodeGame.game.db.dao.MarketDao;
import byCodeGame.game.db.dao.PropDao;
import byCodeGame.game.db.dao.PubDao;
import byCodeGame.game.db.dao.RoleArenaDao;
import byCodeGame.game.db.dao.RoleArmyDao;
import byCodeGame.game.db.dao.RoleDao;
import byCodeGame.game.db.dao.ServerDao;
import byCodeGame.game.db.dao.SignDao;
import byCodeGame.game.db.dao.TargetDao;
import byCodeGame.game.db.dao.TasksDao;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Mail;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.Server;
import byCodeGame.game.entity.bo.Target;
import byCodeGame.game.util.SpringContext;

/**
 * 每30分钟向数据库更新用户数据与家build数据
 * 
 * @author 王君辉
 *
 */
public class RoleUpdateScheduled {

	private static ScheduledExecutorService roleUpdate = new ScheduledThreadPoolExecutor(1);

	/**
	 * 启动调度任务
	 */
	public static void startScheduled() {
		System.out.println("每半小时更新用户");
		roleUpdating();
		serverUpdating();
	}

	/**
	 * 每30分钟更新用户数据
	 */
	private static void roleUpdating() {
		roleUpdate.scheduleAtFixedRate(new Runnable() {
			public void run() {
				RoleDao roleDao = (RoleDao) SpringContext.getBean("roleDao");
				BuildDao buildDao = (BuildDao) SpringContext.getBean("buildDao");
				MailDao mailDao = (MailDao) SpringContext.getBean("mailDao");
				PropDao propDao = (PropDao) SpringContext.getBean("propDao");
				HeroDao heroDao = (HeroDao) SpringContext.getBean("heroDao");
				TasksDao tasksDao = (TasksDao) SpringContext.getBean("tasksDao");
				MarketDao marketDao = (MarketDao) SpringContext.getBean("marketDao");
				PubDao pubDao = (PubDao) SpringContext.getBean("pubDao");
				BarrackDao barrackDao = (BarrackDao) SpringContext.getBean("barrackDao");
				ChapterDao chapterDao = (ChapterDao) SpringContext.getBean("chapterDao");
				FriendDao friendDao = (FriendDao) SpringContext.getBean("friendDao");
				SignDao signDao = (SignDao) SpringContext.getBean("signDao");
				RoleArenaDao roleArenaDao = (RoleArenaDao) SpringContext.getBean("roleArenaDao");
				RoleArmyDao roleArmyDao = (RoleArmyDao) SpringContext.getBean("roleArmyDao");
				TargetDao targetDao = (TargetDao)SpringContext.getBean("targetDao");
				
				ConcurrentMap<Integer, Role> roleMap = RoleCache.getRoleMap();
				for (Map.Entry<Integer, Role> entry : roleMap.entrySet()) {
					Role tempRole = entry.getValue();
					// 更新role
					if (tempRole.isChange() && tempRole != null) {
						roleDao.updateRole(tempRole);
						tempRole.setChange(false);
					}
					// 更新build
					if (tempRole.getBuild().isChange() && tempRole.getBuild() != null) {
						buildDao.updateBuild(tempRole.getBuild());
						tempRole.getBuild().setChange(false);
					}
					// 更新Mail
					if (tempRole.getRoleMail() != null && tempRole.getRoleMail().size() > 0) {
						for (Mail mail : tempRole.getRoleMail()) {
							if (mail.isChange()) {
								mailDao.updateMail(mail);
								mail.setChange(false);
							}
						}
					}
					// 更新道具
					for (Map.Entry<Integer, Prop> propEntry : tempRole.getPropMap().entrySet()) {
						Prop tempProp = propEntry.getValue();
						if (tempProp.isChange()) {
							propDao.updateProp(tempProp);
							tempProp.setChange(false);
						}
					}
					// 更新英雄
					for (Map.Entry<Integer, Hero> heroEntry : tempRole.getHeroMap().entrySet()) {
						if (heroEntry.getValue().isChange()) {
							heroDao.updateHero(heroEntry.getValue());
							heroEntry.getValue().setChange(false);
						}
					}
					// 任务
					if (tempRole.getTasks().isChange()) {
						tasksDao.updateTasks(tempRole.getTasks());
						tempRole.getTasks().setChange(false);
					}
					// 市场
					if (tempRole.getMarket().isChange()) {
						marketDao.updateMarket(tempRole.getMarket());
						tempRole.getMarket().setChange(false);
					}
					// 酒馆
					if (tempRole.getPub().isChang()) {
						pubDao.updatePub(tempRole.getPub());
						tempRole.getPub().setChang(false);
					}

					if (tempRole.getBarrack().isChange()) {
						barrackDao.updataBarrack(tempRole.getBarrack());
						tempRole.getBarrack().setChange(false);
					}
					if (tempRole.getChapter().isChange()) {
						chapterDao.updateChapter(tempRole.getChapter());
						tempRole.getChapter().setChange(false);
					}
					if (tempRole.getFriend().isChang()) {
						friendDao.updateFriend(tempRole.getFriend());
						tempRole.getFriend().setChang(false);
					}
					if (tempRole.getSign().isChang()) {
						signDao.updateSign(tempRole.getSign());
						tempRole.getSign().setChang(false);
					}
					// if(tempRole.getRoleArena().isChange()){
					// roleArenaDao.updateRoleArena(tempRole.getRoleArena());
					// tempRole.getRoleArena().setChange(false);
					// }
					if (tempRole.getRoleArmy().isChange()) {
						roleArmyDao.updateRoleArmy(tempRole.getRoleArmy());
						tempRole.getRoleArmy().setChange(false);
					}
					
					if(tempRole.getTarget().isChange())
					{
						targetDao.updateTarget(tempRole.getTarget());
						tempRole.getTarget().setChange(false);
					}
				}
			}
		}, 1, 30, TimeUnit.MINUTES);
	}

	/**
	 * 每30分钟更新用户数据
	 */
	private static void serverUpdating() {
		roleUpdate.scheduleAtFixedRate(new Runnable() {
			public void run() {
				ServerDao serverDao = (ServerDao)SpringContext.getBean("serverDao");
				Server server = ServerCache.getServer();
				serverDao.updateServer(server);
			}
		}, 1, 30, TimeUnit.MINUTES);
	}
}