package byCodeGame.game.moudle.pub;

public class PubConstant {
	/** 酒馆会谈单次 */
	public static final short TALK_PUB = 21201;
	/** 酒馆拜访 */
	public static final short VISIT_PUB = 21202;
	/** 携带武将扩充 */
	public static final short GET_VISIT_AWARD = 21203;
	/** 武将离队 */
	public static final short HERO_DEQUEUE = 21205;
	/** 获取玩家的酒馆信息 */
	public static final short GET_PUB_INFO = 21206;
	/** 连续会谈 */
	public static final short MULTIPLE_TALK_PUB = 21207;
	/** 进入酒馆 */
	public static final short ENTER_PUB = 21208;
	/** 积分兑换英雄 */
	public static final short MILE_CONVERT_HERO = 21209;
	/** 本周英雄 */
	public static final short GET_PUB_HEROS = 21210;
	/** 显示酒桌 */
	public static final short SHOW_WINE_DESK = 21211;
	/** 获取寻访地图信息 */
	public static final short GET_MAPINFO = 21204;
	/** 换一批武将 */
	public static final short CHANGE_DESK_HERO = 21212;
	/** 显示酿酒界面 */
	public static final short SHOW_MAKE_WINE = 21213;
	/** 酿酒 */
	public static final short MAKE_WINES = 21214;
	/** 宴请 */
	public static final short DRINK_WINE = 21215;
	/** 领酒 */
	public static final short GET_WINES = 21216;
	/** 解锁酒 */
	public static final short UNLOCK_DESK = 21217;
	/** 加速 */
	public static final short SPEED_UP = 21218;

	/** 每日获得里程无上限的vipLv */
	public static final int VIPLV_TALK_MAX = 9;
	/** 会谈时使用银币的数量 */
	public static final int COST_M_TALK = 10000;
	/** 每天可以使用银币会谈的次数 */
	public static final int TIME_FREE_TALK = 5;
	/** 会谈消耗的类型 银币 */
	public static final byte TYPE_TALK_EXPLOIT = 1;
	/** 会谈消耗的类型 金币 */
	public static final byte TYPE_TALK_GOLD = 2;
	/** 单次会谈的次数累加值 */
	public static final int ADD_TALK_NUMBER = 1;

	/** 酒馆会谈概率 里程 */
	public static final int TALK_MILE_MIN = 0;
	/** 酒馆会谈概率 里程 */
	public static final int TALK_MILE_MAX = 2000;
	/** 酒馆会谈概率 谢谢 */
	public static final int THANKS_MIN = 2001;
	/** 酒馆会谈概率 谢谢 */
	public static final int THANKS_MAX = 4500;
	/** 酒馆会谈概率 杂项 */
	public static final int TALK_OTHER_MIN = 4501;
	/** 酒馆会谈概率 杂项 */
	public static final int TALK_OTHER_MAX = 7500;
	/** 酒馆会谈概率 元宝军令 */
	public static final int TALK_GOLD_MIN = 7501;
	/** 酒馆会谈概率 元宝军令 */
	public static final int TALK_GOLD_MAX = 8999;
	/** 酒馆会谈概率 英雄 */
	public static final int TALK_HERO_MIN = 9000;
	/** 酒馆会谈概率 英雄 */
	public static final int TALK_HERO_MAX = 10000;
	/** 酒馆会谈概率 里程获得数量1 */
	public static final int MILE_GET_1 = 1;
	/** 酒馆会谈概率 里程获得数量2 */
	public static final int MILE_GET_2 = 2;
	/** 酒馆会谈概率 里程获得数量3 */
	public static final int MILE_GET_3 = 3;
	/** 酒馆会谈概率 子项里程 55% */
	public static final int MILE_CHANCE_ONE = 55;
	/** 酒馆会谈概率 子项里程 35% */
	public static final int MILE_CHANCE_TWO = 90;
	/** 酒馆会谈概率 子项里程 10% */
	public static final int MILE_CHANCE_THREE = 100;
	/** 酒馆会谈概率 子项杂项 军功33% */
	public static final int OTHER_EXPLOIT = 33;
	/** 酒馆会谈概率子项杂项 粮食33% */
	public static final int OTHER_FOOD = 66;
	/** 酒馆会谈概率子项杂项 银币33% */
	public static final int OTHER_MONEY = 99;
	/** 酒馆会谈 杂项获得数量 */
	public static final int OTHER_NUMBER = 10000;
	/** 酒馆会谈元宝军令 元宝70% */
	public static final int GOLD_CHANCE = 70;
	/** 酒馆会谈元宝军令 军令30% */
	public static final int ARMYTOKEN_CHANCE = 100;
	/** 酒馆会谈 元宝2 概率25% */
	public static final int GOLD_2_CHANCE = 25;
	/** 酒馆会谈 元宝4概率25% */
	public static final int GOLD_4_CHANCE = 50;
	/** 酒馆会谈 元宝6概率25% */
	public static final int GOLD_6_CHANCE = 75;
	/** 酒馆会谈 元宝8概率25% */
	public static final int GOLD_8_CHANCE = 100;
	/** 酒馆会谈 元宝数量2 */
	public static final int GOLD_NUMBER_2 = 2;
	/** 酒馆会谈 元宝数量4 */
	public static final int GOLD_NUMBER_4 = 4;
	/** 酒馆会谈 元宝数量6 */
	public static final int GOLD_NUMBER_6 = 6;
	/** 酒馆会谈 元宝数量8 */
	public static final int GOLD_NUMBER_8 = 8;
	/** 酒馆会谈 军令1概率90% */
	public static final int ARMYTOKEN_1_CHANCE = 90;
	/** 酒馆会谈 军令5概率10% */
	public static final int ARMYTOKEN_5_CHANCE = 100;
	/** 酒馆会谈 军令数量 1 */
	public static final int ARMYTOKEN_NUMBER_1 = 1;
	/** 酒馆会谈 军令数量 5 */
	public static final int ARMYTOKEN_NUMBER_5 = 5;

	/** 获得奖励类型 无 0 */
	public static final int AWARD_NOTHING = 0;
	/** 获得奖励类型 军令5 */
	public static final int AWARD_ARMYTOKEN = 5;
	/** 获得奖励类型 武将8 */
	public static final int AWARD_HERO = 8;
	/**获得奖励类型 道具9*/
	public static final int AWARD_PROP  = 9;

	/** 玩家已经拥有会谈奖励武将 补偿元宝 10 */
	public static final int EQUALIZE_GOLD = 10;
	/** 玩家已经拥有会谈奖励武将 补偿军令1 */
	public static final int EQUALIZE_ARMYTOKEN = 1;
	/** 玩家已经拥有会谈奖励武将 补偿里程3 */
	public static final int EQUALIZE_MILE = 3;
	/** 玩家当日疲劳值还有剩余 */
	public static final int TRIED_HAS = 0;
	/** 会谈次数达到元宝最高值 */
	public static final int TOP_TALK_NUMBER = 19;

	/** 会谈奖励调用方法三次 */
	public static final int THREE_TALK = 3;
	/** 获得武将时经验的初始值 */
	public static final short EXP_HERO = 0;
	/** 获得武将时武将等级的初始值 */
	public static final short LV_HERO = 1;
	/** 判断拜访房间是否合法 */
	public static final int ROOM_NUMBER = 1;

	/** 玩家拜访时获得的奖励类型 武将 */
	public static final byte VISIT_TYPE = 11;
	/** 拜访成功时邮件的类型 */
	public static final byte MAIL_TYPE = 2;
	/** 拜访成功时邮件的主题 */
	public static final String MAIL_TITLE = "YJ你欠我一个邮件主题";
	/** 拜访成功时邮件的征文 */
	public static final String MAIL_CONTEXT = "YJ你欠我一个拜访奖励邮件文字内容";
	/** 初次拜访 */
	public static final byte FIRST_VISIT_ROOMID = 0;

	/** 获取配置表 武将列表的追加值 */
	public static final int BACE_NUMBER_HERO = 1;
	/** 扩充武将列表时判断的vipLv */
	public static final int MAX_VIPLV = 8;

	/** 登用判断武将状态 */
	public static final byte SATUTS_HERO_USED = 1;
	/** 离队 武将状态 */
	public static final byte SATUTS_HERO_DEQUEUE = 0;
	/** 登用武将时的花费基数 */
	public static final int USED_HERO_COST = 1000;
	/** 登用武将列表最小值为1 */
	public static final int MIN_HERO_LIST = 1;

	/** 玩家的国籍 蜀 */
	public static final byte NATION_TYPE_1 = 1;
	/** 玩家的国籍 魏 */
	public static final byte NATION_TYPE_2 = 2;
	/** 玩家的国籍 吴 */
	public static final byte NATION_TYPE_3 = 3;
	/** 玩家的国籍 无 */
	public static final byte NATION_TYPE_4 = 0;

	/** 判断随机数值开关的数值 */
	public static final int NUMBER_RANDOM = 3;

	/** 判断玩家是否可以使用连续会谈功能 */
	public static final byte MIN_MULTIPLE_TALK = 5;

	/** 玩家获得里程 （银币） */
	public static final int TYPE_Y_MILE = 1;
	/** 玩家获得里程 （金币） */
	public static final int TYPE_G_MILE = 2;

	/** 张曼成 */
	public static final Integer HERO_ZL_ID = 2501;
	/** 连续会谈的折扣 */
	public static final double DISCOUNT_VIP = 0.8;

	/** 免费单次银币会谈时间 */
	public static final int FREE_MONEY_SINGLE_TALK_TIME = 12 * 60 * 60;
	/** 免费单次元宝会谈时间 */
	public static final int FREE_GOLD_SINGLE_TALK_TIME = 48 * 60 * 60;
	/** 进入酒馆的等级 */
	public static final int ENTER_PUB_LV = 2;
	/** 元宝单次会谈积分 */
	public static final int SINGLE_GOLD_SEND_MILE = 2;
	/** 元宝多次会谈积分 */
	public static final int MULTIPLE_GOLD_SEND_MILE = 15;
	/** 连续会谈的次数 */
	public static final int MULTIPLE_TALK_TIMES = 10;
	/** 寻访单次CD */
	public static final int VISIT_CD = 2000;
	/** 寻访统帅增益 */
	public static final int VISIT_HERO_CUT = 30;
	/** 一瓶酒需要多少大米 */
	public static final int WINE_NEED_RICE_NUM = 5;
	/** 免费金币会谈加速时间的次数 */
	public static final int FREE_GOLD_TALK_SPEED_UP_TIMES = 3;
	/** 免费换宴请武将的次数 */
	public static final int FREE_CHANGE_DESK_HERO_TIMES = 3;
	/** 武将喝酒的基础数量 */
	public static final int HERO_DRINK_WINE_BASE_NUM = 1;
	/** 已经宴请 */
	public static final byte ALREADY_DRINK = 1;
	/** 未宴请 */
	public static final byte NOT_DRINK = 0;
	/** 一张酒桌的座位数量 */
	public static final int SEAT_NUM = 4;

	/** 声望的整除值 */
	public static final int PRESTIG_VALUE = 1000;
	/** 声望 敌对 */
	public static final int PRESTIG_BAD = 0;
	/** 声望 友善 */
	public static final int PRESTIG_SOSO = 1;
	/** 声望 尊敬 */
	public static final int PRESTIG_GOOD = 2;
	/** 声望 崇敬 */
	public static final int PRESTIG_GOD = 3;
	/** 装备 阀值 */
	public static final int EQUIP_FLAG = 10000;
	/** 道具 阀值 */
	public static final int ITEM_FLAG = 20000;
	/** 装备 */
	public static final byte TYPE_EQUIP = 9;
	/** 道具 */
	public static final byte TYPE_ITEM = 10;
	/** 英雄 */
	public static final byte TYPE_HERO = 11;
	/** 酒馆的类型		*/
	public static final byte BUILD_TYPE_PUB = 4;
	/** byte 1		*/
	public static final byte B_NUM1 = 1;
	/** byte 2		*/
	public static final byte B_NUM2 = 2;
	/** 换一批武将消耗的金币 */
	public static final int CHANGE_DESK_HERO_COST_GOLD = 300;
	/** 换一批武将消耗的金币(vip) */
	public static final int CHANGE_DESK_HERO_COST_GOLD_VIP = 50;

	/** 大米的id */
	public static final int PROP_RICE_ID = 20040;
	/* 酒的道具id */
	public static final int PROP_WINE_ID = 20041;

	public static final byte DESK1 = 0;
	public static final byte DESK2 = 1;
	public static final byte DESK3 = 2;
	public static final byte DESK2_NEED_VIP_LV = 5;
	public static final byte DESK3_NEED_VIP_LV = 8;
	public static final byte DESK1_NEED_VIP_LV = 0;
	public static final byte UNLOCK = 1;
	public static final byte LOCK = 2;
	public static final byte CAN_NOT_USE = 3;
	public static final int DESK2_NEED_GOLD = 500;
	public static final int DESK3_NEED_GOLD = 800;
	public static final int DESK1_NEED_GOLD = 0;
	/** 抽到武将增加的好感度值 */
	public static final int ADD_EMOTION = 5;

	/** 战功单抽 */
	public static final int TALK_SINGLE_EXPLOIT = 1;
	/** 战功多抽 */
	public static final int TALK_MULIPLE_EXPLOIT = 2;
	/** 金币单抽 */
	public static final int TALK_SINGLE_GOLD = 3;
	/** 金币多抽 */
	public static final int TALK_MULIPLE_GOLD = 4;
	/** 单次会谈 */
	public static final int TALK_TYPE_SINGLE = 0;
	/** 多次会谈 */
	public static final int TALK_TYPE_MULTIPLE = 1;
}
