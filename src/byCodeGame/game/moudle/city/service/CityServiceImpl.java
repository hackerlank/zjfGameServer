package byCodeGame.game.moudle.city.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.ArmsConfigCache;
import byCodeGame.game.cache.file.CityInfoConfigCache;
import byCodeGame.game.cache.file.CityWallConfigCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.MineFarmConfigCache;
import byCodeGame.game.cache.file.RankConfigCache;
import byCodeGame.game.cache.file.RoleScienceConfigCache;
import byCodeGame.game.cache.file.WorldWarPathCache;
import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.cache.local.LegionCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.ServerCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.cache.local.WorldMarchCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.CityDao;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.db.dao.ResultsDao;
import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.entity.bo.Mail;
import byCodeGame.game.entity.bo.MineFarm;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.RoleCity;
import byCodeGame.game.entity.bo.Server;
import byCodeGame.game.entity.file.CityInfoConfig;
import byCodeGame.game.entity.file.CityWallConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.MineFarmConfig;
import byCodeGame.game.entity.file.RankConfig;
import byCodeGame.game.entity.file.RoleScienceConfig;
import byCodeGame.game.entity.file.WorldWarPath;
import byCodeGame.game.entity.po.ActivityMessage;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.entity.po.CityFire;
import byCodeGame.game.entity.po.CityOwGet;
import byCodeGame.game.entity.po.ContributeRank;
import byCodeGame.game.entity.po.FormationData;
import byCodeGame.game.entity.po.History;
import byCodeGame.game.entity.po.Rank;
import byCodeGame.game.entity.po.VassalData;
import byCodeGame.game.entity.po.WorldArmy;
import byCodeGame.game.entity.po.WorldDoubleP;
import byCodeGame.game.entity.po.WorldMarch;
import byCodeGame.game.moudle.activity.service.ActivityService;
import byCodeGame.game.moudle.chapter.ChapterConstant;
import byCodeGame.game.moudle.chat.ChatConstant;
import byCodeGame.game.moudle.chat.service.ChatService;
import byCodeGame.game.moudle.city.CityConstant;
import byCodeGame.game.moudle.fight.FightConstant;
import byCodeGame.game.moudle.fight.service.FightService;
import byCodeGame.game.moudle.hero.HeroConstant;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.moudle.nation.NationConstant;
import byCodeGame.game.moudle.prop.PropConstant;
import byCodeGame.game.moudle.pub.PubConstant;
import byCodeGame.game.moudle.rank.RankConstant;
import byCodeGame.game.moudle.rank.service.RankService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.server.ServerConstant;
import byCodeGame.game.moudle.server.service.ServerService;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.InComeUtils;
import byCodeGame.game.util.PVPUitls;
import byCodeGame.game.util.Utils;
import byCodeGame.game.util.comparator.ComparatorContributeRank;
import cn.bycode.game.battle.core.Battle;
import cn.bycode.game.battle.data.CorpData;
import cn.bycode.game.battle.data.ResultData;
import cn.bycode.game.battle.data.TroopData;

public class CityServiceImpl implements CityService {

	private CityDao cityDao;

	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}

	private InComeService inComeService;

	public void setInComeService(InComeService inComeService) {
		this.inComeService = inComeService;
	}

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private MailService mailService;

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	private HeroService heroService;

	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}

	private HeroDao heroDao;

	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}

	private FightService fightService;

	public void setFightService(FightService fightService) {
		this.fightService = fightService;
	}

	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private ResultsDao resultsDao;

	public void setResultsDao(ResultsDao resultsDao) {
		this.resultsDao = resultsDao;
	}

	private ServerService serverService;

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	private ChatService chatService;

	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}

	private RankService rankService;

	public void setRankService(RankService rankService) {
		this.rankService = rankService;
	}
	
	private ActivityService activityService;
	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

	/**
	 * old
	 */
	public void CityInit() {
		List<RoleCity> list = cityDao.getListRoleCity();
		// if (list.isEmpty()) {
		//
		// }
		// Map<Integer, CityInfoConfig> CityInfoMap =
		// CityInfoConfigCache.getCityInfoMap();
		// // 启动服务器时将封地数据加入缓存
		// /** 每个城市存储玩家id集合 size代表玩家数量 */
		// ConcurrentMap<Integer, List<Integer>> map = new
		// ConcurrentHashMap<Integer, List<Integer>>();
		// /** 每个城市存储玩家封地map id为key value为玩家Id */
		// ConcurrentMap<Integer, Map<Integer, Integer>> cityRoleKeyMap = new
		// ConcurrentHashMap<Integer, Map<Integer, Integer>>();
		// for (int i = 0; i < CityInfoMap.size(); i++) {
		// List<Integer> city = new ArrayList<Integer>();
		// CityInfoConfig cityInfo = CityInfoMap.get(i);
		// Map<Integer, Integer> cityMap = new HashMap<Integer, Integer>();
		// for (RoleCity roleCity : list) {
		// if (cityInfo.getCityId() == roleCity.getCityId()) {
		// city.add(roleCity.getRoleId());
		// }
		// if (cityInfo.getCityId() == roleCity.getCityId()) {
		// cityMap.put(roleCity.getMapKey(), roleCity.getRoleId());
		// }
		// }
		// map.put(i, city);
		// cityRoleKeyMap.put(i, cityMap);
		// }
		// CityCache.setCityRoleMap(map);
		// CityCache.setCityRoleKeyMap(cityRoleKeyMap);
	}

	/**
	 * new
	 * 
	 */
	public void initWorldCity() {

		// 从数据库读取数据
		CityCache.getAllCityMap().putAll(cityDao.getAllCity());

		// 小山村初始化
		CityCache.getVillageNationMap().put(53, NationConstant.WU_TYPE);
		CityCache.getVillageNationMap().put(54, NationConstant.SHU_TYPE);
		CityCache.getVillageNationMap().put(55, NationConstant.WEI_TYPE);

		// 部队逆向填充
		for (City x : CityCache.getAllCityMap().values()) {
			City city = CityCache.getAllCityMap().get(x.getCityId());
			int cityId = city.getCityId();
			// 贡献初始化
			Map<Byte, Map<Integer, ContributeRank>> contributeMap = city.getContributeMap();
			if (contributeMap.get((byte) 1) == null) {
				contributeMap.put((byte) 1, new HashMap<Integer, ContributeRank>());
			}
			if (contributeMap.get((byte) 2) == null) {
				contributeMap.put((byte) 2, new HashMap<Integer, ContributeRank>());
			}
			if (contributeMap.get((byte) 3) == null) {
				contributeMap.put((byte) 3, new HashMap<Integer, ContributeRank>());
			}
			// 防守队列
			Map<Integer, List<WorldArmy>> defInfoMap = city.getDefInfoMap();
			for (List<WorldArmy> list : defInfoMap.values()) {
				for (WorldArmy worldArmy : list) {
					int roleId = worldArmy.getRoleId();
					if (roleId == -1)
						continue;
					Role role = roleService.getRoleById(roleId);
					role.getWorldArmySet().add(worldArmy);
					worldArmy.setLocation(cityId, role);
					worldArmy.setStatus(CityConstant.ARMY_DEF);
				}
			}
			// 核心防守队列
			List<WorldArmy> coreDefInfoMap = city.getCoreDefInfoMap();
			for (WorldArmy worldArmy : coreDefInfoMap) {
				int roleId = worldArmy.getRoleId();
				Role role = roleService.getRoleById(roleId);
				role.getWorldArmySet().add(worldArmy);
				worldArmy.setLocation(cityId, role);
				worldArmy.setStatus(CityConstant.ARMY_CORE);
			}
			// 等待队列
			Map<String, WorldArmy> lazyInfoMap = city.getLazyInfoMap();
			for (WorldArmy worldArmy : lazyInfoMap.values()) {
				int roleId = worldArmy.getRoleId();
				Role role = roleService.getRoleById(roleId);
				role.getWorldArmySet().add(worldArmy);
				worldArmy.setLocation(cityId, role);
				worldArmy.setStatus(CityConstant.ARMY_LAZY);
			}

			// byte nation = city.getNation();
			// Map<Byte,Integer> nationCityMap =
			// ServerCache.getServer().getNationCityNumMap();
			// Integer cityNum = nationCityMap.get(nation);
			// if(cityNum == null){
			// nationCityMap.put(nation, 1);
			// }else{
			// nationCityMap.put(nation,cityNum+1);
			// }

		}

		// 小山村反序列化部队
		ConcurrentMap<Integer, Role> allRole = RoleCache.getRoleMap();
		for (Role role : allRole.values()) {
			List<WorldArmy> worldArmyList = role.getVillageDefList();
			for (WorldArmy worldArmy : worldArmyList) {
				role.getWorldArmySet().add(worldArmy);
				worldArmy.setLocation(this.getVillageIdByNation(role.getNation()), role);
				worldArmy.setStatus(CityConstant.ARMY_LAZY);
			}
		}

		// 检查城市数量
		if (CityCache.getAllCityMap().size() < CityInfoConfigCache.getCityInfoMap().size()) {
			for (CityInfoConfig x : CityInfoConfigCache.getCityInfoMap().values()) {
				if (!CityCache.getAllCityMap().containsKey(x.getCityId())) {
					// 加入新的城市信息
					City city = new City(x);
					cityDao.insertCity(city);
					CityCache.getAllCityMap().put(city.getCityId(), city);
				} else {

				}
			}
		}

		WorldMarchCache.setCanMarch(true);

	}

	@Override
	public int getVillageIdByNation(byte type) {
		int cityId = 0;
		Map<Integer, Byte> map = CityCache.getVillageNationMap();
		for (Entry<Integer, Byte> entry : map.entrySet()) {
			byte nation = entry.getValue();
			if (nation == type) {
				cityId = entry.getKey();
				break;
			}
		}

		return cityId;
	}

	/**
	 * 初始化矿信息
	 */
	public void initMineFram() {
		List<MineFarm> temp = cityDao.getListMineFarm();
		for (MineFarm mf : temp) {
			CityCache.addMinFram(mf);
		}
		if (temp.size() < MineFarmConfigCache.getAllMap().size()) {
			for (MineFarmConfig x : MineFarmConfigCache.getAllMap().values()) {
				if (CityCache.getAllMine().contains(x.getCityId())) {
					if (!CityCache.getAllMine().get(x.getCityId()).containsKey(x.getBuildingID())) {
						MineFarm mineFarm = new MineFarm();
						mineFarm.setBuildingID(x.getBuildingID());
						mineFarm.setCityId(x.getCityId());
						cityDao.insertMineFarm(mineFarm);
						CityCache.addMinFram(mineFarm);
					}
				} else {
					MineFarm mineFarm = new MineFarm();
					mineFarm.setBuildingID(x.getBuildingID());
					mineFarm.setCityId(x.getCityId());
					cityDao.insertMineFarm(mineFarm);
					CityCache.addMinFram(mineFarm);
				}
			}
		}
	}

	/**
	 * 获取玩家所有世界信息
	 * 
	 * @author xjd
	 */
	public Message getAllWorldFormation(Role role) {
		Message message = new Message();
		message.setType(CityConstant.GET_WORLD_FORMATION);
		// TODO 需要补全逻辑关于可用的部队数量
		message.put((byte) 5);
		message.put((byte) role.getWorldArmySet().size());
		for (WorldArmy worldArmy : role.getWorldArmySet()) {
			message.putString(worldArmy.getId());
			message.putInt(worldArmy.getClientId());
			message.putString(worldArmy.getName());
			message.putInt(worldArmy.getHeroCaptainId());
			message.putInt(worldArmy.getLocation());
			message.put(worldArmy.getStatus());
			int location = worldArmy.getLocation();
			String cityname = "";
			if (location == -1) {
				WorldMarch worldMarch = this.getWorldMarch(worldArmy.getId());
				int finalCityId = worldMarch.getFinalCity().getCityId();
				CityInfoConfig config = CityInfoConfigCache.getCityInfoConfigById(finalCityId);
				cityname = config.getCityName();
			} else {
				CityInfoConfig config = CityInfoConfigCache.getCityInfoConfigById(worldArmy.getLocation());
				cityname = config.getCityName();
			}
			message.putString(cityname);
		}
		return message;
	}

	/**
	 * 获取世界大战单一阵型
	 * 
	 * @author xjd
	 */
	public Message getFormation(Role role, int id) {
		WorldArmy worldArmy = null;
		Message message = new Message();
		message.setType(CityConstant.GET_FORMATION_INFO);
		for (WorldArmy army : role.getWorldArmySet()) {
			if (army.getClientId() == id) {
				worldArmy = army;
			}
		}
		if (worldArmy == null) {
			// message.putShort(ErrorCode.ERR_ID_WORLDARMY);
			return null;
		}
		FormationData data = worldArmy.getFormationData();
		Map<Byte, Integer> temp = data.getData();
		StringBuffer sb = new StringBuffer();
		sb.append(worldArmy.getClientId()).append(":");
		for (Map.Entry<Byte, Integer> entry : temp.entrySet()) {
			sb.append(entry.getKey()).append(",");
			sb.append(entry.getValue()).append(";");
		}
		message.putString(sb.toString());
		message.putInt(worldArmy.getHeroCaptainId());
		message.putString(worldArmy.getName());
		return message;
	}

	/**
	 * 保存世界阵型信息
	 * 
	 * @author xjd
	 */
	public Message saveWorldFormation(Role role, int useFormationID, byte key, int heroId) {
		Message message = new Message();
		message.setType(CityConstant.SAVE_WORLD_FORMATION);
		Map<Byte, Integer> map = role.getWorldFormation().get(useFormationID).getData();
		StringBuffer sb = new StringBuffer();
		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null)
			return null;
		if (hero.getLocation() != 0) {
			message.putShort(ErrorCode.ERR_USE_HERO);
			sb.append(useFormationID).append(":");
			for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
				sb.append(entry.getKey()).append(",");
				sb.append(entry.getValue()).append(";");
			}
			message.putString(sb.toString());
			message.putInt(0);
			message.putString("");
			return message;
		}

		if (!this.getLocation(role, useFormationID, key, heroId)) {
			message.putShort(ErrorCode.ERR_LOC_NUM);
			sb.append(useFormationID).append(":");
			for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
				sb.append(entry.getKey()).append(",");
				sb.append(entry.getValue()).append(";");
			}
			message.putString(sb.toString());
			message.putInt(0);
			message.putString("");
			return message;
		}
		// heroId!=2表示阵上武将下阵
		if (!role.getHeroMap().containsKey(heroId) && heroId != HeroConstant.RM_HERO) {
			message.putShort(ErrorCode.NO_HERO);
			sb.append(useFormationID).append(":");
			for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
				sb.append(entry.getKey()).append(",");
				sb.append(entry.getValue()).append(";");
			}
			message.putString(sb.toString());
			message.putInt(0);
			message.putString("");
			return message;
		}

		if (heroId == HeroConstant.RM_HERO && role.getUseFormationID() == useFormationID) {// 默认阵型最后一名武将不能下阵
			int index = 0;
			for (Map.Entry<Byte, Integer> map2 : map.entrySet()) {
				if (map2.getValue() > HeroConstant.RM_HERO) {
					++index;
				}
			}
			if (index <= 1) {//
				message.putShort(ErrorCode.FORMATION_NO_HERO);
				sb.append(useFormationID).append(":");
				for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
					sb.append(entry.getKey()).append(",");
					sb.append(entry.getValue()).append(";");
				}
				message.putString(sb.toString());
				message.putInt(0);
				message.putString("");
				return message;
			}
		}
		byte key2 = 0;// 保存替换武将原来所在的位置
		int heroId2;// 保存该位置原来的武将id
		heroId2 = role.getWorldFormation().get(useFormationID).getData().get(key);
		for (Map.Entry<Byte, Integer> map2 : map.entrySet()) {
			if (map2.getValue() == heroId) {
				key2 = map2.getKey();
			}
		}
		role.getWorldFormation().get(useFormationID).getData().put(key, heroId);
		if (key2 != (byte) 0 && heroId != HeroConstant.RM_HERO) {
			role.getWorldFormation().get(useFormationID).getData().put(key2, heroId2);
		}
		role.setChange(true);
		message.putShort(ErrorCode.SUCCESS);
		sb.append(useFormationID).append(":");
		for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
			sb.append(entry.getKey()).append(",");
			sb.append(entry.getValue()).append(";");
		}
		message.putString(sb.toString());
		message.putInt(0);
		message.putString("");
		return message;
	}

	/**
	 * 开始编辑世界阵型
	 */
	public Message startWorldFormation(Role role, int id) {
		FormationData formationData = role.getWorldFormation().get(id);
		// TODO 这里可能根据需要增加过滤条件
		Message message = new Message();
		message.setType(CityConstant.START_WORLD_FORMATION);
		// 判断官职是否允许开启阵型
		RankConfig config = RankConfigCache.getRankConfigById(role.getRank());
		if (config == null)
			return null;
		if (config.getMaxWorldArmy() < id - CityConstant.MINUS_NUM) {
			message.putShort(ErrorCode.NO_RANK);
			return message;
		}
		if (formationData == null) {
			message.putShort(ErrorCode.ERR_START_WORLD_FORMATION);
			return message;
		}
		for (WorldArmy x : role.getWorldArmySet()) {
			if (x.getClientId() == id) {
				message.putShort(ErrorCode.ERR_START_WORLD_FORMATION);
				return message;
			}
		}
		formationData.clearData();
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	public Message changeWorldArmyFormation(Role role, int armyId, byte key, int heroId, WorldArmy worldArmy) {
		Message message = new Message();
		message.setType(CityConstant.SAVE_WORLD_FORMATION);
		StringBuffer sb = new StringBuffer();
		FormationData data = worldArmy.getFormationData();
		// 检查是否在阵型中
		Map<Byte, Integer> map = data.getData();
		if (!map.values().contains(heroId)) {
			message.putShort(ErrorCode.ERR_ADD_HERO);
			sb.append(armyId).append(":");
			for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
				sb.append(entry.getKey()).append(",");
				sb.append(entry.getValue()).append(";");
			}
			message.putString(sb.toString());
			message.putInt(worldArmy.getHeroCaptainId());
			message.putString(worldArmy.getName());
			return message;
		}

		byte key2 = 0;// 保存替换武将原来所在的位置
		int heroId2;// 保存该位置原来的武将id
		CorpData[] temp = worldArmy.getTroopData().getCorps();
		CorpData temp1 = temp[key];
		CorpData temp2 = null;
		heroId2 = data.getData().get(key);
		for (Map.Entry<Byte, Integer> map2 : map.entrySet()) {
			if (map2.getValue() == heroId) {
				key2 = map2.getKey();
				temp2 = temp[key2];
			}
		}
		data.getData().put(key, heroId);
		temp[key] = temp2;
		if (key2 != (byte) 0 && heroId != HeroConstant.RM_HERO) {
			data.getData().put(key2, heroId2);
			temp[key2] = temp1;
		}
		role.setChange(true);
		message.putShort(ErrorCode.SUCCESS);
		sb.append(armyId).append(":");
		for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
			sb.append(entry.getKey()).append(",");
			sb.append(entry.getValue()).append(";");
		}
		message.putString(sb.toString());
		message.putInt(worldArmy.getHeroCaptainId());
		message.putString(worldArmy.getName());
		return message;
	}

	@Override
	public Message getHeroLocation(Role role) {
		Message message = new Message();
		message.setType(CityConstant.GET_HERO_LOCATION);
		message.putInt(role.getHeroMap().size());
		for (Hero hero : role.getHeroMap().values()) {
			message.putInt(hero.getHeroId());
			message.putInt(hero.getLocation());
		}

		return message;
	}

	/**
	 * 变更驻守城门
	 * 
	 * @author xjd
	 */
	public Message changeDefInfo(Role role, int cityId, String id, int newId) {
		Message message = new Message();
		message.setType(CityConstant.CHANGE_DEFINFO);
		City city = CityCache.getAllCityMap().get(cityId);
		if (city == null)
			return null;
		// 检查部队是否存在
		WorldArmy worldArmy = role.getWorldArmyById(id);
		if (worldArmy == null) {
			return null;
		}
		if (worldArmy.getLocation() != city.getCityId()) {
			return null;
		}

		int currentTime = Utils.getNowTime();
		if (!checkIsWorldArmyAlive(currentTime, worldArmy)) {
			message.putShort(ErrorCode.WORLD_ARMY_ALREADY_DEAD);
			return message;
		}

		this.clearDef(city, worldArmy);
		city.addDefInfoWorldArmy(newId, worldArmy);
		message.putShort(ErrorCode.SUCCESS);
		// TODO 世界大战记录
		CityWallConfig config = CityWallConfigCache.getCityWallConfig(cityId, newId);
		serverService.saveHistoryRole(ServerConstant.CHANGE_DEFINFO, worldArmy, config.getWallName());
		if (role.getLegionId() != ServerConstant.NO_LEGION) {
			serverService.saveHistoryLegion(ServerConstant.CHANGE_DEFINFO_LEGION, worldArmy, config.getWallName());
		}
		return message;
	}

	/**
	 * 城主布防
	 * 
	 * @author xjd
	 */
	public Message changeCoreInfo(int cityId, Role role, int index1, int index2) {
		Message message = new Message();
		message.setType(CityConstant.CHANGE_COREINFO);
		City city = CityCache.getCityByCityId(cityId);
		if (index1 == index2 || city == null) {
			return null;
		}
		if (index1 > city.getCoreDefInfoMap().size() || index2 > city.getCoreDefInfoMap().size()) {
			return null;
		}
		// 判断权限
		if (city.getCityOw() != role.getId()) {
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		// 检查位置
		if (index2 == CityConstant.MARCHING) {
			WorldArmy army = city.getCoreDefInfoMap().get(index1);
			// 移除部队
			city.removeCoreInfo(index1);
			city.addLazyInfo(army);
		} else {
			// 更改顺序
			city.changeCoreInfo(index1, index2);
		}

		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 加入护都府
	 * 
	 * @author xjd
	 */
	public Message joinCoreInfo(int cityId, String worldArmyId, Role role) {
		Message message = new Message();
		message.setType(CityConstant.JOIN_COREINFO);
		// 判断部队位置
		WorldArmy worldArmy = role.getWorldArmyById(worldArmyId);
		City city = CityCache.getCityByCityId(cityId);
		int currentTime = Utils.getNowTime();
		if (worldArmy == null || city == null)
			return null;
		// 判断位置
		if (city.getCityId() != worldArmy.getLocation()) {
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		if (!checkIsWorldArmyAlive(currentTime, worldArmy)) {
			message.putShort(ErrorCode.WORLD_ARMY_ALREADY_DEAD);
			return message;
		}
		if (city.getLegionId() != role.getLegionId()) {
			message.putShort(ErrorCode.ERR_ID_CITY_LEGION);
			return message;
		}
		if (city.getCoreDefInfoMap().size() >= GeneralNumConstantCache.getValue("CORE_CITY_NUM")) {
			message.putShort(ErrorCode.MAX_NUM_CORE);
			return message;
		}
		this.clearDef(city, worldArmy);
		city.addCoreInfo(worldArmy);
		message.putShort(ErrorCode.SUCCESS);
		// 战况记录
		serverService.saveHistoryRole(ServerConstant.CHANGE_DEFINFO, worldArmy, "护都府");
		if (role.getLegionId() != ServerConstant.NO_LEGION) {
			serverService.saveHistoryLegion(ServerConstant.CHANGE_DEFINFO_LEGION, worldArmy, "护都府");
		}

		return message;
	}

	/**
	 * 离开护都府
	 * 
	 * @author xjd
	 */
	public Message leaveCoreInfo(Role role, String worldArmyId, int cityId) {
		City city = CityCache.getCityByCityId(cityId);
		WorldArmy worldArmy = role.getWorldArmyById(worldArmyId);
		if (city == null || worldArmy == null) {
			return null;
		}

		if (!city.getCoreDefInfoMap().contains(worldArmy)) {
			return null;
		}
		Message message = new Message();
		message.setType(CityConstant.LEAVE_COREINFO);
		this.clearDef(city, worldArmy);
		city.addLazyInfo(worldArmy);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	@Override
	public Message leaveDefInfo(Role role, String worldArmyId, int cityId, int wallId) {
		City city = CityCache.getCityByCityId(cityId);
		WorldArmy worldArmy = role.getWorldArmyById(worldArmyId);
		if (city == null || worldArmy == null) {
			return null;
		}

		Map<Integer, List<WorldArmy>> defInfoMap = city.getDefInfoMap();
		List<WorldArmy> list = defInfoMap.get(wallId);
		if (list == null || !list.contains(worldArmy)) {
			return null;
		}

		Message message = new Message();
		message.setType(CityConstant.LEAVE_DEFINFO);
		this.clearDef(city, worldArmy);
		city.addLazyInfo(worldArmy);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 调度器处理行军到达
	 */
	public void handlerArrive(WorldMarch worldMarch) {
		City lastCurrentCity = worldMarch.getCurrentCity();
		City lastTargetCity = worldMarch.getTargetCity();
		int a = lastCurrentCity.getCityId();
		int b = lastTargetCity.getCityId();
		WorldWarPath path = WorldWarPathCache.getWorldWarPathByCities(a, b);
		int pathId = Utils.getCityPathNum(a, b);
		int nowTime = Utils.getNowTime();
		byte type = 0;
		// 移动游标
		worldMarch.nextCity();
		City city = worldMarch.getCurrentCity();
		String cityName = CityInfoConfigCache.getCityInfoConfigById(city.getCityId()).getCityName();
		Role role = RoleCache.getRoleById(worldMarch.getWorldArmy().getRoleId());
		WorldArmy worldArmy = worldMarch.getWorldArmy();
		// 有后续目标
		if (worldMarch.getTargetCity() != null) {
			// 判断当前城市是否产生势力变更
			if (role.getNation() == city.getNation()) {
				// 同国则继续行军
				// 设置开始时间
				worldMarch.setMarchStartTime(nowTime);
				// 通知客户端
				type = CityConstant.START_MOVE;
				a = worldMarch.getCurrentCity().getCityId();
				b = worldMarch.getTargetCity().getCityId();
			} else if (this.checkProtected(nowTime, city)) {
				// 保护中
				b = retreat(role, worldArmy, lastCurrentCity);
				a = 0;
				type = CityConstant.CANCEL_MOVE;
				// 战况行军失败
				serverService.saveHistoryRole(ServerConstant.MARCH_FAIL, worldArmy, "");
			} else {
				// 加入攻击队列
				city.addAttInfoWorldArmy(path.getLinkWallID(), worldArmy);
				type = CityConstant.OVER_MOVE;
				// 设置城墙冒火的开始时间
				this.setCityWallFireStartTime(city, pathId, nowTime, role.getNation());

				StringBuilder sb = this.getCityUnderAttackStringBuilder(city, cityName, role.getNation());
				chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_UNDER_ATTACK, sb.toString());
			}
		} else {
			// 到达
			if (this.checkVillage(city.getCityId())) {
				// 如果进入的是小山村
				role.addVillageDefWorldArmy(worldArmy);
				worldArmy.setLocation(city.getCityId(), role);
			} else if (role.getNation() != city.getNation()) {
				if (this.checkProtected(nowTime, city)) {
					// 遣散
					b = retreat(role, worldArmy, lastCurrentCity);
					a = 0;
					type = CityConstant.CANCEL_MOVE;
					// 战况行军失败
					serverService.saveHistoryRole(ServerConstant.MARCH_FAIL, worldArmy, "");
				} else {
					// 加入攻击队列
					city.addAttInfoWorldArmy(path.getLinkWallID(), worldArmy);
					// 更改所有阵型中英雄的location
					worldArmy.setLocation(city.getCityId(), role);
					type = CityConstant.OVER_MOVE;
					// 设置城墙冒火的开始时间
					this.setCityWallFireStartTime(city, pathId, nowTime, role.getNation());
					// 战况行军成功
					serverService.saveHistoryRole(ServerConstant.MARCH_SUESS, worldArmy, "");
					serverService.saveHistoryLegion(ServerConstant.MARCH_SUESS_LEGION, worldArmy);

					StringBuilder sb = this.getCityUnderAttackStringBuilder(city, cityName, role.getNation());
					chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_UNDER_ATTACK, sb.toString());
				}
			} else {
				// 加入防守队列
				city.addDefInfoWorldArmyToMinWorldArmyList(worldArmy);
				// 更改所有阵型中英雄的location
				worldArmy.setLocation(city.getCityId(), role);
				type = CityConstant.OVER_MOVE;
				// 战况行军成功
				serverService.saveHistoryRole(ServerConstant.MARCH_SUESS, worldArmy, "");
				serverService.saveHistoryLegion(ServerConstant.MARCH_SUESS_LEGION, worldArmy);
			}

			// 从缓存中移除行军信息
			WorldMarchCache.removeMarch(worldMarch.getId());
		}
		this.messageToClient(type, worldMarch.getId(), a, b, Utils.getNowTime(), worldMarch);
	}

	/**
	 * 检查是否是小山村
	 * 
	 * @param city
	 * @param role
	 * @return
	 * @author wcy 2016年3月1日
	 */
	private boolean checkVillage(int cityId) {
		return CityCache.getVillageNationMap().containsKey(cityId);
	}

	/**
	 * 调度器处理战斗
	 */
	public void handlerFight(City city) {
		for (Entry<Integer, List<WorldArmy>> x : city.getAttInfoMap().entrySet()) {
			// 检查是否需要战斗
			if (x.getValue().size() > 0) {
				// 攻击队列
				List<WorldArmy> atkList = x.getValue();
				// 防御队列
				List<WorldArmy> defList = city.getDefInfoMap().get(x.getKey());
				boolean flag = this.startFight(city, atkList, defList);
				if (flag) {
					boolean flag2 = this.startFight(city, atkList, city.getCoreDefInfoMap());
					if (flag2) {
						this.changeCityNation(city, atkList.get(0));
						break;
					}
				}
			}
		}
	}

	/**
	 * 检查世界阵型位置
	 * 
	 * @param role
	 * @param useFormationID
	 * @param key
	 * @param heroId
	 * @return
	 */
	private boolean getLocation(Role role, int useFormationID, byte key, int heroId) {
		boolean flag = false;
		int cont = 0;
		// 位置不合法
		if (key > 9 || key < 1) {
			return false;
		}
		// int
		// scienceLv=role.getRoleScienceMap().get(Integer.valueOf(useFormationID));
		for (Entry<Byte, Integer> x : role.getWorldFormation().get(useFormationID).getData().entrySet()) {
			if (x.getValue() > 2 && x.getKey() != key) {
				cont++;
			}
			if (x.getValue() == heroId) {
				cont--;
			}
		}
		RoleScienceConfig x = RoleScienceConfigCache.getRoleScienceConfig(1001, role.getRoleScienceMap().get(1001));
		if (cont < x.getValue()) {
			flag = true;
		}
		return flag;
	}

	// /**
	// * 势力交替
	// *
	// * @param city
	// * @param atk
	// * @param wallId
	// */
	// private void changeCityNation(City city, WorldArmy atk, int wallId) {
	// byte nation = city.getNation();
	// int minContribute = GeneralNumConstantCache.getValue("MIN_CONTRIBUTE");
	// Map<Integer, ContributeRank> map = city.getContributeMap().get(nation);
	// if (map != null) {
	// for (ContributeRank contributeRank : map.values()) {
	// int contribute = contributeRank.getContribute();
	// contribute = (int) Math.floor(contribute - contribute * 0.3);
	// contribute = contribute < minContribute
	// ? minContribute
	// : contribute;
	// contributeRank.setContribute(contribute);
	// }
	// }
	//
	//
	// Role role = RoleCache.getRoleById(atk.getRoleId());
	// // 当前直接占领城市
	// // 清除所有防守队列
	// for (List<WorldArmy> x : city.getDefInfoMap().values()) {
	// for (WorldArmy temp : x) {
	// Role r = roleService.getRoleById(temp.getRoleId());
	// temp.setLocation(0,r);
	// }
	// x.clear();
	// }
	// city.getCoreDefInfoMap().clear();
	// // 更改城市所属势力
	// city.setCityOw(role.getId());
	// city.setLegionId(role.getLegionId());
	// city.setNation(role.getNation());
	// // 将部队加入防御队列
	// for (WorldArmy x : city.getAttInfoMap().get(wallId)) {
	// Role r = roleService.getRoleById(x.getRoleId());
	// x.setLocation(city.getCityId(),r);
	// //部队状态
	// city.addDefInfoWorldArmyToMinWorldArmyList(x);
	// }
	// // 清除其他攻击队列
	// for (Entry<Integer, List<WorldArmy>> x : city.getAttInfoMap().entrySet())
	// {
	// if (x.getKey() != wallId) {
	// for (WorldArmy temp : x.getValue()) {
	// Role r = roleService.getRoleById(temp.getRoleId());
	// temp.setLocation(0,r);
	// }
	// }
	// x.getValue().clear();
	// }
	// // 城市变更
	// city.setLastChange(Utils.getNowTime());
	// city.setChange(true);
	// this.cityChange(city);
	// }

	/**
	 * 开始单条攻防队列的结果计算
	 * 
	 * @param atkList
	 * @param defList
	 * @return
	 */
	private boolean startFight(City city, List<WorldArmy> atkList, List<WorldArmy> defList) {

		if (defList.size() == 0)
			return true;

		Battle battle = new Battle();
		int winContribute = GeneralNumConstantCache.getValue("WIN_GET_CONTRIBUTE");
		int loseContribute = GeneralNumConstantCache.getValue("LOSS_GET_CONTRIBUTE");
		int nowTime = Utils.getNowTime();
		String cityName = CityInfoConfigCache.getCityInfoConfigById(city.getCityId()).getCityName();

		// 开始战斗
		for (;;) {
			if (atkList.size() == 0 || defList.size() == 0) {
				break;
			}
			WorldArmy atkArmy = atkList.get(0);
			WorldArmy defArmy = defList.get(0);
			Role atkRole = null;
			Role defRole = null;
			TroopData atk = atkArmy.getTroopData();
			TroopData def = defArmy.getTroopData();
			ResultData resultData = battle.fightPVP(atk, def);
			resultsDao.insertResults(resultData);
			Role winRole = null;
			Role lossRole = null;
			List<WorldArmy> lossList = null;
			WorldArmy winArmy = null;
			WorldArmy lossArmy = null;
			if (resultData.winCamp == 1) {
				winRole = RoleCache.getRoleById(atk.getPlayerId());
				lossRole = RoleCache.getRoleById(def.getPlayerId());
				atkRole = winRole;
				defRole = lossRole;
				lossList = defList;
				winArmy = atkList.get(0);
				lossArmy = defList.get(0);
			} else {
				winRole = RoleCache.getRoleById(def.getPlayerId());
				lossRole = RoleCache.getRoleById(atk.getPlayerId());
				atkRole = lossRole;
				defRole = winRole;
				lossList = atkList;
				winArmy = defList.get(0);
				lossArmy = atkList.get(0);
			}

			WorldArmy lossWorldArmy = lossList.get(0);
			/** 战况信息 */
			// 胜利方战况 玩家&&公会
			if (winRole != null) {
				// 玩家战况
				serverService.saveHistoryRole(ServerConstant.FIGHT_WIN, winArmy, resultData.uuid);
				// 公会战况
				Legion winLegion = LegionCache.getLegionById(winRole.getLegionId());
				if (winLegion != null) {
					serverService.saveHistoryLegion(ServerConstant.FIGHT_WIN_LEGION, winArmy);
				}
			}
			// 战败方
			if (lossRole != null) {
				// 玩家战况
				serverService.saveHistoryRole(ServerConstant.FIGHT_LOSS, lossArmy, resultData.uuid);
				// 公会战况
				Legion lossLegion = LegionCache.getLegionById(lossRole.getLegionId());
				if (lossLegion != null) {
					serverService.saveHistoryLegion(ServerConstant.FIGHT_LOSS_LEGION, lossArmy);
				}
			}
			// 败者回到据点
			int locationId = this.retreat(lossRole, lossWorldArmy, null);
			lossWorldArmy.setLastDeadTime(Utils.getNowTime());
			lossWorldArmy.removeTroopData();
			lossList.remove(0);

			addContribute(city, lossRole, loseContribute, nowTime);
			addContribute(city, winRole, winContribute, nowTime);
			//声望奖励
			this.prestigeWinAdd(winRole);
			this.prestigeLossAdd(lossRole);			
			
			byte nation = 0;
			if (lossRole == null) {
				nation = 0;
			} else {
				nation = lossRole.getNation();
			}
			winArmy.setWinTime(winArmy.getWinTime() + 1);
			if (lossWorldArmy.getRoleId() != -1) {
				messageToClient(CityConstant.CANCEL_MOVE, lossWorldArmy.getId(), city.getCityId(), locationId, nowTime,
						lossWorldArmy, nation);

				StringBuilder sb = this.getFightResultStringBuilder(city, cityName, atkArmy, atkRole, resultData);
				chatService.systemChat(atkRole, ChatConstant.MESSAGE_TYPE_FIGHT_RESULT, sb.toString());

				// 记录排行榜，如果更替则发送走马灯
				this.recordRankAndSendNotable(nowTime, winArmy, lossArmy);

			}
		}
		return defList.size() == 0 ? true : false;
	}
	
	private void prestigeWinAdd(Role role){
		int addValue = GeneralNumConstantCache.getValue("World_Prestige_Win_Add");
		this.addPrestige(role, addValue);
	}
	
	private void prestigeLossAdd(Role role){
		int addValue = GeneralNumConstantCache.getValue("World_Prestige_Loss_Add");
		this.addPrestige(role, addValue);
	}
	
	private void prestigeWaitAdd(Role role){
		int addValue = GeneralNumConstantCache.getValue("World_Prestige_Wait_Add");
		this.addPrestige(role, addValue);
	}
	
	private void addPrestige(Role role,int addValue){
		if(role == null){
			return;
		}
		roleService.addRolePrestige(role, addValue);
	}

	private void recordRankAndSendNotable(int nowTime, WorldArmy winArmy, WorldArmy lossArmy) {
		List<Rank> originRankList = rankService.sortKillRank();
		List<Rank> originTopRankList = rankService.getTopCountList(originRankList, 1);
		rankService.addKillRank(winArmy, lossArmy, nowTime);
		List<Rank> targetRankList = rankService.sortKillRank();

		rankService.ifTopRankChangeThenNotable(RankConstant.RANK_TYPE_KILL, originTopRankList, targetRankList);
	}

	@Override
	public void MineFarmInit() {
		// List<MineFarm> list = cityDao.getListMineFarm();
		// //list=list==null?new ArrayList<MineFarm>():list;
		// if (list.size()<MineFarmConfigCache.getMineFarmList().size()) {
		// List<MineFarmConfig>
		// mineFarmList=MineFarmConfigCache.getMineFarmList();
		// for (MineFarmConfig m : mineFarmList) {
		// MineFarm mine=new MineFarm(m.getCityId(), m.getKey(), m.getType(), 0,
		// 0, 0);
		// cityDao.insertMineFarm(mine);
		// }
		// }
		// list = cityDao.getListMineFarm();
		//
		// /**每个城市存储玩家矿藏map id为key value为玩家Id*/
		// ConcurrentMap<Integer,Map<Integer, Integer>> cityMoneyKeyMap=new
		// ConcurrentHashMap<Integer,Map<Integer, Integer>>();
		// /**每个城市存储玩家农田map id为key value为玩家Id*/
		// ConcurrentMap<Integer,Map<Integer, Integer>> cityFoodKeyMap=new
		// ConcurrentHashMap<Integer,Map<Integer, Integer>>();
		//
		// //最外面map key :cityId 中间map key :农田类型 最里面 map key:type 位置
		// ConcurrentMap<Integer,Map<Integer, Map<Integer,MineFarm>>> m1=new
		// ConcurrentHashMap<Integer,Map<Integer,Map<Integer, MineFarm>>>();
		// Map<Integer, CityInfoConfig>
		// CityInfoMap=CityInfoConfigCache.getCityInfoMap();
		// for (int i = 1; i < CityInfoMap.size(); i++) {
		// Map<Integer, Map<Integer,MineFarm>> m2=new HashMap<Integer,
		// Map<Integer,MineFarm>>();
		// CityInfoConfig cityInfo=CityInfoMap.get(i);
		// Map<Integer,Integer> moneyMap=new HashMap<Integer, Integer>();
		// Map<Integer,Integer> foodMap=new HashMap<Integer, Integer>();
		// Map<Integer, MineFarm> m3=new HashMap<Integer, MineFarm>();
		// Map<Integer, MineFarm> m4=new HashMap<Integer, MineFarm>();
		// for (MineFarm mineFarm : list) {
		//
		// if(cityInfo.getCityId()==mineFarm.getCityId()){
		//
		// if(mineFarm.getResourceType()==1){
		// moneyMap.put(mineFarm.getMapKey(), mineFarm.getRoleId());
		// m3.put(mineFarm.getMapKey(), mineFarm);
		// m2.put(1, m3);
		// }
		// if(mineFarm.getResourceType()==2){
		// foodMap.put(mineFarm.getMapKey(), mineFarm.getRoleId());
		// m4.put(mineFarm.getMapKey(), mineFarm);
		// m2.put(2, m4);
		// }
		//
		// }
		// }
		// m1.put(i, m2);
		// cityMoneyKeyMap.put(i, moneyMap);
		// cityFoodKeyMap.put(i, foodMap);
		// }
		// CityCache.setMineFarmMap(m1);
	}

	@Override
	public void upMineFarmDB() {
		// CityCache.getQuartzMapCache().clear();
		// int nowTime = (int) (System.currentTimeMillis() / 1000);
		// ConcurrentMap<Integer, Map<Integer, Map<Integer, MineFarm>>>
		// MineFarmMap = CityCache.getMineFarmMap();
		// for (ConcurrentMap.Entry<Integer, Map<Integer, Map<Integer,
		// MineFarm>>> mf1 : MineFarmMap.entrySet()) {
		// Map<Integer, Map<Integer, MineFarm>> map2 = mf1.getValue();
		// for (Entry<Integer, Map<Integer, MineFarm>> mf2 : map2.entrySet()) {
		// Map<Integer, MineFarm> map3 = mf2.getValue();
		// for (Entry<Integer, MineFarm> mf3 : map3.entrySet()) {
		// MineFarm mf4 = mf3.getValue();
		// //
		// if (mf4.isChange()) {
		// cityDao.updateMineFarm(mf4);
		// mf4.setChange(false);
		// }
		// if (mf4.getRoleId() > 0 && mf4.getStarTime() > 0) {
		// // 从开始到现在已经经历了5小时的资源放入快速刷新列表
		// if ((nowTime - mf4.getStarTime()) > (5 * 60 * 60)) {
		// // 快速刷新列表会在每半小时存入数据库时全部清空，重新放入，所以只能新建
		// MineFarm mf5 = new MineFarm(mf1.getKey(), mf3.getKey(), mf2.getKey(),
		// 0, 0, 0);
		// CityCache.getQuartzMapCache().add(mf5);
		// }
		//
		// }
		// }
		// }
		// }
		Map<Integer, Map<Integer, MineFarm>> temp = CityCache.getAllMine();
		for (Map<Integer, MineFarm> map : temp.values()) {
			for (MineFarm x : map.values()) {
				cityDao.updateMineFarm(x);
			}
		}
	}

	@Override
	public void upMineFarm() {
		// int nowTime = (int) (System.currentTimeMillis() / 1000);
		// List<MineFarm> list = CityCache.getQuartzMapCache();
		// for (MineFarm mineFarm : list) {
		// MineFarm mf = CityCache.getMineFarmById(mineFarm.getCityId(),
		// mineFarm.getResourceType(),
		// mineFarm.getMapKey());
		// if (mf.getStarTime() > 0 && mf.getRoleId() > 0) {
		// // 从资源开始到现在经历了6小时的，也就是已经可以回收的资源则进行回收并且更新农场矿洞的资源表
		// if (nowTime - mf.getStarTime() > CityConstant.OCCUPY_TIME) {
		// Role role2 = roleService.getRoleById(mf.getRoleId());
		//
		// int outPut = CityUtils.getOutPut(mf.getCityId(),
		// mf.getResourceType(), mf.getMapKey());
		// int useTime = 0; // 已开采时间
		// int useCrazyTime = 0; // 玩家享用疯狂模式时间
		// if (mineFarm != null) {
		// useTime = mineFarm.getUseTime();
		// useCrazyTime = mineFarm.getUseCrazyTime();
		// }
		// int nowOutPut = (outPut * (int) useTime) + (int) (useCrazyTime *
		// outPut * CityConstant.CONSUME_ADD);
		// if (mf.getResourceType() == 1) {
		// roleService.addRoleMoney(role2, nowOutPut);
		// Mail mail = new Mail();
		// mail.setTitle("zlpsb欠我一个邮件标题");
		// StringBuffer sb = new StringBuffer();
		// sb.append("带回").append(nowOutPut).append("银币");
		// mail.setContext(sb.toString());
		// mailService.sendSYSMail2(role2, mail);
		// } else {
		// roleService.addRoleFood(role2, nowOutPut);
		// Mail mail = new Mail();
		// mail.setTitle("zlpsb欠我一个邮件标题");
		// StringBuffer sb = new StringBuffer();
		// sb.append("带回").append(nowOutPut).append("粮草");
		// mail.setContext(sb.toString());
		// mailService.sendSYSMail2(role2, mail);
		// }
		// mineFarm = CityCache.getMineFarmById(mineFarm.getCityId(),
		// mineFarm.getResourceType(),
		// mineFarm.getMapKey());
		// mineFarm.setRoleId(0);
		// mineFarm.setStarTime(0);
		// }
		// }
		// }
	}

	/**
	 * 获取世界大战记录
	 * 
	 * @author xjd
	 */
	public Message getHistoryInfo(Role role) {// 获取城市封地信息
		Message message = new Message();
		message.setType(CityConstant.GET_HISTORY_INFO);
		message.putInt(role.getHistoryList().size());
		for (History x : role.getHistoryList()) {
			message.putInt(x.getTime());
			message.putInt(x.getYear());
			message.put(x.getType());
			message.putString(x.getStr());
			message.putString(x.getUuid());
		}
		Legion legion = LegionCache.getLegionById(role.getLegionId());
		if (legion == null) {
			message.putInt(0);
		} else {
			message.putInt(legion.getHistoryList().size());
			for (History x : legion.getHistoryList()) {
				message.putInt(x.getTime());
				message.putInt(x.getYear());
				message.put(x.getType());
				message.putString(x.getStr());
				message.putString(x.getUuid());
			}
		}

		message.putInt(ServerCache.getServer().getHistoryList().size());
		for (History x : ServerCache.getServer().getHistoryList()) {
			message.putInt(x.getTime());
			message.putInt(x.getYear());
			message.put(x.getType());
			message.putString(x.getStr());
			message.putString(x.getUuid());
		}
		return message;
	}

	@Override
	public Message goToVillage(Role role, int cityId) {
		Message message = new Message();
		message.setType(CityConstant.GO_TO_VILLAGE);
		CityInfoConfig cityInfoConfig = CityInfoConfigCache.getCityInfoConfigById(cityId);
		int size = 1;
		int startTime = role.getVillageMineFarmStartTime();
		int useTime = Utils.getNowTime() - startTime;
		if (useTime > CityConstant.PRO_TIME) {
			useTime = 0;
		} else {
			useTime = (int) (CityConstant.PRO_TIME - useTime);
		}

		String worldArmyId = role.getVillageMineFarmWorldArmyId();
		WorldArmy worldArmy = role.getWorldArmyById(worldArmyId);
		int captainId = 0;
		if (worldArmy != null)
			captainId = worldArmy.getHeroCaptainId();
		String occupyName = "";
		if (role.getVillageMineFarmWorldArmyId() != null && !role.getVillageMineFarmWorldArmyId().equals(""))
			occupyName = role.getName();

		message.putInt(size);
		message.putInt(15308);
		message.putString(occupyName);
		message.putInt(useTime);
		message.putInt(captainId);
		message.putString(role.getName());
		return message;
	}

	@Override
	public Message goToCity(int cityId, Role role) {// 进城
		Message message = new Message();
		message.setType(CityConstant.GO_TO_CITY);
		City city = CityCache.getCityByCityId(cityId);
		if (city == null) {
//			System.err.println(cityId);
			return null;
		}
		Map<Integer, Integer> roleMap = city.getRoleMap();// 该城市的封地玩家集合
		message.put(city.getNation());
		message.putInt(city.getCityId());
		message.putString(CityInfoConfigCache.getCityInfoConfigById(cityId).getCityName());
		int needFood = 0;
		if (role != null) {
			needFood = this.checkFood(role);// 所需粮草
		}
		message.putInt(needFood);

		message.putInt(roleMap.size());// 显示封地数量
		for (int i = 1; i <= roleMap.size(); i++) {
			if (roleMap.containsKey(i)) {
				Integer id = roleMap.get(i);// 玩家id
				Role role2 = roleService.getRoleById(id);// 根据玩家id查询玩家信息
				message.putInt(id);
				message.putShort(role2.getLv());
				message.putString(role2.getName());
				message.putInt(i);
				Legion legion = LegionCache.getLegionById(role2.getLegionId());
				if (null != legion) {
					message.putString(legion.getName());
				} else {
					message.putString("暂无");
				}
				message.putInt(0);
				int myLordId = role2.getRoleCity().getMyLordRoleId();
				String myLordName = "";
				if (myLordId == 0) {
					myLordName = "暂无";
				} else {
					myLordName = roleService.getRoleById(myLordId).getName();
				}
				message.putString(myLordName);
				message.putInt(role2.getPrestige());

				long nowTime = Utils.getNowTime();
				long useTime = nowTime - role2.getProTime();
				if (useTime > CityConstant.PRO_TIME) {
					useTime = 0;
				} else {
					useTime = CityConstant.PRO_TIME - useTime;
				}
				message.putInt((int) useTime);
				int farmHourInCome = inComeService.getHourIncomeValue(role2, (byte) 2);
				int houseHourInCome = inComeService.getHourIncomeValue(role2, (byte) 1);
				int barrackHourInCome = inComeService.getHourIncomeValue(role2, (byte) 3);
				message.putInt(farmHourInCome);
				message.putInt(houseHourInCome);
				message.putInt(barrackHourInCome);
				RankConfig rankConfig = RankConfigCache.getRankConfigById(role2.getRank());
				message.putString(rankConfig.getName());
				if (role == null) {
					message.put((byte) 3);
				} else {
					// CityInfoConfig infoConfig =
					// CityInfoConfigCache.getCityInfoConfigById(role.getRoleCity().getCityId());
					// CityInfoConfig infoConfig2 =
					// CityInfoConfigCache.getCityInfoConfigById(cityId);
					if (this.checkOwnPosition(cityId, role, i)) {
						message.put((byte) 3);
					} else if (this.checkCanConquer(cityId, role, id)) {
						message.put((byte) 1);
					} else if (this.checkCanAttack(role, cityId)) {
						message.put((byte) 2);
					} else if (this.checkCanNotConquer(role, cityId)) {
						message.put((byte) 4);
					} else {
						message.put((byte) 5);
					}
				}
			} else {
				message.putInt(0);
				message.putShort((short) 0);
				message.putString("0");
				message.putInt(i);
				message.putString("0");
				message.putInt(0);
				message.putString("0");
				message.putInt(0);
				message.putInt(0);
				message.putInt(0);
				message.putInt(0);
				message.putInt(0);
				message.putString("0");
				message.put((byte) 0);
			}

		}
		return message;
	}

	private boolean checkCanNotConquer(Role role, int cityId) {
		CityInfoConfig infoConfig2 = CityInfoConfigCache.getCityInfoConfigById(cityId);
		return role.getNation() == infoConfig2.getNation();
	}

	private boolean checkCanAttack(Role role, int cityId) {
		CityInfoConfig infoConfig = CityInfoConfigCache.getCityInfoConfigById(role.getRoleCity().getCityId());
		CityInfoConfig infoConfig2 = CityInfoConfigCache.getCityInfoConfigById(cityId);
		return infoConfig.getLvMaxNeed() == infoConfig2.getLvMaxNeed()
				&& infoConfig.getNation() != infoConfig2.getNation();
	}

	private boolean checkCanConquer(int cityId, Role role, int roleId) {
		// if(role.getRoleCity().getCityId() == cityId &&
		// role.getRoleCity().getMapKey() != mapKey)
		// return true;
		// 判断是自己的城市
		if (role.getRoleCity().getCityId() == cityId) {
			// 检查封地编号所属的玩家是否是已经征服的
			List<VassalData> list = role.getRoleCity().getVassalRoleId();
			for (VassalData v : list) {
				if (v.getRoleId() == roleId)
					return false;
			}
			RankConfig rankConfig = RankConfigCache.getRankConfigById(role.getRank());
			if (list.size() >= rankConfig.getMaxCity()) {
				return false;
			}
		}

		return true;
	}

	private boolean checkOwnPosition(int cityId, Role role, int mapKey) {
		return role.getRoleCity().getCityId() == cityId && role.getRoleCity().getMapKey() == mapKey;
	}

	@Override
	public Message joinCity(Role role, Integer cityId) {
		Message message = new Message();
		message.setType(CityConstant.JOIN_CITY);

		if (null == CityInfoConfigCache.getCityInfoConfigById(cityId)) {
			message.putShort(ErrorCode.NO_CITY);
			return message;
		}
		CityInfoConfig cityInfoConfig = CityInfoConfigCache.getCityInfoConfigById(cityId);
		// 判断是不是自己的阵营 不是自己阵营不准加入
		if (role.getNation() != cityInfoConfig.getNation()) {
			message.putShort(ErrorCode.NOT_YOUR_NATION);
			return message;
		}
		if (role.getLv() < cityInfoConfig.getLvNum() && role.getLv() >= cityInfoConfig.getLvMaxNeed()) {
			// 判断角色等级是否到达要求
			message.putShort(ErrorCode.NO_LV);
			return message;
		}
		// 该封地的位置key value玩家id
		Map<Integer, Integer> cityKeymap = CityCache.getCityRoleKeyByCityIdMap(cityId);
		int maxI = Utils.getMaxKeyByMap(cityKeymap);
		if (role.getRoleCity().getCityId() > 0) {
			// 不能重复加入封地
			if (role.getRoleCity().getCityId() == cityId) {
				message.putShort(ErrorCode.NOT_JOIN_CITY);
				return message;
			} else {
				// 加入缓存
				CityCache.getCityRoleByCityId(cityId).add(role.getId());
				// 移除缓存
				CityCache.getCityRoleByCityId(role.getRoleCity().getCityId()).remove(role.getId());
				// 将原来的封地位置移除
				CityCache.getCityRoleKeyByCityIdMap(role.getRoleCity().getCityId()).remove(
						role.getRoleCity().getMapKey());
				// 该封地的位置key value玩家id
				for (int i = 1; i <= (maxI + 1); i++) {
					if (!cityKeymap.containsKey(i)) {
						cityKeymap.put(i, role.getId());
						RoleCity roleCity = new RoleCity();
						roleCity.setCityId(cityId);
						roleCity.setMapKey(i);
						roleCity.setRoleId(role.getId());
						role.setRoleCity(roleCity);
						break;
					}
				}

			}
		} else {// 原来没封地
				// 加入缓存
			CityCache.getCityRoleByCityId(cityId).add(role.getId());
			// 移除缓存
			CityCache.getCityRoleByCityId(role.getRoleCity().getCityId()).remove(role.getId());
			for (int i = 1; i <= (maxI + 1); i++) {
				if (!cityKeymap.containsKey(i)) {
					cityKeymap.put(i, role.getId());
					RoleCity roleCity = new RoleCity();
					roleCity.setCityId(cityId);
					roleCity.setMapKey(i);
					roleCity.setRoleId(role.getId());
					role.setRoleCity(roleCity);
					break;
				}
			}
		}
		this.taskService.checkNationTask(role, (byte) 2);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(role.getRoleCity().getCityId());
		message.putInt(role.getRoleCity().getMapKey());
		return message;
	}

	@Override
	public Message worldInfo(Role role) {// 获取世界信息
		Message message = new Message();
		message.setType(CityConstant.WORLD_INFO);
		int nowTime = Utils.getNowTime();
		Map<Integer, City> cityMap = CityCache.getAllCityMap();
		// 去掉不是自己国家的两个小山村
		int size = cityMap.size() - 2;
		message.putInt(size);
		for (Map.Entry<Integer, City> entry : cityMap.entrySet()) {
			City city = entry.getValue();
			CityInfoConfig cityConf = CityInfoConfigCache.getCityInfoConfigById(city.getCityId());
			String legionName = "";
			if (city.getLegionId() != 0) {
				legionName = LegionCache.getLegionById(city.getLegionId()).getName();
			}
			String masterName = "";
			if (city.getCityOw() != 0) {
				masterName = roleService.getRoleById(city.getCityOw()).getName();
			}

			// 如果是小山村
			Map<Integer, Byte> villageNationMap = CityCache.getVillageNationMap();
			if (villageNationMap.containsKey(city.getCityId())) {
				if (villageNationMap.get(city.getCityId()) == role.getNation()) {
					// 如果是自己国家的小山村
					message.putInt(city.getCityId());
					message.putString(cityConf.getCityName());
					if (this.checkFirstRunVillage(role, city.getCityId())) {
						message.putInt(0);
					} else {
						message.putInt(role.getNation());
					}
					message.putString("");
					message.putString(role.getName());
					message.put((byte) 0);
					message.putInt(0);
					message.putInt(cityConf.getGarrisonHero() + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX);
					message.putString(cityConf.getAward());
				}
				continue;
			}

			message.putInt(city.getCityId());
			message.putString(cityConf.getCityName());
			message.putInt(city.getNation());
			message.putString(legionName);
			message.putString(masterName);
			message.put((byte) city.getAtkStartTimeMap().size());
			for (Entry<Integer, CityFire> timeEntry : city.getAtkStartTimeMap().entrySet()) {
				int pathId = timeEntry.getKey();
				CityFire cityFire = timeEntry.getValue();

				message.putInt(pathId);
				message.putInt(cityFire.getStartTime());
				message.put(cityFire.getNation());
			}
			message.putInt(this.getRemainProtectedTime(nowTime, city));
			message.putInt(cityConf.getGarrisonHero() + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX);
			message.putString(cityConf.getAward());
		}

		ConcurrentHashMap<String, WorldMarch> allMarchMap = WorldMarchCache.getAllMarch();
		int allMarchMapSize = allMarchMap.size();
		ConcurrentHashMap<String, WorldMarch> guideMarchMap = WorldMarchCache.getGuideAllMarch();
		// 只显示自己的小山村行军
		ConcurrentHashMap<String, WorldMarch> myGuideMarchMap = new ConcurrentHashMap<String, WorldMarch>();
		for (WorldMarch worldMarch : guideMarchMap.values()) {
			WorldArmy worldArmy = worldMarch.getWorldArmy();
			if (role.getWorldArmySet().contains(worldArmy)) {
				String id = worldMarch.getId();
				myGuideMarchMap.put(id, worldMarch);
			}
		}
		int myGuideMarchMapSize = myGuideMarchMap.size();
		int marchMapSize = allMarchMapSize + myGuideMarchMapSize;

		ConcurrentHashMap<String, WorldMarch> map = new ConcurrentHashMap<>();
		map.putAll(allMarchMap);
		map.putAll(myGuideMarchMap);

		message.putInt(marchMapSize);
		for (Entry<String, WorldMarch> marchEntry : map.entrySet()) {
			String id = marchEntry.getKey();
			WorldMarch worldMarch = marchEntry.getValue();
			int totalTime = worldMarch.getMarchTotalTime();
			City currentCity = worldMarch.getCurrentCity();
			City targetCity = worldMarch.getTargetCity();
			City finalCity = worldMarch.getFinalCity();
			int startTime = worldMarch.getMarchStartTime();
			int pathId = worldMarch.getCurrentPath();
			byte nation = worldMarch.getNation();
			int currentCityId = currentCity.getCityId();
			int targetCityId = targetCity.getCityId();
			int finalCityId = finalCity.getCityId();
			WorldWarPath worldWarPath = WorldWarPathCache.getWorldWarPathByCities(currentCityId, targetCityId);
			int marchTime = worldWarPath.getMarchTime();
			int remainTime = marchTime - (nowTime - startTime);
			int totalRemainTime = this.getWorldMarchTotalRemainTime(worldMarch, nowTime);
			remainTime = remainTime < 0 ? 0 : remainTime;
			WorldArmy worldArmy = worldMarch.getWorldArmy();
			byte myself = (byte) (RoleCache.getRoleById(worldArmy.getRoleId()) == role ? 1 : 0);
			StringBuilder sb = new StringBuilder();
			if (myself == 1) {
				Map<Byte, Integer> data = worldArmy.getFormationData().getData();
				for (int i = 1; i < data.size(); i++) {
					int heroId = data.get((byte) i);
					if (heroId > 2) {
						sb.append(heroId).append(",");
					}
				}
			}
			int location = worldArmy.getLocation();
			int roleId = worldArmy.getRoleId();
			Role worldMarchRole = roleService.getRoleById(roleId);

			message.putString(id);
			message.put(nation);
			message.putInt(totalTime);
			message.putInt(remainTime);
			message.putInt(pathId);
			message.putInt(currentCityId);
			message.putInt(targetCityId);
			message.putInt(marchTime);
			message.put(myself);
			message.putString(sb.toString());
			message.putInt(location);
			message.putInt(totalRemainTime);
			message.putInt(finalCityId);
			message.putString(worldMarchRole.getName());
		}
		message.putInt(role.getStrongHold());
		int legionId = role.getLegionId();
		Legion legion = LegionCache.getLegionById(legionId);
		int legionCityId = this.getRoleLegionCityId(legion, nowTime);
		int deltaTime = this.getLegionCityDeltaTime(legion,nowTime);
		message.putInt(legionCityId);
		message.putInt(deltaTime);
		
		return message;
	}
	
	/**
	 * 获得公会旗子的城市id
	 * @param role
	 * @param nowTime
	 * @return
	 * @author wcy 2016年3月8日
	 */
	private int getRoleLegionCityId(Legion legion,int nowTime){		
		if(legion!=null){
			
			int startTime = legion.getLastCityTime();
			int useTime = nowTime - startTime;
			int effectTime = GeneralNumConstantCache.getValue("LEGION_EFFECTIVE_TIME");
			if (useTime < effectTime) {
				return legion.getCityId();
			}
		}

		return 0;
	}
	
	/**
	 * 公会旗子有效剩余时间
	 * 
	 * @return
	 * @author wcy 2016年3月8日
	 */
	private int getLegionCityDeltaTime(Legion legion, int nowTime) {
		int deltaTime = 0;
		if (legion != null) {
			deltaTime = legion.getLastCityTime() + GeneralNumConstantCache.getValue("LEGION_EFFECTIVE_TIME") - nowTime;
			if (deltaTime < 0) {
				deltaTime = 0;
			}

		}
		return deltaTime;
	}
	
	@Override
	public Message showLegionCity(Role role) {
		Message message = new Message();
		message.setType(CityConstant.SHOW_LEGION_CITY);
		int nowTime = Utils.getNowTime();
		
		int legionId = role.getLegionId();
		Legion legion = LegionCache.getLegionById(legionId);
		
		int legionCityId = this.getRoleLegionCityId(legion,nowTime);
		int deltaTime = this.getLegionCityDeltaTime(legion,nowTime);
		message.putInt(legionCityId);
		message.putInt(deltaTime);			

		
		return message;
	}

	@Override
	public Message getCoreInfo(Role role, int cityId) {
		City city = CityCache.getCityByCityId(cityId);
		if (city == null)
			return null;
		Message message = new Message();
		message.setType(CityConstant.GET_FIEF_INFO);
		int num = city.getCityOwGet().getNum() / 2;
		message.putInt(num);
		message.putInt(num);
		message.putInt(city.getCoreDefInfoMap().size());
		for (WorldArmy x : city.getCoreDefInfoMap()) {
			message.putString(RoleCache.getRoleById(x.getRoleId()).getName());
			message.putInt(x.getHeroCaptainId());
		}
		return message;
	}

	@Override
	public Message getCityMineFarm(Role role, Integer cityId) {
		Message message = new Message();
		message.setType(CityConstant.GET_MINE_FARM);
		City city = CityCache.getCityByCityId(cityId);
		if (city == null)
			return null;

		if (this.checkVillage(cityId)) {
			message.putInt(1);
			message.putInt(15308);
			String id = role.getVillageMineFarmWorldArmyId();
			String occupyName = "";
			int heroCaptainId = 0;

			int startTime = role.getVillageMineFarmStartTime();
			int useTime = Utils.getNowTime() - startTime;
			if (useTime > CityConstant.PRO_TIME) {
				occupyName = CityConstant.HERO_LOCATION_NULL + "";
				useTime = 0;
			} else {
				occupyName = role.getName();
				useTime = (int) (CityConstant.PRO_TIME - useTime);
			}

			WorldArmy w = role.getWorldArmyById(id);
			if (w != null) {
				heroCaptainId = w.getHeroCaptainId();
			}

			message.putString(occupyName);
			message.putInt(useTime);
			message.putInt(heroCaptainId);
			message.putString(role.getName());

		} else {
			Map<Integer, MineFarm> temp = CityCache.getAllMine().get(cityId);
			if (temp == null) {
				return message;
			}
			message.putInt(temp.size());
			for (MineFarm x : temp.values()) {
				this.checkMineFameTime(x);
				message.putInt(x.getBuildingID());
				if (x.getRoleId() == CityConstant.HERO_LOCATION_NULL) {
					message.putString(CityConstant.HERO_LOCATION_NULL + "");
				} else {
					message.putString(RoleCache.getRoleById(x.getRoleId()).getName());
				}
				int p = 21600;
				int nowTime = Utils.getNowTime();
				message.putInt(x.getStarTime() + p - nowTime);
				Role tempRole = RoleCache.getRoleById(x.getRoleId());
				int heroId = 0;
				if (tempRole != null) {
					WorldArmy worldArmy = tempRole.getWorldArmyById(x.getWorldArmyId());
					heroId = worldArmy.getHeroCaptainId();
				}
				message.putInt(heroId);
			}

			Role lordRole = RoleCache.getRoleById(city.getCityOw());
			String lordName = lordRole == null ? "" : lordRole.getName();
			message.putString(lordName);
		}

		return message;
		// // Map<Integer, Integer>
		// moneyMap=CityCache.getCityMoneyKeyMap(cityId);
		// // Map<Integer, Integer> foodMap=CityCache.getCityFoodKeyMap(cityId);
		// int mineSize = MineFarmConfigCache.getMineFarmConfigMap(cityId,
		// 1).size();
		// int farmSize = MineFarmConfigCache.getMineFarmConfigMap(cityId,
		// 2).size();
		// mineSize = mineSize >= farmSize
		// ? mineSize
		// : farmSize;
		// message.putInt(mineSize);
		// for (int i = 1; i <= mineSize; i++) {
		// // int roleId=moneyMap.get(i)==null?0:moneyMap.get(i);
		// message.putInt(i);
		// int lv1 = CityUtils.getMineFramLv(cityId, 1, i);// 矿藏等级
		// int outPut1 = CityUtils.getOutPut(cityId, 1, i);// 矿藏产量
		// MineFarm mineFarm1 = CityCache.getMineFarmById(cityId, 1, i);
		// int roleId1 = 0;
		// String name1 = "NPC"; // 姓名
		// int overplusTime1 = 0; // 占领剩余时间
		// int overCrazyTime1 = 0; // 疯狂模式剩余时间
		// int overProtectTime1 = 0; // 剩余保护时间
		// int useTime1 = 0; // 已开采时间
		// int useCrazyTime1 = 0; // 玩家享用疯狂模式时间
		// byte nation1 = 0;
		// ChapterConfig cc1 = ChapterConfigCache.getChapterConfigById(80000);
		// short roleLv1 = (short) cc1.getLv();
		// if (mineFarm1 != null) {
		// roleId1 = mineFarm1.getRoleId();
		// overplusTime1 = mineFarm1.getOverplusTime();
		// overCrazyTime1 = mineFarm1.getOverCrazyTime();
		// overProtectTime1 = mineFarm1.getOverProtectTime();
		// useTime1 = mineFarm1.getUseTime();
		// useCrazyTime1 = mineFarm1.getUseCrazyTime();
		// if (roleId1 != 0) {// 有玩家发送玩家id 没有发送NPC
		// Role role1 = roleService.getRoleById(roleId1);
		// name1 = role1.getName();
		// nation1 = role1.getNation();
		// roleLv1 = role1.getLv();
		// }
		//
		// }
		// int nowOutPut1 = (outPut1 * (int) useTime1) + (int) (useCrazyTime1 *
		// outPut1 * CityConstant.CONSUME_ADD);
		// byte isMe1 = 0;
		// if (roleId1 == role.getId()) {
		// isMe1 = 1;
		// }
		//
		// message.putInt(lv1);
		// message.putInt(roleId1);
		// message.putShort(roleLv1);
		// message.put(nation1);
		// message.putString(name1);
		// message.putInt(outPut1);// 产量
		// message.putInt(overplusTime1);
		// message.putInt(overCrazyTime1);
		// message.putInt(overProtectTime1);
		// message.putInt(nowOutPut1);
		// message.put(isMe1);
		//
		// int lv2 = CityUtils.getMineFramLv(cityId, 2, i);// 矿藏等级
		// int outPut2 = CityUtils.getOutPut(cityId, 2, i);// 矿藏产量
		// MineFarm mineFarm2 = CityCache.getMineFarmById(cityId, 2, i);
		// int roleId2 = 0;
		// String name2 = "NPC"; // 姓名
		// int overplusTime2 = 0; // 占领剩余时间
		// int overCrazyTime2 = 0; // 疯狂模式剩余时间
		// int overProtectTime2 = 0; // 剩余保护时间
		// int useTime2 = 0; // 已开采时间
		// int useCrazyTime2 = 0; // 玩家享用疯狂模式时间
		// ChapterConfig cc2 = ChapterConfigCache.getChapterConfigById(80000);
		// short roleLv2 = (short) cc2.getLv();
		// byte nation2 = 0;
		// if (mineFarm2 != null) {
		// roleId2 = mineFarm2.getRoleId();
		// overplusTime2 = mineFarm2.getOverplusTime();
		// overCrazyTime2 = mineFarm2.getOverCrazyTime();
		// overProtectTime2 = mineFarm2.getOverProtectTime();
		// useTime2 = mineFarm2.getUseTime();
		// useCrazyTime2 = mineFarm2.getUseCrazyTime();
		// if (roleId2 != 0) {// 有玩家发送玩家id 没有发送NPC
		// Role role2 = roleService.getRoleById(roleId2);
		// name2 = role2.getName();
		// nation2 = role2.getNation();
		// }
		//
		// }
		// int nowOutPut2 = (outPut2 * (int) useTime2) + (int) (useCrazyTime2 *
		// outPut2 * CityConstant.CONSUME_ADD);
		// byte isMe2 = 0;
		// if (roleId2 == role.getId()) {
		// isMe2 = 1;
		// }
		// message.putInt(lv2);
		// message.putInt(roleId2);
		// message.putShort(roleLv2);
		// message.put(nation2);
		// message.putString(name2);
		// message.putInt(outPut2);// 产量
		// message.putInt(overplusTime2);
		// message.putInt(overCrazyTime2);
		// message.putInt(overProtectTime2);
		// message.putInt(nowOutPut2);
		// message.put(isMe2);
		// }
	}

	/**2307
	 * 获取单一资源信息
	 */
	public Message getMineFarmInfo(Role role, int cityId, int buildId) {
		Message message = new Message();
		message.setType(CityConstant.GET_MINE_FARM_INFO);
		int nowTime = Utils.getNowTime();
		int p = 6 * 60 * 60;

		String roleName = "";
		int time = 0;
		int coinIncome = 0;
		int foodIncome = 0;
		int riceIncome = 0;
		int mineralIncome = 0;
		int goldIncome = 0;
		int heroDebris = 0;
		int propability = 0;
		if (this.checkVillage(cityId)) {
			byte nation = CityCache.getVillageNationMap().get(cityId);
			int heroId = CityInfoConfigCache.getCityInfoConfigById(cityId).getGarrisonHero();
			if (role.getVillageMineFarmWorldArmyId() != null && !role.getVillageMineFarmWorldArmyId().equals("")) {
				roleName = role.getName();
				int num = role.getVillageMineFarmStartTime() + p - nowTime;
				time = num;
			} else {
				roleName = 0 + "";
				time = 0;
			}
			coinIncome = 0;
			foodIncome = 0;
			riceIncome = 0;
			mineralIncome = 0;
			goldIncome = 0;
			heroDebris = heroId + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX;
			propability = CityConstant.VILLAGE_PROPBILITY / 100;
		} else {
			MineFarm mineFarm = CityCache.getMineFarmById(cityId, buildId);
			MineFarmConfig config = MineFarmConfigCache.getMineFarmConfigMap(buildId);
			if (mineFarm == null || config == null)
				return null;

			Role temp = RoleCache.getRoleById(mineFarm.getRoleId());

			if (temp == null) {
				roleName = 0 + "";
				time = 0;
			} else {
				roleName = temp.getName();
				int num = mineFarm.getStarTime() + p - nowTime;
				time = num;
			}
			coinIncome = config.getCoinIncome();
			foodIncome = config.getFoodIncome();
			riceIncome = config.getRiceIncome();
			mineralIncome = config.getMineralIncome();
			goldIncome = config.getGoldIncome();
			heroDebris = config.getHeroDebris();
			propability = config.getProbiblity() / 100;
		}

		message.putString(roleName);
		message.putInt(time);
		message.putInt(coinIncome);
		message.putInt(foodIncome);
		message.putInt(riceIncome);
		message.putInt(mineralIncome);
		message.putInt(goldIncome);
		message.putInt(heroDebris);
		message.putInt(propability);

		return message;
	}

	@Override
	public Message crazyCityMineFarm(Role role, Integer cityId, Integer buildId) {
		Message message = new Message();
		message.setType(CityConstant.CRAZY_CITY_MINE_FARM);
		// MineFarm mineFarm = CityCache.getMineFarmById(cityId, type, key);
		// if (mineFarm == null) {// 参数不合法
		// return null;
		// }
		// int roleId = mineFarm.getRoleId();
		// if (roleId == 0 || roleId != role.getId()) {// 不是你的封地
		// message.putShort(ErrorCode.NO_HOME);
		// return message;
		// }
		// if (mineFarm.getCrazyStarTime() > 0) {// 不可以重复开启
		// message.putShort(ErrorCode.NO_REPEAT_OPEN);
		// return message;
		// }
		// if (role.getGold() < CityConstant.CONSUME_GOLD) {// 金币不足
		// message.putShort(ErrorCode.NO_GOLD);
		// return message;
		// }
		// roleService.addRoleGold(role, CityConstant.CONSUME_GOLD);
		// int nowTime = (int) (System.currentTimeMillis() / 1000);
		// mineFarm.setCrazyStarTime(nowTime);
		// 判断权限
		int needG = GeneralNumConstantCache.getValue("DOUBLE_P_NEED");
		boolean isVillage = false;
		MineFarm mineFarm = null;
		if (this.checkVillage(cityId)) {
			isVillage = true;
		} else {
			mineFarm = CityCache.getMineFarmById(cityId, buildId);
			MineFarmConfig config = MineFarmConfigCache.getMineFarmConfigMap(buildId);
			if (config == null || mineFarm == null) {
				return null;
			}
			if (config.getHeroDebris() == CityConstant.NULL_HERO) {
				message.putShort(ErrorCode.MINE_FARM_CRAZY_ERROR);
				return message;
			}
			// 判断是否有权限
			if (role.getId() != mineFarm.getRoleId()) {
				message.putShort(ErrorCode.NO_MINE_FARM);
				return message;
			}
		}

		// 判断金币
		if (role.getGold() < needG) {
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		// 开启翻倍
		roleService.addRoleGold(role, -needG);
		WorldDoubleP p = new WorldDoubleP();
		p.setRoleId(role.getId());
		p.setStartTime(Utils.getNowTime());
		if (isVillage) {
			role.setWorldDoubleP(p);
		} else {
			mineFarm.getDoublePMap().put(p.getRoleId(), p);
		}
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	@Override
	public Message OccupyCityMineOrFarm(Role role, int cityId, int buildId, String worldArmyId) {
		Message message = new Message();
		message.setType(CityConstant.OCCUPY_MINE_OR_FARM);
		int nowTime = Utils.getNowTime();
		WorldArmy worldArmy = role.getWorldArmyById(worldArmyId);

		if (worldArmy == null) {
			return null;
		}
		// 判断部队位置
		if (worldArmy.getLocation() != cityId) {
			message.putShort(ErrorCode.ERR_ID_WORLDARMY);
			return message;
		}

		// 检查是否是小山村
		if (this.checkVillage(cityId)) {
			if (role.getWorldArmyById(role.getVillageMineFarmWorldArmyId()) != null) {
				message.putShort(ErrorCode.CAN_NOT_FIGHT_SELF);
				return message;
			}
			role.setVillageMineFarmWorldArmyId(worldArmyId);
			role.setVillageMineFarmStartTime(nowTime);

			message.putShort(ErrorCode.SUCCESS);
			message.putString("");
			message.putString("");
			message.putInt(0);
			message.putInt(0);
			message.putInt(0);
			message.putString("");
			message.putString("");
			message.putString("");
			message.putInt(0);
			message.putInt(0);
			message.putInt(0);

			return message;
		}

		MineFarm mineFarm = CityCache.getMineFarmById(cityId, buildId);
		MineFarmConfig config = MineFarmConfigCache.getMineFarmConfigMap(buildId);
		if (config == null || mineFarm == null || worldArmy == null) {
			return null;
		}

		if (mineFarm.getRoleId() == worldArmy.getRoleId()) {
			message.putShort(ErrorCode.CAN_NOT_FIGHT_SELF);
			return message;
		}
		// // 判断贡献是否足够
		// TODO 加上贡献
		// Map<Integer, ContributeRank> temp =
		// city.getContributeMap().get(role.getNation());
		// ContributeRank rank = temp.get(role.getId());
		// if (rank == null || rank.getContribute() < config.getContribute()) {
		// message.putShort(ErrorCode.NO_CONTRIBUTION);
		// return message;
		// }
		// rank.setRankNum(rank.getContribute() - config.getContribute());
		// 判断玩家使用部队是否有占领矿
		for (MineFarm x : CityCache.getAllMine().get(cityId).values()) {
			if (x.getWorldArmyId() != null && x.getWorldArmyId().equals(worldArmyId)) {
				x.setRoleId(CityConstant.NULL_HERO);
				x.setWorldArmyId("");
			}
		}
		// 判断是否需要战斗
		// TODO 逻辑发生改变 此处战斗需要播放战报
		ResultData resultData = null;
		if (mineFarm.getRoleId() != CityConstant.NULL_HERO) {
			TroopData atk = PVPUitls.getTroopDataByWorldArmy(worldArmy);
			WorldArmy old = RoleCache.getRoleById(mineFarm.getRoleId()).getWorldArmyById(mineFarm.getWorldArmyId());
			TroopData def = PVPUitls.getTroopDataByWorldArmy(old);
			resultData = new Battle().fightPVP(atk, def);
			resultsDao.insertResults(resultData);
		}
		// 判断战斗结果
		Role winRole = null;
		Role lossRole = null;
		WorldArmy winArmy = null;
		WorldArmy lossArmy = null;

		if (resultData == null) {
			winRole = role;
			mineFarm.setRoleId(role.getId());
			mineFarm.setWorldArmyId(worldArmyId);
			mineFarm.setStarTime(nowTime);
			winArmy = worldArmy;
		} else if (resultData.winCamp == 1) {
			winRole = role;
			lossRole = RoleCache.getRoleById(resultData.defPlayerId);
			winArmy = worldArmy;
			lossArmy = lossRole.getWorldArmyById(mineFarm.getWorldArmyId());
			mineFarm.setRoleId(role.getId());
			mineFarm.setWorldArmyId(worldArmyId);
			mineFarm.setStarTime(nowTime);
		} else {
			// 失败
			winRole = RoleCache.getRoleById(resultData.defPlayerId);
			lossRole = role;
			winArmy = winRole.getWorldArmyById(mineFarm.getWorldArmyId());
			lossArmy = worldArmy;
		}
		message.putShort(ErrorCode.SUCCESS);
		if (resultData == null) {
			message.putString("");
			message.putString("");
			message.putInt(0);
			message.putInt(0);
			message.putInt(0);
			message.putString("");
			message.putString("");
			message.putString("");
			message.putInt(0);
			message.putInt(0);
			message.putInt(0);
		} else {
			message.putString(resultData.uuid);
			message.putString(resultData.time);
			message.putInt(resultData.winCamp);
			message.putInt(resultData.attPlayerId);
			message.putInt(resultData.defPlayerId);
			message.putString(resultData.attName);
			message.putString(resultData.defName);
			message.putString(resultData.report);
			message.putInt(resultData.attLost);
			message.putInt(resultData.defLost);
			message.putInt(resultData.fRound);
		}
		String uuid = resultData == null ? "" : resultData.uuid;
		// 世界大战记录
		if (lossRole != null) {
			serverService.saveHistoryRole(ServerConstant.LOSS_MINE, lossRole, mineFarm, lossArmy, uuid);
		}
		return message;
	}

	/**
	 * 获取玩家占矿可用部队信息
	 */
	public Message canUseArmyMine(Role role, int cityId) {
		City city = CityCache.getCityByCityId(cityId);
		if (city == null)
			return null;
		Message message = new Message();
		message.setType(CityConstant.GET_CAN_USE_ARMY_MINE);

		if (this.checkVillage(cityId)) {
			List<WorldArmy> list = role.getVillageDefList();
			String id = role.getVillageMineFarmWorldArmyId();

			if (id == null || id.equals("")) {
				message.putInt(list.size());
				for (WorldArmy x : list) {
					message.putString(x.getId());
					message.putInt(x.getHeroCaptainId());
					message.putString(x.getName());
				}
			} else {
				List<WorldArmy> l = new ArrayList<>();

				for (WorldArmy x : list) {
					if (id.equals(x.getId())) {
						l.add(x);
					}
				}
				message.putInt(l.size());
				for (WorldArmy x : l) {
					message.putString(x.getId());
					message.putInt(x.getHeroCaptainId());
					message.putString(x.getName());
				}
			}

		} else {
			// 循环玩家部队列表
			Map<String, WorldArmy> temp = new HashMap<String, WorldArmy>();
			for (WorldArmy x : role.getWorldArmySet()) {
				if (x.getLocation() == cityId) {
					temp.put(x.getId(), x);
				}
			}

			// 过滤部队是否已经有占矿

			for (MineFarm x : CityCache.getAllMine().get(cityId).values()) {
				if (temp.containsKey(x.getWorldArmyId())) {
					temp.remove(x.getWorldArmyId());
				}
			}
			message.putInt(temp.size());
			for (WorldArmy x : temp.values()) {
				message.putString(x.getId());
				message.putInt(x.getHeroCaptainId());
				message.putString(x.getName());
			}
		}

		return message;
	}

	/**
	 * 半点奖励
	 * 
	 * @author xjd
	 */
	public void sendMineFramAward(MineFarm mineFarm) {
		if (mineFarm.getRoleId() == CityConstant.NULL_HERO) {
			return;
		}
		// 检查将魂翻倍
		if (mineFarm.getDoublePMap().size() > 0) {
			int cd = GeneralNumConstantCache.getValue("DOUBLE_P");
			List<Integer> temp = new ArrayList<Integer>();
			for (WorldDoubleP x : mineFarm.getDoublePMap().values()) {
				if (Utils.getNowTime() > x.getStartTime() + cd) {
					temp.add(x.getRoleId());
				}
				for (Integer xx : temp) {
					mineFarm.getDoublePMap().remove(xx);
				}
			}
		}
		// 判断是否应该自动离开
		if (Utils.getNowTime() - mineFarm.getStarTime() >= 21600) {
			mineFarm.setRoleId(0);
			mineFarm.setWorldArmyId("");
			mineFarm.setChange(true);
			return;
		}
		MineFarmConfig config = MineFarmConfigCache.getMineFarmConfigMap(mineFarm.getBuildingID());
		Role targetRole = RoleCache.getRoleById(mineFarm.getRoleId());
		City city = CityCache.getCityByCityId(mineFarm.getCityId());
		CityOwGet owGet = city.getCityOwGet();
		Set<ChapterReward> roleRewards = new HashSet<ChapterReward>();
		int p = GeneralNumConstantCache.getValue("CITY_OW_GET");
		// 银币
		if (config.getCoinIncome() > 0) {
			ChapterReward reward = new ChapterReward();
			reward.setType(ChapterConstant.AWARD_MONEY);
			reward.setItemId(CityConstant.NO_ITEM_ID);
			int num = config.getCoinIncome() * p / 100;
			reward.setNum(config.getCoinIncome() - num);
			roleRewards.add(reward);
			owGet.addCoin(num);
		}
		// 粮食
		if (config.getFoodIncome() > 0) {
			ChapterReward reward = new ChapterReward();
			reward.setType(ChapterConstant.AWARD_FOOD);
			reward.setItemId(CityConstant.NO_ITEM_ID);
			int num = config.getFoodIncome() * p / 100;
			reward.setNum(config.getFoodIncome() - num);
			roleRewards.add(reward);
			owGet.addFood(num);
		}
		// 大米
		if (config.getRiceIncome() > 0) {
			ChapterReward reward = new ChapterReward();
			reward.setType(ChapterConstant.AWARD_ITEM);
			reward.setItemId(CityConstant.ITEM_RICE);
			int num = config.getRemedyCoins() * p / 100;
			reward.setNum(config.getRemedyCoins() - num);
			roleRewards.add(reward);
			owGet.addRice(num);
		}
		// 铁矿
		if (config.getMineralIncome() > 0) {
			ChapterReward reward = new ChapterReward();
			reward.setType(ChapterConstant.AWARD_ITEM);
			reward.setItemId(CityConstant.ITEM_MINE);
			int num = config.getMineralIncome() * p / 100;
			reward.setNum(config.getMineralIncome() - num);
			roleRewards.add(reward);
			owGet.addMineralIncome(num);
		}
		// 金币
		if (config.getGoldIncome() > 0) {
			ChapterReward reward = new ChapterReward();
			reward.setType(ChapterConstant.AWARD_GOLD);
			reward.setItemId(CityConstant.NO_ITEM_ID);
			int num = config.getGoldIncome() * p / 100;
			reward.setNum(config.getGoldIncome() - num);
			roleRewards.add(reward);
			owGet.addGold(num);
		}
		// 将魂 不加入城主抽成
		if (config.getHeroDebris() > 0) {
			ChapterReward reward = new ChapterReward();
			int rr = Utils.getRandomNum(0, 10000);
			int pp = config.getProbiblity();
			if (mineFarm.getDoublePMap().containsKey(targetRole.getId())) {
				pp *= 2;
			}
			if (rr < pp) {
				reward.setType(ChapterConstant.AWARD_ITEM);
				reward.setItemId(config.getHeroDebris());
				reward.setNum(1);
			} else {
				reward.setType(ChapterConstant.AWARD_MONEY);
				reward.setItemId(CityConstant.NO_ITEM_ID);
				reward.setNum(config.getRemedyCoins());
			}
			roleRewards.add(reward);
		}
		StringBuilder sb = new StringBuilder();
		for (ChapterReward x : roleRewards) {
			sb.append(x.getType()).append(",").append(x.getItemId()).append(",").append(x.getNum()).append(";");
		}
		// 发送邮件
		Mail mail = new Mail();
		mail.setTitle("占矿收益");
		mail.setContext("公元" + ServerCache.getServer().getYear() + "年<br/>" + "您麾下部队名称在" + "城市名称"
				+ "占有矿点名称，为您收缴的资源，请您过目。");
		mail.setAttached(sb.toString());
		mailService.sendSYSMail(targetRole, mail);
	}

	@Override
	public void sendMineFarmAward(Role role) {
		String worldArmyId = role.getVillageMineFarmWorldArmyId();

		// 将魂狂没有驻守则返回
		if (worldArmyId == null || worldArmyId.equals("")) {
			return;
		}

		// 检查将魂翻倍
		int cd = GeneralNumConstantCache.getValue("DOUBLE_P");
		WorldDoubleP wdp = role.getWorldDoubleP();
		if (Utils.getNowTime() > wdp.getStartTime() + cd) {
			wdp.setRoleId(0);
		}

		byte nation = role.getNation();
		int cityId = this.getVillageIdByNation(nation);
		CityInfoConfig config = CityInfoConfigCache.getCityInfoConfigById(cityId);
		int heroId = config.getGarrisonHero();

		ChapterReward reward = new ChapterReward();
		int rr = Utils.getRandomNum(0, 10000);

		int pp = CityConstant.VILLAGE_PROPBILITY;
		if (role.getId() == wdp.getRoleId()) {
			pp *= 2;
		}
		if (rr < pp) {
			reward.setType(ChapterConstant.AWARD_ITEM);
			reward.setItemId(heroId);
			reward.setNum(1);
		} else {
			reward.setType(ChapterConstant.AWARD_MONEY);
			reward.setItemId(CityConstant.NO_ITEM_ID);
			reward.setNum(CityConstant.VILLAGE_COINS);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(reward.getType()).append(",").append(reward.getItemId()).append(",").append(reward.getNum())
				.append(";");
		// 发送邮件
		Mail mail = new Mail();
		mail.setTitle("占矿收益");
		mail.setContext("公元" + ServerCache.getServer().getYear() + "年<br/>" + "您麾下部队名称在" + "城市名称"
				+ "占有矿点名称，为您收缴的资源，请您过目。");
		mail.setAttached(sb.toString());
		mailService.sendSYSMail(role, mail);

		// 判断是否应该自动离开
		if (Utils.getNowTime() - role.getVillageMineFarmStartTime() >= 21600) {
			role.setVillageMineFarmWorldArmyId("");
		}

	}

	// if (mineFarm == null) {// 配置表新增而数据库中不存在的数据插入数据库和缓存中star
	// if (MineFarmConfigCache.getMineFarmConfigMap(cityId, type).get(key) !=
	// null) {
	// MineFarm mine = new MineFarm(cityId, key, type, 0, 0, 0);
	// cityDao.insertMineFarm(mine);
	// mineFarm = CityCache.getMineFarmById(cityId, type, key);
	// Map<Integer, Map<Integer, MineFarm>> m2 = new HashMap<Integer,
	// Map<Integer, MineFarm>>();
	// Map<Integer, MineFarm> m3 = new HashMap<Integer, MineFarm>();
	// m3.put(key, mine);
	// m2.put(type, m3);
	// if (CityCache.getMineFarmMap().get(cityId) == null) {
	// CityCache.getMineFarmMap().put(cityId, m2);
	// } else {
	// if (CityCache.getMineFarmMap().get(cityId).get(type) == null) {
	// CityCache.getMineFarmMap().get(cityId).put(type, m3);
	// } else {
	// CityCache.getMineFarmMap().get(cityId).get(type).put(key, mineFarm);
	// }
	// }
	// } else {
	// return null;
	// }
	// }// end配置表新增而数据库中不存在的数据插入数据库和缓存中
	// if (role.getArmyToken() <= 0) {// 军令不足
	// message.putShort(ErrorCode.NO_ARMY_TOKEN);
	// return message;
	// }
	// if (mineFarm.getOverProtectTime() > 0) {// 保护时间不可攻打
	// message.putShort(ErrorCode.AT_PROTECT);
	// return message;
	// }
	// int rank = role.getRank();
	// RankConfig rankConfig = RankConfigCache.getRankConfigById(rank);
	// int needNum = type == 1
	// ? rankConfig.getMaxMine()
	// : rankConfig.getMaxFarm();
	// Map<Integer, MineFarm> maps =
	// CityCache.getMineFarmMap().get(cityId).get(type);
	// int mineFarmNum = 0;
	// for (Map.Entry<Integer, MineFarm> map : maps.entrySet()) {
	// MineFarm mf = map.getValue();
	// if (mf.getRoleId() == role.getId()) {
	// mineFarmNum++;
	// }
	// }
	// if (mineFarmNum >= needNum) {
	// message.putShort(ErrorCode.MAX_MINE_OR_FARM);
	// return message;
	// }
	// Role role2 = null;
	// int roleId2 = mineFarm.getRoleId();
	// if (roleId2 != 0) {
	// role2 = roleService.getRoleById(roleId2);
	// /**
	// * PVP
	// */
	//
	// resultData = PvP(role, role2, -1);
	// /*
	// * 根据结果判定之后的处理流程
	// */
	//
	// fightService.returnStar(resultData, 1);
	// int outPut = CityUtils.getOutPut(cityId, type, key);
	// int useTime = 0; // 已开采时间
	// int useCrazyTime = 0; // 玩家享用疯狂模式时间
	// if (mineFarm != null) {
	// useTime = mineFarm.getUseTime();
	// useCrazyTime = mineFarm.getUseCrazyTime();
	// }
	// int nowOutPut = (outPut * (int) useTime) + (int) (useCrazyTime * outPut *
	// CityConstant.CONSUME_ADD);
	// if (resultData.winCamp == 1) {// 攻占资源胜利
	// if (type == 1) {// 攻占矿藏资源
	// roleService.addRoleMoney(role2, nowOutPut);
	// Mail mail = new Mail();
	// mail.setTitle("zlpsb欠我一个邮件标题");
	// StringBuffer sb = new StringBuffer();
	// sb.append("被").append(role.getName()).append("攻占,带回").append(nowOutPut).append("银币");
	// mail.setContext(sb.toString());
	// mailService.sendSYSMail2(role2, mail);
	// }
	// if (type == 2) {// 攻占农田资源
	// roleService.addRoleFood(role2, nowOutPut);
	// Mail mail = new Mail();
	// mail.setTitle("zlpsb欠我一个邮件标题");
	// StringBuffer sb = new StringBuffer();
	// sb.append("被").append(role.getName()).append("攻占,带回").append(nowOutPut).append("粮草");
	// mail.setContext(sb.toString());
	// mailService.sendSYSMail2(role2, mail);
	// }
	// int nowTime = (int) (System.currentTimeMillis() / 1000);
	// MineFarm mf = CityCache.getMineFarmById(cityId, type, key);
	// mf.setRoleId(role.getId());
	// mf.setStarTime(nowTime);
	// this.taskService.checkPvp(role, (byte) 3);
	// } else {
	// }
	//
	// } else {
	// roleService.getArmyToken(role, -1);
	// TroopData roleData = PVPUitls.getTroopDataByRole(role);
	// TroopData chapterData = PVPUitls.getTroopDataByChapterId(80000);
	// resultData = new Battle().fightPVP(roleData, chapterData);
	// fightService.returnStar(resultData, 1);
	// if (resultData.winCamp == 1) {
	// int nowTime = (int) (System.currentTimeMillis() / 1000);
	// ConcurrentMap<Integer, Map<Integer, Map<Integer, MineFarm>>> mineFarmMap
	// = CityCache.getMineFarmMap();
	// MineFarm mf = CityCache.getMineFarmById(cityId, type, key);
	// mf.setRoleId(role.getId());
	// mf.setStarTime(nowTime);
	// this.taskService.checkPvp(role, (byte) 3);
	// }
	// }
	//
	// message.putShort(ErrorCode.SUCCESS);
	// message.putString(resultData.uuid);
	// message.putString(resultData.time);
	// message.putInt(resultData.winCamp);
	// message.putInt(resultData.attPlayerId);
	// message.putInt(resultData.defPlayerId);
	// message.putString(resultData.attName);
	// message.putString(resultData.defName);
	// message.putString(resultData.report);
	// message.putInt(resultData.attStars);
	// message.putInt(resultData.attLost);
	// message.putInt(resultData.defStars);
	// message.putInt(resultData.defLost);
	// message.putInt(resultData.fRound);
	// return message;
	// }

	@Override
	public Message dropCityMineOrFarm(Role role, Integer cityId, Integer buildId) {
		Message message = new Message();
		message.setType(CityConstant.DROP_MINE_OR_FARM);

		/**
		 * 只用于小山村
		 */
		if (this.checkVillage(cityId)) {

			// 矿点没有部队
			if (role.getVillageMineFarmWorldArmyId().equals("")) {
				message.putShort(ErrorCode.ERROR_DROP_MINEFARM);
				return message;
			}

			role.setVillageMineFarmWorldArmyId("");
		} else {

			// MineFarm mineFarm = CityCache.getMineFarmById(cityId, type, key);
			// if (mineFarm == null) {// 参数不合法
			// return null;
			// }
			// int roleId = mineFarm.getRoleId();
			// if (roleId == 0 || roleId != role.getId()) {// 不是你的封地
			// message.putShort(ErrorCode.NO_HOME);
			// return message;
			// }
			// Role role2 = roleService.getRoleById(roleId);
			//
			// int outPut = CityUtils.getOutPut(cityId, type, key);
			//
			// int useTime = 0; // 已开采时间
			// int useCrazyTime = 0; // 玩家享用疯狂模式时间
			// if (mineFarm != null) {
			// useTime = mineFarm.getUseTime();
			// useCrazyTime = mineFarm.getUseCrazyTime();
			// }
			// int nowOutPut = (outPut * (int) useTime) + (int) (useCrazyTime *
			// outPut * CityConstant.CONSUME_ADD);
			// if (type == 1) {
			// roleService.addRoleMoney(role2, nowOutPut);
			// } else {
			// roleService.addRoleFood(role2, nowOutPut);
			// }
			// mineFarm = CityCache.getMineFarmById(cityId, type, key);
			// mineFarm.setRoleId(0);
			// mineFarm.setStarTime(0);
			// message.putShort(ErrorCode.SUCCESS);

			MineFarm mineFarm = CityCache.getMineFarmById(cityId, buildId);
			if (mineFarm == null) {// 参数不合法
				return null;
			}

			int roleId = mineFarm.getRoleId();
			if (roleId == 0 || roleId != role.getId()) {// 不是你的封地
				message.putShort(ErrorCode.NO_HOME);
				return message;
			}

			mineFarm.setRoleId(0);
			mineFarm.setStarTime(0);
			// Role role2 = roleService.getRoleById(roleId);
			//
			// int outPut = CityUtils.getOutPut(cityId, type, key);
			//
			// int useTime = 0; // 已开采时间
			// int useCrazyTime = 0; // 玩家享用疯狂模式时间
			// if (mineFarm != null) {
			// useTime = mineFarm.getUseTime();
			// useCrazyTime = mineFarm.getUseCrazyTime();
			// }
			// int nowOutPut = (outPut * (int) useTime) + (int) (useCrazyTime *
			// outPut * CityConstant.CONSUME_ADD);
			// if (type == 1) {
			// roleService.addRoleMoney(role2, nowOutPut);
			// } else {
			// roleService.addRoleFood(role2, nowOutPut);
			// }
			// mineFarm = CityCache.getMineFarmById(cityId, type, key);
			// mineFarm.setRoleId(0);
			// mineFarm.setStarTime(0);
		}
		message.putShort(ErrorCode.SUCCESS);

		return message;
	}

	@Override
	public Message Conquer(Role role, Integer cityId, Integer key, Integer roleId) {
		Message message = new Message();
		message.setType(CityConstant.CONQUER);
		// int roleId2 = CityCache.getCityRoleKeyByCityIdMap(cityId).get(key) ==
		// null ? 0 : CityCache
		// .getCityRoleKeyByCityIdMap(cityId).get(key);
		City city = CityCache.getCityByCityId(cityId);
		int roleId2 = city.getRoleMap().get(key);
		// if (role.getArmyToken() <= 0) {
		// message.putShort(ErrorCode.NO_ARMY_TOKEN);
		// return message;
		// }
		RankConfig rank = RankConfigCache.getRankConfigById(role.getRank());
		if (role.getRoleCity().getVassalRoleId().size() >= rank.getMaxCity()) {
			message.putShort(ErrorCode.MAX_CITY);
			return message;
		}

		// if (role.getRoleCity().getCityId() == 0) {
		// message.putShort(ErrorCode.NO_TARGET);
		// return message;
		// }

		// TODO
		Role roleA = role;// 攻打方
		Role roleB = roleService.getRoleById(roleId);// 防守方
		if (role.getId() == roleId2 || roleB.getRoleCity().getMyLordRoleId() == role.getId())
			return null;

		// 检查粮草
		int tempAllHp = 0;
		FormationData formationData = role.getFormationData().get(1001);
		for (int x : formationData.getData().values()) {
			if (x <= FightConstant.NUM_USE_2)
				continue;
			Hero hero = role.getHeroMap().get(x);
			// hero.setArmsNum(HeroUtils.getMaxHp(role, hero));
			tempAllHp += hero.getArmsNum();
		}
		// tempAllHp = tempAllHp / 10;
		if (role.getFood() < tempAllHp) {
			message.putShort(ErrorCode.NO_FOOD);
			return message;
		}
		roleService.addRoleFood(role, -tempAllHp);
		ResultData resultData = new ResultData();
		int nowTime = Utils.getNowTime();
		if (roleId2 != roleId) {
			message.putShort(ErrorCode.NO_TARGET);
			return message;
		} else {

			int myLordRoleId = roleB.getRoleCity().getMyLordRoleId();
			if (myLordRoleId != 0) {// 防守方有主公则攻打其主公
				Role roleC = roleService.getRoleById(myLordRoleId);// 防守方主公
				/** 获取战斗结果 */
				resultData = PvP(roleA, roleC, -1);
				if (resultData.winCamp == 1) {// 攻打该玩家主公成功--star
					/** 如果玩家b是玩家a的主公 则玩家a设置为无主公Star */
					if (roleA.getRoleCity().getMyLordRoleId() == roleB.getId()) {
						roleA.getRoleCity().setMyLordRoleId(0);
						List<VassalData> list = roleB.getRoleCity().getVassalRoleId();
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getRoleId() == roleA.getId()) {
								list.remove(i);
							}
						}
					}
					/** end 如果玩家b是玩家a的主公 则玩家a设置为无主公 */
					VassalData vassalData = new VassalData(roleB.getId(), nowTime, 0, 0, 0);
					roleA.getRoleCity().getVassalRoleId().add(vassalData);
					roleB.getRoleCity().setMyLordRoleId(roleA.getId());
					List<VassalData> list = roleC.getRoleCity().getVassalRoleId();
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getRoleId() == roleB.getId()) {
							list.remove(i);
						}
					}
					Mail mail = new Mail();
					mail.setTitle("主公变更");
					StringBuffer sb = new StringBuffer();
					sb.append("您的主公的部队被" + roleA.getName() + "攻破了，你已经成为" + roleA.getName() + "的属城。");
					mail.setContext(sb.toString());
					mailService.sendSYSMail2(roleB, mail);
					Mail mail2 = new Mail();
					mail2.setTitle("属城更替");
					StringBuffer sb2 = new StringBuffer();
					sb2.append("您的部队被" + roleA.getName() + "攻破了，你的属城" + roleB.getName() + "已经归顺" + roleA.getName());
					mail2.setContext(sb2.toString());
					mailService.sendSYSMail2(roleC, mail2);
					message.putShort(ErrorCode.SUCCESS);
					// end---攻打该玩家主公成功
				} else {// 攻打该玩家主公失败---star
				// Mail mail2 = new Mail();
				// mail2.setTitle("zlpsb欠我一个邮件标题");
				// StringBuffer sb2 = new StringBuffer();
				// sb2.append("zlpsb欠我一个邮件内容");
				// mail2.setContext(sb2.toString());
				// mailService.sendSYSMail2(roleC, mail2);
					message.putShort(ErrorCode.SUCCESS);
				}// end---攻打该玩家主公失败

			} else {// 没有主公则攻打该玩家
				/** 获取战斗结果 */
				resultData = PvP(roleA, roleB, -1);

				/*
				 * 根据结果判定之后的处理流程
				 */

				fightService.returnStar(resultData, 1);
				if (resultData.winCamp == 1) {// 攻打玩家成功-----star
					/** 如果玩家b是玩家a的主公 则玩家a设置为无主公Star */
					if (roleA.getRoleCity().getMyLordRoleId() == roleB.getId()) {
						roleA.getRoleCity().setMyLordRoleId(0);
						List<VassalData> list = roleB.getRoleCity().getVassalRoleId();
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getRoleId() == roleA.getId()) {
								list.remove(i);
							}
						}
					}
					/** end 如果玩家b是玩家a的主公 则玩家a设置为无主公 */
					// 玩家a增加一个臣子
					VassalData vassalData = new VassalData(roleB.getId(), nowTime, 0, 0, 0);
					roleA.getRoleCity().getVassalRoleId().add(vassalData);
					// 玩家b的主公设为玩家a
					roleB.getRoleCity().setMyLordRoleId(roleA.getId());
					Mail mail = new Mail();
					mail.setTitle("成为属城");
					StringBuffer sb = new StringBuffer();
					sb.append("您的主城被" + roleA.getName() + "攻破了，您已经成为" + roleA.getName() + "的属城");
					mail.setContext(sb.toString());
					mailService.sendSYSMail2(roleB, mail);
					message.putShort(ErrorCode.SUCCESS);
					// end---攻打玩家成功
				} else {// 攻打玩家失败--star
				// Mail mail = new Mail();
				// mail.setTitle("zlpsb欠我一个邮件标题");
				// StringBuffer sb = new StringBuffer();
				// sb.append("zlpsb欠我一个邮件内容");
				// mail.setContext(sb.toString());
				// mailService.sendSYSMail2(roleB, mail);
					message.putShort(ErrorCode.SUCCESS);
				}// end---攻打玩家失败
			}
		}
		if (resultData.winCamp == 1) {
			this.taskService.checkPvp(role, (byte) 1);
		}
		message.putString(resultData.uuid);
		message.putString(resultData.time);
		message.putInt(resultData.winCamp);
		message.putInt(resultData.attPlayerId);
		message.putInt(resultData.defPlayerId);
		message.putString(resultData.attName);
		message.putString(resultData.defName);
		message.putString(resultData.report);
		message.putInt(resultData.attStars);
		message.putInt(resultData.attLost);
		message.putInt(resultData.defStars);
		message.putInt(resultData.defLost);
		message.putInt(resultData.fRound);
		return message;
	}

	/**
	 * 提升官阶
	 * 
	 * @author xjd
	 */
	public Message upGradeRank(Role role, IoSession is) {
		Message message = new Message();
		message.setType(CityConstant.UP_GRADE_RANK);
		RankConfig config = RankConfigCache.getRankConfigById(role.getRank() + CityConstant.UP_RANK);
		if (config == null) {
			message.putShort(ErrorCode.ERR_UP_RANK);
			return message;
		}
		// 判断声望是否足够
		if (role.getPrestige() < config.getNeedP()) {
			message.putShort(ErrorCode.NO_PRESTIGE);
			return message;
		}
		// 提升官阶
		role.setRank(role.getRank() + CityConstant.UP_RANK);
		role.setChange(true);
		if (config.getHeroId() != CityConstant.NULL_HERO) {
			this.getHero(role, config.getHeroId(), is);
		}
		role.setGetRankPay(CityConstant.NO);
		roleService.addRolePrestige(role, -config.getNeedP());
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(role.getRank());

		String gradeName = config.getName();

		if (chatService.checkSystemChat(ChatConstant.MESSAGE_TYPE_GRADERANK, role.getRank())) {
			StringBuilder sb = this.getGradeRankStringBuilder(role, gradeName);
			chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_GRADERANK, sb.toString());
		}

		return message;
	}

	/**
	 * 反抗
	 * 
	 * @author xjd
	 */
	public Message pvpFight(Role role, int targetId) {
		Message message = new Message();
		message.setType(CityConstant.PVP_FIGHT);
		Role targetRole = roleService.getRoleById(targetId);
		if (targetRole == null) {
			message.putShort(ErrorCode.NULL_TARGET_ROLE);
			return message;
		}
		if (role.getId() == targetRole.getId() || role.getRoleCity().getMyLordRoleId() != targetId) {
			return null;
		}

		// 判断是否超过反抗次数
		if (this.checkCanNotResistance(role)) {
			message.putShort(ErrorCode.RESISTANCE_OUT_OF_RANGE);
			return message;
		}

		// 判断粮草消耗
		int needFood = this.checkFood(role);
		if (role.getFood() < needFood) {
			message.putShort(ErrorCode.NO_FOOD);
			return message;
		}
		// // 判断是否可以攻打
		// if (role.getArmyToken() <= CityConstant.NULL_HERO) {
		// message.putShort(ErrorCode.NO_ARMY_TOKEN);
		// return message;
		// }
		// long temp = System.currentTimeMillis() / 1000;
		// if (targetRole.getProTime() != 0 && temp - targetRole.getProTime() <=
		// CityConstant.PRO_TIME) {
		// message.putShort(ErrorCode.AT_PROTECT);
		// return message;
		// }
		// CityInfoConfig infoConfig =
		// CityInfoConfigCache.getCityInfoConfigById(role.getRoleCity().getCityId());
		// CityInfoConfig infoConfig2 =
		// CityInfoConfigCache.getCityInfoConfigById(targetRole.getRoleCity().getCityId());
		// if (infoConfig.getLvMaxNeed() != infoConfig2.getLvMaxNeed()) {
		// message.putShort(ErrorCode.ERR_PVP_TARGET);
		// return message;
		// }
		// 可以攻打，进入pvp流程
		// roleService.getArmyToken(role, -1);
		// PVEUtil
		TroopData roleData = PVPUitls.getTroopDataByRole(role);
		TroopData roleData2 = PVPUitls.getTroopDataByRole(targetRole);
		ResultData resultData = new Battle().fightPVP(roleData, roleData2);
		/*
		 * 根据结果判定之后的处理流程
		 */
		role.setResistanceNum(role.getResistanceNum() + 1);
		roleService.addRoleFood(role, -needFood);
		fightService.returnStar(resultData, 1);
		roleService.addRolePopulation(role, -resultData.attLost);
		roleService.addRolePopulation(targetRole, -resultData.defLost);
		if (resultData.winCamp == 1) {
			this.taskService.checkPvp(role, (byte) 2);
		}
		Mail mail = new Mail();
		mail.setTitle("属城反抗");
		StringBuilder sb = new StringBuilder();
		if (resultData.winCamp == 1) {
			// 反抗成功取消玩家的主公
			role.getRoleCity().setMyLordRoleId(CityConstant.NO_ITEM_ID);
			// 主公玩家取消属城
			List<VassalData> list = targetRole.getRoleCity().getVassalRoleId();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getRoleId() == role.getId()) {
					list.remove(i);
				}
			}
			/* end************************************** */

			// 邮件
			sb.append("由于您的部队被您的属城：").append(role.getName()).append("战胜了").append(role.getName() + "获得了自由");

		} else {
			// 邮件
			sb.append("您的属城：").append(role.getName()).append("反抗失败，").append("您狠狠的羞辱了他,威望没有损失");
		}
		mail.setContext(sb.toString());
		mailService.sendSYSMail2(targetRole, mail);
		// 返回
		message.putShort(ErrorCode.SUCCESS);
		message.putString(resultData.uuid);
		message.putString(resultData.time);
		message.putInt(resultData.winCamp);
		message.putInt(resultData.attPlayerId);
		message.putInt(resultData.defPlayerId);
		message.putString(resultData.attName);
		message.putString(resultData.defName);
		message.putString(resultData.report);
		message.putInt(resultData.attStars);
		message.putInt(resultData.attLost);
		message.putInt(resultData.defStars);
		message.putInt(resultData.defLost);
		message.putInt(resultData.fRound);
		// 武将获得经验
		int heroExp = FightConstant.NUM_0;
		String expStr = heroService.addHeroExp(role, heroExp);
		message.putInt(heroExp);
		message.putString(expStr);
		return message;
	}

	private boolean checkCanNotResistance(Role role) {
		return role.getResistanceNum() >= 1;
	}

	/**
	 * 获取官职信息
	 * 
	 * @author xjd
	 */
	public Message getRankInfo(Role role) {
		Message message = new Message();
		message.setType(CityConstant.GET_RANK_INFO);
		RankConfig config = RankConfigCache.getRankConfigById(role.getRank());
		if (config == null) {
			return null;
		}
		message.putString(config.getName());
		message.putInt(role.getPrestige());
		message.putInt(config.getPay());
		message.put(role.getGetRankPay());
		RankConfig config2 = RankConfigCache.getRankConfigById(role.getRank() + CityConstant.UP_RANK);
		if (config2 == null) {
			message.putString("");
			message.putInt(CityConstant.NULL_HERO);
			message.putInt(CityConstant.NULL_HERO);
			message.putInt(CityConstant.NULL_HERO);
			message.putInt(CityConstant.NULL_HERO);
			message.putInt(CityConstant.NULL_HERO);
			message.putInt(CityConstant.NULL_HERO);
			message.putInt(CityConstant.NULL_HERO);
		} else {
			message.putString(config2.getName());
			message.putInt(config2.getNeedP());
			if (config2.getHeroId() != CityConstant.NULL_HERO) {
				message.putInt(config2.getHeroId());
			} else {
				message.putInt(CityConstant.NULL_HERO);
			}

			if (config2.getMaxCity() > config.getMaxCity()) {
				message.putInt(config2.getMaxCity() - config.getMaxCity());
			} else {
				message.putInt(CityConstant.NULL_HERO);
			}
			if (config2.getMaxMine() > config.getMaxMine()) {
				message.putInt(config2.getMaxMine() - config.getMaxMine());
			} else {
				message.putInt(CityConstant.NULL_HERO);
			}
			if (config2.getMaxFarm() > config.getMaxFarm()) {
				message.putInt(config2.getMaxFarm() - config.getMaxFarm());
			} else {
				message.putInt(CityConstant.NULL_HERO);
			}
			if (config2.getAddHurt() > config.getAddHurt()) {
				message.putInt(config2.getAddHurt() - config.getAddHurt());
			} else {
				message.putInt(CityConstant.NULL_HERO);
			}
			if (config2.getRmHurt() > config.getRmHurt()) {
				message.putInt(config2.getRmHurt() - config.getRmHurt());
			} else {
				message.putInt(CityConstant.NULL_HERO);
			}
		}
		return message;
	}

	/**
	 * 领取俸禄
	 * 
	 * @author xjd
	 */
	public Message getRankAward(Role role) {
		Message message = new Message();
		message.setType(CityConstant.GET_RANK_AWARD);
		// 是否可领取
		if (role.getGetRankPay() != (byte) CityConstant.NULL_HERO) {
			message.putShort(ErrorCode.ERR_GET_RANK_AWARD);
			return message;
		}

		RankConfig config = RankConfigCache.getRankConfigById(role.getRank());
		roleService.addRoleMoney(role, config.getPay());
		role.setGetRankPay(CityConstant.SUCCESS);
		role.setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	@Override
	public Message getVassalInfo(Role role) {
		Message message = new Message();
		message.setType(CityConstant.GET_VASSAL_INFO);
		RankConfig rank = RankConfigCache.getRankConfigById(role.getRank());
		List<VassalData> list = role.getRoleCity().getVassalRoleId();

		message.putInt(rank.getMaxCity());
		message.putInt(list.size());
		for (VassalData vassalData : list) {
			Role vassalRole = roleService.getRoleById(vassalData.getRoleId());
			RankConfig vassalrank = RankConfigCache.getRankConfigById(vassalRole.getRank());
			Legion legion = LegionCache.getLegionById(vassalRole.getLegionId());
			CityInfoConfig cityInfoConfig = CityInfoConfigCache.getCityInfoConfigById(vassalRole.getRoleCity()
					.getCityId());
			int nowTime = (int) (System.currentTimeMillis() / 1000);
			int differenceTime = (nowTime - vassalData.getLastTime());
			int dayNum = differenceTime / (60 * 60 * 24);
			if (vassalData.getLastTime() == 0) {
				dayNum = -1;
			}
			message.putString(vassalRole.getName());
			message.putInt(vassalRole.getId());
			message.putInt(vassalRole.getLv());
			message.putString(vassalrank.getName());
			message.putString(legion == null ? "暂无" : legion.getName());
			message.putString(cityInfoConfig.getCityName());
			message.putInt(vassalRole.getRoleCity().getMapKey());
			message.putInt(dayNum);
			message.putInt(vassalData.getMoney());
			message.putInt(vassalData.getFood());
			message.putInt(vassalData.getPopulation());
		}
		return message;
	}

	@Override
	public Message dropVassal(Role role, int roleId) {
		Message message = new Message();
		message.setType(CityConstant.DROP_VASSAL);
		if (role.getRoleCity().getVassalByRoleId(roleId) == null) {
			message.putShort(ErrorCode.NO_TARGET);
			return message;
		}
		VassalData vassalData = role.getRoleCity().getVassalByRoleId(roleId);
		// 判断时间是否可以放逐
		if (this.checkCanNotDropVassal(vassalData)) {
			message.putShort(ErrorCode.ERR_TIME_DROP);
			return message;
		}
		role.getRoleCity().getVassalRoleId().remove(vassalData);
		Role myLordRole = roleService.getRoleById(roleId);
		myLordRole.getRoleCity().setMyLordRoleId(0);// 被放弃的玩家主公设为空
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 检查是否不能放逐属臣，不能放逐返回true
	 * 
	 * @param vassalData
	 * @return
	 * @author wcy 2016年1月29日
	 */
	private boolean checkCanNotDropVassal(VassalData vassalData) {
		return vassalData.isCanNotDrop();
		// return Utils.getNowTime() - vassalData.getLastTime()
		// < 24*60*60;
	}

	@Override
	public Message marchWorldArmyFromHome(Role role, int worldFormationId, int heroCaptainId, String name) {
		Message message = new Message();
		message.setType(CityConstant.MARCH_WORLD_ARMY_FROM_HOME);

		FormationData formationData = role.getWorldFormation().get(worldFormationId);
		FormationData cloneFormationData = formationData.clone();
		Map<Byte, Integer> data = cloneFormationData.getData();

		int hasHero = 0;
		for (Entry<Byte, Integer> entry : data.entrySet()) {
			byte pos = entry.getKey();
			int heroId = entry.getValue();

			Hero hero = role.getHeroMap().get(heroId);
			if (hero == null) {
				data.put(pos, 1);
				continue;
			}

			if (hero.getLocation() != CityConstant.CAN_MARCH) {
				data.put(pos, 1);
				continue;
			}
			if (entry.getValue() > 2) {
				hasHero++;
			}
		}
		if (hasHero == 0) {
			message.putShort(ErrorCode.NO_FORMATION_DATA);
			return message;
		}

		if (!role.getHeroMap().containsKey(heroCaptainId)) {
			message.putShort(ErrorCode.NO_HERO);
			return message;
		}

		if (name == null || name.equals("")) {
			// TODO
			return message;
		}

		if (!data.values().contains(heroCaptainId)) {
			message.putShort(ErrorCode.ERR_HERO_CAPTIN);
			return message;
		}

		if (name == null || name.equals("")) {
			message.putShort(ErrorCode.ERR_NAME_ARMY);
			return message;
		}

		int nowTime = Utils.getNowTime();
		int homeCityId = CityInfoConfigCache.getBirthCityByNation(role.getNation()).getCityId();
		City homeCity = CityCache.getCityByCityId(homeCityId);

		String id = nowTime + "@" + role.getId();
		WorldArmy worldArmy = new WorldArmy();
		worldArmy.setId(id);
		worldArmy.setClientId(worldFormationId);
		worldArmy.setName(name);
		worldArmy.setRoleId(role.getId());
		worldArmy.setHeroCaptainId(heroCaptainId);
		worldArmy.setFormationData(cloneFormationData);
		worldArmy.setLocation(homeCity.getCityId(), role);
		worldArmy.setStatus(CityConstant.ARMY_LAZY);

		homeCity.addLazyInfo(worldArmy);

		role.getWorldArmySet().add(worldArmy);

		role.setChange(true);
		homeCity.setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		message.putString(id);

		return message;
	}

	@Override
	public Message showWorldCityInfo(Role role, int cityId) {
		Message message = new Message();
		message.setType(CityConstant.SHOW_WORLD_CITY_INFO);

		City city = CityCache.getAllCityMap().get(cityId);
		if (city == null) {
//			System.err.println("city is null");
//			System.err.println(cityId);
			return null;
		}
		HashMap<String, List<Integer>> myWorldArmys = new HashMap<>();
		StringBuilder mineFarmSB = new StringBuilder();
		CityInfoConfig cityInfoConfig = CityInfoConfigCache.getCityInfoConfigById(cityId);
		// 如果是小山村
		if (this.checkVillage(cityId)) {
			List<WorldArmy> list = role.getVillageDefList();
			for (WorldArmy worldArmy : list) {
				Map<Byte, Integer> formationData = worldArmy.getFormationData().getData();
				ArrayList<Integer> heros = new ArrayList<>();
				myWorldArmys.put(worldArmy.getId(), heros);
				for (Integer heroId : formationData.values()) {
					if (heroId > 2) {
						heros.add(heroId);
					}
				}
			}

			byte type = PubConstant.TYPE_ITEM;
			int itemId = cityInfoConfig.getGarrisonHero() + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX;
			int num = -1;
			mineFarmSB.append(type).append(",");
			mineFarmSB.append(itemId).append(",");
			mineFarmSB.append(num);
			mineFarmSB.append(";");

		} else {
			Map<Integer, List<WorldArmy>> defInfoMap = city.getDefInfoMap();
			for (List<WorldArmy> list : defInfoMap.values()) {
				for (WorldArmy worldArmy : list) {
					int roleId = worldArmy.getRoleId();
					if (roleId == role.getId()) {
						Map<Byte, Integer> formationData = worldArmy.getFormationData().getData();
						ArrayList<Integer> heros = new ArrayList<>();
						myWorldArmys.put(worldArmy.getId(), heros);
						for (Integer heroId : formationData.values()) {
							if (heroId > 2) {
								heros.add(heroId);
							}
						}
					}
				}

			}

			Map<String, WorldArmy> lazyInfoMap = city.getLazyInfoMap();
			for (WorldArmy worldArmy : lazyInfoMap.values()) {
				int roleId = worldArmy.getRoleId();
				if (role.getId() == roleId) {
					Map<Byte, Integer> formationData = worldArmy.getFormationData().getData();
					ArrayList<Integer> heros = new ArrayList<>();
					myWorldArmys.put(worldArmy.getId(), heros);
					for (Integer heroId : formationData.values()) {
						if (heroId > 2) {
							heros.add(heroId);
						}
					}
				}
			}
			List<WorldArmy> list = city.getCoreDefInfoMap();
			for (WorldArmy worldArmy : list) {
				Map<Byte, Integer> formationData = worldArmy.getFormationData().getData();
				ArrayList<Integer> heros = new ArrayList<>();
				myWorldArmys.put(worldArmy.getId(), heros);
				for (Integer heroId : formationData.values()) {
					if (heroId > 2) {
						heros.add(heroId);
					}
				}

			}

			// /城内矿产资源
			List<MineFarmConfig> mineFarmList = MineFarmConfigCache.getMineFarmList(cityId);
			if (mineFarmList != null) {
				for (MineFarmConfig config : mineFarmList) {
					// 银币
					if (config.getCoinIncome() > 0) {
						mineFarmSB.append(ChapterConstant.AWARD_MONEY).append(",");
						mineFarmSB.append(-1).append(";");
					}
					// 粮食
					if (config.getFoodIncome() > 0) {
						mineFarmSB.append(ChapterConstant.AWARD_FOOD).append(",");
						mineFarmSB.append(-1).append(";");
					}
					// 大米
					if (config.getRiceIncome() > 0) {
						mineFarmSB.append(ChapterConstant.AWARD_ITEM).append(",");
						mineFarmSB.append(PubConstant.PROP_RICE_ID).append(",");
						mineFarmSB.append(-1).append(";");
					}
					// 铁矿
					if (config.getMineralIncome() > 0) {
						mineFarmSB.append(ChapterConstant.AWARD_ITEM).append(",");
						mineFarmSB.append(PropConstant.PROP_IRON).append(",");
						mineFarmSB.append(-1).append(";");
					}
					// 金币
					if (config.getGoldIncome() > 0) {
						mineFarmSB.append(ChapterConstant.AWARD_GOLD).append(",");
						mineFarmSB.append(-1).append(";");
					}
					// 将魂 不加入城主抽成
					if (config.getHeroDebris() > 0) {
						mineFarmSB.append(ChapterConstant.AWARD_ITEM).append(",");
						mineFarmSB.append(config.getHeroDebris()).append(",");
						mineFarmSB.append(-1).append(";");
					}
				}

			}

		}

		message.putInt(myWorldArmys.size());
		for (Entry<String, List<Integer>> entry : myWorldArmys.entrySet()) {
			String id = entry.getKey();
			List<Integer> heroList = entry.getValue();
			message.putString(id);
			message.putInt(heroList.size());
			for (Integer heroId : heroList) {
				message.putInt(heroId);
			}
		}
		int remainProtectTime = this.getRemainProtectedTime(Utils.getNowTime(), city);
		message.putInt(remainProtectTime);
		message.putString(mineFarmSB.toString());

		return message;
	}

	@Override
	public Message showWorldCityWallInfo(Role role, int cityId, int wallId) {
		Message message = new Message();
		message.setType(CityConstant.SHOW_WORLD_CITY_WALL_INFO);

		City city = CityCache.getAllCityMap().get(cityId);
		List<WorldArmy> list = null;
		if (wallId != -1) {
			Map<Integer, List<WorldArmy>> walls = city.getDefInfoMap();
			list = walls.get(wallId);
		} else {
			list = city.getCoreDefInfoMap();
		}

		int size = list != null ? list.size() : 0;

		message.putInt(size);
		if (size == 0)
			return message;
		for (WorldArmy worldArmy : list) {
			int captainHeroId = worldArmy.getHeroCaptainId();
			int roleId = worldArmy.getRoleId();
			Map<Byte, Integer> data = worldArmy.getFormationData().getData();

			Role r = roleService.getRoleById(roleId);
			Map<Integer, Hero> map = r.getHeroMap();
			int totalHurtValue = 0;
			for (Integer heroId : data.values()) {
				if (heroId > 2) {
					Hero hero = map.get(heroId);
					totalHurtValue += hero.getFightValue();
				}
			}
			message.putInt(totalHurtValue);
			message.putInt(captainHeroId);
			message.putString(r.getName());
		}
		return message;
	}

	@Override
	public Message speedUpWorldMarch(Role role, String marchId) {
		Message message = new Message();
		message.setType(CityConstant.SPEED_UP_WORLD_MARCH);
		int nowTime = Utils.getNowTime();
		WorldMarch worldMarch = this.getWorldMarch(marchId);
		if (worldMarch == null) {
//			System.out.println("worldMarch is null");
			message.putShort(ErrorCode.WORLD_MARCH_CANT_SPEED_UP);
			return message;
		}

		WorldArmy worldArmy = worldMarch.getWorldArmy();
		LinkedList<City> path = worldMarch.getPath();
		City city1 = path.get(path.size() - 2);

		// 检查经过的城市是否被攻占
		City targetCity = worldMarch.getTargetCity();
		City finalCity = worldMarch.getFinalCity();
		ListIterator<City> it = path.listIterator();
		// 获得当前位置
		while (it.hasNext()) {
			City city = it.next();
			if (city == targetCity) {
				break;
			}
		}
		// 检查剩余经过城市
		while (it.hasNext()) {
			City city = it.next();
			if (city != finalCity) {
				if (city.getNation() != role.getNation()) {
					message.putShort(ErrorCode.WORLD_MARCH_HAS_ENEMY_CITY);
					return message;
				}
			}
		}

		// 如果处于保护状态不可加速
		if (finalCity.getLastChange() != 0 && this.checkProtected(nowTime, finalCity)
				&& worldMarch.getNation() != finalCity.getNation()) {
			message.putShort(ErrorCode.WORLD_CITY_UNDER_PROTECTED);
			return message;
		}

		// 检查金币
		int needGold = this.getSpeedUpWorldMarchNeedGold(role, worldMarch);

		if (role.getGold() < needGold) {
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}

		roleService.addRoleGold(role, -needGold);

		WorldWarPath worldWarPath = WorldWarPathCache.getWorldWarPathByCities(city1.getCityId(), finalCity.getCityId());
		int pathId = Utils.getCityPathNum(city1, finalCity);
		int wallId = worldWarPath.getLinkWallID();
		if (this.checkVillage(finalCity.getCityId())) {// 如果是小山村
			if (this.checkFirstRunVillage(role, finalCity.getCityId())) {
				// 如果是首次
				this.handlerGuideArrive(worldMarch);
			} else {
				// 不是首次
				role.addVillageDefWorldArmy(worldArmy);
			}

		} else {
			if (finalCity.getNation() == role.getNation()) {
				finalCity.addDefInfoWorldArmyToMinWorldArmyList(worldArmy);
			} else {
				finalCity.addAttInfoWorldArmy(wallId, worldArmy);
				// 设置城墙冒火的开始时间
				this.setCityWallFireStartTime(finalCity, pathId, Utils.getNowTime(), role.getNation());

				String cityName = CityInfoConfigCache.getCityInfoConfigById(finalCity.getCityId()).getCityName();
				StringBuilder sb = this.getCityUnderAttackStringBuilder(finalCity, cityName, worldMarch.getNation());
				chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_UNDER_ATTACK, sb.toString());
			}
		}

		worldMarch.getWorldArmy().setLocation(finalCity.getCityId(), role);
		int pathNum = Utils.getCityPathNum(city1.getCityId(), finalCity.getCityId());
		worldMarch.setCurrentPath(pathNum);
		messageToClient(CityConstant.OVER_MOVE, worldMarch.getId(), worldMarch.getCurrentCity().getCityId(),
				finalCity.getCityId(), nowTime, worldMarch);
		// 删除行军信息
		WorldMarchCache.getAllMarch().remove(worldMarch.getId());
		WorldMarchCache.getGuideAllMarch().remove(worldMarch.getId());
		worldMarch.moveTargetCityToFinalCity();
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(needGold);
		// 记录
		serverService.saveHistoryRole(ServerConstant.MARCH_SUESS, worldArmy, "");
		// 公会记录
		Legion legion = LegionCache.getLegionById(role.getLegionId());
		if (legion != null) {
			serverService.saveHistoryLegion(ServerConstant.MARCH_SUESS_LEGION, worldArmy);
		}
		return message;
	}

	/**
	 * 是否在保护时间，在保护时间内返回true
	 * 
	 * @param nowTime
	 * @param finalCity
	 * @return
	 * @author wcy 2016年1月8日
	 */
	private boolean checkProtected(int nowTime, City finalCity) {
		int remainProtectedTime = this.getRemainProtectedTime(nowTime, finalCity);

		if (remainProtectedTime > 0)
			return true;
		return false;
	}

	/**
	 * 获得剩余保护时间
	 * 
	 * @param nowTime
	 * @param city
	 * @return
	 * @author wcy 2016年1月13日
	 */
	private int getRemainProtectedTime(int nowTime, City city) {
		int lastChange = city.getLastChange();
		if (lastChange == 0) {
			return 0;
		}
		int totalTime = GeneralNumConstantCache.getValue("CITY_PROTECT_TIME");
		int remainTime = lastChange + totalTime - nowTime;
		if (remainTime > 0)
			return remainTime;
		return 0;
	}

	@Override
	public Message showSpeedUpWorldMarch(Role role, String marchId) {
		Message message = new Message();
		message.setType(CityConstant.SHOW_SPEED_UP_WORLD_MARCH);
		WorldMarch worldMarch = this.getWorldMarch(marchId);
		int needGold = 0;
		if (worldMarch != null) {
			needGold = this.getSpeedUpWorldMarchNeedGold(role, worldMarch);
		}

		message.putInt(needGold);

		return message;
	}

	@Override
	public Message showStayWorldArmyCity(Role role) {
		Message message = new Message();
		message.setType(CityConstant.SHOW_STAY_WORLD_ARMY_CIY);
		HashSet<WorldArmy> set = role.getWorldArmySet();
		StringBuilder sb = new StringBuilder();
		for (WorldArmy worldArmy : set) {
			int location = worldArmy.getLocation();
			if (location != 0 && location != -1) {
				sb.append(location).append(";");
			}
		}
		message.putString(sb.toString());

		return message;
	}

	/**
	 * 增加贡献值
	 * 
	 * @param city
	 * @param role
	 * @param contribute
	 * @author wcy
	 */
	private void addContribute(City city, Role role, double contribute, int nowTime) {
		if (role == null) {
			return;
		}
		byte nation = role.getNation();
		int roleId = role.getId();
		Map<Byte, Map<Integer, ContributeRank>> contributeMap = city.getContributeMap();
		Map<Integer, ContributeRank> map = contributeMap.get(nation);
		if (map == null) {
			map = new HashMap<>();
			contributeMap.put(nation, map);
		}
		ContributeRank v = map.get(roleId);
		if (v == null) {
			ContributeRank rank = createContributeRank(roleId, nowTime);
			map.put(roleId, rank);
		} else {
			int minContribute = GeneralNumConstantCache.getValue("MIN_CONTRIBUTE");
			int oldcontribute = v.getContribute();
			oldcontribute = (int) Math.floor(oldcontribute + contribute);
			oldcontribute = oldcontribute < minContribute ? minContribute : oldcontribute;
			v.setContribute(oldcontribute);
		}

	}

	@Override
	public Message getContributeRank(Role role, int cityId) {
		Message message = new Message();
		message.setType(CityConstant.GET_CONTRIBUTE_RANK);
		City city = CityCache.getAllCityMap().get(cityId);
		byte nation = role.getNation();
		// 如果是小山村不显示贡献榜
		if (CityCache.getVillageNationMap().get(cityId) != null) {
			return null;
		}
		Map<Integer, ContributeRank> map = city.getContributeMap().get(nation);

		// 排序
		ArrayList<ContributeRank> array = new ArrayList<>(map.values());
		Collections.sort(array, ComparatorContributeRank.getInstance());

		int num = GeneralNumConstantCache.getValue("CONTRIBUTE_NUM");
		num = array.size() < num ? array.size() : num;
		ContributeRank myRank = null;
		message.putInt(num);
		for (int i = 0; i < num; i++) {
			ContributeRank rank = array.get(i);
			Role player = roleService.getRoleById(rank.getRoleId());

			rank.setRankNum(i + 1);
			message.putInt(rank.getRankNum());
			message.putInt(rank.getContribute());
			message.putString(player.getName());

			if (rank.getRoleId() == role.getId()) {
				myRank = rank;
			}
		}

		byte has = (byte) (myRank == null ? 0 : 1);
		int rankNum = 0;
		int contribute = 0;
		String name = role.getName();

		if (myRank != null) {
			rankNum = myRank.getRankNum();
			contribute = myRank.getContribute();
		}
		message.put(has);
		message.putInt(rankNum);
		message.putInt(contribute);
		message.putString(name);

		return message;
	}

	@Override
	public Message showWorldMarchInfo(Role role, String id, String pathLinkStr) {
		Message message = new Message();
		message.setType(CityConstant.SHOW_WORLD_MARCH_INFO);
		String[] path = pathLinkStr.split(",");
		int startCity = 0;
		int targetCity = 0;
		int needFood = 0;
		int totalTime = 0;
		byte illege = 0;

		WorldArmy worldArmy = role.getWorldArmyById(id);
		if (worldArmy == null) {
//			System.err.println(worldArmy);
		}
//		int totalArmyNum = this.getWorldArmyNum(role, worldArmy);
//		System.err.println();

		if (path.length > 1) {
			for (int i = 0; i < path.length - 1; i++) {
				startCity = Integer.parseInt(path[i]);
				targetCity = Integer.parseInt(path[i + 1]);

				WorldWarPath worldWarPath = WorldWarPathCache.getWorldWarPathByCities(startCity, targetCity);
				if (worldWarPath == null) {
					illege = 1;
					needFood = 0;
					totalTime = 0;
				} else {
					totalTime += worldWarPath.getMarchTime();
				}
			}
			
			needFood += this.getWorldWarPathNeedFoodByArmyNum(role,worldArmy,totalTime);
			
		}
		message.putInt(needFood);
		message.putInt(totalTime);
		message.put(illege);

		return message;
	}

	@Override
	public Message marchWorldArmyFromCity(Role role, String id, String cityLinkStr) {
		Message message = new Message();
		message.setType(CityConstant.MARCH_WORLD_ARMY_FROM_CITY);

		int nowTime = Utils.getNowTime();

		String[] cities = cityLinkStr.split(",");
		int finalCityId = Integer.parseInt(cities[cities.length - 1]);
		if(cities.length<2){
			return null;
		}

		// 检查小坑村新手引导
		// if (this.checkFirstRunVillage(role,finalCityId)) {
		// 计算行军时间
		// int startCityId = Integer.parseInt(cities[0]);
		// int villageId = Integer.parseInt(cities[1]);
		// WorldWarPath worldWarPath =
		// WorldWarPathCache.getWorldWarPathByCities(startCityId, villageId);
		// if (worldWarPath == null) {// 两个城之间不存在路
		// return null;
		// }
		// int marchTotalTime = worldWarPath.getMarchTime();
		//
		// //战斗
		// WorldArmy atkWorldArmy = role.getWorldArmyById(id);
		// TroopData atkTroopData = atkWorldArmy.getTroopData();
		// //NPC DEF WORLD ARMY
		// City village = CityCache.getCityByCityId(finalCityId);
		// List<WorldArmy> defList = village.getDefInfoMap().get(1);
		// for (;;) {
		// if (defList.size() == 0) {
		// break;
		// }
		//
		// WorldArmy defWorldArmy = defList.get(0);
		// TroopData defTroopData = defWorldArmy.getTroopData();
		//
		// Battle battle = new Battle();
		// ResultData resultData = battle.fightPVP(atkTroopData, defTroopData);
		//
		// // 设置部队位置
		// atkWorldArmy.setLocation(villageId, role);
		//
		// // 玩家战况
		// serverService.saveHistoryRole(ServerConstant.FIGHT_WIN, atkWorldArmy,
		// resultData.uuid);
		// }
		//
		// message.putShort(ErrorCode.SUCCESS);
		// message.putString(id);
		// message.putInt(nowTime);
		// message.putInt(marchTotalTime);

		// return message;
		// }

		if (!WorldMarchCache.isCanMarch()) {
			message.putShort(ErrorCode.CAN_NOT_MARCH);
			return message;
		}

		if (ServerCache.getServer().getSeason() == ServerConstant.SEASON_WINTER) {
			message.putShort(ErrorCode.WINTER_NOT_MARCH);
			return message;
		}

		// 不能进攻小山村
		if (this.checkVillage(finalCityId) && CityCache.getVillageNationMap().get(finalCityId) != role.getNation()) {
			message.putShort(ErrorCode.CAN_NOT_MARCH);
			return message;
		}
		// 安排行军

		// 获得总兵力数
		WorldArmy worldArmy = role.getWorldArmyById(id);
		if (!checkIsWorldArmyAlive(nowTime, worldArmy)) {
			message.putShort(ErrorCode.WORLD_ARMY_ALREADY_DEAD);
			return message;
		}
//		int totalArmyNum = this.getWorldArmyNum(role, worldArmy);
		// 拆分路线字符串
		if (finalCityId == 0) {
			message.putShort(ErrorCode.TARGET_CITY_IS_INTERN);
			return message;
		}

		ConcurrentHashMap<Integer, City> allCityMap = CityCache.getAllCityMap();
		LinkedList<City> path = new LinkedList<>();

		int marchTotalTime = 0;
		int marchNeedFood = 0;
		int city1Id = 0;
		boolean hasEnemyNation = false;
		for (String cityIdStr : cities) {
			int cityId = Integer.parseInt(cityIdStr);
			City city = allCityMap.get(cityId);
			if (city == null) {
//				System.out.println("城市为空");
				return null;
			}

			// 计算总时间
			if (city1Id != 0) {
				WorldWarPath worldWarPath = WorldWarPathCache.getWorldWarPathByCities(city1Id, cityId);
				if (worldWarPath == null) {// 两个城之间不存在路
					return null;
				}
				marchTotalTime += worldWarPath.getMarchTime();

				// 检查是否路径中除了最后的目标城之外当中有地方城，有则不允许生成该次行军
				if (hasEnemyNation) {
					message.putShort(ErrorCode.WORLD_MARCH_HAS_ENEMY_CITY);
					return message;
				}

				if (city.getNation() != role.getNation()) {
					hasEnemyNation = true;
					CityInfoConfig cityInfoConfig = CityInfoConfigCache.getBirthCityByNation(city.getNation());
					if (cityInfoConfig != null && city.getCityId() == cityInfoConfig.getCityId()) {
						message.putShort(ErrorCode.BIRTH_CITY_NOT_FIGHT);
						return message;
					}
				}
			}
			city1Id = cityId;

			path.add(city);
		}
		marchNeedFood = this.getWorldWarPathNeedFoodByArmyNum(role, worldArmy, marchTotalTime);

		if (role.getFood() < marchNeedFood) {
			// 粮草不足
			message.putShort(ErrorCode.NO_FOOD);
			return message;
		}

		roleService.addRoleFood(role, -marchNeedFood);

		worldArmy.setLocation(CityConstant.MARCHING, role);
		worldArmy.setStatus(CityConstant.ARMY_MOVE);

		WorldMarch worldMarch = new WorldMarch();
		worldMarch.setId(id);
		worldMarch.setPath(path);
		worldMarch.setWorldArmy(worldArmy);
		worldMarch.setMarchStartTime(nowTime);
		worldMarch.setMarchBeginTime(nowTime);
		worldMarch.setMarchTotalTime(marchTotalTime);
		worldMarch.setNation(role.getNation());

		if (this.checkFirstRunVillage(role, finalCityId)) {
			WorldMarchCache.addGuideMarch(worldMarch);
		} else {
			WorldMarchCache.addMarch(worldMarch);
		}
		// 加入调度器
		City currentCity = worldMarch.getCurrentCity();
		int currentCityId = currentCity.getCityId();
		int targetCityId = worldMarch.getTargetCity().getCityId();

		this.messageToClient(CityConstant.START_MOVE, id, currentCityId, targetCityId, Utils.getNowTime(), worldMarch);

		City finalCity = worldMarch.getFinalCity();
		String finalCityName = CityInfoConfigCache.getCityInfoConfigById(finalCity.getCityId()).getCityName();

		StringBuilder sb = this.getMarchStringBuilder(role, finalCity, finalCityName);
		chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_MARCH, sb.toString());

		this.leaveVillage(worldArmy, currentCity, role);
		this.leaveCity(worldArmy, currentCity);

		message.putShort(ErrorCode.SUCCESS);
		message.putString(id);
		message.putInt(nowTime);
		message.putInt(marchTotalTime);

		return message;
	}

	private void leaveVillage(WorldArmy worldArmy, City city, Role role) {
		// Map<Integer, List<WorldArmy>> defInfoMap = city.getDefInfoMap();

		// int wallId = 0;
		// for (Entry<Integer, List<WorldArmy>> entrySet :
		// defInfoMap.entrySet()) {
		// List<WorldArmy> list = entrySet.getValue();
		// if (list == null) {
		// continue;
		// }
		//
		// if (list.contains(worldArmy)) {
		// wallId = entrySet.getKey();
		// break;
		// }
		// }
		//
		// city.removeDefInfoWorldArmy(wallId, worldArmy);
		//
		// city.removeCoreInfo(worldArmy);
		if (!this.checkVillage(city.getCityId())) {
			return;
		}
		List<WorldArmy> list = role.getVillageDefList();
		list.remove(worldArmy);

		// String id = worldArmy.getId();
		// city.removeLazyInfo(id);

		// clearMineFarmInfo(worldArmy, city);

		// Role temp = roleService.getRoleById(worldArmy.getRoleId());

		// for (MineFarm farm :
		// CityCache.getAllMine().get(city.getCityId()).values()) {
		// if (farm.getWorldArmyId() != null &&
		// farm.getWorldArmyId().equals(worldArmy.getId())) {
		// farm.setRoleId(0);
		// farm.setWorldArmyId("");
		// }
		// }
		role.setVillageMineFarmWorldArmyId("");
	}

	/**
	 * 检查是否是小坑村，而且还是群雄城的时候(检新手引导)
	 * 
	 * @param cityId
	 * @return
	 * @author wcy 2016年2月29日
	 */
	private boolean checkFirstRunVillage(Role role, int cityId) {
		if (CityCache.getVillageNationMap().containsKey(cityId)) {
			byte nation = role.getVillageNation();
			if (nation == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Message showWorldMarchRoleInfo(Role role) {
		Message message = new Message();
		message.setType(CityConstant.SHOW_WORLD_MARCH_ROLE_INFO);

		HashSet<WorldArmy> hashSet = role.getWorldArmySet();
		int nowTime = Utils.getNowTime();
		message.putInt(hashSet.size());

		for (WorldArmy worldArmy : hashSet) {
			String id = worldArmy.getId();
			int location = worldArmy.getLocation();
			int finalCityId = 0;
			int totalRemainTime = 0;
			int totalTime = 0;
			int marchTime = 0;
			int pathId = 0;
			byte status = worldArmy.getStatus();
			int currentCityId = 0;
			int targetCityId = 0;
			String name = worldArmy.getName();
			int heroCaptainId = worldArmy.getHeroCaptainId();
			if (location == -1) {
//				System.out.println(id);
				WorldMarch worldMarch = this.getWorldMarch(id);

				finalCityId = worldMarch.getFinalCity().getCityId();
				totalRemainTime = this.getWorldMarchTotalRemainTime(worldMarch, nowTime);
				totalTime = worldMarch.getMarchTotalTime();
				marchTime = worldMarch.getMarchTime();
				pathId = worldMarch.getCurrentPath();
				currentCityId = worldMarch.getCurrentCity().getCityId();
				targetCityId = worldMarch.getTargetCity().getCityId();
			} else {
				finalCityId = location;
				currentCityId = location;
				targetCityId = location;
			}

			Map<Byte, Integer> data = worldArmy.getFormationData().getData();
			StringBuilder sb = new StringBuilder();

			int totalFightValue = 0;
			for (int i = 1; i <= data.size(); i++) {
				int heroId = data.get((byte) i);
				if (heroId > 2) {
					sb.append(heroId).append(",");
					Hero hero = role.getHeroMap().get(heroId);
					totalFightValue += hero.getFightValue();
				}
			}
			byte isAlive = (byte) (checkIsWorldArmyAlive(nowTime, worldArmy) ? 1 : 0);
			int wakeRemainTime = this.getWakeRemainTime(nowTime, worldArmy);
			int worldFormationId = worldArmy.getClientId();

			String finalCityName = CityInfoConfigCache.getCityInfoConfigById(finalCityId).getCityName();
			message.putInt(location);
			message.putString(finalCityName);
			message.putInt(totalRemainTime);
			message.putString(sb.toString());
			message.putInt(totalFightValue);
			message.putInt(totalTime);
			message.putInt(marchTime);
			message.putInt(pathId);
			message.putInt(currentCityId);
			message.putInt(targetCityId);
			message.put(status);
			message.putString(id);
			message.put(isAlive);
			message.putInt(wakeRemainTime);
			message.putString(name);
			message.putInt(heroCaptainId);
			message.putInt(worldFormationId);
		}
		return message;
	}

	private WorldMarch getWorldMarch(String id) {
		WorldMarch w = WorldMarchCache.getWorldMarch(id);
		if (w == null) {
			w = WorldMarchCache.getGuideWorldMarch(id);
		}
		return w;
	}

	@Override
	public Message showOneWorldMarch(String id) {
		Message message = new Message();
		message.setType(CityConstant.SHOW_ONE_WORLD_MARCH);
		byte isExist = 0;
		int finalCityId = 0;
		int remainTime = 0;
		int totalTime = 0;
		int heroCaptainId = 0;
		int roleId = 0;
		StringBuilder sb = new StringBuilder();
		StringBuilder heroFightValueSb = new StringBuilder();
		StringBuilder soldierAndHeroNameSb = new StringBuilder();
		WorldMarch worldMarch = this.getWorldMarch(id);

		if (worldMarch != null) {
			isExist = 1;
			int nowTime = Utils.getNowTime();
			WorldArmy worldArmy = worldMarch.getWorldArmy();
			finalCityId = worldMarch.getFinalCity().getCityId();
			remainTime = this.getWorldMarchTotalRemainTime(worldMarch, nowTime);
			totalTime = worldMarch.getMarchTotalTime();
			heroCaptainId = worldArmy.getHeroCaptainId();
			roleId = worldArmy.getRoleId();
			Role role = roleService.getRoleById(roleId);
			FormationData formationData = worldArmy.getFormationData();
			Map<Byte, Integer> data = formationData.getData();
			for (int i = 1; i <= data.size(); i++) {
				int heroId = data.get((byte) i);
				Hero hero = role.getHeroMap().get(heroId);
				if (hero != null) {
					sb.append(heroId).append(",");
					int fightValue = hero.getFightValue();
					heroFightValueSb.append(fightValue).append(",");

					// 右边的九宫格
					int armsId = hero.getUseArmyId();
					int armsType = ArmsConfigCache.getArmsConfigById(armsId).getFunctionType();
					soldierAndHeroNameSb.append(armsType).append(",").append(heroId);
				}
				soldierAndHeroNameSb.append(";");

			}
		}
//		System.out.println(finalCityId);
		String finalCityName = CityInfoConfigCache.getCityInfoConfigById(finalCityId).getCityName();
		message.put(isExist);
		message.putString(finalCityName);
		message.putInt(heroCaptainId);
		message.putInt(remainTime);
		message.putString(sb.toString());
		message.putInt(totalTime);
		message.putInt(roleId);
		message.putString(heroFightValueSb.toString());
		message.putString(soldierAndHeroNameSb.toString());

		return message;
	}

	@Override
	public Message retreatWorldMarch(Role role, String id) {
		Message message = new Message(CityConstant.RETREATE_WORLD_MARCH);

		WorldMarch worldMarch = this.getWorldMarch(id);
		if (worldMarch == null) {
			message.putShort(ErrorCode.WORLD_MARCH_NOT_EXIST);
			return message;
		}
		City currentCity = worldMarch.getCurrentCity();
		WorldArmy worldArmy = worldMarch.getWorldArmy();
		int locationCityId = retreat(role, worldArmy, currentCity);
		int nowTime = Utils.getNowTime();

		message.putShort(ErrorCode.SUCCESS);
		this.messageToClient(CityConstant.CANCEL_MOVE, id, 0, locationCityId, nowTime, worldMarch);

		return message;
	}

	@Override
	public Message showStrongHold(Role role) {
		Message message = new Message();
		message.setType(CityConstant.SHOW_STRONGHOLD);
		int strongHoldCity = role.getStrongHold();
		// City city = CityCache.getCityByCityId(strongHoldCity);
		// if (city.getNation() != role.getNation()) {
		// strongHoldCity =
		// CityInfoConfigCache.getBirthCityByNation(role.getNation()).getCityId();
		// role.setStrongHold(strongHoldCity);
		// }
		message.putInt(strongHoldCity);
		message.put((byte) (role.getIsStrongHold() > 0 ? 0 : 1));

		return message;
	}

	@Override
	public Message setStrongHold(Role role, int cityId) {
		Message message = new Message();
		message.setType(CityConstant.SET_STRONGHOLD);

		City city = CityCache.getCityByCityId(cityId);
		if (city.getNation() != role.getNation()) {
			message.putShort(ErrorCode.NOT_YOUR_NATION);
			return message;
		}

		// 小山村不能为据点
		if (this.checkVillage(cityId)) {
			message.putShort(ErrorCode.STRONGHOLD_SET_FAILED);
			return message;
		}

		// 检查设置据点的次数
		if (role.getIsStrongHold() > 0) {
			message.putShort(ErrorCode.ALREADY_STRONGHOLD);
			return message;
		}
		role.setStrongHold(cityId);
		role.setIsStrongHold((byte) (role.getStrongHold() + 1));

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(cityId);

		return message;
	}

	@Override
	public Message dismissArmy(Role role, String id) {
		Message message = new Message(CityConstant.DISIMISS_WORLD_ARMY);

		HashSet<WorldArmy> hashSet = role.getWorldArmySet();
		WorldArmy worldArmy = role.getWorldArmyById(id);
		if (worldArmy == null)
			return null;

		int nowTime = Utils.getNowTime();
		if (this.checkWorldArmyDead(nowTime, worldArmy)) {
			message.putShort(ErrorCode.WORLD_ARMY_ALREADY_DEAD);
			return message;
		}
		int cityId = worldArmy.getLocation();
		// 行军途中不可解散
		if (cityId == -1) {
			message.putShort(ErrorCode.WORLD_MARCH_IS_MARCH);
			return message;
		}

		City city = CityCache.getCityByCityId(cityId);
		this.leaveVillage(worldArmy, city, role);
		this.leaveCity(worldArmy, city);

		// 武将变回可组建部队状态
		worldArmy.setLocation(CityConstant.CAN_MARCH, role);
		hashSet.remove(worldArmy);

		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 检查部队是否死亡,处于死亡状态返回true
	 * 
	 * @param nowTime
	 * @param worldArmy
	 * @return
	 * @author wcy 2016年1月13日
	 */
	private boolean checkWorldArmyDead(int nowTime, WorldArmy worldArmy) {
		int wakeTime = GeneralNumConstantCache.getValue("WORLD_ARMY_WAKE_TIME");
		int lastDeadTime = worldArmy.getLastDeadTime();
		return lastDeadTime + wakeTime > nowTime;
	}

	/**
	 * 获取本城玩家所有自己的部队
	 * 
	 * @author xjd
	 */
	public Message getAllArmyCity(Role role, int cityId) {
		Message message = new Message();
		message.setType(CityConstant.GET_ALL_CITY_ARMY);
		int num = CityConstant.NO_ITEM_ID;
		for (WorldArmy x : role.getWorldArmySet()) {
			if (x.getLocation() == cityId) {
				num++;
			}
		}
		message.putInt(num);
		for (WorldArmy x : role.getWorldArmySet()) {
			if (x.getLocation() == cityId) {
				message.putString(x.getId());
				message.putInt(x.getHeroCaptainId());
			}
		}
		return message;
	}

	/***
	 * 行军信息变更
	 * 
	 * @param type
	 * @param id
	 * @param fromId
	 * @param targetId
	 */
	private void messageToClient(byte type, String id, int fromId, int targetId, int nowTime, WorldMarch worldMarch) {
		Message message = new Message();
		message.setType(CityConstant.WORLD_INFO_CHANGE);

		byte nation = worldMarch.getNation();
		int totalTime = worldMarch.getMarchTotalTime();
		int c2cTime = worldMarch.getMarchTime();
		int startTime = worldMarch.getMarchStartTime();
		int pathNum = worldMarch.getCurrentPath();
		if (pathNum == 0) {
			pathNum = Utils.getCityPathNum(fromId, targetId);
		}
		int finalCityId = worldMarch.getFinalCity().getCityId();
		int remainTime = c2cTime - (nowTime - startTime);
		remainTime = remainTime < 0 ? 0 : remainTime;
		int totalRemainTime = getWorldMarchTotalRemainTime(worldMarch, nowTime);

		WorldArmy worldArmy = worldMarch.getWorldArmy();
		int roleId = worldArmy.getRoleId();
		int location = worldArmy.getLocation();
		int captainHeroId = worldArmy.getHeroCaptainId();
		FormationData f = worldArmy.getFormationData();
		Map<Byte, Integer> data = f.getData();
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= data.size(); i++) {
			int heroId = data.get((byte) i);
			if (heroId > 2) {
				sb.append(heroId).append(",");
			}
		}

		Role role = roleService.getRoleById(roleId);
		String name = role != null ? role.getName() : "";

		message.put(type);
		message.putString(id);
		message.putInt(fromId);
		message.putInt(targetId);
		message.put(nation);
		message.putInt(remainTime);
		message.putInt(totalTime);
		message.putInt(pathNum);
		message.putInt(c2cTime);
		message.putInt(roleId);
		message.putString(sb.toString());
		message.putInt(location);
		message.putInt(totalRemainTime);
		message.putInt(finalCityId);
		message.putInt(captainHeroId);
		message.putString(name);
		message.put((byte) (this.checkIsWorldArmyAlive(nowTime, worldArmy) ? 1 : 0));
		message.putInt(this.getWakeRemainTime(nowTime, worldArmy));
		message.put(worldArmy.getStatus());
		message.putString(worldArmy.getName());

		for (IoSession x : SessionCache.getAllSession()) {
			x.write(message);
		}
	}

	private void messageToClient(byte type, String id, int fromId, int targetId, int nowTime, WorldArmy worldArmy,
			byte nation) {
		Message message = new Message();
		message.setType(CityConstant.WORLD_INFO_CHANGE);

		int totalTime = 0;
		int c2cTime = 0;
		int pathNum = 0;
		int remainTime = 0;
		int totalRemainTime = 0;
		int finalCityId = fromId;
		int roleId = worldArmy.getRoleId();
		int location = worldArmy.getLocation();
		int captainHeroId = worldArmy.getHeroCaptainId();
		FormationData f = worldArmy.getFormationData();
		Map<Byte, Integer> data = f.getData();
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= data.size(); i++) {
			int heroId = data.get((byte) i);
			if (heroId > 2) {
				sb.append(heroId).append(",");
			}
		}
		Role role = roleService.getRoleById(roleId);
		String name = role != null ? role.getName() : "";

		message.put(type);
		message.putString(id);
		message.putInt(fromId);
		message.putInt(targetId);
		message.put(nation);
		message.putInt(remainTime);
		message.putInt(totalTime);
		message.putInt(pathNum);
		message.putInt(c2cTime);
		message.putInt(roleId);
		message.putString(sb.toString());
		message.putInt(location);
		message.putInt(totalRemainTime);
		message.putInt(finalCityId);
		message.putInt(captainHeroId);
		message.putString(name);
		message.put((byte) (this.checkIsWorldArmyAlive(nowTime, worldArmy) ? 1 : 0));
		message.putInt(this.getWakeRemainTime(nowTime, worldArmy));
		message.put(worldArmy.getStatus());
		message.putString(worldArmy.getName());

		for (IoSession x : SessionCache.getAllSession()) {
			x.write(message);
		}
	}

	/***
	 * 城市信息变更
	 * 
	 * @param city
	 */
	private void cityChange(City city, int nowTime) {
		Message message = new Message();
		message.setType(CityConstant.CITY_INFO_CHANGE);
		message.putInt(city.getCityId());
		message.put(city.getNation());
		if (city.getLegionId() != 0) {
			message.putString(LegionCache.getLegionById(city.getLegionId()).getName());
		} else {
			message.putString("");
		}
		if (city.getCityOw() != 0) {
			message.putString(roleService.getRoleById(city.getCityOw()).getName());
		} else {
			message.putString("");
		}

		message.putInt(this.getRemainProtectedTime(nowTime, city));
		for (IoSession is : SessionCache.getAllSession()) {
			is.write(message);
		}
	}

	/**
	 * 势力交替
	 * 
	 * @param city
	 * @param atk
	 * @param wallId
	 */
	private void changeCityNation(City city, WorldArmy atk) {
		byte nation = city.getNation();
		int oldRoleId = city.getCityOw();
		Role oldRole = roleService.getRoleById(oldRoleId);

		// 所有使用该城市为据点的玩家更改据点到新手城
		if (nation != 0) {
			int birthCityId = CityInfoConfigCache.getBirthCityByNation(nation).getCityId();
			for (Role r : RoleCache.getRoleMapByNation(nation).values()) {
				if (r.getStrongHold() == city.getCityId()) {
					r.setStrongHold(birthCityId);
					// 世界大战记录
					serverService.saveHistoryRole(ServerConstant.HOME_FAIL, r, city);
				}
			}
		}
		// 国家更替减少贡献值30%
		int nowTime = Utils.getNowTime();
		Map<Integer, ContributeRank> map = city.getContributeMap().get(nation);
		if (map != null) {
			for (ContributeRank contributeRank : map.values()) {
				int contribute = contributeRank.getContribute();
				int roleId = contributeRank.getRoleId();
				Role role = roleService.getRoleById(roleId);
				this.addContribute(city, role, -contribute * 0.3, nowTime);
				// contribute = (int) Math.floor(contribute - contribute * 0.3);
				// contribute = contribute < minContribute ? minContribute :
				// contribute;
				// contributeRank.setContribute(contribute);
			}
		}

		Role role = RoleCache.getRoleById(atk.getRoleId());
		// 当前直接占领城市
		// 清除所有防守队列
		for (List<WorldArmy> x : city.getDefInfoMap().values()) {
			for (WorldArmy temp : x) {
				if (temp.getRoleId() != -1) {
					Role r = roleService.getRoleById(temp.getRoleId());

					this.clearMineFarmInfo(temp, city);
					int locationId = retreat(r, temp, null);
					messageToClient(CityConstant.CANCEL_MOVE, temp.getId(), city.getCityId(), locationId, nowTime,
							temp, nation);
					this.prestigeWaitAdd(r);
				}

			}
			x.clear();
		}
		city.getCoreDefInfoMap().clear();

		// 清空等待队列
		for (WorldArmy x : city.getLazyInfoMap().values()) {
			if (x.getRoleId() != -1) {
				Role r = roleService.getRoleById(x.getRoleId());
				this.clearMineFarmInfo(x, city);
				int locationId = this.retreat(r, x, null);
				messageToClient(CityConstant.CANCEL_MOVE, x.getId(), city.getCityId(), locationId, nowTime, x, nation);
			}
		}
		city.getLazyInfoMap().clear();
		// 世界大战记录 旧势力
		if (city.getNation() != (byte) ServerConstant.NO_LEGION) {
			serverService.saveHistoryService(ServerConstant.LOSS_CITY_COUNTRY, city);
		}
		// 公会
		if (city.getLegionId() != ServerConstant.NO_LEGION) {
			serverService.saveHistoryLegion(ServerConstant.GET_CITY_LEGION, city);
		}
		// 玩家
		if (city.getCityOw() != ServerConstant.NO_LEGION) {
			Role old = RoleCache.getRoleById(city.getCityOw());
			serverService.saveHistoryRole(ServerConstant.LOSS_CITY, old, city);

		}

		// 更改城市所属势力
		city.setCityOw(role.getId());
		city.setLegionId(role.getLegionId());
		city.setNation(role.getNation());
		// 火焰熄灭
		Map<Integer, CityFire> atkStartTimeMap = city.getAtkStartTimeMap();
		for (Entry<Integer, CityFire> cityFireEntry : atkStartTimeMap.entrySet()) {
			int pathId = cityFireEntry.getKey();
			this.setCityWallFireStartTime(city, pathId, 0, (byte) 0);
		}
		// 势力占城数量改变
		// this.changeNationCityNum(role.getNation(), nation);

		// 世界大战记录 新势力
		serverService.saveHistoryService(ServerConstant.GET_CITY_COUNTRY, city);
		// 公会
		if (role.getLegionId() != ServerConstant.NO_LEGION) {
			serverService.saveHistoryLegion(ServerConstant.LOSS_CITY_LEGION, city);
		}
		// 玩家
		serverService.saveHistoryRole(ServerConstant.GET_CITY, role, city);
		// 对应墙加入
		for (Entry<Integer, List<WorldArmy>> entrySet : city.getAttInfoMap().entrySet()) {
			int wallId = entrySet.getKey();
			List<WorldArmy> atklist = entrySet.getValue();
			for (WorldArmy x : atklist) {
				Role r = RoleCache.getRoleById(x.getRoleId());
				if (r.getNation() != city.getNation()) {
					this.clearMineFarmInfo(x, city);
					int locationId = retreat(r, x, null);
					messageToClient(CityConstant.CANCEL_MOVE, x.getId(), city.getCityId(), locationId, nowTime, x,
							nation);
				} else {
					city.addDefInfoWorldArmy(wallId, x);				
				}
				
				// 只要不是占城成功的部队，都加等待声望奖励
				if (x != atk) {
					this.prestigeWaitAdd(r);
				}
			}
		}

		// 清空进攻队列
		for (List<WorldArmy> list : city.getAttInfoMap().values()) {
			list.clear();
		}

		// 城市变更
		city.setLastChange(Utils.getNowTime());
		city.setChange(true);

		// // 所有使用该城市为据点的玩家更改据点到新手城
		// if (nation != 0) {
		// int birthCityId =
		// CityInfoConfigCache.getBirthCityByNation(nation).getCityId();
		// for (Role r : RoleCache.getRoleMapByNation(nation).values()) {
		// if (r.getStrongHold() == city.getCityId()) {
		// r.setStrongHold(birthCityId);
		// //世界大战记录
		// serverService.saveHistoryRole(ServerConstant.HOME_FAIL, r ,city);
		// }
		// }
		// }
		this.cityChange(city, nowTime);

		String cityName = CityInfoConfigCache.getCityInfoConfigById(city.getCityId()).getCityName();
		StringBuilder sb = this.getChangeCityStringBuilder(city, cityName, atk, role);
		chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_CHANGE_CITY, sb.toString());

		// 城主排名变化发送走马灯
		this.recordRankAndSendNotable(nowTime, oldRoleId, city.getCityOw());

		// 如果首次占城则发放奖励
		this.firstOccupy(oldRoleId, city);
		
		ActivityMessage am = new ActivityMessage();
		am.setAction(ActivityMessage.CHANGE_CITY);
		am.setRole(role);
		am.setValue(city.getCityId());
		
		activityService.noticeActivity(am);
	}

	/**
	 * 记录排名发送系统消息
	 * 
	 * @param nowTime
	 * @param winRoleId
	 * @param lossRoleId
	 * @author wcy 2016年2月29日
	 */
	private void recordRankAndSendNotable(int nowTime, int winRoleId, int lossRoleId) {
		List<Rank> originRankList = rankService.sortOwnCityRank();
		rankService.refreshOwnCityNum(nowTime, winRoleId, lossRoleId);

		List<Rank> targetRankList = rankService.sortOwnCityRank();
		List<Rank> topRankList = rankService.getTopCountList(originRankList, 1);
		rankService.ifTopRankChangeThenNotable(RankConstant.RANK_TYPE_OWN_CITY, topRankList, targetRankList);
	}

	/**
	 * 如果首次占城则发放奖励
	 * 
	 * @param cityAfterChange
	 * @author wcy 2016年2月29日
	 */
	private void firstOccupy(int oldRoleId, City cityAfterChange) {
		if (this.checkFirstOccupy(oldRoleId)) {
			// 记录首次占领的国家
			cityAfterChange.setFirstOccupyNation(cityAfterChange.getNation());
			int cityId = cityAfterChange.getCityId();
			CityInfoConfig cityInfoConfig = CityInfoConfigCache.getCityInfoConfigById(cityId);
			String sb = cityInfoConfig.getAward();
			String cityName = cityInfoConfig.getCityName();

			Map<Integer, Role> winRolesMap = RoleCache.getRoleMapByNation(cityAfterChange.getNation());
			for (Role winRole : winRolesMap.values()) {
				this.sendFirstOccupyMail(winRole, sb, cityName);
			}

		}
	}

	/**
	 * 发送首次占领的邮件
	 * 
	 * @param role
	 * @param awardSb
	 * @param cityName
	 * @author wcy 2016年3月4日
	 */
	private void sendFirstOccupyMail(Role role, String awardSb, String cityName) {
		// 发放奖励
		// 发送邮件
		Mail mail = new Mail();
		mail.setTitle("首次攻占【" + cityName + "】奖励");
		mail.setContext("由于您的阵营成功攻下【" + cityName + "】城，所有本势力玩家获得了首次攻占奖励，请注意查收！");
		mail.setAttached(awardSb);
		mailService.sendSYSMail(role, mail);
	}

	/**
	 * 检查首次占领
	 * 
	 * @param oldRoleId
	 * @return
	 * @author wcy 2016年2月29日
	 */
	private boolean checkFirstOccupy(int oldRoleId) {
		return oldRoleId == -1 ? true : false;
	}

	/**
	 * 创建贡献排行榜
	 * 
	 * @param roleId
	 * @return
	 * @author wcy
	 */
	private ContributeRank createContributeRank(int roleId, int nowTime) {
		ContributeRank atkContributeRank = new ContributeRank();
		atkContributeRank.setContribute(0);
		atkContributeRank.setRoleId(roleId);
		atkContributeRank.setEnterTime(nowTime);
		return atkContributeRank;
	}

	/**
	 * 玩家对战
	 * 
	 * @param roleA
	 * @param roleB
	 * @param armyToken
	 * @return
	 */
	private ResultData PvP(Role roleA, Role roleB, int armyToken) {
		roleService.getArmyToken(roleA, armyToken);
		FormationData formationData = roleA.getFormationData().get(roleA.getUseFormationID());
		TroopData roleData = PVPUitls.getTroopDataByRole(roleA);
		FormationData formationData2 = roleB.getFormationData().get(roleB.getUseFormationID());
		TroopData roleData2 = PVPUitls.getTroopDataByRole(roleB);
		ResultData resultData = new Battle().fightPVP(roleData, roleData2);
		roleService.addRolePopulation(roleB, -resultData.defLost);
		roleService.addRolePopulation(roleA, -resultData.attLost);
		return resultData;
	}

	/**
	 * 获取英雄
	 * 
	 * @param role
	 * @param heroId
	 * @param is
	 */
	private void getHero(Role role, int heroId, IoSession is) {
		if (role.getHeroMap().containsKey(heroId)) {
			return;
		}
		HeroConfig config2 = HeroConfigCache.getHeroConfigById(heroId);
		if (config2 == null)
			return;
		Hero hero = heroService.createHero(role, heroId);
		heroService.addHero(is, hero);
	}

	private int countPresitge(Role role, Role targetRole) {
		double temp = CityConstant.Q_D_J_C * ((targetRole.getPrestige() - role.getPrestige()) / CityConstant.Q_D_F_M);
		if (temp < 0) {
			temp = 0;
		}
		int res = CityConstant.BASICS_POINT + CityConstant.WIN_POINT + (int) Math.ceil(temp);
		return res;
	}

	/**
	 * 获得秒世界行军需要的元宝
	 * 
	 * @param role
	 * @param marchId
	 * @return
	 * @author wcy
	 */
	private int getSpeedUpWorldMarchNeedGold(Role role, WorldMarch worldMarch) {
		int nowTime = Utils.getNowTime();
		int totalTime = worldMarch.getMarchTotalTime();
		int beginTime = worldMarch.getMarchBeginTime();

		int remainTime = totalTime - (nowTime - beginTime);

		double needSecond = Math.ceil(remainTime / 60.0);
		int second = GeneralNumConstantCache.getValue("WORLD_MARCH_NEED_GOLD");
		int needGold = (int) Math.ceil(needSecond / second);
		return needGold;
	}

	/**
	 * 获得部队兵力数
	 * 
	 * @param role
	 * @param worldArmy
	 * @return
	 * @author wcy
	 */
	private int getWorldArmyNum(Role role, WorldArmy worldArmy) {
		int totalArmyNum = 0;
		if (worldArmy == null) {
//			System.out.println("worldArmy is null");
		}
		for (Integer heroId : worldArmy.getFormationData().getData().values()) {
			if (heroId > 2) {
				Hero hero = role.getHeroMap().get(heroId);
				int num = hero.getArmsNum();
				totalArmyNum += num;
			}
		}
		return totalArmyNum;
	}

	/**
	 * 根据路径和兵力数获得所需要的粮草
	 * 
	 * @param totalArmyNum
	 * @param worldWarPath
	 * @return
	 * @author wcy
	 */
	private int getWorldWarPathNeedFoodByArmyNum(Role role,WorldArmy worldArmy, int totalMarchTime) {
	
		int resultFood = 0;
		int totalArmyNum = 0;
		if (worldArmy == null) {
//			System.out.println("worldArmy is null");
		}
		int totalLevel = 0;
		int totalNum = 0;
		List<Hero> heroList = new ArrayList<>();
		for (Integer heroId : worldArmy.getFormationData().getData().values()) {
			if (heroId > 2) {
				Hero hero = role.getHeroMap().get(heroId);
//				int num = hero.getArmsNum();
//				totalArmyNum += num;
//				totalNum++;
//				totalLevel += hero.getLv();
				heroList.add(hero);
			}
		}
		resultFood = (int) Math.ceil(InComeUtils.fightNeedFood(heroList) * totalMarchTime
				/ 28800.0);
		return resultFood;
	}

	/**
	 * 2015-12-23 部队离开城市
	 * 
	 * @param worldArmy
	 * @param city
	 * @author wcy
	 */
	private void leaveCity(WorldArmy worldArmy, City city) {
		Map<Integer, List<WorldArmy>> defInfoMap = city.getDefInfoMap();

		int wallId = 0;
		for (Entry<Integer, List<WorldArmy>> entrySet : defInfoMap.entrySet()) {
			List<WorldArmy> list = entrySet.getValue();
			if (list == null) {
				continue;
			}

			if (list.contains(worldArmy)) {
				wallId = entrySet.getKey();
				break;
			}
		}

		city.removeDefInfoWorldArmy(wallId, worldArmy);

		city.removeCoreInfo(worldArmy);

		String id = worldArmy.getId();
		city.removeLazyInfo(id);

		clearMineFarmInfo(worldArmy, city);
	}

	private void clearMineFarmInfo(WorldArmy worldArmy, City city) {
		Role temp = roleService.getRoleById(worldArmy.getRoleId());
		if (city.getCityId() == CityInfoConfigCache.getBirthCityByNation(temp.getNation()).getCityId()) {
			return;
		}
		if (this.checkVillage(city.getCityId())) {
			temp.setVillageMineFarmWorldArmyId("");
		} else {
			for (MineFarm farm : CityCache.getAllMine().get(city.getCityId()).values()) {
				if (farm.getWorldArmyId() != null && farm.getWorldArmyId().equals(worldArmy.getId())) {
					farm.setRoleId(0);
					farm.setWorldArmyId("");
				}
			}
		}
	}

	/**
	 * 获得行军剩余时间
	 * 
	 * @param worldMarch
	 * @param nowTime
	 * @return
	 * @author wcy
	 */
	private int getWorldMarchTotalRemainTime(WorldMarch worldMarch, int nowTime) {
		int totalTime = worldMarch.getMarchTotalTime();
		int beginTime = worldMarch.getMarchBeginTime();
		int remainTime = totalTime - (nowTime - beginTime);
		if (remainTime < 0) {
			remainTime = 0;
		}
		return remainTime;
	}

	/**
	 * 撤退
	 * 
	 * @param role 撤退部队的所属玩家
	 * @param worldArmy 撤退部队
	 * @param currentCity 撤退到的城市，如果为null则回到据点
	 * @return
	 * @author wcy
	 */
	private int retreat(Role role, WorldArmy worldArmy, City currentCity) {
		String id = worldArmy.getId();
		WorldMarchCache.removeMarch(id);
		WorldMarchCache.removeGuideMarch(id);
		if (role == null) {
			return 0;
		}
		byte nation = role.getNation();
		int locationCityId = 0;
		if (currentCity != null && this.checkVillage(currentCity.getCityId()) && role.getVillageNation() == nation) {
			//如果城市是小山村
			locationCityId = currentCity.getCityId();
		} else if (currentCity != null && (currentCity.getNation() == nation)) {
			// 出发城没有被占领
			locationCityId = currentCity.getCityId();
		} else {
			// 出发城市被占领
			if (!checkStrongHoldLoss(role)) {
				locationCityId = role.getStrongHold();
			} else {
				CityInfoConfig birthCityConfig = CityInfoConfigCache.getBirthCityByNation(nation);
				locationCityId = birthCityConfig.getCityId();
			}
			currentCity = CityCache.getCityByCityId(locationCityId);
		}
		worldArmy.setLocation(locationCityId, role);
		if(this.checkVillage(currentCity.getCityId())){
			role.addVillageDefWorldArmy(worldArmy);
		}else{
			currentCity.addLazyInfo(worldArmy);			
		}
		return locationCityId;
	}

	/**
	 * 检查是否可以回据点
	 * 
	 * @param role
	 * @return 被占领返回true
	 * @author wcy
	 */
	private boolean checkStrongHoldLoss(Role role) {
		byte nation = role.getNation();
		CityInfoConfig birthCityConfig = CityInfoConfigCache.getBirthCityByNation(nation);
		int birthCityId = birthCityConfig.getCityId();
		int strongHold = role.getStrongHold();
		if (strongHold == birthCityId) {// 检查据点是否是出生城
			return false;
		} else if (CityCache.getCityByCityId(strongHold).getNation() == nation) {
			return false;
		}
		return true;
	}

	private void clearDef(City city, WorldArmy worldArmy) {
		// 过滤停留
		String id = worldArmy.getId();
		if (city.getLazyInfoMap().containsKey(id)) {
			worldArmy = city.getLazyInfoMap().get(id);
			city.removeLazyInfo(id);
		}
		// 过滤护都府
		if (city.getCoreDefInfoMap().contains(worldArmy)) {
			city.removeCoreInfo(worldArmy);
		}
		// 过滤城墙
		for (Entry<Integer, List<WorldArmy>> x : city.getDefInfoMap().entrySet()) {
			if (x.getValue().contains(worldArmy)) {
				city.removeDefInfoWorldArmy(x.getKey(), worldArmy);
				break;
			}
		}
	}

	/**
	 * 获得复活剩余时间
	 * 
	 * @param currentTime
	 * @author wcy 2016年1月7日
	 */
	private int getWakeRemainTime(int currentTime, WorldArmy worldArmy) {
		int deadTime = worldArmy.getLastDeadTime();
		int wakeTime = GeneralNumConstantCache.getValue("WORLD_ARMY_WAKE_TIME");
		int endTime = deadTime + wakeTime;
		int remainTime = endTime - currentTime;
		if (remainTime < 0) {
			remainTime = 0;
		}

		return remainTime;
	}

	/**
	 * 检查部队是否复活
	 * 
	 * @param worldArmy
	 * @return
	 * @author wcy 2016年1月7日
	 */
	private boolean checkIsWorldArmyAlive(int currentTime, WorldArmy worldArmy) {
		if (this.getWakeRemainTime(currentTime, worldArmy) == 0) {
			return true;
		}
		return false;
	}

	@Override
	public Message speedUpWorldArmyAlive(Role role, String id) {
		Message message = new Message();
		message.setType(CityConstant.SPEED_UP_WORLD_ARMY_ALIVE);

		WorldArmy worldArmy = role.getWorldArmyById(id);
		if (worldArmy == null) {
			return null;
		}
		int nowTime = Utils.getNowTime();
		if (checkIsWorldArmyAlive(nowTime, worldArmy)) {
			// 部队已经复活
			message.putShort(ErrorCode.WORLD_ARMY_ALREADY_ALIVE);
			return message;
		}

		int cost = this.getSpeedUpWorldArmyAliveNeedGold(worldArmy, nowTime);
		if (role.getGold() < cost) {
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		message.putShort(ErrorCode.SUCCESS);

		roleService.addRoleGold(role, -cost);

		int needTime = GeneralNumConstantCache.getValue("WORLD_ARMY_WAKE_TIME");
		worldArmy.setLastDeadTime(worldArmy.getLastDeadTime() - needTime);

		return message;
	}

	/**
	 * 获得秒世界行军需要的元宝
	 * 
	 * @param role
	 * @param marchId
	 * @return
	 * @author wcy
	 */
	private int getSpeedUpWorldArmyAliveNeedGold(WorldArmy worldArmy, int nowTime) {
		int remainTime = this.getWakeRemainTime(nowTime, worldArmy);

		double needSecond = Math.ceil(remainTime / 60.0);
		int second = GeneralNumConstantCache.getValue("WORLD_ARMY_ALIVE_NEED_GOLD");
		int needGold = (int) Math.ceil(needSecond / second);
		return needGold;
	}

	@Override
	public Message showSpeedUpWorldArmyAlive(Role role, String id) {
		Message message = new Message();
		message.setType(CityConstant.SHOW_SPEED_UP_WORLD_ARMY_ALIVE);

		WorldArmy worldArmy = role.getWorldArmyById(id);
		if (worldArmy == null) {
			return null;
		}
		int nowTime = Utils.getNowTime();
		int cost = this.getSpeedUpWorldArmyAliveNeedGold(worldArmy, nowTime);

		message.putInt(cost);
		return message;
	}

	@Override
	public void closeWorldMarch() {
		ConcurrentHashMap<String, WorldMarch> allMarch = WorldMarchCache.getAllMarch();
		for (Entry<String, WorldMarch> marchEntry : allMarch.entrySet()) {
			WorldMarch worldMarch = marchEntry.getValue();
			City currentCity = worldMarch.getCurrentCity();
			int currentCityId = currentCity.getCityId();

			WorldArmy worldArmy = worldMarch.getWorldArmy();
			int roleId = worldArmy.getRoleId();
			Role role = RoleCache.getRoleById(roleId);

			if (currentCityId == 0 || (currentCity.getNation() != role.getNation())) {
				currentCityId = 0;
			} else {
				currentCity.addDefInfoWorldArmyToMinWorldArmyList(worldArmy);
			}
			worldArmy.setLocation(currentCityId, role);
			worldArmy.removeTroopData();
		}

		allMarch.clear();
	}

	/**
	 * 设置城墙冒火的开始时间
	 * 
	 * @param linkWallID
	 * @param nowTime
	 * @author wcy 2016年1月11日
	 */
	private void setCityWallFireStartTime(City city, int pathId, int nowTime, byte nation) {

		CityFire cityFire = city.getAtkStartTimeMap().get(pathId);
		if (cityFire == null) {
			cityFire = new CityFire();
			city.getAtkStartTimeMap().put(pathId, cityFire);
		}
		cityFire.setNation(nation);
		cityFire.setStartTime(nowTime);
	}

	/**
	 * 改变国家拥有的城市数量
	 * 
	 * @param winNation
	 * @param lossNation
	 * @author wcy 2016年1月12日
	 */
	private void changeNationCityNum(byte winNation, byte lossNation) {
		Server server = ServerCache.getServer();
		Map<Byte, Integer> nationCityMap = server.getNationCityNumMap();
		nationCityMap.put(winNation, nationCityMap.get(winNation) + 1);
		nationCityMap.put(lossNation, nationCityMap.get(lossNation) - 1);
	}

	/**
	 * 获取反抗所需的粮草
	 * 
	 * @param role
	 * @return
	 * @author
	 */
	private int checkFood(Role role) {
		int tempAllHp = 0;
		FormationData formationData = role.getFormationData().get(1001);
		int totalLevel = 0;
		int heroNum = 0;
		List<Hero> heroList = new ArrayList<>();
		for (int x : formationData.getData().values()) {
			if (x <= FightConstant.NUM_USE_2)
				continue;
			Hero hero = role.getHeroMap().get(x);
//			tempAllHp += hero.getArmsNum();
//			totalLevel += hero.getLv();
			heroList.add(hero);
			heroNum++;
		}
		// tempAllHp = tempAllHp / 10;
//		int fightNeedFood = InComeUtils.fightNeedFood(tempAllHp, totalLevel, heroNum);
		int fightNeedFood = InComeUtils.fightNeedFood(heroList);
		return fightNeedFood;
	}

	@Override
	public Message getMyLordInfo(Role role) {
		Message message = new Message();
		message.setType(CityConstant.GET_MYLOARD_INFO);
		String lordName = "";
		int lordLv = 1;
		String lordRank = "";
		int fightNeedFood = 0;

		int myLordRoleId = role.getRoleCity().getMyLordRoleId();
		Role myLord = roleService.getRoleById(myLordRoleId);
		List<History> pumHistories = role.getPumHistoryList();

		boolean hasLord = false;
		if (myLord != null) {
			hasLord = true;
			lordName = myLord.getName();
			lordLv = (int) myLord.getLv();

			RankConfig config = RankConfigCache.getRankConfigById(myLord.getRank());
			lordRank = config.getName();
			fightNeedFood = this.checkFood(role);
		}

		message.put((byte) (hasLord ? 1 : 0));
		message.putString(lordName);
		message.putInt(lordLv);
		message.putString(lordRank);
		message.putInt(fightNeedFood);
		message.putInt(myLordRoleId);
		message.put((byte) (this.checkCanNotResistance(role) ? 1 : 0));

		message.putInt(pumHistories.size());
		for (History history : pumHistories) {
			message.putInt(history.getTime());
			message.putInt(history.getYear());
			message.put(history.getType());
			message.putString(history.getStr());
		}

		return message;
	}

	@Override
	public Message clearPumHistory(Role role) {
		Message message = new Message();
		message.setType(CityConstant.CLEAR_PUM_INFO);

		role.getPumHistoryList().clear();
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	private StringBuilder getChangeCityStringBuilder(City city, String cityName, WorldArmy atk, Role role) {
		StringBuilder sb = new StringBuilder();
		sb.append(city.getCityId()).append(",");
		sb.append(cityName).append(",");
		sb.append(role.getId()).append(",");
		sb.append(Utils.getNationName(role.getNation())).append(",");
		sb.append(role.getName()).append(",");
		sb.append(atk.getName()).append(",");
		return sb;
	}

	private StringBuilder getMarchStringBuilder(Role role, City finalCity, String finalCityName) {
		StringBuilder sb = new StringBuilder();
		sb.append(Utils.getNationName(role.getNation())).append(",");
		sb.append(role.getName()).append(",");
		sb.append(role.getId()).append(",");
		sb.append(finalCity.getCityId()).append(",");
		sb.append(Utils.getNationName(finalCity.getNation())).append(",");
		sb.append(finalCityName).append(",");
		return sb;
	}

	private StringBuilder getCityUnderAttackStringBuilder(City city, String cityName, byte atkNation) {
		StringBuilder sb = new StringBuilder();
		sb.append(city.getCityId()).append(",");
		sb.append(cityName).append(",");
		sb.append(Utils.getNationName(city.getNation())).append(",");
		sb.append(Utils.getNationName(atkNation)).append(",");
		return sb;
	}

	private StringBuilder getFightResultStringBuilder(City city, String cityName, WorldArmy atkArmy, Role atkRole,
			ResultData resultData) {
		StringBuilder sb = new StringBuilder();
		sb.append(Utils.getFightResultName(resultData.winCamp)).append(",");
		sb.append(Utils.getNationName(atkRole.getNation())).append(",");
		sb.append(atkArmy.getName()).append(",");
		sb.append(atkArmy.getId()).append(",");
		sb.append(atkArmy.getRoleId()).append(",");
		sb.append(atkRole.getName()).append(",");
		sb.append(city.getCityId()).append(",");
		sb.append(cityName).append(",");
		sb.append(resultData.uuid).append(",");
		return sb;
	}

	private StringBuilder getGradeRankStringBuilder(Role role, String gradeName) {
		StringBuilder sb = new StringBuilder();
		sb.append(role.getName()).append(",");
		sb.append(gradeName).append(",");
		sb.append(Utils.getNationName(role.getNation())).append(",");
		return sb;
	}

	@Override
	public void handlerGuideArrive(WorldMarch worldMarch) {
		City lastCurrentCity = worldMarch.getCurrentCity();
		City lastTargetCity = worldMarch.getTargetCity();
		int a = lastCurrentCity.getCityId();
		int b = lastTargetCity.getCityId();
		// WorldWarPath path = WorldWarPathCache.getWorldWarPathByCities(a, b);
		int pathId = Utils.getCityPathNum(a, b);
		int nowTime = Utils.getNowTime();
		// 移动游标
		worldMarch.nextCity();
		City city = worldMarch.getCurrentCity();
		// String cityName =
		// CityInfoConfigCache.getCityInfoConfigById(city.getCityId()).getCityName();
		Role role = RoleCache.getRoleById(worldMarch.getWorldArmy().getRoleId());
		WorldArmy worldArmy = worldMarch.getWorldArmy();
		// 有后续目标

		// 到达

		// 加入攻击队列
		// city.addAttInfoWorldArmy(path.getLinkWallID(), worldArmy);
		// 更改所有阵型中英雄的location
		worldArmy.setLocation(city.getCityId(), role);
		byte type = CityConstant.OVER_MOVE;
		// 设置城墙冒火的开始时间
		// this.setCityWallFireStartTime(city, pathId, nowTime,
		// role.getNation());
		// 战况行军成功
		// serverService.saveHistoryRole(ServerConstant.MARCH_SUESS, worldArmy,
		// "");
		// serverService.saveHistoryLegion(ServerConstant.MARCH_SUESS_LEGION,
		// worldArmy);

		// StringBuilder sb = this.getCityUnderAttackStringBuilder(city,
		// cityName, role.getNation());
		// chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_UNDER_ATTACK,
		// sb.toString());

		// 从缓存中移除行军信息
		WorldMarchCache.removeGuideMarch(worldMarch.getId());

		this.messageToClient(type, worldMarch.getId(), a, b, Utils.getNowTime(), worldMarch);

		// 攻击队列
		List<WorldArmy> atkList = new ArrayList<>();
		atkList.add(worldArmy);
		// 防御队列
		List<WorldArmy> defList = role.getVillageDefList();
		boolean win = this.handlerGuideFight(city, atkList, defList);

		this.guideChangeCityNation(city, role, worldArmy);

	}

	private boolean handlerGuideFight(City city, List<WorldArmy> atkList, List<WorldArmy> defList) {
		if (defList.size() == 0)
			return true;

		Battle battle = new Battle();
		// int winContribute =
		// GeneralNumConstantCache.getValue("WIN_GET_CONTRIBUTE");
		// int loseContribute =
		// GeneralNumConstantCache.getValue("LOSS_GET_CONTRIBUTE");
		// int nowTime = Utils.getNowTime();
		// String cityName =
		// CityInfoConfigCache.getCityInfoConfigById(city.getCityId()).getCityName();

		// 开始战斗
		for (;;) {
			if (atkList.size() == 0 || defList.size() == 0) {
				break;
			}
			WorldArmy atkArmy = atkList.get(0);
			WorldArmy defArmy = defList.get(0);
			// Role atkRole = null;
			// Role defRole = null;
			TroopData atk = atkArmy.getTroopData();
			TroopData def = defArmy.getTroopData();
			ResultData resultData = battle.fightPVP(atk, def);
			resultsDao.insertResults(resultData);
			Role winRole = null;
			Role lossRole = null;
			List<WorldArmy> lossList = null;
			WorldArmy winArmy = null;
			WorldArmy lossArmy = null;
			if (resultData.winCamp == 1) {
				winRole = RoleCache.getRoleById(atk.getPlayerId());
				lossRole = RoleCache.getRoleById(def.getPlayerId());
				// atkRole = winRole;
				// defRole = lossRole;
				lossList = defList;
				winArmy = atkList.get(0);
				lossArmy = defList.get(0);
			} else {
				winRole = RoleCache.getRoleById(def.getPlayerId());
				lossRole = RoleCache.getRoleById(atk.getPlayerId());
				// atkRole = lossRole;
				// defRole = winRole;
				lossList = atkList;
				winArmy = defList.get(0);
				lossArmy = atkList.get(0);
			}

			WorldArmy lossWorldArmy = lossList.get(0);
			/** 战况信息 */
			// 胜利方战况 玩家&&公会
			if (winRole != null) {
				// 玩家战况
				serverService.saveHistoryRole(ServerConstant.FIGHT_WIN, winArmy, resultData.uuid);
				// 公会战况
				// Legion winLegion =
				// LegionCache.getLegionById(winRole.getLegionId());
				// if (winLegion != null) {
				// serverService.saveHistoryLegion(ServerConstant.FIGHT_WIN_LEGION,
				// winArmy);
				// }
			}
			// // 战败方
			// if (lossRole != null) {
			// // 玩家战况
			// serverService.saveHistoryRole(ServerConstant.FIGHT_LOSS,
			// lossArmy, resultData.uuid);
			// // 公会战况
			// Legion lossLegion =
			// LegionCache.getLegionById(lossRole.getLegionId());
			// if (lossLegion != null) {
			// serverService.saveHistoryLegion(ServerConstant.FIGHT_LOSS_LEGION,
			// lossArmy);
			// }
			// }
			// 败者回到据点
			// int locationId = this.retreat(lossRole, lossWorldArmy, null);
			lossWorldArmy.setLastDeadTime(Utils.getNowTime());
			lossWorldArmy.removeTroopData();
			lossList.remove(0);

			// addContribute(city, lossRole, loseContribute,nowTime);
			// addContribute(city, winRole, winContribute,nowTime);
			// byte nation = 0;
			// if (lossRole == null) {
			// nation = 0;
			// } else {
			// nation = lossRole.getNation();
			// }
			// winArmy.setWinTime(winArmy.getWinTime() + 1);
			// if (lossWorldArmy.getRoleId() != -1) {
			// messageToClient(CityConstant.CANCEL_MOVE, lossWorldArmy.getId(),
			// city.getCityId(), locationId, nowTime,
			// lossWorldArmy, nation);

			// StringBuilder sb = this.getFightResultStringBuilder(city,
			// cityName, atkArmy, atkRole, resultData);
			// chatService.systemChat(atkRole,
			// ChatConstant.MESSAGE_TYPE_FIGHT_RESULT, sb.toString());

			// 记录排行榜，如果更替则发送走马灯
			// this.recordRankAndSendNotable(nowTime, winArmy, lossArmy);

			// }
		}
		return defList.size() == 0 ? true : false;
	}

	/**
	 * 势力交替
	 * 
	 * @param city
	 * @param atk
	 * @param wallId
	 */
	private void guideChangeCityNation(City city, Role role, WorldArmy worldArmy) {
		// byte nation = city.getNation();
		// int oldRoleId = city.getCityOw();
		// Role oldRole = roleService.getRoleById(oldRoleId);

		// 所有使用该城市为据点的玩家更改据点到新手城
		// if (nation != 0) {
		// int birthCityId =
		// CityInfoConfigCache.getBirthCityByNation(nation).getCityId();
		// for (Role r : RoleCache.getRoleMapByNation(nation).values()) {
		// if (r.getStrongHold() == city.getCityId()) {
		// r.setStrongHold(birthCityId);
		// // 世界大战记录
		// serverService.saveHistoryRole(ServerConstant.HOME_FAIL, r, city);
		// }
		// }
		// }
		// //国家更替减少贡献值30%
		// int nowTime = Utils.getNowTime();
		// Map<Integer, ContributeRank> map =
		// city.getContributeMap().get(nation);
		// if (map != null) {
		// for (ContributeRank contributeRank : map.values()) {
		// int contribute = contributeRank.getContribute();
		// int roleId = contributeRank.getRoleId();
		// Role role = roleService.getRoleById(roleId);
		// this.addContribute(city, role, - contribute * 0.3, nowTime);
		// // contribute = (int) Math.floor(contribute - contribute * 0.3);
		// // contribute = contribute < minContribute ? minContribute :
		// contribute;
		// // contributeRank.setContribute(contribute);
		// }
		// }

		// 当前直接占领城市
		// 清除所有防守队列

		// for (List<WorldArmy> x : city.getDefInfoMap().values()) {
		// for (WorldArmy temp : x) {
		// if (temp.getRoleId() != -1) {
		// Role r = roleService.getRoleById(temp.getRoleId());
		//
		// this.clearMineFarmInfo(temp, city);
		// int locationId = retreat(r, temp, null);
		// messageToClient(CityConstant.CANCEL_MOVE, temp.getId(),
		// city.getCityId(), locationId, nowTime,
		// temp, nation);
		// }
		//
		// }
		// x.clear();
		// }
		// city.getCoreDefInfoMap().clear();
		role.getVillageDefList().clear();

		// 清空等待队列
		// for (WorldArmy x : city.getLazyInfoMap().values()) {
		// if (x.getRoleId() != -1) {
		// Role r = roleService.getRoleById(x.getRoleId());
		// this.clearMineFarmInfo(x, city);
		// int locationId = this.retreat(r, x, null);
		// messageToClient(CityConstant.CANCEL_MOVE, x.getId(),
		// city.getCityId(), locationId, nowTime, x, nation);
		// }
		// }
		// city.getLazyInfoMap().clear();
		// 世界大战记录 旧势力
		// if(city.getNation() != (byte)ServerConstant.NO_LEGION)
		// {
		// serverService.saveHistoryService(ServerConstant.LOSS_CITY_COUNTRY,
		// city);
		// }
		// //公会
		// if(city.getLegionId() != ServerConstant.NO_LEGION)
		// {
		// serverService.saveHistoryLegion(ServerConstant.GET_CITY_LEGION,
		// city);
		// }
		// //玩家
		// if(city.getCityOw() != ServerConstant.NO_LEGION)
		// {
		// Role old = RoleCache.getRoleById(city.getCityOw());
		// serverService.saveHistoryRole(ServerConstant.LOSS_CITY, old, city);
		//
		// }

		// 更改城市所属势力
		// city.setCityOw(role.getId());
		// city.setLegionId(role.getLegionId());
		// city.setNation(role.getNation());
		byte oldNation = role.getNation();
		role.setVillageNation(role.getNation());
		// 火焰熄灭
		// Map<Integer, CityFire> atkStartTimeMap = city.getAtkStartTimeMap();
		// for(Entry<Integer,CityFire>
		// cityFireEntry:atkStartTimeMap.entrySet()){
		// int pathId = cityFireEntry.getKey();
		// this.setCityWallFireStartTime(city, pathId, 0, (byte) 0);
		// }
		// 势力占城数量改变
		// this.changeNationCityNum(role.getNation(), nation);

		// 世界大战记录 新势力
		// serverService.saveHistoryService(ServerConstant.GET_CITY_COUNTRY,
		// city);
		// 公会
		// if(role.getLegionId() != ServerConstant.NO_LEGION)
		// {
		// serverService.saveHistoryLegion(ServerConstant.LOSS_CITY_LEGION,
		// city);
		// }
		// 玩家
		serverService.saveHistoryRole(ServerConstant.GET_CITY, role, city);
		// 对应墙加入
		// for (Entry<Integer, List<WorldArmy>> entrySet :
		// city.getAttInfoMap().entrySet()) {
		// int wallId = entrySet.getKey();
		// List<WorldArmy> atklist = entrySet.getValue();
		// for (WorldArmy x : atklist) {
		// Role r = RoleCache.getRoleById(x.getRoleId());
		// if (r.getNation() != city.getNation()) {
		// this.clearMineFarmInfo(x, city);
		// int locationId = retreat(r, x, null);
		// messageToClient(CityConstant.CANCEL_MOVE, x.getId(),
		// city.getCityId(), locationId, nowTime, x,
		// nation);
		// } else {
		// city.addDefInfoWorldArmy(wallId, x);
		// }
		// }
		// }
		role.addVillageDefWorldArmy(worldArmy);

		// 清空进攻队列
		// for (List<WorldArmy> list : city.getAttInfoMap().values()) {
		// list.clear();
		// }

		// 城市变更
		// city.setLastChange(Utils.getNowTime());
		// city.setChange(true);

		// // 所有使用该城市为据点的玩家更改据点到新手城
		// if (nation != 0) {
		// int birthCityId =
		// CityInfoConfigCache.getBirthCityByNation(nation).getCityId();
		// for (Role r : RoleCache.getRoleMapByNation(nation).values()) {
		// if (r.getStrongHold() == city.getCityId()) {
		// r.setStrongHold(birthCityId);
		// //世界大战记录
		// serverService.saveHistoryRole(ServerConstant.HOME_FAIL, r ,city);
		// }
		// }
		// }
		// this.cityChange(city,nowTime);

		Message message = new Message();
		message.setType(CityConstant.CITY_INFO_CHANGE);
		message.putInt(city.getCityId());
		message.put(role.getVillageNation());
		message.putString("");
		message.putString(role.getName());
		message.putInt(0);
		// IoSession is = SessionCache.getSessionById(role.getId());
		for (IoSession is : SessionCache.getAllSession()) {
			is.write(message);
		}

		// String cityName =
		// CityInfoConfigCache.getCityInfoConfigById(city.getCityId()).getCityName();
		// StringBuilder sb = this.getChangeCityStringBuilder(city,cityName,
		// atk, role);
		// chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_CHANGE_CITY,
		// sb.toString());

		// 城主排名变化发送走马灯
		// this.recordRankAndSendNotable(nowTime,oldRoleId,city.getCityOw());

		// 如果首次占城则发放奖励
		// this.firstOccupy(oldRoleId, city);
		boolean firstOccupy = oldNation == 0 ? true : false;
		CityInfoConfig cityInfoConfig = CityInfoConfigCache.getCityInfoConfigById(city.getCityId());
		String awardSb = cityInfoConfig.getAward();
		String cityName = cityInfoConfig.getCityName();
		if (firstOccupy) {
			this.sendFirstOccupyMail(role, awardSb, cityName);
		}

	}

	private void checkMineFameTime(MineFarm mineFarm) {
		// 判断是否应该自动离开
		if (Utils.getNowTime() - mineFarm.getStarTime() >= 21600) {
			mineFarm.setRoleId(0);
			mineFarm.setWorldArmyId("");
			mineFarm.setChange(true);
		}
	}

}
