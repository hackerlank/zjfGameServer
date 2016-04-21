package byCodeGame.game.moudle.fight;

public class FightConstant {
	
	public static enum Action{
		/** 请求攻打关卡	 */
		REQUEST_ATK_CHAPTER(21701),
		/** 根据UUID获取战斗回访*/
		GET_REPORT_UUID(21704);
		
//		/** 战斗结束	 */
//		CHAPTER_FIGHT_OVER(21702),
//		/** 领取关卡奖励	 */
//		GET_CHAPTER_REWARD(21703);
		
		private short val;
		Action(int val){
			this.val = (short)val;
		}
		public short getVal(){
			return val;
		}
	}
//	/** 请求攻打关卡	 */
//	public static final short REQUEST_ATK_CHAPTER = 21701;
//	/** 战斗结束	 */
//	public static final short CHAPTER_FIGHT_OVER = 21702;
//	/** 领取关卡奖励	 */
//	public static final short GET_CHAPTER_REWARD = 21703;
	
	/** 友方	 */
	public static final byte CAMP_FRIEND = -1;
	/** 敌方	 */
	public static final byte CAMP_ENEMY = 1;
	
	/** 武力系数	 */
	public static final int XISHU_ATK = 12;
	/** 这个值在服务器中无意义，客户端需要	 */
	public static final int AWARD_TYPE = 0;
	/** 英雄阵型中的判定				 */
	public static final int NULL_HERO = 0;
	/** 是否需要检测兵力		0:是	 */
	public static final byte IS_FULL_ARMY_NUMBER = 0;
	/** 					1: 否*/
	public static final byte NO_FULL_ARMY_NUMBER = 1;
	
	/** 评定星数时候使用的策略	1号策略	 */
	public static final int TYPE_STARS_1 = 1;
	/** 评定星数时候使用的策略	2号侧率	 */
	public static final int TYPE_STARS_2 = 2;
	/** 评定星数时候使用的策略	atk阵营	 */
	public static final int TYPE_ATT = 1;
	/** 评定星数时候使用的策略	def阵营	 */
	public static final int TYPE_DEF = 2;
	/** 星数	1星						 */
	public static final byte STARS_1 = 1;
	/** 星数	2星						 */
	public static final byte STARS_2 = 2;
	/** 星数	3星						 */
	public static final byte STARS_3 = 3;
	/** 增加关卡今日攻打次数				 */
	public static final byte BYTE_1 = 1;
	
	/** 最小可出征人口					 */
	public static final int MIN_CAN_FIGHT = 5;
	/** byte 0						 */
	public static final byte NUM_0 = 0;
	/** 初始关卡						 */
	public static final int FIRST_CHAPTER_ID = 80000;
	/** 数字1						 */
	public static final int NUM_USE_1 = 1; 
	/** 数字2						 */
	public static final int NUM_USE_2 = 2;
}
