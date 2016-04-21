package byCodeGame.game.moudle.task;

public class TaskConstant {
	
	/** 获取任务信息	 	 */
	public static final short GET_TASK = 20501;
	/** 完成任务 领取奖励	 */
	public static final short COMPLETE_TASK = 20502;
	/** 获取新的任务		 */
	public static final short GET_NEW_TASK = 20503;
	/** 更新任务			 */
	public static final short UPDATE_TASK = 20504;
	/** 领取每日任务奖励	 */
	public static final short GET_AWARD_DAILY_TASK = 20505;
	/** 一键完成每日任务	 */
	public static final short QUICK_COMPLETE_DAILT_TASK = 20506;
	/** 获取每日任务信息	 */
	public static final short GET_DAIL_TASK = 20507;

	/** 任务完成	 		 */
	public static final byte COMPLENT = 1;
	/** 任务未完成			 */
	public static final byte NOT_COMPLENT = 0;
	/** 每日任务已经领取状态	 */
	public static final byte AUTO_COMPLENT = 2;
	
	/** 每日兵种科技点	*/
	public static final int ARMS_RESEARCH_NUM = 10;
	/** 建筑	 		*/
	public static final int TYPE_BUILD = 1;
	/** 招募	 		*/
	public static final int TYPE_RECRUIT = 2;
	/** 征收 	 		*/
	public static final int TYPE_INCOME = 3;
	/** 关卡	 		*/
	public static final int TYPE_CHAPTER = 4;
	/** 科技	 		*/
	public static final int TYPE_SCIENCE = 5;
	/** 训练	 		*/
	public static final int TYPE_TRAIN = 6;
	/** 配置	 		*/
	public static final int TYPE_DEPLOY = 7;
	/** 扩展	 		*/
	public static final int TYPE_EXPEND = 8;
	/** 邮件	 		*/
	public static final int TYPE_MAIL = 9;
	/** 等级	 		*/
	public static final int TYPE_LV = 10;
	/** 会谈	 		*/
	public static final int TYPE_TALK = 11;
	/** 装备	 		*/
	public static final int TYPE_EQUIP = 12;
	/** 强化	 		*/
	public static final int TYPE_SSTRENGTHEN = 13;
	/** 阵型	 		*/
	public static final int TYPE_FORMATION = 14;
	/** 队形	 		*/
	public static final int TYPE_PUB = 15;
	/** 兵种	 		*/
	public static final int TYPE_ARENA = 16;
	/** 签到		 	*/
	public static final int TYPE_SIGN = 17;
	/** 军团			*/
	public static final int TYPE_LEGION = 18;
	/** 资源			*/
	public static final int TYPE_RESOURCE = 19;
	/** 扫荡	 		*/
	public static final int TYPE_RAIDS = 20;
	/** 队列	 		*/
	public static final int TYPE_QUEUE = 21;
	/** 武将等级		*/
	public static final int TYPE_HEROLV = 22;
	/** 资源	 		*/
	public static final int TYPE_PVP = 23;
	/** 晋升	 		*/
	public static final int TYPE_RLV = 24;
	/** 主城场景		*/
	public static final int TYPE_BULIDLV = 25;
	/** 配属			*/
	public static final int TYPE_ATTACH = 26;
	/** 寻访			*/
	public static final int TYPE_VISIT = 27;
	/** 制造			*/
	public static final int TYPE_MAKE = 28;
	/** 酒桌			*/
	public static final int TYPE_DESK = 29;
	/** vip			*/
	public static final int TYPE_VIP = 30;
	
	/** 玩家	 		*/
	public static final int TYPE_ROLE = 98;
	/** 国家	 		*/
	public static final int TYPE_NATION = 99;
	
	
	public static final String REWARD_TYPE_EXP = "1";
	public static final String REWARD_TYPE_MONEY = "2";
	public static final String REWARD_TYPE_FOOD = "5";
	public static final String REWARD_TYPE_EXPLOIT = "4";
	public static final String REWARD_TYPE_GOLD = "3";
	public static final String REWARD_TYPE_PUPLATION = "6";
	public static final String REWARD_TYPE_ARMYTOKEN = "7";
	public static final String REWARD_TYPE_PRISAGE = "8";
	public static final String REWARD_TYPE_ITEM = "10";
	public static final String REWARD_TYPE_EQUIP = "9";
	public static final String REWARD_TYPE_HERO = "11";
	public static final String REWARD_TYPE_DAILY = "12";
	
	public static final int TYPE_0 = 0;
	public static final int TYPE_1 = 1;
	public static final int TYPE_2 = 2;
	public static final int TYPE_3 = 3;
	public static final int TYPE_6 = 6;
	public static final byte TYPE_BYTE_1 = 1;
	public static final byte TYPE_BYTE_2 = 2;
	public static final int ANY_SCIENCE = 99;
	public static final int TYPE_HOUSE = 3;
	public static final int TYPE_FARM = 2;
	public static final int TYPE_BARRACK = 3;
	public static final int TYPE_WEAPON = 5;
	public static final int TYPE_YU_LING_ZHEN = 1001;
	
	
	/** 任务奖励邮件的标题		*/
	public static final String MAIL_TASK_TITLE = "YJ你又欠我一个邮件标题";
	/** 任务奖励邮件的征文		*/
	public static final String MAIL_TASK_CONTEXT = "YJ你又欠我一个邮件正文";
	
	
	/** 科技的特殊判定类型0 全部科技*/
	public static final byte ALL_SCIENCE_TYPE = 0;
	
	/** 每日任务奖励常量	军令	 */
	public static final int STATIC_AWARD_ARMYTOKEN = 2;
	/** 每日任务奖励常量	200	 */
	public static final int STATIC_NUMBER1 = 200;
	/** 每日任务经理常量	300	 */
	public static final int STATIC_NUMBER2 = 300;
	/** 每日任务奖励常量	500	 */
	public static final int STATIC_NUMBER3 = 500;
	/** 每日任务奖励常量	600	 */
	public static final int STATIC_NUMBER4 = 600;
	/** 每日任务奖励常量	700	 */
	public static final int STATIC_NUMBER5 = 700;
	/** 每日任务奖励常量	800	 */
	public static final int STATIC_NUMBER6 = 800;
	
	/** 一键完成每日任务的花费(金币)	 */
	public static final int COST_QUICK_COMPLETE = 20;
	/** 每日任务重置玩家需求的等级		 */
	public static final int INIT_DAILY_TASK_LV = 5;
	/** 任务类型： 每日任务			 */
	public static final int TASK_TYPE_DAILY = 2;
}
