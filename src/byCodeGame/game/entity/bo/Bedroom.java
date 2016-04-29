package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import byCodeGame.game.entity.bo.base.RoleComponent;
import byCodeGame.game.entity.po.BedSpace;

/**
 * 卧室
 * 
 * @author wcy 2016年4月29日
 *
 */
public class Bedroom extends RoleComponent {
	// 床信息
	private String bedSpaceStr;
	// 床的信息表
	private Map<Byte, BedSpace> bedSpaceMap = new HashMap<>();

	public Map<Byte, BedSpace> getBedSpaceMap() {
		return bedSpaceMap;
	}

	public void setBedSpaceStr(String bedSpaceStr) {
		if (bedSpaceStr == null || bedSpaceStr.equals("")) {
			return;
		}
		this.bedSpaceStr = bedSpaceStr;
		bedSpaceMap.clear();

		String[] originData = bedSpaceStr.split(",");
		// 获取床的数量
		int index = 0;
		int size = Integer.parseInt(originData[index]);
		index++;

		for (int i = 0; i < size; i++) {
			byte bedIndex = Byte.parseByte(originData[index++]);
			int bed = Integer.parseInt(originData[index++]);
			int beddingBag = Integer.parseInt(originData[index++]);
			int bedSheet = Integer.parseInt(originData[index++]);
			int pillow1 = Integer.parseInt(originData[index++]);
			int pillow2 = Integer.parseInt(originData[index++]);

			BedSpace bedSpace = new BedSpace();
			bedSpace.setBed(bed);
			bedSpace.setBeddingBag(beddingBag);
			bedSpace.setBedSheet(bedSheet);
			bedSpace.setPillow1(pillow1);
			bedSpace.setPillow2(pillow2);
			
			this.bedSpaceMap.put(bedIndex, bedSpace);
		}
	}

	public String getBedSpaceStr() {
		StringBuilder sb = new StringBuilder();
		int size = bedSpaceMap.size();
		sb.append(size).append(",");

		for (Entry<Byte, BedSpace> entrySet : bedSpaceMap.entrySet()) {
			byte index = entrySet.getKey();
			BedSpace bed = entrySet.getValue();

			sb.append(index).append(",");
			sb.append(bed.getBed()).append(",");
			sb.append(bed.getBeddingBag()).append(",");
			sb.append(bed.getBedSheet()).append(",");
			sb.append(bed.getPillow1()).append(",");
			sb.append(bed.getPillow2()).append(",");
		}
		this.bedSpaceStr = sb.toString();
		return bedSpaceStr;
	}
}
