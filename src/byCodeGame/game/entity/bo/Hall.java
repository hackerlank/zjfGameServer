package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import byCodeGame.game.entity.bo.base.RoleComponent;
import byCodeGame.game.entity.po.HallPhotoSpace;
import byCodeGame.game.entity.po.HallSpace;

/**
 * 
 * @author wcy 2016年5月5日
 *
 */
public class Hall extends RoleComponent {
	/** 客厅位置字符串 */
	private String hallSpaceStr;
	/** 客厅位置表 */
	private Map<Byte, HallSpace> hallSpaceMap = new HashMap<>();

	private String hallPhotoSpaceStr;

	private Map<Byte, HallPhotoSpace> hallPhotoSpaceMap = new HashMap<>();

	public void setHallSpaceStr(String hallSpaceStr) {
		if (hallSpaceStr == null || hallSpaceStr.equals("")) {
			return;
		}

		this.hallSpaceStr = hallSpaceStr;
		hallSpaceMap.clear();
		String[] hallSpaceArray = hallSpaceStr.split(",");

		int i = 0;
		while (i < hallSpaceArray.length) {
			byte position = Byte.parseByte(hallSpaceArray[i++]);
			int propId = Byte.parseByte(hallSpaceArray[i++]);

			HallSpace hallSpace = new HallSpace();
			hallSpace.setPosition(position);
			hallSpace.setPropId(propId);

			this.hallSpaceMap.put(position, hallSpace);
		}
	}

	public String getHallSpaceStr() {
		StringBuilder sb = new StringBuilder();
		for (Entry<Byte, HallSpace> entry : hallSpaceMap.entrySet()) {
			byte position = entry.getKey();
			HallSpace hallSpace = entry.getValue();

			sb.append(position).append(",");
			sb.append(hallSpace.getPropId()).append(",");
		}
		hallSpaceStr = sb.toString();
		return hallSpaceStr;
	}

	public Map<Byte, HallSpace> getHallSpaceMap() {
		return hallSpaceMap;
	}

	public String getHallPhotoSpaceStr() {
		StringBuilder sb = new StringBuilder();

		for (HallPhotoSpace hallPhotoSpace : this.getHallPhotoSpaceMap().values()) {
			byte position = hallPhotoSpace.getPosition();
			int photoId = hallPhotoSpace.getPhotoId();
			sb.append(position).append(",");
			sb.append(photoId).append(",");
		}
		this.hallPhotoSpaceStr = sb.toString();
		return hallPhotoSpaceStr;
	}

	public void setHallPhotoSpaceStr(String hallPhotoSpaceStr) {
		if(hallPhotoSpaceStr == null||hallPhotoSpaceStr.equals("")){
			return;
		}
		this.hallPhotoSpaceStr = hallPhotoSpaceStr;
		String[] hallPhotoSpaceArray = hallPhotoSpaceStr.split(",");
		
		this.hallPhotoSpaceMap.clear();
		
		int i = 0;
		while(i<hallPhotoSpaceArray.length){
			byte position = Byte.parseByte(hallPhotoSpaceArray[i++]);
			int photoId = Integer.parseInt(hallPhotoSpaceArray[i++]);
			
			HallPhotoSpace space = new HallPhotoSpace();
			space.setPosition(position);
			space.setPhotoId(photoId);
			
			this.hallPhotoSpaceMap.put(position, space);
		}
	}

	public Map<Byte, HallPhotoSpace> getHallPhotoSpaceMap() {
		return hallPhotoSpaceMap;
	}

}
