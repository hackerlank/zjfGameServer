package byCodeGame.game.entity.po;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import byCodeGame.game.entity.file.TargetConfig;

public class TargetBean {
	private int id;
	private String nowStuts;
	private Map<Byte, Integer> nowStutsMap = new HashMap<Byte, Integer>();
	private byte isComplete;
	
	
	public TargetBean() {
		
	}
	
	public TargetBean(String[] value)
	{
		this.id = Integer.parseInt(value[0]);
		this.setNowStuts(value[1]);
		this.isComplete = Byte.parseByte(value[2]);
	}
	
	public TargetBean(TargetConfig config)
	{
		this.id = config.getId();
		this.setNowStuts(config.getTypeUse());
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(byte isComplete) {
		this.isComplete = isComplete;
	}
	public Map<Byte, Integer> getNowStutsMap() {
		return nowStutsMap;
	}
	public void setNowStutsMap(Map<Byte, Integer> nowStutsMap) {
		this.nowStutsMap = nowStutsMap;
	}
	
	
	public String getNowStuts() {
		StringBuilder sb = new StringBuilder();
		for(Entry<Byte, Integer> x:this.nowStutsMap.entrySet())
		{
			sb.append(x.getKey()).append(",").append(x.getValue()).append(";");
		}
		return sb.toString();
	}
	public void setNowStuts(String nowStuts) {
		String[] strs = nowStuts.split(";");
		for(String str : strs)
		{
			String[] temp = str.split(",");
			this.nowStutsMap.put(Byte.parseByte(temp[0]), Integer.parseInt(temp[1]));
		}
		this.nowStuts = nowStuts;
	}
	
	
}
