package byCodeGame.game.entity.po;

import java.util.LinkedList;
import java.util.ListIterator;

import byCodeGame.game.cache.file.WorldWarPathCache;
import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.file.WorldWarPath;
import byCodeGame.game.util.Utils;

/**
 * 世界行军
 * 
 * @author wcy
 *
 */
public class WorldMarch {
	/** 唯一表示符 '行军开始时间，roleId' */
	private String id;
	/** 部队 */
	private WorldArmy worldArmy;
	/** 路径 */
	private LinkedList<City> path = new LinkedList<>();
	/** 当前城市指针 */
	private ListIterator<City> currentPathIter = null;
	/** 目标城市指针 */
	private ListIterator<City> targetPathIter = null;
	/**出发城市*/
	private City beginCity = null;
	/** 当前城市 */
	private City currentCity = null;
	/** 目标城市 */
	private City targetCity = null;
	/**最终城市*/
	private City finalCity = null;
	/** 当前城市与目标城市间的路径号 */
	private int currentPath = 0;
	/** 设置行军总时间 */
	private int marchTotalTime = 0;
	/** 开始行军时间 */
	private int marchStartTime = 0;
	/** 行军时间 */
	private int marchTime = 0;
	/**行军开始时间(定了之后不会变)*/
	private int marchBeginTime = 0;
	/**国家*/
	private byte nation;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return 获得该行军的部队
	 */
	public WorldArmy getWorldArmy() {
		return worldArmy;
	}

	/**
	 * @param 设置行军的部队
	 */
	public void setWorldArmy(WorldArmy worldArmy) {
		this.worldArmy = worldArmy;
	}

	/**
	 * @return the path
	 */
	public LinkedList<City> getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(LinkedList<City> path) {
		this.path = path;
		setBeginCity(path.get(0));
		setFinalCity(path.get(path.size()-1));
		currentPathIter = path.listIterator();
		targetPathIter = path.listIterator();

		// 移动到第一个城市
		// 设置当前所在的城市
		currentCity = currentPathIter.hasNext()
			? currentPathIter.next()
			: null;

		// 设置目标城市
		targetCity = targetPathIter.hasNext()
			? targetPathIter.next()
			: null;
		targetCity = targetPathIter.hasNext()
			? targetPathIter.next()
			: null;

		currentPath = Utils.getCityPathNum(currentCity, targetCity);
		marchTime = getMarchTime(currentCity, targetCity);
	}

	/**
	 * 移动至下一个城市
	 * 
	 * @return
	 * @author wcy
	 */
	public City nextCity() {
		// 设置当前所在的城市
		currentCity = currentPathIter.hasNext()
			? currentPathIter.next()
			: null;

		// 设置目标城市
		targetCity = targetPathIter.hasNext()
			? targetPathIter.next()
			: null;

		currentPath = Utils.getCityPathNum(currentCity, targetCity);
		marchTime = getMarchTime(currentCity, targetCity);
		
		return currentCity;
	}

	/***
	 * 移动目标城市值终点
	 * 
	 * @author wcy 2015年12月29日
	 */
	public void moveTargetCityToFinalCity(){
		while(targetPathIter.hasNext()){
			targetCity = targetPathIter.next();
		}
	}
	/**
	 * 移动至前一个城市
	 * 
	 * @return
	 * @author wcy
	 */
	public City previousCity() {
		// 设置当前所在的城市
		currentCity = currentPathIter.hasPrevious()
			? currentPathIter.previous()
			: null;

		// 设置目标城市
		targetCity = targetPathIter.hasPrevious()
			? targetPathIter.previous()
			: null;

		currentPath = Utils.getCityPathNum(currentCity, targetCity);
		marchTime = getMarchTime(currentCity, targetCity);

		return currentCity;
	}

	/**
	 * 获得当前城市
	 * 
	 * @return
	 * @author wcy
	 */
	public City getCurrentCity() {
		return currentCity;
	}

	/**
	 * 获得目标城市
	 * 
	 * @return
	 * @author wcy
	 */
	public City getTargetCity() {
		return targetCity;
	}

	/**
	 * @return the targetPathIter
	 */
	public ListIterator<City> getTargetPathIter() {
		return targetPathIter;
	}

	public ListIterator<City> getCurrentPathIter() {
		return currentPathIter;
	}

	/**
	 * 获得当前路径号
	 * 
	 * @return
	 * @author wcy
	 */
	public int getCurrentPath() {
		return currentPath;
	}
	
	public void setCurrentPath(int currentPath){
		this.currentPath = currentPath;
	}

	/**
	 * @return the marchTotalTime
	 */
	public int getMarchTotalTime() {
		return marchTotalTime;
	}

	/**
	 * @param marchTotalTime the marchTotalTime to set
	 */
	public void setMarchTotalTime(int marchTotalTime) {
		this.marchTotalTime = marchTotalTime;
	}

	/**
	 * @return 行军开始时间
	 */
	public int getMarchStartTime() {
		return marchStartTime;
	}

	/**
	 * @param 设置行军开始时间
	 */
	public void setMarchStartTime(int marchStartTime) {
		this.marchStartTime = marchStartTime;
	}

	/**
	 * @return the marchTime
	 */
	public int getMarchTime() {
		return marchTime;
	}

	/**
	 * 获得城与城之间的行军时间
	 * 
	 * @param currentCity
	 * @param targetCity
	 * @return
	 * @author wcy
	 */
	private int getMarchTime(City currentCity, City targetCity) {

		if (currentCity == null || targetCity == null) {
			return 0;
		}
		int city1Id = currentCity.getCityId();
		int city2Id = targetCity.getCityId();
		WorldWarPath worldWarPath = WorldWarPathCache.getWorldWarPathByCities(city1Id, city2Id);
		return worldWarPath.getMarchTime();
	}

	/**
	 * @return the nation
	 */
	public byte getNation() {
		return nation;
	}

	/**
	 * @param nation the nation to set
	 */
	public void setNation(byte nation) {
		this.nation = nation;
	}

	/**
	 * @return the marchBeginTime
	 */
	public int getMarchBeginTime() {
		return marchBeginTime;
	}

	/**
	 * @param marchBeginTime the marchBeginTime to set
	 */
	public void setMarchBeginTime(int marchBeginTime) {
		this.marchBeginTime = marchBeginTime;
	}

	/**
	 * @return the finalCity
	 */
	public City getFinalCity() {
		return finalCity;
	}

	/**
	 * @param finalCity the finalCity to set
	 */
	public void setFinalCity(City finalCity) {
		this.finalCity = finalCity;
	}

	/**
	 * @return the beginCity
	 */
	public City getBeginCity() {
		return beginCity;
	}

	/**
	 * @param beginCity the beginCity to set
	 */
	public void setBeginCity(City beginCity) {
		this.beginCity = beginCity;
	}

}
