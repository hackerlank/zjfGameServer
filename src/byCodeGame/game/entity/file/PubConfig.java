package byCodeGame.game.entity.file;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wcy 2016年1月5日
 *
 */
public class PubConfig {
	private int lv;
	private String exploitHero;
	private int heroProb;
	private int debrisProb;
	private int propProb;
	
	private ArrayList<Integer> exploitHeroList = new ArrayList<>();

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public String getExploitHero() {
		StringBuffer sb = new StringBuffer();
		for (Integer heroId : exploitHeroList) {
			sb.append(heroId + ";");
		}
		this.exploitHero = sb.toString();
		return exploitHero;
	}

	public void setExploitHero(String exploitHero) {
		this.exploitHero = exploitHero;
		exploitHeroList.clear();
		String[] heros = exploitHero.split(";");
		for (String hero : heros) {
			int id = Integer.valueOf(hero);
			exploitHeroList.add(id);
		}
	}

	public ArrayList<Integer> getExploitHeroList() {
		return this.exploitHeroList;
	}

	public int getHeroProb() {
		return heroProb;
	}

	public void setHeroProb(int heroProb) {
		this.heroProb = heroProb;
	}

	/**
	 * @return the debrisProb
	 */
	public int getDebrisProb() {
		return debrisProb;
	}

	/**
	 * @param debrisProb the debrisProb to set
	 */
	public void setDebrisProb(int debrisProb) {
		this.debrisProb = debrisProb;
	}

	/**
	 * @return the propProb
	 */
	public int getPropProb() {
		return propProb;
	}

	/**
	 * @param propProb the propProb to set
	 */
	public void setPropProb(int propProb) {
		this.propProb = propProb;
	}

}
