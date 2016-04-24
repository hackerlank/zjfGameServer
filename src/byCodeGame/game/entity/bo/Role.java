package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.bo.base.GameEntity;

/**
 * 玩家
 * 
 * @author wcy 2016年4月22日
 *
 */
public class Role extends GameEntity {
	// 玩家名
	private String name;
	// 玩家帐号
	private String account;
	// 宠物映射表
	private Map<Integer, Hero> heroMap = new HashMap<>();
	// 喜欢的英雄id
	private int loveHeroId;

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

	public void setLoveHeroId(int loveHeroId) {
		this.loveHeroId = loveHeroId;
	}

	public int getLoveHeroId() {
		return loveHeroId;
	}

}
