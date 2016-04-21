package byCodeGame.game.moudle.sign;

public class SignConstant {
	/** 获取玩家的签到信息	*/
	public static final short GET_SIGN_INFO = 21901;
	/** 当日签到		 	*/
	public static final short SIGN_TODAY = 21902;
	/** 补签				*/
	public static final short SIGN_RETROACTIVE = 21903;
	/** 领取奖励			*/
	public static final short GET_AWARD_SIGN = 21904;
	/** 领取签到武将		*/
	public static final short GET_SIGN_HERO = 21905;
	
	
	/** 签到武将			*/
	public static final int SIGN_HERO_ID = 2503;
	/** 签到次数			*/
	public static final int SIGN_DAYS_NEED = 25;
	
	
	/** 补签需要的金币数量	*/
	public static final int SIGN_RETROACTIVE_GOLD =10;
	/** 补签递增量			*/
	public static final int SIGN_RETROACTIVE_GOLD2 = 5;
	/** 签到天数的最小		*/
	public static final byte MIN_SIGN_DAY = 1;
	/** 签到天数的最大		*/
	public static final byte MAX_SIGN_DAY = 31;
	
	/** 签到奖励常量	500	*/
	public static final int STATIC_NUMBER_1 = 500;
	/** 签到奖励常量	1000*/
	public static final int STATIC_NUMBER_2 = 1000;
	/** 签到奖励常量	1500*/
	public static final int STATIC_NUMBER_3 = 1500;
	/** 签到奖励常量	3000*/
	public static final int STATIC_NUMBER_4 = 3000;
	/** 签到奖励常量	4500*/
	public static final int STATIC_NUMBER_5 = 4500;
	/** 签到奖励常量	5000*/
	public static final int STATIC_NUMBER_6 = 5000;
	/** 签到奖励常量	7500*/
	public static final int STATIC_NUMBER_7 = 7500;
	/** 签到奖励军令1	2	*/
	public static final int STATIC_NUMBER_8 = 2;
	/** 签到奖励元宝	50	*/
	public static final int STATIC_NUMBER_9 = 50;
	/** 签到奖励军令2	5	*/
	public static final int STATIC_NUMBER_10 = 5;
	/** 签到奖励元宝2	100	*/
	public static final int STATIC_NUMBER_11 = 100;
	
	/** 今日是否签到	否	*/
	public static final byte NO_SIGN_TODAY = 0;
	/** 今天是否签到	是	*/
	public static final byte IS_SIGN_TODAY = 1;
}
