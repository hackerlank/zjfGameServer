package byCodeGame.game.cache.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.MineFarm;
import byCodeGame.game.entity.file.MineFarmConfig;

public class CityCache {
	/** 全部城市信息			*/
	private static ConcurrentHashMap<Integer, City> allCityMap = new ConcurrentHashMap<Integer, City>();
	
	/** 矿信息				*/
	private static ConcurrentHashMap<Integer, Map<Integer,MineFarm>> allMine = new ConcurrentHashMap<Integer, Map<Integer,MineFarm>>();
	
	private static Map<Integer,Byte> villageNationMap = new HashMap<>();
	
	/**获得小山村的国籍*/
	public static Map<Integer,Byte> getVillageNationMap(){
		return villageNationMap;
	}
	
	public static void addMinFram(MineFarm mineFarm)
	{
		if(mineFarm.getBuildingID() == 15010&& mineFarm.getCityId() == 50){
		}
		if(!allMine.containsKey(mineFarm.getCityId()))
		{
			allMine.put(mineFarm.getCityId(), new HashMap<Integer,MineFarm>());
		}
		allMine.get(mineFarm.getCityId()).put(mineFarm.getBuildingID(), mineFarm);
	}
	
	public static MineFarm getMineFarmById(int cityId ,int buildId)
	{
		Map<Integer,MineFarm> map = allMine.get(cityId);		
		return map.get(buildId);
	}
	
	/**
	 * 存储每个城市中的玩家信息
	 * key 城市ID  线程安全
	 * list 玩家id集合
	 */
	private static ConcurrentMap<Integer,List<Integer>> cityRoleMap=new  ConcurrentHashMap<Integer,List<Integer>>();
	
	/**
	 *key 城市id
	 * Key   封地位置
	 * value 玩家id
	 */
	private static ConcurrentMap<Integer,Map<Integer, Integer>> cityRoleKeyMap=new  ConcurrentHashMap<Integer,Map<Integer, Integer>>();
	
	/**
	 * 每50分钟将时间超过5小时的加入到Map中
	 * 再每5秒执行跟新方法
	 */
	private static List<MineFarm> quartzMapCache=new ArrayList<MineFarm>();
	/**
	 * minefarm表的缓存数据
	 * 城市的农田和矿藏缓存
	 */
	//最外面map key :cityId  中间map key农田类型   : 最里面 map key:type 位置 
	private static ConcurrentMap<Integer,Map<Integer, Map<Integer,MineFarm>>> mineFarmMap=new ConcurrentHashMap<Integer,Map<Integer,Map<Integer, MineFarm>>>();
	public static ConcurrentMap<Integer, List<Integer>> getCityRoleMap() {
		return cityRoleMap;
	}
	public static  List<Integer> getCityRoleByCityId(Integer cityId) {
		return cityRoleMap.get(cityId);
	}
	public static void setCityRoleMap(ConcurrentMap<Integer, List<Integer>> cityRoleMap) {
		CityCache.cityRoleMap = cityRoleMap;
	}
	public static ConcurrentMap<Integer, Map<Integer, Integer>> getCityRoleKeyMap() {
		return cityRoleKeyMap;
	}
	public static void setCityRoleKeyMap(
			ConcurrentMap<Integer, Map<Integer, Integer>> cityRoleKeyMap) {
		CityCache.cityRoleKeyMap = cityRoleKeyMap;
	}
	public static Map<Integer, Integer> getCityRoleKeyByCityIdMap(Integer cityId) {
		return cityRoleKeyMap.get(cityId);
	}
	public static ConcurrentMap<Integer, Map<Integer, Map<Integer, MineFarm>>> getMineFarmMap() {
		return mineFarmMap;
	}
	public static MineFarm getMineFarmById(Integer cityId,Integer type,Integer key) {
		MineFarm mineFarm=mineFarmMap.get(cityId).get(type).get(key);
		return mineFarm;
	}
	public static void setMineFarmMap(
			ConcurrentMap<Integer, Map<Integer, Map<Integer, MineFarm>>> mineFarmMap) {
		CityCache.mineFarmMap = mineFarmMap;
	}
	public static List<MineFarm> getQuartzMapCache() {
		return quartzMapCache;
	}
	public static void setQuartzMapCache(List<MineFarm> quartzMapCache) {
		CityCache.quartzMapCache = quartzMapCache;
	}
	public static ConcurrentHashMap<Integer, City> getAllCityMap() {
		return allCityMap;
	}
	public static void setAllCityMap(ConcurrentHashMap<Integer, City> allCityMap) {
		CityCache.allCityMap = allCityMap;
	}
	public static City getCityByCityId(int cityId)
	{
		return allCityMap.get(cityId);
	}
	public static ConcurrentHashMap<Integer, Map<Integer,MineFarm>> getAllMine() {
		return allMine;
	}
	public static void setAllMine(ConcurrentHashMap<Integer, Map<Integer,MineFarm>> allMine) {
		CityCache.allMine = allMine;
	}
}
