package byCodeGame.game.scheduler;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.heart.service.HeartService;
import byCodeGame.game.util.SpringContext;

/**
 * 已停用
 * @author xjd
 *
 */
public class ArmyTokenUpdateScheduled {
	private static ScheduledExecutorService armyTokenUpdate = new ScheduledThreadPoolExecutor(
			1);
	
	public static void startScheduled() {
		armyTokenUping();
	}
	
	private static void armyTokenUping() {
		armyTokenUpdate.scheduleAtFixedRate(new Runnable() {
			public void run() {
				HeartService heartService = (HeartService)SpringContext.getBean("heartService");
				ConcurrentMap<Integer, Role> roleMap = RoleCache.getRoleMap();
				for(Map.Entry<Integer, Role> entry : roleMap.entrySet()){
					Role tempRole = entry.getValue();
					heartService.addArmyToken(tempRole);
					tempRole.setChange(true);
				}
			}
		}, 1, 30, TimeUnit.MINUTES);
	}
}
