package byCodeGame.game.entity.bo;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import byCodeGame.game.entity.po.ActivityData;

/**
 * 
 * @author wcy 2016年4月18日
 *
 */
public class Activity {
	// 数据库id
	private int id;
	// 活动id
	private int activityId;
	// 活动状态
	private byte status;
	// 活动进度表<玩家或者公会id,进度>
	private Map<Integer, ActivityData> dataMap = new ConcurrentHashMap<>();
	// 活动数据
	private String dataStr;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getActivityId() {
		return activityId;
	}

	/**
	 * 设置活动状态
	 * 
	 * @param status
	 * @author wcy 2016年4月19日
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * 活动状态
	 * 
	 * @return
	 * @author wcy 2016年4月19日
	 */
	public byte getStatus() {
		return status;
	}

	public Map<Integer, ActivityData> getDataMap() {
		return dataMap;
	}

	public void setDataStr(String dataStr) {
		if (dataStr == null || dataStr.equals("")) {
			return;
		}
		this.dataStr = dataStr;
		String[] oneInformation = dataStr.split("}");
		for (String str : oneInformation) {
			String[] idAndData = str.split("\\{");
			Integer id = Integer.parseInt(idAndData[0]);
			ActivityData progress = new ActivityData(idAndData[1]);
			dataMap.put(id, progress);
		}
	}

	public String getDataStr() {
		StringBuilder sb = new StringBuilder();
		for (Entry<Integer, ActivityData> entry : dataMap.entrySet()) {
			int id = entry.getKey();
			ActivityData p = entry.getValue();
			sb.append(id).append("{").append(p).append("}");
		}
		this.dataStr = sb.toString();
		return dataStr;
	}

}
