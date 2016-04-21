package byCodeGame.game.entity.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;









import byCodeGame.game.cache.file.CityGarrisonConfigCache;
import byCodeGame.game.entity.file.CityInfoConfig;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.entity.po.CityFire;
import byCodeGame.game.entity.po.CityOwGet;
import byCodeGame.game.entity.po.ContributeRank;
import byCodeGame.game.entity.po.FormationData;
import byCodeGame.game.entity.po.WorldArmy;
import byCodeGame.game.moudle.city.CityConstant;

/**
 * 2015/12/14
 * 
 * @author xjd
 *
 */
public class City {
	/**
	 * 城市ID
	 */
	private int cityId;
	/**
	 * 玩家ID集合 格式为mapKey,RoleId;mapKey,RoleId;...
	 */
	private String roleIds;
	/**
	 * 将玩家Id转换成玩家Id集合
	 */
	private Map<Integer, Integer> roleMap = new HashMap<Integer, Integer>();

	private Map<Integer, Integer> roleLocation = new HashMap<Integer, Integer>();
	
	/** 城主ID */
	private int cityOw;

	/** 势力编号 */
	private byte nation;

	/** 所属公会编号 */
	private int legionId;

	/** 防守数据 DB */
	private String defInfo;
	/**等待数据DB*/
	private String lazyInfo;
	/**都护府防守数据DB*/
	private String coreDefInfo;

	/** 防守信息<城门编号，防守军队列表> */
	private Map<Integer, List<WorldArmy>> defInfoMap = new HashMap<>();
	/**都护府防守表*/
	private List<WorldArmy> coreDefInfoMap = new ArrayList<>();

	/** 防守信息<城门编号，进攻军队列表> */
	private Map<Integer, List<WorldArmy>> attInfoMap = new HashMap<>();
	/** 等待部队信息<部队id,部队>*/
	private Map<String,WorldArmy> lazyInfoMap = new HashMap<>();
	/** 贡献值信息 */
	private String contribute;
	/** 贡献值信息<国家,<玩家id,贡献值>> */
	private Map<Byte, Map<Integer, ContributeRank>> contributeMap = new HashMap<>();
	/** 城主今日抽成				   */
	private CityOwGet cityOwGet = new CityOwGet();
	
	private int lastChange;
	/**进攻开始时间<pathId,CityFire>*/
	private Map<Integer,CityFire> atkStartTimeMap = new HashMap<>();
	
	private byte firstOccupyNation;
	

	// TODO 经营预留

	/*************************************************************************/
	public City() {

	}

	public City(CityInfoConfig config) {
		this.cityId = config.getCityId();
		this.nation = (byte) config.getNation();
		this.cityOw = config.getCityOw();
		for (int i = 1; i <= config.getWallNum(); i++) {
			this.defInfoMap.put(i, new ArrayList<WorldArmy>());
			if(config.getCityType() == 0)
			{
				
			}else {
				String strs[] = CityGarrisonConfigCache.getCityGarrisonConfigById(config.getCityId()).getTroopDatas().split(";");
				for(String str : strs)
				{
					WorldArmy worldArmy = new WorldArmy();
					worldArmy.setRoleId(-1);
					worldArmy.setFormationData(new FormationData(str));
					worldArmy.setId("wolegeca");
					this.addDefInfoWorldArmy(i, worldArmy);
				}
			}
		}
	}

	private boolean change;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
		this.change = true;

	}

	public Map<Integer, Integer> getRoleMap() {
		return roleMap;
	}

	public  synchronized int addRole(int roleId)
	{
		int key = this.roleMap.size()+1;
		this.roleMap.put(key, roleId);
		return key;
	}

	
	public void setRoleMap(Map<Integer, Integer> roleMap) {
		this.roleMap = roleMap;
		this.change = true;
	}

	public String getRoleIds() {
		Map<Integer, Integer> map = roleMap;
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			int i = entry.getKey();
			sb.append(i).append(",");
			int y = entry.getValue();
			sb.append(y).append(";");
		}
		this.roleIds = sb.toString();
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		if (roleIds != null && !roleIds.equals("")) {
			String[] strs = roleIds.split(";");
			for (String strValue : strs) {
				String[] str = strValue.split(",");
				roleMap.put(Integer.valueOf(str[0]), Integer.valueOf(str[1]));
			}
		}
		this.change = true;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public int getCityOw() {
		return cityOw;
	}

	public void setCityOw(int cityOw) {
		this.cityOw = cityOw;
	}

	public byte getNation() {
		return nation;
	}

	public void setNation(byte nation) {
		this.nation = nation;
	}

	public int getLegionId() {
		return legionId;
	}

	public void setLegionId(int legionId) {
		this.legionId = legionId;
	}

	/**
	 * @return the defInfoMap
	 */
	public Map<Integer, List<WorldArmy>> getDefInfoMap() {
		return defInfoMap;
	}

	/**
	 * @return the defInfo
	 */
	public String getDefInfo() {
		StringBuffer sb = new StringBuffer();
		for (Entry<Integer, List<WorldArmy>> gate : defInfoMap.entrySet()) {
			Integer gateNum = gate.getKey();
			List<WorldArmy> list = gate.getValue();

			sb.append(gateNum).append("~");

			for (WorldArmy worldArmy : list) {
				int roleId = worldArmy.getRoleId();
				String id = worldArmy.getId();
				int heroCaptainId = worldArmy.getHeroCaptainId();
				String name = worldArmy.getName();
				int clientID = worldArmy.getClientId();
				FormationData formationData = worldArmy.getFormationData();
				Map<Byte, Integer> map = formationData.getData();

				sb.append(roleId).append(":").append(id).append("[").append(name).append(",").append(clientID)
						.append(",").append(heroCaptainId).append(">");

				for (Entry<Byte, Integer> formationDataEntry : map.entrySet()) {
					byte pos = formationDataEntry.getKey();
					int heroId = formationDataEntry.getValue();
//					if (heroId > 2) {
						sb.append(pos).append(",").append(heroId).append("!");
//					}
				}
				sb.append("]");
			}

			sb.append(";");

		}
		this.defInfo = sb.toString();
		return defInfo;
	}

	/**
	 * @param defInfo the defInfo to set
	 */
	public void setDefInfo(String defInfo) {
		if (defInfo == null || defInfo.equals("")) {
			return;
		}
		this.defInfo = defInfo;

		String[] gates = defInfo.split(";");
		for (String gate : gates) {
			String[] str = gate.split("~");
			int gateNum = Integer.parseInt(str[0]);
			List<WorldArmy> list = defInfoMap.get(gateNum);
			if (list == null) {
				list = new ArrayList<WorldArmy>();
				defInfoMap.put(gateNum, list);
			}
			if (str.length != 2) {
				continue;
			}
			String info = str[1];

			String[] worldArmysInfo = info.split("]");
			if (worldArmysInfo.length == 0) {
				continue;
			}
			for (String worldArmyInfo : worldArmysInfo) {
				String[] str1 = worldArmyInfo.split(":");
				int roleId = Integer.parseInt(str1[0]);

				String[] info2 = str1[1].split("\\[");
				String id = info2[0];
				WorldArmy worldArmy = new WorldArmy();
				worldArmy.setId(id);
				worldArmy.setRoleId(roleId);
				FormationData formationData = new FormationData();
				
				String[] info3 = info2[1].split(">");
				String[] info4 = info3[0].split(",");
				String name = info4[0];
				int clientID = Integer.parseInt(info4[1]);				
				int heroCaptainId = Integer.parseInt(info4[2]);
				worldArmy.setHeroCaptainId(heroCaptainId);
				worldArmy.setName(name);
				worldArmy.setClientId(clientID);
				String formationDataStr = info3[1];
				String[] dataStr = formationDataStr.split("!");
				for (String data1 : dataStr) {
					String[] heroInfo = data1.split(",");
					byte pos = Byte.parseByte(heroInfo[0]);
					int heroId = Integer.parseInt(heroInfo[1]);
					formationData.getData().put(pos, heroId);
				}

				worldArmy.setFormationData(formationData);
				list.add(worldArmy);
			}
		}
	}

	/**
	 * @return the attInfoMap
	 */
	public Map<Integer, List<WorldArmy>> getAttInfoMap() {
		return attInfoMap;
	}

	public synchronized void addDefInfoWorldArmy(int wallId, WorldArmy worldArmy) {
		List<WorldArmy> list = defInfoMap.get(wallId);
		if (list == null) {
			list = new ArrayList<>();
			defInfoMap.put(wallId, list);
		}
		list.add(worldArmy);
		worldArmy.setStatus(CityConstant.ARMY_DEF);
	}

	public synchronized void removeDefInfoWorldArmy(int wallId, WorldArmy worldArmy) {
		List<WorldArmy> list = defInfoMap.get(wallId);
		if (list == null) {
			return;
		}
		list.remove(worldArmy);
	}

	public synchronized void addAttInfoWorldArmy(int wallId, WorldArmy worldArmy) {
		List<WorldArmy> list = attInfoMap.get(wallId);
		if (list == null) {
			list = new ArrayList<>();
			attInfoMap.put(wallId, list);
		}
		list.add(worldArmy);
		worldArmy.setStatus(CityConstant.ARMY_ATK);
	}
	
	public synchronized void removeCoreInfo(int index)
	{
		this.coreDefInfoMap.remove(index);
	}
	
	public synchronized void removeCoreInfo(WorldArmy worldArmy){
		this.coreDefInfoMap.remove(worldArmy);
	}
	
	public synchronized void changeCoreInfo(int index1 ,int index2)
	{
		WorldArmy temp = this.getCoreDefInfoMap().get(index2);
		this.coreDefInfoMap.set(index2, this.getCoreDefInfoMap().get(index1));
		this.coreDefInfoMap.set(index1, temp);
	}
	
	public synchronized void addCoreInfo(WorldArmy army)
	{
		this.coreDefInfoMap.add(army);
		army.setStatus(CityConstant.ARMY_CORE);
	}

	public synchronized void removeAttInfoWorldArmy(int wallId, WorldArmy worldArmy) {
		List<WorldArmy> list = attInfoMap.get(wallId);
		if (list == null) {
			return;
		}
		list.remove(worldArmy);
	}
	
	public synchronized void addLazyInfo(WorldArmy worldArmy)
	{
		this.lazyInfoMap.put(worldArmy.getId(), worldArmy);
		worldArmy.setStatus(CityConstant.ARMY_LAZY);
	}
	
	public synchronized void removeLazyInfo(String id)
	{
		this.lazyInfoMap.remove(id);
	}

	public synchronized void addDefInfoWorldArmyToMinWorldArmyList(WorldArmy worldArmy) {
		 List<WorldArmy> minList = null;
		for (Entry<Integer, List<WorldArmy>> entry : defInfoMap.entrySet()) {
			List<WorldArmy> list = entry.getValue();
			if (minList == null) {
				minList = list;
			} else {
				int size = minList.size();
				if (list.size() < size) {
					minList = list;
				}
			}
		}
		minList.add(worldArmy);
		worldArmy.setStatus(CityConstant.ARMY_DEF);
	}

	public int getLastChange() {
		return lastChange;
	}

	public void setLastChange(int lastChange) {
		this.lastChange = lastChange;
	}

	/**
	 * @return the contributeMap
	 */
	public Map<Byte, Map<Integer, ContributeRank>> getContributeMap() {
		return contributeMap;
	}

	/**
	 * @param contributeMap the contributeMap to set
	 */
	public void setContributeMap(Map<Byte, Map<Integer, ContributeRank>> contributeMap) {
		this.contributeMap = contributeMap;
	}

	/**
	 * @return the contribute
	 */
	public String getContribute() {
		StringBuffer sb = new StringBuffer();
		for(Entry<Byte,Map<Integer,ContributeRank>> entry:contributeMap.entrySet()){
			byte nation = entry.getKey();
			Map<Integer,ContributeRank> map = entry.getValue();
			sb.append(nation).append("-");
			for(Entry<Integer,ContributeRank> e:map.entrySet()){
				int roleId = e.getKey();
				ContributeRank rank = e.getValue();
				int value = rank.getContribute();
				int enterTime = rank.getEnterTime();
				sb.append(roleId).append(",").append(value).append(",").append(enterTime).append(";");
			}
			sb.append("]");
		}
		this.contribute = sb.toString();
		return contribute;
	}

	/**
	 * @param contribute the contribute to set
	 */
	public void setContribute(String contribute) {
		if (contribute == null || contribute.equals("")) {
			return;
		}
		this.contribute = contribute;
		String[] str1 = contribute.split("]");
		for (String info : str1) {
			String[] str2 = info.split("-");
			byte nation = Byte.parseByte(str2[0]);
			if(str2.length == 1){
				continue;
			}
			String roleInfo = str2[1];
			String[] sets = roleInfo.split(";");
			for(String set :sets){
				String[] s = set.split(",");
				int roleId = Integer.parseInt(s[0]);
				int value = Integer.parseInt(s[1]);
				int enterTime = Integer.parseInt(s[2]);
				
				Map<Integer,ContributeRank> map = contributeMap.get(nation);
				if(map == null){
					map = new HashMap<>();
					contributeMap.put(nation,map);
				}

				ContributeRank rank = map.get(roleId);
				if (rank == null) {
					rank = new ContributeRank();
					rank.setRoleId(roleId);
					rank.setContribute(0);
					rank.setEnterTime(enterTime);
					map.put(roleId, rank);
				}

				rank.setContribute(rank.getContribute() + value);

			}
		}
	}

	/**
	 * @return the lazyInfoMap
	 */
	public Map<String,WorldArmy> getLazyInfoMap() {
		return lazyInfoMap;
	}

	/**
	 * @param lazyInfoMap the lazyInfoMap to set
	 */
	public void setLazyInfoMap(Map<String,WorldArmy> lazyInfoMap) {
		this.lazyInfoMap = lazyInfoMap;
	}

	/**
	 * @return the lazyInfo
	 */
	public String getLazyInfo() {
		StringBuffer sb = new StringBuffer();

		for (Entry<String, WorldArmy> entry : lazyInfoMap.entrySet()) {
			String id = entry.getKey();
			WorldArmy worldArmy = entry.getValue();
			int roleId = worldArmy.getRoleId();
			int heroCaptainId = worldArmy.getHeroCaptainId();
			String name = worldArmy.getName();
			int clientID = worldArmy.getClientId();
			FormationData formationData = worldArmy.getFormationData();
			Map<Byte, Integer> data = formationData.getData();
			
			sb.append(id).append(":").append(roleId).append("[").append(name).append(",").append(clientID).append(",")
					.append(heroCaptainId).append(">");
			for(Entry<Byte ,Integer> dataEntry:data.entrySet()){
				byte pos = dataEntry.getKey();
				int heroId = dataEntry.getValue();
				
				sb.append(pos).append(",").append(heroId).append(";");
			}
			sb.append("]");			
		}
		this.lazyInfo = sb.toString();
		return lazyInfo;
	}

	/**
	 * @param lazyInfo the lazyInfo to set
	 */
	public void setLazyInfo(String lazyInfo) {
		if (lazyInfo == null || lazyInfo.equals("")) {
			return;
		}
		this.lazyInfo = lazyInfo;
		String[] str1 = lazyInfo.split("]");
		for (String str2 : str1) {
			String[] str3 = str2.split("\\[");
			String[] idInfo = str3[0].split(":");
			String worldArmyId = idInfo[0];
			int roleId = Integer.parseInt(idInfo[1]);

			String str4 = str3[1];
			String[] str5 = str4.split(">");
			String[] str6 = str5[0].split(",");
			String name = str6[0];
			int clientID = Integer.parseInt(str6[1]);
			int heroCaptainId = Integer.parseInt(str6[2]);
			String herosInfos = str5[1];
			String[] heroInfos = herosInfos.split(";");
			
			WorldArmy worldArmy = new WorldArmy();
			worldArmy.setId(worldArmyId);
			worldArmy.setRoleId(roleId);
			worldArmy.setHeroCaptainId(heroCaptainId);
			worldArmy.setName(name);
			worldArmy.setClientId(clientID);
			FormationData formationData = new FormationData();
			Map<Byte, Integer> data = formationData.getData();
			for (String heroInfo : heroInfos) {
				String[] hero = heroInfo.split(",");
				byte pos = Byte.parseByte(hero[0]);
				int heroId = Integer.parseInt(hero[1]);

				data.put(pos, heroId);
			}
			worldArmy.setFormationData(formationData);
			lazyInfoMap.put(worldArmyId, worldArmy);
		}
	}

	/**
	 * @return the coreDefInfo
	 */
	public String getCoreDefInfo() {
		StringBuffer sb = new StringBuffer();
		for (WorldArmy worldArmy : coreDefInfoMap) {
			String id = worldArmy.getId();
			int roleId = worldArmy.getRoleId();
			int heroCaptainId = worldArmy.getHeroCaptainId();
			String name = worldArmy.getName();
			int clientId = worldArmy.getClientId();
			FormationData formationData = worldArmy.getFormationData();
			Map<Byte, Integer> data = formationData.getData();
			
			sb.append(id).append(":").append(roleId).append("[").append(name).append(",").append(clientId).append(",").append(heroCaptainId).append(">");
			for(Entry<Byte ,Integer> dataEntry:data.entrySet()){
				byte pos = dataEntry.getKey();
				int heroId = dataEntry.getValue();
				
				sb.append(pos).append(",").append(heroId).append(";");
			}
			sb.append("]");			
		}
		this.coreDefInfo = sb.toString();
		return coreDefInfo;
	}

	/**
	 * @param coreDefInfo the coreDefInfo to set
	 */
	public void setCoreDefInfo(String coreDefInfo) {
		if (coreDefInfo == null || coreDefInfo.equals("")) {
			return;
		}
		this.coreDefInfo = coreDefInfo;
		String[] str1 = coreDefInfo.split("]");
		for (String str2 : str1) {
			String[] str3 = str2.split("\\[");
			String[] idInfo = str3[0].split(":");
			String worldArmyId = idInfo[0];
			int roleId = Integer.parseInt(idInfo[1]);

			String str4 = str3[1];
			String[] str5 = str4.split(">");
			String[] str6 = str5[0].split(",");
			String name = str6[0];
			int clientId = Integer.parseInt(str6[1]);
			
			int heroCaptainId = Integer.parseInt(str6[2]);
			String herosInfos = str5[1];
			String[] heroInfos = herosInfos.split(";");
			WorldArmy worldArmy = new WorldArmy();
			worldArmy.setId(worldArmyId);
			worldArmy.setRoleId(roleId);
			worldArmy.setHeroCaptainId(heroCaptainId);
			worldArmy.setName(name);
			worldArmy.setClientId(clientId);
			FormationData formationData = new FormationData();
			Map<Byte, Integer> data = formationData.getData();
			for (String heroInfo : heroInfos) {
				String[] hero = heroInfo.split(",");
				byte pos = Byte.parseByte(hero[0]);
				int heroId = Integer.parseInt(hero[1]);

				data.put(pos, heroId);

			}
			worldArmy.setFormationData(formationData);
			coreDefInfoMap.add(worldArmy);
		}
	}

	/**
	 * @return the coreDefInfoMap
	 */
	public List<WorldArmy> getCoreDefInfoMap() {
		return coreDefInfoMap;
	}

	public CityOwGet getCityOwGet() {
		return cityOwGet;
	}

	public void setCityOwGet(CityOwGet cityOwGet) {
		this.cityOwGet = cityOwGet;
	}

	/**
	 * @return the atkStartTimeMap
	 */
	public Map<Integer,CityFire> getAtkStartTimeMap() {
		return atkStartTimeMap;
	}

	/**
	 * @param atkStartTimeMap the atkStartTimeMap to set
	 */
	public void setAtkStartTimeMap(Map<Integer,CityFire> atkStartTimeMap) {
		this.atkStartTimeMap = atkStartTimeMap;
	}
	
	public Map<Integer, Integer> getRoleLocation() {
		return roleLocation;
	}

	public void setRoleLocation(Map<Integer, Integer> roleLocation) {
		this.roleLocation = roleLocation;
	}

	public byte getFirstOccupyNation() {
		return firstOccupyNation;
	}

	public void setFirstOccupyNation(byte firstOccupyNation) {
		this.firstOccupyNation = firstOccupyNation;
	}
}
