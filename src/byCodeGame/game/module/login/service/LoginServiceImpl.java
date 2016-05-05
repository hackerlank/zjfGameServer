package byCodeGame.game.module.login.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.BedroomDao;
import byCodeGame.game.db.dao.BinDao;
import byCodeGame.game.db.dao.FarmDao;
import byCodeGame.game.db.dao.HallDao;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.db.dao.KitchenDao;
import byCodeGame.game.db.dao.PubDao;
import byCodeGame.game.db.dao.RoleDao;
import byCodeGame.game.entity.bo.Bedroom;
import byCodeGame.game.entity.bo.Bin;
import byCodeGame.game.entity.bo.Farm;
import byCodeGame.game.entity.bo.Hall;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Home;
import byCodeGame.game.entity.bo.Kitchen;
import byCodeGame.game.entity.bo.Pub;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.module.login.LoginConstant;
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

	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	private HeroDao heroDao;

	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}

	private BedroomDao bedroomDao;

	public void setBedroomDao(BedroomDao bedroomDao) {
		this.bedroomDao = bedroomDao;
	}

	private KitchenDao kitchenDao;

	public void setKitchenDao(KitchenDao kitchenDao) {
		this.kitchenDao = kitchenDao;
	}

	private FarmDao farmDao;

	public void setFarmDao(FarmDao farmDao) {
		this.farmDao = farmDao;
	}

	private PubDao pubDao;

	public void setPubDao(PubDao pubDao) {
		this.pubDao = pubDao;
	}

	private BinDao binDao;

	public void setBinDao(BinDao binDao) {
		this.binDao = binDao;
	}

	private HallDao hallDao;

	public void setHallDao(HallDao hallDao) {
		this.hallDao = hallDao;
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
			Set<String> accountSet = RoleCache.getAccountSet();
			if (accountSet.contains(account)) { // 有数据 登录成功
				message.putShort(ErrorCode.SUCCESS);
			} else { // 无数据 创建角色
				message.putShort(ErrorCode.SHORT_TWO);
			}
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

			// 获取缓存中的role
			Role role = RoleCache.getRoleByAccount(account);

			if (role == null) { // 缓存中没有role数据
				role = roleDao.getRoleByAccount(account);
				this.roleLoginDataInit(role);
			}

			// 检测配属英雄
			IoSession oldSession = SessionCache.getSessionByRoleId(role.getId());
			if (oldSession != null) { // 该账号已登录
				oldSession.setAttribute("roleId", null);
				oldSession.close(false);
			}

			// session绑定ID
			ioSession.setAttribute("roleId", role.getId());
			// session放入缓存
			SessionCache.addSession(role.getId(), ioSession);
			// System.out.println(ioSession + "放入缓存");

			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		// reentrantLock.unlock();
		// }

	}

	/**
	 * 玩家数据登陆初始化
	 * 
	 * @param role
	 */
	private void roleLoginDataInit(Role role) {
		// 英雄初始化
		int roleId = role.getId();
		List<Hero> list = heroDao.getHerosByRoleId(role.getId());
		Map<Integer, Hero> heroMap = role.getHeroMap();
		for (Hero hero : list) {
			heroMap.put(hero.getHeroId(), hero);
		}
		// 道具初始化

		// 建筑初始化
		Home home = new Home();
		home.setRoleId(roleId);
		role.setHome(home);

		// 卧室初始化
		Bedroom bedroom = bedroomDao.getBedroomByRoleId(roleId);
		home.setBedroom(bedroom);

		// 厨房初始化
		Kitchen kitchen = kitchenDao.getKitchenByRoleId(roleId);
		home.setKitchen(kitchen);

		// 回收站初始化
		Bin bin = binDao.getBinByRoleId(roleId);
		home.setBin(bin);

		// 农场初始化
		Farm farm = farmDao.getFarmByRoleId(roleId);
		home.setFarm(farm);

		// 酒馆初始化
		Pub pub = pubDao.getPubByRoleId(roleId);
		home.setPub(pub);

		// 大厅初始化
		Hall hall = hallDao.getHallByRoleId(roleId);
		home.setHall(hall);

	}

}
