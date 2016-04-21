package byCodeGame.game.moudle.hero;

public class HeroConstant {

	/** 获取英雄列表 */
	public static final short GET_HERO_LIST = 21101;
	/** 获取突飞队列数据 */
	public static final short GET_QUICK_TRAIN_DATA = 21102;
	/** 突飞武将 */
	public static final short QUICK_TRAIN_HERO = 21103;
	/** 重置突飞队列CD */
	public static final short RESET_QUICK_TRAIN_CD = 21104;
	/** 训练武将 */
	public static final short TRAIN_HERO = 21105;
	/** 获取训练队列数据 */
	public static final short GET_TRAIN_LIST_DATA = 21106;
	/** 更新武将训练数据 */
	public static final short UPDATE_TRAIN_LIST = 21107;
	/** 开启训练位 */
	public static final short ADD_TRAIN_SEAT = 21108;
	/** 终止武将训练 */
	public static final short STOP_HERO_TRAIN = 21109;
	/** 更改武将训练模式 */
	public static final short CHANGE_TRAIN_MODE = 21110;
	/** 获得武将 */
	public static final short ADD_HERO = 21111;
	/** 保存阵型 */
	public static final short SAVE_FORMATION = 21112;
	/** 获取阵型数据 */
	public static final short GET_FORMATION_DATA = 21113;
	/** 进阶武将 */
	public static final short UP_REBIRTH_LV = 21114;
	/** 武将配置兵力 */
	public static final short DEPLOY_ARMS = 21115;
	/** 增加兵力 */
	public static final short ADD_ARMSNUM = 21116;
	/** 获取所有阵型数据 */
	public static final short GET_FORMATION_DATAALL = 21117;
	/** 获取所有阵型数据 */
	public static final short UPDATE_USEFORMATIONID = 21118;
	/** 技能加点 */
	public static final short UP_SKILL = 21119;
	/** 英雄好感度等级提升 */
	public static final short ADD_HERO_EMOTION = 21120;
	/** 获取英雄兵种信息 */
	public static final short GET_HERO_ARMY_INFO = 21121;
	/** 获取英雄转生信息 */
	public static final short GET_HERO_RE_LV = 21122;
	/** 获得兵的所有兵种信息 */
	public static final short GET_ALL_HERO_ARMY_INFO = 21124;
	public static final short RETREAIN_ARMY = 21125;
	public static final short UP_DUTY_LV = 21126;
	public static final short UP_STAR = 21127;
	public static final short INSTEAD_ARMY = 21128;
	/**显示转职界面*/
	public static final short SHOW_DUTY = 21129;
	public static final short SHOW_RANK = 21130;
	public static final short SHOW_HERO_DETAIL = 21131;
	public static final short SHOW_RETRAIN = 21132;
	public static final short COMPOUND_HERO = 21133;
	public static final short SHOW_GOLD_TO_MANUAL = 21134;
	public static final short GOLD_TO_MANUAL = 21135;

	/** 每次突飞增加的CD时间 (1分钟) */
	public static final long QUICQ_TRAIN_CD = 60 * 3;
	/** 普通突飞基础消耗战功 */
	public static final int BASE_NEED_EXPLOIT = 100;
	/** 突飞获得的基础经验值 */
	public static final int BASE_GET_EXP = 10;
	/** 突飞队列最大时间 */
	public static final long MAX_QUICK_TRAIN = 4 * 60 * 60;
	/** 基础获得经验 */
	public static final int BASE_TRAIN_VALUE = 1000;

	/** 武将状态-在野 */
	public static final byte HERO_STATUS_ZAIYE = 0;
	/** 武将状态-招募中 */
	public static final byte HERO_STATUS_DUILIE = 1;

	public static final byte BYTE_0 = 0;
	public static final byte BYTE_1 = 1;
	public static final byte BYTE_2 = 2;
	public static final long LONG_0 = 0;
	public static final int INT_0 = 0;

	public static final short R_TYPE_0 = 0;
	public static final short R_TYPE_1 = 1;
	public static final short R_TYPE_2 = 2;
	public static final short R_TYPE_3 = 3;
	public static final short R_TYPE_4 = 4;
	public static final short R_TYPE_5 = 5;

	public static final byte TRAIN_TIME_8 = 8;
	public static final byte TRAIN_TIME_12 = 12;
	public static final byte TRAIN_TIME_24 = 24;
	public static final byte TRAIN_TIME_48 = 48;
	public static final byte TRAIN_TIME_72 = 72;

	public static final double TYPE1_VALUE = 1;
	public static final double TYPE2_VALUE = 1.25;
	public static final double TYPE3_VALUE = 1.5;
	public static final double TYPE4_VALUE = 2;
	public static final double TYPE5_VALUE = 3;

	// 训练模式所需银币或金币数量
	public static final int TRAIN_TYPE1_NEED_MONEY = 2000;
	public static final int TRAIN_TYPE2_NEED_GOLD = 2;
	public static final int TRAIN_TYPE3_NEED_GOLD = 5;
	public static final int TRAIN_TYPE4_NEED_GOLD = 15;
	public static final int TRAIN_TYPE5_NEED_GOLD = 50;
	// 训练时间所需银币或金币数
	public static final int TRAIN_TIME8_NEED_MONEY = 0;
	public static final int TRAIN_TIME12_NEED_MONEY = 2000;
	public static final int TRAIN_TIME24_NEED_MONEY = 5000;
	public static final int TRAIN_TIME48_NEED_GOLD = 5;
	public static final int TRAIN_TIME72_NEED_GOLD = 50;

	public static final byte[] TIME_ARR = { 8, 12, 24, 48, 72 };
	public static final byte[] TYPE_ARR = { 1, 2, 3, 4, 5 };

	/** 最大vip等级 */
	public static final int MAX_VIP = 11;
	/** 限制武将等级使用 */
	public static final int TYPE_1 = 1;

	/** 武将下阵 */
	public static final int RM_HERO = 2;
	/** 阵型id最大值 */
	public static final int MAX_FORMATIONID = 1999;
	/** 武将训练位置最大值 */
	public static final int MAX_SEAT_TRAIN = 12;
	/** 武将技能升级的银币消耗 */
	public static final int UP_SKILL_COST = 1000;

	/** 武将转生之后的等级 */
	public static final short RE_LV = 1;
	/** 突飞令的类型 */
	public static final int TYPE_HERO_EXP = 13;
	/** 英雄升级 */
	public static final short HERO_LV_UP = 21123;

	/** 武将阵型的数量 */
	public static final byte NUM_FORMATION = 3;
	/**
	 * 正在使用
	 */
	public static final byte SOLDIER_USING = 3;
	/**
	 * 不可用
	 */
	public static final byte SOLDIER_CAN_USE = 1;
	/**
	 * 不可使用
	 */
	public static final byte SOLDIER_CAN_NOT_USE = 2;
	/**
	 * 初级兵符ID
	 */
	public static final int SMALL_SOLDIER_EXP_PROP_ID = 20033;
	/**
	 * 中级兵符ID
	 */
	public static final int MEDIUM_SOLDIER_EXP_PROP_ID = 20034;
	/**
	 * 高级兵符ID
	 */
	public static final int BIG_SOLDIER_EXP_PROP_ID = 20035;
	/**
	 * 印绶id
	 */
	public static final Integer DUTY_PROP = 20031;
	/** 最大转职数 */
	public static final int MAX_DUTY_LV = 3;
	/** 没有重修的限制等级 */
	public static final int NO_RETRAIN_LV = 1;
	/** 最大转生数 */
	public static final short MAX_REBIRTH_LV = 8;
	public static final int HERO_STAR_PROP_INITITIAL_INDEX = 30000;
	
	/** 初始兵种的ID数组		*/
	public static final int[] ARMY_ID = {0,2101,2102,2103,2104,2105};
	/** 没有默认兵种			*/
	public static final int NO_ARMY_ID = 0; 
	
}
