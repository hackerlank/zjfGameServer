package byCodeGame.game.entity.file;

public class MainCityConfig {
	/** 等级 */
	private int level;
	/** 花费 */
	private int costCoins;
	/** CD */
	private int upgradeTime;
	/** 关卡限制 */
	private int chapterLimit;
	/** 声望产值 */
	private int prestigeYield;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCostCoins() {
		return costCoins;
	}

	public void setCostCoins(int costCoins) {
		this.costCoins = costCoins;
	}

	public int getUpgradeTime() {
		return upgradeTime;
	}

	public void setUpgradeTime(int upgradeTime) {
		this.upgradeTime = upgradeTime;
	}

	public int getChapterLimit() {
		return chapterLimit;
	}

	public void setChapterLimit(int chapterLimit) {
		this.chapterLimit = chapterLimit;
	}

	public int getPrestigeYield() {
		return prestigeYield;
	}

	public void setPrestigeYield(int prestigeYield) {
		this.prestigeYield = prestigeYield;
	}

}
