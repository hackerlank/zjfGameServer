package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;

/**
 * 玩家
 * 
 * @author wcy 2016年4月22日
 *
 */
public class Role {
	// 玩家id
	private int id;
	// 玩家名
	private String name;
	// 玩家帐号
	private String account;
	// 宠物映射表
	private Map<Integer, Hero> heroMap = new HashMap<>();
	// serverId为key的道具表
	private Map<Integer, Prop> serverIdPropMap = new HashMap<>();
	// configId为key的道具表
	private Map<Integer, Prop> configIdPropMap = new HashMap<>();
	// 家信息
	private Home home;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount() {
		return account;
	}

	public Map<Integer, Hero> getHeroMap() {
		return heroMap;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	public Home getHome() {
		return home;
	}

	public Map<Integer, Prop> getConfigIdPropMap() {
		return configIdPropMap;
	}

	public Map<Integer, Prop> getServerIdPropMap() {
		return serverIdPropMap;
	}

}
