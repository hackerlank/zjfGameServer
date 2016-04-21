package byCodeGame.game.entity.file;

/**
 * 装备配置表
 * @author xjd
 *
 */
public class EquipConfig {

	/** 装备配置表ID	 */
	private int id;
	/** 装备位置	 1武器  2头盔 3衣服 4披风 5马 6旗子 */
	private int slot;
	/** 品质	 1白色   2绿色  3蓝色  4紫色  5橙色*/
	private int quality;
	/** 攻击力	 */
	private int atk;
	/** 攻击力强化值	 */
	private int atkStrongValue;
	/** 防御力	 */
	private int def;
	/** 防御力强化值	 */
	private int defStrongValue;
	/** 战法攻击力	 */
	private int zatk;
	/** 战法攻击力强化值	 */
	private int zatkStrongValue;
	/** 战法防御力	 */
	private int zdef;
	/** 战法强化值	 */
	private int zdefStrongValue;
	/** 计策攻击力	 */
	private int catk;
	/** 计策攻击力强化值	 */
	private int catkStrongValue;
	/** 计策防御力	 */
	private int cdef;
	/** 计策强化值	 */
	private int cdefStrongValue;
	/** 血量	 */
	private int hp;
	/** 血量强化值	 */
	private int hpStrongValue;
	/**获得声望值*/
	private int prestigeAward;

	/** 卖出价格	 */
	private int sellPrice;
	/** 买入价格	 */
	private int buyPrice;
	/** 套装ID	 */
	private int series;
	/** 穿戴需求等级	 */
	private int needLv;
	/**能否卖出*/
	private int canSale;
	/**名字*/
	private String name;
	/**简介*/
	private String info;
	/** 炼化返回		*/
	private int returnStone;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSlot() {
		return slot;
	}
	public void setSlot(int slot) {
		this.slot = slot;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getAtkStrongValue() {
		return atkStrongValue;
	}
	public void setAtkStrongValue(int atkStrongValue) {
		this.atkStrongValue = atkStrongValue;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getDefStrongValue() {
		return defStrongValue;
	}
	public void setDefStrongValue(int defStrongValue) {
		this.defStrongValue = defStrongValue;
	}
	public int getZatk() {
		return zatk;
	}
	public void setZatk(int zatk) {
		this.zatk = zatk;
	}
	public int getZatkStrongValue() {
		return zatkStrongValue;
	}
	public void setZatkStrongValue(int zatkStrongValue) {
		this.zatkStrongValue = zatkStrongValue;
	}
	public int getZdef() {
		return zdef;
	}
	public void setZdef(int zdef) {
		this.zdef = zdef;
	}
	public int getZdefStrongValue() {
		return zdefStrongValue;
	}
	public void setZdefStrongValue(int zdefStrongValue) {
		this.zdefStrongValue = zdefStrongValue;
	}
	public int getCatk() {
		return catk;
	}
	public void setCatk(int catk) {
		this.catk = catk;
	}
	public int getCatkStrongValue() {
		return catkStrongValue;
	}
	public void setCatkStrongValue(int catkStrongValue) {
		this.catkStrongValue = catkStrongValue;
	}
	public int getCdef() {
		return cdef;
	}
	public void setCdef(int cdef) {
		this.cdef = cdef;
	}
	public int getCdefStrongValue() {
		return cdefStrongValue;
	}
	public void setCdefStrongValue(int cdefStrongValue) {
		this.cdefStrongValue = cdefStrongValue;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getHpStrongValue() {
		return hpStrongValue;
	}
	public void setHpStrongValue(int hpStrongValue) {
		this.hpStrongValue = hpStrongValue;
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
	public int getSeries() {
		return series;
	}
	public void setSeries(int series) {
		this.series = series;
	}
	public int getCanSale() {
		return canSale;
	}
	public void setCanSale(int canSale) {
		this.canSale = canSale;
	}
	public int getNeedLv() {
		return needLv;
	}
	public void setNeedLv(int needLv) {
		this.needLv = needLv;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	@Override
	public String toString() {
		return  id  + "," + slot + ","
				+ quality + "," + atk  + ","
				+ atkStrongValue + "," + def + ","
				+ defStrongValue + "," + zatk + ","
				+ zatkStrongValue + "," + zdef + ","
				+ zdefStrongValue + "," + catk + ","
				+ catkStrongValue + "," + cdef + ","
				+ cdefStrongValue + "," + hp + ","
				+ hpStrongValue + ","  
				+ "," + sellPrice + "," + buyPrice
				+ "," + series + "," + needLv + ","
				+ canSale + "," + name + "," + info;
	}
	public int getReturnStone() {
		return returnStone;
	}
	public void setReturnStone(int returnStone) {
		this.returnStone = returnStone;
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
