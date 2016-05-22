package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.bo.base.RoleComponent;
import byCodeGame.game.entity.po.WorkInfo;

public class Business extends RoleComponent{
	//工作信息
	private String workStr;
	//工作列表
	private Map<String, WorkInfo> workInfoMap = new HashMap<>();

	public void setWorkStr(String workStr) {
		this.workStr = workStr;
	}

	public String getWorkStr() {
		return workStr;
	}

	public Map<String, WorkInfo> getWorkInfoMap() {
		return workInfoMap;
	}
}
