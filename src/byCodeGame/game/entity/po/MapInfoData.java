package byCodeGame.game.entity.po;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.functors.MapTransformer;

import byCodeGame.game.entity.file.MapInfo;

/***
 * 寻访随机结果时使用
 * @author xjd
 *
 */
public class MapInfoData {
	private int id;
	/** 差的集合 Key：配置表ID(英雄ID 物品ID) Value:权重	*/
	private Map<Integer, MapInfoItemData> soso = new HashMap<Integer, MapInfoItemData>();
	/** 好的集合 Key：配置表ID(英雄ID 物品ID) Value:权重	*/
	private Map<Integer, MapInfoItemData> good = new HashMap<Integer, MapInfoItemData>();
	
	
	public MapInfoData() {
		// TODO Auto-generated constructor stub
	}
	
	public MapInfoData(MapInfo mapInfo) {
		this.id = mapInfo.getId();
		StringBuilder sbSoso = new StringBuilder();
		sbSoso.append(mapInfo.getHeroSetSoso()).append(";").append(mapInfo.getItemSetSoso())
			.append(";").append(mapInfo.getEquipSetSoso()).append(";");
		StringBuilder sbGood = new StringBuilder();
		sbGood.append(mapInfo.getHeroSetGood()).append(";").append(mapInfo.getItemSetGood())
			.append(";").append(mapInfo.getEquipSetGood());
		
		for(String strs : sbSoso.toString().split(";"))
		{
			String[] temp = strs.split(",");
			this.soso.put(Integer.parseInt(temp[0]), new MapInfoItemData(temp));
		}
		
		for(String strs : sbGood.toString().split(";"))
		{
			String[] temp = strs.split(",");
			this.good.put(Integer.parseInt(temp[0]), new MapInfoItemData(temp));
		}
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<Integer, MapInfoItemData> getSoso() {
		return soso;
	}
	public void setSoso(Map<Integer, MapInfoItemData> soso) {
		this.soso = soso;
	}
	public Map<Integer, MapInfoItemData> getGood() {
		return good;
	}
	public void setGood(Map<Integer, MapInfoItemData> good) {
		this.good = good;
	}
}
