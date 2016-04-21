package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.po.HeroSoldier;
import byCodeGame.game.util.HeroUtils;

public class Hero {

	/** 所属玩家ID */
	private int roleId;
	/** 英雄ID (配置表ID) */
	private int heroId;
	/** 英雄名字 */
	private String heroName;
	/** 等级 */
	private short lv;
	/** 可带兵品阶 */
	private int rank;
	/** 经验 */
	private int exp;
	/** 进阶等级 */
	private short rebirthLv;

	/** 兵力配置 (兵种ID,兵种数量;兵种ID,兵种数量;...) */
	private String troopsConfig;
	// /** 兵力配置MAP */
	// private Map<Integer, HerosTroops> troopMap = new HashMap<Integer,
	// HerosTroops>();
	/** 装备列表 key:装备位置 value:道具id(数据库ID) */
	private Map<Byte, Integer> equipMap = new HashMap<Byte, Integer>();
	/** 武将状态 0酒馆 1队列中 */
	private byte status;
	/** 战斗时候的uuid（仅对应 控制器 和 fightObj） */
	private String uuid;

	/** 兵种ID */
	private int armsId;
	/** 配置数量(非实际数量) */
	private int armsNum;
	/** 兵种信息 */
	private String armyInfo;
	/** 士兵情况表 */
	private HashMap<Integer, HeroSoldier> armyInfoMap = new HashMap<Integer, HeroSoldier>();
	/** 正在使用的兵种id */
	private int useArmyId;
	/** 武将好感度 **/
	private int emotion;
	/** 已经解锁的最大兵种品阶 **/
	private int maxArmyQuality;
	/** 主动技能 */
	private int skillId;
	/** 技能等级 */
	private int skillLv;
	/** 被动技能 */
	private int skillBD;
	/** 被动等级 */
	private int skillBDLv;
	/** 英雄好感度的等级 */
	private int emotionLv;
	/** 战力值 */
	private int fightValue;
	/** 所属建筑 */
	private byte bulidId;
	/** 替换的兵种数据 */
	private String insteadArmy;
	/** 使用的兵种数据 */
	private String usingArmy;
	/** 替换兵种集 			*/
	private HashSet<Integer> insteadArmySet = new HashSet<Integer>();
	/** 使用兵种集 			*/
	private HashSet<Integer> usingArmySet = new HashSet<Integer>();
	/** 职业等级 			*/
	private int dutyLv;
	/** 当前体力			*/
	private int manual;
	/** 最后一次获得体力时间	*/
	private int lastGetMa;
	/** 当前英雄的位置		*/
	private int location;
	/** 世界疲劳度			*/
	private int tired;
	/** 世界血量			*/
	private int worldHp;

	/** 面板属性 */
	private double generalAttrck;
	private double generalDefence;
	private double powerAttack;
	private double powerDefence;
	private double magicalAttack;
	private double magicalDefence;
	private int addHurt;
	private int rmHurt;
	private int bingji;

	/************************************************************************************/
	public int getArmsId() {
		return armsId;
	}

	public void setArmsId(int armsId) {
		this.armsId = armsId;
	}

	public int getArmsNum() {
		return armsNum;
	}

	public void setArmsNum(int armsNum) {
		this.armsNum = armsNum;
	}

	private boolean change;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public String getHeroName() {
		return heroName;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public String getTroopsConfig() {
		StringBuilder sb = new StringBuilder();
		sb.append(armsId).append(",").append(armsNum).append(";");
		this.troopsConfig = sb.toString();
		return troopsConfig;
	}

	public void setTroopsConfig(String troopsConfig) {
		if (troopsConfig != null && !troopsConfig.equals("")) {
			String[] strs = troopsConfig.split(";");
			for (String tempStr : strs) {
				String[] tempConfig = tempStr.split(",");
				armsId = Integer.valueOf(tempConfig[0]);
				armsNum = Integer.valueOf(tempConfig[1]);
			}
		}
	}

	// public String getTroopsConfig() {
	// StringBuilder sb = new StringBuilder();
	// for(Map.Entry<Integer, HerosTroops> entry : troopMap.entrySet()){
	// HerosTroops tempHerosTroops = entry.getValue();
	// sb.append(tempHerosTroops.getArmsId()).append(",").append(tempHerosTroops.getNum()).append(";");
	// }
	// this.troopsConfig = sb.toString();
	// return troopsConfig;
	// }
	// public void setTroopsConfig(String troopsConfig) {
	// if(troopsConfig != null && !troopsConfig.equals("")){
	// String[] strs = troopsConfig.split(";");
	// for(String tempStr : strs){
	// String[] tempConfig = tempStr.split(",");
	// HerosTroops tempHerosTroops = new HerosTroops();
	// tempHerosTroops.setArmsId(Integer.parseInt(tempConfig[0]));
	// tempHerosTroops.setNum(Integer.parseInt(tempConfig[1]));
	// getTroopMap().put(tempHerosTroops.getArmsId(), tempHerosTroops);
	// }
	// }
	// }
	// public Map<Integer, HerosTroops> getTroopMap() {
	// return troopMap;
	// }
	// public void setTroopMap(Map<Integer, HerosTroops> troopMap) {
	// this.troopMap = troopMap;
	// }
	/**
	 * 指定兵种ID增加数量
	 * 
	 * @param id
	 */
	// public void addArmsNum(int id,int num){
	// if(!troopMap.containsKey(id)){
	// HerosTroops herosTroops = new HerosTroops();
	// herosTroops.setArmsId(id);
	// herosTroops.setNum(num);
	// troopMap.put(id, herosTroops);
	// }else{
	// HerosTroops herosTroops = troopMap.get(id);
	// herosTroops.setNum(num);
	// }
	// }
	public short getLv() {
		return lv;
	}

	public void setLv(short lv) {
		this.lv = lv;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public short getRebirthLv() {
		return rebirthLv;
	}

	public void setRebirthLv(short rebirthLv) {
		this.rebirthLv = rebirthLv;
	}

	public Map<Byte, Integer> getEquipMap() {
		return equipMap;
	}

	public void setEquipMap(Map<Byte, Integer> equipMap) {
		this.equipMap = equipMap;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getRank() {
		return rank;
	}

	/**
	 * 星级
	 * 
	 * @param rank
	 * @author wcy
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * 获得兵种信息映射表HashMap<兵种id,兵种信息>
	 * 
	 * @return
	 */
	public HashMap<Integer, HeroSoldier> getArmyInfoMap() {
		return armyInfoMap;
	}

	/**
	 * 获得兵种信息字符串
	 * 
	 * @return
	 */
	public String getArmyInfo() {
		StringBuffer bf = new StringBuffer();
		for (Map.Entry<Integer, HeroSoldier> entry : armyInfoMap.entrySet()) {
			HeroSoldier heroSoldier = entry.getValue();
			bf.append(heroSoldier.getSoldierId() + "," + heroSoldier.getExp() + "," + heroSoldier.getSkillLv() + ","
					+ heroSoldier.getStatus() + "," + heroSoldier.getLv() + ";");

		}
		armyInfo = bf.toString();
		return armyInfo;
	}

	/**
	 * 根据字符串解析成兵种信息的映射表
	 * 
	 * @param armyInfo 信息字符串
	 */
	public void setArmyInfo(String armyInfo) {
		if (armyInfo != null && !armyInfo.equals("")) {
			String[] soldiers = armyInfo.split(";");
			for (String soldier : soldiers) {
				String[] property = soldier.split(",");
				int soldierId = Integer.valueOf(property[0]);
				int exp = Integer.valueOf(property[1]);
				int skillScale = Integer.valueOf(property[2]);
				byte lock = Byte.valueOf(property[3]);
				int lv = property.length == 5 ? Integer.valueOf(property[4]) : 1;

				HeroSoldier heroSoldier = new HeroSoldier(soldierId, exp, skillScale, lock, lv);
				armyInfoMap.put(soldierId, heroSoldier);
			}

		}
	}

	/**
	 * 加入军队信息
	 * 
	 * @param soldier
	 * @author wcy
	 */
	public void addArmyInfo(HeroSoldier soldier) {
		armyInfoMap.put(soldier.getSoldierId(), soldier);
	}

	/**
	 * 使用兵种的id
	 * 
	 * @return
	 */
	public int getUseArmyId() {
		return useArmyId;
	}

	/**
	 * 设置兵种的id
	 * 
	 * @param useArmyId
	 */
	public void setUseArmyId(int useArmyId) {
		this.useArmyId = useArmyId;
	}

	/**
	 * 获得武将好感度
	 * 
	 * @return
	 * @author wcy
	 */
	public int getEmotion() {
		return emotion;
	}

	/**
	 * 设置武将好感度
	 * 
	 * @param emotion
	 * @author wcy
	 */
	public void setEmotion(int emotion) {
		this.emotion = emotion;
	}

	public int getMaxArmyQuality() {
		return maxArmyQuality;
	}

	public void setMaxArmyQuality(int maxArmyQuality) {
		this.maxArmyQuality = maxArmyQuality;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getSkillLv() {
		return skillLv;
	}

	public void setSkillLv(int skillLv) {
		if (skillLv > this.skillLv)
			this.skillLv = skillLv;
	}

	public int getSkillBD() {
		return skillBD;
	}

	public void setSkillBD(int skillBD) {
		this.skillBD = skillBD;
	}

	public int getSkillBDLv() {
		return skillBDLv;
	}

	public void setSkillBDLv(int skillBDLv) {
		if (skillBDLv > this.skillBDLv)
			this.skillBDLv = skillBDLv;
	}

	public void setArmyInfoMap(HashMap<Integer, HeroSoldier> armyInfoMap) {
		this.armyInfoMap = armyInfoMap;
	}

	public int getEmotionLv() {
		return emotionLv;
	}

	public void setEmotionLv(int emotionLv) {
		this.emotionLv = emotionLv;
	}

	public int getFightValue() {
		HeroConfig config = HeroUtils.getCaptain(this);
		//===========================================================
		// double d1 = this.getArmsNum() * 0.05;
		// double atkValue = this.getGeneralAttrck() *0.42 +
		// this.getMagicalAttack() * 0.34 + this.getPowerAttack() * 0.34;
		// double defValue = this.getGeneralDefence() *0.70 +
		// this.getPowerDefence() *0.58 + this.getMagicalDefence() *0.58;
		// double d2 = (config.getPower() + config.getCaptain() +
		// config.getIntel()) * 8.54;
		// double d3 = (this.getAddHurt()+this.rmHurt+this.bingji)*0.5;
		// HeroConfig heroConfig =
		// HeroConfigCache.getHeroConfigById(this.getHeroId());
		// int finalNum = (int) ((d1 + atkValue + defValue + d2 + d3) *
		// HeroUtils.COEFFICIENT[heroConfig.getQuality()]);
		//===========================================================

		int finalNum = HeroUtils.getHeroFightValue(getArmsNum(), getGeneralAttrck(), getPowerAttack(), getMagicalAttack(),
				getGeneralDefence(), getPowerDefence(), getMagicalDefence(), config.getCaptain(), config.getPower(),
				config.getIntel());

		this.setFightValue(finalNum);
		return finalNum;
	}

	public void setFightValue(int fightValue) {
		this.fightValue = fightValue;
	}

	public double getGeneralAttrck() {
		return generalAttrck;
	}

	public void setGeneralAttrck(double generalAttrck) {
		this.generalAttrck = generalAttrck;
	}

	public double getGeneralDefence() {
		return generalDefence;
	}

	public void setGeneralDefence(double generalDefence) {
		this.generalDefence = generalDefence;
	}

	public double getPowerAttack() {
		return powerAttack;
	}

	public void setPowerAttack(double powerAttack) {
		this.powerAttack = powerAttack;
	}

	public double getPowerDefence() {
		return powerDefence;
	}

	public void setPowerDefence(double powerDefence) {
		this.powerDefence = powerDefence;
	}

	public double getMagicalAttack() {
		return magicalAttack;
	}

	public void setMagicalAttack(double magicalAttack) {
		this.magicalAttack = magicalAttack;
	}

	public double getMagicalDefence() {
		return magicalDefence;
	}

	public void setMagicalDefence(double magicalDefence) {
		this.magicalDefence = magicalDefence;
	}

	public int getAddHurt() {
		return addHurt;
	}

	public void setAddHurt(int addHurt) {
		this.addHurt = addHurt;
	}

	public int getRmHurt() {
		return rmHurt;
	}

	public void setRmHurt(int rmHurt) {
		this.rmHurt = rmHurt;
	}

	public int getBingji() {
		return bingji;
	}

	public void setBingji(int bingji) {
		this.bingji = bingji;
	}

	public byte getBulidId() {
		return bulidId;
	}

	public void setBulidId(byte bulidId) {
		this.bulidId = bulidId;
	}

	/**
	 * @return the insteadArmy
	 */
	public String getInsteadArmy() {
		StringBuffer sb = new StringBuffer();
		for (Integer heroId : insteadArmySet) {
			sb.append(heroId + ",");
		}
		this.insteadArmy = sb.toString();
		return insteadArmy;
	}

	/**
	 * @param insteadArmy the insteadArmy to set
	 */
	public void setInsteadArmy(String insteadArmy) {
		if (insteadArmy == null || insteadArmy.equals("")) {
			return;
		}
		this.insteadArmy = insteadArmy;

		String[] s = insteadArmy.split(",");
		for (String s1 : s) {
			int heroId = Integer.valueOf(s1);
			insteadArmySet.add(heroId);
		}
	}

	/**
	 * @return the usingArmy
	 */
	public String getUsingArmy() {
		StringBuffer sb = new StringBuffer();
		for (Integer heroId : usingArmySet) {
			sb.append(heroId + ",");
		}
		this.usingArmy = sb.toString();
		return usingArmy;
	}

	/**
	 * @param usingArmy the usingArmy to set
	 */
	public void setUsingArmy(String usingArmy) {
		if (usingArmy == null || usingArmy.equals("")) {
			return;
		}
		this.usingArmy = usingArmy;

		String[] s = usingArmy.split(",");
		for (String s1 : s) {
			int heroId = Integer.valueOf(s1);
			usingArmySet.add(heroId);
		}
	}

	/**
	 * @return the insteadArmySet
	 */
	public HashSet<Integer> getInsteadArmySet() {
		return insteadArmySet;
	}

	/**
	 * @return the usingArmySet
	 */
	public HashSet<Integer> getUsingArmySet() {
		return usingArmySet;
	}

	/**
	 * @return the dutyLv
	 */
	public int getDutyLv() {
		return dutyLv;
	}

	/**
	 * @param dutyLv the dutyLv to set
	 */
	public void setDutyLv(int dutyLv) {
		this.dutyLv = dutyLv;
	}

	public int getManual() {
		return manual;
	}

	public void setManual(int manual) {
		this.manual = manual;
	}

	public int getLastGetMa() {
		return lastGetMa;
	}

	public void setLastGetMa(int lastGetMa) {
		this.lastGetMa = lastGetMa;
	}

	public void addManual(int value)
	{
		this.manual += value;
		if(manual > HeroConfigCache.getHeroConfigById(heroId).getMaxMa())
		{
			this.manual =  HeroConfigCache.getHeroConfigById(heroId).getMaxMa();
		}else if (manual < 0) {
			this.manual = 0;
		}
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getTired() {
		return tired;
	}

	public void setTired(int tired) {
		this.tired = tired;
	}

	public int getWorldHp() {
		return worldHp;
	}

	public void setWorldHp(int worldHp) {
		this.worldHp = worldHp;
	}
}
