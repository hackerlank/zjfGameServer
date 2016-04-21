package byCodeGame.game.entity.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.po.Package;

public class Item {

	private int id;
	/** 消耗品类型 1礼包  2银币  3军令  4元宝  5经验  6战功	7粮食  8里程  9扫荡令*/
	private int type;
	/** 礼包所包含的道具、道具类型及道具数量 (id,类型,数量;id,类型,数量;) type为1时使用	 */
	private String rewardStr;
	/** 礼包map	 */
	private Map<Integer,Package> rewardMap = new HashMap<Integer, Package>();
	/** 加值数量 (type非1时使用)	 */
	private int value;
	/** 是否可以卖出 0不可卖出 1可以卖出	 */
	private int canSell;
	/** 卖出价格	 */
	private int sellPrice;
	/** 买入价格	 */
	private int buyPrice;
	/** 使用所需等级	 */
	private int needLv;
	/** 是否可用		 */
	private int canUse;
	/**质量*/
	private int quality;
	/**道具名称*/
	private String name;
	/** 获得声望值 */
	private int prestigeAward;
	
	
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getCanSell() {
		return canSell;
	}
	public void setCanSell(int canSell) {
		this.canSell = canSell;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}
	public int getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	public int getNeedLv() {
		return needLv;
	}
	public void setNeedLv(int needLv) {
		this.needLv = needLv;
	}
	public String getRewardStr() {
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<Integer, Package> entry : this.getRewardMap().entrySet()){
			sb.append(entry.getKey()).append(",").append(entry.getValue()).append(";");
		}
		this.rewardStr = sb.toString();
		return rewardStr;
	}
	public void setRewardStr(String rewardStr) {
		if(rewardStr !=null && !rewardStr.equals("")){
			String[] strs = rewardStr.split(";");
			for(String itemStr : strs){
				String[] itemArr = itemStr.split(",");
				Package tempPackage = new Package();
				tempPackage.setId(Integer.valueOf(itemArr[0]));
				tempPackage.setFunctionType(Byte.parseByte(itemArr[1]));
				tempPackage.setValue(Integer.valueOf(itemArr[2]));
				this.getRewardMap().put(tempPackage.getId(), tempPackage);
			}
		}
	}
	public Map<Integer,Package> getRewardMap() {
		return rewardMap;
	}
	public void setRewardMap(Map<Integer,Package> rewardMap) {
		this.rewardMap = rewardMap;
	}
	public int getCanUse() {
		return canUse;
	}
	public void setCanUse(int canUse) {
		this.canUse = canUse;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * @return the prestigeAward
	 */
	public int getPrestigeAward() {
		return prestigeAward;
	}
	/**
	 * @param prestigeAward the prestigeAward to set
	 */
	public void setPrestigeAward(int prestigeAward) {
		this.prestigeAward = prestigeAward;
	}
	
}
