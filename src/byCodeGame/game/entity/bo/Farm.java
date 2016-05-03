package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.bo.base.RoleComponent;
import byCodeGame.game.entity.po.FarmSpace;

/**
 * 农场
 * 
 * @author wcy 2016年4月29日
 *
 */
public class Farm extends RoleComponent {
	private String farmSpaceStr;

	private Map<Byte, FarmSpace> farmSpaceMap = new HashMap<>();

	public void setFarmSpaceStr(String farmSpaceStr) {
		if (farmSpaceStr == null || farmSpaceStr.equals("")) {
			return;
		}
		String[] farmSpaceArray = this.farmSpaceStr.split(",");
		int i = 0;
		while (i < farmSpaceArray.length) {
			byte position = Byte.parseByte(farmSpaceArray[i++]);

			FarmSpace farmSpace = new FarmSpace();
			farmSpace.setPosition(position);

			farmSpaceMap.put(position, farmSpace);
		}
		this.farmSpaceStr = farmSpaceStr;
	}

	public String getFarmSpaceStr() {
		StringBuilder sb = new StringBuilder();
		for (FarmSpace farmSpace : farmSpaceMap.values()) {
			sb.append(farmSpace.getPosition()).append(",");
		}
		this.farmSpaceStr = sb.toString();
		return farmSpaceStr;
	}

	public Map<Byte, FarmSpace> getFarmSpaceMap() {
		return farmSpaceMap;
	}
}
