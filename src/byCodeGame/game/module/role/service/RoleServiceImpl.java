package byCodeGame.game.module.role.service;

import java.util.List;
import java.util.Map;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.db.dao.KitchenDao;
import byCodeGame.game.db.dao.RoleDao;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Kitchen;
import byCodeGame.game.entity.bo.Role;

public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	private HeroDao heroDao;

	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}

	private KitchenDao kitchenDao;

	public void setKitchenDao(KitchenDao kitchenDao) {
		this.kitchenDao = kitchenDao;
	}

	@Override
	public Role getRoleById(int roleId) {
		Role role = RoleCache.getRoleById(roleId);
		if (role == null) {
			role = roleDao.getRoleById(roleId);
			if (role != null) {
				this.roleDataInit(role);
				RoleCache.putRole(role);
				String name = role.getName();
				String account = role.getAccount();

				RoleCache.getNameSet().add(name);
				RoleCache.getAccountSet().add(account);
			}
		}

		return role;
	}

	@Override
	public Role getRoleByAccount(String account) {
		Role role = RoleCache.getRoleByAccount(account);
		if (role == null) {
			role = roleDao.getRoleByAccount(account);
			if (role != null) {
				this.roleDataInit(role);
				RoleCache.putRole(role);
				String name = role.getName();

				RoleCache.getNameSet().add(name);
				RoleCache.getAccountSet().add(account);
			}

		}
		return role;
	}

	/**
	 * 玩家数据初始化
	 * 
	 * @param role
	 * @author wcy 2016年5月6日
	 */
	private void roleDataInit(Role role) {
		// 英雄初始化
		int roleId = role.getId();
		List<Hero> list = heroDao.getHerosByRoleId(role.getId());
		Map<Integer, Hero> heroMap = role.getHeroMap();
		for (Hero hero : list) {
			heroMap.put(hero.getHeroId(), hero);
		}
		// 道具初始化

		// 厨房初始化
		Kitchen kitchen = kitchenDao.getKitchenByRoleId(roleId);
		role.setKitchen(kitchen);

	}

}
