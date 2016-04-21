package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.po.WorldDoubleP;



/**
 * 2015/8/19
 * @author shao
 *	玩家封地 矿藏 农田 详情
 */
public class MineFarm {
	public MineFarm(){}
	
	public MineFarm(int cityId, int mapKey, int resourceType, int starTime,
			int crazyStarTime, int roleId) {
		super();
		this.cityId = cityId;
		//TODO 此处修改未完成
//		this.buildingID  = 
//		this.mapKey = mapKey;
//		this.resourceType = resourceType;
		this.starTime = starTime;
//		this.crazyStarTime = crazyStarTime;
		this.roleId = roleId;
	}

	private int buildingID;
	
	/**
	 * 所在城市id
	 */
	private int cityId;
//	/**
//	 * 矿藏或农田所在位置
//	 */
//	private int mapKey;
	
//	/**
//	 * 类型(1为矿藏2位农田)
//	 */
//	private int resourceType;
	/**
	 * 采矿  (农田)开始时间
	 */
	private int starTime;
//	/**
//	 * 矿藏疯狂模式开启时间
//	 */
//	private int crazyStarTime;
	/**
	 * 玩家Id
	 */
	private int roleId;
	
	private String worldArmyId;
	
//	//以下属性通过计算不存数据库
//	/**
//	 * 开采结束时间
//	 */
//	private int endTime;
//	/**
//	 * 开采进行时间
//	 */
//	private int useTime;
//	/**
//	 * 疯狂模式该玩家享用时间
//	 */
//	private int useCrazyTime;
//	/**
//	 * 疯狂模式结束时间
//	 */
//	private int crazyEndTime;
//	/**
//	 * 玩家占领剩余时间
//	 */
//	private int overplusTime;
//	
//	/**
//	 * 玩家占领剩余保护时间
//	 */
//	private int overProtectTime;
//	
//	/**
//	 * 疯狂模式剩余时间
//	 */
//	private int overCrazyTime;
	
	private boolean change;
	
	private String doubleP;
	
	private Map<Integer, WorldDoubleP> doublePMap = new HashMap<Integer, WorldDoubleP>();
	
	public int getCityId() {
		return cityId;
	}
	
	public void setCityId(int cityId) {
		this.change = true;
		this.cityId = cityId;
	}
	
	
	
//	public int getMapKey() {
//		this.change = true;
//		return mapKey;
//	}
//
//	public void setMapKey(int mapKey) {
//		this.change = true;
//		this.mapKey = mapKey;
//	}

//	public int getResourceType() {
//		return resourceType;
//	}
//
//	public void setResourceType(int resourceType) {
//		this.change = true;
//		this.resourceType = resourceType;
//	}

	public int getStarTime() {
		return starTime;
	}
	public void setStarTime(int starTime) {
		this.change = true;
		this.starTime = starTime;
	}
//	public int getCrazyStarTime() {
//		return crazyStarTime;
//	}
//	public void setCrazyStarTime(int crazyStarTime) {
//		this.change = true;
//		this.crazyStarTime = crazyStarTime;
//	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.change = true;
		this.roleId = roleId;
	}
	
//	public int getEndTime() {
//		
//		if(this.starTime==0){
//			this.endTime=0;
//		}else{
//			this.endTime=this.starTime+CityConstant.OCCUPY_TIME;
//		}
//		return this.endTime;
//	}
	
//	public int getUseTime() {
//		if(this.starTime==0){
//			this.useTime=0;
//		}else{
//			int nowTime=(int)(System.currentTimeMillis()/1000);
//			useTime=nowTime-this.starTime;
//			useTime=useTime>CityConstant.OCCUPY_TIME?
//			CityConstant.OCCUPY_TIME:useTime;
//		}
//		return useTime;
//	}
//	public int getUseCrazyTime() {
//		if(this.crazyStarTime==0){
//			this.useCrazyTime=0;
//		}else{
//			int nowTime=(int)(System.currentTimeMillis()/1000);
//			nowTime=nowTime>getCrazyEndTime()?getCrazyEndTime():nowTime;
//			int crazyStar=crazyStarTime<starTime?crazyStarTime:starTime;
//			useCrazyTime=nowTime-crazyStar;
//			useCrazyTime=useCrazyTime>CityConstant.CRAZY_TIME?0:useCrazyTime;
//		}
//		return useCrazyTime;
//	}

//	public int getCrazyEndTime() {
//		if(this.crazyStarTime==0){
//			this.crazyEndTime=0;
//		}else{
//			this.crazyEndTime=this.crazyStarTime+CityConstant.CRAZY_TIME;
//		}
//		return this.crazyEndTime;
//	}
//	public int getOverplusTime() {
//		if(this.starTime==0){
//			this.overplusTime=0;
//		}else{
//			this.overplusTime=CityConstant.OCCUPY_TIME-this.getUseTime();
//		}
//		return overplusTime;
//	}
//	public int getOverProtectTime() {
//		int nowTime=(int)(System.currentTimeMillis()/1000);
//		if(this.starTime==0){
//			this.overProtectTime=0;
//		}else{
//			//当前时间减去 开采开始时间  
//			int gatherTime=nowTime-this.starTime;
//			gatherTime=gatherTime>CityConstant.PROTECT_TIME?
//					CityConstant.PROTECT_TIME:gatherTime;
//			this.overProtectTime=CityConstant.PROTECT_TIME-gatherTime;
//		}
//		return overProtectTime;
//	}
//	public int getOverCrazyTime() {
//		int nowTime=(int)(System.currentTimeMillis()/1000);
//		if(this.crazyStarTime==0){
//			this.overCrazyTime=0;
//		}else{
//			//当前时间减去 开采开始时间  
//			int gatherTime=nowTime-this.crazyStarTime;
//			gatherTime=gatherTime>CityConstant.CRAZY_TIME?
//					CityConstant.CRAZY_TIME:gatherTime;
//			this.overCrazyTime=CityConstant.CRAZY_TIME-gatherTime;
//		}
//		return overCrazyTime;
//	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public int getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(int buildingID) {
		this.buildingID = buildingID;
	}

	public String getWorldArmyId() {
		return worldArmyId;
	}

	public void setWorldArmyId(String worldArmyId) {
		this.worldArmyId = worldArmyId;
	}

	public Map<Integer, WorldDoubleP> getDoublePMap() {
		return doublePMap;
	}

	public void setDoublePMap(Map<Integer, WorldDoubleP> doublePMap) {
		this.doublePMap = doublePMap;
	}

	public String getDoubleP() {
		StringBuilder sb = new StringBuilder();
		for(WorldDoubleP x : this.doublePMap.values())
		{
			sb.append(x.getRoleId()).append(",").append(x.getStartTime()).append(";");
		}
		return sb.toString();
	}

	public void setDoubleP(String doubleP) {
		if(doubleP != null && !doubleP.equals(""))
		{
			String[] strs = doubleP.split(";");
			for(String x : strs)
			{
				String[] xx = x.split(",");
				WorldDoubleP temp = new WorldDoubleP(xx);
				this.doublePMap.put(temp.getRoleId(), temp);
			}
		}
		this.doubleP = doubleP;
	}
	
}
