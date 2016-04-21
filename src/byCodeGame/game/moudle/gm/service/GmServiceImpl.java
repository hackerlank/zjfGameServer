package byCodeGame.game.moudle.gm.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.HeroLvConfigCache;
import byCodeGame.game.cache.file.IconUnlockConfigCache;
import byCodeGame.game.cache.file.RoleExpConfigCache;
import byCodeGame.game.cache.file.StrengthenConfigCache;
import byCodeGame.game.cache.local.AuctionCache;
import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.cache.local.LegionCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.ServerCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.AuctionDao;
import byCodeGame.game.db.dao.CityDao;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.db.dao.KeyDao;
import byCodeGame.game.db.dao.LegionDao;
import byCodeGame.game.db.dao.ServerDao;
import byCodeGame.game.entity.Key;
import byCodeGame.game.entity.bo.Auction;
import byCodeGame.game.entity.bo.Chapter;
import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.HeroLvConfig;
import byCodeGame.game.entity.file.IconUnlockConfig;
import byCodeGame.game.entity.file.RoleExpConfig;
import byCodeGame.game.entity.file.StrengthenConfig;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.moudle.chapter.service.ChapterService;
import byCodeGame.game.moudle.city.service.CityService;
import byCodeGame.game.moudle.gm.GmConstant;
import byCodeGame.game.moudle.hero.HeroConstant;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.moudle.role.RoleConstant;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.science.ScienceConstant;
import byCodeGame.game.remote.Message;
import byCodeGame.game.remote.SessionCloseHanlder;
import byCodeGame.game.util.SysManager;
import byCodeGame.game.util.Utils;

public class GmServiceImpl implements GmService {
	private HeroService heroService;

	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}

	private InComeService inComeService;

	public void setInComeService(InComeService inComeService) {
		this.inComeService = inComeService;
	}

	private PropService propService;

	public void setPropService(PropService propService) {
		this.propService = propService;
	}

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private HeroDao heroDao;
	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}
	
	private LegionDao legionDao;
	public void setLegionDao(LegionDao legionDao) {
		this.legionDao = legionDao;
	}
	
	private AuctionDao auctionDao;
	public void setAuctionDao(AuctionDao auctionDao) {
		this.auctionDao = auctionDao;
	}
	
	private CityDao cityDao;
	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}
	
	private ServerDao serverDao;
	public void setServerDao(ServerDao serverDao) {
		this.serverDao = serverDao;
	}
	
	private CityService cityService;
	public void setCityService(CityService cityService){
		this.cityService = cityService;
	}
	
	private KeyDao keyDao; 
	public void setKeyDao(KeyDao keyDao) {
		this.keyDao = keyDao;
	}
	
	private ChapterService chapterService;
	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}
	
	public Message getOnLineRoleData() {

		Message message = new Message();
		message.setType(GmConstant.GET_ALL_ROLE_DATA);

		Collection<IoSession> allSession = SessionCache.getAllSession();

		message.putInt(allSession.size());
		for (IoSession session : allSession) {
			Role role = RoleCache.getRoleBySession(session);
			message.putInt(role.getId());
			message.putString(role.getName());
			message.putString(role.getAccount());
			message.putInt(role.getExp());
			message.putShort(role.getLv());
			message.putInt(role.getGold());
			message.putInt(role.getMoney());
			message.putInt(role.getFood());
			message.putInt(role.getExploit());
			message.putInt(role.getPrestige());
			message.putInt(role.getArmyToken());
			message.put(role.getVipLv());
			message.putInt(role.getSumGold());
		}
		return message;
	}

	@Override
	public Message GMList(Role role, String functionName, int x, int y, int z, IoSession session) {
		Message message = new Message();
		message.setType(GmConstant.GM_LIST);
		short msg = 0;
		if (functionName.equals("setRoleLv")) {// 设置城主等级
			msg = setRoleLv(role, x);
		} else if (functionName.equals("addRoleExp")) {// 增加城主经验
			msg = addRoleExp(role, x);
		} else if (functionName.equals("getHero")) {// 添加一个武将
			msg = getHero(role, x, session);
		} else if (functionName.equals("setHeroLv")) {// 为一个武将设定等级
			msg = setHeroLv(role, x, y);
		} else if (functionName.equals("setHeroRLv")) {// 设定一个武将的转生等级
			msg = setHeroRLv(role, x, y);
		} else if (functionName.equals("addHeroExp")) {// 增加一个武将的经验
			msg = addHeroExp(role, x, y);
		} else if (functionName.equals("getItem")) {// 增加道具
			msg = getItem(role, x, y, session);
		} else if (functionName.equals("getEquip")) {// 增加装备
			msg = getEquip(role, x, y, session);
		} else if (functionName.equals("addYINBI")) {// 增加银币
			msg = addYINBI(role, x);
		} else if (functionName.equals("addYUANBAO")) {// 增加充值币
			msg = addYUANBAO(role, x);
			
		} else if (functionName.equals("addLIANGCAO")) {// 增加粮草
			msg = addLIANGCAO(role, x);
		} else if (functionName.equals("addRENKOU")) {// 增加兵力
			msg = addRENKOU(role, x);
		} else if (functionName.equals("setEquipLevel")) {// 设定装备强化等级(所有该ID的统一设定)
			msg = setEquipLevel(role, x, y);
		} else if (functionName.equals("setScience")) {// 设置科技等级
			msg = setScience(role, x, y);
		} else if (functionName.equals("addHeroHaoGan")) {// 增加英雄好感度
			msg = addHeroEmotion(role, x, y);
		}
		message.putShort(msg);

		return message;
	}
	
	/**
	 * 使用特权码
	 */
	public Message useKey(String key ,Role role) {
		Message message = new Message();
		message.setType(GmConstant.USE_PRIVILEGE);
		Key key2 = keyDao.getKey(key);
		if(key2 == null)
		{
			message.putShort(ErrorCode.ERR_USE_PRIVILEGE);
			return message;
		}
		if(key2.getIsUsed() == GmConstant.IS_USED)
		{
			message.putShort(ErrorCode.ERR_IS_UESD);
			return message;
		}
		if(role.getGetPstrSet().contains(key2.getType()))
		{
			message.putShort(ErrorCode.ERR_TYPE_IS_USED);
			return message;
		}
		//获取奖励
		role.getGetPstrSet().add(key2.getType());
		role.setChange(true);
		Set<ChapterReward> set = Utils.changStrToAward(key2.getAward());
		this.chapterService.getAward(role, set);
		//使用成功
		key2.setIsUsed(GmConstant.IS_USED);
		key2.setUseTime(Utils.getNowTime());
		keyDao.updateKey(key2);
		message.putShort(ErrorCode.SUCCESS);
		message.putString(key2.getAward());
		return message;
	}
	/**
	 * 停服指令<br/>
	 * 
	 * -关闭入口<br/>
	 * -强制下线<br/>
	 * -处理数据<br/>
	 * -sys.exit(0)
	 * @author xjd
	 */
	public void stopApp(String gm) {
		// 判断密匙
		if(!gm.equals(SysManager.getGM_PWD()))
		{
			System.out.println(SysManager.getGM_PWD());
			return;
		}
		//关闭入口
		SysManager.setSERVICE_FLAG(false);
		try {
			//在线玩家下线
			Collection<IoSession> allSession = SessionCache.getAllSession();
			Iterator<IoSession> it = allSession.iterator();
			while(it.hasNext()) {
				it.next().close(true);
			}
			
			//取消所有行军
			cityService.closeWorldMarch();
			
			//玩家数据
			for(Role role:RoleCache.getRoleMap().values()) {
				SessionCloseHanlder.updateRoleData(role);
				SessionCache.removeSessionById(role.getId());
			}
			//军团数据
			Map<Integer, Legion>  legionMap = LegionCache.getLegionMap();
			for(Map.Entry<Integer, Legion> entry : legionMap.entrySet()){
				Legion tempLegion = entry.getValue();
				legionDao.updateLegion(tempLegion);
			}
			//拍卖行数据
			for(Auction auction: AuctionCache.getOffTimeMap().values())
			{
				auctionDao.updateAuction(auction);
			}
			//世界数据
			for(City city:CityCache.getAllCityMap().values())
			{
				cityDao.updateCity(city);
			}
			//季节年份
			serverDao.updateServer(ServerCache.getServer());
		} catch (Exception e) {
			System.err.println("系统异常!!!");
			e.printStackTrace();
			//TODO 此处如果异常需要输出信息至指定位置
		} finally {
			//系统退出
			System.exit(0);
		}
	}
	
	/**
	 * 设置城主等级
	 * 
	 * @param role
	 * @param x
	 * @return
	 */
	private short setRoleLv(Role role, int x) {
		RoleExpConfig roleExp = RoleExpConfigCache.getMap().get(x);
		if (roleExp != null) {
			role.setLv((short) x);
			Map<Byte,Short> buildLvMap = role.getBuild().getBuildLvMap();
			//解锁之前的所有建筑
			 Map<String, IconUnlockConfig> map = IconUnlockConfigCache.getIconUnlockConfigMap();
			
			 for(Entry<String,IconUnlockConfig> e :map.entrySet()){
				IconUnlockConfig config = e.getValue();
				String name = e.getKey();
				short lv = (short) config.getValue();
				byte type = (byte)config.getType();

				if (role.getLv() >= lv && !name.equals("maincity")) {
					buildLvMap.put(type, (short) 1);
				}
			 }
			
			int exp = roleExp.getBaseExp();
			role.setExp(exp);

			// 玩家当前等级当前经验
			RoleExpConfig expConfig = RoleExpConfigCache.getMap().get((int) role.getLv());
			int tempExp = role.getExp() - expConfig.getBaseExp();
			inComeService.checkGetBuild(role);
			IoSession is = SessionCache.getSessionById(role.getId());
			Message message = new Message();
			message.setType(RoleConstant.ROLE_LV_UP);
			message.putShort(role.getLv());
			message.putInt(tempExp);
			message.putInt(expConfig.getNextExp() - expConfig.getBaseExp());
			is.write(message);
			return ErrorCode.SUCCESS;
		}
		return ErrorCode.NO_LV;
	}

	/**
	 * 增加城主经验
	 */
	private short addRoleExp(Role role, int x) {
		roleService.addRoleExp(role, x);
		return ErrorCode.SUCCESS;
	}

	private short getHero(Role role, int heroNum, IoSession session) {
		if (role.getHeroMap().containsKey(heroNum))
			return ErrorCode.HERO_AREADY_HAS;
		HeroConfig config = HeroConfigCache.getHeroConfigById(heroNum);
		if (config == null)
			return ErrorCode.NO_HERO;
		int heroId = heroNum;
		Hero hero = heroService.createHero(role, heroId);
		// 通知玩家
		heroService.addHero(session, hero);
		hero.setChange(true);
		return ErrorCode.SUCCESS;
	}

	/**
	 * 为一个武将设定等级
	 * 
	 * @param role 玩家对象
	 * @param heroId 要升级的英雄id,如果是-1则是全部英雄
	 * @param lv 要升级到的等级
	 * @return
	 */
	private short setHeroLv(Role role, int heroId, int lv) {
		// 如果是-1,则是全部武将
		if (heroId == -1) {
			int size = role.getHeroMap().size();
			if (size == 0) {
				return ErrorCode.NO_HERO;
			}
			for (Hero hero : role.getHeroMap().values()) {
				hero.setLv((short) lv);
				HeroLvConfig heroLv = HeroLvConfigCache.getHeroLvConfig(lv);
				hero.setExp(heroLv.getBaseExp());
			}
			return ErrorCode.SUCCESS;
		}

		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null) {
			return ErrorCode.NO_HERO;
		}
		hero.setLv((short) lv);
		HeroLvConfig heroLv = HeroLvConfigCache.getHeroLvConfig(lv);
		hero.setExp(heroLv.getBaseExp());
		return ErrorCode.SUCCESS;
	}

	/**
	 * 设定一个武将的转生等级
	 * 
	 * @param role
	 * @param heroId
	 * @param lv
	 * @return
	 */
	private short setHeroRLv(Role role, int heroId, int lv) {
		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null) {
			return ErrorCode.NO_HERO;
		}
		// 当前进阶等级
		// 下次进阶所需等级
		// 进阶成功 武将的经验和等级重置
		hero.setExp((short) 0);
		hero.setLv((short) 1);
		hero.setRebirthLv((short) (lv));
		hero.setChange(true);
		return ErrorCode.SUCCESS;
	}

	private short addHeroExp(Role role, int heroId, int exp) {
		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null) {
			return ErrorCode.NO_HERO;
		}
		Map<Integer, HeroLvConfig> heroLvConfigMap = HeroLvConfigCache.getHeroLvConfigMap();
		HeroLvConfig tempHeroLvConfig = heroLvConfigMap.get((int) role.getLv());
		// if ((int) (hero.getExp() + exp) >= tempHeroLvConfig.getNextExp() -
		// HeroConstant.TYPE_1) {
		// exp = tempHeroLvConfig.getNextExp() - HeroConstant.TYPE_1 -
		// hero.getExp();
		// }
		// hero.setExp(hero.getExp() + exp);
		// heroService.countHeroLv(hero, role);
		// hero.setChange(true);

		if (hero.getExp() >= tempHeroLvConfig.getNextExp() - HeroConstant.TYPE_1) {
			return ErrorCode.MAX_EXP_HERO;
		}

		int forHeroLv = getHeroLvByExp(hero, hero.getExp() + exp);
		if (forHeroLv <= role.getLv()) {
			hero.setExp(hero.getExp() + exp);
			hero.setLv((short) (forHeroLv));
		} else {
			HeroLvConfig config = heroLvConfigMap.get(role.getLv());
			exp = config.getNextExp() - HeroConstant.TYPE_1;
			hero.setLv(role.getLv());
			hero.setExp(exp);
			hero.setChange(true);
			heroService.countHeroLv(hero, role);

		}
		hero.setChange(true);
		heroService.countHeroLv(hero, role);

		return ErrorCode.SUCCESS;
	}

	private int getHeroLvByExp(Hero hero, int exp) {
		Map<Integer, HeroLvConfig> map = HeroLvConfigCache.getHeroLvConfigMap();
		int maxLv = 0;
		for (HeroLvConfig heroLvConfig : map.values()) {
			int baseExp = heroLvConfig.getBaseExp();
			int nextExp = heroLvConfig.getNextExp();
			int lv = heroLvConfig.getLv();
			if (lv > maxLv) {
				maxLv = lv;
			}
			if (exp >= baseExp && exp < nextExp) {
				return lv;
			}
		}
		return maxLv;
	}

	private short getItem(Role role, int itemId, int num, IoSession is) {
		propService.addProp(role, itemId, (byte) 2, num, is);
		return ErrorCode.SUCCESS;
	}

	private short getEquip(Role role, int itemId, int num, IoSession is) {
		if (num == 0) {
			num = 1;
		}
		propService.addProp(role, itemId, (byte) 1, num, is);
		return ErrorCode.SUCCESS;
	}

	private short addYINBI(Role role, int money) {
		roleService.addRoleMoney(role, money);
		return ErrorCode.SUCCESS;
	}

	private short addYUANBAO(Role role, int value) {
		roleService.addRoleGold(role, value);
		roleService.addRoleExploit(role, value);
		return ErrorCode.SUCCESS;
	}

	private short addLIANGCAO(Role role, int value) {
		roleService.addRoleFood(role, value);
		roleService.addRolePrestige(role, value);
		return ErrorCode.SUCCESS;
	}

	private short addRENKOU(Role role, int value) {
		roleService.addRolePopulation(role, value);
		return ErrorCode.SUCCESS;
	}

	private short setEquipLevel(Role role, int itemId, int lv) {
		// 取出装备
		Prop prop = role.getPropMap().get(itemId);
		if (prop == null) {
			return ErrorCode.NO_PROP;
		}
		StrengthenConfig strengthenConfig = StrengthenConfigCache.getStrengthenConfigByLv(prop.getLv());
		if (strengthenConfig == null) {
			return ErrorCode.NO_PROP;
		}
		// 装备等级无法超过人物等级
		// 取出装备配置表
		EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());
		prop.setLv((short) (lv));
		return ErrorCode.SUCCESS;
	}

	private short setScience(Role role, int type, int lv) {
		// 判断类型是否合法
		if (type < ScienceConstant.MIN_ROLE_SCIENCE || type > ScienceConstant.MAX_ROLE_SCIENCE) {
			return ErrorCode.ERR_SCIENCE;
		}
		// 取出对应升级的科技配置表信息
//		RoleScienceConfig roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(type);
//		if (roleScienceConfig == null) {
//			return ErrorCode.ERR_SCIENCE;
//		}
		// 判断玩家等级是否足够
		// if (role.getLv() < (short) roleScienceConfig.getNeedLv(role)
		// || role.getLv() < (short) roleScienceConfig.getOpenLv()) {
		// return false;
		// }
		// 判断玩家战功是否足够

		role.getRoleScienceMap().put(type, lv);
		role.setChange(true);
		// 获取解锁的位置
//		if (type <= ScienceConstant.MAX_FORMATION__SCIENCE && type > ScienceConstant.MIN_ROLE_SCIENCE) {
//			int[] arr = roleScienceConfig.getFormationPosition(role);
//			for (int i = 0; i < arr.length; i++) {
//				if (arr[i] > 0) {
//					if (role.getFormationData().get(type).getData().get((byte) arr[i]) == 1) {// 将解锁位置为未解锁的位置
//						role.getFormationData().get(type).getData().put((byte) arr[i], 2);// 设置为已解锁
//					}
//				}
//			}
//		}
		return ErrorCode.SUCCESS;
	}

	private short addHeroEmotion(Role role, int x, int y) {
		int heroId = x;
		int emotionValue = y;
		heroService.addHeroEmotion(role, heroId, emotionValue);
		return ErrorCode.SUCCESS;
	}


}
