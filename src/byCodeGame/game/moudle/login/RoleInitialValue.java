package byCodeGame.game.moudle.login;

import byCodeGame.game.util.Utils;

/**
 * 玩家各项初始值
 * 
 * @author 王君辉
 *
 */
public class RoleInitialValue {

//	/** 初始金钱 */ 该值已在常量表中
//	public static final int MONEY = 500;
//	/** 初始金币 */ 该值已在常量表中
//	public static final int GOLD = 0;
//	/** 初始粮食 */ 该值已在常量表中
//	public static final int FOOD = 2000;
	/** 初始军令 */
	public static final int ARMY_TOKEN = 50;
	/** 初始战功 */
	public static final int EXPLOIT = 0;
	/** 初始等级 */
	public static final short LV = 1;
	/** 初始使用阵型ID */
	public static final int USEFORMATIONID = 1001;
	/** 最大背包数量 */
	public static final short MAX_BAG_NUM = 20;

	/** 初始武将训练位 */
	public static final byte TRAIN_NUM = 3;
	/** 初始训练位数据 */
	public static final String TRAIN_DATA = "0,0,0,0,0,0;0,0,0,0,0,0;0,0,0,0,0,0;";

	/** 初始化兵种科技等级 */
	public static String ARMS_RESEACH = Utils.getArmsResearch();

	/** 初始农场等级 */
	public static final String FARM_LV = "1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
	/** 初始民居等级 */
	public static final String HOUSE_LV = "1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
	/** 初始兵营等级 */
	public static final String BARRACK_LV = "1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
	/** 初始建筑等级 */
//	public static final String BULID_LV = "2,1;3,1;4,1;5,1;6,1;7,1;8,1";
	public static final String BULID_LV = "4,1";
	/** 初始化人口值 */
	public static final int POPULATION = 9999;
	/** 初始化人口上限 */
	public static final int POPULATION_LIMIT = 1000;
	// public static final String BARRACK_LV =
	// "0,0;0,0;0,0;0,0;0,0;0,0;0,0;0,0;0,0;0,0;0,0;0,0;0,0;0,0;0,0;0,0;";
	/** 初始建筑队列 */
	public static final String BUILD_QUEUE = "0,0,0,0,0;0,0,0,0,0;";
	/** 初始科技队列 */
	public static final String ROLESCIENCE_QUEUE = "0,0,0,0,0,";
	// /** 初始兵营建筑队列 */
	public static final String BARRACK_QUEUE = "0,0,0;";
	// /** 初始农田建筑队列 */
	// public static final String FARM_QUEUE = "0,0,0;";
	// /** 初始民居建筑队列 */
	// public static final String HOUSE_QUEUE = "0,0,0;";
	/** 初始武将队列数量 */
	public static final short RECRUIT_HERO_NUM = 15;

	/** 用户初始任务 */
	public static final String DOING_TASK = "1,1,0;";
	/** 测试用VIP等级 */
	public static final byte VIP_LV = 10;
	/** 武将1 男主将 */
	public static final int HERO_ID = 2500;
	/** 武将1所带小兵 */
	public static final int ARMS_ID = 2101;
	/** 武将2 女主将 */
	public static final int HERO2_ID = /* 2600 */2499;
	/** 武将2所带小兵 */
	public static final int ARMS2_ID = 2103;
	/** 武将初始品阶 */
	public static final int RANK = 1;
	/** 玩家科技信息初始化 */
	public static final String INIT_SCIENCE = "1001,1;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;"
			+ "2008,1;2009,0;2010,0;2011,0;";

	/** 玩家初始的团队战役次数 */
	public static final byte RAID_TIMES = 3;

	/** 玩家科技点数 */
	public static final int ARMS_RESEARCH_INIT = 10;

	/** 玩家功能图标解锁 */
	public static final String ICON_UNLOCK_INIT = "";
	/** 酒馆宴请初始酒桌数量 */
	public static final String PUB_DESK = "0,1;1,3;2,3";
	/** 初始缓存 */
	public static final String INCOME_CACHE = "1,0;2,0;3,0;4,0;5,0;7,0";
	/**前一天的缓存量*/
	public static final String LAST_DAY_INCOME_CACHE = "1,0;2,0;3,0;4,0;5,0;7,0";
	/** 初始声望 */
	public static final String PRESTIG_VALUE = "1,1000;2,0;3,0;4,0;5,0;6,0;7,0;8,0;9,0;10,0;11,0;12,0;13,0;";
	/** 初始引导：强 */
	public static final byte LEAD_POINT = 0;
	/** 初始引导：弱 */
	public static final String LEAD_STR = "";
	/** 初始化阵型 */
	public static final String FORMATION_STR = "1001:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!"
			+ "1002:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!" + "1003:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!";
	// + "1004:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!" +
	// "1005:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!" ;
	/** 初始化世界阵型*/
	public static final String WORLD_FORMATION = "3001:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!"
			+ "3002:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!" + "3003:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!"
			+ "3004:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!" + "3005:1,1;2,1;3,1;4,1;5,1;6,1;7,1;8,1;9,1;!";

//	public static final String TARGET_INIT = "1-1,1;-0]";
	public static final String TARGET_INIT = "-1,1;-0]";
}
