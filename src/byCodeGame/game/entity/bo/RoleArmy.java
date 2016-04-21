package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.po.ArmyData;

public class RoleArmy {
	private int roleId;
	/** 天赋Str		*/
	private String roleArmyStr;
	/** 步兵天赋Map	*/
	private Map<Integer,ArmyData> bbMap = new HashMap<Integer, ArmyData>();
	/** 骑兵天赋Map	*/
	private Map<Integer, ArmyData> qqMap = new HashMap<Integer, ArmyData>();
	/** 弓兵天赋Map	*/
	private Map<Integer, ArmyData> gbMap = new HashMap<Integer , ArmyData>();
	/** 法师天赋Map	*/
	private Map<Integer, ArmyData> fsMap = new HashMap<Integer, ArmyData>();
	/** 枪兵天赋Map	*/
	private Map<Integer, ArmyData> qbMap = new HashMap<Integer, ArmyData>();
	/** 已经使用的天赋点*/
	private int usedPoint;
	
	private boolean change;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleArmyStr() {
		StringBuilder sb = new StringBuilder();
		for( ArmyData x : bbMap.values())
		{
			sb.append(x.getId()).append(",").append(x.getType()).append(",").append(x.getRank())
				.append(",").append(x.getLv()).append(",").append(x.getPoint()).append(";");
		}
		for( ArmyData x : qqMap.values())
		{
			sb.append(x.getId()).append(",").append(x.getType()).append(",").append(x.getRank())
				.append(",").append(x.getLv()).append(",").append(x.getPoint()).append(";");
		}
		for( ArmyData x : gbMap.values())
		{
			sb.append(x.getId()).append(",").append(x.getType()).append(",").append(x.getRank())
				.append(",").append(x.getLv()).append(",").append(x.getPoint()).append(";");
		}
		for( ArmyData x : fsMap.values())
		{
			sb.append(x.getId()).append(",").append(x.getType()).append(",").append(x.getRank())
				.append(",").append(x.getLv()).append(",").append(x.getPoint()).append(";");
		}
		for( ArmyData x : qbMap.values())
		{
			sb.append(x.getId()).append(",").append(x.getType()).append(",").append(x.getRank())
				.append(",").append(x.getLv()).append(",").append(x.getPoint()).append(";");
		}
		this.roleArmyStr = sb.toString();
		return roleArmyStr;
	}
	public void setRoleArmyStr(String roleArmyStr) {
		if(roleArmyStr != null && !roleArmyStr.equals("")){
			String[] strArmy = roleArmyStr.split(";");
			for(String x : strArmy)
			{
				String temp[] = x.split(",");
				ArmyData armyData = new ArmyData();
				armyData.setId(Integer.parseInt(temp[0]));
				armyData.setType(Integer.parseInt(temp[1]));
				armyData.setRank(Integer.parseInt(temp[2]));
				armyData.setLv(Integer.parseInt(temp[3]));
				armyData.setPoint(Integer.parseInt(temp[4]));
				switch (armyData.getType()) {
				case 1:
					this.bbMap.put(armyData.getId(), armyData);
					break;
				case 2:
					this.qqMap.put(armyData.getId(), armyData);
					break;
				case 3:
					this.gbMap.put(armyData.getId(), armyData);
					break;
				case 4:
					this.fsMap.put(armyData.getId(), armyData);
					break;
				case 5:
					this.qbMap.put(armyData.getId(), armyData);
					break;
				default:
					break;
				}
			}
		}
		this.roleArmyStr = roleArmyStr;
	}
	public Map<Integer, ArmyData> getBbMap() {
		return bbMap;
	}
	public void setBbMap(Map<Integer, ArmyData> bbMap) {
		this.bbMap = bbMap;
	}
	public Map<Integer, ArmyData> getQqMap() {
		return qqMap;
	}
	public void setQqMap(Map<Integer, ArmyData> qqMap) {
		this.qqMap = qqMap;
	}
	public Map<Integer, ArmyData> getGbMap() {
		return gbMap;
	}
	public void setGbMap(Map<Integer, ArmyData> gbMap) {
		this.gbMap = gbMap;
	}
	public Map<Integer, ArmyData> getFsMap() {
		return fsMap;
	}
	public void setFsMap(Map<Integer, ArmyData> fsMap) {
		this.fsMap = fsMap;
	}
	public Map<Integer, ArmyData> getQbMap() {
		return qbMap;
	}
	public void setQbMap(Map<Integer, ArmyData> qbMap) {
		this.qbMap = qbMap;
	}
	
	public boolean isChange() {
		return change;
	}
	public void setChange(boolean change) {
		this.change = change;
	}
	public int getUsedPoint() {
		return usedPoint;
	}
	public void setUsedPoint(int usedPoint) {
		this.usedPoint = usedPoint;
	}

	public ArmyData getArmyData(int type , int id)
	{
		switch (type) {
		case 1:
			return this.bbMap.get(id);
		case 2:
			return this.qqMap.get(id);	
		case 3:
			return this.gbMap.get(id);
		case 4:
			return this.fsMap.get(id);	
		case 5:
			return this.qbMap.get(id);	
		default:
			return null;
		}
	}
	
	public void putArmyData(ArmyData armyData)
	{
		switch (armyData.getType()) {
		case 1:
			this.bbMap.put(armyData.getId(), armyData);
			break;
		case 2:
			this.qqMap.put(armyData.getId(), armyData);
			break;
		case 3:
			this.gbMap.put(armyData.getId(), armyData);
			break;
		case 4:
			this.fsMap.put(armyData.getId(), armyData);
			break;
		case 5:
			this.qbMap.put(armyData.getId(), armyData);	
			break;
		default:
			break;
		}
	}
	
	public void clearRoleArmy()
	{
		this.usedPoint = 0;
		this.bbMap.clear();
		this.qqMap.clear();
		this.gbMap.clear();
		this.fsMap.clear();
		this.qbMap.clear();
	}
	
	public Map<Integer, ArmyData> getMapById(int type)
	{
		if(type == 1)
		{
			return bbMap;
		}else if (type == 2) {
			return qqMap;
		}else if (type == 3) {
			return gbMap;
		}else if (type == 4) {
			return fsMap;
		}else if (type == 5) {
			return qbMap;
		}
		
		return null;
	}
	
	public int getOneGiftPoint(int type)
	{
		int temp = 0;
		Map<Integer,ArmyData> map = null;
		switch (type) {
		case 1:
			map = this.bbMap;
			break;
		case 2:
			map = this.qqMap;
			break;
		case 3:
			map = this.gbMap;
			break;
		case 4:
			map = this.fsMap;
			break;
		case 5:
			map = this.qbMap;	
			break;
		default:
			break;
		}
		for(ArmyData x : map.values())
		{
			temp += x.getPoint();
		}
		return temp;
	}
}
