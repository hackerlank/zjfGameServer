package byCodeGame.game.navigation;

/**
 * 指令导航划分
 * 
 */
public class NavigationModule {
	/** 登录 */
	public static final short LOGIN = 101;
	/** 创建角色 */
	public static final short CREAT_ROLE = 102;
	/** 获取角色信息 */
	public static final short GET_ROLE_DATA = 103;

	/** 世界聊天 */
	public static final short CHAT = 201;
	/** 私聊 */
	public static final short CHAT_PRIVATE = 203;
	/**国家聊天*/
	public static final short CHAT_COUNTRY = 204;
	/** 军团聊天 */
	public static final short CHAT_LEGION = 205;

	/** 征收 */
	public static final short LEVY = 301;
	/** 升级建筑 */
	public static final short UP_BUILD = 302;
	/** 获取征收数据 */
	public static final short GET_INCOME_DATE = 303;
	/** 开启建筑 */
	public static final short OPEN_BUILD = 304;
	/** 获取建筑数据 */
	public static final short GET_BUILD_DATA = 305;
	/** 开启新的建筑队列 */
	public static final short OPEN_NEW_BUILD_QUEUE = 306;
	/** 通过粮草补满人口 */
	public static final short FILL_POPULATION = 307;
	/** 获取建筑队列数据 */
	public static final short GET_BUILD_QUEUE_DATA = 308;
	/** 清除建筑队列cd */
	public static final short CLEAR_BUILD_QUEUE_TIME = 309;
	/** 获取开启新的建筑队列需要的元宝数 **/
	public static final short GET_NEED_GOLD = 311;
	/** 配属武将 */
	public static final short ATTACH_HERO = 312;
	/** 取消配属 */
	public static final short CANCEL_ATTACH = 313;
	/** 获取武将配属增益 */
	public static final short GET_INFO_ATTACH = 314;
	/** 获得建筑简要信息 */
	public static final short GET_SIMPLE_BUILD_DATA = 315;
	/** 建筑类型升级 */
	public static final short BUILD_TYPE_LV_UP = 316;
	/** 获取资源 */
	public static final short GET_NUM_RESOURCE = 317;
	/** 获取新建建筑怪物信息 */
	public static final short GET_FIGHT_INFO = 318;
	/** 显示加速征收 */
	public static final short SHOW_SPEED_UP_LEVY = 319;
	/** 加速征收 */
	public static final short SPEED_UP_LEVY = 320;
	/**领声望*/
	public static final short GET_PRESTIGE = 321;
	/**显示排名*/
	public static final short SHOW_RANK_INFO = 322;
	/** 获取建筑信息 	*/
	public static final short GET_BUILD_INFO = 323;
	/**显示征收信息*/
	public static final short SHOW_LEVY_INFO = 325;

	/** 获取所有在线玩家数据 */
	public static final short GET_ONLINE_ROLE_DATA = 401;
	/** GM工具集 */
	public static final short GM_LIST = 402;
	/** 停服指令 		*/
	public static final short GM_STOP_APP = 444;
	/** 使用特权码		*/
	public static final short USE_PRIVILEGE = 403;

	/** 获取任务信息 */
	public static final short GET_DONG_TASK = 501;
	/** 完成任务 领取奖励 */
	public static final short COMPLETE_TASK = 502;
	/** 领取每日任务奖励 */
	public static final short GET_DAILY_TASK_AWARD = 505;
	/** 一键完成 */
	public static final short QUICK_COMPLETE_DAILY_TASK = 506;
	/** 获取每日任务信息 */
	public static final short GET_DAILY_TASK = 507;

	/** 设置国籍 		*/
	public static final short SET_NATION = 601;
	/** 获取国家人数	*/
	public static final short GET_NATION_NUM = 602;

	/** 玩家发送邮件 */
	public static final short SEND_MAIL_PLAYER = 801;
	/** 获取当前玩家所有邮件 */
	public static final short GET_ALL_MAIL = 802;
	/** 提取附件 */
	public static final short TAKE_ITEM_MAIL = 803;
	/** 删除邮件 */
	public static final short DELETE_MAIL = 804;
	/** 设置邮件是否已读 */
	public static final short MAIL_CHECKED_TYPE = 806;

	/** 创建公会 */
	public static final short CRETA_LEGION = 701;
	/** 获取军团信息 */
	public static final short GET_LEGION_DATA = 702;
	/** 获取军团列表 */
	public static final short GET_LEGION_LIST = 703;
	/** 申请加入军团 */
	public static final short APPLY_JOIN_LEGION = 704;
	/** 获取申请列表 */
	public static final short GET_APPLY_LIST = 705;
	/** 同意玩家加入军团 */
	public static final short AGREE_JOIN_LEGION = 706;
	/** 任命&撤销副团长 */
	public static final short SET_DEPUTY = 707;
	/** 修改军团图标 */
	public static final short CHANG_FACE_ID = 708;
	/** 修改军团公告 */
	public static final short CHANG_NAOTICE = 709;
	/** 获取成员列表 */
	public static final short GET_ALL_MEMBER = 710;
	/** 退出公会 */
	public static final short QUITE_LEGION = 711;
	/** 公会ID搜索 */
	public static final short SEARCH_LEGION = 712;
	/** 军团科技升级 */
	public static final short UPGRADE_SCIENCE = 713;
	/** 设定军团科技升级的类型 */
	public static final short SET_APPOINT_SCIENCE = 714;
	/** 设定自动接受申请的状态 */
	public static final short SET_AUTO_AGREE_JOIN = 715;
	/** 军团兑换 */
	public static final short EXCHANGE_LEGION = 716;
	/** 拒绝玩家 */
	public static final short REJECT_JOIN = 717;
	/** 撤销申请 */
	public static final short CANCEL_APPLY = 719;
	/** 增加军团最大人数 */
	public static final short ADD_MAX_PEOPLE_NUM = 720;
	/** 获取兑换信息 */
	public static final short GET_INFO_EXCHANGE = 724;
	/** 设置集结旗		*/
	public static final short SET_TARGET_LEGION = 725;
	/** 修改旗号		*/
	public static final short CHANG_SHORT_NAME = 726;

	/** 获取用户道具列表 */
	public static final short GET_PORP_LIST = 901;
	/** 使用道具 */
	public static final short USE_PROP = 902;
	/** 装备道具 */
	public static final short EQUIP_PROP = 904;
	/** 卸载道具 */
	public static final short UNINSTALL_EQUIP = 905;
	/** 一键换装 */
	public static final short QUICK_DRESS = 906;
	/** 单个位置互换装备 */
	public static final short SINGLE_DRESS = 907;
	/** 强化 */
	public static final short STRENGTHEN_EQUIP = 909;
	/** 扩充玩家背包 */
	public static final short ADD_MAX_NUM_BACK = 912;
	/** 出售道具（玩家出售） */
	/** 炼化装备 */
	public static final short RECOVERY_EQUIP = 913;
	/** 精炼装备 */
	public static final short REFINE_EQUIP = 914;
	/** 更换词缀 */
	public static final short CHANGE_PREFIX = 915;
	/** 炼铁 */
	public static final short MAKE_IRON = 916;
	/** 显示炼铁界面 */
	public static final short SHOW_MAKE_IRON = 917;
	/** 领铁锭 */
	public static final short GET_IRON = 918;
	/** 获得宝物 */
	public static final short GET_VISIT_TREASURE = 919;
	/** 寻宝 */
	public static final short VISIT_TREASURE = 920;
	/** 显示强化界面 */
	public static final short SHOW_STRENGTHEN = 921;
	/** 显示装备 */
	public static final short SHOW_EQUIP = 922;
	/** 显示加铁块后的强化界面 */
	public static final short SHOW_ADD_IRON_STRENGTHEN = 923;

	/** 出售道具（玩家出售） */
	public static final short SELL_MARKET = 1001;
	/** 购买装备 （玩家购买） */
	public static final short BUY_MARKET = 1002;
	/** 市场刷新 （玩家消耗元宝） */
	public static final short FRESH_MARKET = 1003;
	/** 获取玩家的市场信息 */
	public static final short GET_MARKET_INFO = 1004;
	/** 获取藏宝阁信息 		*/
	public static final short GET_GOLD_MARKET_INFO = 1005;
	/** 藏宝阁购买 			*/
	public static final short BUY_GOLD_MARKET = 1006;
	/** 获取拍卖物品信息	*/
	public static final short SHOW_AUCTION_INFO = 1007;
	/** 卖出拍卖物品		*/
	public static final short SELL_AUCTION = 1008;
	/** 购买拍卖物品		*/
	public static final short BUY_AUCTION = 1009;
	/** 下架拍卖物品		*/
	public static final short CANCEL_AUCTION = 1010;

	/** 倒卖 */
	public static final short VISIT_SALE = 1011;
	/** 议价 */
	public static final short BARGAIN_ITEMS = 1012;
	/**显示议价*/
	public static final short SHOW_BARGAIN_ITEMS = 1013;
	/**显示声望商城*/
	public static final short SHOW_WORLD_MARKET_ITEMS = 1014;
	/**购买声望商城*/
	public static final short BUY_WORLD_MARKET_ITEMS = 1015;
	/**刷新声望商城*/
	public static final short REFRESH_WORLD_MARKET_ITEM = 1016;
	/**解锁声望商城格子*/
	public static final short UNLOCK_PRESTIGE_LIMIT = 1017;
	

	/** 获取自己的拍卖物品信息*/
	public static final short SHOW_AUCTION_SELF = 1050;


	/** 获取英雄列表 */
	public static final short GET_HERO_LIST = 1101;
	/** 突飞武将 */
	public static final short QUICK_TRAIN_HERO = 1103;
	/** 重置突飞CD */
	public static final short RESET_QUICK_TRAIN_CD = 1104;
	/** 训练武将 */
	public static final short TRAIN_HERO = 1105;
	/** 获取训练队列数据 */
	public static final short GET_TRAIN_LIST_DATA = 1106;
	/** 更新武将训练数据 */
	public static final short UPDATE_TRAIN_LIST = 1107;
	/** 增加训练位 */
	public static final short ADD_TRAIN_SEAT = 1108;
	/** 终止武将训练 */
	public static final short STOP_HERO_TRAIN = 1109;
	/** 更改武将训练模式 */
	public static final short CHANGE_HERO_TRAIN_MODE = 1110;
	/** 保存阵型 */
	public static final short SAVE_FORMATION = 1112;
	/** 获取阵型数据 */
	public static final short GET_FORMATION_DATA = 1113;
	/** 进阶武将 */
	public static final short UP_REBIRTH_LV = 1114;
	/** 武将配置兵力 */
	public static final short DEPLOY_ARMS = 1115;
	/** 补充兵力 */
	public static final short ADD_ARMSNUM = 1116;
	/** 获取所有阵型数据 */
	public static final short GET_FORMATION_DATAALL = 1117;
	/** 修改使用阵型ID */
	public static final short UPDATE_USEFORMATIONID = 1118;
	/** 升级武将技能 */
	public static final short HERO_UPDATE_SKILL_LV = 1119;
	/** 获取武将的兵种信息 */
	public static final short GET_HERO_ARMY_INFO = 1121;
	/** 获取武将晋升信息 */
	public static final short GET_HERO_RLV_INFO = 1122;
	/** 获取武将兵类型的所有兵种 */
	public static final short GET_ALL_HERO_ARMY_INFO = 1124;
	/** 重修兵种 */
	public static final short RETRAIN_ARMY = 1125;
	/** 武将转职 */
	public static final short UP_DUTY_LV = 1126;
	/** 武将升星 */
	public static final short UP_RANK_LV = 1127;
	/** 替换兵种 */
	public static final short INSTEAD_ARMY = 1128;
	/** 显示转职界面 */
	public static final short SHOW_DUTY = 1129;
	/** 显示升星 */
	public static final short SHOW_RANK = 1130;
	/** 显示武将详细信息界面 */
	public static final short SHOW_HERO_DETAIL = 1131;
	/** 显示重修 */
	public static final short SHOW_RETRAIN = 1132;
	/** 合成英雄 */
	public static final short COMPOUND_HERO = 1133;
	/**显示购买体力*/
	public static final short SHOW_BUY_MANUAL = 1134;
	/**购买体力值*/
	public static final short BUY_MANUAL = 1135;

	/** 酒馆会谈 */
	public static final short TALK_PUB = 1201;
	/** 酒馆拜访 */
	public static final short TALK_VISIT = 1202;
	/** 扩充招募武将列表 */
	public static final short GET_VISIT_AWARD = 1203;
	/** 武将登用 */
	public static final short GET_MAPINFO = 1204;
	/** 武将离队 */
	public static final short DEQUEUE_HERO_PUB = 1205;
	/** 获取玩家酒馆信息 */
	public static final short GET_PUB_INFO = 1206;
	/** 连续会谈 */
	public static final short MULTIPLE_TALK = 1207;
	/** 进入酒店 */
	public static final short ENTER_PUB = 1208;
	/** 积分换英雄 */
	public static final short MILE_CONVERT_HERO = 1209;
	/** 获得本周英雄 */
	public static final short GET_PUB_HEROS = 1210;
	/** 显示酒桌 */
	public static final short SHOW_WINE_DESK = 1211;
	/** 换一批酒桌英雄 */
	public static final short CHANGE_DESK_HERO = 1212;
	/** 显示酿酒界面 */
	public static final short SHOW_MAKE_WINE = 1213;
	/** 酿酒 */
	public static final short MAKE_WINE = 1214;
	/** 宴请 */
	public static final short DRINK_WINE = 1215;
	/** 领酒 */
	public static final short GET_WINE = 1216;
	/** 解锁酒桌 */
	public static final short UNLOCK_DESK = 1217;
	/** 加速 */
	public static final short SPEED_UP = 1218;

	/** 建造兵营 */
	public static final short BUILD_BARRACK = 1301;
	/** 升级兵营 */
	public static final short UP_BARRACK = 1302;
	/** 出售兵营 */
	public static final short SELL_BARRACK = 1303;
	/** 更改兵营 */
	public static final short CHANGE_BARRACK = 1304;
	/** 招募士兵 */
	public static final short RECRUIT_ARMS = 1305;
	/** 获取兵营建筑数据 */
	public static final short GET_BARRACK_DATA = 1306;
	/** 获取兵力数据 */
	public static final short GET_TROOPS_DATA = 1307;
	/** 显示征讨 */
	public static final short SHOW_MAKE_EXPLOIT = 1308;
	/** 领取战功 */
	public static final short GET_EXPLOIT = 1309;
	/**升级兵击等级*/
	public static final short UP_ARMY_SKILL = 1310;
	/**征讨*/
	public static final short VISIT_OFFENSIVE = 1311;
	/**显示兵击等级*/
	public static final short SHOW_ARMY_SKILL = 1312;
	/**显示征讨*/
	public static final short SHOW_VISIT_OFFENSIVE = 1313;
	/**显示征讨排名*/
	public static final short GET_OFFENSIVE_RANK = 1314;

	/** 获取玩家的科技信息 */
	public static final short GET_ROLE_SCIENCE_INFO = 1401;
	/** 升级科技 */
	public static final short UPGRADE_ROLE_SCIENCE = 1402;
	/** 获取科技队列信息 */
	public static final short GET_ROLE_SCIENCE_QUEUE = 1403;
	/** 清除科技队列cd */
	public static final short CLEAR_ROLE_SCIENCE_QUEUE_TIME = 1404;
	/** 献策 */
	public static final short OFFER_SCIENCE = 1405;
	/** 获取献策奖励 */
	public static final short GET_VISIT_SCIENCE_AWARD = 1406;

	/** 心跳 */
	public static final short HEART = 1501;
	/** 充值 */
	public static final short RECHARGE = 1502;
	/** 改名 */
	public static final short RENAME = 1503;
	/** 头像 */
	public static final short FACEID = 1506;
	/** 军令 */
	public static final short BUY_ARMYTOKEN = 1508;
	/** 获取vip信息 */
	public static final short GET_VIP_INFO = 1515;
	/** 获取VIp礼包信息 */
	public static final short GET_VIPRAWARD_INFO = 1517;
	/** 领取VIP礼包 */
	public static final short GET_VIP_AWARD = 1518;
	/** 玩家官邸升级 */
	public static final short ROLE_CITY_LV_UP = 1519;
	/** 获得玩家数据 */
	public static final short ROLE_GET_LV_DATA = 1520;
	/** 变更标识 */
	public static final short SET_LEAD_POINT = 1521;
	/** 完成新建筑开启 */
	public static final short FINISH_NEW_BUILD = 1522;
	/** 每日友好提示 */
	public static final short FRIEND_TIP = 1524;

	/** 获取关卡信息 */
	public static final short GET_CHAPTER_DATA = 1601;
	/** 获取全部通过的关卡数据 */
	public static final short GET_ALL_CHAPTER_DATA = 1602;
	/** 扫荡挂卡 */
	public static final short RAIDS_CHAPTER = 1603;
	/** 重置关卡次数 */
	public static final short REFRESH_CHAPTER_TIMES = 1604;
	/** 领取章节奖励 */
	public static final short GET_CHAPTER_AWARD = 1605;
	/** 获取星数数据 */
	public static final short GET_ALL_STAR = 1606;
	/** 获取章节奖励数据 */
	public static final short GET_CHAPTER_AWARD_DATA = 1607;
	/** 获取单一章节关卡 */
	public static final short GET_PART_CHAPTER_DATA = 1608;

	/** 请求攻打关卡 			*/
	public static final short REQUEST_ATK_CHAPTER = 1701;
	/** 根据UUID获取战斗回放	*/
	public static final short GET_REPORT_BY_UUID = 1704;

	/** 获取玩家好友信息 */
	public static final short GET_INFO_FRIEND = 1801;
	/** 修改玩家好友信息 */
	public static final short MODIFY_FRIEND_LIST = 1802;
	/** 修改玩家黑名单 */
	public static final short MODIFY_BLACK_LIST = 1803;

	/** 获取玩家签到信息 */
	public static final short GET_SIGN_INFO = 1901;
	/** 当日签到 */
	public static final short SIGN_TODAY = 1902;
	/** 补签 */
	public static final short SIGN_RETROACTIVE = 1903;
	/** 领取签到奖励 */
	public static final short GET_SIGN_AWARD = 1904;
	/** 领取签到武将 */
	public static final short GET_SIGN_HERO = 1905;

	/** 获取神兽信息 */
	public static final short GET_ANIMAL_INFO = 2001;
	/** 喂养神兽 */
	public static final short EAT_ANIMAL = 2002;

	/** 获取竞技场信息 */
	public static final short GET_ARENA_DATA = 2101;
	/** 获取竞技场对手 */
	public static final short GET_ARENA_TARGET = 2102;
	/** 挑战对手 */
	public static final short DARE_TARGET = 2103;
	/** 设置竞技场出场武将 */
	public static final short SET_FIGHT_HERO = 2104;
	/** 测试 */
	public static final short TEST_ARENA = 2105;
	/** 获取竞技场 */
	public static final short GET_ARENA_FIGHT_LOAD = 2106;
	/** 获取天梯排名 */
	public static final short GET_Ladder = 2107;
	/** 获取当前品阶排名 */
	public static final short GET_NowLadder = 2108;

	/** 获取大厅信息 */
	public static final short GET_LOBBY_INFO = 2201;
	/** 创建新房间 */
	public static final short CREAT_NEW_ROOM = 2202;
	/** 加入房间 */
	public static final short JOIN_ROOM = 2203;
	/** 获取房间信息 */
	public static final short GET_ROOM_INFO = 2204;
	/** 退出房间 */
	public static final short QUIT_ROOM = 2206;
	/** 踢出房间 */
	public static final short KICK_ROLE = 2207;
	/** 设置上阵武将 */
	public static final short SAVE_ROOMROLE_FORMATION = 2209;
	/** 准备完毕&取消准备 */
	public static final short SET_ROLE_STATUS = 2210;
	/** 开始战斗 */
	public static final short START_BATTLE = 2211;
	/** 更新玩家自身加载进度 */
	public static final short SET_LOADING_PROGRESS = 2213;
	/** 更新其他玩家加载进度 */
	public static final short UPDATE_LOADING_PROGRESS = 2214;

	/** 获取城市信息 (暂未使用) */
	public static final short GET_CITY_INFO = 2301;
	/** 进城 */
	public static final short GO_TO_CITY = 2302;
	/** 迁入 */
	public static final short JOIN_CITY = 2303;
	/** 获取世界信息 		 */
	public static final short WORLD_INFO = 2304;
	/** 获取护都府信息		 */
	public static final short GET_FIEF_INFO = 2305;
	/** 获取资源点信息 		 */
	public static final short GET_CITY_MINE_FARM = 2306;
	/** 获取资源点信息 		 */
	public static final short GET_CITY_MINE_FARM_INFO = 2307;
	/** 获取占矿可用部队信息	 */
	public static final short GET_CITY_MINE_FARM_CAN = 2308;
	/** 占领 */
	public static final short OCCUPY_CITY_MINE_OR_FARM = 2309;
	/** 疯狂增长 */
	public static final short CRAZY_CITY_MINE_FARM = 2310;
	/** 占领 */
	public static final short DROP_CITY_MINE_OR_FARM = 2311;
	/** 征服 */
	public static final short CONQUER = 2312;
	/** 放弃属臣 */
	public static final short DROP_VASSAL = 2313;
	/** 获取玩家本城所有部队	*/
	public static final short GET_ALL_CITY_ARMY = 2314;
	/**进村*/
	public static final short GO_TO_VILLAGE = 2315;
	/**显示公会城市*/
	public static final short SHOW_LEGION_CITY = 2316;
	/** 提升官阶 */
	public static final short UP_GRADE_RANK = 2350;
	/** 攻打（他势力） */
	public static final short PVP_FIGHT = 2351;
	/** 获取官职信息 */
	public static final short GET_RANK_INFO = 2352;
	/** 领取俸禄 */
	public static final short GET_RANK_AWARD = 2353;
	/** 获取属臣详细信息 */
	public static final short GET_VASSAL_INFO = 2354;
	/** 获取主公信息 */
	public static final short GET_MYLORD_INFO = 2355;
	/** 清空进贡信息 */
	public static final short CLEAR_PUM_INFO = 2356;
	/** 开始编辑世界阵型		*/
	public static final short START_WORLD_FORMATION = 2360;
	/** 获取玩家世界阵型信息		*/
	public static final short GET_WORLD_FORMATION = 2370;
	/** 保存玩家世界阵型信息		*/
	public static final short SAVE_WORLD_FORMATION = 2371;
	/** 获取英雄位置			*/
	public static final short GET_HERO_LOCATION = 2372;
	/**显示世界城市城墙信息*/
	public static final short SHOW_WORLD_CITY_WALL_INFO = 2373;
	/**行军*/
	public static final short MARCH_WORLD_ARMY_FROM_HOME = 2374;
	/**显示加速世界行军*/
	public static final short SHOW_SPEED_UP_WORLD_MARCH = 2375;
	/**加速世界行军*/
	public static final short SPEED_UP_WORLD_MARCH = 2376;
	/**显示驻守城市*/
	public static final short SHOW_STAY_WORLD_ARMY_CITY = 2377;
	/** 布防		*/
	public static final short CHANG_DEFINFO = 2378;
	/**  城主布防	*/
	public static final short CHANG_COREINFO = 2382;
	/**显示行军信息*/
	public static final short SHOW_WORLD_MARCH_INFO = 2379;
	/**显示世界城市信息 */
	public static final short SHOW_WORLD_CITY_INFO = 2380;
	/**获得贡献排行*/
	public static final short GET_CONTRIBUTE_RANK = 2381;
	/**从城市行军*/
	public static final short MARCH_WORLD_ARMY_FROM_CITY = 2383;
	/** 加入护都府			*/
	public static final short JOIN_CORE = 2384;
	/** 离开护都府			*/
	public static final short LEAVE_CORE = 2385;
	/**显示玩家情报*/
	public static final short SHOW_WORLD_MARCH_ROLE_INFO = 2386;
	/**显示一支行军的信息*/
	public static final short SHOW_ONE_WORLD_MARCH = 2387;
	/**撤军*/
	public static final short RETREATE_WORLD_MARCH = 2388;
	/**显示据点*/
	public static final short SHOW_STRONGHOLD = 2389;
	/**设置据点*/
	public static final short SET_STRONGHOLD = 2390;	
	/**解散部队*/
	public static final short DISIMISS_WORLD_ARMY = 2391;
	/**离开防守墙*/
	public static final short LEAVE_DEFINFO = 2392;
	/** 获取世界大战单一阵型信息		*/
	public static final short GET_FORMATION_INFO = 2394;
	/** 修改部队阵型				*/
	public static final short CHANGE_WORLD_ARMY_FORMATION = 2395;
	/** 复活加速*/
	public static final short SPEED_UP_WORLD_ARMY_ALIVE= 2396;
	/** 显示复活加速*/
	public static final short SHOW_SPEED_UP_WORLD_ARMY_ALIVE = 2397;

	/** 获取兵种科技信息 */
	public static final short GET_ARMS_RESEARCH = 2401;
	/** 兵种科技升级 */
	public static final short ADD_ARMS_RESEARCH_LV = 2402;
	/** 获取天赋信息 */
	public static final short GET_ROLE_ARMY_INFO = 2450;
	/** 升级天赋 */
	public static final short UP_ROLE_ARMY = 2451;
	/** 重置天赋 */
	public static final short RE_SET_ROLE_ARMY = 2452;

	/**
	 * 兵种系统
	 */
	/** 升级兵种等级 */
	public static final short UPDATE_HERO_SOLDIER_LV = 2402;
	/** 升级英雄兵种技能 */
	public static final short UPDATE_HERO_SOLDIER_SKILL = 2403;
	/** 进阶英雄兵种 */
	public static final short UPDATE_HERO_SOLDIER_QUALITY = 2404;
	/** 更换英雄兵种 */
	public static final short CHANGE_HERO_SOLDIER = 2405;

	/** 获取通天塔信息 */
	public static final short GET_BABEL_INFO = 2501;
	/** 挑战通天塔 */
	public static final short FIGHT_BABEL = 2502;
	/** 选择剧本		*/
	public static final short CHOICE_USERID = 2503;
	/** 调整部队信息	*/
	public static final short CHANG_TROOP_DATA = 2504;
	/** 获取当前武将信息*/
	public static final short GET_HERO_INFO = 2505;
	/** 重置通天塔		*/
	public static final short RESET_TOWER = 2506;
	/** 复活部队		*/
	public static final short REVIVE_HERO = 2507;
	
	/** 获取目标信息	*/
	public static final short GET_TARGET_INFO = 2601;
	/** 领取目标奖励	*/
	public static final short GET_AWARD_TARGET = 2602;
	/** 领取活动奖励	*/
	public static final short GET_ACTIVE_AWARD = 2604;
	
	/**显示活动*/
	public static final short SHOW_ACTIVITY = 2701;

	/** test:Fight **/
	public static final short FIGHT_TEST = 9999;
}
