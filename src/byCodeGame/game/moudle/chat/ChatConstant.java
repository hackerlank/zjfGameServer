package byCodeGame.game.moudle.chat;

public class ChatConstant {

	/** 世界消息	 */
	public static final short SEND_CHAT = 20201;
	/** 接收消息（服务器主动发送）	 */
	public static final short GET_CHAT = 20202;
	/** 私聊		 */
	public static final short PRIVATE_CHAT = 20203;
	/** 国家聊天	 */
	public static final short COUNTRY_CHAT = 20204;
	/** 军团聊天	 */
	public static final short LEGION_CHAT = 20205;
	
	/** 系统频道	 */
	public static final byte SYSTEM_TYPE = 99;
	/** 世界频道	 */
	public static final byte WORLD_TYPE = 1;
	/** 国家频道	 */
	public static final byte COUNTRY_TYPE = 2;
	/** 组队频道	 */
	public static final byte MATE_TYPE = 5;
	/** 私聊频道	 */
	public static final byte PRIVATE_TYPE = 4;
	/** 军团频道*/
	public static final byte LEGION_TYPE = 3;
	
	/** 世界频道发言时间限制	 */
	public static final long TIME_WORLD = 10000;
	/** 私聊频道发言时间限制	 */
	public static final long TIME_PRIVATE = 1000;
	/** 其它频道发言时间限制	 */
	public static final long TIME_OTHER = 3000;
	
	/**  消息类型  文字 ： 0	 */
	public static final byte MESSAGE_TYPE_WORDS = 0;
	/**  消息类型  道具 ： 1	 */
	public static final byte MESSAGE_TYPE_ITEM = 1;
	/**  消息类型	武将 ：2	 */
	public static final byte MESSAGE_TYPE_HERO = 2;
	/**	  消息类型	战报 ：3	 */
	public static final byte MESSAGE_TYPE_REPORT = 3;
	/** 消息类型 官邸等级：4*/
	public static final byte MESSAGE_TYPE_ROLE_LV = 4;
	/** 消息类型 VIP：5*/
	public static final byte MESSAGE_TYPE_VIP = 5;
	/** 消息类型 升星：6*/
	public static final byte MESSAGE_TYPE_RANK = 6;
	/** 消息类型 转职：7*/
	public static final byte MESSAGE_TYPE_DUTY = 7;
	/** 消息类型 转生：8*/
	public static final byte MESSAGE_TYPE_REBIRTH = 8;
	/** 消息类型 官职：9*/
	public static final byte MESSAGE_TYPE_GRADERANK = 9;
	/** 消息类型 开始行军：10*/
	public static final byte MESSAGE_TYPE_MARCH = 10;
	/** 消息类型 战斗结果：11*/
	public static final byte MESSAGE_TYPE_FIGHT_RESULT = 11;
	/** 消息类型 城市更替：12*/
	public static final byte MESSAGE_TYPE_CHANGE_CITY = 12;
	/** 消息类型 城池被攻*/
	public static final byte MESSAGE_TYPE_UNDER_ATTACK = 13;
	/** 消息类型 排名*/
	public static final byte MESSAGE_TYPE_RANKING = 14;
	/** 消息类型 公会 */
	public static final byte MESSAGE_TYPE_LEGION = 15;
	/**消息类型 活动结束*/
	public static final byte MESSAGE_TYPE_ACTIVITY_END = 16;
	
	
	
	
	
	/**	 没有目标ID时候使用	 */
	public static final int NO_TARGET_ID = 0;
	/**	 没有目标名字时使用	 */
	public static final String NO_TARGET_NAME = "0";
	/** VIP等级0			 */
	public static final byte NO_VIP_LV = 0;
	
	public static final String BAISE = "#ffffff";
	public static final String LVSE = "#00FF00";
	public static final String LANSE = "#EC88DC";
	public static final String ZISE = "#DF3AF3";
	public static final String HONGSE = "#f34d4d";
	
	public static final int NATION_SHU = 1;
	public static final int NATION_WEI = 2;
	public static final int NATION_WU = 3;
}
