package byCodeGame.game.module.login.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.BusinessDao;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.db.dao.KitchenDao;
import byCodeGame.game.db.dao.PropDao;
import byCodeGame.game.entity.bo.Business;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Kitchen;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.module.login.LoginConstant;
import byCodeGame.game.module.role.service.RoleService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.tools.CacheLockUtil;
import byCodeGame.game.util.SysManager;

/**
 * 登录服务
 * 
 * @author AIM
 *
 */
public class LoginServiceImpl implements LoginService {

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private HeroDao heroDao;

	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}

	private PropDao propDao;

	public void setPropDao(PropDao propDao) {
		this.propDao = propDao;
	}

	private KitchenDao kitchenDao;

	public void setKitchenDao(KitchenDao kitchenDao) {
		this.kitchenDao = kitchenDao;
	}

	private BusinessDao businessDao;

	public void setBusinessDao(BusinessDao businessDao) {
		this.businessDao = businessDao;
	}

	@Override
	public Message login(String account, IoSession session) {
		ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class, account);
		reentrantLock.lock();
		Message message = new Message();
		message.setType(LoginConstant.LOGIN);
		if (!SysManager.isSERVICE_FLAG()) {
			message.putShort(ErrorCode.SERVICE_CLOSED);
			return message;
		}
		try {

			// 获得完整的role对象
			Role role = roleService.getRoleByAccount(account);
			if (role == null) {
				message.putShort(ErrorCode.NO_ROLE);
				return message;
			}

			IoSession oldSession = SessionCache.getSessionByRoleId(role.getId());
			if (oldSession != null) { // 该账号已登录
				oldSession.setAttribute("roleId", null);
				oldSession.close(false);
			}

			// session绑定ID
			session.setAttribute("roleId", role.getId());
			// session放入缓存
			SessionCache.addSession(role.getId(), session);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reentrantLock.unlock();
		}
		return message;
	}

	@Override
	public Message getRoleData(String account, IoSession ioSession) {
		// ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class,
		// account);
		// reentrantLock.lock();

		try {
			Message message = new Message();
			message.setType(LoginConstant.GET_ROLE_DATA);

			// 获得完整的role对象
			Role role = roleService.getRoleByAccount(account);
			if (role == null) {
				message.putShort(ErrorCode.NO_ROLE);
				return message;
			}

			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		// reentrantLock.unlock();
		// }

	}

	@Override
	public void roleLoginDataInit(Role role) {
		int roleId = role.getId();

		// 获取英雄
		List<Hero> heroList = heroDao.getHerosByRoleId(roleId);
		Map<Integer, Hero> heroMap = role.getHeroMap();
		heroMap.clear();
		for (Hero hero : heroList) {
			int id = hero.getId();
			heroMap.put(id, hero);
		}

		// 获取道具
		List<Prop> propList = propDao.getPropsByRoleId(roleId);
		Map<Integer, Prop> configPropMap = role.getConfigIdPropMap();
		Map<Integer, Prop> serverPropMap = role.getServerIdPropMap();
		for (Prop prop : propList) {
			int configId = prop.getConfigId();
			int serverId = prop.getId();
			configPropMap.put(configId, prop);
			serverPropMap.put(serverId, prop);
		}

		// 获取厨房
		Kitchen kitchen = kitchenDao.getKitchenByRoleId(roleId);
		role.setKitchen(kitchen);
		
		//经营
		Business business = businessDao.getBusinessByRoleId(roleId);
		role.setBusiness(business);
	}

}
