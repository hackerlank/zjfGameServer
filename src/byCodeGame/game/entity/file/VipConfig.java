package byCodeGame.game.entity.file;

public class VipConfig {
	/** vip等级 */
	private int vipLv;
	/** 每日行程疲劳值 */
	private int maxMile;
	/** 当前可拥有的训练位 */
	private int trainNum;
	/** 当前等级可使用的训练模式 */
	private int trainMode;
	/** 当前等级可使用的训练时间 */
	private int trainTime;
	/** 当前等级基础经验值 */
	private int baseExp;
	/** 下一等级所需经验值 */
	private int nextExp;
	/** 刷新关卡次数限制 */
	private int refreshChapterTimes;
	/** 每日可以购买军令的数量 */
	private int armyTokenLimit;
	/** 银币征收次数 */
	private int levyCoinTimes;
	/** 银币免费征次数 */
	private int freeLevyCoinTimes;
	/** 粮草征收次数 */
	private int levyFarmTimes;
	/** 粮草免费征收次数 */
	private int freeLevyFarmTimes;
	/** 兵营征讨次数 */
	private int barracksAtkTimes;
	/** 每日献策次数 */
	private int scienceEruptTimes;
	/** Vip礼包信息 */
	private String award;
	/** 通天塔次数		*/
	private int towerTimes;

	public int getVipLv() {
		return vipLv;
	}
	public void setVipLv(int vipLv) {
		this.vipLv = vipLv;
	}
	public int getMaxMile() {
		return maxMile;
	}
	public void setMaxMile(int maxMail) {
		this.maxMile = maxMail;
	}
	public int getTrainNum() {
		return trainNum;
	}
	public void setTrainNum(int trainNum) {
		this.trainNum = trainNum;
	}
	public int getTrainMode() {
		return trainMode;
	}
	public void setTrainMode(int trainMode) {
		this.trainMode = trainMode;
	}
	public int getTrainTime() {
		return trainTime;
	}
	public void setTrainTime(int trainTime) {
		this.trainTime = trainTime;
	}
	public int getBaseExp() {
		return baseExp;
	}
	public void setBaseExp(int baseExp) {
		this.baseExp = baseExp;
	}
	public int getNextExp() {
		return nextExp;
	}
	public void setNextExp(int nextExp) {
		this.nextExp = nextExp;
	}
	public int getRefreshChapterTimes() {
		return refreshChapterTimes;
	}
	public void setRefreshChapterTimes(int refreshChapterTimes) {
		this.refreshChapterTimes = refreshChapterTimes;
	}
	public int getArmyTokenLimit() {
		return armyTokenLimit;
	}
	public void setArmyTokenLimit(int armyTokenLimit) {
		this.armyTokenLimit = armyTokenLimit;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public int getLevyCoinTimes() {
		return levyCoinTimes;
	}
	public void setLevyCoinTimes(int levyCoinTimes) {
		this.levyCoinTimes = levyCoinTimes;
	}
	public int getLevyFarmTimes() {
		return levyFarmTimes;
	}
	public void setLevyFarmTimes(int levyFarmTimes) {
		this.levyFarmTimes = levyFarmTimes;
	}
	public int getBarracksAtkTimes() {
		return barracksAtkTimes;
	}
	public void setBarracksAtkTimes(int barracksAtkTimes) {
		this.barracksAtkTimes = barracksAtkTimes;
	}
	public int getScienceEruptTimes() {
		return scienceEruptTimes;
	}
	public void setScienceEruptTimes(int scienceEruptTimes) {
		this.scienceEruptTimes = scienceEruptTimes;
	}
	public int getFreeLevyCoinTimes() {
		return freeLevyCoinTimes;
	}
	public void setFreeLevyCoinTimes(int freeLevyCoinTimes) {
		this.freeLevyCoinTimes = freeLevyCoinTimes;
	}
	public int getFreeLevyFarmTimes() {
		return freeLevyFarmTimes;
	}
	public void setFreeLevyFarmTimes(int freeLevyFarmTimes) {
		this.freeLevyFarmTimes = freeLevyFarmTimes;
	}
	public int getTowerTimes() {
		return towerTimes;
	}
	public void setTowerTimes(int towerTimes) {
		this.towerTimes = towerTimes;
	}	
}
