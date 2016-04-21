package byCodeGame.game.moudle.science;

public class ScienceConstant {
	/** 获取玩家科技信息 */
	public static final short GET_INFO_ROLE_SCIENCE = 21401;

	/** 升级科技 */
	public static final short UPGRADE_ROLE_SCIENCE = 21402;

	/** 获取科技队列 */
	public static final short GET_ROLE_SCIENCE_QUEUE = 21403;
	/** 清除科技队列时间 */
	public static final short CLEAR_ROLE_SCIENCE_QUEUE_TIME = 21404;
	/** 献策 */
	public static final short OFFER_SCIENCE = 21405;
	/** 领取献策奖励 */
	public static final short GET_VISIT_SCIENCE_AWARD = 21406;

	/** 取出配置表时累加值 */
	public static final int GET_LV = 1;
	/** 科技类型的最小值 */
	public static final int MIN_ROLE_SCIENCE = 999;
	/** 阵型科技的最大值 */
	public static final int MAX_FORMATION__SCIENCE = 1999;
	/** 科技类型的最大值 */
	public static final int MAX_ROLE_SCIENCE = 3000;
	/** 科技队列最大时间 */
	public static final int MAX_QUEUE_TIME = 60 * 240;

	public static final byte CLOSE_QUEUE = 1;

	public static final byte OPEN_QUEUE = 0;
	/** 科技队列每600 秒消耗2GOLD金币 */
	public static final int TIME_UNIT = 600;
	/** 每600 秒消耗2金币数 */
	public static final int GOLDBYTIME_UNIT = 2;

	/** 科技是否开启 0未开启 */
	public static final byte NOT_OPEN = 0;
	/** 科技是否开启 1已开启 */
	public static final byte CAN_OPEN = 1;
	/** 建筑类型：科技 */
	public static final byte TYPE_BUILD_SCIENCE = 6;

	public static final int FOOD_SCIENCE_ID = 2009;
	public static final int MONEY_SCIENCE_ID = 2008;

}
