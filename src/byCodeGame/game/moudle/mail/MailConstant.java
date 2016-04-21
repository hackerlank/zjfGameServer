package byCodeGame.game.moudle.mail;

public class MailConstant {
	/** 玩家发送邮件		*/
	public static final short SEND_MAIL_PLAYER = 20801;
	/** 获取当前玩家所有的邮件	*/
	public static final short GET_ALL_MAIL = 20802;
	/** 提取附件			*/
	public static final short TAKE_ITEM_MAIL = 20803;
	/** 删除邮件			*/
	public static final short DELETE_MAIL = 20804;
	/** 系统推送邮件		*/
	public static final short NEW_MAIL_COME = 20805;
	/** 设置邮件是否已读		*/
	public static final short MAIL_CHECK_TYPE = 20806;
	
	/** 邮件类型   玩家		*/
	public static final byte MAIL_TYPE_PLAYER = 3;
	/** 邮件类型   奖励		*/
	public static final byte MAIL_TYPE_REWARD = 2;
	/** 邮件类型   系统		*/
	public static final byte MAIL_TYPE_SYSTEM = 1;
	
	/** 邮件已读			*/
	public static final byte MAIL_CHECKED_ALREADY = 1;
	/** 邮件未读			*/
	public static final byte MAIL_CHECKED_NOT = 0;
	
	/** 道具的类型	1装备	*/
	public static final byte TYPE_1 = 9;
	/** 道具的类型 2消耗品	*/
	public static final byte TYPE_2 = 10;
	/** 道具的类型 3金币		*/
	public static final byte TYPE_3 = 3;
	/** 道具的类型 4银币		*/
	public static final byte TYPE_4 = 2;
	/** 道具的类型 5粮草		*/
	public static final byte TYPE_5 = 5;
	
	/** 系统邮件的发送人	*/
	public static final String SEND_NAME = "系统";
	
	/** 邮件的标题最长		*/
	public static final int MAX_TILE_LENGTH = 20;
	/** 邮件的征文最长		*/
	public static final int MAX_CONTEXT_LENGTH = 200;
	
	/** 使用发送邮件功能的最小等级		*/
	public static final short MIN_LV_SEND_MAIL = 1;
	
	/** 邮件默认的保留时间			*/
	public static final long TIME_MAIL_SAVE = 259200;
	/** 客户端所需					*/
	public static final String STR_NUM = "0";
	/** 邮件时间是否为0				*/
	public static final long CHECK_TIME = 0;
}
