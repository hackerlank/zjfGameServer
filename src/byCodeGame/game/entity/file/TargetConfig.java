package byCodeGame.game.entity.file;

import java.util.HashMap;
import java.util.Map;

public class TargetConfig {
	private int id;
	private int nation;
	private String type;
	private Map<Byte, Integer> typeMap = new HashMap<Byte, Integer>();
	private String award;
	private int nextId;
	private int stage;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public void setNation(int nation) {
		this.nation = nation;
	}
	public int getNation() {
		return nation;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public int getNextId() {
		return nextId;
	}
	public void setNextId(int nextId) {
		this.nextId = nextId;
	}
	public int getStage(){
		return stage;
	}	
	public void setStage(int stage){
		this.stage = stage;
	}
	public Map<Byte, Integer> getTypeMap() {
		return typeMap;
	}
	public void setTypeMap(Map<Byte, Integer> typeMap) {
		this.typeMap = typeMap;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		String[] str = type.split(";");
		for(String temp : str)
		{
			String[] x = temp.split(",");
			this.typeMap.put(Byte.parseByte(x[0]), Integer.parseInt(x[1]));
		}
		this.type = type;
	}
	
	public String getTypeUse()
	{
		StringBuilder sb = new StringBuilder();
		for(Byte x : this.typeMap.keySet())
		{
			sb.append(x).append(",").append(0).append(";");
		}
		return sb.toString();
	}
}
