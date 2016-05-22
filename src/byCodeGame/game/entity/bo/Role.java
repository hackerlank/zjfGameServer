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
	// 钱
	private long money;
	// 钻石
	private long diamond;
	// 宠物映射表<英雄数据库id,英雄>
	private Map<Integer, Hero> heroMap = new HashMap<>();
	// serverId为key的道具表
	private Map<Integer, Prop> serverIdPropMap = new HashMap<>();
	// configId为key的道具表
	private Map<Integer, Prop> configIdPropMap = new HashMap<>();
	// 厨房信息
	private Kitchen kitchen;
	// 经营信息
	private Business business;

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

	public Map<Integer, Prop> getConfigIdPropMap() {
		return configIdPropMap;
	}

	public Map<Integer, Prop> getServerIdPropMap() {
		return serverIdPropMap;
	}

	public void setKitchen(Kitchen kitchen) {
		this.kitchen = kitchen;
	}

	public Kitchen getKitchen() {
		return kitchen;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Business getBusiness() {
		return business;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public long getMoney() {
		return money;
	}

	public void setDiamond(long diamond) {
		this.diamond = diamond;
	}

	public long getDiamond() {
		return diamond;
	}
}
