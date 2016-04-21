package byCodeGame.game.entity.file;

import java.util.HashMap;
import java.util.Map;


/**
 * 关卡配置表
 * @author xjd
 */
public class ChapterConfig {

	private int id;
	/** 怪物等级 */
	private int lv;
	/** 关卡类型(1普通  2精英  3BOOS)	 */
	private int type;
	/** 所属战役ID	 */
	private int battleId;
	/** 每日可攻打次数	 */
	private int dayTime;
	/** 奖励硬币		 */
	private int coins;
	/** 奖励主公经验	 */
	private int exp;
	/** 奖励英雄经验	 */
	private int heroExp;
	/** 奖励战功		 */
	private int exploit;
	/**
	 *  随机奖励	(随机概率,道具id,道具类型,数量;.....) </br>
	 *  道具类型(1经验  2银币  3粮草  4战功  5道具  6装备)
	 **/
	private String randomReward;
	
	/** 首次掉落		 */
	private String firstDrop;
	
	/** 碎片掉落		 */
	private String debrisAward;
	
	/** 扫荡奖励		 */
	private String raidAward;
	/**
	 *  关卡武将配置(id,id,...)
	 *  对应ChapterArmsConfig类的id
	 *  troops 转换关卡武将配置key  位置  value heroId
	 */
	private String troops;
	/** 武将配置数组	 */
	
	/** 完成这一关卡后解锁的关卡	 */
	private int nextChapterId;
	/** 解锁的武将	 */
	private int unLockHero;
	/** 解锁方式	1通关获得 2酒馆招募	 */
	private int unLockHeroType;
//	/** 好感度加成					 */
//	private int favour;
	/** 评价星数时候采用的策略		 */
	private int starStrategy;
	/** 标题  代替玩家名 */
	private String title;
	/** 使用阵型Id */
	private int formationID;
//	/** 使用阵型等级*/
//	private int formationLevel;
	private int imageid;
	private int advisablePower;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDayTime() {
		return dayTime;
	}
	public void setDayTime(int dayTime) {
		this.dayTime = dayTime;
	}
//	public void setReward(String reward) {
//		if(reward != null && !reward.equals("")){
//			String[] strs = reward.split(";");
//			for(String str : strs){
//				String[] vStr = str.split(",");
//				if (Byte.valueOf(vStr[0]) == (byte)10) {
//					EquipConfig config = EquipConfigCache.getEquipConfigById(Integer.parseInt(vStr[1]));
//					if(config == null) continue;
//				}
//				ChapterReward chapterReward = new ChapterReward();
//				chapterReward.setType(Byte.valueOf(vStr[0]));
//				chapterReward.setItemId(Integer.parseInt(vStr[1]));
//				chapterReward.setNum(Integer.parseInt(vStr[2]));
//				this.getRewardSet().add(chapterReward);
//			}
//		}
//	}
//	public Set<ChapterReward> getRewardSet() {
//		return rewardSet;
//	}
	public int getNextChapterId() {
		return nextChapterId;
	}
	public void setNextChapterId(int nextChapterId) {
		this.nextChapterId = nextChapterId;
	}
	public int getBattleId() {
		return battleId;
	}
	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}
	public Map<Integer, String> getTroops() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		String str []=troops.split(",");
		int i=0;
		for (String arr : str) {
			i++;
			data.put(i, arr);
		}
		return data;
	}
	
	public String getTroopStr()
	{
		return this.troops;
	}
	public void setTroops(String troops) {
			this.troops=troops;
	}
	public String getRandomReward() {
		return randomReward;
	}
	public void setRandomReward(String randomReward) {
		this.randomReward = randomReward;
	}
	public int getUnLockHero() {
		return unLockHero;
	}
	public void setUnLockHero(int unLockHero) {
		this.unLockHero = unLockHero;
	}
	public int getUnLockHeroType() {
		return unLockHeroType;
	}
	public void setUnLockHeroType(int unLockHeroType) {
		this.unLockHeroType = unLockHeroType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getFormationID() {
		return formationID;
	}
	public void setFormationID(int formationID) {
		this.formationID = formationID;
	}
//	public int getFormationLevel() {
//		return formationLevel;
//	}
//	public void setFormationLevel(int formationLevel) {
//		this.formationLevel = formationLevel;
//	}
	public int getStarStrategy() {
		return starStrategy;
	}
	public void setStarStrategy(int starStrategy) {
		this.starStrategy = starStrategy;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getHeroExp() {
		return heroExp;
	}
	public void setHeroExp(int heroExp) {
		this.heroExp = heroExp;
	}
//	public int getFavour() {
//		return favour;
//	}
//	public void setFavour(int favour) {
//		this.favour = favour;
//	}
	public int getExploit() {
		return exploit;
	}
	public void setExploit(int exploit) {
		this.exploit = exploit;
	}
	public int getImageid() {
		return imageid;
	}
	public void setImageid(int imageid) {
		this.imageid = imageid;
	}
	public String getFirstDrop() {
		return firstDrop;
	}
	public void setFirstDrop(String firstDrop) {
		this.firstDrop = firstDrop;
	}
	public String getDebrisAward() {
		return debrisAward;
	}
	public void setDebrisAward(String debrisAward) {
		this.debrisAward = debrisAward;
	}
	public int getAdvisablePower() {
		return advisablePower;
	}
	public void setAdvisablePower(int advisablePower) {
		this.advisablePower = advisablePower;
	}
	public String getRaidAward() {
		return raidAward;
	}
	public void setRaidAward(String raidAward) {
		this.raidAward = raidAward;
	}
	
}
