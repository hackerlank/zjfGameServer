package byCodeGame.game.entity.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import byCodeGame.game.entity.po.BuildQueue;
//import byCodeGame.game.entity.po.FightSnapData;
import byCodeGame.game.entity.po.FormationData;
import byCodeGame.game.entity.po.History;
import byCodeGame.game.entity.po.RoleScienceQueue;
import byCodeGame.game.entity.po.Tower;
import byCodeGame.game.entity.po.WorldArmy;
import byCodeGame.game.entity.po.WorldDoubleP;
import byCodeGame.game.moudle.city.CityConstant;
import byCodeGame.game.util.handler.GoldChangHandler;

public class Role {
	/** 角色id */
	private int id;
	/** 角色名 */
	private String name;
	/** 账号 */
	private String account;
	/** 等级 */
	private short lv;
	/** 经验 */
	private int exp;
	/** 游戏币 */
	private int money;
	/** 金币 */
	private int gold;
	/** 充值的金币数量总和(vip经验) */
	private int sumGold;
	/** 声望 */
	private int prestige;
	/** 粮食 */
	private int food;
	/** 人口 */
	private int population;
	/** 人口上限 */
	private int populationLimit;
	/** 战功 */
	private int exploit;
	/** VIP等级 */
	private byte vipLv;
	/** 已经领取的Vip礼包 */
	private String alreadyGetVipAward;
	/** 已经领取的Vip礼包集合 */
	private Set<Integer> alreadyGetVipAwardSet = new HashSet<Integer>();
	/** 军令 */
	private int armyToken;
	/** 每日购买军令的数量 */
	private int areadyBuyArmyToken;
	/** 玩家建筑数据 */
	private Build build;
	/** 用户任务数据 */
	private Tasks tasks;
	/** 国籍 */
	private byte nation;
	/** 最大背包数量 */
	private short maxBagNum;
	/** 玩家道具列表 key:道具数据库ID */
	private Map<Integer, Prop> propMap = new ConcurrentHashMap<Integer, Prop>();
	/** 玩家消耗品列表 key:配置表的道具id */
	private Map<Integer, Prop> consumePropMap = new ConcurrentHashMap<Integer, Prop>();
	/** 玩家道具列表 背包 */
	// private Map<Integer, Prop> backPack = new ConcurrentHashMap<Integer,
	// Prop>();

	/**
	 * 被动消耗品的id,<消耗品配置表id,道具>
	 */
	private Map<Integer, Prop> passivePropMap = new ConcurrentHashMap<Integer, Prop>();

	/** 最后世界频道 发言时间 */
	private long time_1;
	/** 最后私聊 发言时间 */
	private long time_2;
	/** 最后其它 发言时间 */
	private long time_3;

	/** 军团ID */
	private int legionId;
	/** 申请中的工会ID(军团ID,军团ID) */
	private String applyLegion;
	/** 申请中的工会ID集合 */
	private Set<Integer> applyLegionSet = new HashSet<Integer>();
	/** 军团贡献 */
	private int legionContribution;
	/** 账户下所有邮件 */
	private List<Mail> roleMail = new ArrayList<Mail>();

	/** 最后下线时间 */
	private long lastOffLineTime;
	/** 最后退出公会时间 */
	private long lastQuiteLegion;

	/** 所有会谈过的武将列表 */
	private Map<Integer, Hero> heroMap = new HashMap<Integer, Hero>();
	// /** 可用的武将列表 */
	// private Map<Integer, Hero> recruitHeroMap = new HashMap<Integer, Hero>();
	/** 使用的阵型ID */
	private Integer useFormationID;
	/** 阵型配置数据 (位置ID,武将ID;位置ID,武将ID;...) */
	private String formationStr;
	/** 阵型配置数据类 Integer 阵型id FormationData阵型信息 */
	// private FormationData formationData = new FormationData();
	private Map<Integer, FormationData> formationData = new HashMap<Integer, FormationData>();
	/** 世界阵型模块		*/
	private String worldFormationStr;
	private Map<Integer, FormationData> worldFormation = new HashMap<Integer, FormationData>();
	
	/** 科技点数 */
	private int armsResearchNum;
	/** 兵种id,兵种科技等级;兵种id,兵种科技等级;兵种id,兵种科技等级;... */
	private String armsResearchStr;
	/** key兵种id,value兵种科技等级 */
	private Map<Integer, Integer> armsResearchData = new HashMap<Integer, Integer>();

	/** 玩家的市场数据 */
	private Market market;
	/** 玩家的酒馆数据 */
	private Pub pub;
	/** 玩家每天可升级公会科技的类型 初始为0每天重置 */
	private byte upLegionScience;
	/** 玩家兵营数据类 */
	private Barrack barrack;
	/** 玩家的科技信息 */
	private String roleScience;
	/** 玩家科技信息map */
	private Map<Integer, Integer> roleScienceMap = new HashMap<Integer, Integer>();
	/** 玩家关卡信息 */
	private Chapter chapter;
	/** 玩家好友信息 */
	private Friend friend;
	/** 玩家签到信息 */
	private Sign sign;
	/** 竞技场数据 */
	private RoleArena roleArena;
	/** 战斗数据临时存放类 */
	// private FightSnapData fightSnapData = new FightSnapData();
	/** 最后一次增加军令的时间 */
	private long lastGetArmyToken;
	/** faceId */
	private byte faceId;
	/** 团队战役次数 */
	private byte raidTimes;
	/** 玩家团队战役的uuid */
	private String roomUUID;
	/** 玩家封地所在城市 */
	private RoleCity roleCity;
	/** 科技队列 */
	private String roleScienceQueue;
	/** 科技队列 */
	private RoleScienceQueue scienceQueue;
	/** 注册Ip */
	private String registerIp;
	/** 最后登录Ip */
	private String lastLoginIp;
	/** 登录次数 */
	private int loginNum;
	/** 注册时间 */
	private long registerTime;
	/** 登录时间 */
	private long loginTime;
	/** 在线总时长 */
	private long onLineSumTime;
	/** 玩家操作 */
	private String playerOperation;
	/** 玩家官阶 */
	private int rank;
	/** 今日俸禄是否领取 */
	private byte getRankPay;
	/** 保护盾时间 */
	private long proTime;
	/** 兵种研究院 */
	private RoleArmy roleArmy;
	/** 开启未领取的建筑		*/
	private String iconUnlock;
	/** 使用集合				*/
	private ArrayList<String> iconList = new ArrayList<String>();
	/** 战力值 		*/
	private int fightValue;
	/** 引导标识：强 	*/
	private int leadPoint;
	/** 引导标识：弱 	*/
	private String leadStr;
	/** 重修次数 		*/
	private int reTrainTimes;
	/** 每日友好提示	*/
	private Map<Byte, Integer> friendTipMap = new HashMap<Byte,Integer>();
	/** 世界大战战况	 */
	private String historyStr;
	/** 世界大战战况Map */
	private List<History> historyList = new ArrayList<History>();
	/** 记录进贡信息Str */
	private String pumHistoryStr;
	/** 进贡信息Map	 */
	private List<History> pumHistoryList = new ArrayList<History>();
	
	private Set<String> auctionInfo = new HashSet<String>();
	/**世界大战部队表*/
	private HashSet<WorldArmy> worldArmySet = new HashSet<>();
	/**据点*/
	private int strongHold;
	/**是否设置据点*/
	private byte isStrongHold;
	/** 今日献策次数		*/
	private int scienceTime;
	/**反抗次数*/
	private int resistanceNum;
	
	/** 目标				*/
	private Target target;
	/** 已经领取的礼包类型	*/
	private String getPstr;
	/** 已经领取的礼包类型	*/
	private Set<Byte> getPstrSet = new HashSet<Byte>();
	/**官邸升级时间*/
	private String rankRecordTimeStr;
	/**排名时间记录*/
	private HashMap<Byte,Integer> rankRecordTimeMap = new HashMap<>();
	/**小村庄防御队列字符串*/
	private String villageDefStr;
	/**小村庄防御队列*/
	private List<WorldArmy> villageDefList = new ArrayList<>();
	/**小村庄矿的部队id*/
	private String villageMineFarmWorldArmyId;
	/**小村庄矿的开始时间*/
	private int villageMineFarmStartTime;
	/**小村庄国籍*/
	private byte villageNation;
	/**世界大战小山村翻倍字符串*/
	private String worldDoublePStr;
	/**世界大战小山村翻倍*/
	private WorldDoubleP worldDoubleP = new WorldDoubleP();
	/** 通天塔		*/
	private Tower tower;
	
	//TODO 平台相关
	private int partnerId;
	private byte canUse;
	
	
	public String getRoleScienceQueue() {
		StringBuilder sb = new StringBuilder();
		RoleScienceQueue scienceQueue = this.getScienceQueue();
		sb.append(scienceQueue.getTime()).append(",").append(scienceQueue.getLastUpTime()).append(",")
				.append(scienceQueue.getOpen()).append(",").append(scienceQueue.getType()).append(",");

		this.roleScienceQueue = sb.toString();
		return roleScienceQueue;
	}

	public void setRoleScienceQueue(String roleScienceQueue) {
		if (roleScienceQueue != null && !roleScienceQueue.equals("")) {

			String[] strBuild = roleScienceQueue.split(",");
			RoleScienceQueue roleScience = new RoleScienceQueue();
			roleScience.setId(1);
			roleScience.setTime(Integer.parseInt(strBuild[0]));
			roleScience.setLastUpTime(Long.parseLong(strBuild[1]));
			roleScience.setOpen(Byte.parseByte(strBuild[2]));
			roleScience.setType(Byte.parseByte(strBuild[3]));
			this.scienceQueue = roleScience;
		}
	}

	public RoleScienceQueue getScienceQueue() {
		return scienceQueue;
	}

	public void setScienceQueue(RoleScienceQueue scienceQueue) {
		this.scienceQueue = scienceQueue;
	}

	/** 建筑队列 */
	private String BuildQueue;
	/** 建筑队列map key:队列id value:buildQueue对象 */
	private Map<Byte, BuildQueue> BuildQueueMap = new HashMap<Byte, BuildQueue>();

	public String getBuildQueue() {
		StringBuilder sb = new StringBuilder();
		for (byte i = 1; i <= getBuildQueueMap().size(); i++) {
			BuildQueue tempHouseQueue = getBuildQueueMap().get(i);
			sb.append(tempHouseQueue.getTime()).append(",").append(tempHouseQueue.getLastUpTime()).append(",")
					.append(tempHouseQueue.getOpen()).append(",").append(tempHouseQueue.getType()).append(";");
		}
		this.BuildQueue = sb.toString();
		return BuildQueue;
	}

	public void setBuildQueue(String BuildQueue) {
		if (BuildQueue != null && !BuildQueue.equals("")) {
			String[] strs = BuildQueue.split(";");
			for (int i = 0; i < strs.length; i++) {
				String[] strBuild = strs[i].split(",");
				BuildQueue tempQueue = new BuildQueue();
				tempQueue.setId(i + 1);
				tempQueue.setTime(Integer.parseInt(strBuild[0]));
				tempQueue.setLastUpTime(Long.parseLong(strBuild[1]));
				tempQueue.setOpen(Byte.parseByte(strBuild[2]));
				tempQueue.setType(Byte.parseByte(strBuild[3]));
				this.getBuildQueueMap().put((byte) tempQueue.getId(), tempQueue);
			}
		}
	}

	public Map<Byte, BuildQueue> getBuildQueueMap() {
		return BuildQueueMap;
	}

	public void setBuildQueueMap(Map<Byte, BuildQueue> BuildQueueMap) {
		this.BuildQueueMap = BuildQueueMap;
	}

	private boolean change;

	/** 金币变化事件 */
	public GoldChangHandler onGoldChanged = new GoldChangHandler();

	/***************************** get & set *********************************************/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.change = true;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
		this.change = true;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		int temp = this.gold - gold;
		if (temp > 0) {
			this.onGoldChanged.notifyDemoEvent(temp, this.id);
		}

		this.gold = gold;
		this.change = true;
	}

	public int getPrestige() {
		return prestige;
	}

	public void setPrestige(int prestige) {
		if (prestige < 0) {
			prestige = 0;
		}
		this.prestige = prestige;
	}

	public int getFood() {
		return food;
	}

	public synchronized void setFood(int food) {
		this.food = food;
		this.change = true;
	}

	public int getPopulation(Role role) {
		int max = getPopulationLimit(role);
		if (population > max) {
			return max;
		}
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
		this.change = true;
	}

	public int getPopulationLimit(Role role) {
		// populationLimit = RoleInitialValue.POPULATION_LIMIT;
		// if (role.getBuild() != null) {
		// Map<Integer, Integer> barrackConfigMap =
		// role.getBuild().getBarrackLvMap();
		// for (Map.Entry<Integer, Integer> entry : barrackConfigMap.entrySet())
		// {
		// BuildConfig buildCongfig =
		// BuildConfigCache.getBuildConfig(entry.getValue(), (byte) 3);
		// populationLimit += buildCongfig.getCapa ();
		// }
		// }
		return populationLimit;
	}

	public void setPopulationLimit(int populationLimit) {
		this.populationLimit = populationLimit;
		this.change = true;
	}

	public int getExploit() {
		return exploit;
	}

	public void setExploit(int exploit) {
		this.exploit = exploit;
		this.change = true;
	}

	public byte getVipLv() {
		return vipLv;
	}

	public void setVipLv(byte vipLv) {
		this.vipLv = vipLv;
		this.change = true;
	}

	public int getArmyToken() {
		return armyToken;
	}

	public void setArmyToken(int armyToken) {
		this.armyToken = armyToken;
		this.change = true;
	}

	public int getMoney() {
		return money;
	}

	public synchronized void setMoney(int money) {
		this.money = money;
		this.change = true;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
		this.change = true;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
		this.change = true;
	}

	public short getLv() {
		return lv;
	}

	public void setLv(short lv) {
		this.lv = lv;
		this.change = true;
	}

	public Build getBuild() {
		return build;
	}

	public void setBuild(Build build) {
		this.build = build;
		this.change = true;
	}

	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
		this.change = true;
	}

	public int getLegionId() {
		return legionId;
	}

	public void setLegionId(int legionId) {
		this.legionId = legionId;
		this.change = true;
	}

	public Set<Integer> getApplyLegionSet() {
		return applyLegionSet;
	}

	public void setApplyLegionSet(Set<Integer> applyLegionSet) {
		this.applyLegionSet = applyLegionSet;
		this.change = true;
	}

	public byte getNation() {
		return nation;
	}

	public void setNation(byte nation) {
		this.nation = nation;
		this.change = true;
	}

	public long getTime_1() {
		return time_1;
	}

	public void setTime_1(long time_1) {
		this.time_1 = time_1;
		this.change = true;
	}

	public long getTime_2() {
		return time_2;
	}

	public void setTime_2(long time_2) {
		this.time_2 = time_2;
		this.change = true;
	}

	public long getTime_3() {
		return time_3;
	}

	public void setTime_3(long time_3) {
		this.time_3 = time_3;
		this.change = true;
	}

	public List<Mail> getRoleMail() {
		return roleMail;
	}

	public void setRoleMail(List<Mail> roleMail) {
		this.roleMail = roleMail;
		this.change = true;
	}

	public int getLegionContribution() {
		return legionContribution;
	}

	public void setLegionContribution(int legionContribution) {
		this.legionContribution = legionContribution;
		this.change = true;
	}

	public long getLastOffLineTime() {
		return lastOffLineTime;
	}

	public void setLastOffLineTime(long lastOffLineTime) {
		this.lastOffLineTime = lastOffLineTime;
		this.change = true;
	}

	public String getApplyLegion() {
		StringBuilder sb = new StringBuilder();
		for (Integer legionId : this.applyLegionSet) {
			sb.append(legionId).append(",");
		}
		this.applyLegion = sb.toString();
		return applyLegion;
	}

	public void setApplyLegion(String applyLegion) {
		this.change = true;
		if (applyLegion != null && !applyLegion.equals("")) {
			String[] strs = applyLegion.split(",");
			for (String legionId : strs) {
				this.applyLegionSet.add(Integer.valueOf(legionId));
			}
		}
	}

	public long getLastQuiteLegion() {
		return lastQuiteLegion;
	}

	public void setLastQuiteLegion(long lastQuiteLegion) {
		this.change = true;
		this.lastQuiteLegion = lastQuiteLegion;
	}

	public Map<Integer, Prop> getPropMap() {
		return propMap;
	}

	public void setPropMap(Map<Integer, Prop> propMap) {
		this.change = true;
		this.propMap = propMap;
	}

	public short getMaxBagNum() {
		return maxBagNum;
	}

	public void setMaxBagNum(short maxBagNum) {
		this.change = true;
		this.maxBagNum = maxBagNum;
	}

	// public Map<Integer, Prop> getBackPack() {
	// return backPack;
	// }

	// public void setBackPack(Map<Integer, Prop> backPack) {
	// this.change=true;
	// this.backPack = backPack;
	// }

	public Map<Integer, Hero> getHeroMap() {
		return heroMap;
	}

	public void setHeroMap(Map<Integer, Hero> heroMap) {
		this.change = true;
		this.heroMap = heroMap;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.change = true;
		this.market = market;
	}

	public Pub getPub() {
		return pub;
	}

	public void setPub(Pub pub) {
		this.change = true;
		this.pub = pub;
	}

	// public Map<Integer, Hero> getRecruitHeroMap() {
	// return recruitHeroMap;
	// }
	//
	// public void setRecruitHeroMap(Map<Integer, Hero> recruitHeroMap) {
	// this.change = true;
	// this.recruitHeroMap = recruitHeroMap;
	// }

	// public FormationData getFormationData() {
	// return formationData;
	// }
	//
	// public void setFormationData(FormationData formationData) {
	// this.formationData = formationData;
	// }

	public Map<Integer, FormationData> getFormationData() {
		return formationData;
	}

	public int getArmsResearchNum() {
		return armsResearchNum;
	}

	public void setArmsResearchNum(int armsResearchNum) {
		this.change = true;
		this.armsResearchNum = armsResearchNum;
	}

	public String getArmsResearchStr() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, Integer> armsResearchData : this.armsResearchData.entrySet()) {
			sb.append(armsResearchData.getKey()).append(",").append(armsResearchData.getValue()).append(";");
		}
		this.armsResearchStr = sb.toString();
		return armsResearchStr;
	}

	public void setArmsResearchStr(String armsResearchStr) {
		this.change = true;
		if (armsResearchStr != null && !armsResearchStr.equals("")) {
			String[] strs = armsResearchStr.split(";");
			for (String dataStr : strs) {
				String[] str2 = dataStr.split(",");
				this.armsResearchData.put(Integer.valueOf(str2[0]), Integer.valueOf(str2[1]));
			}
		}
		this.armsResearchStr = armsResearchStr;
	}

	public Map<Integer, Integer> getArmsResearchData() {
		return armsResearchData;
	}

	public void setArmsResearchData(Map<Integer, Integer> armsResearchData) {
		this.change = true;
		this.armsResearchData = armsResearchData;
	}

	public void setFormationData(Map<Integer, FormationData> formationData) {
		this.change = true;
		this.formationData = formationData;
	}

	public Integer getUseFormationID() {
		return useFormationID;
	}

	public void setUseFormationID(Integer useFormationID) {
		this.change = true;
		this.useFormationID = useFormationID;
	}

	public String getFormationStr() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, FormationData> formationData : this.formationData.entrySet()) {
			sb.append(formationData.getKey()).append(":");
			for (Map.Entry<Byte, Integer> entry : formationData.getValue().getData().entrySet()) {
				sb.append(entry.getKey()).append(",").append(entry.getValue()).append(";");
			}
			sb.append("!");
		}
		this.formationStr = sb.toString();
		return formationStr;
	}

	public void setFormationStr(String formationStr) {
		this.change = true;
		if (formationStr != null && !formationStr.equals("")) {
			String[] strs = formationStr.split("!");
			for (String dataStr : strs) {
				String[] str2 = dataStr.split(":");
				FormationData forma = new FormationData();
				String[] str3 = str2[1].split(";");
				for (String str4 : str3) {
					String[] str5 = str4.split(",");
					forma.getData().put(Byte.parseByte(str5[0]), Integer.valueOf(str5[1]));
				}
				this.formationData.put(Integer.valueOf(str2[0]), forma);
			}
		}
		this.formationStr = formationStr;
	}

	public byte getUpLegionScience() {
		return upLegionScience;
	}

	public void setUpLegionScience(byte upLegionScience) {
		this.change = true;
		this.upLegionScience = upLegionScience;
	}

	public Barrack getBarrack() {
		return barrack;
	}

	public void setBarrack(Barrack barrack) {
		this.change = true;
		this.barrack = barrack;
	}

	public Map<Integer, Integer> getRoleScienceMap() {
		return roleScienceMap;
	}

	public void setRoleScienceMap(Map<Integer, Integer> roleScienceMap) {
		this.change = true;
		this.roleScienceMap = roleScienceMap;
	}

	public String getRoleScience() {
		StringBuilder sb = new StringBuilder();
		for (Entry<Integer, Integer> entry : this.roleScienceMap.entrySet()) {
			sb.append(entry.getKey()).append(",").append(entry.getValue()).append(";");
		}

		this.roleScience = sb.toString();
		return roleScience;
	}

	public void setRoleScience(String roleScience) {
		this.change = true;
		if (roleScience != null && !roleScience.equals("")) {
			String[] strs = roleScience.split(";");
			for (String science : strs) {
				String[] tempStr = science.split(",");
				this.roleScienceMap.put(Integer.parseInt(tempStr[0]), Integer.parseInt(tempStr[1]));
			}
		}
	}

	public int getSumGold() {
		return sumGold;
	}

	public void setSumGold(int sumGold) {
		this.change = true;
		this.sumGold = sumGold;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.change = true;
		this.chapter = chapter;
	}

	// public FightSnapData getFightSnapData() {
	// return fightSnapData;
	// }
	//
	// public void setFightSnapData(FightSnapData fightSnapData) {
	// this.fightSnapData = fightSnapData;
	// }

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.change = true;
		this.friend = friend;
	}

	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sign) {
		this.change = true;
		this.sign = sign;
	}

	public RoleArena getRoleArena() {
		return roleArena;
	}

	public void setRoleArena(RoleArena roleArena) {
		this.change = true;
		this.roleArena = roleArena;
	}

	public long getLastGetArmyToken() {
		return lastGetArmyToken;
	}

	public void setLastGetArmyToken(long lastGetArmyToken) {
		this.change = true;
		this.lastGetArmyToken = lastGetArmyToken;
	}

	public byte getFaceId() {
		return faceId;
	}

	public void setFaceId(byte faceId) {
		this.change = true;
		this.faceId = faceId;
	}

	public byte getRaidTimes() {
		return raidTimes;
	}

	public void setRaidTimes(byte raidTimes) {
		this.change = true;
		this.raidTimes = raidTimes;
	}

	public String getRoomUUID() {
		return roomUUID;
	}

	public void setRoomUUID(String roomUUID) {
		this.change = true;
		this.roomUUID = roomUUID;
	}

	public int getAreadyBuyArmyToken() {
		return areadyBuyArmyToken;
	}

	public void setAreadyBuyArmyToken(int areadyBuyArmyToken) {
		this.change = true;
		this.areadyBuyArmyToken = areadyBuyArmyToken;
	}

	public RoleCity getRoleCity() {
		return roleCity;
	}

	public void setRoleCity(RoleCity roleCity) {
		this.change = true;
		this.roleCity = roleCity;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.change = true;
		this.registerIp = registerIp;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.change = true;
		this.lastLoginIp = lastLoginIp;
	}

	public int getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(int loginNum) {
		this.change = true;
		this.loginNum = loginNum;
	}

	public long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(long registerTime) {
		this.change = true;
		this.registerTime = registerTime;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.change = true;
		this.loginTime = loginTime;
	}

	public long getOnLineSumTime() {
		return onLineSumTime;
	}

	public void setOnLineSumTime(long onLineSumTime) {
		this.change = true;
		this.onLineSumTime = onLineSumTime;
	}

	public String getPlayerOperation() {
		return playerOperation;
	}

	public void setPlayerOperation(String playerOperation) {
		this.change = true;
		this.playerOperation = playerOperation;
	}

	public void addPlayerOperation(String playerOperation) {
		this.change = true;
		if (this.playerOperation == null) {
			this.playerOperation = playerOperation + ",";
		} else {
			this.playerOperation = this.playerOperation + playerOperation + ",";
		}
		String[] arr = this.playerOperation.split(",");
		if (arr.length > 19) {
			this.playerOperation = this.playerOperation.substring(this.playerOperation.indexOf(",") + 1);
		}
	}

	public Set<Integer> getAlreadyGetVipAwardSet() {
		return alreadyGetVipAwardSet;
	}

	public void setAlreadyGetVipAwardSet(Set<Integer> alreadyGetVipAwardSet) {
		this.change = true;
		this.alreadyGetVipAwardSet = alreadyGetVipAwardSet;
	}

	public String getAlreadyGetVipAward() {
		StringBuilder sb = new StringBuilder();
		for (Integer awardId : this.alreadyGetVipAwardSet) {
			sb.append(awardId).append(",");
		}
		this.alreadyGetVipAward = sb.toString();
		return alreadyGetVipAward;
	}

	public void setAlreadyGetVipAward(String alreadyGetVipAward) {
		this.change = true;
		if (alreadyGetVipAward != null && !alreadyGetVipAward.equals("")) {
			String[] strs = alreadyGetVipAward.split(",");
			for (String awardId : strs) {
				this.alreadyGetVipAwardSet.add(Integer.valueOf(awardId));
			}
		}

		this.alreadyGetVipAward = alreadyGetVipAward;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.change = true;
		this.rank = rank;
	}

	public byte getGetRankPay() {
		return getRankPay;
	}

	public void setGetRankPay(byte getRankPay) {
		this.change = true;
		this.getRankPay = getRankPay;
	}

	public long getProTime() {
		return proTime;
	}

	public void setProTime(long proTime) {
		this.change = true;
		this.proTime = proTime;
	}

	public RoleArmy getRoleArmy() {
		return roleArmy;
	}

	public void setRoleArmy(RoleArmy roleArmy) {
		this.change = true;
		this.roleArmy = roleArmy;
	}
	
	public String getIconUnlock() {
		StringBuilder sb = new StringBuilder();
		for(String x : this.iconList)
		{
			sb.append(x).append(",");
		}
		return sb.toString();
	}

	public void setIconUnlock(String iconUnlock) {
		this.change = true;
		if(iconUnlock != null && !iconUnlock.equals(""))
		{
			String[] strs = iconUnlock.split(",");
			for(String x : strs)
			{
				this.iconList.add(x);
			}
		}
		this.iconUnlock = iconUnlock;
	}

	public Map<Integer, Prop> getPassivePropMap() {
		return passivePropMap;
	}

	public void setPassivePropMap(Map<Integer, Prop> passivePropMap) {
		this.passivePropMap = passivePropMap;
	}

	public int getFightValue() {
		calculateFightValue();
		return fightValue;
	}

	public void setFightValue(int fightValue) {
		this.fightValue = fightValue;
	}

	/**
	 * 计算战力值
	 * 
	 * @author wcy
	 */
	private void calculateFightValue() {
		FormationData formationData = this.formationData.get(useFormationID);
		if (formationData != null) {
			Map<Byte, Integer> mapData = formationData.getData();
			this.fightValue = 0;
			for (Integer heroId : mapData.values()) {
				Hero hero = heroMap.get(heroId);
				if (hero != null) {
					this.fightValue += hero.getFightValue();
				}
			}
		}

	}

	/**
	 * 消耗品道具列表，<配置表的itemId,Prop>
	 * 
	 * @return
	 * @author wcy
	 */
	public Map<Integer, Prop> getConsumePropMap() {
		return consumePropMap;
	}

	/**
	 * 消耗品道具列表，<配置表的itemId,Prop>
	 * 
	 * @param consumePropMap
	 * @author wcy
	 */
	public void setConsumePropMap(Map<Integer, Prop> consumePropMap) {
		this.change = true;
		this.consumePropMap = consumePropMap;
	}

	public int getLeadPoint() {
		return leadPoint;
	}

	public void setLeadPoint(int leadPoint) {
		this.leadPoint = leadPoint;
	}

	public String getLeadStr() {
		return leadStr;
	}

	public void setLeadStr(String leadStr) {
		this.leadStr = leadStr;
	}

	/**
	 * @return the reTrainTimes 重修次数
	 */
	public int getReTrainTimes() {
		return reTrainTimes;
	}

	/**
	 * @param reTrainTimes the reTrainTimes to set 设置重修次数
	 */
	public void setReTrainTimes(int reTrainTimes) {
		this.reTrainTimes = reTrainTimes;
	}

	/**
	 * 重置重修次数
	 * 
	 * @author wcy
	 */
	public void resetRetrainTimes(){
		setReTrainTimes(0);
	}

	public ArrayList<String> getIconList() {
		return iconList;
	}

	public void setIconList(ArrayList<String> iconList) {
		this.iconList = iconList;
	}

	public Map<Byte, Integer> getFriendTipMap() {
		return friendTipMap;
	}

	public void setFriendTipMap(Map<Byte, Integer> friendTipMap) {
		this.friendTipMap = friendTipMap;
	}

	public Set<String> getAuctionInfo() {
		return auctionInfo;
	}

	public void setAuctionInfo(Set<String> auctionInfo) {
		this.auctionInfo = auctionInfo;
	}

	public Map<Integer, FormationData> getWorldFormation() {
		return worldFormation;
	}

	public void setWorldFormation(Map<Integer, FormationData> worldFormation) {
		this.worldFormation = worldFormation;
	}

	public String getWorldFormationStr() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, FormationData> worldFormation : this.worldFormation.entrySet()) {
			sb.append(worldFormation.getKey()).append(":");
			for (Map.Entry<Byte, Integer> entry : worldFormation.getValue().getData().entrySet()) {
				sb.append(entry.getKey()).append(",").append(entry.getValue()).append(";");
			}
			sb.append("!");
		}
		this.worldFormationStr = sb.toString();
		return worldFormationStr;
	}

	public void setWorldFormationStr(String worldFormationStr) {
		this.change = true;
		if (worldFormationStr != null && !worldFormationStr.equals("")) {
			String[] strs = worldFormationStr.split("!");
			for (String dataStr : strs) {
				String[] str2 = dataStr.split(":");
				FormationData forma = new FormationData();
				String[] str3 = str2[1].split(";");
				for (String str4 : str3) {
					String[] str5 = str4.split(",");
					forma.getData().put(Byte.parseByte(str5[0]), Integer.valueOf(str5[1]));
				}
				this.worldFormation.put(Integer.valueOf(str2[0]), forma);
			}
		}
		this.worldFormationStr = worldFormationStr;
	}

	/**
	 * @return the worldArmySet
	 */
	public HashSet<WorldArmy> getWorldArmySet() {
		return worldArmySet;
	}

	/**
	 * @param worldArmySet the worldArmySet to set
	 */
	public void setWorldArmySet(HashSet<WorldArmy> worldArmySet) {
		this.worldArmySet = worldArmySet;
	}

	
	public WorldArmy getWorldArmyById(String worldArmyId)
	{
		WorldArmy temp = null;
		for(WorldArmy x : this.getWorldArmySet())
		{
			if(x.getId().equals(worldArmyId))
			{
				temp = x;
			}
		}
		return temp;
	}

	/**
	 * @return the strongHold
	 */
	public int getStrongHold() {
		return strongHold;
	}

	/**
	 * @param strongHold the strongHold to set
	 */
	public void setStrongHold(int strongHold) {
		this.strongHold = strongHold;
	}

	/**
	 * @return the setStrongHold
	 */
	public byte getIsStrongHold() {
		return isStrongHold;
	}

	/**
	 * @param isStrongHold the setStrongHold to set
	 */
	public void setIsStrongHold(byte isStrongHold) {
		this.isStrongHold = isStrongHold;
	}

	public List<History> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<History> historyList) {
		this.historyList = historyList;
	}

	public String getHistoryStr() {
		StringBuilder sb = new StringBuilder();
		for(History x : this.historyList)
		{
			sb.append(x.getTime()).append("~").append(x.getYear()).append("~")
				.append(x.getType()).append("~").append(x.getStr()).append("~")
				.append(x.getUuid()).append("!");
		}
		return sb.toString();
	}

	public void setHistoryStr(String historyStr) {
		if(historyStr != null && !historyStr.equals(""))
		{
			String[] strs = historyStr.split("!");
			for(String x : strs)
			{
				String[] temp = x.split("~");
				History history = new History(temp);
				this.historyList.add(history);
			}
		}
		this.historyStr = historyStr;
	}
	
	/**
	 * 加锁添加世界大战记录
	 * @param history
	 */
	public synchronized void addHistory(History history)
	{
		if(this.historyList.size() >= 50)
		{
			this.historyList.remove(0);
		}
		this.historyList.add(history);
	}
	

	public int getScienceTime() {
		return scienceTime;
	}

	public void setScienceTime(int scienceTime) {
		this.scienceTime = scienceTime;
	}

	public List<History> getPumHistoryList() {
		return pumHistoryList;
	}

	public void setPumHistoryList(List<History> pumHistoryList) {
		this.pumHistoryList = pumHistoryList;
	}

	public String getPumHistoryStr() {
		StringBuilder sb = new StringBuilder();
		for(History x : this.pumHistoryList)
		{
			sb.append(x.getTime()).append("~").append(x.getYear()).append("~")
				.append(x.getType()).append("~").append(x.getStr()).append("~")
				.append(x.getUuid()).append("!");
		}
		return sb.toString();
	}

	public void setPumHistoryStr(String pumHistoryStr) {
		if(pumHistoryStr != null && !pumHistoryStr.equals(""))
		{
			String[] strs = pumHistoryStr.split("!");
			for(String x : strs)
			{
				String[] temp = x.split("~");
				History history = new History(temp);
				this.pumHistoryList.add(history);
			}
		}
		this.pumHistoryStr = pumHistoryStr;
	}

	public void addPumHistoryStr(History history)
	{
		if(this.pumHistoryList.size() >= 30)
		{
			this.pumHistoryList.remove(0);
		}
		this.pumHistoryList.add(history);
	}

	/**
	 * @return the resistanceNum
	 */
	public int getResistanceNum() {
		return resistanceNum;
	}

	/**
	 * @param resistanceNum the resistanceNum to set
	 */
	public void setResistanceNum(int resistanceNum) {
		this.resistanceNum = resistanceNum;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public Set<Byte> getGetPstrSet() {
		return getPstrSet;
	}

	public void setGetPstrSet(Set<Byte> getPstrSet) {
		this.getPstrSet = getPstrSet;
	}

	public String getGetPstr() {
		StringBuilder sb = new StringBuilder();
		for(Byte x : this.getPstrSet)
		{
			sb.append(x).append(",");
		}
		return sb.toString();
	}

	public void setGetPstr(String getPstr) {
		if(getPstr!=null && !getPstr.equals(""))
		{
			String[] str = getPstr.split(",");
			for(String x:str)
			{
				this.getPstrSet.add(Byte.parseByte(x));
			}
		}
		
		this.getPstr = getPstr;
	}

	/**
	 * 获得排行记录时间
	 * @return
	 * @author wcy 2016年2月26日
	 */
	public String getRankRecordTimeStr() {
		StringBuilder sb = new StringBuilder();
		for(Entry<Byte,Integer> entry:rankRecordTimeMap.entrySet()){
			byte type = entry.getKey();
			int time = entry.getValue();
			
			sb.append(type).append(",").append(time).append(";");
		}
		this.rankRecordTimeStr = sb.toString();
		return rankRecordTimeStr;
	}

	/**
	 * 设置排行记录时间
	 * @param rankRecordTimeStr
	 * @author wcy 2016年2月26日
	 */
	public void setRankRecordTimeStr(String rankRecordTimeStr) {
		if (rankRecordTimeStr != null && !rankRecordTimeStr.equals("")) {
			this.rankRecordTimeStr = rankRecordTimeStr;
			String[] allTypeRankRecordTimeStr = rankRecordTimeStr.split(";");
			for(String typeRankRecordTimeStr:allTypeRankRecordTimeStr){
				String[] typeRankRecordTime = typeRankRecordTimeStr.split(",");
				byte type = Byte.parseByte(typeRankRecordTime[0]);
				int time = Integer.parseInt(typeRankRecordTime[1]);
				
				this.rankRecordTimeMap.put(type, time);
			}
		}
	}

	/**
	 * 获得排行记录时间表
	 * @return
	 * @author wcy 2016年2月26日
	 */
	public HashMap<Byte, Integer> getRankRecordTimeMap() {
		return rankRecordTimeMap;
	}

	public String getVillageDefStr() {

		StringBuffer sb = new StringBuffer();

		sb.append("1~");
		for (WorldArmy worldArmy : villageDefList) {
			String id = worldArmy.getId();
			int roleId = worldArmy.getRoleId();
			int heroCaptainId = worldArmy.getHeroCaptainId();
			String name = worldArmy.getName();
			int clientID = worldArmy.getClientId();
			FormationData formationData = worldArmy.getFormationData();
			Map<Byte, Integer> data = formationData.getData();

			sb.append(id).append(":").append(roleId).append("[").append(name).append(",").append(clientID).append(",")
					.append(heroCaptainId).append(">");
			for (Entry<Byte, Integer> dataEntry : data.entrySet()) {
				byte pos = dataEntry.getKey();
				int heroId = dataEntry.getValue();

				sb.append(pos).append(",").append(heroId).append("!");
			}
			sb.append("]");
		}
		sb.append(";");
		this.villageDefStr = sb.toString();

		return villageDefStr;
	}

	public void setVillageDefStr(String villageDefStr) {
		if (villageDefStr == null || villageDefStr.equals("")) {
			return;
		}
		this.villageDefStr = villageDefStr;
		String[] str = villageDefStr.split(";");
		if(str.length == 0){
			return;
		}
		
		for (String gateInfo : str) {
			String[] gateAndGateInfo = gateInfo.split("~");
			if (gateAndGateInfo.length <= 1) {
				return;
			}
			int gateNum = Integer.parseInt(gateAndGateInfo[0]);

			String info = gateAndGateInfo[1];
			String[] worldArmysInfo = info.split("]");

			List<WorldArmy> list = villageDefList;

			if (worldArmysInfo.length == 0) {
				return;
			}
			for (String worldArmyInfo : worldArmysInfo) {
				String[] str1 = worldArmyInfo.split("\\[");
				String[] info2 = str1[0].split(":");
				String id = info2[0];
				int roleId = Integer.parseInt(info2[1]);

				WorldArmy worldArmy = new WorldArmy();
				worldArmy.setId(id);
				worldArmy.setRoleId(roleId);
				FormationData formationData = new FormationData();

				String[] info3 = str1[1].split(">");
				String[] info4 = info3[0].split(",");
				String name = info4[0];
				int clientID = Integer.parseInt(info4[1]);
				int heroCaptainId = Integer.parseInt(info4[2]);
				worldArmy.setHeroCaptainId(heroCaptainId);
				worldArmy.setName(name);
				worldArmy.setClientId(clientID);
				String formationDataStr = info3[1];
				String[] dataStr = formationDataStr.split("!");
				for (String data1 : dataStr) {
					String[] heroInfo = data1.split(",");
					byte pos = Byte.parseByte(heroInfo[0]);
					int heroId = Integer.parseInt(heroInfo[1]);
					formationData.getData().put(pos, heroId);
				}

				worldArmy.setFormationData(formationData);
				list.add(worldArmy);
			}
		}
	}

	public List<WorldArmy> getVillageDefList() {
		return villageDefList;
	}
	/**
	 * 加入小山村等待队列
	 * @param worldArmy
	 * @author wcy 2016年3月8日
	 */
	public void addVillageDefWorldArmy(WorldArmy worldArmy){
		villageDefList.add(worldArmy);
		worldArmy.setStatus(CityConstant.ARMY_LAZY);
	}

	public String getVillageMineFarmWorldArmyId() {
		return villageMineFarmWorldArmyId;
	}

	public void setVillageMineFarmWorldArmyId(String villageMineFarmWorldArmyId) {
		this.villageMineFarmWorldArmyId = villageMineFarmWorldArmyId;
	}

	public int getVillageMineFarmStartTime() {
		return villageMineFarmStartTime;
	}

	public void setVillageMineFarmStartTime(int villageMineFarmStartTime) {
		this.villageMineFarmStartTime = villageMineFarmStartTime;
	}

	public byte getVillageNation() {
		return villageNation;
	}

	public void setVillageNation(byte villageNation) {
		this.villageNation = villageNation;
	}

	public WorldDoubleP getWorldDoubleP() {
		return worldDoubleP;
	}

	public void setWorldDoubleP(WorldDoubleP worldDoubleP) {
		this.worldDoubleP = worldDoubleP;
	}

	public String getWorldDoublePStr() {
		StringBuilder sb = new StringBuilder();
		int roleId = worldDoubleP.getRoleId();
		int startTime = worldDoubleP.getStartTime();
		sb.append(roleId).append(",").append(startTime);
		this.worldDoublePStr = sb.toString();
		return worldDoublePStr;
	}

	public void setWorldDoublePStr(String worldDoublePStr) {
		if(worldDoublePStr == null||worldDoublePStr.equals("")){
			return ;
		}
		this.worldDoublePStr = worldDoublePStr;
		String[] array = worldDoublePStr.split(",");
		int roleId = Integer.parseInt(array[0]);
		int startTime = Integer.parseInt(array[1]);
		
		worldDoubleP.setRoleId(roleId);
		worldDoubleP.setStartTime(startTime);		
	}

	public Tower getTower() {
		return tower;
	}

	public void setTower(Tower tower) {
		this.tower = tower;
	}
}
