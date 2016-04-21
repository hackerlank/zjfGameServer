package byCodeGame.game.moudle.server.service;

import java.util.Calendar;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.sun.swing.internal.plaf.metal.resources.metal;

import byCodeGame.game.cache.file.CityInfoConfigCache;
import byCodeGame.game.cache.file.MineFarmConfigCache;
import byCodeGame.game.cache.file.PrestigeShopCache;
import byCodeGame.game.cache.local.LegionCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.ServerCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.db.dao.ServerDao;
import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.entity.bo.MineFarm;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.Server;
import byCodeGame.game.entity.file.CityInfoConfig;
import byCodeGame.game.entity.file.MineFarmConfig;
import byCodeGame.game.entity.file.PrestigeShop;
import byCodeGame.game.entity.po.History;
import byCodeGame.game.entity.po.WorldArmy;
import byCodeGame.game.entity.po.WorldMarketItems;
import byCodeGame.game.moudle.city.CityConstant;
import byCodeGame.game.moudle.nation.NationConstant;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.server.ServerConstant;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.Utils;

public class ServerServiceImpl implements ServerService{

	private ServerDao serverDao;
	public void setServerDao(ServerDao serverDao){
		this.serverDao = serverDao;
	}

	@Override
	public void serverInit() {
		Server server = serverDao.getServer(ServerConstant.SERVER_ID);
		int nowTime = Utils.getNowTime();
		ServerCache.setServer(server);

		if (server == null) {
			server = new Server();
			server.setServerId(ServerConstant.SERVER_ID);
			server.setStartTime(nowTime);
			server.setSeason(ServerConstant.SEASON_SPRING);
			server.setYear(ServerConstant.START_YEAR);
			refreshWorldMarket(server,Utils.getNowTime());
			
			serverDao.insertServer(server);
			ServerCache.setServer(server);
			
			server.setChange(true);
		} else {
			this.serverChange();
		}
		
	}

	private void refreshWorldMarket(Server server,int nowTime) {
		// 世界商品初始化		
		Map<Integer, WorldMarketItems> map = server.getWorldItemsMap();
		Map<Integer, PrestigeShop> publicMap = PrestigeShopCache.getPublicItems();
		this.addWorldMarketItems(publicMap, map);
		
		server.setWorldItemsRefreshTime(nowTime);
	}

	@Override
	public void serverChange() {
		Server server = ServerCache.getServer();
		byte temp = server.getSeason();
		temp++;
		// 正式
//		 if (temp > ServerConstant.SEASON_WINTER) {
		// 测试
		if (temp > ServerConstant.SEASON_AUTUMN) {
			server.setSeason(ServerConstant.SEASON_SPRING);
			server.setYear(server.getYear() + ServerConstant.YEAR_ADD);
		} else {
			server.setSeason(temp);
		}
	}
	
	/**
	 * 
	 * @param map
	 * @param targetMap
	 * @author wcy 2015年12月28日
	 */
	private void addWorldMarketItems(Map<Integer,PrestigeShop> map,Map<Integer,WorldMarketItems> targetMap){
		for (PrestigeShop prestigeShop : map.values()) {
			
			int id = prestigeShop.getRowId();
			int inventory = prestigeShop.getInventory();		
			
			WorldMarketItems worldMarketItems = new WorldMarketItems();
			worldMarketItems.setId(id);
			worldMarketItems.setInventory(inventory);
			
			targetMap.put(id, worldMarketItems);
		}
	}

	@Override
	public void refreshWorldMarket(int nowTime) {
		// TODO Auto-generated method stub
		Server server = ServerCache.getServer();
		server.getWorldItemsMap().clear();

		refreshWorldMarket(server,nowTime);		
	}

	
	/**
	 * 记录世界大战记录
	 * @author xjd
	 */
	public void saveHistoryRole(byte type,WorldArmy army,String uuid) {
		History history = new History();
		history.setTime(Utils.getNowTime());
		history.setYear(ServerCache.getServer().getYear());
		history.setType(type);
		history.setUuid(uuid);
		StringBuilder sb = new StringBuilder();
		Role role = RoleCache.getRoleById(army.getRoleId());
		if(role == null) return;
		CityInfoConfig config = CityInfoConfigCache.getCityInfoConfigById(army.getLocation());
		//根据类型处理不同的情况
		switch (type) {
		case ServerConstant.MARCH_SUESS:
			sb.append("cityName").append(",").append(config.getCityName()).append(";");
			sb.append("teamName").append(",").append(army.getName()).append(";");
			sb.append("teamId").append(",").append(army.getId()).append(";");
			break;
		case ServerConstant.MARCH_FAIL:
			sb.append("cityName").append(",").append(config.getCityName()).append(";");
			sb.append("teamName").append(",").append(army.getName()).append(";");
			sb.append("teamId").append(",").append(army.getId()).append(";");
			break;
		case ServerConstant.FIGHT_WIN:
			sb.append("cityName").append(",").append(config.getCityName()).append(";")
				.append("otherCity").append(",").append(config.getCityName()).append(";");
			sb.append("teamName").append(",").append(army.getName()).append(";");
			sb.append("teamId").append(",").append(army.getId()).append(";");
			break;
		case ServerConstant.FIGHT_LOSS:
			CityInfoConfig config2 = CityInfoConfigCache.getCityInfoConfigById(role.getStrongHold());
			sb.append("cityName").append(",").append(config.getCityName()).append(";")
				.append("otherCity").append(",").append(config2.getCityName()).append(";");
			sb.append("teamName").append(",").append(army.getName()).append(";");
			sb.append("teamId").append(",").append(army.getId()).append(";");
			break;
		default:
			return;
			
		}
		history.setStr(sb.toString());
		role.addHistory(history);
		this.sendMessageToRole(role,type);
	}
	
	/**
	 * 记录世界大战记录 玩家信息
	 * 重载方法
	 * @author xjd
	 */
	public void saveHistoryRole(byte type,Role role,City city)
	{
		History history = new History();
		history.setTime(Utils.getNowTime());
		history.setYear(ServerCache.getServer().getYear());
		history.setType(type);
		CityInfoConfig config = CityInfoConfigCache.getCityInfoConfigById(city.getCityId());
		StringBuilder sb = new StringBuilder();
		switch (type) {
		case ServerConstant.HOME_FAIL:
			sb.append("cityName").append(",").append(config.getCityName()).append(";");
			break;
		case ServerConstant.LOSS_CITY:
			sb.append("cityName").append(",").append(config.getCityName()).append(";");
			break;
		case ServerConstant.GET_CITY:
			sb.append("cityName").append(",").append(config.getCityName()).append(";");
			break;
		default:
			return;
		}
		history.setStr(sb.toString());
		role.addHistory(history);
		this.sendMessageToRole(role,type);
	}
	
	/**
	 * 记录世界大战记录 玩家信息
	 * 重载方法
	 */
	public void saveHistoryRole(byte type, Role role, MineFarm mineFarm ,WorldArmy worldArmy,String uuid) {
		History history = new History();
		history.setTime(Utils.getNowTime());
		history.setYear(ServerCache.getServer().getYear());
		history.setType(type);
		history.setUuid(uuid);
		MineFarmConfig config = MineFarmConfigCache.getMineFarmConfigMap(mineFarm.getBuildingID());
		CityInfoConfig config2 = CityInfoConfigCache.getCityInfoConfigById(mineFarm.getCityId());
		StringBuilder sb = new StringBuilder();
		switch (type) {
		case ServerConstant.LOSS_MINE:
			sb.append("teamName").append(",").append(worldArmy.getName()).append(";");
			sb.append("teamId").append(",").append(worldArmy.getId()).append(";");
			sb.append("cityName").append(",").append(config2.getCityName()).append(";");
			sb.append("mine").append(",").append(config.getName()).append(";");
			break;
		default:
			return;
		}
		history.setStr(sb.toString());
		role.addHistory(history);
		this.sendMessageToRole(role,type);
	}
	
	/**
	 * 记录世界大战记录 玩家信息
	 * 城墙建筑
	 */
	public void saveHistoryDefRole(byte type, WorldArmy army, String name) {
		History history = new History();
		history.setTime(Utils.getNowTime());
		history.setYear(ServerCache.getServer().getYear());
		history.setType(type);
		CityInfoConfig config2 = CityInfoConfigCache.getCityInfoConfigById(army.getLocation());
		Role role = RoleCache.getRoleById(army.getRoleId());
		StringBuilder sb = new StringBuilder();
		switch (type) {
		case ServerConstant.CHANGE_DEFINFO:
			sb.append("teamName").append(",").append(army.getName()).append(";");
			sb.append("teamId").append(",").append(army.getId()).append(";");
			sb.append("cityName").append(",").append(config2.getCityName()).append(";");
			sb.append("buildName").append(",").append(name).append(";");
			break;
		default:
			return;
		}
		history.setStr(sb.toString());
		role.addHistory(history);
		this.sendMessageToRole(role,type);
	}
	
	/**
	 * 世界大战记录 公会
	 * @author xjd
	 */
	public void saveHistoryLegion(byte type, WorldArmy army) {
		History history = new History();
		history.setTime(Utils.getNowTime());
		history.setYear(ServerCache.getServer().getYear());
		history.setType(type);
		//部队的玩家
		Role role = RoleCache.getRoleById(army.getRoleId());
		//目的地的配置表
		CityInfoConfig config = CityInfoConfigCache.getCityInfoConfigById(army.getLocation());
		//公会
		Legion legion = LegionCache.getLegionById(role.getLegionId());
		if(legion == null) return;
		StringBuilder sb = new StringBuilder();
		switch (type) {
		case ServerConstant.MARCH_SUESS_LEGION:
			sb.append("nickName").append(",").append(role.getName()).append(";")
				.append("cityName").append(",").append(config.getCityName()).append(";");
			sb.append("teamName").append(",").append(army.getName()).append(";");
			sb.append("teamId").append(",").append(army.getId()).append(";");
			break;
			
		case ServerConstant.FIGHT_WIN_LEGION:
			sb.append("nickName").append(",").append(role.getName()).append(";")
				.append("cityName").append(",").append(config.getCityName()).append(";")
				.append("otherCity").append(",").append(config.getCityName()).append(";");
			sb.append("teamName").append(",").append(army.getName()).append(";");
			sb.append("teamId").append(",").append(army.getId()).append(";");
			break;
		case ServerConstant.FIGHT_LOSS_LEGION:
			CityInfoConfig config2 = CityInfoConfigCache.getCityInfoConfigById(role.getStrongHold());
			sb.append("nickName").append(",").append(role.getName()).append(";")
				.append("cityName").append(",").append(config.getCityName()).append(";")
				.append("otherCity").append(",").append(config2.getCityName()).append(";");
			sb.append("teamName").append(",").append(army.getName()).append(";");
			sb.append("teamId").append(",").append(army.getId()).append(";");
			break;
		default:
			return;
		}
		history.setStr(sb.toString());
		legion.addHistory(history);
		this.sendMessageToLegion(legion, type);
	}
	
	/**
	 * 世界大战记录 公会
	 * 重载方法
	 * @author xjd
	 */
	public void saveHistoryLegion(byte type, City city) {
		History history = new History();
		history.setTime(Utils.getNowTime());
		history.setYear(ServerCache.getServer().getYear());
		history.setType(type);
		//部队的玩家
		Role role = RoleCache.getRoleById(city.getCityOw());
		//目的地的配置表
		CityInfoConfig config = CityInfoConfigCache.getCityInfoConfigById(city.getCityId());
		//公会
		Legion legion = LegionCache.getLegionById(city.getLegionId());
		StringBuilder sb = new StringBuilder();
		switch (type) {
		case ServerConstant.GET_CITY_LEGION:
			sb.append("cityName").append(",").append(config.getCityName()).append(";");
			sb.append("nickName").append(",").append(role.getName()).append(";");
			break;
		case ServerConstant.LOSS_CITY_LEGION:
			sb.append("cityName").append(",").append(config.getCityName()).append(";");
			break;
		default:
			return;
		}
		history.setStr(sb.toString());
		legion.addHistory(history);
		this.sendMessageToLegion(legion, type);
	}
	
	
	/**
	 * 世界大战记录 公会
	 * 城墙建筑
	 */
	public void saveHistoryLegion(byte type, WorldArmy army, String name) {
		History history = new History();
		history.setTime(Utils.getNowTime());
		history.setYear(ServerCache.getServer().getYear());
		history.setType(type);
		//部队的玩家
		Role role = RoleCache.getRoleById(army.getRoleId());
		//目的地的配置表
		CityInfoConfig config = CityInfoConfigCache.getCityInfoConfigById(army.getLocation());
		//公会
		Legion legion = LegionCache.getLegionById(role.getLegionId());
		StringBuilder sb = new StringBuilder();
		switch (type) {
		case ServerConstant.CHANGE_DEFINFO_LEGION:
			sb.append("nickName").append(",").append(role.getName()).append(";");
			sb.append("teamName").append(",").append(army.getName()).append(";");
			sb.append("teamId").append(",").append(army.getId()).append(";");
			sb.append("cityName").append(",").append(config.getCityName()).append(";");
			sb.append("buildName").append(",").append(name).append(";");
			break;
		default:
			return;
		}
		history.setStr(sb.toString());
		legion.addHistory(history);
		this.sendMessageToLegion(legion, type);
	}
	
	/**
	 * 世界大战记录	势力信息
	 * @author xjd
	 */
	public void saveHistoryService(byte type, City city) {
		CityInfoConfig config = CityInfoConfigCache.getCityInfoConfigById(city.getCityId());
		History history = new History();
		history.setTime(Utils.getNowTime());
		history.setYear(ServerCache.getServer().getYear());
		history.setType(type);
		StringBuilder sb = new StringBuilder();
		
		sb.append("country").append(",");
		sb.append(Utils.getNationName(city.getNation()));
		sb.append(";");
		if(city.getLegionId() == ServerConstant.NO_LEGION)
		{
			sb.append("corp").append(",").append("").append(";");
		}else {
			Legion legion = LegionCache.getLegionById(city.getLegionId());
			sb.append("corp").append(",").append(legion.getName()).append(";");
		}
		sb.append("cityName").append(",").append(config.getCityName()).append(";");
		switch (type) {
		case ServerConstant.GET_CITY_COUNTRY:
			Role role = RoleCache.getRoleById(city.getCityOw());
			sb.append("nickName").append(",").append(role.getName()).append(";");
			break;
		case ServerConstant.LOSS_CITY_COUNTRY:
			break;
		default:
			return;
		}
		history.setStr(sb.toString());
		ServerCache.getServer().addHistory(history);
		this.sendMessageToAll(type);
	}

	/**
	 * 通知玩家自身
	 * @param role
	 * @param type
	 */
	private void sendMessageToRole(Role role,byte type)
	{
		Message message = new Message();
		message.setType(ServerConstant.NEW_HISTORY);
		message.put(type);
		IoSession ioSession = SessionCache.getSessionById(role.getId());
		if(ioSession != null)
		{
			ioSession.write(message);
		}
	}
	
	/**
	 * 通知公会玩家
	 * @param legion
	 * @param type
	 */
	private void sendMessageToLegion(Legion legion,byte type)
	{
		Message message = new Message();
		message.setType(ServerConstant.NEW_HISTORY);
		message.put(type);
		for(int x : legion.getMemberSet())
		{
			IoSession ioSession = SessionCache.getSessionById(x);
			if(ioSession != null)
			{
				ioSession.write(message);
			}
		}
		
	}
	
	/**
	 * 通知所有玩家
	 * @param type
	 */
	private void sendMessageToAll(byte type)
	{
		Message message = new Message();
		message.setType(ServerConstant.NEW_HISTORY);
		message.put(type);
		for(IoSession io : SessionCache.getAllSession())
		{
			io.write(message);
		}
	}
}
