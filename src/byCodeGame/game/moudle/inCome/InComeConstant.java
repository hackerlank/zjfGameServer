package byCodeGame.game.moudle.inCome;

public class InComeConstant {
	public enum HeroAttrType{
		captain,power,intel
	}
	/** 征收 */
	public static final short LEVY = 20301;
	/** 升级建筑 */
	public static final short UP_BUILD = 20302;
	/** 获取征收数据 */
	public static final short GET_INCOME_DATA = 20303;
	/** 开启一个新建筑 */
	public static final short OPEN_NEW_BUILD = 20304;
	/** 获取建筑数据 */
	public static final short GET_BUILD_DATA = 20305;
	/** 开启一个新的建筑队列 */
	public static final short OPEN_NEW_BUILD_QUEUE = 20306;
	/** 补满人口 */
	public static final short FILL_POPULATION = 20307;
	/** 获取建筑队列数据 */
	public static final short GET_BUILD_QUEUE_DATA = 20308;
	/** 清除队列时间 */
	public static final short CLEAR_BUILD_QUEUE_TIME = 20309;
	/** 服务器主动推送建筑增加信息 */
	public static final short SEND_GET_BUILD = 20310;

	public static final short GET_NEED_GOLD = 20311;

	/** 配属武将 */
	public static final short ATTACH_HERO = 20312;
	/** 取消配属 */
	public static final short CANCEL_ATTACH = 20313;
	/** 获取配属增益 */
	public static final short GET_INFO_ATTACH = 20314;
	/** 获取简要建筑信息 */
	public static final short GET_SIMPLE_BUILD_DATA = 20315;
	/** 建筑类型升级 */
	public static final short BUILD_TYPE_LV_UP = 20316;
	/** 获取资源 */
	public static final short GET_NUM_RESOURCE = 20317;
	/** 获取新开建筑的怪物信息 */
	public static final short GET_FIGHT_INFO = 20318;
	public static final short SHOW_SPEED_UP_LEVY = 20319;
	public static final short SPEED_UP_LEVY = 20320;
	/**领声望*/
	public static final short GET_PRESTIGE = 20321;
	public static final short SHOW_RANK_INFO = 20322;
	/** 获取建筑信息		*/
	public static final short GET_BUILD_INFO = 20323;
	/**城市信息改变*/
	public static final short BUILD_INFO_CHANGE = 20324;
	/**显示征收信息*/
	public static final short SHOW_LEVY_INFO = 20325;
	

	/** 每个人口消耗粮食数量 */
	public static final short POPULATION_FOOD = 1000;
	/** 建筑队列最大时间 */
	public static final int MAX_QUEUE_TIME = 7200;
	/** 最大建筑数量 */
	public static final byte MAX_QUEUE_NUM = 6;
	/** 最大征收值系数 */
	public static final int MAX_LEVY_MODULUS = 8;

	public static final byte CLOSE_QUEUE = 1;

	public static final byte OPEN_QUEUE = 0;
	/** 建筑队列每600 秒消耗1金币 */
	public static final int TIME_UNIT = 600;
	/** 征收时的主公抽成 */
	public static final double PAYOFF = 0.05;

	/** 没有武将的ID */
	public static final int NO_HERO = 0;
	/** 没有配属建筑 */
	public static final byte NO_ATTACH = 0;

	/** 建筑类型主城 */
	public static final byte TYPE_MAIN_CITY = 1;
	/** 建筑类型农田 */
	public static final byte TYPE_FARM = 2;
	/** 建筑类型居民 */
	public static final byte TYPE_HOUSE = 3;
	/** 建筑类型酒馆 */
	public static final byte TYPE_PUB = 4;
	/** 建筑类型锻造房 */
	public static final byte TYPE_FORGE = 5;
	/** 建筑类型科技塔 */
	public static final byte TYPE_TECH = 6;
	/** 建筑类型兵营 */
	public static final byte TYPE_CAMP = 7;
	/** 建筑类型集市 */
	public static final byte TYPE_MARKET = 8;
	/** 建筑类型试练营*/
	public static final byte TYPE_SHILIANYING = 9;
	/** 资源建筑最小cd时间 */
	public static final int MIN_BUILD_CD_TIME = 3;

	/** 额外固定每小时收益 */
	public static final int ADD_VALUE = 100;
	/** 没有等级 */
	public static final int NO_LV = 0;
	/** 科技解锁等级 */
	public static final Integer SCIENCE_UNLOCK = 1;
	/** 没有星数	 */
	public static final byte NO_STARTS = 0;
	/** 历史记录类型：粮草	*/
	public static final byte HIS_TYPE_FOOD = 24;
	/** 历史记录类型：银币	*/
	public static final byte HIS_TYPE_MONEY = 25;
	/**免费征收*/
	public static final byte LEVYTYPE_FREE = 1;
	/**花钱征收*/
	public static final byte LEVYTYPE_GOLD = 2;

	/** 领取资源类型（与客户端同步） */
	public static final byte RES_TYPE_SILVER = 0;
	public static final byte RES_TYPE_FOOD = 1;
	public static final byte RES_TYPE_GOLD = 2;
	public static final byte RES_TYPE_EXPLOIT = 3;
	public static final byte RES_TYPE_PRESTIGE = 4;
	public static final int LEVY_SPEED_UP_RANGE = 7200;
	public static final int LEVY_SPEED_UP_INSIDE_RANGE = 120;
	public static final int LEVY_SPEED_UP_OUTSIDE_RANGE = 360;
	public static final byte LEVY_SPEED_UP_FREE = 1;
	public static final byte LEVY_SPEED_UP_NOT_FREE = 0;
	public static final long LEVY_SPEED_UP_FREE_REDUECE_TIME = 1800;
	
	public static final byte CHANGE_TYPE_UNLOCK = 0;
	public static final byte CHANGE_TYPE_LV = 1;
	public static final byte CHANGE_TYPE_INCOME = 2;
	public static final byte CHANGE_TYPE_ATTACH = 3;
}
