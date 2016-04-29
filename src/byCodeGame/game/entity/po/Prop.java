package byCodeGame.game.entity.po;

import byCodeGame.game.entity.bo.base.RoleComponent;

/**
 * 道具
 * 
 * @author wcy 2016年4月29日
 *
 */
public class Prop extends RoleComponent {
	// 数据库id
	private int id;
	// 配置表id
	private int configId;
	// 质量
	private int quality;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public int getConfigId() {
		return configId;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getQuality() {
		return quality;
	}

}
