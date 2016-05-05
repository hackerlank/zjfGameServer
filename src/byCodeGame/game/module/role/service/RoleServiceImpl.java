package byCodeGame.game.module.role.service;

import java.util.List;
import java.util.Map;

import byCodeGame.game.cache.local.RoleCache;
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

public class RoleServiceImpl implements RoleService {

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
	public Role getRoleById(int roleId) {
		Role role = RoleCache.getRoleById(roleId);
		if (role == null) {
			role = roleDao.getRoleById(roleId);
			this.roleDataInit(role);
			RoleCache.putRole(role);
		}

		return role;
	}

	@Override
	public Role getRoleByAccount(String account) {
		Role role = RoleCache.getRoleByAccount(account);
		if (role == null) {
			role = roleDao.getRoleByAccount(account);
			this.roleDataInit(role);
			RoleCache.putRole(role);
		}
		return role;
	}

	private void roleDataInit(Role role) {
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
