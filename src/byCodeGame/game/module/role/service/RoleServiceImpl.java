package byCodeGame.game.module.role.service;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.db.dao.KitchenDao;
import byCodeGame.game.db.dao.RoleDao;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.module.login.service.LoginService;

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

	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@Override
	public Role getRoleById(int roleId) {
		Role role = RoleCache.getRoleById(roleId);
		if (role == null) {
			role = roleDao.getRoleById(roleId);
			if (role != null) {
				loginService.roleLoginDataInit(role);
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
				loginService.roleLoginDataInit(role);
				RoleCache.putRole(role);
				String name = role.getName();

				RoleCache.getNameSet().add(name);
				RoleCache.getAccountSet().add(account);
			}

		}
		return role;
	}

}
