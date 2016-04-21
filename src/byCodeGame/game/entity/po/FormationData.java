package byCodeGame.game.entity.po;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 玩家阵型数据
 * @author 王君辉
 *
 */
public class FormationData{
	
	/** 配置 key:位置ID  value:武将ID	 */
	private Map<Byte, Integer> data = new HashMap<Byte, Integer>();
	
	public FormationData(){
		getData().put((byte)1, 0);
		getData().put((byte)2, 0);
		getData().put((byte)3, 0);
		getData().put((byte)4, 0);
		getData().put((byte)5, 0);
		getData().put((byte)6, 0);
		getData().put((byte)7, 0);
		getData().put((byte)8, 0);
		getData().put((byte)9, 0);
	}
	
	public FormationData(String str){
		String[] strs = str.split(",");
		getData().put((byte)1, Integer.parseInt(strs[0]));
		getData().put((byte)2, Integer.parseInt(strs[1]));
		getData().put((byte)3, Integer.parseInt(strs[2]));
		getData().put((byte)4, Integer.parseInt(strs[3]));
		getData().put((byte)5, Integer.parseInt(strs[4]));
		getData().put((byte)6, Integer.parseInt(strs[5]));
		getData().put((byte)7, Integer.parseInt(strs[6]));
		getData().put((byte)8, Integer.parseInt(strs[7]));
		getData().put((byte)9, Integer.parseInt(strs[8]));
	}

	public Map<Byte, Integer> getData() {
		return data;
	}

	public void setData(Map<Byte, Integer> data) {
		this.data = data;
	}
	
	@Override
	public FormationData clone() {		
		FormationData clone = new FormationData();
		HashMap<Byte,Integer> cloneData= new HashMap<Byte,Integer>();
		
		for(Entry<Byte,Integer> entry:this.data.entrySet()){
			byte pos = entry.getKey();
			int heroId = entry.getValue();
			cloneData.put(pos, heroId);
		}
		
		clone.setData(cloneData);
		
		return clone;
	}
	
	public void clearData()
	{
		getData().put((byte)1, 0);
		getData().put((byte)2, 0);
		getData().put((byte)3, 0);
		getData().put((byte)4, 0);
		getData().put((byte)5, 0);
		getData().put((byte)6, 0);
		getData().put((byte)7, 0);
		getData().put((byte)8, 0);
		getData().put((byte)9, 0);
	}
	
	public void putHero(byte point , int heroId)
	{
		this.data.put(point, heroId);
	}
}
