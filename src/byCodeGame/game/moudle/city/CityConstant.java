package byCodeGame.game.moudle.city;

public class CityConstant {
	/**
	 * OK表示图标可点击  NO表示不可点击 ISOK表示已迁入
	 */
	public static final Byte ISOK=2;
	public static final Byte OK=1;
	public static final Byte NO=0;
	/**
	 *获取战况信息 
	 */
	public static final short GET_HISTORY_INFO=22301;
	/**
	 * 进城
	 */
	public static final short GO_TO_CITY=22302;
	/**进村*/
	public static final short GO_TO_VILLAGE = 22315;
	/**显示公会城市*/
	public static final short SHOW_LEGION_CITY = 22316;
	/**
	 * 迁入
	 */
	public static final short JOIN_CITY=22303;
	//张角ID
	public static final Integer ZHANGJIAO_ID=80212;
	//击败张角的关卡星数
	public static final Byte STAR_XING=3;
	public static final Byte SUCCESS=1;
	public static final Byte ERROR=0;
	/**
	 * 获取世界信息
	 */
	public static final short WORLD_INFO=22304;
	/** 开始编辑世界阵型			*/
	public static final short START_WORLD_FORMATION = 22360;
	/** 获取玩家世界阵型			*/
	public static final short GET_WORLD_FORMATION = 22370;
	/** 保存玩家世界阵型信息			*/
	public static final short SAVE_WORLD_FORMATION = 22371;
	/** 获取英雄当前位置			*/
	public static final short GET_HERO_LOCATION = 22372;
	/**显示城市城墙信息*/
	public static final short SHOW_WORLD_CITY_WALL_INFO = 22373;	
	/**组建部队*/
	public static final short MARCH_WORLD_ARMY_FROM_HOME = 22374;
	/**显示世界大战行军加速信息*/
	public static final short SHOW_SPEED_UP_WORLD_MARCH = 22375;
	/**加速世界大战行军*/
	public static final short SPEED_UP_WORLD_MARCH = 22376;
	/**显示驻守城市*/
	public static final short SHOW_STAY_WORLD_ARMY_CIY = 22377;
	/** 布防			*/
	public static final short CHANGE_DEFINFO = 22378;
	/** 城主布防		*/
	public static final short CHANGE_COREINFO = 22382;
	
	/**显示行军信息*/
	public static final short SHOW_WORLD_MARCH_INFO = 22379;
	/**显示城市信息*/
	public static final short SHOW_WORLD_CITY_INFO = 22380;
	/**获得贡献排行*/
	public static final short GET_CONTRIBUTE_RANK = 22381;
	/**行军（不是从新手城）*/
	public static final short MARCH_WORLD_ARMY_FROM_CITY = 22383;
	/** 加入护都府		*/
	public static final short JOIN_COREINFO = 22384;
	/** 离开护都府		*/
	public static final short LEAVE_COREINFO = 22385;
	/** 显示军情*/
	public static final short SHOW_WORLD_MARCH_ROLE_INFO = 22386;
	/**显示一支行军的信息点马*/
	public static final short SHOW_ONE_WORLD_MARCH = 22387;
	/**撤军*/
	public static final short RETREATE_WORLD_MARCH = 22388;
	/**显示据点*/
	public static final short SHOW_STRONGHOLD = 22389;
	/**设置据点*/
	public static final short SET_STRONGHOLD = 22390;
	/**解散部队*/
	public static final short DISIMISS_WORLD_ARMY = 22391;
	/**离开防守墙*/
	public static final short LEAVE_DEFINFO = 22392;
	/** 获取单一世界大战阵型			*/
	public static final short GET_FORMATION_INFO = 22394;
	/** 修改部队阵型				*/
	public static final short CHANGE_ARMY_FORMATION = 22395;
	/**显示加速部队复活*/
	public static final short SPEED_UP_WORLD_ARMY_ALIVE = 22396;
	/**显示加速部队复活*/
	public static final short SHOW_SPEED_UP_WORLD_ARMY_ALIVE = 22397;
	/** 世界信息发生变更			*/
	public static final short WORLD_INFO_CHANGE = 22399;
	/** 城市信息发生变更			*/
	public static final short CITY_INFO_CHANGE = 22398;
	/**
	 * 点击封地
	 */
	public static final short GET_FIEF_INFO=22305;
	/**
	 * 获取资源点信息
	 */
	public static final short GET_MINE_FARM=22306;
	/**
	 * 获取矿藏或农田详细信息
	 */
	public static final short GET_MINE_FARM_INFO=22307;
	
	public static final short GET_CAN_USE_ARMY_MINE = 22308;
	/**
	 * 占领	矿藏或者农田
	 */
	public static final short OCCUPY_MINE_OR_FARM=22309;
	/**
	 * 疯狂增长
	 */
	public static final short CRAZY_CITY_MINE_FARM=22310;
	/**
	 * 放弃占领	矿藏或者农田
	 */
	public static final short DROP_MINE_OR_FARM=22311;
	
	/**
	 * 征服
	 */
	public static final short CONQUER=22312;
	
	/**
	 * 放弃属城
	 */
	public static final short DROP_VASSAL=22313;
	
	/** 获取玩家在城市所有自己的部队	*/
	public static final short GET_ALL_CITY_ARMY = 22314;
	
	/**
	 * 属臣详细信息
	 */
	public static final short GET_VASSAL_INFO=22354;

	//国籍状态  1为同盟  0为敌对
	public static final Byte ALLY=1;
	public static final Byte HOSTILE=0;
	/** 表示英雄可用		*/
	public static final int HERO_LOCATION_NULL = 0;
	/** 消息类型 开始		*/
	public static final byte START_MOVE = 1;
	/** 消息类型 结束		*/
	public static final byte OVER_MOVE = 2;
	/** 消息类型 失败		*/
	public static final byte CANCEL_MOVE = 3;

	//最大驻守武将数量
	public static final  int MAX_AT_HREO=5;
	//驻守武将消耗银币系数*lv
	public static final  int MONEY_MULTIPLE=100;
	
	//获得战胜窃贼获得战功的系数
	public static final short EXPLOIT_MULTIPLE=10;
	//每次偷窃消耗军令
	public static final int CONSUME_ARMY_TOKEN=1;
	//获得偷窃成功的银币系数
	public static final double STEAL_MONEY_MULTIPLE=0.02;
	//获得偷窃成功的粮草系数
	public static final double STEAL_FOOD_MULTIPLE=0.02;
	
	//疯狂模式持续时间
	public static final  int CRAZY_TIME=60*60*8;
	
	//1为自己的封地(矿藏,农田)  0表示是别人的
	public static final byte IS_MYSELF=1;
	public static final byte NOIS_MYSELF=0;
	//最大开采时间
	public static final int OCCUPY_TIME=60*60*6;
	//保护时间
	public static final int PROTECT_TIME=60*60*2;
	//开启疯狂模式消耗的金币
	public static final int CONSUME_GOLD=-10;
	//疯狂模式增加的系数  0.5表示为原来的1.5倍
	public static final double CONSUME_ADD=0.5;
	
	/** 提升官阶			*/
	public static final short UP_GRADE_RANK = 22350;
	/** 官阶升级时 +1			*/
	public static final int UP_RANK = 1;
	/** 没有英雄				*/
	public static final int NULL_HERO = 0;
	/** 反抗					*/
	public static final short PVP_FIGHT = 22351;
	/** 声望基础值				*/
	public static final int BASICS_POINT = 50;
	/** 胜利加成				*/
	public static final int WIN_POINT = 50;
	/** 强敌加成				*/
	public static final int Q_D_J_C = 300;
	/** 强敌分母				*/
	public static final double Q_D_F_M = 5000;
	/** 保护盾时间常量(秒)			*/
	public static final long PRO_TIME = 7200;
	/** 邮件标题				*/
	public static final String TITLE_MAILE = "那个谁谁谁欠我邮件标题" ;
	/** 邮件征文				*/
	public static final String CONTEXT_MIAL1 = "欠我邮件正文" ;
	/** 获取官职信息			*/
	public static final short GET_RANK_INFO = 22352;
	/** 领取俸禄				*/
	public static final short GET_RANK_AWARD = 22353;
	
	public static final int MARCHING = -1;
	
	public static final int CAN_MARCH = 0;
	
	/** 部队状态：移动		*/
	public static final byte ARMY_MOVE = -1;
	/** 部队状态：停留		*/
	public static final byte ARMY_LAZY = 1;
	/** 部队状态：城墙		*/
	public static final byte ARMY_DEF = 2;
	/** 部队状态：护都		*/
	public static final byte ARMY_CORE = 3;
	/** 部队状态：进攻		*/
	public static final byte ARMY_ATK = 4;
	
	
	/** 没有物品编号		 */
	public static final int NO_ITEM_ID = 0;
	/** 大米的物品编号		 */
	public static final int ITEM_RICE = 20040;
	/** 铁矿的物品编号		 */
	public static final int ITEM_MINE = 20043;
	public static final short GET_MYLOARD_INFO = 22355;
	/**清空进贡信息*/
	public static final short CLEAR_PUM_INFO = 22356;
	/**发生战斗*/
	public static final byte FIGHTING = 1;
	/**没有发生战斗*/
	public static final byte NO_FIGHTING = 0;
	/**小村庄暴击率*/
	public static final int VILLAGE_PROPBILITY = 2000;
	/**小村庄补偿*/
	public static final int VILLAGE_COINS = 500;
	/** 世界阵型判定量		*/
	public static final int MINUS_NUM = 3000;
}
