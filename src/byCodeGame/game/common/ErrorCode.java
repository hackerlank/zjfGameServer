package byCodeGame.game.common;

/**
 * @author ming 创建时间：2014-10-17
 */

public class ErrorCode {
	public static final short ERROR = 0;
	public static final short SUCCESS = 1;
	public static final short SHORT_TWO = 2;

	/** 服务器正在维护 */
	public static final short SERVICE_CLOSED = 30001;

	/** 游戏币不足 */
	public static final short NO_MONEY = 30101;
	/** 金币不足 */
	public static final short NO_GOLD = 30102;
	/** 目标用户未找到 */
	public static final short NULL_TARGET_ROLE = 30103;
	/** 战功不足 */
	public static final short NO_EXPLOIT = 30104;
	/** vip等级不足 */
	public static final short NO_VIP_LV = 30105;
	/** 玩家等级不足 */
	public static final short NO_LV = 30106;
	/** 粮食不足 */
	public static final short NO_FOOD = 30107;
	/** 军令不足 */
	public static final short NO_ARMY_TOKEN = 30108;
	/** 贡献不足 			*/
	public static final short NO_CONTRIBUTION = 30109;
	/** 官邸等级不足，请升级官邸 */
	public static final short LV_NOT_ENOUGH = 30110;
	/** 人口已满 			*/
	public static final short POPULTION_OVER = 30111;
	/** 背包格子不足 		*/
	public static final short NO_BACKNUMBER = 30112;
	/** 声望不足 			*/
	public static final short NO_PRESTIGE = 30113;
	/** 章节不允许升级 		*/
	public static final short CHAPTER_NO_ALLOW_LEVEL_UP = 30114;
	/** 没有国际 			*/
	public static final short NO_NATION = 30115;
	/** 武将没有体力		*/
	public static final short NO_MANUAL = 30116;
	/** 官职不够			*/
	public static final short NO_RANK = 30117;
	
	/** 私聊目标不在线或者用户不存在 */
	public static final short CHAT_TARGETID_ERR = 30201;
	/** 发送消息速度过快 */
	public static final short CHAT_TIME_ERR = 30202;
	/** 黑名单用户无法私聊 */
	public static final short CHAT_ERR_BLACK = 30203;
	/** 玩家还没有国籍 */
	public static final short CHAT_ERR_NATION = 30204;

	/** 建筑未解锁 不能升级 */
	public static final short BUILD_NO_LOCK = 30301;
	/** 未达到解锁条件 不能解锁建筑 */
	public static final short OPEN_ERROR = 30302;
	/** 建筑以开启 */
	public static final short BUILD_ID_OPEN = 30303;
	/** 没有空闲的建筑队列 */
	public static final short NO_BUILD_QUEUE = 30304;
	/** 建筑队列数量达到最高 */
	public static final short MAX_BUILD_QUEUE_NUM = 30305;
	/** 武将已经在征收中 */
	public static final short HERO_IN_LEVY = 30306;
	/** 主建筑物等级未达到 */
	public static final short BUILD_LV_NO_REACH = 30307;
	/**锻造房等级未达到*/
	public static final short PROP_BUILD_LV_NO_REACH = 30308;
	/*兵营等级未达到*/
	public static final short BARRACK_BUILD_LV_NO_REACH = 30309;
	/*计策府等级未达到*/
	public static final short SCIENCE_BUILD_LV_NO_REACH = 30310;
	/**超出今日征收限制*/
	public static final short LEVY_TIMES_OUT_RANGE = 30311;
	/**没有资源可以领取*/
	public static final short NO_RESOURCE = 30312;

	/** 特权码不正确		*/
	public static final short ERR_USE_PRIVILEGE = 30401;
	/** 特权码已经被使用	*/
	public static final short ERR_IS_UESD = 30402;
	/** 同一种类型的礼包只能使用一次	*/
	public static final short ERR_TYPE_IS_USED = 30403;
	
	/** 任务未完成不能领取奖励 */
	public static final short NOT_COM_TASK = 30501;
	/** 已经领取过今日每日任务奖励 */
	public static final short ALREADY_GET_TODAY = 30502;
	/** 完成度不足无法领取 */
	public static final short ERR_GET_DAILY_TASK_AWARD = 30503;
	/** 每日任务列表为空 */
	public static final short ERR_DAILY_TASK_MAP = 30504;

	/** 已有国籍，不能重复设置 */
	public static final short NATION_AREADY_HAS = 30601;

	/** 军团名重复 */
	public static final short LEGION_NAME_REPEAT = 30701;
	/** 玩家已经在军团中 */
	public static final short IN_LEGION = 30702;
	/** 公会名太长 */
	public static final short LEGION_NAME_LONG = 30703;
	/** 达到最大申请数量 */
	public static final short MAX_APPLY_NUM = 30704;
	/** 未找到目标军团 */
	public static final short NO_LEGION = 30705;
	/** 玩家没有加入军团 */
	public static final short NO_IN_LEGION = 30706;
	/** 目标玩家已加入其它军团 */
	public static final short IN_ORDER_LEGION = 30707;
	/** 军团达到最大人数 */
	public static final short MAX_PEOPLE_NUM = 30708;
	/** 没有操作权限 */
	public static final short NO_ACCEPT = 30709;
	/** 玩家已经是副团长 */
	public static final short AREADY_IS_DEPUTY = 30710;
	/** 玩家不是副团长 */
	public static final short NOT_DEPUTY = 30711;
	/** 非法的军团图标 */
	public static final short ILLEGAL_FACEID = 30712;
	/** 非法的公告长度 */
	public static final short ILLEGAL_NOTICE = 30713;
	/** 工会内有其成员无法解散工会 */
	public static final short QUITE_ERR = 30714;
	/** 科技代码有误 */
	public static final short ERR_SCIENCE = 30715;
	/** 玩家当日升级次数已用完 */
	public static final short ERR_NUMBER_SCIENCE = 30716;
	/** 军团等级不够无法升级科技 */
	public static final short ERR_UP_SCIENCE = 30717;
	/** 军团兑换道具不合法 */
	public static final short ERR_EXCHANGE_ITEM = 30718;
	/** 目标是会长不能任命 */
	public static final short TARGET_IS_CAPTAIN = 30719;
	/** 已达到副团长上限 */
	public static final short MAX_DEPT_LEGION = 30720;
	/** 不符合军团自动申请的条件 */
	public static final short NO_LV_ERR_LEGION = 30721;
	/** 设定等级不合法 */
	public static final short ILLEGAL_LV_AUTO = 30722;
	/** 请先兑换上一级武将 */
	public static final short ILLEGAL_ID_HERO = 30723;
	/** 冷却时间未结束		*/
	public static final short ERR_TIME_CIYT_ID = 30724;
	/** 错误的集结城市编号	*/
	public static final short ERR_CITY_ID = 30725;
	/** 错误的旗号长度		*/
	public static final short ERR_LENGTH_SHORT_NAME = 30726;
	/** 旗号重复			*/
	public static final short ERR_SHORT_NAME = 30727;

	/** 邮件有附件未提取 */
	public static final short ITEM_MAIL_ERR = 30801;
	/** 不能给自己发邮件 */
	public static final short MAIL_ID_ERR = 30802;
	/** 邮件为空 */
	public static final short MIAL_DELETE_ID_ERR = 30803;
	/** 邮件标题过长 */
	public static final short MAIL_TITLE_TOO_LONG = 30804;
	/** 邮件正文过长 */
	public static final short MAIL_CONTEXT_TOO_LONG = 30805;

	/** 玩家没有该物品 */
	public static final short NO_PROP = 30901;
	/** 该道具不是消耗品 不能使用 */
	public static final short PROP_CANT_USE = 30902;
	/** 道具已装备 */
	public static final short PROP_IS_EQUIP = 30903;
	/** 该道具不是装备 不能装备 */
	public static final short PROP_NOT_EQUIP = 30904;
	/** 不可一键换装 某件装备武将可能无法装备 */
	public static final short CAN_NOT_QUICE_DRESS = 30905;
	/** 不可兑换装备 */
	public static final short CAN_NOT_SINGLE_DRESS = 30906;
	/** 强化队列不可用 */
	public static final short STRENGTHEN_CAN_NOT_USE = 30907;
	/** 背包格子数量不合法 */
	public static final short ERR_NUM_BACK = 30908;
	/** 武将等级不够无法装备 */
	public static final short ERR_HERO_LV = 30909;
	/** 该道具不能批量使用 */
	public static final short ERR_UES_NUM = 30910;
	/** 已经达到当前等级上限 */
	public static final short ERR_LV_USE = 30911;
	/** 白色装备无法精炼 */
	public static final short ERR_RE = 30912;
	/** 精炼石不足 */
	public static final short ERR_NUM_STONE = 30913;
	/** 无法精炼达到最大值 */
	public static final short ERR_MAX_STONE = 30914;
	/** 为拥有词缀不能更换 */
	public static final short ERR_CHANGE_PREFIX = 30915;
	/** 没有可以卸下的装备 */
	public static final short NO_EQUIP_PROP = 30916;
	/** 炼铁失败 */
	public static final short FAIL_MAKE_IRON = 30917;

	/** 玩家持有数量不足 */
	public static final short ERR_NUMBER_SELL = 31001;
	/** 道具不可售 */
	public static final short ERR_CAN_NOT_SELL = 31002;
	/** 玩家金币数量不足无法购买 */
	public static final short GOLD_NOT_ENOUGH = 31003;
	/** 玩家银币数量不足无法购买 */
	public static final short MONEY_NOT_ENOUGH = 31004;
	/** 玩家背包空间不足 */
	public static final short BACKPACK_ERR = 31005;
	/** 道具类型错误 无法购买 */
	public static final short ERR_TYPE_MARKET_BUY = 31006;
	/** 玩家本日刷新次数达到上限 */
	public static final short ERR_TIME_FRESH = 31007;
	/** 达到玩家拍卖上限	 	*/
	public static final short MAX_NUM_AUCTION = 31008;
	/** 物品已经下架			*/
	public static final short ERR_TIME_AUCTION = 31009;
	/**	已经议价				*/
	public static final short MARKET_HAS_BARGIN = 31010;
	/** 价格不能低于最低价		*/
	public static final short LOWEST_AUCTION_PRICE = 31011;
	/**没有库存*/
	public static final short NO_INVENTORY = 31012;

	/** 突飞CD未到 */
	public static final short QUICE_TRAIN_CD = 31101;
	/** 训练位已有武将 */
	public static final short TRAIN_SITE_HAS_HERO = 31102;
	/** 没有该武将 */
	public static final short NO_HERO = 31103;
	/** 达到当前VIP等级可开启最大训练位置 */
	public static final short MAX_TRAIN_SETA = 31104;
	/** 当前VIP等级不可使用此训练模式 */
	public static final short CANT_USE_TRAIN_MODE = 31105;
	/** 不能进阶 武将未达到进阶等级要求 */
	public static final short CANT_UP_REBIRTH_LV = 31106;
	/** 不能突飞已达经验上限 */
	public static final short MAX_EXP_HERO = 31107;

	/** 玩家已经拥有该武将 */
	/** 已经达到武将转生上限 */
	public static final short MAX_R_LV = 31108;
	/** 好感度不足 */
	public static final short NO_EMO_HERO = 31109;
	/** 武将等级不足 */
	public static final short NO_LV_HERO = 31110;
	/** 武将最大转职数 */
	public static final short MAX_DUTY_LV = 31111;
	/** 武将满星 */
	public static final short MAX_STAR_LV = 31112;
	/** 没有可替换兵种 */
	public static final short NO_INSTEAD_ARMY = 31113;
	/** 不可重修 */
	public static final short NO_RETRAIN_LV = 31114;
	/** 行兵布阵等级不足 */
	public static final short ERR_LOC_NUM = 31115;
	/** 兵营等级不足 */
	public static final short NO_CAMP_LV = 31116;
	/** 武将满体力 */
	public static final short HERO_FULL_MANUAL = 31117;

	/** 玩家已经拥有该武将 */
	public static final short HERO_AREADY_HAS = 31201;
	/** 上次寻访未结束 */
	public static final short VISIT_NOT = 31202;
	/** 玩家访问的房间ID不正确 */
	public static final short ROOM_ID_ERR = 31203;
	/** 玩家的招募武将列表已达到上限 */
	public static final short ERR_LIMIT_HERO_LIST = 31204;
	/** 武将已被登用 */
	public static final short ERR_USED_HERO = 31205;
	/** 登用武将已达上限 */
	public static final short ERR_HERO_NUMBER = 31206;
	/** 登用武将列表不能为空 */
	public static final short ERR_MIN_HERO_LIST = 31207;
	/** 武将已上阵不能离队 */
	public static final short AREADY_IN_FORMATION = 31208;
	/** 积分不足 */
	public static final short NO_MILE = 31209;
	/** 已经宴请了 */
	public static final short ALREADY_DRINK = 31210;
	/** 已经解锁酒桌 */
	public static final short ALREADY_UNLOCK = 31211;
	/** 免费加速用完 */
	public static final short NO_TIMES = 31212;
	/** 无需加速 */
	public static final short NO_NEED_SPEED = 31213;

	/** 当前兵种类型达到最大值 */
	public static final short MAX_ARMS = 31302;
	/** 当前兵种科技点数不足 */
	public static final short NO_ARMSRESEARCH_NUM = 31303;
	/** 二阶兵种未全部研发完毕,不可研发三阶兵种 */
	public static final short NOT_ARMSRESEARCH_MAX = 31304;
	/**最大兵种等级*/
	public static final short MAX_ARMY_SKILL = 31305;
	/**最大征讨数*/
	public static final short MAX_OFFENSIVE = 31306;

	/** 科技类型不合法 */
	public static final short ERR_TYPE_SCIENCE = 31401;
	/** 科技等级已到上限 */
	public static final short ERR_MAX_SCIENCE = 31402;
	/** 献策次数已达今日上限	*/
	public static final short MAX_TIME_SCIENCE = 31403;

	/** 名字长度不合法 */
	public static final short NAME_OVER_LENGTH = 31501;
	/** 名字不和谐 */
	public static final short ERR_NAME_FORMAT = 31502;
	/** 名字有重复 */
	public static final short NAME_IS_AREADY_HAS = 31503;
	/** 超出军令购买限制 */
	public static final short MAX_ERR_ARMYTOKEN_BUY = 31504;

	/** 今日关卡已达到最大战斗次数 */
	public static final short CHAPTER_MAX_TIMES = 31601;
	/** 关卡战斗失败 */
	public static final short LOSE_CHAPTER = 31602;
	/** 不可扫荡，星数不够 */
	public static final short RAIDS_ERROR = 31603;
	/** 最大刷新次数 */
	public static final short MAX_REFRESH_TIME = 31604;
	/** 已经领取过本奖励 */
	public static final short ALREADY_GET_AWARD = 31605;
	/** 未达到领取所需星数 */
	public static final short NO_STARS = 31606;
	/** 剩余次数不为0 */
	public static final short CHAPTER_HAS_TIME = 31607;
	/** 无本章节关卡信息 */
	public static final short CHAPTER_DATA_NOT_FOUND = 31608;

	/** 兵力不足 */
	public static final short ARMY_NUMBER_ERR = 31701;
	/** 兵力总数不足 */
	public static final short ARMY_NUM_ALL_ERR = 31702;
	/** 战报已消失		*/
	public static final short ERR_UUID_REPORT = 31703;

	/** 目标已经是好友 */
	public static final short AREADY_IS_FRIEND = 31801;
	/** 目标不是好友 */
	public static final short IS_NOT_FRIEND = 31802;
	/** 目标已经是黑名单 */
	public static final short AREADY_IS_BLACK = 31803;
	/** 目标不在黑名单中 */
	public static final short IS_NOT_BLACK = 31804;
	/** 好友达到上限 */
	public static final short MAX_FRIEND_LIST = 31805;
	/** 黑名单达到上限 */
	public static final short MAX_BLACK_LIST = 31806;

	/** 玩家今日已经签到 */
	public static final short ERR_AREADY_SIGN = 31901;
	/** 签到天数不合法 */
	public static final short ERR_DAY_SIGN = 31902;
	/** 签到礼包已经领取不能重复领取 */
	public static final short AWARD_SIGN_AREADY_GET = 31903;
	/** 玩家签到次数不足 */
	public static final short NO_SIGN_TIMES = 31904;
	/** 补签日期为今日无需补签 */
	public static final short ERR_TODAY_SIGN = 31905;

	/** 没有这个神兽 */
	public static final short NO_ANIMAL = 32001;
	/** 达到最大喂养次数 */
	public static final short MAX_EAT_TIMES = 32002;

	/** 竞技场挑战CD未到 */
	public static final short ARENA_FIGHT_TIME_ERR = 32101;
	/** 达到最大攻打次数 */
	public static final short ARENA_MAX_TIMES = 32102;

	/** 大厅房间已满 */
	public static final short MAX_LOBBY_NUM = 32201;
	/** 玩家本周次数已耗尽 */
	public static final short NO_RAID_TIMES = 32202;
	/** 房间已满 */
	public static final short MAX_ROOM_NUM = 32203;
	/** 房间不存在 */
	public static final short ROOM_NOT_ACC = 32204;
	/** 玩家是房主只能解散房间 */
	public static final short ERR_QUIT_ROOM = 32205;
	/** 玩家处于准备完毕状态 */
	public static final short ERR_QUIT_STATUS = 32206;
	/** 被人踢出 */
	public static final short BE_KICKED_FROM_ROOM = 32207;
	/** 房间人数未满无法调整阵型 */
	public static final short ERR_SET_STATUS = 32208;
	/** 无法开始 有玩家未准备 */
	public static final short CAN_NOT_START_BATTLE = 32209;
	/** 没有替换武将可以替换 */
	public static final short NULL_CHANGE_HERO = 32210;
	/** 位置不合法 */
	public static final short NO_KEY = 32211;
	/** 阵型类型不合法 */
	public static final short NO_FORMATIONID = 32212;
	/** 默认阵型不允许没有武将 */
	public static final short FORMATION_NO_HERO = 32213;

	/** 不是自己国籍 */
	public static final short NOT_YOUR_NATION = 32301;
	/** 未满足三星模式击败张角 */
	public static final short NOT_ZHANGJIAO = 32302;
	/** 不能重复迁入封地 */
	public static final short NOT_JOIN_CITY = 32303;
	/** 驻守武将数量已达到上限 */
	public static final short MAX_AT_HREO = 32304;
	/** 不存在该武将 */
	public static final short NOT_THIS_HERO = 32305;
	/** 武将的等级不足 */
	public static final short NO_HERO_LV = 32306;
	/** 武将已执行过驻守任务 */
	public static final short MAX_AT = 32307;
	/** 不可偷窃同盟玩家 */
	public static final short NO_STEAL = 32308;
	/** 不存在该城市 */
	public static final short NO_CITY = 32309;
	/** 不存在这种类型的矿藏或者农田资源(id) */
	public static final short NO_MINEFARMID = 32310;
	/** 不是你的封地 */
	public static final short NO_HOME = 32311;
	/** 不可以重复开启疯狂模式 */
	public static final short NO_REPEAT_OPEN = 32312;
	/** 保护时间不可攻打 */
	public static final short AT_PROTECT = 32313;
	/** 未找到目标 */
	public static final short NO_TARGET = 32314;
	/** 可征服属下已达到上限 */
	public static final short MAX_CITY = 32315;
	/** 无法升级 */
	public static final short ERR_UP_RANK = 32350;
	/** 无法攻打 */
	public static final short ERR_PVP_TARGET = 32351;
	/** 无法领取 */
	public static final short ERR_GET_RANK_AWARD = 32352;
	/** 资源占领已达到上限 */
	public static final short MAX_MINE_OR_FARM = 32353;
	/**阵形不可为空*/
	public static final short NO_FORMATION_DATA = 32354;
	/**不可行军*/
	public static final short CAN_NOT_MARCH = 32355;
	/**行军目标称不可是出生点*/
	public static final short TARGET_CITY_IS_INTERN = 32356;
	/**行军路线当中有敌军城市*/
	public static final short WORLD_MARCH_HAS_ENEMY_CITY = 32357;
	/**冬季不可行军*/
	public static final short WINTER_NOT_MARCH = 32358;
	/** 部队信息错误		*/
	public static final short ERR_ID_WORLDARMY = 32359;
	/** 不是城市所属公会无法进入	*/
	public static final short ERR_ID_CITY_LEGION = 32360;
	/** 护都府部队达到上限		*/
	public static final short MAX_NUM_CORE = 32361;
	/**行军不存在*/
	public static final short WORLD_MARCH_NOT_EXIST = 32362;
	/**新手城不可攻打*/
	public static final short BIRTH_CITY_NOT_FIGHT = 32363;
	/**已经设置据点*/
	public static final short ALREADY_STRONGHOLD = 32364;
	/**行军加速失败*/
	public static final short WORLD_MARCH_CANT_SPEED_UP = 32365;
	/** 开启失败请检查阵型		*/
	public static final short ERR_START_WORLD_FORMATION = 32366;
	/** 武将不可用				*/
	public static final short ERR_USE_HERO = 32367;
	/** 不能新增武将请先解散部队	*/
	public static final short ERR_ADD_HERO = 32368;
	/** 领队武将ID错误			*/
	public static final short ERR_HERO_CAPTIN = 32369;
	/** 部队名字不可为空		*/
	public static final short ERR_NAME_ARMY = 32370;
	/** 部队还未复活 */
	public static final short WORLD_ARMY_ALREADY_DEAD = 32371;
	/**部队已经复活*/
	public static final short WORLD_ARMY_ALREADY_ALIVE = 32372;
	/**城市在保护中*/
	public static final short WORLD_CITY_UNDER_PROTECTED = 32373;
	/** 无法攻击自己的矿产		*/
	public static final short CAN_NOT_FIGHT_SELF = 32374;
	/** 无法放弃属城时间未到		*/
	public static final short ERR_TIME_DROP = 32375;
	/**超过反抗次数*/
	public static final short RESISTANCE_OUT_OF_RANGE = 32376;
	/**不能设为据点*/
	public static final short STRONGHOLD_SET_FAILED = 32377;
	/**不可放弃矿点*/
	public static final short ERROR_DROP_MINEFARM = 32378;
	/**世界部队正在行军不可解散*/
	public static final short WORLD_MARCH_IS_MARCH = 32379;
	/**只有将魂矿才可增长*/
	public static final short MINE_FARM_CRAZY_ERROR = 32380;
	/**不是你的矿*/
	public static final short NO_MINE_FARM = 32381;

	/** 兵种不可用wcy */
	public static final short ARMS_SOLDIER_CAN_NOT_USE = 32401;
	/** 兵种未解锁wcy */
	public static final short ARMS_NOT_UNLOCK = 32402;
	/** 兵种等级超过武将等级wcy */
	public static final short ARMS_SOLDIERLV_BIGGER_THAN_HEROLV = 32403;
	/** 兵符不可使用wcy */
	public static final short ARMS_PROP_CAN_NOT_USE = 32404;
	/** 没有升级道具 */
	public static final short ARMS_PROP_NOT_EXIST = 32405;
	/** 一阶兵种没有技能 */
	public static final short ARMS_QUALITY_ONE_HAS_NOT_SKILL = 32406;
	/** 兵种技能等级超过兵种等级 */
	public static final short ARMS_SKILLLV_BIGGER_THAN_SOLDIERLV = 32407;
	/** 进阶至顶级 */
	public static final short ARMS_QULALITY_MAX = 32408;
	/** 武将等级未达到 */
	public static final short ARMS_HERO_QUALITY_NOT_REACH = 32409;
	/** 兵种等级上限 */
	public static final short MAX_EXP_HEROSOLDIER = 32410;
	/** 兵阶不符合升级 */
	public static final short ARMS_SOLDIER_QUALITY_NOT_UNLOCK = 32411;

	/** 前置点数不够 */
	public static final short ERR_UP_ROLE_ARMY = 32450;
	/** 天赋点不够 */
	public static final short NO_ROLE_POINT = 32451;
	/** 已达到天赋最大等级 */
	public static final short MAX_LV_ROLE_ARMY = 32452;
	/** 已经有同等级天赋存在 */
	public static final short ALREADY_HAS_ROLE_ARMY = 32453;
	
	/** 本次通天塔已通关		*/
	public static final short ALREADY_PASS = 32501;
	/** 该关卡已通关			*/
	public static final short ERR_ID_BAB = 32502;
	/** 阵型上没有可用武将		*/
	public static final short NO_DATA_CAN_USE = 32503;
	/** 错误的部队信息			*/
	public static final short ERR_CORP_DATA_ID = 32504;
	/** 超过最大上阵部队数量		*/
	public static final short MAX_SIZE_TROOP = 32505;
	/** 部队未死亡不能使用复活功能	*/
	public static final short ERR_REVIVE_HERO = 32506;
	/** 选择武将数量错过最大值	*/
	public static final short ERR_MAX_NUM_HERO = 32507;
	
	/** 目标未完成			*/
	public static final short CAN_NOT_GET_TARGET = 32601;
	/** 未达到领取条件		*/
	public static final short CAN_NOT_GET_FIRST_R = 32602;
}
