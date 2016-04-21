package byCodeGame.game.moudle.chapter;

public class ChapterConstant {
	/** 扫荡时候花费的元宝数量	*/
	public static final int RAID_CHAPTER_COST = 5;
	/** 扫荡券的物品编号		*/
	public static final int RAID_CHAPTER_ITEM_ID = 20029;
	/** 扫荡花费的类型	1扫荡券	*/
	public static final byte UES_TYPE_COOLPAN = 1;
	/** 扫荡花费的类型	2金币	*/
	public static final byte USE_TYPE_GOLD = 2;
	/** 扫荡需要的星数			*/
	public static final byte NEED_RAID_STARS = 3;
	/** 获取道具的最小背包数		*/
	public static final int NEED_NUM_BACK_ITEM = 1;
	/** 邮件title			*/
	public static final String MAIL_TITLE = "欠我一个邮件标题" ;
	/** 邮件正文				*/
	public static final String MAIL_CONTEXT = "欠我一个邮件正文";
	
	public static enum Action{
		/** 获取关卡信息	 */
		CHAPTER_DATA(21601),
		/** 战斗结束	 */
		GET_ALL_CHAPTER_DATA(21602),
		/** 扫荡关卡	 */
		RAIDS(21603),
		/**	重置关卡次数	 		*/
		REFRESH_TIMES(21604),
		/** 获取关卡奖励	 		*/
		GET_CHAPTER_AWARD(21605),
		/** 获取关卡星数	 		*/
		GET_CHAPTER_STARS(21606),
		/** 获取章节奖励信息  		*/
		GET_CHAPTER_AWARD_DATA(21607),
		/** 获取单一章节关卡信息		*/
		GET_PART_CHAPTER_DATA(21608);
		private short val;
		Action(int val){
			this.val = (short)val;
		}
		public short getVal(){
			return val;
		}
	}
	
	public static final byte AWARD_EXP = 1;
	public static final byte AWARD_MONEY = 2;
	public static final byte AWARD_GOLD = 3;
	public static final byte AWARD_EXPLOIT = 4;
	public static final byte AWARD_FOOD = 5;
	public static final byte AWARD_POPULATION = 6;
	public static final byte AWARD_ARMYTOKEN = 7;
	public static final byte AWARD_PRESTIGE = 8;
	public static final byte AWARD_EQUIP = 9;
	public static final byte AWARD_ITEM = 10;
	public static final byte AWARD_HERO = 11;
	/**
	 * 标准制式的奖励类型<br/>
	 * 2015.05.12
	 * @author xjd
	 *
	 */
	public static enum Award{
	 	EXP(1),
	 	MONEY(2),
	 	GOLD(3),
	 	EXPLOIT(4),
	 	FOOD(5),
	 	POPULATION(6),
	 	ARMYTOKEN(7),
	 	PRESTIGE(8),
	 	EQUIP(9),
	 	ITEM(10),
	 	HERO(11);
		
		private byte val;
		Award(int val){
			this.val = (byte)val;
		}
		public byte getVal(){
			return val;
		}
	}
	public static final int NUM_1 = 1;
	
	public static final int NUM_0 = 0;
	/** 成就奖励不可领取	*/
	public static final byte CAN_NOT_GET = 0;
	/** 成就奖励可领取		*/
	public static final byte CAN_GET = 1;
	/** 成就奖励已经领取	*/
	public static final byte ALREADY_GET = 2;
	/** 扫荡令单次消耗		*/
	public static final short USE_RIDS = -1;
}
