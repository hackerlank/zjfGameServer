package byCodeGame.game.util.test;

import java.util.HashMap;
import java.util.Map;

public class FightTestData {
	private String name;
	private int formationId;
	private int formationLv;
	/** 位置,英雄ID,等级,转生等级,小兵ID;*/
//	private String heroStr;
	private Map<Integer, String> heroMap = new HashMap<Integer, String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFormationId() {
		return formationId;
	}
	public void setFormationId(int formationId) {
		this.formationId = formationId;
	}
	public int getFormationLv() {
		return formationLv;
	}
	public void setFormationLv(int formationLv) {
		this.formationLv = formationLv;
	}
	public Map<Integer, String> getHeroMap() {
		return heroMap;
	}
	public void setHeroMap(Map<Integer, String> heroMap) {
		this.heroMap = heroMap;
	}
}
