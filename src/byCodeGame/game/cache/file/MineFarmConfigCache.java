package byCodeGame.game.cache.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byCodeGame.game.entity.file.MineFarmConfig;


public class MineFarmConfigCache {
	//存储所有的配置表中的数据   用来与数据库比对  暂无其它用法
	private static Map<Integer , MineFarmConfig> mineFarmList= new HashMap<Integer, MineFarmConfig>();
	private static Map<Integer, ArrayList<MineFarmConfig>> cityMine = new HashMap<Integer,ArrayList<MineFarmConfig>>();
	public static void putMineFarmConfig(MineFarmConfig mf){
		mineFarmList.put(mf.getBuildingID(), mf);
		List<MineFarmConfig> temp = cityMine.get(mf.getCityId());
		if(temp == null)
		{
			cityMine.put(mf.getCityId(), new ArrayList<MineFarmConfig>());
		}
		cityMine.get(mf.getCityId()).add(mf);
	}
	
	public static Map<Integer , MineFarmConfig> getAllMap()
	{
		return mineFarmList;
	}
	/**
	 * 获取单个
	 * @param id
	 * @return
	 */
	public static MineFarmConfig getMineFarmConfigMap(int id){
		return mineFarmList.get(id);
	}

	/**
	 * 获取城市集合
	 * @param cityId
	 * @return
	 */
	public static List<MineFarmConfig> getMineFarmList(int cityId) {
		return cityMine.get(cityId);
	}
}
