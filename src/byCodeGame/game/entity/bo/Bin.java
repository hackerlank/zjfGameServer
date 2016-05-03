package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.bo.base.RoleComponent;
import byCodeGame.game.entity.po.BinSpace;

public class Bin extends RoleComponent {
	/** 转换机字符串 */
	private String binSpaceStr;

	/** 转换机表 */
	private Map<Byte, BinSpace> binSpaceMap = new HashMap<>();

	public void setBinSpaceStr(String binSpaceStr) {
		if (binSpaceStr == null || binSpaceStr.equals("")) {
			return;
		}

		this.binSpaceStr = binSpaceStr;
		String[] binSpaceArray = binSpaceStr.split(",");

		int i = 0;
		while (i < binSpaceArray.length) {
			byte position = Byte.parseByte(binSpaceArray[i++]);
			int convertMachineId = Integer.parseInt(binSpaceArray[i++]);

			BinSpace binSpace = new BinSpace();
			binSpace.setConvertMachineId(convertMachineId);
			binSpace.setPosition(position);

			binSpaceMap.put(position, binSpace);
		}

	}

	public String getBinSpaceStr() {
		StringBuilder sb = new StringBuilder();
		for (BinSpace binSpace : binSpaceMap.values()) {
			sb.append(binSpace.getPosition()).append(",");
			sb.append(binSpace.getConvertMachineId()).append(",");
		}
		this.binSpaceStr = sb.toString();
		return binSpaceStr;
	}

	public Map<Byte, BinSpace> getBinSpaceMap() {
		return binSpaceMap;
	}

}
