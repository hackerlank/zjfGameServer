package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.bo.base.RoleComponent;
import byCodeGame.game.entity.po.PubSpace;

/**
 * 
 * @author wcy 2016年4月29日
 *
 */
public class Pub extends RoleComponent {
	private String pubSpaceStr;
	private Map<Byte, PubSpace> pubSpaceMap = new HashMap<>();

	public void setPubSpaceStr(String pubSpaceStr) {
		if(pubSpaceStr == null||pubSpaceStr.equals("")){
			return;
		}
		
		int i = 0;
		String[] pubSpaceArray = pubSpaceStr.split(",");
		while(i<pubSpaceArray.length){
			byte position = Byte.parseByte(pubSpaceArray[i++]);
			int pubBucketId = Integer.parseInt(pubSpaceArray[i++]);
			int pubForgeId = Integer.parseInt(pubSpaceArray[i++]);
			
			PubSpace pubSpace = new PubSpace();
			pubSpace.setPosition(position);
			pubSpace.setPubBucketId(pubBucketId);
			pubSpace.setPubForgeId(pubForgeId);
			
			this.pubSpaceMap.put(position, pubSpace);
		}
		this.pubSpaceStr = pubSpaceStr;
	}

	public String getPubSpaceStr() {
		StringBuilder sb = new StringBuilder();
		for (PubSpace pubSpace : pubSpaceMap.values()) {
			sb.append(pubSpace.getPosition()).append(",");
			sb.append(pubSpace.getPubBucketId()).append(",");
			sb.append(pubSpace.getPubForgeId()).append(",");
		}
		this.pubSpaceStr = sb.toString();
		return pubSpaceStr;
	}

	public Map<Byte, PubSpace> getPubSpaceMap() {
		return pubSpaceMap;
	}
}
