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
		
		int i = 0;
		while(i<originData.length){
			byte position = Byte.parseByte(originData[i++]);
			int bed = Integer.parseInt(originData[i++]);
			int beddingBag = Integer.parseInt(originData[i++]);
			int bedSheet = Integer.parseInt(originData[i++]);
			int pillow1 = Integer.parseInt(originData[i++]);
			int pillow2 = Integer.parseInt(originData[i++]);

			BedSpace bedSpace = new BedSpace();
			bedSpace.setPosition(position);
			bedSpace.setBedId(bed);
			bedSpace.setBeddingBagId(beddingBag);
			bedSpace.setBedSheetId(bedSheet);
			bedSpace.setPillow1Id(pillow1);
			bedSpace.setPillow2Id(pillow2);

			this.bedSpaceMap.put(position, bedSpace);
		}
	}

	public String getBedSpaceStr() {
		StringBuilder sb = new StringBuilder();
		for (BedSpace bed : bedSpaceMap.values()) {
			sb.append(bed.getPosition()).append(",");
			sb.append(bed.getBedId()).append(",");
			sb.append(bed.getBeddingBagId()).append(",");
			sb.append(bed.getBedSheetId()).append(",");
			sb.append(bed.getPillow1Id()).append(",");
			sb.append(bed.getPillow2Id()).append(",");
		}
		this.bedSpaceStr = sb.toString();
		return bedSpaceStr;
	}
}
