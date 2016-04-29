package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.bo.base.RoleComponent;
import byCodeGame.game.entity.po.HearthSpace;

/**
 * 厨房
 * 
 * @author wcy 2016年4月28日
 *
 */
public class Kitchen extends RoleComponent {
	private Map<Byte, HearthSpace> hearthSpace = new HashMap<>();

	private String hearthSpaceStr;

	public Map<Byte, HearthSpace> getHearthSpace() {
		return hearthSpace;
	}

	public void setHearthSpaceStr(String hearthSpaceStr) {
		this.hearthSpaceStr = hearthSpaceStr;
	}

	public String getHearthSpaceStr() {
		return hearthSpaceStr;
	}
}
