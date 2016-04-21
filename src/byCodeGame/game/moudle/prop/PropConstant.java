package byCodeGame.game.moudle.prop;

public class PropConstant {

	/** 获取道具列表 */
	public static final short GET_PROP_LIST = 20901;
	/** 使用道具 */
	public static final short USE_PROP = 20902;
	/** 增加道具 */
	public static final short ADD_PROP = 20903;
	/** 装备道具 */
	public static final short EQUIP_PROP = 20904;
	/** 卸载装备 */
	public static final short UNINSTALL_EQUIP = 20905;
	/** 一键换装 */
	public static final short QUICE_DRESS = 20906;
	/** 单个位置装备互换 */
	public static final short SINGLE_DRESS = 20907;
	/** 开启强化100%成功 */
	public static final short OPEN_MUST_SUCCESS = 20908;
	/** 强化 */
	public static final short STRENGTHEN_EQUIP = 20909;
	/** 刷新强化队列 */
	public static final short RESTART_STRENGTHEN = 20910;
	/** 获取玩家强化队列信息 */
	public static final short GET_INFO_STRENGTHEN = 20911;
	/** 扩充玩家背包 */
	public static final short ADD_MAX_BACK_NUM = 20912;
	/** 炼化装备 */
	public static final short RETURN_EQUIP = 20913;
	/** 精炼装备 */
	public static final short REFINE_EQUIP = 20914;
	/** 更换词缀 */
	public static final short CHANGE_PREFIX = 20915;
	/** 炼铁 */
	public static final short MAKE_IRON = 20916;
	/** 显示炼铁界面 */
	public static final short SHOW_MAKE_IRON = 20917;
	/** 领铁锭 */
	public static final short GET_IRONS = 20918;
	/** 获得寻得宝物 */
	public static final short GET_VISIT_TREASURE_AWARD = 20919;
	/** 寻宝 */
	public static final short VISIT_TREASURE = 20920;
	/** 显示强化界面 */
	public static final short SHOW_STRENGTHEN = 20921;
	/** 显示未装备的装备 */
	public static final short SHOW_EQUIP = 20922;
	/** 显示加铁块后的强化界面 */
	public static final short SHOW_ADD_IRON_STRENGTHEN = 20923;

	public static final short NUM_1 = 1;
	public static final short LV_1 = 1;
	public static final int USE_0 = 0;

	public static final byte SLOT_0 = 0;
	public static final byte SLOT_1 = 1;
	public static final byte SLOT_2 = 2;
	public static final byte SLOT_3 = 3;
	public static final byte SLOT_4 = 4;
	public static final byte SLOT_5 = 5;
	public static final byte SLOT_6 = 6;

	public static final int ITEM_TYPE_1 = 1;
	public static final int ITEM_TYPE_2 = 2;
	public static final int ITEM_TYPE_3 = 3;
	public static final int ITEM_TYPE_4 = 4;
	public static final int ITEM_TYPE_5 = 5;
	public static final int ITEM_TYPE_6 = 6;
	public static final int ITEM_TYPE_7 = 7;
	public static final int ITEM_TYPE_8 = 8;
	public static final int ITEM_TYPE_9 = 9;
	public static final int ITEM_TYPE_10 = 10;
	public static final int ITEM_TYPE_11 = 11;
	public static final int ITEM_TYPE_12 = 12;
	public static final int ITEM_TYPE_13 = 13;
	public static final int ITEM_TYPE_14 = 14;
	public static final int ITEM_TYPE_15 = 15;
	public static final int ITEM_TYPE_16 = 16;

	/** 道具类型 装备 */
	public static final byte FUNCTION_TYPE_1 = 1;
	/** 道具类型 消耗品 */
	public static final byte FUNCTION_TYPE_2 = 2;

	/** 强化成功率下限 */
	public static final int MIN_STRENGTHEN = 1;
	/** 强化成功率上限 */
	public static final int MAX_STRENGTHEN = 100;

	/** 开启强化成功100%的时间系数 10天 */
	public static final int TEN_OPEN_DAY = 10;
	/** 开启强化成功100%的时间系数 20天 */
	public static final int TWENTY_OPEN_DAY = 20;
	/** 开始强化成功100%的时间系数 30天 */
	public static final int THIRTY_OPEN_DAY = 30;
	/** 开启强化成功100%的金币花费 10天 */
	public static final int TEN_OPEN_COST = 1000;
	/** 开启强化成功100%的金币花费 20天 */
	public static final int TWENTY_OPEN_COST = 1900;
	/** 开启强化成功100%的金币花费 30天 */
	public static final int THIRTY_OPEN_COST = 2800;
	/** 开启强化成功100%的判定 */
	public static final int NO_OPEN = 0;

	/** 检查强化队列CD的判定值 */
	public static final long CD_CHECK = 0;
	/** 强化队列可用的常量 */
	public static final byte CAN_ADD_STRENGTHEN = 0;
	/** 强化队列不可用的常量 */
	public static final byte CAN_NOT_ADD_STRENGTHEN = 1;
	/** 强化队列最长时间 */
	public static final long MAX_STRENGTHEN_CD = 600;
	/** 强化装备等级+1 */
	public static final short LV_UP_PROP = 1;
	/** 强化结果 0失败 */
	public static final byte FAIL_STRENGTHEN = 0;
	/** 强化结果 1成功 */
	public static final byte SUCCESS_STRENGTHEN = 1;
	/** 100%强化成功每日递减值（天数） */
	public static final int DAY_MINUS_SUCCESS = 1;

	/** 装备的品质白色 */
	public static final int QUALITY_TYPE_1 = 1;
	/** 装备的品质绿色 */
	public static final int QUALITY_TYPE_2 = 2;
	/** 装备的品质蓝色 */
	public static final int QUALITY_TYPE_3 = 3;
	/** 装备的品质紫色 */
	public static final int QUALITY_TYPE_4 = 4;
	/** 装备的品质橙色 */
	public static final int QUALITY_TYPE_5 = 5;

	/** 玩家背包的初始数量 */
	public static final int INIT_NUM_BACK = 20;
	public static final int COUNT_BACK = 1;
	/** 迭代最大值 */
	public static final short MAX_HEAP_VALUE = 999;

	/** 精炼石的配置表编号 */
	public static final int STONE_ID = 20030;
	/** 精炼石的需求 */
	public static final int NEED_NUM_G = 10;
	/** 精炼石的需求 */
	public static final int NEED_NUM_B = 50;
	/** 精炼石的需求 */
	public static final int NEED_NUM_P = 100;
	/** 道具是主动使用 */
	public static final int CAN_USE = 1;

	public static final int PROP_IRON = 20042;
	public static final int PROP_ORE = 20043;
	public static final int IRON_NEED_ORE_NUM = 5;

	public static final short UNLOCK_RECOVERY_LV = 35;
	public static final short UNLOCK_REFINE_LV = 35;
}
