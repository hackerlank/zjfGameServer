package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import byCodeGame.game.entity.bo.base.RoleComponent;
import byCodeGame.game.entity.po.KitchenSpace;

/**
 * 厨房
 * 
 * @author wcy 2016年4月28日
 *
 */
public class Kitchen extends RoleComponent {
	/** 灶台表 */
	private Map<Byte, KitchenSpace> kitchenSpaceMap = new HashMap<>();
	/** 灶台字符串 */
	private String kitchenSpaceStr;

	public Map<Byte, KitchenSpace> getKitchenSpaceMap() {
		return kitchenSpaceMap;
	}

	public void setKitchenSpaceStr(String kitchenSpaceStr) {
		if (kitchenSpaceStr == null || kitchenSpaceStr.equals("")) {
			return;
		}
		this.kitchenSpaceStr = kitchenSpaceStr;
		this.kitchenSpaceMap.clear();

		String[] kitchenSpaceArray = kitchenSpaceStr.split(",");
		int i = 0;
		while (i < kitchenSpaceArray.length) {
			byte position = Byte.parseByte(kitchenSpaceArray[i++]);
			int electricForgeId = Integer.parseInt(kitchenSpaceArray[i++]);
			int panId = Integer.parseInt(kitchenSpaceArray[i++]);

			KitchenSpace kitchenSpace = new KitchenSpace();
			kitchenSpace.setPosition(position);
			kitchenSpace.setElectricForgeId(electricForgeId);
			kitchenSpace.setPanId(panId);

			kitchenSpaceMap.put(position, kitchenSpace);
		}

	}

	public String getKitchenSpaceStr() {
		StringBuilder sb = new StringBuilder();
		for (KitchenSpace kitchenSpace : kitchenSpaceMap.values()) {
			sb.append(kitchenSpace.getPosition()).append(",");
			sb.append(kitchenSpace.getElectricForgeId()).append(",");
			sb.append(kitchenSpace.getPanId()).append(",");
		}
		this.kitchenSpaceStr = sb.toString();
		return kitchenSpaceStr;
	}
}
