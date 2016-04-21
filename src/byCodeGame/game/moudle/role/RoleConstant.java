package byCodeGame.game.moudle.role;

public class RoleConstant {

	/** 创建角色界面默认的角色性别(女) */
	public static final short DEFAULT_SEX = 0;
	/** 创建角色界名称格式规范 */
	public static final String NAME_FORMAT = "^[A-Za-z0-9\u4e00-\u9fa5]{0,}$";

	/** 设定玩家的名字 */
	public static final short SET_ROLE_NAME = 21503;
	/** 玩家升级 */
	public static final short ROLE_LV_UP = 21504;
	/** 玩家获得军令 */
	public static final short ADD_ARMYTOKEN_ROLE = 21505;
	/** 设定玩家头像 */
	public static final short SET_ROLE_FACE = 21506;
	/** 玩家获得经验 */
	public static final short ADD_EXP_ROLE = 21507;
	/** 玩家获得银币 */
	public static final short ADD_MONEY_ROLE = 21509;
	/** 玩家获得金币 */
	public static final short ADD_GOLD_ROLE = 21510;
	/** 玩家获得粮草 */
	public static final short ADD_FOOD_ROLE = 21511;
	/** 玩家获得战功 */
	public static final short ADD_EXPLOIT_ROLE = 21512;
	/** 玩家获得人口 */
	public static final short ADD_POPULATION_ROLE = 21513;
	/** 玩家获得声望 */
	public static final short ADD_PRESTIGE_ROLE = 21514;
	/** 官邸升级 */
	public static final short ROLE_CITY_LV_UP = 21519;
	/** 获得玩家数据 */
	public static final short GET_ROLE_DATA = 21520;
	/** 引导表示变更 */
	public static final short SET_LEAD_POINT = 21521;

	/** 完成新建筑流程		 */
	public static final short FINISH_NEW_BUILD = 21522;
	/** 新建筑主动推送		 */
	public static final short OPEN_NEW_BUILD = 21523;
	/** 每日友好提示		 */
	public static final short FRIEND_TIP = 21524;
	
	/** 名字的最长的长度	 */
	public static final int MAX_LENGTH_NAME = 6;
	/** 名字的最小的长度 */
	public static final int MIN_LENGTH_NAME = 2;

	/** 头像编号最小值 */
	public static final byte MIN_SIZE_FACEID = 0;
	/** 头像编号最大值 */
	public static final byte MAX_SIZE_FACEID = 6;

	/** 每日任务开启的等级 */
	public static final short MIN_DAILY_TASK_LV = 2;
	/** 市场开启的等级 */
	public static final short MIN_MARKET_LV = 10;

	/** 引导标识：强 */
	public static final byte LEAD_S = 1;
	/** 引导标识：弱 */
	public static final byte LEAD_R = 2;
	/** 免费重修 */
	public static final int FREE_RETRAIN = 1;
	/** 重修使用金币数量 */
	public static final int[] RETRAIN_USE_GOLD_ARRAY = { 0, 5, 10, 15, 20 };

	public static final int NUM_0 = 0;
	public static final int NUM_1 = 1;
	
}
